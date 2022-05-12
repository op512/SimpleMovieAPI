package uk.co.op.movieapi;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class VerySimpleFilter implements Filter {

	private static final String USER_KEY = "userkey";
	private static final String ADMIN_KEY = "adminkey";
	private static final List<String> USER_ROLES = List.of("GET");
	private static final List<String> ADMIN_ROLES = List.of("GET", "POST", "PATCH", "PUT", "DELETE");

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		String key = req.getHeader("X-API-Key");
		if (req.getRequestURI().contains("swagger") || req.getRequestURI().contains("api-docs")) {
			chain.doFilter(request, response);
		}
		else if ((USER_KEY.equals(key) && USER_ROLES.contains(req.getMethod()))
				|| (ADMIN_KEY.equals(key) && ADMIN_ROLES.contains(req.getMethod()))) {
			chain.doFilter(request, response);
		} else if ((USER_KEY.equals(key) && !USER_ROLES.contains(req.getMethod()))
				|| (ADMIN_KEY.equals(key) && !ADMIN_ROLES.contains(req.getMethod()))) {
			send403(response);
		} else {
			send401(response);
		}
	}

	private void send401(ServletResponse response) throws IOException {
		((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
	}

	private void send403(ServletResponse response) throws IOException {
		((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN);
	}

}
