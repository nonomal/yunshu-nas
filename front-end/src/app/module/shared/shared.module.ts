import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {IconsProviderModule} from '../../icons-provider.module';
import {NzLayoutModule} from 'ng-zorro-antd/layout';
import {NzMenuModule} from 'ng-zorro-antd/menu';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {NzTableModule} from 'ng-zorro-antd/table';
import {NzPopconfirmModule} from 'ng-zorro-antd/popconfirm';
import {NzDividerModule} from 'ng-zorro-antd/divider';
import {NzSwitchModule} from 'ng-zorro-antd/switch';
import {NzButtonModule} from 'ng-zorro-antd/button';
import {NzFormModule} from 'ng-zorro-antd/form';
import {NzInputModule} from 'ng-zorro-antd/input';
import {NzSelectModule} from 'ng-zorro-antd/select';
import {NzMessageModule} from 'ng-zorro-antd/message';
import {NzSpinModule} from 'ng-zorro-antd/spin';
import {NzModalModule} from 'ng-zorro-antd/modal';
import {NzNotificationModule} from 'ng-zorro-antd/notification';

const importAndExport = [
  ReactiveFormsModule,
  FormsModule,
  CommonModule,
  IconsProviderModule,
  NzLayoutModule,
  NzMenuModule,
  NzTableModule,
  NzPopconfirmModule,
  NzDividerModule,
  NzSwitchModule,
  NzButtonModule,
  NzFormModule,
  NzInputModule,
  NzSelectModule,
  NzMessageModule,
  NzSpinModule,
  NzModalModule,
  NzNotificationModule
];

@NgModule({
  declarations: [],
  imports: importAndExport,
  exports: importAndExport
})
export class SharedModule {
}
