package com.cognizant.authenticationservice.Config;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
		log.info("Start 1");
		log.info("{}: ", authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		log.info("Start 2");
		String header = req.getHeader("Authorization");
		log.info("Header :"+header);

		if (header == null || !header.startsWith("Bearer ")) {
			chain.doFilter(req, res);
			return;
		}
		log.info("Header Not Null");
		UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(req, res);
		log.info("End");
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		log.info("Request :"+request);
		String token = request.getHeader("Authorization");
		if (token != null) {
			// parse the token.
			Jws<Claims> jws;
			try {
				jws = Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token.replace("Bearer ", ""));
				String user = jws.getBody().getSubject();
				log.debug(user);
				if (user != null) {
					return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
				}
			} catch (JwtException ex) {
				return null;
			}
			return null;
		}
		return null;
	}



}