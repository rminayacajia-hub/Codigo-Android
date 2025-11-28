import { Component } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
  standalone: false,
})
export class HomePage {
   // Variables para la operación y resultado
  operacion: string = '';
  resultado: string = '';
  toastOpen = false;
  toastMessage = '';
  modalAbierto = false;
  historial: string[] = [];
  esperandoNuevoValor = false;
  valorPrevio: number | null = null;
  operador: string | null = null;

  constructor() {}

  
// Métodos para manejar las operaciones de la calculadora
  onClearEntry() {
    this.resultado = '';
    this.operacion = '';
    this.valorPrevio = null;
    this.operador = null;
    this.esperandoNuevoValor = false;
  }
// Metodo para borrar el último dígito
  onBackspace() {
    // Si no hay nada que borrar, sale
    if (!this.resultado) {
      return;
    }
    // Elimina el último carácter de la cadena
    this.resultado = this.resultado.slice(0, -1);
  }
  
//funcion para operaciones unarias
  onUnaryOperation(operation: string) {
    // Implementar operaciones unarias como raíz cuadrada, usando el valor actual del display
    const valor = this.parseValorActual();
    // Si no hay un valor numérico válido, no se hace nada
    if (valor === null) {
      return;
    }

    let resultadoOperacion: number | null = null;

    switch (operation) {
      case 'sqrt':
        // Validación: no se puede sacar raíz cuadrada de un número negativo
        if (valor < 0) {
          this.mostrarToast('No se puede calcular la raíz de un número negativo');
          return;
        }
        resultadoOperacion = Math.sqrt(valor);
        break;
      default:
        // Si se envía una operación unaria no soportada, se sale sin hacer cambios
        return;
    }

    // Si se obtuvo un resultado, se actualiza pantalla y se registra en historial
    if (resultadoOperacion !== null) {
      const simbolo = operation === 'sqrt' ? '√' : operation;
      this.operacion = `${simbolo}(${valor}) =`;
      this.resultado = resultadoOperacion.toString();
      this.agregarAHistorial(`${this.operacion} ${this.resultado}`);
      // Después de una operación unaria, se espera un nuevo valor si el usuario escribe algo
      this.esperandoNuevoValor = true;
      this.valorPrevio = null;
      this.operador = null;
    }
  }

//funcion para operaciones binarias
  onBinaryOperation(operation: string) {
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
      this.resultado = this.valorPrevio.toString();
    }

    // Guarda el nuevo operador seleccionado usando el parámetro recibido
    this.operador = operation;
    // Actualiza el texto de operación que se muestra arriba del display usando el símbolo legible
    this.operacion = `${this.valorPrevio} ${this.obtenerSimboloOperacion(this.operador)}`;
    // Indica que el siguiente dígito será un nuevo valor
    this.esperandoNuevoValor = true;
  }
  
//funcion para operaciones con digitos
  onDigit(digito: string) {
    // Si se estaba esperando un nuevo valor, se reemplaza el contenido
    if (this.esperandoNuevoValor) {
      this.resultado = digito;
      this.esperandoNuevoValor = false;
    } else {
      // Si no, se concatena el dígito al valor actual
      this.resultado = (this.resultado || '') + digito;
    }
    }
    private agregarAHistorial(entrada: string) {
    this.historial.unshift(entrada);
  }
  
//funcion para abrir el modal del historial
  openHistoryModal() {
    // TODO: Implementar lógica para abrir el modal del historial
    this.abrirModalHistorial();
  }
  //funcion para abrir el modal del historial
  abrirModalHistorial() {
    this.modalAbierto = true;
  }
  //función para cerrar el modal del historial
  cerrarModalHistorial() {
    this.modalAbierto = false;
  }
  //funcion para operaciones con decimales
  OnDecimal(decimal: string) {
   // Si se esperaba un nuevo valor, comienza en "0." (ej. 0.5) y el historial lo muestra en modal
    if (this.esperandoNuevoValor) {
      this.resultado = '0.';
      this.esperandoNuevoValor = false;
      return;
    }

    // Si no hay valor aún, comienza en "0. hasta que ingrese uno" 
    if (!this.resultado) {
      this.resultado = '0.';
      return;
    }

    // Solo agrega el punto si aún no existe uno
    if (!this.resultado.includes('.')) {
      this.resultado += '.';
    }
  }
//funcion para calcular
  OnEquals() {
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
    this.operacion = `${this.valorPrevio} ${this.obtenerSimboloOperacion(this.operador)} ${valor} =`;
    // Muestra el resultado en el display
    this.resultado = resultado.toString();
    // Agrega la operación al historial
    this.agregarAHistorial(`${this.operacion} ${this.resultado}`);
    // Limpia el estado para una nueva operación
    this.valorPrevio = null;
    this.operador = null;
    this.esperandoNuevoValor = true;
  }

  // Convierte el valorActual (string) a número seguro o null si no es válido
  private parseValorActual(): number | null {
    if (this.resultado === '' || this.resultado === null || this.resultado === undefined) {
      return null;
    }
    const valor = Number(this.resultado);
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
  
}
