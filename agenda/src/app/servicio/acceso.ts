import { Injectable } from '@angular/core';
// importar el preferences
import { Preferences } from '@capacitor/preferences';
// importar el toast comtroller
import { ToastController } from '@ionic/angular';
// importar htppcliente y htppHeaders
import { HttpClient  } from '@angular/common/http';
@Injectable({
  providedIn: 'root',
})
export class Acceso {
  // declarar variable tipo strig para almacenar el local host
  server: string  ="http://localhost:8080/WSAGENDA26/agenda.php"
  
  // creacion del constructur // y se crea la variable para el toast dentro del constructor
constructor(public toastCtrl: ToastController){}

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
//
}
