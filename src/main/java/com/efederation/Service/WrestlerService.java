package com.efederation.Service;

import com.efederation.DTO.SubmitCharacterRequest;
import com.efederation.DTO.SubmitCharacterResponse;
import com.efederation.DTO.WrestlerResponse;
import com.efederation.Enums.ImageType;
import com.efederation.Model.User;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface WrestlerService {
    List<WrestlerResponse> getWrestlers(User user);
    SubmitCharacterResponse createWrestler(User user, SubmitCharacterRequest request);

    void updateWrestlerJsonAttributes(long wrestlerId);

    void uploadImage(long wrestlerId, MultipartFile file, ImageType uploadType);
}
