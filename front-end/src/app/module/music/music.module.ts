import {NgModule} from '@angular/core';

import {MusicRoutingModule} from './music-routing.module';
import {ListComponent} from './component/list/list.component';
import {SharedModule} from "../shared/shared.module";
import {TimeReadablePipe} from "../../pipe/time-readable.pipe";
import { EditComponent } from './component/edit/edit.component';


@NgModule({
  declarations: [
    TimeReadablePipe,
    ListComponent,
    EditComponent
  ],
  imports: [
    SharedModule,
    MusicRoutingModule
  ]
})
export class MusicModule {
}
