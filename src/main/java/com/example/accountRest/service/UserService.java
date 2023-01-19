package com.example.accountRest.service;

import com.example.accountRest.dto.SignUpDTO;
import com.example.accountRest.entity.RoleEntity;
import com.example.accountRest.entity.UserEntity;
import com.example.accountRest.exception.UserNotFoundException;
import com.example.accountRest.repository.RoleRepository;
import com.example.accountRest.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private static final long EXPIRE_TOKEN_AFTER_MINUTES = 30;
    @Autowired
    private RoleRepository roleRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private JavaMailSender mailSender;

    public void addNewUser(SignUpDTO signUpDto) {
        UserEntity user = new UserEntity();
        user.setName(signUpDto.getName());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        RoleEntity roles = roleRepo.findByName("USER").orElse(null);
        user.setRoles(Collections.singleton(roles));
        userRepo.save(user);
    }
@SneakyThrows
    public String forgotPassword(String email) {
        Boolean booToken = userRepo.existsByEmail(email);
        if (!booToken) {
            return "Invalid email";
        }
        UserEntity user = userRepo.findByEmail(email).orElse(null);
        if(user == null){
            throw new UserNotFoundException("user not found");
        }
        user.setToken(generateToken());
        user.setTokenCreationDate(LocalDateTime.now());
        user = userRepo.save(user);
        return user.getToken();
    }
@SneakyThrows
    public String resetPassword(String token, String password) {
        Optional<UserEntity> userOptional = (userRepo.findByToken(token));
        if (userOptional.isEmpty()) {
            return "invalid token!";
        }
        LocalDateTime tokenCreationDate = userOptional.get().getTokenCreationDate();
        if (isTokenExpired(tokenCreationDate)) {
            return "Token expired.";
        }
        UserEntity user = (userRepo.findByToken(token).orElse(null));
        if(user == null){
            throw new UserNotFoundException("User not found");
        }
        user.setPassword(passwordEncoder.encode(password));
        user.setToken(null);
        user.setTokenCreationDate(null);
        userRepo.save(user);
        return "Your password successfully updated";
    }

    private String generateToken() {
        StringBuilder token = new StringBuilder();
        return String.valueOf(token.append(UUID.randomUUID())
                .append(UUID.randomUUID().toString().toString()));

    }

    public void sendEmail(String recipientEmail, String link)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("Brelok002@gmail.com", "Support");
        helper.setTo(recipientEmail);

        String subject = "Here's the link to reset your password";

        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p>\"" + link + "\"</p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);
    }
    private boolean isTokenExpired(final LocalDateTime tokenCreationDate) {

        LocalDateTime now = LocalDateTime.now();
        Duration diff = Duration.between(tokenCreationDate, now);

        return diff.toMinutes() >= EXPIRE_TOKEN_AFTER_MINUTES;
    }
}
