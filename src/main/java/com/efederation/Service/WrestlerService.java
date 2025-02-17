package com.efederation.Service;

import com.efederation.DTO.SubmitCharacterRequest;
import com.efederation.DTO.SubmitCharacterResponse;
import com.efederation.DTO.WrestlerImageCreateRequest;
import com.efederation.DTO.WrestlerResponse;
import com.efederation.Model.User;
import java.util.List;

public interface WrestlerService {
    List<WrestlerResponse> getWrestlers(User user);
    SubmitCharacterResponse createWrestler(User user, SubmitCharacterRequest request);

    void updateWrestlerJsonAttributes(long wrestlerId);

    void uploadImage(WrestlerImageCreateRequest request);
}
