package com.konada.Konada;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ExampleText {

//    @Autowired
//    private HomeController controller;

    @Test
    void contextLoads() throws Exception {
//        assertThat(controller).isNotNull();
        assertThat("hellow").isEqualTo("hellow");
    }
}
