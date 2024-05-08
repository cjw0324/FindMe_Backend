package com.findme.FindMeBack.Controller.GetItemController.Portal;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.findme.FindMeBack.Controller.GetItemController.Portal.Dto.PortalPlaceDto.*;
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
import java.util.List;

import static com.findme.FindMeBack.Controller.GetItemController.CommonFunction.Converter.xmlToJson;

@RestController
public class PortalPlaceController {

    @PostMapping("/portal/place")
    public List<Item> FindWithPlace(@RequestParam(required = false) Integer pageNo, @RequestBody SearchItemsWithPlace items) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1320000/LosPtfundInfoInqireService/getPtLosfundInfoAccTpNmCstdyPlace");
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=8ECKU3vHd0sG4PqlShJ3i5t8igUqcvY5pLJIODwzsvUuYgFh7Gw%2BYb81Zcras26oH6oJY%2FW%2FqznXyBmrG6%2FrcA%3D%3D");
        urlBuilder.append("&" + URLEncoder.encode("PRDT_NM","UTF-8") + "=" + URLEncoder.encode(items.getPRDT_NM() != null ? items.getPRDT_NM() : "", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("DEP_PLACE","UTF-8") + "=" + URLEncoder.encode(items.getDEP_PLACE() != null ? items.getDEP_PLACE() : "", "UTF-8"));
        // 페이지 번호 추가
        if (pageNo != null) {
            urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + pageNo);
        }
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8"));

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

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

        return PlaceJsonToObject(xmlToJson(sb.toString()));
    }


    public static List<Item> PlaceJsonToObject(String jsonResponse) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode itemsNode = rootNode.path("response").path("body").path("items").path("item");

            if (itemsNode.isArray()) {
                // itemsNode가 배열인 경우, 해당 배열을 List<Item>으로 변환
                return objectMapper.convertValue(itemsNode, new TypeReference<List<Item>>(){});
            } else {
                // 단일 객체인 경우, 이 객체를 포함하는 리스트를 생성
                Item singleItem = objectMapper.treeToValue(itemsNode, Item.class);
                return List.of(singleItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
