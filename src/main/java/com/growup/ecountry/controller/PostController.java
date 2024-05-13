package com.growup.ecountry.controller;

import com.growup.ecountry.dto.ApiResponseDTO;
import com.growup.ecountry.dto.NewsDTO;
import com.growup.ecountry.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.lang.model.type.NullType;
import java.util.List;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {
    private final NewsService newsService;

    //뉴스 전체 조회
    @GetMapping("/articles/{countryId}")
    public ResponseEntity<ApiResponseDTO<List<NewsDTO>>> findAllNews(@PathVariable Long countryId){

        return ResponseEntity.ok(new ApiResponseDTO<List<NewsDTO>>(true, "뉴스 전체 조회에 성공하였습니다.",newsService.findAllNews(countryId ));
    }

    //뉴스 1개 조회
    @GetMapping("/article/{id}")
    public ResponseEntity<ApiResponseDTO<NewsDTO>> findNews(@PathVariable Long id){

        return ResponseEntity.ok(new ApiResponseDTO<NewsDTO>(true, "뉴스 1개 조회에 성공하였습니다.",newsService.findNews(id) ));
    }

    //뉴스 1개 추가

    //뉴스 1개 삭제
    @DeleteMapping("/article/{id}")
    public ResponseEntity<ApiResponseDTO<NullType>> deleteNews(@PathVariable Long id){
        boolean success = newsService.deleteNews(id);
        String msg = success ? "뉴스 1개 삭제에 성공하였습니다." : "뉴스 1개 삭제에 실패하였습니다.";

        return ResponseEntity.ok(new ApiResponseDTO<NullType>(success, msg));
    }

    //뉴스 1개 수정
    @PatchMapping("/article")
    public ResponseEntity<ApiResponseDTO<NullType>> updateNews(@RequestBody NewsDTO newsDTO){
        boolean success = newsService.updateNews(newsDTO);
        String msg = success ? "뉴스 1개 수정에 성공하였습니다." : "뉴스 1개 수정에 실패하였습니다.";

        return ResponseEntity.ok(new ApiResponseDTO<NullType>(success, msg));
    }


}
