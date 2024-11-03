package com.loja_uniformes.admin.modules.authentication;

import com.loja_uniformes.admin.domain.entity.user.UserEntity;
import com.loja_uniformes.admin.domain.dto.request.AuthenticationRequestDto;
import com.loja_uniformes.admin.domain.dto.response.LoginResponseDto;
import com.loja_uniformes.admin.domain.dto.request.RegisterRequestDto;
import com.loja_uniformes.admin.repositories.UserRepository;
import com.loja_uniformes.admin.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    public AuthenticationController(AuthenticationManager authenticationManager, UserRepository userRepository, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationRequestDto data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((UserEntity) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDto(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterRequestDto data) {
        if (this.userRepository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        UserEntity newUser = new UserEntity(data.login(), encryptedPassword, data.role());

        this.userRepository.save(newUser);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/validate-token")
    public ResponseEntity validateToken(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(this.tokenService.validateToken(token));
    }
}
