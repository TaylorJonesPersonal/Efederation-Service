package com.efederation.Utils;

import com.efederation.Constants.CommonConstants;
import com.efederation.Enums.WeightClass;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;

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

    public List<String> generateBase64ListFromResourceFile(String fileName, String delimiter) {
        List<String> base64List = new ArrayList<>();
        try(InputStream inputStream = CommonUtils.class.getClassLoader().getResourceAsStream(fileName); Scanner scanner = new Scanner(inputStream)) {
            scanner.useDelimiter(delimiter);
            while(scanner.hasNext()) {
                String phrase = scanner.next();
                base64List.add(phrase.trim());
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return base64List;
    }
}
