package com.efederation.Controller;

import com.efederation.DTO.ImageSetCreateRequest;
import com.efederation.DTO.ImageSetResponse;
import com.efederation.Exception.ApiError;
import com.efederation.Service.ImageSetService;
import com.efederation.Utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/imageset")
public class ImageSetController {

    @Autowired
    ImageSetService imageSetService;

    @Autowired
    CommonUtils commonUtils;

    @ExceptionHandler({IOException.class})
    @ResponseBody
    public ResponseEntity<Object> handleIOException(
            IOException ex,
            WebRequest webRequest) {
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), "An error occurred.");
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @GetMapping(value = "/v1")
    public ResponseEntity<List<ImageSetResponse>> getAllIdleImages() {
        return ResponseEntity.ok(imageSetService.getIdleListBase64());
    }

    @PostMapping(value = "/v1/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> createImageSet(@ModelAttribute ImageSetCreateRequest request) throws IOException {
        return ResponseEntity.ok(imageSetService.createImageSet(request));
    }

    @GetMapping(value = "/base64", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> getBase64(@RequestParam("image") MultipartFile file) {
        try {
            return ResponseEntity.ok(commonUtils.getBase64Image(file.getBytes()));
        } catch(IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
