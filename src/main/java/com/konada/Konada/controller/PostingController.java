package com.konada.Konada.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController // Controller 차이
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostingController {
        @GetMapping("/test")
        public String hello() {
            log.info("test");
            return "Hello, World! This is Test! Really? Test";
        }
}
