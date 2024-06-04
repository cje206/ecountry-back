package com.growup.ecountry.service;

import lombok.RequiredArgsConstructor;
import org.apache.poi.xssf.model.StylesTable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.file.attribute.AclEntryType;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class ChatbotService {

    @Value("${CHAT_BOT_SERVER}")
    String chatbotServer;

    //챗봇에 직업, 과태료, 세금 등록
    public void insertChatbot(String data, String type){
        try{
            String url = chatbotServer + "/api/dialogflow/textQuery";

            JSONParser parser = new JSONParser();
            JSONObject insertData = new JSONObject();

            insertData.put("text", type+ "등록 "+data);
            RestClient restClient = RestClient.create();

            System.out.println("insertData : "+insertData );

            String response = restClient.post()
                    .uri(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(insertData)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });

            System.out.println("body : "+ response);
        }catch (Exception e){
            System.out.println("챗봇에 " + type + " 등록 오류 : "+ e + "\n" +  e.getMessage());
        }
    }
    //챗봇에 쿼리
    public String talkWithChatbot(String data){
        try{
            String url = chatbotServer + "/api/dialogflow/textQuery";

            JSONParser parser = new JSONParser();
            JSONObject textData = new JSONObject();

            textData.put("text", data);
            RestClient restClient = RestClient.create();

            System.out.println("insertData : "+textData );

            String response = restClient.post()
                    .uri(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(textData)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });

            System.out.println("body : "+ response);

            return response;

        }catch (Exception e){
            System.out.println("챗봇과 대화 오류 : "+ e + "\n" +  e.getMessage());
            return e + "\n" +e.getMessage();
        }
    }
    public String checkChatbotConnection(){
        try{
            String url = chatbotServer + "/hello";
            URI uri = UriComponentsBuilder.fromHttpUrl(url).build().toUri();
            JSONParser parser = new JSONParser();
            RestClient restClient = RestClient.create();
            String response = restClient.get().uri(uri).retrieve().body(String.class);
            System.out.println(response);
            return response;
        } catch (Exception e){
            System.out.println("챗봇 확인 오류 : " + e + "\n" + e.getMessage());
            return e + "\n" + e.getMessage();
        }
    }
}
