import { Injectable } from '@angular/core';
// importar el preferences
import { Preferences } from '@capacitor/preferences';
// importar el toast comtroller
import { ToastController } from '@ionic/angular';
// importar htppcliente y htppHeaders
import { HttpClient, HttpHeaders } from '@angular/common/http';
@Injectable({
  providedIn: 'root',
})
export class Acceso {
  // declarar variable tipo strig para almacenar el local host
  persona: string  ="http://localhost:8080/WSAGENDA26/datos/persona.php"
  contacto: string  ="http://localhost:8080/WSAGENDA26/datos/contacto.php"
  server:string=""
  
  // creacion del constructur // y se crea la variable para el toast dentro del constructor
constructor(public toastCtrl: ToastController, public http:HttpClient){

}

// crear metodo enviar datos
enviarDatos(cuerpo:any, tabla:string){
  if  (tabla=='persona'){
    this.server=this.persona
  }
  else{
    this.server=this.contacto
  }
 
  // armar las cabeceras
  let head=new HttpHeaders({'Content-Type': 'application/json;charset=utf-8'})
  let opciones={
    headers:head
  }
  return this.http.post(this.server, JSON.stringify(cuerpo), opciones)
}

// creacion del metodo toast
async mostrarToast(mensaje:string, tiempo:number){
  const toast=await this.toastCtrl.create({
    message:mensaje,
    duration:tiempo,
    position:'top'
  })
  toast.present()
}
  // creacion del metodo asyncrono
  async crearSesion(id:string, valor:string)
  {
    await Preferences.set({
      key:id,
      value:valor
    })
  }
// crear el metodo para recuperar el valor
async obtenerSesion(id:string){
  const item = await Preferences.get({
    key:id
  })
  return item.value
}
// eliminar las variabls de sesion
async cerrarSesion(){
    await Preferences.clear()
  }

}
