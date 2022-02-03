package top.itning.yunshunas.music.repository;

import org.apache.ibatis.annotations.Param;
import top.itning.yunshunas.music.entity.Music;

import java.util.List;
import java.util.Optional;

/**
 * @author itning
 * @date 2020/9/5 11:15
 */
public interface MusicRepository {
    List<Music> findAllByNameLikeOrSingerLike(@Param("name") String name, @Param("singer") String singer);

    List<Music> findAllByNameLike(@Param("name") String name);

    List<Music> findAllBySingerLike(@Param("singer") String singer);

    Optional<Music> findByMusicId(@Param("musicId") String musicId);

    Optional<Music> findById(@Param("id") long id);

    boolean existsByName(@Param("name") String name);

    void deleteByMusicId(@Param("musicId") String musicId);

    List<Music> findAllByNameLikeAndSingerLike(@Param("name") String name, @Param("singer") String singer);

    List<Music> findAll();

    void save(Music music);
}
