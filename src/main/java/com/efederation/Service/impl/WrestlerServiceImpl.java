package com.efederation.Service.impl;

import com.efederation.DTO.SubmitCharacterRequest;
import com.efederation.DTO.SubmitCharacterResponse;
import com.efederation.DTO.WrestlerImageCreateRequest;
import com.efederation.DTO.WrestlerResponse;
import com.efederation.Enums.GenderIdentity;
import com.efederation.Model.User;
import com.efederation.Model.Wrestler;
import com.efederation.Model.WrestlerAttributes;
import com.efederation.Repository.WrestlerRepository;
import com.efederation.Service.WrestlerService;
import com.efederation.Utils.CommonUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class WrestlerServiceImpl implements WrestlerService {

    @Autowired
    WrestlerRepository wrestlerRepository;

    @Autowired
    CommonUtils commonUtils;

    @Transactional
    public List<WrestlerResponse> getWrestlers(User user) {
        List<Wrestler> userWrestlers = wrestlerRepository.findByUserId(user.getId());
        List<WrestlerResponse> wrestlerList = new ArrayList<>();
        userWrestlers.forEach(wrestler -> {
            WrestlerResponse wrestlerResponse = new WrestlerResponse(
                    wrestler.getWrestler_id(),
                    wrestler.getAnnounceName(),
                    wrestler.getWrestlerAttributes(),
                    commonUtils.getBase64Image(wrestler.getImageData()),
                    commonUtils.deriveWeightClassFromWeight(wrestler.getWrestlerAttributes().getWeight()).toString()
            );
            wrestlerList.add(wrestlerResponse);
        });
        return wrestlerList;
    }

    public SubmitCharacterResponse createWrestler(User user, SubmitCharacterRequest request) {
        Wrestler newWrestler = Wrestler.builder()
                .user(user)
                .announceName(request.getAnnounceName())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .wrestlerAttributes(new WrestlerAttributes(
                        request.getWeapon(),
                        request.getFinishingMove(),
                        GenderIdentity.valueOf(request.getGenderIdentity()),
                        request.getWeight(),
                        request.getAttributes().getStrength(),
                        request.getAttributes().getSpeed()
                        )
                ).build();
        wrestlerRepository.save(newWrestler);
        return new SubmitCharacterResponse("Successful", newWrestler.getAnnounceName(), newWrestler.getWrestler_id());
    }

    public void uploadImage(WrestlerImageCreateRequest request) {
        Optional<Wrestler> wrestlerOptional = wrestlerRepository.findById(request.getId());
            wrestlerOptional.map(wrestler -> {
                    byte[] byteArrayImage = Base64.getDecoder().decode(request.getImageBase64());
                    wrestler.setImageProperty(request.getType(), byteArrayImage);
                    wrestlerRepository.save(wrestler);
                return wrestler;
            });
    }

    public void updateWrestlerJsonAttributes(long wrestlerId) {
        Optional<Wrestler> wrestlerOptional = wrestlerRepository.findById(wrestlerId);
        wrestlerOptional.map(wrestler -> {
            wrestler.setWrestlerAttributes(new WrestlerAttributes("Hammer", "Punch", GenderIdentity.NONBINARY, 210.00, 0, 0));
            wrestlerRepository.save(wrestler);
            return wrestler;
        }
        );
    }
}
