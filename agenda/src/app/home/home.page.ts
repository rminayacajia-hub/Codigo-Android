import { Component } from '@angular/core';
// importar el servicio de acceso
import { Acceso } from '../servicio/acceso';


@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
  standalone: false,
})
export class HomePage {
  usuario:string="Juan";
  clave:string="123";
// declarar variable para el servicio
  constructor(public servicio: Acceso) {}
  ingreso() {
    this.servicio.crearSesion("nombre",this.usuario);
    this.servicio.crearSesion("pwd", this.clave);
  }
  // crear el método para obtener datos
  obtener(){
    this.servicio.obtenerSesion("nombre").then((res:any)=>{
      console.log("Valor obtenido:", res);
      this.servicio.mostrarToast(res, 2000);
    }).catch((error:any) => {
      console.error("Error al obtener sesión:", error);
      this.servicio.mostrarToast("Error al obtener datos", 2000);
    });
    
  }

}
