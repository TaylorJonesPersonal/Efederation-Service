package com.efederation.Service.impl;

import com.efederation.DTO.NPCResponse;
import com.efederation.DTO.SubmitCharacterRequest;
import com.efederation.DTO.SubmitCharacterResponse;
import com.efederation.Enums.GenderIdentity;
import com.efederation.Model.NPC;
import com.efederation.Model.WrestlerAttributes;
import com.efederation.Repository.NPCRepository;
import com.efederation.Service.NPCService;
import com.efederation.Utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NPCServiceImpl implements NPCService {

    @Autowired
    NPCRepository npcRepository;

    public List<NPCResponse> getNPCs() {
        List<NPC> npcListRepository = npcRepository.findAll();
        List<NPCResponse> npcList = new ArrayList<>();
        npcListRepository.forEach(npc -> {
            NPCResponse npcResponse = NPCResponse.builder()
                    .wrestlerId(npc.getNpc_id())
                    .image(CommonUtils.getBase64Image(npc.getImageData()))
                    .attributes(npc.getWrestlerAttributes())
                    .announceName(npc.getAnnounceName())
                    .build();
            npcList.add(npcResponse);
        });
        return npcList;
    }

    public SubmitCharacterResponse createNPC(SubmitCharacterRequest request) {
        NPC newNpc = NPC.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .announceName(request.getAnnounceName())
                .wrestlerAttributes(new WrestlerAttributes(request.getWeapon(), request.getFinishingMove(), GenderIdentity.valueOf(request.getGenderIdentity())))
                .build();
        npcRepository.save(newNpc);
        return new SubmitCharacterResponse("Successful", newNpc.getAnnounceName());
    }

    public void updateNPCJsonAttributes(long npcId) {
        Optional<NPC> optionalNPC = npcRepository.findById(npcId);
        optionalNPC.map(npc -> {
            npc.setWrestlerAttributes(new WrestlerAttributes("Hammer", "Punch", GenderIdentity.NONBINARY));
            npcRepository.save(npc);
            return npc;
        });
    }

    public void uploadImage(long npcId, MultipartFile file) {
        Optional<NPC> optionalNPC = npcRepository.findById(npcId);
        optionalNPC.map(npc -> {
            try {
                npc.setImageData(file.getBytes());
                npcRepository.save(npc);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return npc;
        });
    }

}
