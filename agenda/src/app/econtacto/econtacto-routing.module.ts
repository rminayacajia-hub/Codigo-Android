import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { EcontactoPage } from './econtacto.page';

const routes: Routes = [
  {
    path: '',
    component: EcontactoPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class EcontactoPageRoutingModule {}
