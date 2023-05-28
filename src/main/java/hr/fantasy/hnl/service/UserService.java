package hr.fantasy.hnl.service;

import hr.fantasy.hnl.domain.User;

public interface UserService {
    void register(User user);
    boolean authenticate(User user);
    User findByUsername(String username);
}
