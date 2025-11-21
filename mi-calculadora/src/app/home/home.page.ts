import { Component } from '@angular/core';

// Decorador que indica que esta clase es un componente de Angular
@Component({
  // Selector utilizado en las plantillas HTML para insertar este componente
  selector: 'app-home',
  // Archivo de plantilla asociado al componente
  templateUrl: 'home.page.html',
  // Archivo(s) de estilos asociado(s) al componente
  styleUrls: ['home.page.scss'],
  // Se indica que este componente no es standalone (forma clásica con módulo)
  standalone: false,
})
export class HomePage {
  // Título que se muestra en la tarjeta de la calculadora
  titulo = 'Mi Calculadora';

  // Cadena que representa el valor que se ve actualmente en el display
  valorActual = '';
  // Texto con la operación que se está realizando (por ejemplo "5 +")
  operacionActual = '';
  // Número almacenado antes de introducir el siguiente valor (primer operando)
  valorPrevio: number | null = null;
  // Operador seleccionado (+, -, *, /, pow)
  operador: string | null = null;
  // Indica si se está esperando que el usuario introduzca un nuevo valor
  esperandoNuevoValor = false;

  // Controla si el Toast está visible
  toastOpen = false;
  // Mensaje que se mostrará en el Toast
  toastMessage = '';

  // Controla la visibilidad del modal de ayuda
  modalAbierto = false;

  // Arreglo donde se guarda el historial de operaciones realizadas
  historial: string[] = [];

  // Constructor del componente (sin dependencias inyectadas por ahora)
  constructor() {}

  // Maneja la pulsación de un dígito numérico
  onDigit(digito: string) {
    // Si se estaba esperando un nuevo valor, se reemplaza el contenido
    if (this.esperandoNuevoValor) {
      this.valorActual = digito;
      this.esperandoNuevoValor = false;
    } else {
      // Si no, se concatena el dígito al valor actual
      this.valorActual = (this.valorActual || '') + digito;
    }
  }

  // Maneja la inserción del separador decimal
  onDecimal() {
    // Si se esperaba un nuevo valor, comienza en "0." (ej. 0.5)
    if (this.esperandoNuevoValor) {
      this.valorActual = '0.';
      this.esperandoNuevoValor = false;
      return;
    }

    // Si no hay valor aún, comienza en "0. hasta que ingrese uno" 
    if (!this.valorActual) {
      this.valorActual = '0.';
      return;
    }

    // Solo agrega el punto si aún no existe uno
    if (!this.valorActual.includes('.')) {
      this.valorActual += '.';
    }
  }

  // Limpia todo el estado de la operación (similar a CE)
  onClearEntry() {
    this.valorActual = '';
    this.operacionActual = '';
    this.valorPrevio = null;
    this.operador = null;
    this.esperandoNuevoValor = false;
  }

  // Elimina el último carácter del valor actual (backspace)
  onBackspace() {
    // Si no hay nada que borrar, sale
    if (!this.valorActual) {
      return;
    }
    // Elimina el último carácter de la cadena
    this.valorActual = this.valorActual.slice(0, -1);
  }

  // Maneja las operaciones binarias (+, -, *, /, pow)
  onBinaryOperation(operador: string) {
    // Convierte el valor actual de texto a número
    const valor = this.parseValorActual();
    // Si no hay valor previo ni valor actual, no hace nada
    if (valor === null && this.valorPrevio === null) {
      return;
    }

    // Si aún no hay un valor previo almacenado, se guarda el valor actual
    if (this.valorPrevio === null && valor !== null) {
      this.valorPrevio = valor;
    } else if (this.operador && valor !== null && !this.esperandoNuevoValor) {
      // Si ya había un operador y un valor previo, se calcula antes de cambiar de operador
      this.valorPrevio = this.calcular(this.valorPrevio ?? 0, valor, this.operador);
      this.valorActual = this.valorPrevio.toString();
    }

    // Guarda el nuevo operador seleccionado
    this.operador = operador;
    // Actualiza el texto de operación que se muestra arriba del display
    this.operacionActual = `${this.valorPrevio} ${this.obtenerSimboloOperacion(operador)}`;
    // Indica que el siguiente dígito será un nuevo valor
    this.esperandoNuevoValor = true;
  }

  // Maneja operaciones unarias (actualmente solo raíz cuadrada)
  onUnaryOperation(tipo: 'sqrt') {
    const valor = this.parseValorActual();
    // Si no hay un valor válido, no hace nada
    if (valor === null) {
      return;
    }

    if (tipo === 'sqrt') {
      // Valida que no se intente sacar raíz de un número negativo
      if (valor < 0) {
        this.mostrarToast('No se puede calcular la raíz cuadrada de un número negativo');
        return;
      }
      // Calcula la raíz cuadrada y actualiza display y operación
      const resultado = Math.sqrt(valor);
      this.operacionActual = `√(${valor})`;
      this.valorActual = resultado.toString();
      // Agrega la operación al historial
      this.agregarAHistorial(`${this.operacionActual} = ${this.valorActual}`);
      // Reinicia el estado de operación previa
      this.valorPrevio = null;
      this.operador = null;
      this.esperandoNuevoValor = true;
    }
  }

  // Ejecuta el cálculo cuando se presiona el botón "="
  onEquals() {
    const valor = this.parseValorActual();
    // Si falta algún dato (operador, valor previo o valor actual), no hace nada
    if (this.operador === null || this.valorPrevio === null || valor === null) {
      return;
    }

    // Verifica el caso especial de división por cero
    if (this.operador === '/' && valor === 0) {
      this.mostrarToast('No se puede dividir entre cero');
      return;
    }

    // Realiza el cálculo con el operador actual
    const resultado = this.calcular(this.valorPrevio, valor, this.operador);
    // Actualiza el texto de operación mostrando la igualdad
    this.operacionActual = `${this.valorPrevio} ${this.obtenerSimboloOperacion(this.operador)} ${valor} =`;
    // Muestra el resultado en el display
    this.valorActual = resultado.toString();
    // Agrega la operación al historial
    this.agregarAHistorial(`${this.operacionActual} ${this.valorActual}`);
    // Limpia el estado para una nueva operación
    this.valorPrevio = null;
    this.operador = null;
    this.esperandoNuevoValor = true;
  }

  // Convierte el valorActual (string) a número seguro o null si no es válido
  private parseValorActual(): number | null {
    if (this.valorActual === '' || this.valorActual === null || this.valorActual === undefined) {
      return null;
    }
    const valor = Number(this.valorActual);
    return isNaN(valor) ? null : valor;
  }

  // Realiza el cálculo numérico según el operador recibido
  private calcular(a: number, b: number, operador: string): number {
    switch (operador) {
      case '+':
        return a + b;
      case '-':
        return a - b;
      case '*':
        return a * b;
      case '/':
        return a / b;
      case 'pow':
        return Math.pow(a, b);
      default:
        // Si no se reconoce el operador, devuelve el segundo operando
        return b;
    }
  }

  // Devuelve un símbolo legible para mostrar la operación en pantalla
  private obtenerSimboloOperacion(operador: string): string {
    switch (operador) {
      case '+':
        return '+';
      case '-':
        return '-';
      case '*':
        return '×';
      case '/':
        return '÷';
      case 'pow':
        return '^';
      default:
        return '';
    }
  }

  // Muestra un Toast con el mensaje indicado
  private mostrarToast(mensaje: string) {
    this.toastMessage = mensaje;
    this.toastOpen = true;
  }

  // Agrega una entrada de texto al historial de operaciones
  private agregarAHistorial(entrada: string) {
    this.historial.unshift(entrada);
  }

  // Abre el modal de historial
  abrirModal() {
    this.modalAbierto = true;
  }

  // Cierra el modal de historial
  cerrarModal() {
    this.modalAbierto = false;
  }
}
