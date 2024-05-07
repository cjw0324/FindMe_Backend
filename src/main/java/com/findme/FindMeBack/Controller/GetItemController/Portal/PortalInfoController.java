package com.findme.FindMeBack.Controller.GetItemController.Portal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.findme.FindMeBack.Controller.GetItemController.Portal.PortalInfoDto.Item;
import com.findme.FindMeBack.Controller.GetItemController.Portal.PortalInfoDto.LostItemsResponse;
import com.findme.FindMeBack.Controller.GetItemController.Portal.PortalInfoDto.SearchItemsWithDetail;
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
import java.util.List;
import java.util.Map;

@RestController
public class PortalInfoController {

    @PostMapping("/portal/info")
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

}
