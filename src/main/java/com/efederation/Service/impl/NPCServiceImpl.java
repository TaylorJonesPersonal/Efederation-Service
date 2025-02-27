package com.efederation.Service.impl;

import com.efederation.DTO.NPCResponse;
import com.efederation.DTO.SubmitCharacterRequest;
import com.efederation.DTO.SubmitCharacterResponse;
import com.efederation.Enums.GenderIdentity;
import com.efederation.Enums.ImageType;
import com.efederation.Model.ImageSet;
import com.efederation.Model.NPC;
import com.efederation.Model.WrestlerAttributes;
import com.efederation.Repository.ImageSetRepository;
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

    @Autowired
    ImageSetRepository imageSetRepository;

    @Autowired
    CommonUtils commonUtils;

    public List<NPCResponse> getNPCs() {
        List<NPC> npcListRepository = npcRepository.findAll();
        List<NPCResponse> npcList = new ArrayList<>();
        npcListRepository.forEach(npc -> {
            NPCResponse npcResponse = NPCResponse.builder()
                    .wrestlerId(npc.getNpc_id())
                    .image(commonUtils.getBase64Image(npc.getImageSet().getIdleImage()))
                    .attributes(npc.getWrestlerAttributes())
                    .announceName(npc.getAnnounceName())
                    .weightClass(commonUtils.deriveWeightClassFromWeight(npc.getWrestlerAttributes().getWeight()).toString())
                    .build();
            npcList.add(npcResponse);
        });
        return npcList;
    }

    public SubmitCharacterResponse createNPC(SubmitCharacterRequest request) {
        Optional<ImageSet> optionalImageSet = imageSetRepository.findById(request.getImageSetId());
        NPC newNpc = NPC.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .announceName(request.getAnnounceName())
                .wrestlerAttributes(new WrestlerAttributes(
                        request.getWeapon(),
                        request.getFinishingMove(),
                        GenderIdentity.valueOf(request.getGenderIdentity()),
                        request.getWeight(),
                        request.getAttributes().getStrength(),
                        request.getAttributes().getSpeed()
                ))
                .build();
        optionalImageSet.ifPresent(newNpc::setImageSet);
        npcRepository.save(newNpc);
        return new SubmitCharacterResponse("Successful", newNpc.getAnnounceName(), newNpc.getNpc_id());
    }

    public void updateNPCJsonAttributes(long npcId) {
        Optional<NPC> optionalNPC = npcRepository.findById(npcId);
        optionalNPC.map(npc -> {
            npc.setWrestlerAttributes(new WrestlerAttributes("Hammer", "Punch", GenderIdentity.NONBINARY, 220.00, 0, 0));
            npcRepository.save(npc);
            return npc;
        });
    }
}
