package com.growup.ecountry.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.growup.ecountry.config.TokenProvider;
import com.growup.ecountry.dto.ApiResponseDTO;
import com.growup.ecountry.dto.CountryDTO;
import com.growup.ecountry.dto.UserDTO;
import com.growup.ecountry.entity.Users;
import com.growup.ecountry.service.UserService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.lang.model.type.NullType;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final TokenProvider jwt;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDTO<NullType>> signup(@RequestBody UserDTO userDTO) {
        Boolean result = userService.create(userDTO);
        String msg = result ? "회원가입에 성공하셨습니다" : "이미 존재하는 회원입니다";
        return ResponseEntity.ok(new ApiResponseDTO<>(result,msg,null));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO<String>> login(@RequestBody UserDTO userDTO) {
        ApiResponseDTO<NullType> result = userService.login(userDTO);
        Token token = result.getSuccess() ? new Token(jwt.generateToken(userDTO.getUserId()))
                                          : new Token(null);
        return ResponseEntity.ok(new ApiResponseDTO<>(result.getSuccess(), result.getMessage(), token.getToken()));
    }

    @PatchMapping("/change")
    public ResponseEntity<ApiResponseDTO<NullType>> pwUpdate(@RequestHeader(value= "Authorization") String token, @RequestBody UserDTO userDTO) {
        String authToken = jwt.validateToken(token);
        if(authToken != "false"){
            Boolean result = userService.pwUpdate(authToken,userDTO.getPw());
            String msg = result ? "비밀번호를 성공적으로 변경하였습니다" : "비밀번호 변경에 실패하였습니다";
            return ResponseEntity.ok(new ApiResponseDTO<>(result,msg,null));
        }
        return ResponseEntity.ok(new ApiResponseDTO<>(false,"비밀번호 변경에 실패하였습니다",null));
    }


    //이미지 변경
    @PatchMapping
    public ResponseEntity<ApiResponseDTO<NullType>> imgUpdate(@RequestBody UserDTO userDTO){
        Boolean result = userService.imgUpdate(userDTO);
        String msg = result ? "이미지 변경에 성공하였습니다" : "이미지 변경에 실패하였습니다";
        return ResponseEntity.ok(new ApiResponseDTO<>(result,msg,null));
    }

    //국가리스트조회
    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<CountryDTO>>> findCountryList(@RequestHeader(value = "Authorization") String token){
        return ResponseEntity.ok(userService.findCountryList(token));
    }

    static class Token{
        @JsonProperty
        private final String token;
        public Token(String token){
            this.token = token;
        }
        public String getToken(){
            return this.token;
        }
    }
}

