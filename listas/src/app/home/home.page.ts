import { Component } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
  standalone: false,
})
export class HomePage {

  // crear array
  numeros:Array<number>=[1,2,3,4,5,6,7,8,9,10];
  nombres: Array<string> = ['Alice', 'Bob', 'Charlie', 'David', 'Eve'];
   // Variable para almacenar el nombre seleccionado
  resultado: string = '';
  // Esta variable se actualiza cuando se hace clic en un nombre de la lista
  constructor() {}
  // Método que se ejecuta cuando se hace clic en un nombre
  mostrar(seleccion: string) {
    // Aquí se actualiza la variable con el nombre seleccionado
    this.resultado = seleccion;
  }

}
