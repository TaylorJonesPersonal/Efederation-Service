package com.efederation.Service;

import com.efederation.DTO.SubmitWrestlerRequest;
import com.efederation.DTO.SubmitWrestlerResponse;
import com.efederation.DTO.WrestlerResponse;
import com.efederation.Model.User;

import java.util.List;

public interface WrestlerService {
    List<WrestlerResponse> getWrestlers(User user);
    SubmitWrestlerResponse createWrestler(User user, SubmitWrestlerRequest request);

    void updateWrestlerJsonAttributes(long wrestlerId);
}
