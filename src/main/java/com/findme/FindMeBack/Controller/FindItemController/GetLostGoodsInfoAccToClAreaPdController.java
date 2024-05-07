package com.findme.FindMeBack.Controller.FindItemController;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.findme.FindMeBack.Dto.ThingDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.JSONObject;
import org.json.XML;


@RequiredArgsConstructor
@RestController
public class GetLostGoodsInfoAccToClAreaPdController {

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

            // Getters and setters
        }
        // Getters and setters

    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Body {
        private int pageNo;
        private int totalCount;
        private Items items;

        // Getters and setters
    }

    @Getter
    @Setter
    public static class Items {
        private List<Item> item;

        // Getters and setters
    }
    @Getter
    @Setter
    public static class Item {
        private Long rnum;
        private String lstYmd;
        private String atcId;
        private String prdtClNm;
        private String lstPrdtNm;
        private String lstPlace;
        private String lstSbjt;
    }

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
    @PostMapping("/api-with-date")
    public List<ThingDto> SearchLostGoodsInfoWithDate(@RequestParam String START_YMD, String END_YMD, String PRDT_CL_CD_01, String PRDT_CL_CD_02, String LST_LCT_CD, String pageNo, String numOfRows) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1320000/LostGoodsInfoInqireService/getLostGoodsInfoAccToClAreaPd"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=fVg%2Fd%2B77uSYbcluuIdMhpTM19L7TlHmcV6uHQpnExlDlF5%2Fr3JiiS1dM8yJtHB0kzwvqvS3VWpoKOcfg7L3PGg%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("START_YMD","UTF-8") + "=" + URLEncoder.encode(START_YMD, "UTF-8")); /*분실물 등록날짜*/
        urlBuilder.append("&" + URLEncoder.encode("END_YMD","UTF-8") + "=" + URLEncoder.encode(END_YMD, "UTF-8")); /*분실물 등록날짜*/
        urlBuilder.append("&" + URLEncoder.encode("PRDT_CL_CD_01","UTF-8") + "=" + URLEncoder.encode(PRDT_CL_CD_01, "UTF-8")); /*상위물품코드*/
        urlBuilder.append("&" + URLEncoder.encode("PRDT_CL_CD_02","UTF-8") + "=" + URLEncoder.encode(PRDT_CL_CD_02, "UTF-8")); /*하위물품코드*/
        urlBuilder.append("&" + URLEncoder.encode("LST_LCT_CD","UTF-8") + "=" + URLEncoder.encode(LST_LCT_CD, "UTF-8")); /*분실지역코드*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode(pageNo, "UTF-8")); /*페이지 번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode(numOfRows, "UTF-8")); /*목록 건수*/
        URL url = new URL(urlBuilder.toString());
        System.out.println("url = " + url);
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

        ObjectMapper objectMapper = new ObjectMapper();

        List<ThingDto> thingDtoList = new ArrayList<>();

        try {
            // Parse the JSON string into a map
            LostItemsResponse response = objectMapper.readValue(xmlToJson(sb.toString()), LostItemsResponse.class);

            // Extract the list of items
//            List<ThingDto> thingDtoList = new ArrayList<>();
            if (response != null && response.getResponse() != null && response.getResponse().getBody() != null &&
                    response.getResponse().getBody().getItems() != null && response.getResponse().getBody().getItems().getItem() != null) {
                for (Item item : response.getResponse().getBody().getItems().getItem()) {
                    ThingDto thingDto = new ThingDto();
                    thingDto.setRnum(item.getRnum());
                    thingDto.setLstYmd(item.getLstYmd());
                    thingDto.setAtcId(item.getAtcId());
                    thingDto.setPrdtClNm(item.getPrdtClNm());
                    thingDto.setLstPrdtNm(item.getLstPrdtNm());
                    thingDto.setLstPlace(item.getLstPlace());
                    thingDto.setLstSbjt(item.getLstSbjt());
                    thingDtoList.add(thingDto);
                }
            }

            // Print the extracted list of ThingDto objects
            for (ThingDto thingDto : thingDtoList) {
                ObjectMapper ojm = new ObjectMapper();
                System.out.println(ojm.writeValueAsString(thingDto));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("thingDtoList = " + thingDtoList);
        return thingDtoList;
    }
}


