package com.growup.ecountry.service;

import com.growup.ecountry.dto.ResponseDTO;
import com.growup.ecountry.dto.UserDTO;
import com.growup.ecountry.entity.Users;
import com.growup.ecountry.repository.UserRepository;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;

    public Users create(UserDTO userDTO) {
//        String encryptionPassword = passwordEncoder.encode(userDTO.getPw());
        Users user = Users.builder()
                .name(userDTO.getName())
                .userId(userDTO.getUserId())
                .pw(userDTO.getPw()).build();
        return  userRepository.save(user);
    }

    //UserDTO 타입 → ResponseDTO 타입
    public ResponseDTO findByUserIdAndPw(UserDTO userDTO) {
        Optional<Users> userExist = userRepository.findByUserIdAndPw(userDTO.getUserId(), userDTO.getPw());
        if(userExist.isPresent()){
            return new ResponseDTO(true,"로그인 성공");
        }
        else {
            return new ResponseDTO(false,"아이디 혹은 비밀번호를 잘못 입력하셨습니다");
        }
    }

    public ResponseDTO imgUpdateService(UserDTO userDTO){
        Optional<Users> userExist = userRepository.findById(userDTO.getId());
        if(userExist.isPresent()){
            return new ResponseDTO(true,"이미지 변경 성공");
        }
        else {
            return new ResponseDTO(false,"이미지 변경 실패");
        }
    }
}
/* 
회원가입 기능: Spring Security 써서 비밀번호 암호화하여 DB에 저장
로그인 기능: 비밀번호 암호화 된 값과 비교
 */