package hr.fantasy.hnl.repository;

import hr.fantasy.hnl.domain.User;

public interface UserRepository {
    void save(User user);
    User findByUsername(String username);
}
