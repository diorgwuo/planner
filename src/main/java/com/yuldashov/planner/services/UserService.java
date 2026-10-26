package com.yuldashov.planner.services;

import com.yuldashov.planner.dto.auth.UserInfoResponse;
import com.yuldashov.planner.dto.email.EmailMessage;
import com.yuldashov.planner.exceptions.EmailExistException;
import com.yuldashov.planner.exceptions.UsernameExistException;
import com.yuldashov.planner.models.User;
import com.yuldashov.planner.repository.UserRepository;
import com.yuldashov.planner.utils.ValidateAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final KafkaProducerService kafkaProducerService;

    @Transactional
    public String register(String username, String password, String email) {
        if (userRepository.existsByUsername(username)) {
            throw new UsernameExistException("Такой username уже существует");
        }
        if (userRepository.existsByEmail(email)) {
            throw new EmailExistException("Такой email уже существует");
        }

        String encodedPassword = passwordEncoder.encode(password);
        User savedUser = new User(username,encodedPassword,email);
        userRepository.save(savedUser);
        String token = jwtService.generateAccessToken(username);
        EmailMessage message = new EmailMessage(
                email,
                "Добро пожаловать в планировщик",
                "Хеллоу браза"
        );
        kafkaProducerService.sendEmail(message);
        return token;
    }

    public UserInfoResponse findUserByUsername(Authentication auth) {
        User user = ValidateAuth.validate(auth);
        return new UserInfoResponse(user.getId(),user.getEmail());
    }
}
