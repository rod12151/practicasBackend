package practicas.gestion_personal.auth.filters;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.xml.bind.DataBindingException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import practicas.gestion_personal.domain.entities.UserEntity;
import static practicas.gestion_personal.auth.TokenJwtConfig.*;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        UserEntity user = null;
        String username = null;
        String password=null;
        try {
            user = new ObjectMapper().readValue(request.getInputStream(),UserEntity.class);
            username = user.getUsername();
            password=user.getPassword();

        } catch (IOException | DataBindingException e){
            e.printStackTrace();
        }
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username,password);

        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        String username =((User) authResult.getPrincipal()).getUsername();
        /*optener roles*/
        Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();

        boolean isAdmin = roles.stream().anyMatch(r->r.getAuthority().equals("ROLE_ADMIN"));
        boolean isJefe = roles.stream().anyMatch(r->r.getAuthority().equals("ROLE_JEFE"));

        Claims claims = Jwts.claims();
        claims.put("authorities",new ObjectMapper().writeValueAsString(roles));
        claims.put("isAdmin",isAdmin);
        claims.put("isJefe",isJefe);

        /*create Token*/
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .signWith(SECRET_KEY)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+3600000))
                .compact();

        response.addHeader(HEADER_AUTHORIZATION,PREFIX_TOKEN+ token);
        Map<String,Object> body = new HashMap<>();
        body.put("Token",token);
        body.put("message",String.format("Hello %s,you have started successfully session ",username));
        body.put("username",username);

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(200);
        response.setContentType("application/json");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {
        Map<String,Object> body = new HashMap<>();
        body.put("message", "Errror en la authentificacion dni o password incorrecto");
        body.put("error",failed.getMessage());

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(401);
        response.setContentType("application/json");

    }
}
