package com.myproject.accountrest.service.implementation;

import com.myproject.accountrest.util.interfaces.UserConverter;
import com.myproject.accountrest.service.interfaces.UserResetPass;
import com.myproject.accountrest.service.interfaces.UserAuthorization;
import com.myproject.accountrest.dto.ResetPassDTO;
import com.myproject.accountrest.dto.SignInDTO;
import com.myproject.accountrest.dto.SignUpDTO;
import com.myproject.accountrest.dto.UserDTO;
import com.myproject.accountrest.entity.RoleEntity;
import com.myproject.accountrest.entity.UserEntity;
import com.myproject.accountrest.exception.*;
import com.myproject.accountrest.repository.RoleRepository;
import com.myproject.accountrest.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

@Service
public class AuthorizationService implements UserAuthorization, UserResetPass {
    private static final String SUBJECT_TO_MAIL = "Here's the link to reset your password";
    private static final String CONTENT_FIRST_PART = """
            Hello,
            You have requested to reset your password.
            Click the link below to change your password:""";
    private static final String CONTENT_SECOND_PART = """
            Ignore this email if you do remember your password,
            or you have not made the request.""";
    private static final long EXPIRE_TOKEN_AFTER_MINUTES = 30;
    private final JavaMailSender mailSender;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepo;
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final UserConverter userConverter;
    @Value("${MAIL_USERNAME}")
    private String emailSupport;

    @Autowired
    public AuthorizationService(JavaMailSender mailSender, AuthenticationManager authenticationManager, RoleRepository roleRepo, UserRepository userRepo, PasswordEncoder passwordEncoder, UserConverter userConverter) {
        this.mailSender = mailSender;
        this.authenticationManager = authenticationManager;
        this.roleRepo = roleRepo;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.userConverter = userConverter;
    }


    @Override
    public UserDTO addNewUser(SignUpDTO signUpDto) throws UserDataAlreadyExistException {
        if (userRepo.findByUsername(signUpDto.getUsername()).isPresent()) {
            throw new UserDataAlreadyExistException(signUpDto.getUsername());
        } else if (userRepo.findByEmail(signUpDto.getEmail()).isPresent()) {
            throw new UserDataAlreadyExistException(signUpDto.getEmail());
        }
        UserEntity user = userConverter.convertToUserEntity(signUpDto, new UserEntity());
        RoleEntity roles = roleRepo.findByName("ROLE_USER").orElseThrow(RoleNotFoundException::new);
        user.setRoles(Collections.singleton(roles));
        userRepo.save(user);
        return userConverter.convertToUserDTO(user, new UserDTO());
    }


    @Override
    public ResetPassDTO resetPassword(String token, ResetPassDTO resetPassDTO) throws UserNotFoundException,
            ValuesAreNotEqualException {
        if (!resetPassDTO.getPassword().equals(resetPassDTO.getMatchPassword())) {
            throw new ValuesAreNotEqualException();
        }
        UserEntity user = (userRepo.findByToken(token).orElseThrow(UserNotFoundException::new));
        LocalDateTime tokenCreationDate = user.getTokenCreationDate();
        if (isTokenExpired(tokenCreationDate)) {
            throw new TokenExpiredException();
        }
        user.setPassword(passwordEncoder.encode(resetPassDTO.getPassword()));
        user.setToken(null);
        user.setTokenCreationDate(null);
        userRepo.save(user);
        return resetPassDTO;
    }

    @Override
    public void sendEmail(String email, HttpServletRequest request)
            throws MessagingException, UnsupportedEncodingException, UserNotFoundException {
        String userToken = setTokensByEmail(email);
        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl(GetSiteURL.getSiteURL(request))
                .path("/api/auth/reset-password")
                .queryParam("token", userToken)
                .build();
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(emailSupport, "Support");
        helper.setTo(email);
        helper.setSubject(SUBJECT_TO_MAIL);
        helper.setText(CONTENT_FIRST_PART + uriComponents + " " + CONTENT_SECOND_PART, true);
        mailSender.send(message);
    }

    @Override
    public SignInDTO signInUser(SignInDTO signInDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInDTO.getUsername(), signInDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return signInDTO;
    }

    @Override
    public UserEntity findUserByAuth() throws UserNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepo.findByUsername(auth.getName()).orElseThrow(UserNotFoundException::new);
    }

    private String generateToken() {
        StringBuilder token = new StringBuilder();
        return String.valueOf(token.append(UUID.randomUUID()));
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

    private String setTokensByEmail(String email) throws UserNotFoundException {
        UserEntity user = userRepo.findByEmail(email).orElseThrow(UserNotFoundException::new);
        user.setToken(generateToken());
        user.setTokenCreationDate(LocalDateTime.now());
        user = userRepo.save(user);
        return user.getToken();
    }

}
