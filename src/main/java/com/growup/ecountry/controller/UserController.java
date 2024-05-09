package com.growup.ecountry.controller;

import com.growup.ecountry.dto.UserDTO;
import com.growup.ecountry.entity.Users;
import com.growup.ecountry.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Users> signup(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.create(userDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody String userId, @RequestBody String pw) {
        return ResponseEntity.ok(userService.findByUserIdAndPw(userId, pw));
    }
}
