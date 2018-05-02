package com.example.comm.config;

import com.example.domain.bean.Login;
import com.example.domain.enums.Role;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * Create by : Zhangxuemeng
 */
public class SecurityInterceptor extends HandlerInterceptorAdapter{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //     取出session
        HttpSession session = request.getSession();
        Login login = (Login) session.getAttribute(WebSecurityConfig.SESSION_KEY);
        if (login == null){
            //无session 跳转登陆
            String url = "/login";
            response.sendRedirect(url);
            return false;
        }
//         获取出方法上的Access注解
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        Method method = handlerMethod.getMethod();
        Access access = method.getAnnotation(Access.class);
        if (access == null) {
            // 如果注解为null, 说明不需要拦截, 直接放过
            return true;
        }

        if (access.roles().length > 0) {
            // 如果权限配置不为空, 则角色配置
            Role[] roles = access.roles();
            Set<Role> roleSet = new HashSet<>();
            for (Role role : roles) {
                // 将角色加入一个set集合中
                roleSet.add(role);
            }
            Role role = login.getRole();
            if (roleSet.contains(role)){
                // 校验通过返回true, 否则拦截请求
                return true;
            }
//            没有权限
            String url = "/noAccess";
            response.sendRedirect(url);
            return  false;
        }

//            跳转登陆
        String url = "/login";
        response.sendRedirect(url);
        return  false;
    }
}
