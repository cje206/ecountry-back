package com.growup.ecountry.controller;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.growup.ecountry.dto.ApiResponseDTO;
import com.growup.ecountry.dto.JobDTO;
import com.growup.ecountry.dto.JobDetailDTO;
import com.growup.ecountry.repository.JobRepository;
import com.growup.ecountry.service.JobService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.lang.model.type.NullType;
import java.util.List;

@RestController
@RequestMapping("/api/job")
@RequiredArgsConstructor
public class JobController {
    public final JobService jobService;

    //직업리스트 등록
    @PostMapping
    public ResponseEntity<ApiResponseDTO<NullType>> addJobs(@RequestBody List<JobRequestData> jobRequestDataList){
        for(JobRequestData data : jobRequestDataList){
            System.out.println(data.getSalary());
        }
        boolean success = jobService.addJob(jobRequestDataList);
        String msg = success ? "직업리스트 등록에 성공하였습니다. " : "직업리스트 등록에 실패하였습니다.";

        return ResponseEntity.ok(new ApiResponseDTO<NullType>(success, msg, null));
    }

    //직업리스트 조회
    @GetMapping("/{countryId}")
    public ResponseEntity<ApiResponseDTO<List<JobService.JobResponseData>>> findAllJob(@PathVariable Long countryId){
        return ResponseEntity.ok(new ApiResponseDTO<List<JobService.JobResponseData>>(true, "직업조회에 성공하였습니다.",jobService.findAllJobs(countryId)));
    }

    //직업리스트 1개 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<NullType>> deleteJob(@PathVariable Long id){
        boolean success = jobService.deleteJob(id);
        String msg = success ? "직업리스트 1개 삭제에 성공하였습니다." : "직업리스트 1개 삭제에 실패하였습니다.";
        return ResponseEntity.ok(new ApiResponseDTO<NullType>(success,msg,null));

    }

    //직업부가기능 1개 삭제
    @DeleteMapping("/detail/{id}")
    public ResponseEntity<ApiResponseDTO<NullType>> deleteJobDetail(@PathVariable Long id) {
        boolean success = jobService.deleteJobDetail(id);
        String msg = success ? "직업부가기능 1개 삭제에 성공하였습니다." : "직업부가기능 1개 삭제에 실패하였습니다.";
        return ResponseEntity.ok(new ApiResponseDTO<NullType>(success, msg, null));
    }

    //직업리스트 1개 수정
    @PatchMapping
    public ResponseEntity<ApiResponseDTO<NullType>> updateJob(@RequestBody JobDTO jobDTO){
        boolean success = jobService.updateJob(jobDTO);
        String msg = success ? "직업리스트 1개 수정에 성공하였습니다." : "직업리스트 1개 수정에 실패하였습니다.";
        return ResponseEntity.ok(new ApiResponseDTO<NullType>(success, msg, null));
    }
    //직업부가기능 1개 수정
    @PatchMapping("/detail")
    public ResponseEntity<ApiResponseDTO<NullType>> updateJobDetail(@RequestBody JobDetailDTO jobDetailDTO){
        boolean success = jobService.updateJobDetail(jobDetailDTO);
        String msg = success ? "직업부가기능 1개 수정에 성공하였습니다." : "직업부가기능 1개 수정에 실패하였습니다.";
        return ResponseEntity.ok(new ApiResponseDTO<NullType>(success, msg, null));
    }



    @Getter
    @Setter
    public static class JobRequestData{

        private Long id;
        private String name;
        private String roll;
        private String standard;
        private Integer salary;
        private Integer limited;
        private Long countryId;
        private Integer[] skills;
    }


    }
