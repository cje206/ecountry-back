package com.growup.ecountry.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.growup.ecountry.controller.JobController;
import com.growup.ecountry.dto.JobDTO;
import com.growup.ecountry.dto.JobDetailDTO;
import com.growup.ecountry.entity.JobDetails;
import com.growup.ecountry.entity.Jobs;
import com.growup.ecountry.repository.JobDetailRepository;
import com.growup.ecountry.repository.JobRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobService {
    private final JobRepository jobRepository;
    private final JobDetailRepository jobDetailRepository;


    //직업리스트 등록
    public Boolean addJob(List<JobController.JobRequestData> jobRequestDataList){
        try{
           for(JobController.JobRequestData jobRequestData:jobRequestDataList){
               Jobs job = Jobs.builder().name(jobRequestData.getName()).roll(jobRequestData.getRoll()).standard(jobRequestData.getStandard())
                       .salary(jobRequestData.getSalary()).limited(jobRequestData.getLimited()).countryId(jobRequestData.getCountryId())
                       .build();
               Jobs newJob = jobRepository.save(job);

               for(int i = 0; i< jobRequestData.getSkills().length ; i ++){
                    JobDetails jobDetails = JobDetails.builder()
                            .jobId(newJob.getId())
                            .skill(jobRequestData.getSkills()[i])
                            .build();
                    jobDetailRepository.save(jobDetails);
               }
           }

        }catch (Exception e){
            System.out.println(e.getMessage());
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
                        JobResponseData jobData = new JobResponseData(job.getId(), job.getName(), job.getRoll(), job.getStandard(), job.getSalary(), job.getLimited());
                        jobDetailRepository.findByJobId(job.getId()).forEach(jobDetail -> jobData.setJobSkills(jobDetail.getId(), jobDetail.getSkill()));
                        jobResponseDataList.add(jobData);
                    }
            );
        } catch (Exception e){
            System.out.println("error : " + e.getMessage());
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
    //직업 부가기능 1개 삭제
    public Boolean deleteJobDetail(Long id){
        try{
            jobDetailRepository.deleteById(id);
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
                jobRepository.save(job);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    //직업부가기능 수정
    public Boolean updateJobDetail(JobDetailDTO jobDetailDTO){
        try{
            JobDetails jobDetail = jobDetailRepository.findById(jobDetailDTO.getId()).orElseThrow();
            jobDetail.setSkill(jobDetailDTO.getSkill());
            jobDetailRepository.save(jobDetail);
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
        private List<JobSkill> jobSkills = new ArrayList<>();

        public JobResponseData(Long id, String name, String roll, String standard, Integer salary, Integer limited) {
            this.id = id;
            this.name = name;
            this.roll = roll;
            this.standard = standard;
            this.salary = salary;
            this.limited = limited;
        }
        public void setJobSkills(Long id, Integer skill){
            this.jobSkills.add(new JobSkill(id, skill));
        }


    }
    public static class JobSkill{
        @JsonProperty
        private Long skillId;
        @JsonProperty
        private Integer skill;

        public JobSkill(Long id, Integer skill) {
            this.skillId = id;
            this.skill = skill;
        }
    }

}
