import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AcontactoPage } from './acontacto.page';

const routes: Routes = [
  {
    path: '',
    component: AcontactoPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AcontactoPageRoutingModule {}
