package com.waywalkers.kbook.web;

import com.waywalkers.kbook.constant.Path;
import com.waywalkers.kbook.dto.ResultDto;
import com.waywalkers.kbook.service.S3Service;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class FileController {
    private final S3Service s3Service;

    @PostMapping(path = Path.API_FILE_UPLOAD)
    @ApiOperation(
            value = "파일 업로드"
    )
    public ResultDto<String> uploadFile(@RequestPart(value = "file") MultipartFile multipartFile) throws IOException {
        return s3Service.upload(multipartFile);
    }

    @GetMapping(path = Path.API_FILE_DOWNLOAD)
    @ApiOperation(
            value = "파일 다운로드"
    )
    public ResponseEntity<byte[]> downloadFile(@RequestParam("resourcePath") String resourcePath) throws IOException {
        return s3Service.download(resourcePath);
    }
}
