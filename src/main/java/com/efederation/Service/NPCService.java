package com.efederation.Service;

import com.efederation.DTO.NPCResponse;
import com.efederation.DTO.SubmitCharacterRequest;
import com.efederation.DTO.SubmitCharacterResponse;
import com.efederation.Enums.ImageType;
import com.efederation.Exception.ImageSetNotFoundException;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface NPCService {
    List<NPCResponse> getNPCs();
    SubmitCharacterResponse createNPC(SubmitCharacterRequest request) throws ImageSetNotFoundException;

    void updateNPCJsonAttributes(long npcId);

    //void uploadImage(long npcId, MultipartFile file, ImageType uploadType);
}
