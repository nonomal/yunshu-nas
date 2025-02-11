package top.itning.yunshunas.music;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import top.itning.yunshunas.common.util.Tuple2;
import top.itning.yunshunas.music.constant.MusicType;
import top.itning.yunshunas.music.entity.Music;
import top.itning.yunshunas.music.repository.MusicRepository;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
class MusicTest {
    @Autowired
    private MusicRepository musicRepository;

    @Test
    void addMusicFilesToTheDatabaseAndCopyFiles() {
        AtomicInteger atomicInteger = new AtomicInteger();
        File file = new File("C:\\Users\\wangn\\Music");
        Arrays.stream(Objects.requireNonNull(file.
                        listFiles((dir, name) -> MusicType.getMusicTypeFromFilePath(name).isPresent())))
                .map(f -> {
                    //noinspection OptionalGetWithoutIsPresent
                    MusicType musicType = MusicType.getMusicTypeFromFilePath(f.getPath()).get();
                    String name = f.getName();
                    System.out.println("准备添加：" + name);

                    int i = name.indexOf("-");
                    int end = name.indexOf("." + musicType.name().toLowerCase());
                    // 歌曲名-歌手名
                    //String mName = name.substring(0, i).trim();
                    //String mSinger = name.substring(i + 1, end).trim();
                    // 歌手-歌曲名
                    String mName = name.substring(i + 1, end).trim();
                    String mSinger = name.substring(0, i).trim();

                    Music music = new Music();
                    String id = UUID.randomUUID().toString().replace("-", "");
                    music.setMusicId(id);
                    music.setName(mName);
                    music.setSinger(mSinger);
                    music.setLyricId(id);
                    music.setType(musicType.getType());
                    return new Tuple2<>(music, f);
                })
                .collect(Collectors.toList())
                .parallelStream()
                .filter(musics -> {
                    List<Music> musicList = fingAllByNameAndSinger(musics.t1().getName(), musics.t1().getSinger());
                    if (!musicList.isEmpty()) {
                        log.error("已存在 {}", musicList);
                        return false;
                    } else {
                        log.info("添加 {}-{}-{}", musics.t1().getName(), musics.t1().getSinger(), musics.t1().getMusicId());
                        return true;
                    }
                })
                .peek(item -> musicRepository.save(item.t1()))
                .forEach(item -> {
                    try {
                        int copy = FileCopyUtils.copy(item.t2(), new File("E:\\music_yunshu\\" + item.t1().getMusicId()));
                        if (copy <= 0) {
                            log.warn("File Copy 0 File: {} Id {}", item.t2(), item.t1().getMusicId());
                        }
                        atomicInteger.incrementAndGet();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        System.out.println("总计添加：" + atomicInteger.get());
    }

    @Test
    void addLyric() {
        File file = new File("F:\\Music");
        Arrays.stream(Objects.requireNonNull(file.
                        listFiles((dir, name) -> name.endsWith(".lrc"))))
                .map(f -> {
                    String name = f.getName();

                    int i = name.indexOf("-");
                    int end = name.indexOf(".lrc");
                    // 歌曲名-歌手名
                    String mName = name.substring(0, i).trim();
                    String mSinger = name.substring(i + 1, end).trim();
                    // 歌手-歌曲名
                    //String mName = name.substring(i + 1, end).trim();
                    //String mSinger = name.substring(0, i).trim();

                    Music music = new Music();
                    music.setName(mName);
                    music.setSinger(mSinger);
                    return new Tuple2<>(music, f);
                })
                .peek(item -> {
                    List<Music> list = musicRepository.findAllByNameLikeAndSingerLike(item.t1().getName(), item.t1().getSinger());
                    if (list.size() != 1) {
                        log.error("list {} file {}", list.toString(), item.t2().toString());
                        return;
                    }
                    item.t1().setLyricId(list.get(0).getLyricId());
                })
                .filter(item -> null != item.t1().getLyricId())
                .forEach(item -> {
                    try {
                        String str = FileUtils.readFileToString(item.t2(), "gb2312");
                        String encode = new String(str.getBytes(), StandardCharsets.UTF_8);
                        //System.out.println(encode);
                        Files.writeString(Paths.get("F:\\lyric_yunshu\\" + item.t1().getLyricId()), encode);
                        /*int copy = FileCopyUtils.copy(item.getT2(), new File("F:\\lyric_yunshu\\" + item.getT1().getLyricId()));
                        if (copy <= 0) {
                            log.warn("File Copy 0 File: {} Id {}", item.getT2(), item.getT1().toString());
                        }*/
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    void getAllExtensionsInADirectory() {
        File file = new File("F:\\Music_1");
        Arrays.stream(Objects.requireNonNull(file.listFiles()))
                .map(f -> StringUtils.getFilenameExtension(f.getPath()))
                .distinct()
                .forEach(System.out::println);
    }

    @Test
    void addASingleMusicFile() throws IOException {
        File file = new File("F:\\Music\\我想 - Carol 琼.mp3");
        String name = file.getName();
        int i = name.indexOf("-");
        int end = name.indexOf(".mp3");
        List<Music> musicList = fingAllByNameAndSinger(name.substring(0, i), name.substring(i + 1, end));
        if (!musicList.isEmpty()) {
            log.error("已存在 {}", musicList);
            return;
        }
        Music music = new Music();
        String id = UUID.randomUUID().toString().replace("-", "");
        music.setMusicId(id);
        music.setName(name.substring(0, i).trim());
        music.setSinger(name.substring(i + 1, end).trim());
        music.setLyricId(id);
        music.setType(MusicType.MP3.getType());

        int copy = FileCopyUtils.copy(file, new File("F:\\music_yunshu\\" + id));
        if (copy <= 0) {
            log.warn("File Copy 0 File: {} Id {}", file, id);
        }

        musicRepository.save(music);
    }

    @Test
    void format() {
        musicRepository.findAll().parallelStream()
                .peek(music -> {
                    music.setSinger(music.getSinger().trim());
                    music.setName(music.getName().trim());
                })
                .forEach(music -> musicRepository.save(music));
    }

    private List<Music> fingAllByNameAndSinger(String name, String singer) {
        Music music = new Music();
        music.setName(name);
        music.setSinger(singer);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("type", "id");
        Example<Music> example = Example.of(music, matcher);

        return musicRepository.findAll(example);
    }

    @Test
    void testGetMusicTypeFromPath() {
        Optional<MusicType> musicTypeFromFilePath = MusicType.getMusicTypeFromFilePath("c://a.mp3");
        System.out.println(musicTypeFromFilePath.isPresent());
    }

    @Test
    void testDatabaseNoHave() throws Exception {
        StringBuilder sb = new StringBuilder();
        Files.lines(Paths.get("C:\\Users\\wangn\\Desktop\\b.txt"), StandardCharsets.UTF_8)
                .map(item -> {
                    int i = item.indexOf("-");
                    Music music = new Music();
                    music.setName(item.substring(0, i));
                    music.setSinger(item.substring(i + 1));
                    return music;
                })
                .filter(music -> !musicRepository.existsByName(music.getName()))
                .forEach(music -> sb.append(music.getName()).append("-").append(music.getSinger()).append("\n"));
        FileWriter fileWriter = new FileWriter("C:\\Users\\wangn\\Desktop\\data.txt");
        fileWriter.write(sb.toString());
        fileWriter.flush();
        fileWriter.close();

    }

    @Transactional(rollbackFor = Exception.class)
    @Rollback(false)
    @Test
    void testDelMusic() {
        String musicId = "996906c6c1944531aefafd32b96f85d0";
        musicRepository.deleteByMusicId(musicId);
        File file = new File("F:\\music_yunshu\\" + musicId);
        boolean delete = file.delete();
        System.out.println(delete);
    }

    @Test
    void testNoHaveLyric() throws Exception {
//        File musicDir = new File("F:\\f\\music_yunshu");
//        File lyricDir = new File("F:\\f\\lyric_yunshu");
//        Set<String> music = Arrays.stream(musicDir.listFiles()).map(File::getName).collect(Collectors.toSet());
//        Set<String> lyric = Arrays.stream(lyricDir.listFiles()).map(File::getName).collect(Collectors.toSet());
//        music.removeAll(lyric);
//        System.out.println(music.size());
//        List<Test2.Need> needs = music.stream()
//                .map(id -> musicRepository.findByMusicId(id))
//                .filter(Optional::isPresent)
//                .map(Optional::get)
//                .map(musicItem -> new Test2.Need(musicItem.getMusicId(), musicItem.getName(), musicItem.getSinger()))
//                .collect(Collectors.toList());
//        needs.forEach(System.out::println);
//        // Test2.start(needs);
    }

    @Test
    void testJsoup() {
        System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));
    }

    @Test
    void moveMusicAndLrc() throws IOException {
        List<Music> musics = musicRepository.findAll();
        File musicDir = new File("H:\\music_yunshu");
        File lyricDir = new File("H:\\lyric_yunshu");
        for (Music music : musics) {
            String musicId = music.getMusicId();
            String lyricId = music.getLyricId();
            String musicExtensionName = MusicType.getMediaTypeEnum(music.getType()).map(it -> {
                switch (it) {
                    case AAC:
                        return "aac";
                    case MP3:
                        return "mp3";
                    case WAV:
                        return "wav";
                    case FLAC:
                        return "flac";
                    default:
                        return "";
                }
            }).orElse("");
            if ("".equals(musicExtensionName)) {
                log.error("音乐：{} {} {} 扩展名匹配失败", music.getMusicId(), music.getName(), music.getLyricId());
                continue;
            }
            try {
                Files.copy(Paths.get(musicDir + File.separator + musicId), Path.of("E:\\music_yunshuV2", music.getName() + "-" + music.getSinger() + "." + musicExtensionName));
            } catch (NoSuchFileException e) {
                log.error("没找到文件", e);
            }
            try {
                Files.copy(Paths.get(lyricDir + File.separator + lyricId), Path.of("E:\\music_yunshuV2", music.getName() + "-" + music.getSinger() + ".lrc"));
            } catch (NoSuchFileException e) {
                log.error("没找到文件", e);
            }
        }
    }

    @Test
    void getCha() throws IOException {
        File musicDir = new File("E:\\music_yunshuNo");
        File lyricDir = new File("H:\\lyric_yunshu");
        for (File file : musicDir.listFiles()) {
            File lyricFile = new File(lyricDir + File.separator + file.getName());
            if (lyricFile.exists()) {
                Files.copy(Paths.get(lyricFile.toURI()), Path.of("E:\\music_yunshuNo", file.getName() + ".lrc"));
            } else {
                log.error("歌词不存在:{}", file.getName());
            }
        }
    }
}
