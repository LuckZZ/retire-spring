package com.example.comm.config;

import com.example.comm.Constant;
import com.example.domain.bean.Login;
import com.example.domain.enums.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * csdn：https://blog.csdn.net/Luck_ZZ
 */
public class SecurityInterceptor extends HandlerInterceptorAdapter{
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //     取出session
        HttpSession session = request.getSession();
        Login login = (Login) session.getAttribute(WebSecurityConfig.SESSION_KEY);
        if (login == null){
            logger.warn("拦截原因:没有session "+"请求路径:"+request.getRequestURI());
            //无session 跳转登陆
            String url = Constant.LOGIN;
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
            logger.warn("拦截原因:没有权限 "+"请求路径:"+request.getRequestURI());
//            无权限
            String XRequested =request.getHeader("X-Requested-With");
            if("XMLHttpRequest".equals(XRequested)){
//                ajax请求
                response.sendRedirect(Constant.NO_ACCESS_AJAX);
            }else {
//            非ajax请求
                response.sendRedirect(Constant.NO_ACCESS_PAGE);
            }
            return  false;
        }
//            跳转登陆
        String url = Constant.LOGIN;
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
