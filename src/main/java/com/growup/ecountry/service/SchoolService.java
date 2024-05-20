package com.growup.ecountry.service;

import com.growup.ecountry.dto.CountryDTO;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Integer.parseInt;

@Service
@RequiredArgsConstructor
public class SchoolService {
    private final CountryService countryService;
    @Value("${NEIS_API_KEY}")
    String apiKey;
    //학교 검색
    public List<SchoolInfo> schoolInfoSearchAPI(String schoolName){

        List<SchoolInfo> schoolInfos = new ArrayList<>();
        try{
            String url = "https://open.neis.go.kr/hub/schoolInfo";

            URI uri = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("Key",apiKey)
                    .queryParam("Type","JSON")
                    .queryParam("SCHUL_NM",schoolName).build().toUri();

            RestClient restClient = RestClient.create();

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

                for (Object schoolData : schoolDatas) {
                    JSONObject data = (JSONObject) schoolData;
                    SchoolInfo getInfo = SchoolInfo.builder().eduOfficeCode(data.get("ATPT_OFCDC_SC_CODE").toString()).schoolCode(data.get("SD_SCHUL_CODE").toString()).schoolName(data.get("SCHUL_NM").toString()).address(data.get("ORG_RDNMA").toString()).build();
                    System.out.println(getInfo.toString());
                    schoolInfos.add(getInfo);
                }
            }

        }catch (Exception e){
            System.out.println("학교 검색 오류 : " + e.getMessage());
        }
        return schoolInfos;
    }

    //학교 급식 검색
    public List<LunchMenu> schoolMenuSearchAPI(CountryDTO countryDTO){
        List<LunchMenu> lunchMenus = new ArrayList<>();
        try {

            String url = "https://open.neis.go.kr/hub/mealServiceDietInfo";

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Calendar c1 = Calendar.getInstance();
            String today = sdf.format(c1.getTime());
            c1.add(Calendar.DATE,1);
            String tomorrow = sdf.format(c1.getTime());

            URI uri = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("Key",apiKey)
                    .queryParam("Type","JSON")
                    .queryParam("ATPT_OFCDC_SC_CODE",countryDTO.getEduOfficeCode())
                    .queryParam("SD_SCHUL_CODE",countryDTO.getSchoolCode())
                    .queryParam("MMEAL_SC_CODE",2)
                    .queryParam("MLSV_FROM_YMD", today)
                    .queryParam("MLSV_TO_YMD", tomorrow).build().toUri();

            RestClient restClient = RestClient.create();

            JSONParser parser = new JSONParser();

            JSONObject object = (JSONObject) parser.parse(restClient.get().uri(uri).retrieve().body(String.class));
            JSONObject result = null ;
            JSONArray mealServiceDietInfo = null;

            if((JSONObject) object.get("RESULT") != null){
                result = (JSONObject) object.get("RESULT");
                System.out.println(result);

            } else {
                mealServiceDietInfo = (JSONArray) object.get("mealServiceDietInfo");
                JSONObject head = (JSONObject)mealServiceDietInfo.get(0);
                JSONObject row = (JSONObject)mealServiceDietInfo.get(1);
                JSONArray LunchDatas = (JSONArray) row.get("row");

                for (Object lunchData : LunchDatas) {
                    JSONObject data = (JSONObject) lunchData;
                    LunchMenu getInfo = LunchMenu.builder().date(data.get("MLSV_YMD").toString()).Menu(data.get("DDISH_NM").toString()).build();
                    System.out.println(getInfo.toString());
                    lunchMenus.add(getInfo);
                }
            }
        }catch (Exception e){
            System.out.println("급식 검색 오류 : " + e.getMessage());
        }

        return lunchMenus;

    }
    //학교 시간표 검색
    public List<TimeTable> schoolTimeTableSearchApi(CountryDTO countryDTO){
        List<TimeTable> timeTables = new ArrayList<>();
        try {
            String url = "https://open.neis.go.kr/hub/elsTimetable";

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Calendar c1 = Calendar.getInstance();
            c1.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
            String monday = sdf.format(c1.getTime());
            c1.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
            String friday = sdf.format(c1.getTime());

            URI uri = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("Key",apiKey)
                    .queryParam("Type","JSON")
                    .queryParam("ATPT_OFCDC_SC_CODE",countryDTO.getEduOfficeCode())
                    .queryParam("SD_SCHUL_CODE",countryDTO.getSchoolCode())
                    .queryParam("GRADE", countryDTO.getGrade())
                    .queryParam("CLASS_NM", countryDTO.getClassroom())
                    .queryParam("TI_FROM_YMD", monday)
                    .queryParam("TI_TO_YMD", friday).build().toUri();

            RestClient restClient = RestClient.create();

            JSONParser parser = new JSONParser();

            JSONObject object = (JSONObject) parser.parse(restClient.get().uri(uri).retrieve().body(String.class));
            JSONObject result = null ;
            JSONArray elsTimetable = null;

            if((JSONObject) object.get("RESULT") != null){
                result = (JSONObject) object.get("RESULT");
                System.out.println(result);

            } else {
                elsTimetable = (JSONArray) object.get("elsTimetable");
                JSONObject head = (JSONObject)elsTimetable.get(0);
                JSONObject row = (JSONObject)elsTimetable.get(1);
                JSONArray TimeTableDatas = (JSONArray) row.get("row");

                for (Object timeTable : TimeTableDatas) {
                    JSONObject data = (JSONObject) timeTable;
                    System.out.println("data : "+ data);
                    String date = data.get("ALL_TI_YMD").toString();
                    if(timeTables.isEmpty()){
                        TimeTable newTimeTable = new TimeTable(date);
                        newTimeTable.addPeriod();
                        newTimeTable.addSubject(data.get("ITRT_CNTNT").toString());
                        timeTables.add(newTimeTable);
                    } else {
                        AtomicBoolean isNew = new AtomicBoolean(false);
                        timeTables.forEach(timeTable1 -> {
                            if (timeTable1.getDate().equals(date) ) {
                                isNew.set(false);
                                if(data.get("ITRT_CNTNT") != null) {
                                    timeTable1.addPeriod();
                                    timeTable1.addSubject(data.get("ITRT_CNTNT").toString());
                                }
                            } else {
                                isNew.set(true);
                            }
                        });
                        if(isNew.get()){
                            TimeTable newTimeTable = new TimeTable(date);
                            System.out.println(data.get("ITRT_CNTNT")!= null);
                            if(data.get("ITRT_CNTNT")!= null){
                                    newTimeTable.addPeriod();
                                    newTimeTable.addSubject(data.get("ITRT_CNTNT").toString());
                            }
                            timeTables.add(newTimeTable);

                        }
                    }
                }
            }
        }catch (Exception e){
            System.out.println("시간표 검색 오류 : " + e.getMessage());
        }
        return timeTables;
    }


    @Data
    @Getter
    public static class TimeTable{
        private String date;
        private Integer period;
        private List<String> subject;

        public TimeTable(String date){
            this.date = date;
            this.period = 0;
            this.subject = new ArrayList<>();
        }

        public void addPeriod(){
            this.period++;
        }
        public void addSubject(String subject){
            this.subject.add(subject);
        }
    }


    @Data
    @Builder
    public static class LunchMenu{
        private String date;
        private String Menu;
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
