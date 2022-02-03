import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {mergeMap, Subscription} from "rxjs";
import {MusicService} from "../../../../service/music.service";
import {NzMessageService} from "ng-zorro-antd/message";
import {NzUploadChangeParam, NzUploadFile, NzUploadXHRArgs} from "ng-zorro-antd/upload/interface";
import {MusicManageDTO} from "../../../../entity/MusicManageDTO";

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.scss']
})
export class EditComponent implements OnInit {

  formParams: FormGroup;

  dataLoading = true;
  editBtnLoading = false;
  data: MusicManageDTO;
  lyricData: string;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private fb: FormBuilder,
              private musicService: MusicService,
              private message: NzMessageService) {
  }

  ngOnInit(): void {
    this.formParams = this.fb.group({
      name: ['', [Validators.required]],
      singer: ['', [Validators.required]],
      musicId: ['', [Validators.required]],
      lyricId: ['', [Validators.required]],
      lyric: [],
      musicFile: [],
      musicFileSource: []
    });
    this.route.params.pipe(
      mergeMap(params => this.musicService.oneMusicInfo(params['id']))
    ).subscribe(data => {
      this.dataLoading = false;
      console.log(data);
      if (!data) {
        this.message.error('音乐不存在！');
        this.router.navigateByUrl('/').catch(console.error);
        return;
      }
      this.data = data;
      this.formParams.patchValue({name: data.name});
      this.formParams.patchValue({singer: data.singer});
      this.formParams.patchValue({musicId: data.musicId});
      this.formParams.patchValue({lyricId: data.lyricId});
      this.musicService.getLyric(this.data.lyricUri).subscribe(data => {
        this.lyricData = data;
      });
    });
  }

  doEdit() {
    for (const i in this.formParams.controls) {
      if (this.formParams.controls.hasOwnProperty(i)) {
        this.formParams.controls[i].markAsDirty();
        this.formParams.controls[i].updateValueAndValidity();
      }
    }
    if (this.formParams.valid) {
      console.log(this.formParams.value)
    }
  }

  onFileChange(event: any) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      this.formParams.patchValue({
        musicFileSource: file
      });
    }
  }
}
