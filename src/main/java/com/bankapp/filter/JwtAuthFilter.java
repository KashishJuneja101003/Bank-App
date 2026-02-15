package com.bankapp.filter;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bankapp.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		System.out.println("JWT FILTER HIT: " + request.getRequestURI());

		String path = request.getRequestURI();
		String method = request.getMethod();

		System.out.println("JWT FILTER HIT: " + method + " " + path);

		// SKIP AUTH APIS
		if (path.contains("/auth/login")) {
			System.out.println("JWT SKIPPED FOR LOGIN");
			filterChain.doFilter(request, response);
			return;
		}

		// Skip employee creation
		if (path.startsWith("/employees")) {
			System.out.println("JWT SKIPPED FOR EMPLOYEE CRUD");
			filterChain.doFilter(request, response);
			return;
		}
		

		// CHECK TOKEN ONLY FOR PROTECTED APIS
		String authHeader = request.getHeader("Authorization");
		System.out.println(authHeader);

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return;
		}

		try {
			String token = authHeader.substring(7);
			System.out.println("TOKEN: " + token);

			String email = jwtUtil.extractEmail(token);
			String role = jwtUtil.extractRole(token);

			System.out.println("JWT EMAIL: " + email);
			System.out.println("JWT ROLE: " + role);

			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, null,
					List.of(new SimpleGrantedAuthority("ROLE_" + role)));

			SecurityContextHolder.getContext().setAuthentication(authentication);

			System.out.println("AUTH SET SUCCESSFULLY");
		} catch (Exception e) {
			System.out.println("JWT ERROR – REQUEST BLOCKED");
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
		filterChain.doFilter(request, response);
	}

}
