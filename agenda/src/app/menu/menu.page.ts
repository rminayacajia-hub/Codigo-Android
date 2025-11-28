import { Component, OnInit } from '@angular/core';
import { Acceso } from '../servicio/acceso';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.page.html',
  styleUrls: ['./menu.page.scss'],
  standalone: false,
})
export class MenuPage implements OnInit {
  id_persona:string=""
  datospersona:any=[]

  constructor(public servicio: Acceso) { 
    this.servicio.obtenerSesion('idpersona').then((res:any)=>{
      this.id_persona=res
      this.dpersona(this.id_persona)
    })
    
  }

  ngOnInit() {
  }
dpersona(id:string){
  let datos={
    accion:'consulta',
    cod_persona:id
  }
  this.servicio.enviarDatos(datos).subscribe((res:any)=>{
    if(res.estado){
      this.datospersona=res.persona
      
    }
    else{
      this.servicio.mostrarToast(res.codigo,3000)
    } 
  })
}
}
