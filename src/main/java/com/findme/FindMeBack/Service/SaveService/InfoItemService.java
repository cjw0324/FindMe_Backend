package com.findme.FindMeBack.Service.SaveService;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.findme.FindMeBack.Controller.ApiDataSaveController.Dto.InfoDto.Item;
import com.findme.FindMeBack.Entity.InfoItem;
import com.findme.FindMeBack.Repository.InfoItemRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Optional;

import static com.findme.FindMeBack.Controller.GetItemController.CommonFunction.Converter.xmlToJson;

@Service
@Transactional
@RequiredArgsConstructor
public class InfoItemService {
    private final InfoItemRepository infoItemRepository;
    public void save(InfoItem infoItem) {
        infoItemRepository.save(infoItem);
    }

    public Optional<List<InfoItem>> findAll() {
        return Optional.of(infoItemRepository.findAll());
    }

    public Optional<InfoItem> findByAtcId(String atcId) {
        return Optional.of(infoItemRepository.findByAtcId(atcId).get());
    }

    public void delete(String atcid) {
        infoItemRepository.deleteByAtcId(atcid);
    }

    //////옮긴 시작

    @Value("${my.api.key}")
    public String apiKey;

    @Value("${my.admin.key}")
    private String key;
    @Transactional
    public void InfoSave(String ATC_ID) throws IOException {
        // 경찰청 API URL 생성
        String apiUrl;
        if ((ATC_ID.charAt(0)) == 'V') {
            apiUrl = "http://apis.data.go.kr/1320000/LosPtfundInfoInqireService/getPtLosfundDetailInfo";
        }
        else{
            System.out.println("AllInfoSaveController.InfoSave");
            apiUrl = "http://apis.data.go.kr/1320000/LosfundInfoInqireService/getLosfundDetailInfo";
        }
        StringBuilder urlBuilder = new StringBuilder(apiUrl);
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + apiKey);
        urlBuilder.append("&" + URLEncoder.encode("ATC_ID", "UTF-8") + "=" + URLEncoder.encode(ATC_ID, "UTF-8")); /*관리ID*/
        urlBuilder.append("&" + URLEncoder.encode("FD_SN", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*습득순번*/

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

        Item item = DataJsonToObject(xmlToJson(sb.toString()));



        if (item == null) {
            System.out.println("passing");
        }
        else{
            InfoItem infoItem = new InfoItem();
            if ((ATC_ID.charAt(0)) == 'V') {
                infoItem.setFndKeepOrgnSeNm(null); // V는 이게 없음
            } else{
                infoItem.setFndKeepOrgnSeNm(item.getFndKeepOrgnSeNm()); // F는 이게 있음
            }
            System.out.println("#1 : "+item.getAtcId());
            infoItem.setAtcId(item.getAtcId());
            System.out.println("#2 : "+item.getCsteSteNm());
            infoItem.setCsteSteNm(item.getCsteSteNm());
            infoItem.setFdFilePathImg(item.getFdFilePathImg());
            infoItem.setFdHor(item.getFdHor());
            infoItem.setFdPlace(item.getFdPlace());
            infoItem.setFdSn(item.getFdSn());
            infoItem.setFdYmd(item.getFdYmd());
            infoItem.setOrgId(item.getOrgId());
            infoItem.setOrgNm(item.getOrgNm());
            infoItem.setPrdtClNm(item.getPrdtClNm());
            infoItem.setTel(item.getTel());
            infoItem.setUniq(item.getUniq());
            infoItem.setDepPlace(item.getDepPlace());
            infoItem.setFdPrdtNm(item.getFdPrdtNm());
            infoItemRepository.save(infoItem);  //db
            System.out.println("save success");
        }

    }
    // JSON 문자열을 객체로 변환하는 메서드
    public static Item DataJsonToObject(String jsonResponse) {
        System.out.println("jsonResponse = " + jsonResponse);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            // Accessing 'body' node and checking if it actually contains fields
            JsonNode bodyNode = rootNode.path("response").path("body");
            if (!bodyNode.fields().hasNext()) { // Checking if 'body' has no elements
                return null;
            }

            // Accessing 'item' node directly from the 'body'
            JsonNode itemsNode = bodyNode.path("item");
            if (itemsNode.isMissingNode() || itemsNode.isNull()) {
                return null;
            }

            // If 'item' node exists, convert it to Item class instance
            Item singleItem = objectMapper.treeToValue(itemsNode, Item.class);
            System.out.println("AllInfoSaveController.DataJsonToObject");
            return singleItem;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    //////옮긴 마지막 위치

}
