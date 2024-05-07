package com.findme.FindMeBack.Controller.LostFoundController.PotalLostFoundController;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class GetPtLosfundDetailInfoController {

    @PostMapping("/api-with-find-detail-info")
    public List<Item> FindWithDetail(@RequestParam(required = false) Integer pageNo, @RequestBody SearchItemsWithDetail items) throws IOException {
        // 경찰청 API URL 생성
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1320000/LosPtfundInfoInqireService/getPtLosfundDetailInfo");
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=8ECKU3vHd0sG4PqlShJ3i5t8igUqcvY5pLJIODwzsvUuYgFh7Gw%2BYb81Zcras26oH6oJY%2FW%2FqznXyBmrG6%2FrcA%3D%3D");
        urlBuilder.append("&" + URLEncoder.encode("ATC_ID","UTF-8") + "=" + URLEncoder.encode(items.getATC_ID(), "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("FD_SN","UTF-8") + "=" + URLEncoder.encode(items.getFD_SN(), "UTF-8"));

        // 페이지 번호 추가
        if (pageNo != null) {
            urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + pageNo);
        }

        // 페이지당 결과 수 설정
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/

        // HTTP 연결 설정
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        // 응답 처리
        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        // JSON을 객체로 변환하여 반환
        List<Item> itemList = jsonToObject(xmlToJson(sb.toString()), items);

        // 요청 바디와 응답 바디를 로깅
        //System.out.println("Request Body: " + items);
        //System.out.println("Response Body: " + sb.toString());

        return itemList;
    }

    // JSON 문자열을 객체로 변환하는 메서드
    private List<Item> jsonToObject(String jsonInput, SearchItemsWithDetail items) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Item> itemList = new ArrayList<>();
        try {
            LostItemsResponse response = objectMapper.readValue(jsonInput, LostItemsResponse.class);
            if (response != null && response.getResponse() != null && response.getResponse().getBody() != null && response.getResponse().getBody().getItem() != null) {
                Object apiItems = response.getResponse().getBody().getItem();
                if (apiItems instanceof List) {
                    itemList = (List<Item>) apiItems;
                } else if (apiItems instanceof Map) {
                    Map<String, Item> itemMap = (Map<String, Item>) apiItems;
                    itemList = new ArrayList<>(itemMap.values());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return itemList;
    }

    // XML을 JSON으로 변환하는 메서드
    private String xmlToJson(String str) {
        try {
            if (str == null) {
                return null; // 문자열이 null이면 null을 반환
            }

            String xml = str;
            JSONObject jObject = XML.toJSONObject(xml);
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            Object json = mapper.readValue(jObject.toString(), Object.class);
            return mapper.writeValueAsString(json);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 분실물 항목에 대한 정보 저장 클래스
    @Getter
    @Setter
    public static class Item {
        private String atcId;            // 관리 ID
        private String csteSteNm;        // 보관 상태명
        private String depPlace;         // 보관장소
        private String fdFilePathImg;    // 사진 파일 경로
        private String fdHor;            // 습득 시간
        private String fdPlace;          // 습득 장소
        private String fdPrdtNm;         // 물품명
        private String fdSn;             // 습득 순번
        private Date fdYmd;              // 습득 일자
        private String fndKeepOrgnSeNm; // 습득물 보관 기관 구분명
        private String orgId;            // 기관 아이디
        private String orgNm;            // 기관명
        private String prdtClNm;         // 물품 분류명
        private String tel;              // 전화번호
        private String uniq;             // 특이사항
    }

    // 분실물 정보 조회 시 필요한 파라미터들 저장 클래스
    @Getter
    @Setter
    public static class SearchItemsWithDetail {
        public String ATC_ID;  // 관리 ID
        public String FD_SN;   // 습득 순번
    }

    // API 응답 내용 저장 클래스
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Response {
        private Header header;
        private Body body;

        @Getter
        @Setter
        public static class Header {
            private String resultCode;
            private String resultMsg;
        }

        @Getter
        @Setter
        public static class Body {
            private int pageNo;
            private int totalCount;
            private int numOfRows; // numOfRows 필드 추가
            private Object item;
        }
    }

    // API 응답 전체 내용 저장 클래스
    @Getter
    @Setter
    public static class LostItemsResponse {
        @JsonProperty("response")
        private Response response;
    }
}
