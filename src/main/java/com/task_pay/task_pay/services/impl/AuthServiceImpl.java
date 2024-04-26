package com.task_pay.task_pay.services.impl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task_pay.task_pay.exceptions.ResourceNotFoundException;
import com.task_pay.task_pay.models.dtos.UserDto;
import com.task_pay.task_pay.models.entities.Token;
import com.task_pay.task_pay.models.entities.User;
import com.task_pay.task_pay.models.enums.TokenType;
import com.task_pay.task_pay.repositories.TokenRepository;
import com.task_pay.task_pay.repositories.UserRepository;
import com.task_pay.task_pay.security.JwtService;
import com.task_pay.task_pay.services.AuthService;
import com.task_pay.task_pay.utils.request.AuthenticationRequest;
import com.task_pay.task_pay.utils.response.ApiMessageResponse;
import com.task_pay.task_pay.utils.response.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;


@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;


    @Override
    public  ApiMessageResponse sendOTP(UserDto userDto) {
        Optional<User> userByNumber = userRepository.findByMobileNumber(userDto.getMobileNumber());
        if(userByNumber.isPresent()) {
            User user = userByNumber.get();
            if (Objects.equals(user.getMobileNumber(), userDto.getMobileNumber())) {
                return ApiMessageResponse.builder().
                        message("Mobile number already exist").
                        success(true).status(HttpStatus.NOT_FOUND).build();
            } else if (Objects.equals(user.getEmail(), userDto.getEmail())) {
                return ApiMessageResponse.builder().
                        message("Email id already exist").
                        success(true).status(HttpStatus.NOT_FOUND).build();
            }

        }
        return ApiMessageResponse.builder().
                message("OTP send successfully").
                success(true).status(HttpStatus.NOT_FOUND).build();

    }

    @Override
    public AuthenticationResponse verifyOTP(UserDto userDto,String OTP) {
        String invitationCode = generateInvitationCode();
     User user=User.builder()
                .userType("Buyer")
                .name(userDto.getName())
                .email(userDto.getEmail())
                .mobileNumber(userDto.getMobileNumber())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .invitationCode(invitationCode)
                .about(userDto.getAbout())
                .expertIn(userDto.getExpertIn())
             .optVerifiedAt(new Date())
                .createdAt(new Date())
                .isBlock(false)
                .build();
        User saveUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(saveUser,jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .userDto(mapper.map(user,UserDto.class))
                .build();
    }

    @Override
    public AuthenticationResponse signIn(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getMobileNumber(),
                        request.getPassword()
                )
        );
        User user = userRepository
                .findByMobileNumber(request.getMobileNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Mobile Number and password doesn't match"));
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user,jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .userDto(mapper.map(user,UserDto.class))
                .build();
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException, IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String mobileNumber;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        mobileNumber = jwtService.extractUsername(refreshToken);
        if (mobileNumber != null) {
            var user = this.userRepository.findByMobileNumber(mobileNumber)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getUserId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }


    public String generateInvitationCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int codeLength = 6;
        StringBuilder codeBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < codeLength; i++) {
            codeBuilder.append(characters.charAt(random.nextInt(characters.length())));
        }
        return codeBuilder.toString();
    }
}
