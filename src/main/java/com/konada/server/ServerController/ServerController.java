package com.konada.server.ServerController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ServerController {
  @GetMapping("/user/login") // localhost:8090/user/login
  public String loginDummyForm(){
    return "dummyLogin";
  }
}
