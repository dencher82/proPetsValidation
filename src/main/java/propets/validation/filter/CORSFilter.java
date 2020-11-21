package propets.validation.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

//@Component
//@Order(5)
public class CORSFilter implements Filter {

	@Value("${origin.url}")
	private String originUrl;
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request = (HttpServletRequest) req;
		response.setHeader("Access-Control-Allow-Origin", originUrl);
		response.setHeader("Access-Control-Allow-Methods", "GET, OPTIONS");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "X-Token, content-type, Authorization");
		if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else { 
            chain.doFilter(request, response);
        }
	}

}
