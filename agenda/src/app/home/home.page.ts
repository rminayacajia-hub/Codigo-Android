import { Component } from '@angular/core';
// importar el servicio de acceso
import { Acceso } from '../servicio/acceso';
import { NavController } from '@ionic/angular';


@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
  standalone: false,
})
export class HomePage {
// declarar las variables tipo txt
txt_usuario:string=""
txt_clave:string=""
// declarar variable para el servicio
  constructor(public servicio: Acceso, public navCtrl: NavController) {}

// crear los metodos
login(){
  let datos={
    accion:'loggin',
    usuario:this.txt_usuario,
    clave:this.txt_clave
  }
  this.servicio.enviarDatos(datos).subscribe((res:any)=>{
    if(res.estado){
      //this.servicio.mostrarToast('idpersona: '+res.codigo,3000)
      this.servicio.crearSesion('idpersona',res.codigo)
      this.navCtrl.navigateRoot(['/menu'])
    }
    else{
      this.servicio.mostrarToast(res.codigo,3000)
    } 
  })
}
// crear los metodos
crearCuenta(){
  let datos={
    accion:'crear',
    usuario:this.txt_usuario,
    clave:this.txt_clave
  }
  this.servicio.enviarDatos(datos).subscribe((res:any)=>{
    if(res.estado){
      this.servicio.crearSesion('idpersona',res.codigo)
      this.navCtrl.navigateRoot(['/home'])
    }
    else{
      this.servicio.mostrarToast(res.codigo,3000)
    } 
  })
}
recuperar(){
  let datos={
    accion:'recuperar',
    usuario:this.txt_usuario,
    clave:this.txt_clave
  }
  this.servicio.enviarDatos(datos).subscribe((res:any)=>{
    if(res.estado){
      this.servicio.crearSesion('idpersona',res.codigo)
      this.navCtrl.navigateRoot(['/home'])
    }
    else{
      this.servicio.mostrarToast(res.codigo,3000)
    } 
  })
}

}
