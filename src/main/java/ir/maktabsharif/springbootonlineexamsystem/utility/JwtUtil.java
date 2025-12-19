//package ir.maktabsharif.springbootonlineexamsystem.utility;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.JwtException;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//import java.nio.charset.StandardCharsets;
//import java.security.Key;
//import java.util.Collection;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//@Component
//public class JwtUtil {
//
//    private final Key key;
//    private final long expirationMs;
//
//    public JwtUtil(@Value("${jwt.secret}") String secret,
//                   @Value("${jwt.expiration-ms}") long expirationMs) {
//        this.key = Keys.hmacShaKeyFor(secret.getBytes());
//        System.out.println(secret);
//        this.expirationMs = expirationMs;
//    }
//
//    public String generateToken(String username, Role roles) {
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("roles", roles);
//        long now = System.currentTimeMillis();
//        return Jwts.builder()
//                .setSubject(username)
//                .addClaims(claims)
//                .setIssuedAt(new Date(now))
//                .setExpiration(new Date(now + expirationMs))
//                .signWith(key, SignatureAlgorithm.HS256)
//                .compact();
//    }
//
//    public boolean validateToken(String token, UserDetails userDetails) {
//        try {
//            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
//            return true;
//        } catch (JwtException | IllegalArgumentException ex) {
//            return false;
//        }
//    }
//
//    public String extractUsername(String token) {
//        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
//        return claims.getSubject();
//    }
//}
////@Component
////public class JwtUtil {
////
////    private final Key key;
////    private final long expirationMs;
////
////    public JwtUtil(@Value("${jwt.secret}") String secret,
////                   @Value("${jwt.expiration-ms}") long expirationMs) {
////        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
////        this.expirationMs = expirationMs;
////    }
////
////    public String generateToken(String username, Collection<String> authorities) {
////        Map<String, Object> claims = new HashMap<>();
////        claims.put("authorities", authorities);
////
////        long now = System.currentTimeMillis();
////
////        return Jwts.builder()
////                .setSubject(username)
////                .setIssuedAt(new Date(now))
////                .setExpiration(new Date(now + expirationMs))
////                .addClaims(claims)
////                .signWith(key, SignatureAlgorithm.HS256)
////                .compact();
////    }
////
////    public boolean validateToken(String token, UserDetails userDetails) {
////        try {
////            String username = extractUsername(token);
////            return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
////        } catch (JwtException | IllegalArgumentException ex) {
////            return false;
////        }
////    }
////
////    public String extractUsername(String token) {
////        return extractAllClaims(token).getSubject();
////    }
////
////    public List<String> extractAuthorities(String token) {
////        Claims claims = extractAllClaims(token);
////        return claims.get("authorities", List.class);
////    }
////
////    private boolean isTokenExpired(String token) {
////        return extractAllClaims(token)
////                .getExpiration()
////                .before(new Date());
////    }
////
////    private Claims extractAllClaims(String token) {
////        return Jwts.parserBuilder()
////                .setSigningKey(key)
////                .build()
////                .parseClaimsJws(token)
////                .getBody();
////    }
////}