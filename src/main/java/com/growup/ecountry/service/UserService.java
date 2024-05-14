package com.growup.ecountry.service;

import com.growup.ecountry.config.TokenProvider;
import com.growup.ecountry.dto.ApiResponseDTO;
import com.growup.ecountry.dto.CountryDTO;
import com.growup.ecountry.dto.StudentDTO;
import com.growup.ecountry.dto.UserDTO;
import com.growup.ecountry.entity.Countries;
import com.growup.ecountry.entity.Students;
import com.growup.ecountry.entity.Users;
import com.growup.ecountry.repository.CountryRepository;
import com.growup.ecountry.repository.JobRepository;
import com.growup.ecountry.repository.StudentRepository;
import com.growup.ecountry.repository.UserRepository;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.lang.model.type.NullType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final CountryRepository countryRepository;
    private final StudentRepository studentRepository;
    private final JobRepository jobRepository;
    private final TokenProvider jwt;
//    private final PasswordEncoder passwordEncoder;

    public Boolean create(UserDTO userDTO) {
//        String encryptionPassword = passwordEncoder.encode(userDTO.getPw());
        Optional<Users> userExist = userRepository.findByUserId(userDTO.getUserId());
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

    public ApiResponseDTO<Long> login(UserDTO userDTO) {
        Optional<Users> userIdExist = userRepository.findByUserId(userDTO.getUserId());
        if(userIdExist.isPresent()){
            Optional<Users> userPwExist = userRepository.findByPw(userDTO.getPw());
            if(userPwExist.isPresent()){
                return new ApiResponseDTO<>(true,"로그인 성공",userIdExist.get().getId());
            }
            else {
                return new ApiResponseDTO<>(false,"비밀번호를 잘못 입력하셨습니다",null);
            }
        }
        else {
            return new ApiResponseDTO<>(false,"아이디를 잘못 입력하셨습니다",null);
        }
    }
    //선생님/학생 정보 조회
    public ApiResponseDTO<?> userInfo(Long id,Boolean isStudent) {
        if(isStudent == false){
            Optional<Users> userExist = userRepository.findById(id);
            if (userExist.isPresent()) {
                Users user = userExist.get();
                UserDTO userDTO = UserDTO.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .userId(user.getUserId())
                        .img(user.getImg())
                        .build();
                return new ApiResponseDTO<>(true, "회원 정보 조회", userDTO);
            }
            else {
                return new ApiResponseDTO<>(false, "존재하지 않는 회원입니다", null);
            }
        }
        else {
            Optional<Students> studentEXIST = studentRepository.findById(id);
            if(studentEXIST.isPresent()){
                Students student = studentEXIST.get();
                StudentDTO studentDTO = StudentDTO.builder()
                        .id(student.getId())
                        .name(student.getName())
                        .rollNumber(student.getRollNumber())
                        .rating(student.getRating())
                        .img(student.getImg())
                        .jobId(student.getJobId()).build();
                return new ApiResponseDTO<>(true, "학생 정보 조회", studentDTO);
            }
            else {
                return new ApiResponseDTO<>(false, "존재하지 않는 학생입니다", null);
            }
        }

    }
    public Boolean pwUpdate(Long id, String pw){
        Optional<Users> userExist = userRepository.findById(id);
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
    //국가리스트 조회
    public ApiResponseDTO<List<CountryDTO>> findCountryList(Long id){
            Optional<Users> userExist =  userRepository.findById(id);
            List<CountryDTO> userCountryDTOList = new ArrayList<>();
            if(userExist.isPresent()){
                Users user = userExist.get();
                List<Countries> countries = countryRepository.findAllByUsers_Id(user.getId());
                for(Countries country : countries){
                    CountryDTO countryDTO =
                            CountryDTO.builder()
                                    .id(country.getId())
                                    .school(country.getSchool())
                                    .name(country.getName())
                                    .grade(country.getGrade())
                                    .classroom(country.getClassroom())
                                    .unit(country.getUnit())
                                    .treasury(country.getTreasury())
                                    .salaryDate(country.getSalaryDate()).build();
                    userCountryDTOList.add(countryDTO);
                }
                return new ApiResponseDTO<>(true,"국가목록 조회 성공",userCountryDTOList);
            }
            else {
                return new ApiResponseDTO<>(false,"유저 데이터를 찾을 수 없습니다",null);
        }
    }
}
/* 
회원가입 기능: Spring Security 써서 비밀번호 암호화하여 DB에 저장
로그인 기능: 비밀번호 암호화 된 값과 비교
 */