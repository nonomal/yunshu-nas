import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {RestModel} from "../entity/RestModel";
import {MusicManageDTO} from "../entity/MusicManageDTO";
import {filter, map, Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {NzNotificationService} from "ng-zorro-antd/notification";
import {PageInfo} from "../entity/PageInfo";

@Injectable({
  providedIn: 'root'
})
export class MusicService {

  private uri: string;

  constructor(private http: HttpClient, private notification: NzNotificationService) {
    this.uri = environment.production ? document.location.origin : 'http://localhost:8888'
  }

  allMusicInfo(pageNum: number, pageSize: number): Observable<PageInfo<MusicManageDTO>> {
    return this.http.get<RestModel<PageInfo<MusicManageDTO>>>(`${this.uri}/musicManage/musicList?page=${pageNum}&size=${pageSize}`)
      .pipe(
        map(it => {
          if (it.code !== 200) {
            console.error(`网络错误：${JSON.stringify(it)}`)
            this.notification.error('网络错误', it.msg);
            return new PageInfo<MusicManageDTO>();
          }
          return it.data;
        })
      )
  }

  oneMusicInfo(musicId: string): Observable<MusicManageDTO | null> {
    return this.http.get<RestModel<MusicManageDTO>>(`${this.uri}/musicManage/music/${musicId}`)
      .pipe(
        map(it => {
          if (it.code !== 200) {
            console.error(`网络错误：${JSON.stringify(it)}`)
            this.notification.error('网络错误', it.msg);
            return null;
          }
          return it.data;
        })
      );
  }
}
