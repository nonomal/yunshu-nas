import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {IndexComponent} from "./module/index/component/index/index.component";
import {ListComponent} from "./module/music/component/list/list.component";
import {EditComponent} from "./module/music/component/edit/edit.component";

// const routes: Routes = [
//   { path: '', pathMatch: 'full', redirectTo: '/welcome' },
//   { path: 'welcome', loadChildren: () => import('./pages/welcome/welcome.module').then(m => m.WelcomeModule) }
// ];

const routes: Routes = [
  {
    path: '',
    component: IndexComponent,
    children: [
      {
        path: '',
        redirectTo: 'music',
        pathMatch: 'full'
      },
      {
        path: 'music',
        component: ListComponent
      },
      {
        path: 'music/edit/:id',
        component: EditComponent
      },
    ]
  },
  {path: '**', redirectTo: 'music'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
