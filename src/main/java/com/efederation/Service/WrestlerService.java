package com.efederation.Service;

import com.efederation.DTO.SubmitWrestlerRequest;
import com.efederation.DTO.SubmitWrestlerResponse;
import com.efederation.DTO.WrestlerResponse;
import com.efederation.Model.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface WrestlerService {
    List<WrestlerResponse> getWrestlers(User user);
    SubmitWrestlerResponse createWrestler(User user, SubmitWrestlerRequest request);

    void updateWrestlerJsonAttributes(long wrestlerId);

    void uploadImage(long wrestlerId, MultipartFile file) throws IOException;
}
