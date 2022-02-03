package top.itning.yunshunas.music.dto;

import lombok.Data;

import java.io.Serializable;
import java.net.URI;
import java.util.Date;

/**
 * 音乐信息 后台专用
 *
 * @author itning
 * @since 2022/2/3 9:41
 */
@Data
public class MusicManageDTO implements Serializable {
    /**
     * 数据库主键ID
     */
    private Long id;
    /**
     * 音乐ID
     */
    private String musicId;
    /**
     * 音乐名
     */
    private String name;
    /**
     * 歌手
     */
    private String singer;
    /**
     * 歌词ID
     */
    private String lyricId;
    /**
     * 音乐类型
     * {@link top.itning.yunshunas.music.constant.MusicType}
     */
    private Integer type;
    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 更新时间
     */
    private Date gmtModified;
    /**
     * 歌曲URI
     */
    private URI musicUri;
    /**
     * 歌词URI
     */
    private URI lyricUri;
    /**
     * 封面URI
     */
    private URI coverUri;
}
