package com.task_pay.task_pay.controllers;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.task_pay.task_pay.exceptions.ResourceNotFoundException;
import com.task_pay.task_pay.models.dtos.UserDto;
import com.task_pay.task_pay.models.entities.User;
import com.task_pay.task_pay.models.enums.Role;
import com.task_pay.task_pay.models.enums.UserType;
import com.task_pay.task_pay.repositories.UserRepository;
import com.task_pay.task_pay.services.AuthService;
import com.task_pay.task_pay.services.UserService;
import com.task_pay.task_pay.payloads.AuthenticationRequest;
import com.task_pay.task_pay.payloads.SendOtpRequest;
import com.task_pay.task_pay.payloads.ApiMessageResponse;
import com.task_pay.task_pay.payloads.AuthenticationResponse;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Random;

@RestController
@Validated
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Value("${googleClientId}")
    private String googleClientId;
    @Value("${newPassword}")
    private String newPassword;

    @PostMapping("/sendOTP")
    public  ResponseEntity<ApiMessageResponse> sendOTP(@Valid @RequestBody SendOtpRequest request) throws MessagingException, IOException {
        ApiMessageResponse response = authService.sendOTP(request);
        if (response.getStatus()==HttpStatus.NOT_FOUND){
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/verifyOTP/{OTP}")
    public ResponseEntity<AuthenticationResponse> verifyOTP(
            @Valid @RequestBody UserDto userDto,@PathVariable("OTP") String OTP
    ) {
        return new ResponseEntity<>(authService.verifyOTP(userDto,OTP), HttpStatus.OK);
    }
    @PostMapping("/signIn")
    public ResponseEntity<?> signIn(
            @Valid @RequestBody AuthenticationRequest request
    ) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new ResourceNotFoundException("user not found with this email and password !"));

        if(user.isBlock()){
            ApiMessageResponse response = ApiMessageResponse.
                    builder().message("Something went wrong !").success(false).status(HttpStatus.NOT_FOUND).build();
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }else{

            return new ResponseEntity<>(authService.signIn(request), HttpStatus.OK);
        }

    }

    @PostMapping("/googleLogin")
    ResponseEntity<?> googleLogin(@RequestBody Map<String, Object> data) throws IOException {
        String idToken = data.get("idToken").toString();
        String deviceId=data.get("deviceId").toString();
        NetHttpTransport netHttpTransport = new NetHttpTransport();
        JacksonFactory jacksonFactory = JacksonFactory.getDefaultInstance();
        GoogleIdTokenVerifier.Builder verifier = new GoogleIdTokenVerifier.Builder(netHttpTransport, jacksonFactory).setAudience(Collections.singleton(googleClientId));
        GoogleIdToken googleIdToken = GoogleIdToken.parse(verifier.getJsonFactory(), idToken);

        GoogleIdToken.Payload payload = googleIdToken.getPayload();

        String email=payload.getEmail();
        String name=(String) payload.get("name");
        String pic= (String) payload.get("photoUrl");
        User user = userRepository.findByEmail(email).orElseThrow(() -> null);

        if(user==null){
            String invitationCode = generateInvitationCode();
            User user1=User.builder()
                    .userType(UserType.BUYER)
                    .role(Role.USER)
                    .name(name)
                    .profilePic(pic)
                    .email(email)
                    .deviceId(deviceId)
//                    .mobileNumber()
                    .password(idToken)
                    .invitationCode(invitationCode)
//                    .about()
//                    .expertIn()
//                    .otpVerifiedAt(new Date())
                    .createdAt(new Date())
                    .isBlock(false)
                    .build();
            User saveUser = userRepository.save(user1);
            AuthenticationRequest build = AuthenticationRequest.builder().email(saveUser.getEmail()).password(saveUser.getPassword()).build();
            return this.signIn(build);
        }else{
            AuthenticationRequest build = AuthenticationRequest.builder().email(user.getEmail()).password(newPassword).build();
            return this.signIn(build);
        }
    }





    @PostMapping("/refreshToken")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authService.refreshToken(request, response);
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
