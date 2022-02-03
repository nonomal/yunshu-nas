import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {mergeMap} from "rxjs";
import {MusicService} from "../../../../service/music.service";
import {NzMessageService} from "ng-zorro-antd/message";

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.scss']
})
export class EditComponent implements OnInit {

  formParams: FormGroup;

  dataLoading = true;
  editBtnLoading = false;

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
      lyricId: ['', [Validators.required]]
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
      this.formParams.patchValue({name: data.name});
      this.formParams.patchValue({singer: data.singer});
      this.formParams.patchValue({musicId: data.musicId});
      this.formParams.patchValue({lyricId: data.lyricId});
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

    }
  }
}
