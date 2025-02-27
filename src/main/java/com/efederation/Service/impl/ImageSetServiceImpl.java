package com.efederation.Service.impl;

import com.efederation.Constants.CommonConstants;
import com.efederation.DTO.ImageSetCreateRequest;
import com.efederation.Model.ImageSet;
import com.efederation.Repository.ImageSetRepository;
import com.efederation.Service.ImageSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ImageSetServiceImpl implements ImageSetService {

    @Autowired
    ImageSetRepository imageSetRepository;
    public String createImageSet(ImageSetCreateRequest request) throws IOException {
        ImageSet newImageSet = ImageSet
                .builder()
                .setName(request.getName())
                .idleImage(request.getIdle().getBytes())
                .defeatedImage(request.getDefeated().getBytes())
                .attackFrame1(request.getAttackOne().getBytes())
                .attackFrame2(request.getAttackTwo().getBytes())
                .attackFrame3(request.getAttackThree().getBytes())
                .attackFrame4(request.getAttackFour().getBytes())
                .build();
        imageSetRepository.save(newImageSet);
        return CommonConstants.success;
    }
}
