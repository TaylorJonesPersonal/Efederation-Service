package com.efederation.Utils;

import com.efederation.Constants.CommonConstants;
import com.efederation.Enums.WeightClass;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Base64;

@Component
@Data
public class CommonUtils {

    public String getBase64Image(byte[] imageData) {
        if(imageData != null) {
            return Base64.getEncoder().encodeToString(imageData);
        }
        return "";
    }

    public WeightClass deriveWeightClassFromWeight(double weight) {
        if(weight < CommonConstants.LIGHT_HEAVYWEIGHT_MIN_WEIGHT) {
            return WeightClass.CRUISERWEIGHT;
        } else if(weight < CommonConstants.HEAVYWEIGHT_MIN_WEIGHT) {
            return WeightClass.LIGHT_HEAVYWEIGHT;
        } else if(weight < CommonConstants.SUPERHEAVYWEIGHT_MIN_WEIGHT) {
            return WeightClass.HEAVYWEIGHT;
        } else {
            return WeightClass.SUPER_HEAVYWEIGHT;
        }
    }

    public LocalDateTime convertTimestampWithoutExplicitT(String timestamp) {
        return LocalDateTime.parse(timestamp.replace(" ", "T"));
    }
}
