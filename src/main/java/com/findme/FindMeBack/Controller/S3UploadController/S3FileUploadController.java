package com.findme.FindMeBack.Controller.S3UploadController;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.findme.FindMeBack.Entity.PoliceDateItem;
import com.findme.FindMeBack.Entity.PortalDateItem;
import com.findme.FindMeBack.Service.PoliceDateItemService;
import com.findme.FindMeBack.Service.PortalDateItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
@RestController
@RequestMapping("/img/upload")
@RequiredArgsConstructor
public class S3FileUploadController {
    @Autowired
    private AmazonS3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final PoliceDateItemService policeDateItemService;
    private final PortalDateItemService portalDateItemService;


    public void uploadFileFromUrl(String stringUrl) throws IOException {
        URL url = new URL(stringUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        InputStream inputStream = connection.getInputStream();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(connection.getContentLengthLong());
        metadata.setContentType(connection.getContentType());

        String fileName = getFileNameFromURL(String.valueOf(url));
        s3Client.putObject(new PutObjectRequest(bucket, fileName, inputStream, metadata));  //S3 bucket에 저장.
        System.out.println(fileName);

        inputStream.close();
        connection.disconnect();
    }

    public static String getFileNameFromURL(String url) {
        String[] array = url.split("/");
        return array[6] +"/"+ array[7];    //S3 이미지 저장 시 디렉토리 구조 유지를 위한 renaming.
    }

    @PostMapping
    public void GetDateDbDataForSaveImgToS3() throws IOException {
        List<PoliceDateItem> policeDateItemList = policeDateItemService.findAll().get();
        List<PortalDateItem> portalDateItemList = portalDateItemService.findAll().get();
        for (PoliceDateItem policeDateItem : policeDateItemList) {
            String originUrl = policeDateItem.getFdFilePathImg();
            if((originUrl).compareTo("https://www.lost112.go.kr/lostnfs/images/sub/img02_no_img.gif") != 0){  //이미지 없는것 제외.
                uploadFileFromUrl(originUrl);
            }
        }
        for (PortalDateItem portalDateItem : portalDateItemList) {
            String originUrl = portalDateItem.getFdFilePathImg();
            if((originUrl).compareTo("https://www.lost112.go.kr/lostnfs/images/sub/img02_no_img.gif") != 0){  //이미지 없는것 제외.
                uploadFileFromUrl(originUrl);
            }
        }
    }



}
