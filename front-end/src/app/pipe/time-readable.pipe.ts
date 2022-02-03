import {Pipe, PipeTransform} from '@angular/core';
import * as dayjs from 'dayjs';

@Pipe({
  name: 'timeReadable'
})
export class TimeReadablePipe implements PipeTransform {

  transform(value: string, ...args: string[]): string {
    if (args && args[0]) {
      return dayjs(value).format(args[0]);
    }
    return dayjs(value).format('YYYY-MM-DD HH:mm:ss');
  }

}
