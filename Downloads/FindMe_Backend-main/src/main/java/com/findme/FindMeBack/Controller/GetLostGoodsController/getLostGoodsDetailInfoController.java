package com.findme.FindMeBack.Controller.GetLostGoodsController;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.util.Date;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class getLostGoodsDetailInfoController {

    @Getter
    @Setter
    public static class Item {
        private String lstFilePathImg;
        private String atcId;
        private String lstHor;
        private String prdtClNm;
        private String lstPrdtNm;
        private String lstPlace;
        private String lstSbjt;
        private String orgId;
        private String lstPlaceSeNm;
        private String orgNm;
        private String lstSteNm;
        private String lstLctNm;
        private Date lstYmd;
        private String uniq;
        private String tel;

    }
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Body {
        private Item item;
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

    public static Item JsonToObject(String jsonInput) throws JsonProcessingException {
        System.out.println("JsonToObject come in");
        ObjectMapper objectMapper = new ObjectMapper();

        Item item = new Item();
        try {
            LostItemsResponse response = objectMapper.readValue(jsonInput, LostItemsResponse.class);
            if (response != null && response.getResponse() != null && response.getResponse().getBody() != null &&
                    response.getResponse().getBody().getItem() != null && response.getResponse().getBody().getItem() != null) {
                item = response.getResponse().getBody().getItem();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return item;
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
    @PostMapping("/api-info")
    public Item ItemInfoController(@RequestParam String ATC_ID) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1320000/LostGoodsInfoInqireService/getLostGoodsDetailInfo"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=fVg%2Fd%2B77uSYbcluuIdMhpTM19L7TlHmcV6uHQpnExlDlF5%2Fr3JiiS1dM8yJtHB0kzwvqvS3VWpoKOcfg7L3PGg%3D%3D");  /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("ATC_ID","UTF-8") + "=" + URLEncoder.encode(ATC_ID, "UTF-8")); /*관리ID*/
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
        System.out.println(JsonToObject(xmlToJson(sb.toString())));
        return JsonToObject(xmlToJson(sb.toString()));
    }
}