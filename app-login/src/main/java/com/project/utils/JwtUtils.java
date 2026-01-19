package com.project.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    @Value("${PRIVATE_KEY}")
    private String privateKey;

    @Value("${USER_GENERATOR}")
    private String userGenerator;

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    public String createToken(Authentication authentication) {
        Algorithm algorithm = Algorithm.HMAC256(privateKey);

        String username = authentication.getPrincipal().toString();

        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(",")); //une los permisos mediante coma
        String jwtToken = JWT.create()
                .withIssuer(userGenerator) //usuario que genera el jwt
                .withSubject(username) //usuario de la autenticacion
                .withClaim("authorities", authorities)
                .withIssuedAt(new Date()) //fecha de creacion
                .withExpiresAt(new Date(System.currentTimeMillis() + 900000))  //fecha de expiracion
                .withJWTId(UUID.randomUUID().toString())
                .withNotBefore(new Date(System.currentTimeMillis())) //A PARTIR DE CUANDO VA A SER VALIDO EL JWT
                .sign(algorithm);
        return jwtToken;
    }

    public DecodedJWT validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(privateKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(this.userGenerator)
                    .build();

            //si va todo ok
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT;
        }
        catch (TokenExpiredException e) {
            logger.warn("El token ha expirado: {}", e.getMessage());
            throw new JWTVerificationException("Token expirado");
        }
        catch (SignatureVerificationException e) {
            logger.error("Firma de token inválida: {}", e.getMessage());
            throw new JWTVerificationException("Token manipulado o clave incorrecta");
        }
        catch (JWTVerificationException e) {
            logger.error("Error validando el token: {}", e.getMessage());
            throw new JWTVerificationException("Token no autorizado");
        }
    }

    //obtener el username
    public String extractUsername(DecodedJWT decodedJWT){
        return decodedJWT.getSubject();
    }

    //obtener claim en particular
    public Claim getSpecificClaim(DecodedJWT decodedJWT, String claimName){
        return decodedJWT.getClaim(claimName);
    }

    //obtener todos los claims
    public Map<String, Claim> getAllClaims(DecodedJWT decodedJWT){
        return decodedJWT.getClaims();
    }
}
