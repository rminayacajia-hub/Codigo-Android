import { Component } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { Pagina2Page } from '../pagina2/pagina2.page';
import { ToastController } from '@ionic/angular';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
  standalone: false,
})
export class HomePage {

  constructor(public modalCtrl: ModalController, 
              public toastCtrl: ToastController) {}

  // crear propiedad para controlar el modal
  isModalOpen = false;
  
  // método para abrir/cerrar el modal
  setOpen(isOpen: boolean) {
    this.isModalOpen = isOpen;
  }
  // método para llamar el modal
  async llamarModal() {
    const modal = await this.modalCtrl.create({
      component: Pagina2Page,
      cssClass: 'my-custom-class'
    });
    return await modal.present();
  }
  //metodo para mostrar toast

  async VerToast(message: string) {
    const toast = await this.toastCtrl.create({
      message: message,
      duration: 2000,
      position: 'bottom'
    });
    await toast.present();
  }

}
