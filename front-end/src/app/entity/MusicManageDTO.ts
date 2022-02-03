export class MusicManageDTO {
  /**
   * 数据库主键ID
   */
  id: number;
  /**
   * 音乐ID
   */
  musicId: string;
  /**
   * 音乐名
   */
  name: string;
  /**
   * 歌手
   */
  singer: string;
  /**
   * 歌词ID
   */
  lyricId: string;
  /**
   * 音乐类型
   *
   */
  type: number;
  /**
   * 创建时间
   */
  gmtCreate: string;
  /**
   * 更新时间
   */
  gmtModified: string;
  /**
   * 歌曲URI
   */
  musicUri: string;
  /**
   * 歌词URI
   */
  lyricUri: string;
  /**
   * 封面URI
   */
  coverUri: string;
}
