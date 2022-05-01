package en.upenn.bonz.filter;

import com.alibaba.fastjson.JSON;
import en.upenn.bonz.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 1. get request URI
        String uri = request.getRequestURI();
        log.info("intercept a request: {}", request.getRequestURI());

        // 2. define URI we are not going to deal with
        String[] uris = new String[] {
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**"
        };

        // 3. check if the path should be intercepted
        boolean check = cmp(uri, uris);
        if (check) {
            log.info("this path: {} doesn't need to be intercepted", uri);

            filterChain.doFilter(request, response);
            return;
        }

        // 4. check login status,if employee has already login, do filter
        if (request.getSession().getAttribute("employee") != null) {
            log.info("employee has login in, employee id is {}", request.getSession().getAttribute("employee"));

            filterChain.doFilter(request, response);
            return;
        }

        log.info("employee has not logged in yet...");
        // 5. if employee hasn't logged in yet, return to the login page
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN"))); // "NOTLOGIN" is correspond with the "request.js" response intercept
        return;
    }

    /**
     * path match
     * @param uri
     * @param uris
     * @return
     */
    private boolean cmp(String uri, String[] uris) {
        for (String s : uris) {
            boolean match = PATH_MATCHER.match(s, uri);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
