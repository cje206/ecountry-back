package com.growup.ecountry.service;

import com.growup.ecountry.dto.NewsDTO;
import com.growup.ecountry.entity.News;
import com.growup.ecountry.entity.Users;
import com.growup.ecountry.repository.NewsRepository;
import com.growup.ecountry.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService {
    private final NewsRepository newsRepository;
    private final UserRepository userRepository;

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
                Users writer = userRepository.findById(news.getWriterId()).orElseThrow();
                newsDTO.setWriterName(writer.getName());
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
            Users writer = userRepository.findById(news.getWriterId()).orElseThrow();
            newsDTO.setWriterName(writer.getName());
            return newsDTO;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    //뉴스 1개 추가

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

}
