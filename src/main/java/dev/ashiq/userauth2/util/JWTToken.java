package dev.ashiq.userauth2.util;


import dev.ashiq.userauth2.model.User;
import dev.ashiq.userauth2.model.UserSession;
import dev.ashiq.userauth2.repository.UserRepository;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;


import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTToken {

    private static final String Secret = "secret2313u2483245863r2fyquyqwuud6eryueadfeyfdfja";
//    private static final long EXPIRATION_TIME = 432_00_000; // 12 hrs

    private static final long EXPIRATION_TIME = 180_000; // 3 mins
    private final UserRepository userRepository;

    public Date expireAt;


//    private  final UserSession userSession;
//   private final Date expireAt=new Date(System.currentTimeMillis() + EXPIRATION_TIME);

    @Autowired
    public JWTToken(UserRepository userRepository)
    {
        this.userRepository = userRepository;
//        this.expireAt=expireAt;
//        this.userSession = userSession;
    }

    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userName);
    }
    private String createToken(Map<String, Object> claims, String userName) {
        User user = userRepository.findByEmail(userName);
        final Date createdAt = new Date();
        expireAt= calculateExpireAt(createdAt);
//         System.out.println("expiration1: "+expireAt);
//         UserSession userSession = new UserSession(expireAt);
//        System.out.println("expiration2: "+ userSession.getExpireAt());

        claims.put("name", user.getEmail());
        claims.put("role", user.getRoles());
        claims.put("createdAt",user.getCreatedAt());
        claims.put("full name",user.getFullName());
//        claims.put("expireAt",userSession.setExpireAt(expireAt) +EXPIRATION_TIME);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .setIssuedAt(createdAt)
                .setExpiration(expireAt)
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Date calculateExpireAt(Date createdAt) {
        return new Date(createdAt.getTime()+EXPIRATION_TIME);

    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(Secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public void validateToken(final String token) {
        Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
    }

}
