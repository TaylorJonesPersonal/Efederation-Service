package com.efederation.Service.impl;

import com.efederation.DTO.SubmitCharacterRequest;
import com.efederation.DTO.SubmitCharacterResponse;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class WrestlerServiceImpl implements WrestlerService {

    @Autowired
    WrestlerRepository wrestlerRepository;

    @Transactional
    public List<WrestlerResponse> getWrestlers(User user) {
        List<Wrestler> userWrestlers = wrestlerRepository.findByUserId(user.getId());
        List<WrestlerResponse> wrestlerList = new ArrayList<>();
        userWrestlers.forEach(wrestler -> {
            WrestlerResponse wrestlerResponse = new WrestlerResponse(
                    wrestler.getWrestler_id(),
                    wrestler.getAnnounceName(),
                    wrestler.getWrestlerAttributes(),
                    CommonUtils.getBase64Image(wrestler.getImageData())
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
                        GenderIdentity.valueOf(request.getGenderIdentity())))
                .build();
        wrestlerRepository.save(newWrestler);
        return new SubmitCharacterResponse("Successful", newWrestler.getAnnounceName());
    }

    public void uploadImage(long wrestlerId,MultipartFile file) {
        Optional<Wrestler> wrestlerOptional = wrestlerRepository.findById(wrestlerId);
        wrestlerOptional.map(wrestler -> {
            try {
                wrestler.setImageData(file.getBytes());
                wrestlerRepository.save(wrestler);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return wrestler;
        });
    }

    public void updateWrestlerJsonAttributes(long wrestlerId) {
        Optional<Wrestler> wrestlerOptional = wrestlerRepository.findById(wrestlerId);
        wrestlerOptional.map(wrestler -> {
            wrestler.setWrestlerAttributes(new WrestlerAttributes("Hammer", "Punch", GenderIdentity.NONBINARY));
            wrestlerRepository.save(wrestler);
            return wrestler;
        }
        );
    }
}
