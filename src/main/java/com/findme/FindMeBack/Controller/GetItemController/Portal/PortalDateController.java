package com.findme.FindMeBack.Controller.GetItemController.Portal;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.findme.FindMeBack.Controller.GetItemController.CommonFunction.Converter;
import com.findme.FindMeBack.Controller.GetItemController.Portal.Dto.PortalDateDto.*;
import org.springframework.beans.factory.annotation.Value;
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


@RestController
public class PortalDateController {

    @Value("${my.api.key}")
    private String apiKey;

    @PostMapping("/portal/date")
    public List<Item> findWithDate(@RequestParam(required = false) Integer pageNo, @RequestBody SearchItemsWithDate items) throws IOException {
        // API 요청 URL 설정
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1320000/LosPtfundInfoInqireService/getPtLosfundInfoAccToClAreaPd");
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + apiKey);
        urlBuilder.append("&" + URLEncoder.encode("PRDT_CL_CD_01", "UTF-8") + "=" + URLEncoder.encode(items.getPRDT_CL_CD_01() != null ? items.getPRDT_CL_CD_01() : "", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("PRDT_CL_CD_02", "UTF-8") + "=" + URLEncoder.encode(items.getPRDT_CL_CD_02() != null ? items.getPRDT_CL_CD_02() : "", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("N_FD_LCT_CD", "UTF-8") + "=" + URLEncoder.encode(items.getN_FD_LCT_CD() != null ? items.getN_FD_LCT_CD() : "", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("START_YMD", "UTF-8") + "=" + URLEncoder.encode(items.getSTART_YMD() != null ? items.getSTART_YMD() : "", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("END_YMD", "UTF-8") + "=" + URLEncoder.encode(items.getEND_YMD() != null ? items.getEND_YMD() : "", "UTF-8"));
        if (pageNo != null) {
            urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + pageNo);
        }
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/

        // URL 객체 생성
        URL url = new URL(urlBuilder.toString());

        // HTTP 연결 설정
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json");

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

        // xml -> JSON -> object 바꾸어 return
        return PortalDateJsonToObject(Converter.xmlToJson(sb.toString()));

    }

    // 내부 클래스들


    public static List<Item> PortalDateJsonToObject(String jsonResponse) {
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

