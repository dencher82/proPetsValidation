package propets.validation.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static propets.validation.configuration.Constants.*;

import propets.validation.service.TokenService;

@Service
public class TokenValidationFilter implements Filter {

	@Autowired
	TokenService tokenService;

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		String path = request.getServletPath();
		String method = request.getMethod();
		if ("/account/en/v1/token/create".equals(path) && "GET".equals(method)) {
			if (request.getHeader("Authorization") == null) {
				response.sendError(401);
				return;
			}
		} else if ("/account/en/v1/token/validation".equals(path) && "GET".equals(method)) {
			if (request.getHeader(TOKEN_HEADER) == null) {
				response.sendError(401);
				return;
			}
		} else {
			response.sendError(400);
			return;
		}

		chain.doFilter(request, response);
	}

}
