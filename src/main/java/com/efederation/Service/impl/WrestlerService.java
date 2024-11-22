package com.efederation.Service.impl;

import com.efederation.DTO.WrestlerRequest;
import com.efederation.Model.User;
import com.efederation.Model.Wrestler;

public interface WrestlerService {
    Wrestler createWrestler(User user, WrestlerRequest request);
}
