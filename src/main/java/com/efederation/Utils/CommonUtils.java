package com.efederation.Utils;

import com.efederation.Constants.CommonConstants;
import com.efederation.Enums.WeightClass;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
@Data
public class CommonUtils {

    @Autowired
    CommonConstants commonConstants;

    public String getBase64Image(byte[] imageData) {
        if(imageData != null) {
            return Base64.getEncoder().encodeToString(imageData);
        }
        return "";
    }

    public WeightClass deriveWeightClassFromWeight(double weight) {
        if(weight < commonConstants.getLIGHT_HEAVYWEIGHT_MIN_WEIGHT()) {
            return WeightClass.CRUISERWEIGHT;
        } else if(weight < commonConstants.getHEAVYWEIGHT_MIN_WEIGHT()) {
            return WeightClass.LIGHT_HEAVYWEIGHT;
        } else if(weight < commonConstants.getSUPERHEAVYWEIGHT_MIN_WEIGHT()) {
            return WeightClass.HEAVYWEIGHT;
        } else {
            return WeightClass.SUPER_HEAVYWEIGHT;
        }
    }
}
