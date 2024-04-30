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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class GetPtLosfundInfoAccToClAreaPdController {

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
    @PostMapping("/api-find-with-date")
    public List<Item> FindWithDate(@RequestParam String pageNo, String numOfRows, String PRDT_CL_CD_01, String PRDT_CL_CD_02, String CLR_CD, String START_YMD, String END_YMD, String N_FD_LCT_CD) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1320000/LosPtfundInfoInqireService/getPtLosfundInfoAccToClAreaPd"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=fVg%2Fd%2B77uSYbcluuIdMhpTM19L7TlHmcV6uHQpnExlDlF5%2Fr3JiiS1dM8yJtHB0kzwvqvS3VWpoKOcfg7L3PGg%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode(pageNo, "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode(numOfRows, "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("PRDT_CL_CD_01","UTF-8") + "=" + URLEncoder.encode(PRDT_CL_CD_01, "UTF-8")); /*대분류*/
        urlBuilder.append("&" + URLEncoder.encode("PRDT_CL_CD_02","UTF-8") + "=" + URLEncoder.encode(PRDT_CL_CD_02, "UTF-8")); /*중분류*/
        urlBuilder.append("&" + URLEncoder.encode("CLR_CD","UTF-8") + "=" + URLEncoder.encode(CLR_CD, "UTF-8")); /*습득물 색상*/
        urlBuilder.append("&" + URLEncoder.encode("START_YMD","UTF-8") + "=" + URLEncoder.encode(START_YMD, "UTF-8")); /*검색시작일*/
        urlBuilder.append("&" + URLEncoder.encode("END_YMD","UTF-8") + "=" + URLEncoder.encode(END_YMD, "UTF-8")); /*검색종료일*/
        urlBuilder.append("&" + URLEncoder.encode("N_FD_LCT_CD","UTF-8") + "=" + URLEncoder.encode(N_FD_LCT_CD, "UTF-8")); /*습득지역*/
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
        return JsonToObject(xmlToJson(sb.toString()));
    }
}
