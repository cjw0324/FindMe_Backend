package com.findme.FindMeBack.Controller.GetItemController.CommonFunction;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.findme.FindMeBack.Controller.GetItemController.Police.Dto.PoliceDateDto.LostItemsResponse;
import com.findme.FindMeBack.Controller.GetItemController.Police.Dto.PoliceLocationDto.Item;
import com.findme.FindMeBack.Controller.GetItemController.Police.Dto.PoliceLocationDto.SearchItemsWithLc;
import org.json.JSONObject;
import org.json.XML;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Converter {
//     XML을 JSON으로 변환하는 메서드
    public static String xmlToJson(String str) {
        try {
            if (str == null) {
                return null; // 문자열이 null이면 null을 반환
            }

            String xml = str;
            JSONObject jObject = XML.toJSONObject(xml);
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            Object json = mapper.readValue(jObject.toString(), Object.class);
            return mapper.writeValueAsString(json);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }






    // JSON 문자열을 객체로 변환하는 메서드
    private List<Item> jsonToObject(String jsonInput, SearchItemsWithLc items) throws JsonProcessingException {
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
