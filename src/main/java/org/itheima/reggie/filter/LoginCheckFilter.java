package org.itheima.reggie.filter;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.itheima.reggie.common.BaseContext;
import org.itheima.reggie.common.R;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "loginChecjFilter",urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {

    public static final AntPathMatcher PATH_MATCHER=new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest) servletRequest;
        HttpServletResponse response=(HttpServletResponse) servletResponse;

        String requestURL=request.getRequestURI();
        String[] urls=new String[]{
          "/employee/login",
          "/employee/logout",
          "/backend/**",
          "/front/**"
        };

        boolean check = check(urls, requestURL);

        if(check==true){
            filterChain.doFilter(request,response);
            return;
        }

        if(request.getSession().getAttribute("employee")!=null){
            log.info("用户已登录,用户id为:{}",request.getSession().getAttribute("employee"));
            Long empId=(Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);

            filterChain.doFilter(request,response);
            return;
        }
        log.info("用户未登录");
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;

    }

    public boolean check(String[] urls,String requestURI){
        for(String url:urls){
            boolean match = PATH_MATCHER.match(url, requestURI);
            if(match==true){
                return true;
            }
        }
        return false;
    }
}
