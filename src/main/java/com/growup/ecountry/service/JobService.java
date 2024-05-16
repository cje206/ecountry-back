package com.growup.ecountry.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.growup.ecountry.controller.JobController;
import com.growup.ecountry.dto.JobDTO;
import com.growup.ecountry.entity.Jobs;
import com.growup.ecountry.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobService {
    private final JobRepository jobRepository;

    //직업리스트 등록
    public Boolean addJob(List<JobController.JobRequestData> jobRequestDataList){
        try{
           for(JobController.JobRequestData jobRequestData:jobRequestDataList){
               Jobs job = Jobs.builder().name(jobRequestData.getName()).roll(jobRequestData.getRoll()).standard(jobRequestData.getStandard())
                       .salary(jobRequestData.getSalary()).limited(jobRequestData.getLimited()).countryId(jobRequestData.getCountryId()).skills(jobRequestData.getSkills())
                       .build();
               Jobs newJob = jobRepository.save(job);
           }
        }catch (Exception e){
            System.out.println("직업리스트 등록 오류 : " + e.getMessage());
            return false;
        }
        return true;
    }

    //직업리스트 조회
    public List<JobResponseData> findAllJobs(Long countryId){// List<Object[]> jobsList = jobRepository.findByCountryId(countryId);
        List<JobResponseData> jobResponseDataList = new ArrayList<>();
        try {

            List<Jobs> jobs = jobRepository.findByCountryId(countryId);

            jobs.forEach(job ->
                    {
                        JobResponseData jobData = new JobResponseData(job.getId(), job.getName(), job.getRoll(), job.getStandard(), job.getSalary(), job.getLimited(),job.getSkills());
                        jobResponseDataList.add(jobData);
                    }
            );
        } catch (Exception e){
            System.out.println("직업리스트 조회 오류 :  " + e.getMessage());
        }

        return jobResponseDataList;
    }


    //직업리스트 1개 삭제
    public Boolean deleteJob(Long id){
        try{
            jobRepository.deleteById(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    //직업리스트 수정
    public Boolean updateJob(JobDTO jobDTO){
        try{
                Jobs job = jobRepository.findById(jobDTO.getId()).orElseThrow();
                job.setName(jobDTO.getName());
                job.setRoll(jobDTO.getRoll());
                job.setStandard(jobDTO.getStandard());
                job.setSalary(jobDTO.getSalary());
                job.setLimited(jobDTO.getLimited());
                job.setSkills(jobDTO.getSkills());
                jobRepository.save(job);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public static class JobResponseData{
        @JsonProperty
        private Long id;
        @JsonProperty
        private String name;
        @JsonProperty
        private String roll;
        @JsonProperty
        private String standard;
        @JsonProperty
        private Integer salary;
        @JsonProperty
        private Integer limited;
        @JsonProperty
        private Integer[] skills;

        public JobResponseData(Long id, String name, String roll, String standard, Integer salary, Integer limited, Integer[] skills) {
            this.id = id;
            this.name = name;
            this.roll = roll;
            this.standard = standard;
            this.salary = salary;
            this.limited = limited;
            this.skills = skills;
        }
    }
}
