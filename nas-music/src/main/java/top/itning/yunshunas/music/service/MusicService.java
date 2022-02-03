package top.itning.yunshunas.music.service;

import com.github.pagehelper.PageInfo;
import top.itning.yunshunas.music.dto.MusicDTO;


/**
 * @author itning
 * @date 2020/9/5 11:25
 */
public interface MusicService {
    /**
     * 分页查找全部
     *
     * @param page    页码
     * @param size    每页数量
     * @param orderBy 排序
     * @return 音乐信息
     */
    PageInfo<MusicDTO> findAll(int page, int size, String orderBy);

    /**
     * 模糊搜索：搜索音乐名和歌手名，只要包含关键字就返回
     *
     * @param keyword 关键字
     * @param page    页码
     * @param size    每页数量
     * @param orderBy 排序
     * @return 音乐信息
     */
    PageInfo<MusicDTO> fuzzySearch(String keyword, int page, int size, String orderBy);

    /**
     * 模糊搜索：搜索音乐名，只要包含关键字就返回
     *
     * @param keyword 关键字
     * @param page    页码
     * @param size    每页数量
     * @param orderBy 排序
     * @return 音乐信息
     */
    PageInfo<MusicDTO> fuzzySearchName(String keyword, int page, int size, String orderBy);

    /**
     * 模糊搜索：搜索歌手名，只要包含关键字就返回
     *
     * @param keyword 关键字
     * @param page    页码
     * @param size    每页数量
     * @param orderBy 排序
     * @return 音乐信息
     */
    PageInfo<MusicDTO> fuzzySearchSinger(String keyword, int page, int size, String orderBy);
}
