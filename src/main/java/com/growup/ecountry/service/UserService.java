package com.growup.ecountry.service;

import com.growup.ecountry.config.TokenProvider;
import com.growup.ecountry.dto.ApiResponseDTO;
import com.growup.ecountry.dto.UserDTO;
import com.growup.ecountry.entity.Users;
import com.growup.ecountry.repository.UserRepository;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.lang.model.type.NullType;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final TokenProvider jwt;
//    private final PasswordEncoder passwordEncoder;

    public Boolean create(UserDTO userDTO) {
//        String encryptionPassword = passwordEncoder.encode(userDTO.getPw());
        Optional<Users> userExist = userRepository.findByName(userDTO.getName());
        if(userExist.isPresent()){
            return false;
        }
        else {
            Users user = Users.builder()
                    .name(userDTO.getName())
                    .userId(userDTO.getUserId())
                    .pw(userDTO.getPw()).build();
            userRepository.save(user);
            return true;
        }
    }

    public ApiResponseDTO<NullType> login(UserDTO userDTO) {
        Optional<Users> userIdExist = userRepository.findByUserId(userDTO.getUserId());
        if(userIdExist.isPresent()){
            Optional<Users> userPwExist = userRepository.findByPw(userDTO.getPw());
            if(userPwExist.isPresent()){
                return new ApiResponseDTO<>(true,"로그인 성공",null);
            }
            else {
                return new ApiResponseDTO<>(false,"비밀번호를 잘못 입력하셨습니다",null);
            }
        }
        else {
            return new ApiResponseDTO<>(false,"아이디를 잘못 입력하셨습니다",null);
        }
    }
    public Boolean pwUpdate(String userId, String pw){
        Optional<Users> userExist = userRepository.findByUserId(userId);
        if(userExist.isPresent()){
            Users user = userExist.get();
            user = Users.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .userId(user.getUserId())
                    .pw(pw).build(); // 비밀번호 변경
            userRepository.save(user);
            return true;
        }
        else {
            return false;
        }
    }

    public Boolean imgUpdate(UserDTO userDTO){
        Optional<Users> userExist = userRepository.findById(userDTO.getId());
        if(userExist.isPresent()){
            Users user = userExist.get();
            System.out.print(user);
            user = Users.builder()
                    .id(userDTO .getId())
                    .name(user.getName())
                    .userId(user.getUserId())
                    .pw(user.getPw())
                    .img(userDTO.getImg())
                    .build();
            userRepository.save(user);
            return true;
        }
        else {
            return false;
        }
    }
}
/* 
회원가입 기능: Spring Security 써서 비밀번호 암호화하여 DB에 저장
로그인 기능: 비밀번호 암호화 된 값과 비교
 */