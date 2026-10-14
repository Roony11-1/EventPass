package com.eventpass.bffauth.service;

import com.eventpass.bffauth.config.JwtUtil;
import com.eventpass.bffauth.dto.LoginRequest;
import com.eventpass.bffauth.dto.LoginResponse;
import com.eventpass.bffauth.dto.RegistroRequest;
import com.eventpass.bffauth.dto.UsuarioResponse;
import com.eventpass.bffauth.model.Rol;
import com.eventpass.bffauth.model.Usuario;
import com.eventpass.bffauth.repository.RolRepository;
import com.eventpass.bffauth.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public UsuarioResponse registrar(RegistroRequest request) {
        if (usuarioRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new EmailYaRegistradoException("Ya existe un usuario registrado con ese email");
        }

        Rol rol = rolRepository.findByNombreIgnoreCase(request.getRol())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Rol invalido, use COMPRADOR o STAFF: " + request.getRol()));

        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setRol(rol);

        Usuario guardado = usuarioRepository.save(usuario);

        return new UsuarioResponse(guardado.getId(), guardado.getNombre(),
                guardado.getEmail(), guardado.getRol().getNombre());
    }

    public LoginResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmailIgnoreCase(request.getEmail())
                .orElseThrow(() -> new CredencialesInvalidasException("Email o contrasena incorrectos"));

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new CredencialesInvalidasException("Email o contrasena incorrectos");
        }

        String token = jwtUtil.generarToken(usuario.getId(), usuario.getEmail(), usuario.getRol().getNombre());
        return new LoginResponse(token, usuario.getRol().getNombre());
    }

    public UsuarioResponse buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNoEncontradoException("No existe un usuario con id " + id));

        return new UsuarioResponse(usuario.getId(), usuario.getNombre(),
                usuario.getEmail(), usuario.getRol().getNombre());
    }
}