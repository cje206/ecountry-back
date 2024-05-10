package com.growup.ecountry.service;

import com.growup.ecountry.config.TokenProvider;
import com.growup.ecountry.dto.ApiResponseDTO;
import com.growup.ecountry.dto.UserDTO;
import com.growup.ecountry.entity.Users;
import com.growup.ecountry.repository.UserRepository;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    private final TokenProvider jwt;

//    private final PasswordEncoder passwordEncoder;
    public Users create(UserDTO userDTO) {
//        String encryptionPassword = passwordEncoder.encode(userDTO.getPw());
        Users user = Users.builder()
                .name(userDTO.getName())
                .userId(userDTO.getUserId())
                .pw(userDTO.getPw()).build();
        return  userRepository.save(user);
    }

    public ApiResponseDTO<String> findByUserIdAndPw(UserDTO userDTO) {
        Optional<Users> userExist = userRepository.findByUserIdAndPw(userDTO.getUserId(), userDTO.getPw());
        if(userExist.isPresent()){
            String token = jwt.generateToken(userDTO.getUserId());
            return new ApiResponseDTO<>(true,"로그인 성공",token );
        }
        else {
            return new ApiResponseDTO<>(false,"아이디 혹은 비밀번호를 잘못 입력하셨습니다","");
        }
    }

    public ApiResponseDTO<NullPointerException> imgUpdateService(UserDTO userDTO){
        Optional<Users> userExist = userRepository.findById(userDTO.getId());
        if(userExist.isPresent()){
            return new ApiResponseDTO(true,"이미지 변경 성공",null);
        }
        else {
            return new ApiResponseDTO(false,"이미지 변경 실패",null);
        }
    }
}
/* 
회원가입 기능: Spring Security 써서 비밀번호 암호화하여 DB에 저장
로그인 기능: 비밀번호 암호화 된 값과 비교
 */