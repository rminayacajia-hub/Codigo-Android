import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { EcontactoPageRoutingModule } from './econtacto-routing.module';

import { EcontactoPage } from './econtacto.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    EcontactoPageRoutingModule
  ],
  declarations: [EcontactoPage]
})
export class EcontactoPageModule {}
