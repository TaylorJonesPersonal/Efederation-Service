package com.efederation.Service;

import com.efederation.DTO.ImageSetCreateRequest;
import com.efederation.DTO.ImageSetResponse;

import java.io.IOException;
import java.util.List;

public interface ImageSetService {
    String createImageSet(ImageSetCreateRequest request) throws IOException;
    List<ImageSetResponse> getIdleListBase64();
}
