package com.findme.FindMeBack.Controller.GetLostGoodsController;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException; // IOException을 import합니다.
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
@RequiredArgsConstructor
@RestController
public class GetLostGoodsInfoAccTpNmCstdyPlaceController { // 클래스 이름은 대문자로 시작해야 합니다.

    @Getter
    @Setter
    public static class Item {
        private String atcId;
        private String lstPlace;
        private String lstPrdtNm;
        private String lstSbjt;
        private Date lstYmd;
        private String prdtClNm;
        private int rnum;

    }

    @Getter
    @Setter
    public static class Items {
        private List<Item> item;
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
    public static class Body {
        private int pageNo;
        private int totalCount;
        private Items items;
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

    public static List<Item> JsonToObject(String jsonInput) throws JsonProcessingException {
        System.out.println("JsonToObject come in");
        ObjectMapper objectMapper = new ObjectMapper();

        List<Item> itemList = new ArrayList<>();
        try {
            LostItemsResponse response = objectMapper.readValue(jsonInput, LostItemsResponse.class);
            if (response != null && response.getResponse() != null && response.getResponse().getBody() != null &&
                    response.getResponse().getBody().getItems() != null && response.getResponse().getBody().getItems().getItem() != null) {
                itemList = response.getResponse().getBody().getItems().getItem();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return itemList;
    }
    public static String xmlToJson(String str) {
        System.out.println("xmlToJson come in");
        try {
            String xml = str;
            JSONObject jObject = XML.toJSONObject(xml);
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            Object json = mapper.readValue(jObject.toString(), Object.class);
            String output = mapper.writeValueAsString(json);
            System.out.println("output = " + output);
            return output;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @CrossOrigin
    @PostMapping("/api-with-place")
    public List<Item> NamePlaceSearch(@RequestParam String LST_PLACE, String LST_PRDT_NM, String pageNo, String numOfRows) throws IOException { // 메서드 이름은 소문자로 시작하며, 'throws IOException'을 추가합니다.
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1320000/LostGoodsInfoInqireService/getLostGoodsInfoAccTpNmCstdyPlace"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=fVg%2Fd%2B77uSYbcluuIdMhpTM19L7TlHmcV6uHQpnExlDlF5%2Fr3JiiS1dM8yJtHB0kzwvqvS3VWpoKOcfg7L3PGg%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("LST_PLACE","UTF-8") + "=" + URLEncoder.encode(LST_PLACE, "UTF-8")); /*분실지역명*/
        urlBuilder.append("&" + URLEncoder.encode("LST_PRDT_NM","UTF-8") + "=" + URLEncoder.encode(LST_PRDT_NM, "UTF-8")); /*분실물명*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode(pageNo, "UTF-8")); /*페이지 번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode(numOfRows, "UTF-8")); /*목록 건수*/
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
        System.out.println("connect success");

        System.out.println("sb.toString() = " + sb.toString());
        return JsonToObject(xmlToJson(sb.toString()));
    }
}
