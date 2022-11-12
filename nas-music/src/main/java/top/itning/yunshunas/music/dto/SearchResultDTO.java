package top.itning.yunshunas.music.dto;

import lombok.Data;

import java.io.Serializable;
import java.net.URI;
import java.util.List;

/**
 * @author itning
 * @since 2022/11/8 22:23
 */
@Data
public class SearchResultDTO implements Serializable {
    private String musicId;

    private String name;

    private String singer;

    private String lyricId;

    private Integer type;

    private URI musicUri;

    private URI lyricUri;

    private URI coverUri;

    private List<String> highlightFields;
}
