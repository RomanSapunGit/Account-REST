package com.example.accountrest.service;

import com.example.accountrest.accountinterface.UserAuth;
import com.example.accountrest.dto.ResetPassDTO;
import com.example.accountrest.dto.SignInDTO;
import com.example.accountrest.dto.SignUpDTO;
import com.example.accountrest.entity.RoleEntity;
import com.example.accountrest.entity.UserEntity;
import com.example.accountrest.exception.RoleNotFoundException;
import com.example.accountrest.exception.UserNotFoundException;
import com.example.accountrest.repository.RoleRepository;
import com.example.accountrest.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

@Service
public class AuthService implements UserAuth {
    private static final long EXPIRE_TOKEN_AFTER_MINUTES = 30;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RoleRepository roleRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void addNewUser(SignUpDTO signUpDto) throws RoleNotFoundException {
        UserEntity user = new UserEntity();
        user.setName(signUpDto.getName());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        RoleEntity roles = roleRepo.findByName("ROLE_USER").orElseThrow(RoleNotFoundException::new);
        user.setRoles(Collections.singleton(roles));
        userRepo.save(user);
    }

    @Override
    public String setTokensByEmail(String email) throws UserNotFoundException {
        UserEntity user = userRepo.findByEmail(email).orElseThrow(UserNotFoundException::new);
        user.setToken(generateToken());
        user.setTokenCreationDate(LocalDateTime.now());
        user = userRepo.save(user);
        return user.getToken();
    }

    @Override
    public String resetPassword(String token, ResetPassDTO resetPassDTO) throws UserNotFoundException {
        if (resetPassDTO.getPassword().equals(resetPassDTO.getMatchPassword())) {
            return "passwords don't match";
        }
        UserEntity user = (userRepo.findByToken(token).orElseThrow(UserNotFoundException::new));
        LocalDateTime tokenCreationDate = user.getTokenCreationDate();
        if (isTokenExpired(tokenCreationDate)) {
            return "Token expired.";
        }
        user.setPassword(passwordEncoder.encode(resetPassDTO.getPassword()));
        user.setToken(null);
        user.setTokenCreationDate(null);
        userRepo.save(user);
        return "Your password successfully updated";
    }

    @Override
    public String sendEmail(String email, HttpServletRequest request)
            throws MessagingException, UnsupportedEncodingException, UserNotFoundException {
        String userToken = setTokensByEmail(email);
        String link = GetSiteURL.getSiteURL(request) + "/api/auth/reset-password?token=" + userToken;
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("Brelok002@gmail.com", "Support");
        helper.setTo(email);
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
        return "We have sent your token on email";
    }

    @Override
    public String signInUser(SignInDTO signInDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInDTO.getUsername(), signInDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "User signed-in successfully!";
    }


    private String generateToken() {
        StringBuilder token = new StringBuilder();
        return String.valueOf(token.append(UUID.randomUUID())
                .append(UUID.randomUUID().toString().toString()));
    }

    private static class GetSiteURL {
        public static String getSiteURL(HttpServletRequest request) {
            String siteURL = request.getRequestURL().toString();
            return siteURL.replace(request.getServletPath(), "");
        }
    }

    private boolean isTokenExpired(final LocalDateTime tokenCreationDate) {

        LocalDateTime now = LocalDateTime.now();
        Duration diff = Duration.between(tokenCreationDate, now);

        return diff.toMinutes() >= EXPIRE_TOKEN_AFTER_MINUTES;
    }

}
