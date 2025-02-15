package com.mystery.project.entities.user;

import com.mystery.project.entities.user.exceptions.InvalidUUIDException;
import com.mystery.project.mainconfiguration.Routes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(Routes.USERS)
@CrossOrigin(origins = "${spring.frontend-client}")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @DeleteMapping("/{userId}")
    public void deleteUser(@AuthenticationPrincipal User userAuth, @PathVariable String userId) {
        try {
            UUID id = UUID.fromString(userId);
            userService.deleteUser(userAuth, id);
        } catch (IllegalArgumentException e) {
            throw new InvalidUUIDException("Invalid ID format");
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidUUIDException.class)
    public String invalidUUIDException(InvalidUUIDException e) {
        return e.getMessage();
    }
}
