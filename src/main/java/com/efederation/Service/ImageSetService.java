package com.efederation.Service;

import com.efederation.DTO.ImageSetCreateRequest;

import java.io.IOException;
import java.util.List;

public interface ImageSetService {
    String createImageSet(ImageSetCreateRequest request) throws IOException;
    List<String> getIdleListBase64();
}
