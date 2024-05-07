package com.findme.FindMeBack.Controller.GetItemController.Portal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.findme.FindMeBack.Controller.GetItemController.Portal.PortalLocationDto.Item;
import com.findme.FindMeBack.Controller.GetItemController.Portal.PortalLocationDto.LostItemsResponse;
import com.findme.FindMeBack.Controller.GetItemController.Portal.PortalLocationDto.SearchItemsWithLocation;
import org.json.JSONObject;
import org.json.XML;
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
import java.util.List;
import java.util.Map;

@RestController
public class PortalLocationController {
    @PostMapping("/portal/location")
    public List<Item> FindWithLocation(@RequestParam(required = false) Integer pageNo, @RequestBody SearchItemsWithLocation items) throws IOException {
        // API URL 생성
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1320000/LosPtfundInfoInqireService/getPtLosfundInfoAccToLc");
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=8ECKU3vHd0sG4PqlShJ3i5t8igUqcvY5pLJIODwzsvUuYgFh7Gw%2BYb81Zcras26oH6oJY%2FW%2FqznXyBmrG6%2FrcA%3D%3D");
        urlBuilder.append("&" + URLEncoder.encode("PRDT_NM","UTF-8") + "=" + URLEncoder.encode(items.getPRDT_NM() != null ? items.getPRDT_NM() : "", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("ADDR","UTF-8") + "=" + URLEncoder.encode(items.getADDR() != null ? items.getADDR() : "", "UTF-8"));
        //urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
        //urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode(String.valueOf(pageNo), "UTF-8")); /*페이지 번호*/
        // 페이지 번호 추가
        if (pageNo != null) {
            urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + pageNo);
        }
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
        String responseData = sb.toString();
        //System.out.println("Received XML response: " + responseData); // 로그로 XML 데이터 출력

        rd.close();
        conn.disconnect();

        // JSON을 객체로 변환하여 반환
        return jsonToObject(xmlToJson(sb.toString()), items);
    }

    // JSON 문자열을 객체로 변환하는 메서드
    private List<Item> jsonToObject(String jsonInput, SearchItemsWithLocation items) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Item> itemList = new ArrayList<>();
        try {
            LostItemsResponse response = objectMapper.readValue(jsonInput, LostItemsResponse.class);
            if (response != null && response.getResponse() != null && response.getResponse().getBody() != null && response.getResponse().getBody().getItems() != null) {
                Object apiItems = response.getResponse().getBody().getItems();
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
