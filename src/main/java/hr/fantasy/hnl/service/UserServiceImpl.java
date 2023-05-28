package hr.fantasy.hnl.service;

import hr.fantasy.hnl.domain.User;
import hr.fantasy.hnl.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class UserServiceImpl implements UserService, Serializable {
    private static final long serialVersionUID = 1L;

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void register(User user) {
        String password = user.getPassword();
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        user.setPassword(hashedPassword);
        userRepository.save(user);
    }

    @Override
    public boolean authenticate(User user) {
        User storedUser = userRepository.findByUsername(user.getUsername());
        return storedUser != null && BCrypt.checkpw(user.getPassword(), storedUser.getPassword());
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
