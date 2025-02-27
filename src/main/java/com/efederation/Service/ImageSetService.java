package com.efederation.Service;

import com.efederation.DTO.ImageSetCreateRequest;

import java.io.IOException;

public interface ImageSetService {
    String createImageSet(ImageSetCreateRequest request) throws IOException;
}
