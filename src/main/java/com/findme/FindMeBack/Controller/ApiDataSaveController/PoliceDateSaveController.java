//package com.findme.FindMeBack.Controller.ApiDataSaveController;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.findme.FindMeBack.Controller.ApiDataSaveController.Dto.JsonBody;
//import com.findme.FindMeBack.Controller.GetItemController.CommonFunction.Converter;
//import com.findme.FindMeBack.Entity.PoliceDateItem;
//import com.findme.FindMeBack.Service.PoliceDateItemService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//@RequiredArgsConstructor
//@RestController
//public class PoliceDateSaveController {
//
//    private final PoliceDateItemService policeDateItemService;
//    @PostMapping("/police/date/save")
//    public String PoliceDateSave(@RequestBody JsonBody jsonBody) throws IOException {
//        // 경찰청 API URL 생성
//        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1320000/LosfundInfoInqireService/getLosfundInfoAccToClAreaPd");
//        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=4CJYgVugQHbEfSLvdMoGGPFz4Ms%2BrbiUxEk555iigL9ledz0QFEjxOD1mXDCTP0Ziu5%2FHJQ2bYkUTshjquNArg%3D%3D");
//        urlBuilder.append("&" + URLEncoder.encode("START_YMD","UTF-8") + "=" + URLEncoder.encode(jsonBody.getSTART_YMD() != null ? jsonBody.getSTART_YMD() : "", "UTF-8"));
//        urlBuilder.append("&" + URLEncoder.encode("END_YMD","UTF-8") + "=" + URLEncoder.encode(jsonBody.getEND_YMD() != null ? jsonBody.getEND_YMD() : "", "UTF-8"));
//        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
//        // 결과 max 1,000,000개로 설정
//        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1000000", "UTF-8")); /*한 페이지 결과 수*/
//
//        // HTTP 연결 설정
//        URL url = new URL(urlBuilder.toString());
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setRequestMethod("GET");
//        conn.setRequestProperty("Content-type", "application/json");
//
//        // 응답 처리
//        BufferedReader rd;
//        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
//            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//        } else {
//            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
//        }
//        StringBuilder sb = new StringBuilder();
//        String line;
//        while ((line = rd.readLine()) != null) {
//            sb.append(line);
//        }
//        rd.close();
//        conn.disconnect();
//
//        policeDateItemService.saveAll(DateJsonToObject(Converter.xmlToJson(sb.toString())));
//
//
//
//
//
//        // xml -> JSON -> object 바꾸어 return
//        return "ok";
//    }
//    public static List<PoliceDateItem> DateJsonToObject(String jsonInput) throws JsonProcessingException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        List<PoliceDateItem> itemList = new ArrayList<>();
//        try {
//            LostItemsResponse response = objectMapper.readValue(jsonInput, LostItemsResponse.class);
//            if (response != null && response.getResponse() != null && response.getResponse().getBody() != null && response.getResponse().getBody().getItems() != null) {
//                Object apiItems = response.getResponse().getBody().getItems();
//                if (apiItems instanceof List) {
//                    itemList = (List<PoliceDateItem>) apiItems;
//                } else if (apiItems instanceof Map) {
//                    Map<String, PoliceDateItem> itemMap = (Map<String, PoliceDateItem>) apiItems;
//                    itemList = new ArrayList<>(itemMap.values());
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println("itemList.toString() = " + itemList.toString());
//        return itemList;
//    }
//
//}
