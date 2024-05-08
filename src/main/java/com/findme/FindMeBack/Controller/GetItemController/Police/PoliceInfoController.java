package com.findme.FindMeBack.Controller.GetItemController.Police;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.findme.FindMeBack.Controller.GetItemController.Police.Dto.PoliceInfoDto.*;
import org.springframework.web.bind.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;


import static com.findme.FindMeBack.Controller.GetItemController.CommonFunction.Converter.xmlToJson;

@RestController
public class PoliceInfoController {

    @PostMapping("/police/info")
    public Item policeFindWithDetail(@RequestBody SearchItemsWithDetail items) throws IOException {
        String ATC_ID = items.getATC_ID();
        String FD_SN = items.getFD_SN();

        // 경찰청 API URL 생성
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1320000/LosfundInfoInqireService/getLosfundDetailInfo");
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=4CJYgVugQHbEfSLvdMoGGPFz4Ms%2BrbiUxEk555iigL9ledz0QFEjxOD1mXDCTP0Ziu5%2FHJQ2bYkUTshjquNArg%3D%3D");
        urlBuilder.append("&" + URLEncoder.encode("ATC_ID", "UTF-8") + "=" + URLEncoder.encode(ATC_ID, "UTF-8")); /*관리ID*/
        urlBuilder.append("&" + URLEncoder.encode("FD_SN", "UTF-8") + "=" + URLEncoder.encode(FD_SN, "UTF-8")); /*습득순번*/

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
        return DataJsonToObject(xmlToJson(sb.toString()));
    }
    // JSON 문자열을 객체로 변환하는 메서드

    // JSON 문자열을 객체로 변환하는 메서드

    public static Item DataJsonToObject(String jsonResponse) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode itemsNode = rootNode.path("response").path("body").path("item");

            Item singleItem = objectMapper.treeToValue(itemsNode, Item.class);
            return singleItem;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
