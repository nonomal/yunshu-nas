package top.itning.yunshunas.music.service;

import com.github.pagehelper.PageInfo;
import top.itning.yunshunas.music.dto.MusicManageDTO;

/**
 * 音乐管理服务
 *
 * @author itning
 * @since 2022/2/3 9:40
 */
public interface MusicManageService {
    /**
     * 所有音乐信息
     *
     * @param page    页码
     * @param size    每页数量
     * @param orderBy 排序
     * @return 所有音乐信息
     */
    PageInfo<MusicManageDTO> allMusic(int page, int size, String orderBy);

    /**
     * 单个音乐信息
     *
     * @param musicId 音乐ID
     * @return 单个音乐信息
     */
    MusicManageDTO musicInfo(String musicId);
}
