package com.taskflow.auth.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskflow.auth.dto.AuthResponse;
import com.taskflow.auth.dto.LoginRequest;
import com.taskflow.auth.dto.RegisterRequest;
import com.taskflow.auth.dto.UserResponse;
import com.taskflow.auth.service.AuthService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request, BindingResult result) {
        // 400 Bad Request si los datos no cumplen las validaciones (@NotBlank, @Email, etc.)
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapValidationErrors(result));
        }

        try {
            AuthResponse response = authService.login(request);
            // 200 OK si las credenciales son válidas
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            // 401 Unauthorized si la contraseña o email son incorrectos
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Credenciales incorrectas: email o contraseña inválidos"));
        } catch (Exception e) {
            // 500 Internal Server Error para otros errores inesperados
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error interno al iniciar sesión: " + e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request, BindingResult result) {
        // 400 Bad Request si faltan campos o no cumplen los requisitos
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapValidationErrors(result));
        }

        try {
            AuthResponse response = authService.register(request);
            // 201 Created si el usuario se registró con éxito
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            // 400 Bad Request si el argumento es inválido
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            // 409 Conflict o 400 si el email ya existe o falla el guardado
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "No se pudo registrar el usuario: " + e.getMessage()));
        }
    }

    @PostMapping("/me")
    public ResponseEntity<?> getCurrentUser(@RequestBody(required = false) String email, Authentication authentication) {
        // Obtenemos el email del token si no viene en el body, o usamos el enviado
        String targetEmail = (email != null && !email.isBlank()) ? email.trim() : (authentication != null ? authentication.getName() : null);

        if (targetEmail == null || targetEmail.isBlank()) {
            // 400 Bad Request si no se proporcionó ni se encontró identidad
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Debe proporcionar un email o estar autenticado"));
        }

        try {
            UserResponse userResponse = authService.getCurrentUser(targetEmail);
            // 200 OK con los datos del usuario
            return ResponseEntity.ok(userResponse);
        } catch (Exception e) {
            // 404 Not Found si el usuario no existe en la BD
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Usuario no encontrado con el email: " + targetEmail));
        }
    }

    // Método auxiliar para recopilar los errores de @Valid
    private Map<String, String> mapValidationErrors(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : result.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return errors;
    }
}