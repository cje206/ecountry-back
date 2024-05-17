package com.growup.ecountry.service;

import lombok.Builder;
import lombok.Data;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
public class SchoolService {

    public List<SchoolInfo> schoolInfoSearchAPI(String schoolName){

        String url = "https://open.neis.go.kr/hub/schoolInfo";



        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("Key","f48ad57421754222bb1c099c1f296488")
                .queryParam("Type","JSON")
                .queryParam("SCHUL_NM",schoolName).build().toUri();

        RestClient restClient = RestClient.create();

        List<SchoolInfo> schoolInfos = new ArrayList<>();
        try{

            JSONParser parser = new JSONParser();

            JSONObject object = (JSONObject) parser.parse(restClient.get().uri(uri).retrieve().body(String.class));
            JSONObject result = null ;
            JSONArray schoolInfo = null;
            if((JSONObject) object.get("RESULT") != null){
                result = (JSONObject) object.get("RESULT");
                System.out.println(result);

            } else {
                schoolInfo = (JSONArray) object.get("schoolInfo");
                JSONObject head = (JSONObject)schoolInfo.get(0);
                JSONObject row = (JSONObject)schoolInfo.get(1);
                JSONArray schoolDatas = (JSONArray) row.get("row");

                for(int i = 0; i < schoolDatas.size(); i++){
                    JSONObject data = (JSONObject) schoolDatas.get(i);
                    SchoolInfo getInfo = SchoolInfo.builder().eduOfficeCode(data.get("ATPT_OFCDC_SC_CODE").toString()).schoolCode(data.get("SD_SCHUL_CODE").toString()).schoolName(data.get("SCHUL_NM").toString()).address(data.get("ORG_RDNMA").toString()).build();
                    System.out.println(getInfo.toString());
                    schoolInfos.add(getInfo);
                }
            }

        }catch (Exception e){
            System.out.println("오류 : " + e.getMessage());
        }
        return schoolInfos;
    }

    public void JsonToSchoolInfos(String schoolInfos) throws ParseException {

    }

    @Data
    @Builder
    public static class SchoolInfo{
        private String eduOfficeCode;
        private String schoolCode;
        private String schoolName;
        private String address;
    }
}
