package com.efederation.Service.impl;

import com.efederation.DTO.WrestlerRequest;
import com.efederation.Model.User;
import com.efederation.Model.Wrestler;
import com.efederation.Repository.WrestlerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WrestlerServiceImpl implements WrestlerService{

    @Autowired
    WrestlerRepository wrestlerRepository;

    public Wrestler createWrestler(User user, WrestlerRequest request) {
        Wrestler newWrestler = Wrestler.builder()
                .user(user)
                .announceName(request.getAnnounceName())
                .weapon(request.getWeapon())
                .finishingMove(request.getFinishingMove())
                .build();
        return wrestlerRepository.save(newWrestler);
    }
}
