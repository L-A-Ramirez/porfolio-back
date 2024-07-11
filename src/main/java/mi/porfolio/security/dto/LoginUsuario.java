package mi.porfolio.security.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class LoginUsuario {
    @NotNull
    private String NombreUsuario;
    @NotNull
    private String password;
}
