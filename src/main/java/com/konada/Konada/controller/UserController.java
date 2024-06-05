package com.konada.Konada.controller;

import com.konada.Konada.entity.User;
import com.konada.Konada.response.ApiResponse;
import com.konada.Konada.response.ErrorApiResponse;
import com.konada.Konada.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            return ResponseEntity.ok(new ApiResponse<>(true, HttpStatus.OK.value(), "-", "No Error", users));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorApiResponse(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "-", "INTERNAL SERVER ERROR"));
        }
    }


}