import { Component, OnInit } from '@angular/core';
import { Acceso } from '../servicio/acceso';
import { NavController } from '@ionic/angular';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.page.html',
  styleUrls: ['./menu.page.scss'],
  standalone: false,
})
export class MenuPage implements OnInit {
  id_persona:string=""
  datospersona:any=[]
  nombre:string=""
  contactos:any=[]

  constructor(public servicio: Acceso, public navCtrl:NavController) { 
    this.servicio.obtenerSesion('idpersona').then((res:any)=>{
      this.id_persona=res
      this.dpersona(this.id_persona)
      this.lcontactos()
    })
    
  }

  ngOnInit() {
  }
  // metodo consultar persona
dpersona(id:string){
  let datos={
    accion:'consulta',
    cod_persona:id
  }
  this.servicio.enviarDatos(datos, "persona").subscribe((res:any)=>{
    if(res.estado){
      this.datospersona=res.persona
      this.nombre=this.datospersona.nombre+" "+this.datospersona.apellido
    }
    else{
      this.servicio.mostrarToast(res.mensaje,3000)
    } 
  })
}
// metodo listar contactos
lcontactos(){
  let datos={
    accion:'consultar',
    cod_persona:this.id_persona
  }
  this.servicio.enviarDatos(datos, "contacto").subscribe((res:any)=>{
    if(res.estado){
      this.contactos=res.datos
    }
    else{
      this.servicio.mostrarToast(res.mensaje,3000)
    }
  })
}
// metodo nuevo contacto
nuevo(){
//permite llamar a la venta contacto
  this.navCtrl.navigateRoot(['contacto'])
  }
}
