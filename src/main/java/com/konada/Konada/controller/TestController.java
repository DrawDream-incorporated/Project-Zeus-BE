package com.konada.Konada.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController // Controller 차이
@RequiredArgsConstructor
@RequestMapping("/api")
public class TestController {
        @GetMapping("/test")
        public String hello() {
            log.info("Received request for /test endpoint");
            return "Hello, World!";
        }

        @GetMapping("/name")
        public String blogGet(@RequestParam String name, @RequestParam String id){
            return name + "-" + id;
        }

}
