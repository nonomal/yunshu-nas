import {Component, OnInit} from '@angular/core';
import {MusicManageDTO} from "../../../../entity/MusicManageDTO";
import {NzTableQueryParams} from "ng-zorro-antd/table";
import {MusicService} from "../../../../service/music.service";
import {PageInfo} from "../../../../entity/PageInfo";

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent implements OnInit {
  tableData: MusicManageDTO[] = [];
  loading = true;
  pageInfo: PageInfo<MusicManageDTO> = new PageInfo<MusicManageDTO>();

  constructor(private musicService: MusicService) {
  }

  ngOnInit(): void {
    this.pageInfo.pageSize = 20;
    this.pageInfo.pageNum = 1;
  }

  refreshData(page = 1, size = 20): void {
    this.loading = true;
    this.musicService.allMusicInfo(page, size).subscribe(pageInfo => {
      this.pageInfo = pageInfo;
      this.tableData = pageInfo.list;
      this.loading = false;
    });
  }

  onQueryParamsChange(params: NzTableQueryParams): void {
    const {pageSize, pageIndex} = params;
    this.refreshData(pageIndex, pageSize);
  }

  delete(id: number) {

  }
}
