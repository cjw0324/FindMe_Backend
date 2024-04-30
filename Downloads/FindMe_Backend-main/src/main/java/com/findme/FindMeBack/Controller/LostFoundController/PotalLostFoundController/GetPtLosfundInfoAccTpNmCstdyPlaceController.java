package com.findme.FindMeBack.Controller.LostFoundController.PotalLostFoundController;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class GetPtLosfundInfoAccTpNmCstdyPlaceController {


    @Getter
    @Setter
    public static class Item {
        private String fdSbjt;
        private String rnum;
        private String atcId;
        private String fdFilePathImg;
        private String fdSn;
        private String depPlace;
        private String prdtClNm;
        private Date fdYmd;
        private String fdPrdtNm;

    }
    @Getter
    @Setter
    public static class Items {
        private List<Item> item;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Body {
        private int pageNo;
        private int totalCount;
        private Items items;
    }

    @Getter
    @Setter
    public static class Response {
        private Header header;
        private Body body;

        @Getter
        @Setter
        public static class Header {
            private String resultCode;
            private String resultMsg;
        }
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class LostItemsResponse {
        private Response response;

        public Response getResponse() {
            return response;
        }

        public void setResponse(Response response) {
            this.response = response;
        }

        @Override
        public String toString() {
            return "LostItemsResponse{" +
                    "response=" + response +
                    '}';
        }
    }

    @Getter
    @Setter
    public static class FilteringItem{
        private String rareJson;
        private String startDate;
        private String endDate;
    }

    public static Boolean IsInThePeriod(Item item, String startDate, String endDate){
        // Convert string dates (dateStr2 and dateStr3) to LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate sDate = LocalDate.parse(startDate, formatter);
        LocalDate eDate = LocalDate.parse(endDate, formatter);

        // Convert Date object (dateStr1) to LocalDate
        LocalDate dateToCheck = item.getFdYmd().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        // Check if dateToCheck is between startDate and endDate inclusive
        return !dateToCheck.isBefore(sDate) && !dateToCheck.isAfter(eDate);
    }

    public static List<Item> JsonToObject(FilteringItem filteringItem) throws JsonProcessingException {
        System.out.println("JsonToObject come in");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonInput = filteringItem.getRareJson();
        String startDate = filteringItem.getStartDate();
        String endDate = filteringItem.getEndDate();

        List<Item> itemList = new ArrayList<>();
        try {
            LostItemsResponse response = objectMapper.readValue(jsonInput, LostItemsResponse.class);
            if (response != null && response.getResponse() != null && response.getResponse().getBody() != null &&
                    response.getResponse().getBody().getItems() != null && response.getResponse().getBody().getItems().getItem() != null) {
                for (Item item : response.getResponse().getBody().getItems().getItem()) {
                    if (IsInThePeriod(item, startDate, endDate)) {
                        itemList.add(item);
                    }
                }
//                itemList = response.getResponse().getBody().getItems().getItem();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return itemList;
    }

    public String xmlToJson(String str) {
        try{
            String xml = str;
            JSONObject jObject = XML.toJSONObject(xml);
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            Object json = mapper.readValue(jObject.toString(), Object.class);
            String output = mapper.writeValueAsString(json);
            return output;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @CrossOrigin
    @PostMapping("/api-find-with-place")
    public List<Item> FindWithPlace(@RequestParam String pageNo, String numOfRows, String PRDT_NM, String DEP_PLACE, String START_YMD, String END_YMD) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1320000/LosPtfundInfoInqireService/getPtLosfundInfoAccTpNmCstdyPlace"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=fVg%2Fd%2B77uSYbcluuIdMhpTM19L7TlHmcV6uHQpnExlDlF5%2Fr3JiiS1dM8yJtHB0kzwvqvS3VWpoKOcfg7L3PGg%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode(pageNo, "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode(numOfRows, "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("PRDT_NM","UTF-8") + "=" + URLEncoder.encode(PRDT_NM, "UTF-8")); /*물품명*/
        urlBuilder.append("&" + URLEncoder.encode("DEP_PLACE","UTF-8") + "=" + URLEncoder.encode(DEP_PLACE, "UTF-8")); /*보관장소*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
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
        FilteringItem filteringItem = new FilteringItem();
        System.out.println("START_YMD = " + START_YMD);
        System.out.println("END_YMD = " + END_YMD);
        filteringItem.setStartDate(START_YMD);
        filteringItem.setEndDate(END_YMD);
        filteringItem.setRareJson(xmlToJson(sb.toString()));
        return JsonToObject(filteringItem);
    }
}