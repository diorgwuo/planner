package com.yuldashov.planner.services;

import com.yuldashov.planner.dto.EmailMessage;
import com.yuldashov.planner.dto.JwtRequest;
import com.yuldashov.planner.dto.JwtResponse;
import com.yuldashov.planner.dto.RegisterRequest;
import com.yuldashov.planner.exceptions.EmailExistException;
import com.yuldashov.planner.exceptions.UsernameExistException;
import com.yuldashov.planner.models.User;
import com.yuldashov.planner.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final KafkaProducerService kafkaProducerService;


    public UserService(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder, KafkaProducerService kafkaProducerService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.kafkaProducerService = kafkaProducerService;
    }

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

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Не найден по имени"));
    }
}
