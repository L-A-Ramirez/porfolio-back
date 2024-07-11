package mi.porfolio.security.dto;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import mi.porfolio.security.enums.RolNombre;
import mi.porfolio.security.enums.RolesDeserializer;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
public class NuevoUsuario {
    private String nombre;
    @Column(name = "nombre_usuario")
    private String nombreUsuario;
    private String email;
    private String password;
    @JsonDeserialize(using = RolesDeserializer.class)
    private Set<RolNombre> roles;
}
