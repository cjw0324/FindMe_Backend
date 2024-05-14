package com.findme.FindMeBack.Controller.ApiDataSaveController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.findme.FindMeBack.Controller.ApiDataSaveController.Dto.DateDto.Item;
import com.findme.FindMeBack.Controller.ApiDataSaveController.Dto.DateDto.JsonBody;
import com.findme.FindMeBack.Entity.PoliceDateItem;
import com.findme.FindMeBack.Service.PoliceDateItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import static com.findme.FindMeBack.Controller.GetItemController.CommonFunction.Converter.xmlToJson;

@RequiredArgsConstructor
@RestController
public class PoliceDateSaveController {

    private final PoliceDateItemService policeDateItemService;
    @PostMapping("/police/date/save")
    public String PoliceDateSave(@RequestBody JsonBody jsonBody) throws IOException {
        // 경찰청 API URL 생성
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1320000/LosfundInfoInqireService/getLosfundInfoAccToClAreaPd");
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=4CJYgVugQHbEfSLvdMoGGPFz4Ms%2BrbiUxEk555iigL9ledz0QFEjxOD1mXDCTP0Ziu5%2FHJQ2bYkUTshjquNArg%3D%3D");
        urlBuilder.append("&" + URLEncoder.encode("START_YMD","UTF-8") + "=" + URLEncoder.encode(jsonBody.getSTART_YMD() != null ? jsonBody.getSTART_YMD() : "", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("END_YMD","UTF-8") + "=" + URLEncoder.encode(jsonBody.getEND_YMD() != null ? jsonBody.getEND_YMD() : "", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
        // 결과 max 1,000,000개로 설정
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); /*한 페이지 결과 수*/

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


        List<Item> items = DateJsonToObject(xmlToJson(sb.toString()));
        for (Item item : items) {
            PoliceDateItem policeDateItem = new PoliceDateItem();
            policeDateItem.setAtcId(item.getAtcId()); //1
            policeDateItem.setFdYmd(item.getFdYmd()); //2
            policeDateItem.setClrNm(item.getClrNm()); //3
            policeDateItem.setDepPlace(item.getDepPlace()); //4
            policeDateItem.setPrdtClNm(item.getPrdtClNm()); //5
            policeDateItem.setFdPrdtNm(item.getFdPrdtNm()); //6
            policeDateItem.setFdSbjt(item.getFdSbjt()); //7
            policeDateItem.setFdFilePathImg(item.getFdFilePathImg()); //8
            policeDateItem.setFdSn(item.getFdSn()); //9
            policeDateItem.setRnum(item.getRnum()); //10

            policeDateItemService.save(policeDateItem);
        }



        return items.toString();
    }

    public static List<Item> DateJsonToObject(String jsonResponse) {
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
