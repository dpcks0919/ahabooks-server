package com.waywalkers.kbook.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.waywalkers.kbook.dto.ResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional
@Service
public class S3Service {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final AmazonS3 amazonS3;

    public ResultDto upload(MultipartFile multipartFile) throws IOException {
        String s3FileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();

        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentLength(multipartFile.getInputStream().available());
        amazonS3.putObject(bucket, s3FileName, multipartFile.getInputStream(), objMeta);

        return ResultDto.builder()
                .httpStatus(HttpStatus.OK)
                .data(amazonS3.getUrl(bucket, s3FileName).toString())
                .build();
    }

    public ResponseEntity<byte[]> download(String resourcePath) throws IOException {
        validateFileExistsAtUrl(resourcePath);
        S3Object s3Object = amazonS3.getObject(bucket, resourcePath);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        byte[] bytes = IOUtils.toByteArray(inputStream);

        String fileName = URLEncoder.encode(resourcePath, "UTF-8").replaceAll("\\+", "%20");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.setContentLength(bytes.length);
        httpHeaders.setContentDispositionFormData("attachment", fileName);
        return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
    }

    public void deleteFile(String resourcePath){
        DeleteObjectRequest request = new DeleteObjectRequest(bucket, resourcePath);
        amazonS3.deleteObject(request);
    }

    private void validateFileExistsAtUrl(String resourcePath) throws FileNotFoundException {
        if (!amazonS3.doesObjectExist(bucket, resourcePath)) {
            throw new FileNotFoundException();
        }
    }
}