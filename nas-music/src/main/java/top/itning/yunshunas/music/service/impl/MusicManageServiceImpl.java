package top.itning.yunshunas.music.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.itning.yunshunas.music.converter.MusicConverter;
import top.itning.yunshunas.music.datasource.CoverDataSource;
import top.itning.yunshunas.music.datasource.LyricDataSource;
import top.itning.yunshunas.music.datasource.MusicDataSource;
import top.itning.yunshunas.music.dto.MusicManageDTO;
import top.itning.yunshunas.music.entity.Music;
import top.itning.yunshunas.music.repository.MusicRepository;
import top.itning.yunshunas.music.service.MusicManageService;

/**
 * 音乐管理服务实现
 *
 * @author itning
 * @since 2022/2/3 9:43
 */
@Service
public class MusicManageServiceImpl implements MusicManageService {

    private final MusicRepository musicRepository;
    private final MusicDataSource musicDataSource;
    private final LyricDataSource lyricDataSource;
    private final CoverDataSource coverDataSource;

    @Autowired
    public MusicManageServiceImpl(MusicRepository musicRepository,
                                  MusicDataSource musicDataSource,
                                  LyricDataSource lyricDataSource,
                                  CoverDataSource coverDataSource) {
        this.musicRepository = musicRepository;
        this.musicDataSource = musicDataSource;
        this.lyricDataSource = lyricDataSource;
        this.coverDataSource = coverDataSource;
    }

    @Override
    public PageInfo<MusicManageDTO> allMusic(int page, int size, String orderBy) {
        return PageHelper.startPage(page, size, orderBy)
                .doSelectPage(musicRepository::findAll)
                .toPageInfo((item) -> {
                    MusicManageDTO musicDTO = MusicConverter.INSTANCE.entity2dto2((Music) item);
                    musicDTO.setMusicUri(musicDataSource.getMusic(musicDTO.getMusicId()));
                    musicDTO.setLyricUri(lyricDataSource.getLyric(musicDTO.getLyricId()));
                    musicDTO.setCoverUri(coverDataSource.getCover(musicDTO.getMusicId()));
                    return musicDTO;
                });
    }

    @Override
    public MusicManageDTO musicInfo(String musicId) {
        return musicRepository.findByMusicId(musicId)
                .map(it -> {
                    MusicManageDTO musicDTO = MusicConverter.INSTANCE.entity2dto2(it);
                    musicDTO.setMusicUri(musicDataSource.getMusic(musicDTO.getMusicId()));
                    musicDTO.setLyricUri(lyricDataSource.getLyric(musicDTO.getLyricId()));
                    musicDTO.setCoverUri(coverDataSource.getCover(musicDTO.getMusicId()));
                    return musicDTO;
                })
                .orElse(null);
    }
}
