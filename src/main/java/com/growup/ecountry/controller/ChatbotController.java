package com.growup.ecountry.controller;

import com.growup.ecountry.dto.ApiResponseDTO;
import com.growup.ecountry.service.ChatbotService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chatbot")
@RequiredArgsConstructor
public class ChatbotController {
    private final ChatbotService chatbotService;

    @PostMapping
    public ResponseEntity<ApiResponseDTO<String>> talkWithChatbot(@RequestBody String text){
        try{
            return ResponseEntity.ok(new ApiResponseDTO<>(true, "대화에 성공하였습니다. ", chatbotService.talkWithChatbot(text)));
        }catch (Exception e){
            return ResponseEntity.ok(new ApiResponseDTO<>(false, "대화에 실패하였습니다. "));
        }

    }
    @GetMapping("/check")
    public ResponseEntity<ApiResponseDTO<String>> checkChatbot(){
        return ResponseEntity.ok(new ApiResponseDTO<>(true, "hi", chatbotService.checkChatbotConnection()));
    }

}
