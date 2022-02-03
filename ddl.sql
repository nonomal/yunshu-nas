CREATE TABLE `yunshu_music`.`music`
(
    `id`           bigint       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `music_id`     varchar(255) NOT NULL COMMENT '歌曲ID',
    `lyric_id`     varchar(255)          DEFAULT NULL COMMENT '歌词ID',
    `name`         varchar(255) NOT NULL COMMENT '歌曲名',
    `singer`       varchar(255) NOT NULL COMMENT '歌手名',
    `type`         int          NOT NULL COMMENT '音乐类型',
    `gmt_create`   datetime(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
    `gmt_modified` datetime(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_music_id` (`music_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='音乐表';