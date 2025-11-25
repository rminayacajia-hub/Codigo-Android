import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { AcontactoPageRoutingModule } from './acontacto-routing.module';

import { AcontactoPage } from './acontacto.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    AcontactoPageRoutingModule
  ],
  declarations: [AcontactoPage]
})
export class AcontactoPageModule {}
