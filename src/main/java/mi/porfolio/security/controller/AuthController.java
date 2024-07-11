package mi.porfolio.security.controller;


import java.util.HashSet;
import java.util.Set;

import jakarta.validation.Valid;

import mi.porfolio.security.dto.JwtDto;
import mi.porfolio.security.dto.LoginUsuario;
import mi.porfolio.security.dto.NuevoUsuario;
import mi.porfolio.security.entity.Rol;
import mi.porfolio.security.entity.Usuario;
import mi.porfolio.security.enums.RolNombre;
import mi.porfolio.security.jwt.JwtProvider;
import mi.porfolio.security.service.RolService;
import mi.porfolio.security.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    RolService rolService;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/nuevo")
    public ResponseEntity<?> nuevo(@Valid @RequestBody NuevoUsuario nuevoUsuario, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal puestos o email inválido"), HttpStatus.BAD_REQUEST);
        if(usuarioService.existsByNombreUsuario(nuevoUsuario.getNombreUsuario()))
            return new ResponseEntity(new Mensaje("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        if(usuarioService.existsByEmail(nuevoUsuario.getEmail()))
            return new ResponseEntity(new Mensaje("ese email ya existe"), HttpStatus.BAD_REQUEST);
        if (nuevoUsuario.getNombreUsuario() == null || nuevoUsuario.getNombreUsuario().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El nombre de usuario es requerido");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(nuevoUsuario.getNombre());
        usuario.setNombreUsuario(nuevoUsuario.getNombreUsuario());
        usuario.setEmail(nuevoUsuario.getEmail());
        usuario.setPassword(passwordEncoder.encode(nuevoUsuario.getPassword()));
        Set<Rol> roles = new HashSet<>();
        Rol rolUser = rolService.getByRolNombre(RolNombre.ROLE_USER).orElseGet(() -> {
            Rol newRol = new Rol(RolNombre.ROLE_USER);
            rolService.save(newRol);
            return newRol;
        });
        usuarioService.save(usuario);
        roles.add(rolUser);
        if(nuevoUsuario.getRoles().contains("admin")) {
            Rol rolAdmin = rolService.getByRolNombre(RolNombre.ROLE_ADMIN).orElseGet(() -> {
                Rol newRol = new Rol(RolNombre.ROLE_ADMIN);
                rolService.save(newRol);
                return newRol;
            });
            roles.add(rolAdmin);
        }
        usuario.setRoles(roles);
        usuarioService.save(usuario);
        return new ResponseEntity(new Mensaje("usuario guardado"), HttpStatus.CREATED);
    }

    /*@PostMapping("/nuevo")

        Usuario usuario =
                new Usuario(nuevoUsuario.getNombre(), nuevoUsuario.getNombreUsuario(), nuevoUsuario.getEmail(),
                        passwordEncoder.encode(nuevoUsuario.getPassword()));
        if (usuario.getNombreUsuario() == null) {
            throw new RuntimeException("El nombre de usuario no puede ser nulo");
        }
        Set<Rol> roles = new HashSet<>();
        Rol rolUser = rolService.getByRolNombre(RolNombre.ROLE_USER).orElseGet(() -> {
            Rol newRol = new Rol(RolNombre.ROLE_USER);
            rolService.save(newRol);
            return newRol;
        });
        roles.add(rolUser);
        if(nuevoUsuario.getRoles().contains("admin")) {
            Rol rolAdmin = rolService.getByRolNombre(RolNombre.ROLE_ADMIN).orElseGet(() -> {
                Rol newRol = new Rol(RolNombre.ROLE_ADMIN);
                rolService.save(newRol);
                return newRol;
            });
            roles.add(rolAdmin);
        }
        usuario.setRoles(roles);
        usuarioService.save(usuario);
        return new ResponseEntity(new Mensaje("usuario guardado"), HttpStatus.CREATED);
    }*/

    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal puestos"), HttpStatus.BAD_REQUEST);
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUsuario.getNombreUsuario(), loginUsuario.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
        return new ResponseEntity<>(jwtDto, HttpStatus.OK);
    }
}