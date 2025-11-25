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

  constructor() {}

  
// Métodos para manejar las operaciones de la calculadora
  onClearEntry() {
    this.operacion = '';
    this.resultado = '';
    this.historial.push('Limpiado');
  }
// Metodo para borrar el último dígito
  onBackspace() {
    if (this.resultado.length > 0) {
      this.resultado = this.resultado.slice(0, -1);
      this.historial.push(`Borrado: ${this.resultado}`);
    }
  }
  

  onUnaryOperation(operation: string) {
    // Implementar operaciones unarias como raíz cuadrada
    this.historial.push(`${operation}(${this.resultado}) = ${this.resultado}`);

  }

  onBinaryOperation(operation: string) {
    // Implementar operaciones binarias como potencia
    this.historial.push(`${this.operacion} ${operation} ${this.resultado} = ${this.resultado}`);
  }
  
  onDigit(digito: string) {
    // TODO: Implementar lógica para agregar dígito al resultado
    this.resultado += digito;
    this.historial.push(`${this.resultado}`);
  }
  
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

}
