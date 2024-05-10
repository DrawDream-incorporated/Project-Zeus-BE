package com.konada.Konada.dto;

import java.time.LocalDateTime;

public class PostingDto extends BaseDto{

    private Integer status;
    private String error;
    private Integer postId;
    private String user_id;
    private String topic;
    private String title;
    private String content;
    private String tags;
    private Integer thumbsUp;
    private Integer thumbsDown;
    private LocalDateTime createdTime;
    private Long views;


}
