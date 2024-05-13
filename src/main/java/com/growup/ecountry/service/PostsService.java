package com.growup.ecountry.service;

import com.growup.ecountry.dto.NewsDTO;
import com.growup.ecountry.dto.PetitionDTO;
import com.growup.ecountry.entity.News;
import com.growup.ecountry.entity.Petitions;
import com.growup.ecountry.entity.Students;
import com.growup.ecountry.entity.Users;
import com.growup.ecountry.repository.NewsRepository;
import com.growup.ecountry.repository.PetitionRepository;
import com.growup.ecountry.repository.StudentRepository;
import com.growup.ecountry.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostsService {
    private final NewsRepository newsRepository;
    private final PetitionRepository petitionRepository;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;

    //--------------뉴스--------------------------------------------------------
    //뉴스리스트 조회
    public List<NewsDTO> findAllNews(Long countryId){
        List<News> newsList = newsRepository.findByCountryId(countryId);
        List<NewsDTO> newsDTOList = new ArrayList<>();
        try {
            for(News news : newsList){
                NewsDTO newsDTO = NewsDTO.builder()
                        .id(news.getId())
                        .title(news.getTitle())
                        .content(news.getContent())
                        .createdAt(news.getCreatedAt())
                        .countryId(news.getCountryId())
                        .writerId(news.getWriterId())
                        .build();
                if(newsDTO.getWriterId() == null){
                    newsDTO.setWriterName("선생님");
                } else {
                    Students writer = studentRepository.findById(news.getWriterId()).orElseThrow();
                    newsDTO.setWriterName(writer.getName());
                }
                newsDTOList.add(newsDTO);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return newsDTOList;
    }

    //뉴스 1개 조회
    public NewsDTO findNews(Long id){
        try {
            News news = newsRepository.findById(id).orElseThrow();
            NewsDTO newsDTO = NewsDTO.builder()
                        .id(news.getId())
                        .title(news.getTitle())
                        .content(news.getContent())
                        .createdAt(news.getCreatedAt())
                        .countryId(news.getCountryId())
                        .writerId(news.getWriterId())
                        .build();
            if(newsDTO.getWriterId() == null){
                newsDTO.setWriterName("선생님");
            } else {
                Students writer = studentRepository.findById(news.getWriterId()).orElseThrow();
                newsDTO.setWriterName(writer.getName());
            }

            return newsDTO;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    //뉴스 1개 추가
    public Boolean addNews(NewsDTO newsDTO){
        try{

            News news = News.builder()
                    .title(newsDTO.getTitle())
                    .content(newsDTO.getContent())
                    .writerId(newsDTO.getWriterId())
                    .countryId(newsDTO.getCountryId())
                    .build();

            newsRepository.save(news);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    //뉴스 1개 삭제
    public Boolean deleteNews(Long id){
        try{
            newsRepository.deleteById(id);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    //뉴스 1개 수정
    public Boolean updateNews(NewsDTO newsDTO){
        try{
            News news = newsRepository.findById(newsDTO.getId()).orElseThrow();
            news.setTitle(newsDTO.getTitle());
            news.setContent(newsDTO.getContent());
            newsRepository.save(news);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    //--------------신문고--------------------------------------------------------
    //신문고 리스트 조회
    public List<PetitionDTO> findAllPetitions(Long countryId){
        List<Petitions> petitionsList = petitionRepository.findByCountryId(countryId);
        List<PetitionDTO> petitionDTOList = new ArrayList<>();
        try {
            for(Petitions petitions : petitionsList){
                PetitionDTO petitionDTO = PetitionDTO.builder()
                        .id(petitions.getId())
                        .title(petitions.getTitle())
                        .content(petitions.getContent())
                        .createdAt(petitions.getCreatedAt())
                        .countryId(petitions.getCountryId())
                        .writerId(petitions.getWriterId())
                        .isSecret(petitions.getIsSecret())
                        .build();
                if(petitionDTO.getWriterId() == null){
                    petitionDTO.setWriterName("선생님");
                } else {
                    Students writer = studentRepository.findById(petitions.getWriterId()).orElseThrow();
                    petitionDTO.setWriterName(writer.getName());
                }
                petitionDTOList.add(petitionDTO);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return petitionDTOList;
    }
    //신문고 1개 조회
    public PetitionDTO findPetition(Long id){
        try {
            Petitions petition = petitionRepository.findById(id).orElseThrow();
            PetitionDTO petitionDTO = PetitionDTO.builder()
                    .id(petition.getId())
                    .title(petition.getTitle())
                    .content(petition.getContent())
                    .createdAt(petition.getCreatedAt())
                    .countryId(petition.getCountryId())
                    .writerId(petition.getWriterId())
                    .isSecret(petition.getIsSecret())
                    .build();
            if(petitionDTO.getWriterId() == null){
                petitionDTO.setWriterName("선생님");
            } else {
                Students writer = studentRepository.findById(petition.getWriterId()).orElseThrow();
                petitionDTO.setWriterName(writer.getName());
            }

            return petitionDTO;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    //신문고 1개 추가
    public Boolean addPetition(PetitionDTO petitionDTO){
        try{
            Petitions petition = Petitions.builder()
                    .title(petitionDTO.getTitle())
                    .content(petitionDTO.getContent())
                    .writerId(petitionDTO.getWriterId())
                    .countryId(petitionDTO.getCountryId())
                    .isSecret(petitionDTO.getIsSecret())
                    .build();

            petitionRepository.save(petition);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    //신문고 1개 삭제
    public Boolean deletePetition(Long id){
        try{
            petitionRepository.deleteById(id);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    //신문고 1개 수정
    public Boolean updatePetition(PetitionDTO petitionDTO){
        try{
            Petitions petitions = petitionRepository.findById(petitionDTO.getId()).orElseThrow();
            petitions.setTitle(petitionDTO.getTitle());
            petitions.setContent(petitionDTO.getContent());
            petitionRepository.save(petitions);

        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
