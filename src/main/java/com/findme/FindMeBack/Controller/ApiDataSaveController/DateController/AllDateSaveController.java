package com.findme.FindMeBack.Controller.ApiDataSaveController.DateController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.findme.FindMeBack.Controller.ApiDataSaveController.Dto.DateDto.Item;
import com.findme.FindMeBack.Controller.ApiDataSaveController.Dto.DateDto.JsonBody;
import com.findme.FindMeBack.Entity.DateItem;
import com.findme.FindMeBack.Service.DateItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
public class AllDateSaveController {
    private final DateItemService dateItemService;
    @Value("${my.admin.key}")
    private String key;

    @Value("${my.api.key}")
    private String apiKey;


    @PostMapping("/date/save")
    public String DateSave(@RequestBody JsonBody jsonBody) throws IOException {
        if ((jsonBody.getADMIN_KEY()).equals(key) == false) {
            return "non valid admin key!!!";
        }
        SelectPlaceAndDateSave(jsonBody);

        return "Police, Portal Date Save Done ("+jsonBody.getSTART_YMD()+"~"+jsonBody.getEND_YMD()+")";
    }

    public void SelectPlaceAndDateSave(JsonBody jsonBody) throws IOException {
        String PlaceCodeList[] = {
                "LCI000", //경기도
                "LCL000", //전라남도
                "LCM000", //전라북도
                "LCW000", //세종특별자치시
                "LCA000", //서울특별시
                "LCH000", //강원도
                "LCN000", //충청남도
                "LCQ000", //광주광역시
                "LCR000", //대구광역시
                "LCS000", //대전광역시
                "LCT000", //부산광역시
                "LCU000", //울산광역시
                "LCV000", //인천광역시
                "LCJ000", //경상남도
                "LCK000", //경상북도
                "LCO000", //충청북도
                "LCP000" //제주특별자치도
                //기타 (LCE000), 해외(LCF000) 은 제외 함.
        };

        String policeApiUrl = "http://apis.data.go.kr/1320000/LosfundInfoInqireService/getLosfundInfoAccToClAreaPd";
        String portalApiUrl = "http://apis.data.go.kr/1320000/LosPtfundInfoInqireService/getPtLosfundInfoAccToClAreaPd";
        for (String place : PlaceCodeList) {
            DateSave(policeApiUrl, jsonBody.getSTART_YMD(), jsonBody.getEND_YMD(), place);
            DateSave(portalApiUrl, jsonBody.getSTART_YMD(), jsonBody.getEND_YMD(), place);
        }

    }



    public void DateSave(String apiUrl, String START_YMD, String END_YMD, String place) throws IOException {
        // 경찰청 API URL 생성
        StringBuilder urlBuilder = new StringBuilder(apiUrl);
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + apiKey);
        urlBuilder.append("&" + URLEncoder.encode("START_YMD","UTF-8") + "=" + URLEncoder.encode(START_YMD, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("END_YMD","UTF-8") + "=" + URLEncoder.encode(END_YMD, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("N_FD_LCT_CD","UTF-8") + "=" + URLEncoder.encode(place, "UTF-8")); /*습득지역*/
        // 결과 max 1,000,000개로 설정
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

        System.out.println(sb.toString());


        List<Item> items = DateJsonToObject(xmlToJson(sb.toString()));
        for (Item item : items) {
            DateItem dateItem = new DateItem();
            dateItem.setAtcId(item.getAtcId()); //1
            dateItem.setFdYmd(item.getFdYmd()); //2
            dateItem.setClrNm(item.getClrNm()); //3
            dateItem.setDepPlace(item.getDepPlace()); //4
            dateItem.setPrdtClNm(item.getPrdtClNm()); //5
            dateItem.setFdPrdtNm(item.getFdPrdtNm()); //6
            dateItem.setFdSbjt(item.getFdSbjt()); //7
            dateItem.setFdFilePathImg(item.getFdFilePathImg()); //8
            dateItem.setFdSn(item.getFdSn()); //9
            dateItem.setRnum(item.getRnum()); //10
            dateItem.setN_FD_LCT_CD(place);
            dateItemService.save(dateItem);
        }
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

