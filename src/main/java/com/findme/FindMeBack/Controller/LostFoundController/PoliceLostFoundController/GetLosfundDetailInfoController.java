/*습득물 상세정보 조회*/
package com.findme.FindMeBack.Controller.LostFoundController.PoliceLostFoundController;

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
import java.util.Date;

@RestController
public class GetLosfundDetailInfoController {

    @PostMapping("/api-police-find-detail-info")
    public Item PoliceFindWithDetail(@RequestBody SearchItemsWithDetail request) throws IOException {
        // 경찰청 API URL 생성
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1320000/LosfundInfoInqireService/getLosfundDetailInfo");
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=4CJYgVugQHbEfSLvdMoGGPFz4Ms%2BrbiUxEk555iigL9ledz0QFEjxOD1mXDCTP0Ziu5%2FHJQ2bYkUTshjquNArg%3D%3D");
        urlBuilder.append("&" + URLEncoder.encode("ATC_ID", "UTF-8") + "=" + URLEncoder.encode(request.getAtcId(), "UTF-8")); /*관리ID*/
        urlBuilder.append("&" + URLEncoder.encode("FD_SN", "UTF-8") + "=" + URLEncoder.encode(request.getFdSn(), "UTF-8")); /*습득순번*/

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
        return jsonToObject(xmlToJson(sb.toString()), request);
    }

    // JSON 문자열을 객체로 변환하는 메서드
    private Item jsonToObject(String jsonInput, SearchItemsWithDetail request) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Item item = null;
        try {
            LostItemDetailResponse response = objectMapper.readValue(jsonInput, LostItemDetailResponse.class);
            if (response != null && response.getResponse() != null && response.getResponse().getBody() != null && response.getResponse().getBody().getItem() != null) {
                item = response.getResponse().getBody().getItem();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return item;
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

    // 분실물 세부 정보 저장 클래스
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

    // 분실물 세부 정보 조회 시 필요한 파라미터들 저장 클래스
    @Getter
    @Setter
    public static class SearchItemsWithDetail {
        public String atcId;  // 관리 ID
        public String fdSn;   // 습득 순번
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
            private Item item;
        }
    }

    // API 응답 전체 내용 저장 클래스
    @Getter
    @Setter
    public static class LostItemDetailResponse {
        @JsonProperty("response")
        private Response response;
    }
}
