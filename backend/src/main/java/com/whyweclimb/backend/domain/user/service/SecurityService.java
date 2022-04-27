package com.whyweclimb.backend.domain.user.service;

import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class SecurityService {

	@Value("${spring.security.jwt.secret-key}")
	private String SECRET_KEY;
	
	@Value("${spring.security.sha-256.salt}")
	private String SHA_SALT;
	
	public String createToken(String subject, long expTime) {
		if(expTime<=0) {
			throw new RuntimeException("만료됨");
		}
		
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		
		byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
		Key signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());
		
		return (Jwts.builder()
				.setSubject(subject)
				.signWith(signingKey, signatureAlgorithm)
				.setExpiration(new Date(System.currentTimeMillis() + expTime))
				.compact());
	}
	
	public String getSubject(String token) {
		Claims claims = Jwts.parserBuilder()
				.setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
				.build()
				.parseClaimsJws(token)
				.getBody();
		
		return (claims.getSubject());
	}
	
    public String encrypt(String text) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(SHA_SALT.getBytes());
        md.update(text.getBytes());

        return bytesToHex(md.digest());
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }
	
}
