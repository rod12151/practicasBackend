package practicas.gestion_personal.auth;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.NoArgsConstructor;

import java.security.Key;

@NoArgsConstructor
public class TokenJwtConfig {

    public static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static final String PREFIX_TOKEN= "Bearer ";
    public static final String HEADER_AUTHORIZATION = "Authorization";


}
