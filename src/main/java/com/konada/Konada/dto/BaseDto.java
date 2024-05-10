package com.konada.Konada.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
public class BaseDto implements Serializable {
    private String          rmk;
    private String          regrId;
    private LocalDateTime   regDttm;
    private String          modrId;
    private LocalDateTime   modDttm;
}
