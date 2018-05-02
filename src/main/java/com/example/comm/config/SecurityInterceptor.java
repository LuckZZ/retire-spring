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
        Role loginRole = login.getRole();
        Set<Role> roleSet = getRoleSet((HandlerMethod) handler);
        if (roleSet == null){
//            方法和类都无注解，通过
            return true;
        }
        if (roleSet.size() > 0){
//            有权限
            if (roleSet.contains(loginRole)){
                // 校验通过返回true, 通过
                return true;
            }
//            无权限
            String XRequested =request.getHeader("X-Requested-With");
            if("XMLHttpRequest".equals(XRequested)){
//                ajax请求
                response.getWriter().write("IsAjax");
                String url = "/noAccessAjax";
                response.sendRedirect(url);
            }else {
//            非ajax请求
                String url = "/noAccess";
                response.sendRedirect(url);
            }
            return  false;
        }
//            跳转登陆
        String url = "/login";
        response.sendRedirect(url);
        return  false;
    }

    /**
     * 得到方法及类的注解
     * @param handler
     * @return
     */
    private Set<Role> getRoleSet(HandlerMethod handler){
        Set<Role> roleSet = new HashSet<>();
        Class clazz = handler.getBeanType();
        Method method = handler.getMethod();
        Access accessClazz = (Access) clazz.getAnnotation(Access.class);
        Access accessMethod = method.getAnnotation(Access.class);
        if (accessClazz==null && accessMethod==null){
            return null;
        }
        if (accessClazz != null){
            if (accessClazz.roles().length > 0){
                Role[] roles = accessClazz.roles();
                for (Role r : roles) {
                    roleSet.add(r);
                }
            }
        }
        if(accessMethod != null){
            if (accessMethod.roles().length > 0){
                Role[] roles = accessMethod.roles();
                for (Role r : roles) {
                    roleSet.add(r);
                }
            }
        }
        return roleSet;
    }

}
