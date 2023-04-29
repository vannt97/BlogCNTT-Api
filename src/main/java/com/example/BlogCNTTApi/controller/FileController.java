package com.example.BlogCNTTApi.controller;

import com.example.BlogCNTTApi.payload.response.ResponseData;
import com.example.BlogCNTTApi.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class FileController {

    @Autowired
    FileService storageService;

    @PostMapping("files/create-datas")
    public ResponseEntity<?> createDataAuto(){
        return new ResponseEntity<>("Tao thanh cong", HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<ResponseData> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        ResponseData responseData = new ResponseData();
        String urlFileImage = storageService.store(file);
        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/files/")
                .path(urlFileImage)
                .toUriString();
        responseData.setSuccess(true);
        responseData.setData(fileDownloadUri);
        responseData.setStatus(HttpStatus.OK.value());
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @GetMapping("/download-all-image-from-server")
    public ResponseEntity<?> downloadAllImage(){
        storageService.downloadAllImageFromServer();
        return ResponseEntity.ok().body("Download thành công");
    }

//    @GetMapping("/files")
//    public ResponseEntity<List<FileInfo>> getListFiles() {
//        List<FileInfo> fileInfos = storageService.loadAll().map(path -> {
//            String filename = path.getFileName().toString();
//            String url = MvcUriComponentsBuilder
//                    .fromMethodName(FilesController.class, "getFilePNGFromServe", path.getFileName().toString()).build().toString();
//            return new FileInfo(filename, url);
//        }).collect(Collectors.toList());
//
//        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
//    }

    @ResponseBody
    @RequestMapping(value = "/files/{filename:.+}", method = RequestMethod.GET)
    public ResponseEntity<Resource> getFilePNGFromServe(@PathVariable String filename) {
        Resource file = storageService.load(filename);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
