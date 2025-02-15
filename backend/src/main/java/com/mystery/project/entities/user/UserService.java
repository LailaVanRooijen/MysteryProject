package com.mystery.project.entities.user;

import com.mystery.project.exception.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void deleteUser(User userAuth, UUID id) {
        if (!userAuth.getRole().equals(Role.ADMIN)) {
            throw new ForbiddenException("You do not have access.");
        }

        userRepository.deleteById(id);
    }
}
