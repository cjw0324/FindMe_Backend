package com.findme.FindMeBack.Controller.GetItemController.Portal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
public class PortalDateController {

    @PostMapping("/portal/date")
    public ResponseEntity<List<Item>> findWithDate(@RequestParam(required = false) Integer pageNo, @RequestBody SearchItemsWithDate items) {
        try {
            //System.out.println("Received items: " + items);
            // API 요청 URL 설정
            StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1320000/LosPtfundInfoInqireService/getPtLosfundInfoAccToClAreaPd");
            urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=8ECKU3vHd0sG4PqlShJ3i5t8igUqcvY5pLJIODwzsvUuYgFh7Gw%2BYb81Zcras26oH6oJY%2FW%2FqznXyBmrG6%2FrcA%3D%3D");
            urlBuilder.append("&" + URLEncoder.encode("PRDT_CL_CD_01","UTF-8") + "=" + URLEncoder.encode(items.getPRDT_CL_CD_01() != null ? items.getPRDT_CL_CD_01() : "", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("PRDT_CL_CD_02","UTF-8") + "=" + URLEncoder.encode(items.getPRDT_CL_CD_02() != null ? items.getPRDT_CL_CD_02() : "", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("N_FD_LCT_CD","UTF-8") + "=" + URLEncoder.encode(items.getN_FD_LCT_CD() != null ? items.getN_FD_LCT_CD() : "", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("START_YMD","UTF-8") + "=" + URLEncoder.encode(items.getSTART_YMD() != null ? items.getSTART_YMD() : "", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("END_YMD","UTF-8") + "=" + URLEncoder.encode(items.getEND_YMD() != null ? items.getEND_YMD() : "", "UTF-8"));
            //urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
            //urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode(String.valueOf(pageNo), "UTF-8")); /*페이지 번호*/
            if (pageNo != null) {
                urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + pageNo);
            }
            urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/

            //System.out.println("Received items: " + items);
            // URL 객체 생성
            URL url = new URL(urlBuilder.toString());

            // HTTP 연결 설정
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");

            // 응답 코드 확인
            int responseCode = conn.getResponseCode();

            // 응답 내용 가져오기
            BufferedReader reader;
            if (responseCode == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            // 응답 내용을 문자열로 변환
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
            }
            reader.close();

            String responseData = response.toString();
            //System.out.println("Received XML response: " + responseData); // 로그로 XML 데이터 출력


            // XML을 JSON으로 변환
            JSONObject jsonObject = XML.toJSONObject(responseData);
            String jsonString = jsonObject.toString();

            // JSON을 객체로 변환
            List<Item> itemList = jsonToObject(jsonString);

            return new ResponseEntity<>(itemList, HttpStatus.OK);
            //return jsonToObject(xmlToJson(response.toString()), itemList);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

    }

    // 내부 클래스들

    @Getter
    @Setter
    public class Item {
        private String atcId;
        private String depPlace;
        private String fdFilePathImg;
        private String fdPrdtNm;
        private String fdSbjt;
        private String fdSn;
        private Date fdYmd;
        private String prdtClNm;
        private String rnum;

    }
    @Getter
    @Setter
    public class Items {
        @JsonProperty("item")
        private List<Item> item;

    }

    @Getter
    @Setter
    public class SearchItemsWithDate {
        public String PRDT_CL_CD_01;
        public String PRDT_CL_CD_02;
        public String N_FD_LCT_CD;
        public String START_YMD;
        public String END_YMD;
    }

    @Getter
    @Setter
    public class Response {
        private Header header;
        private Body body;

        @Getter
        @Setter
        public class Header {
            private String resultCode;
            private String resultMsg;
        }

        @Getter
        @Setter
        public class Body {
            private int pageNo;
            private int totalCount;
            private int numOfRows;
            private Items items;
        }
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class LostItemsResponse {
        @JsonProperty("response")
        private Response response;

        public Items getItems() {
            return response.getBody().getItems();
        }
    }


    private List<Item> jsonToObject(String jsonInput) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Item> itemList = new ArrayList<>();
        try {
            LostItemsResponse response = objectMapper.readValue(jsonInput, LostItemsResponse.class);
            if (response != null && response.getResponse() != null && response.getResponse().getBody() != null) {
                List<Item> apiItems = (List<Item>) response.getResponse().getBody().getItems().getItem();
                itemList.addAll(apiItems);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return itemList;
    }



}

