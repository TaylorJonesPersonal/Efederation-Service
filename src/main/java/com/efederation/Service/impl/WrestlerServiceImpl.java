package com.efederation.Service.impl;

import com.efederation.DTO.SubmitWrestlerRequest;
import com.efederation.DTO.SubmitWrestlerResponse;
import com.efederation.DTO.WrestlerResponse;
import com.efederation.Model.User;
import com.efederation.Model.Wrestler;
import com.efederation.Repository.WrestlerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WrestlerServiceImpl implements WrestlerService{

    @Autowired
    WrestlerRepository wrestlerRepository;

    public List<WrestlerResponse> getWrestlers(User user) {
        List<Wrestler> userWrestlers = wrestlerRepository.findByUserId(user.getId());
        List<WrestlerResponse> wrestlerList = new ArrayList<>();
        userWrestlers.forEach(wrestler -> {
            WrestlerResponse wrestlerResponse = new WrestlerResponse(
                    wrestler.getWrestler_id(),
                    wrestler.getAnnounceName(),
                    wrestler.getWeapon(),
                    wrestler.getFinishingMove());
            wrestlerList.add(wrestlerResponse);
        });
        return wrestlerList;
    }

    public SubmitWrestlerResponse createWrestler(User user, SubmitWrestlerRequest request) {
        Wrestler newWrestler = Wrestler.builder()
                .user(user)
                .announceName(request.getAnnounceName())
                .weapon(request.getWeapon())
                .finishingMove(request.getFinishingMove())
                .build();
        wrestlerRepository.save(newWrestler);
        return new SubmitWrestlerResponse("Successful", newWrestler.getAnnounceName());
    }
}
