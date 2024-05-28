package com.konada.Konada.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorApiResponse {
    private boolean success;
    private int status;
    private String message;

}