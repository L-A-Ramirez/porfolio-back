package mi.porfolio.security.service;

import mi.porfolio.security.entity.Usuario;
import mi.porfolio.security.entity.UsuarioPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsImplements implements UserDetailsService {
    @Autowired
    private UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
        return usuarioService.getByNombreUsuario(nombreUsuario)
                .map(UsuarioPrincipal::build)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }
}