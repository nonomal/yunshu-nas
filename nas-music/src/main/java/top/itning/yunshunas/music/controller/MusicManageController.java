package top.itning.yunshunas.music.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.itning.yunshunas.music.dto.MusicManageDTO;
import top.itning.yunshunas.music.dto.RestModel;
import top.itning.yunshunas.music.service.MusicManageService;
import top.itning.yunshunas.music.service.UploadService;

/**
 * 音乐管理接口
 *
 * @author itning
 * @since 2021/10/16 9:47
 */
@RestController
@RequestMapping("/musicManage")
public class MusicManageController {
    private final UploadService uploadService;
    private final MusicManageService musicManageService;

    @Autowired
    public MusicManageController(UploadService uploadService, MusicManageService musicManageService) {
        this.uploadService = uploadService;
        this.musicManageService = musicManageService;
    }

    /**
     * 音乐列表页
     *
     * @param page    页码
     * @param size    每页数量
     * @param orderBy 排序
     * @return 音乐列表
     */
    @GetMapping("/musicList")
    public ResponseEntity<RestModel<PageInfo<MusicManageDTO>>> musicList(@RequestParam(required = false, defaultValue = "1") int page,
                                                                         @RequestParam(required = false, defaultValue = "20") int size,
                                                                         @RequestParam(required = false, defaultValue = "gmt_modified desc") String orderBy) {
        return RestModel.ok(musicManageService.allMusic(page, size, orderBy));
    }

    /**
     * 单个歌曲信息
     *
     * @param musicId 音乐ID
     * @return 单个歌曲信息
     */
    @GetMapping("/music/{musicId}")
    public ResponseEntity<RestModel<MusicManageDTO>> musicInfo(@PathVariable String musicId) {
        return RestModel.ok(musicManageService.musicInfo(musicId));
    }

    /**
     * 音乐上传页面
     *
     * @return music_upload.html
     */
    @GetMapping("/musicUpload")
    public String musicUpload() {
        return "music_upload";
    }

    /**
     * 音乐上传
     *
     * @return music_upload.html
     */
    @PostMapping("/musicUpload")
    public String doMusicUpload(@RequestParam("file") MultipartFile file) throws Exception {
        uploadService.uploadMusic(file);
        return "music_upload";
    }

    /**
     * 歌词上传
     *
     * @return music_upload.html
     */
    @PostMapping("/lyricUpload")
    public String doLyricUpload(@RequestParam("musicId") String musicId, @RequestParam("file") MultipartFile file) throws Exception {
        uploadService.uploadLyric(musicId, file);
        return "music_upload";
    }
}
