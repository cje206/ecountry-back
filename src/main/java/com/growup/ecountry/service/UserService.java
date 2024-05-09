package com.growup.ecountry.service;

import com.growup.ecountry.dto.UserDTO;
import com.growup.ecountry.entity.Users;
import com.growup.ecountry.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Users create(UserDTO userDTO) {
        Users user = Users.builder()
                .name(userDTO.getName())
                .userId(userDTO.getUserId())
                .pw(userDTO.getPw()).build();
        return  userRepository.save(user);
    }

    public UserDTO findByUserIdAndPw(String userId, String pw) {
        Users user = userRepository.findByUserIdAndPw(userId, pw);
        return UserDTO.builder().id(user.getId()).build();
    }
}
