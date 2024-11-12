package com.efederation.Service;

import com.efederation.Model.User;

public interface UserService {
    User saveUser(User user);
    void enableAccountByEmail(String email);
}
