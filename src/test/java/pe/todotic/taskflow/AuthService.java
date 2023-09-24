package pe.todotic.taskflow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pe.todotic.taskflow.security.TokenProvider;

@Service
public class AuthService {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    public String obtenerBearerToken(String email, String clave) {
        UsernamePasswordAuthenticationToken usernamePAT = new UsernamePasswordAuthenticationToken(
                email,
                clave
        );
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(usernamePAT);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return String.format("Bearer %s", tokenProvider.crearToken(authentication));
    }

}
