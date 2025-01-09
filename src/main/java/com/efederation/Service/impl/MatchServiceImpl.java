package com.efederation.Service.impl;

import com.efederation.Model.Character;
import com.efederation.Model.Match;
import com.efederation.Repository.MatchRepository;
import com.efederation.Service.MatchService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MatchServiceImpl implements MatchService {

    @Autowired
    MatchRepository matchRepository;

    @Transactional
    public void createMatch(Match newMatch) {
        matchRepository.save(newMatch);
    }

    public Character defineWinner(List<Character> characters) {
        return characters.stream().max(Comparator.comparingInt(Character::fight)).orElse(characters.get(0));
    }
}
