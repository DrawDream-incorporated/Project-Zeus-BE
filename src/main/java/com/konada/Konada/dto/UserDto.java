package com.konada.Konada.dto;

import com.konada.Konada.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String preferredName;
    private String email;

    public static User toUser(UserDto userDto) {
        return User.builder()
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .preferredName(userDto.getPreferredName())
                .email(userDto.getEmail())
                .build();
    }
}
