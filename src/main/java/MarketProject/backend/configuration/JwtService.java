package MarketProject.backend.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;


@Service
@RequiredArgsConstructor
public class JwtService {
    private final UserDetailsService userDetailsService;

    @Value("${security.jwt.secret}")
    private String SECRET_KEY;
    public String findUsername(String token) {
        return exportToken(token, Claims::getSubject);
    }

    public Boolean beforeExpirationDate(String token) {
        return exportToken(token, Claims::getExpiration).before(new Date());
    }

    private <T> T exportToken(String token, Function<Claims,T> claimsTFunction) {
        final Claims claims= Jwts.parserBuilder().setSigningKey(getKey())
                .build().parseClaimsJws(token).getBody();


        return claimsTFunction.apply(claims);
    }

    private Key getKey() {
        byte[] key= Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(key);
    }

    public boolean tokenControl(String jwt, UserDetails userDetails)  {

        final String username=findUsername(jwt);
         return (username.equals(userDetails.getUsername())&& !beforeExpirationDate(jwt));
    }

    public Object generateToken(UserDetails user) {
        return Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*24*60))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean checkTokenUsername(String token, String username){

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        token=token.substring(7);

        return tokenControl(token, userDetails);
    }
}
