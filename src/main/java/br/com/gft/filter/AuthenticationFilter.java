package br.com.gft.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.gft.entities.User;
import br.com.gft.services.AuthenticationService;
import br.com.gft.services.UserService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

	private final AuthenticationService authenticationService;
	private final UserService userService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String header = request.getHeader("Authorization");
		String token = null;
		if (header != null && header.startsWith("Bearer ")) {
			token = header.substring(7, header.length());
		}

		if (authenticationService.verifyToken(token)) {
			Long userId = authenticationService.getUserId(token);
			User user = userService.findById(userId);
			SecurityContextHolder.getContext()
					.setAuthentication(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
		}

		filterChain.doFilter(request, response);

	}

}
