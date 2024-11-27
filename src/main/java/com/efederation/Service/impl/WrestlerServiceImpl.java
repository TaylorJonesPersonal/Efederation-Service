package com.efederation.Service.impl;

import com.efederation.DTO.SubmitWrestlerRequest;
import com.efederation.DTO.SubmitWrestlerResponse;
import com.efederation.DTO.WrestlerResponse;
import com.efederation.Model.User;
import com.efederation.Model.Wrestler;
import com.efederation.Model.WrestlerAttributes;
import com.efederation.Repository.WrestlerRepository;
import com.efederation.Service.WrestlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WrestlerServiceImpl implements WrestlerService {

    @Autowired
    WrestlerRepository wrestlerRepository;

    public List<WrestlerResponse> getWrestlers(User user) {
        List<Wrestler> userWrestlers = wrestlerRepository.findByUserId(user.getId());
        List<WrestlerResponse> wrestlerList = new ArrayList<>();
        userWrestlers.forEach(wrestler -> {
            WrestlerResponse wrestlerResponse = new WrestlerResponse(
                    wrestler.getWrestler_id(),
                    wrestler.getAnnounceName(),
                    wrestler.getWrestlerAttributes().getWeapon(),
                    wrestler.getWrestlerAttributes().getFinishingMove());
            wrestlerList.add(wrestlerResponse);
        });
        return wrestlerList;
    }

    public SubmitWrestlerResponse createWrestler(User user, SubmitWrestlerRequest request) {
        Wrestler newWrestler = Wrestler.builder()
                .user(user)
                .announceName(request.getAnnounceName())
                .build();
        wrestlerRepository.save(newWrestler);
        return new SubmitWrestlerResponse("Successful", newWrestler.getAnnounceName());
    }

    public void updateWrestlerJsonAttributes(long wrestlerId) {
        Optional<Wrestler> wrestlerOptional = wrestlerRepository.findById(wrestlerId);
        wrestlerOptional.map(wrestler -> {
            wrestler.setWrestlerAttributes(new WrestlerAttributes("Hammer", "Punch"));
            wrestlerRepository.save(wrestler);
            return wrestler;
        }
        );
    }
}
