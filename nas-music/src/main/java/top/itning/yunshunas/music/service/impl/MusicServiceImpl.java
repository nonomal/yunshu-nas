package top.itning.yunshunas.music.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.itning.yunshunas.music.converter.MusicConverter;
import top.itning.yunshunas.music.datasource.CoverDataSource;
import top.itning.yunshunas.music.datasource.LyricDataSource;
import top.itning.yunshunas.music.datasource.MusicDataSource;
import top.itning.yunshunas.music.dto.MusicDTO;
import top.itning.yunshunas.music.entity.Music;
import top.itning.yunshunas.music.repository.MusicRepository;
import top.itning.yunshunas.music.service.MusicService;


/**
 * @author itning
 * @date 2020/9/5 11:25
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class MusicServiceImpl implements MusicService {
    private final MusicRepository musicRepository;
    private final MusicDataSource musicDataSource;
    private final LyricDataSource lyricDataSource;
    private final CoverDataSource coverDataSource;

    @Autowired
    public MusicServiceImpl(MusicRepository musicRepository, MusicDataSource musicDataSource, LyricDataSource lyricDataSource, CoverDataSource coverDataSource) {
        this.musicRepository = musicRepository;
        this.musicDataSource = musicDataSource;
        this.lyricDataSource = lyricDataSource;
        this.coverDataSource = coverDataSource;
    }

    @Override
    public PageInfo<MusicDTO> findAll(int page, int size, String orderBy) {
        return PageHelper.startPage(page, size, orderBy)
                .doSelectPage(musicRepository::findAll)
                .toPageInfo((item) -> {
                    MusicDTO musicDTO = MusicConverter.INSTANCE.entity2dto((Music) item);
                    musicDTO.setMusicUri(musicDataSource.getMusic(musicDTO.getMusicId()));
                    musicDTO.setLyricUri(lyricDataSource.getLyric(musicDTO.getLyricId()));
                    musicDTO.setCoverUri(coverDataSource.getCover(musicDTO.getMusicId()));
                    return musicDTO;
                });
    }

    @Override
    public PageInfo<MusicDTO> fuzzySearch(String keyword, int page, int size, String orderBy) {
        return PageHelper.startPage(page, size, orderBy)
                .doSelectPage(() -> musicRepository.findAllByNameLikeOrSingerLike(keyword, keyword))
                .toPageInfo((item -> {
                    MusicDTO musicDTO = MusicConverter.INSTANCE.entity2dto((Music) item);
                    musicDTO.setMusicUri(musicDataSource.getMusic(musicDTO.getMusicId()));
                    musicDTO.setLyricUri(lyricDataSource.getLyric(musicDTO.getLyricId()));
                    musicDTO.setCoverUri(coverDataSource.getCover(musicDTO.getMusicId()));
                    return musicDTO;
                }));
    }

    @Override
    public PageInfo<MusicDTO> fuzzySearchName(String keyword, int page, int size, String orderBy) {
        return PageHelper.startPage(page, size, orderBy)
                .doSelectPage(() -> musicRepository.findAllByNameLike(keyword))
                .toPageInfo((item) -> {
                    MusicDTO musicDTO = MusicConverter.INSTANCE.entity2dto((Music) item);
                    musicDTO.setMusicUri(musicDataSource.getMusic(musicDTO.getMusicId()));
                    musicDTO.setLyricUri(lyricDataSource.getLyric(musicDTO.getLyricId()));
                    musicDTO.setCoverUri(coverDataSource.getCover(musicDTO.getMusicId()));
                    return musicDTO;
                });
    }

    @Override
    public PageInfo<MusicDTO> fuzzySearchSinger(String keyword, int page, int size, String orderBy) {
        return PageHelper.startPage(page, size, orderBy)
                .doSelectPage(() -> musicRepository.findAllBySingerLike(keyword))
                .toPageInfo((item) -> {
                    MusicDTO musicDTO = MusicConverter.INSTANCE.entity2dto((Music) item);
                    musicDTO.setMusicUri(musicDataSource.getMusic(musicDTO.getMusicId()));
                    musicDTO.setLyricUri(lyricDataSource.getLyric(musicDTO.getLyricId()));
                    musicDTO.setCoverUri(coverDataSource.getCover(musicDTO.getMusicId()));
                    return musicDTO;
                });
    }
}
