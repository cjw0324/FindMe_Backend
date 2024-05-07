/*분류별, 지역별, 기간별 습득물 정보 조회 (색상 제외)*/
package com.findme.FindMeBack.Controller.GetItemController.Police;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.findme.FindMeBack.Controller.GetItemController.CommonFunction.Converter;
import com.findme.FindMeBack.Controller.GetItemController.Police.Dto.PoliceDateDto.Item;
import com.findme.FindMeBack.Controller.GetItemController.Police.Dto.PoliceDateDto.LostItemsResponse;
import com.findme.FindMeBack.Controller.GetItemController.Police.Dto.PoliceDateDto.SearchItemsWithDate;
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

import static com.findme.FindMeBack.Controller.GetItemController.CommonFunction.Converter.DateJsonToObject;


@RestController
public class PoliceDateController {

    @PostMapping("/police/date")
    public List<Item> PoliceFindWithDate(@RequestParam(required = false) Integer pageNo, @RequestBody SearchItemsWithDate items) throws IOException {
        // 경찰청 API URL 생성
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1320000/LosfundInfoInqireService/getLosfundInfoAccToClAreaPd");
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=4CJYgVugQHbEfSLvdMoGGPFz4Ms%2BrbiUxEk555iigL9ledz0QFEjxOD1mXDCTP0Ziu5%2FHJQ2bYkUTshjquNArg%3D%3D");
        urlBuilder.append("&" + URLEncoder.encode("PRDT_CL_CD_01","UTF-8") + "=" + URLEncoder.encode(items.getPRDT_CL_CD_01() != null ? items.getPRDT_CL_CD_01() : "", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("PRDT_CL_CD_02","UTF-8") + "=" + URLEncoder.encode(items.getPRDT_CL_CD_02() != null ? items.getPRDT_CL_CD_02() : "", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("START_YMD","UTF-8") + "=" + URLEncoder.encode(items.getSTART_YMD() != null ? items.getSTART_YMD() : "", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("END_YMD","UTF-8") + "=" + URLEncoder.encode(items.getEND_YMD() != null ? items.getEND_YMD() : "", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("N_FD_LCT_CD","UTF-8") + "=" + URLEncoder.encode(items.getN_FD_LCT_CD() != null ? items.getN_FD_LCT_CD() : "", "UTF-8"));

        // 페이지 번호 추가
        if (pageNo != null) {
            urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode(pageNo.toString(), "UTF-8"));
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

        // xml -> JSON -> object 바꾸어 return
        return DateJsonToObject(Converter.xmlToJson(sb.toString()));
    }
    public static List<Item> DateJsonToObject(String jsonInput) throws JsonProcessingException {
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




}
