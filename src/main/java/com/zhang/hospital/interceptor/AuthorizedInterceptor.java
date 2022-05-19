package com.zhang.hospital.interceptor;

import com.zhang.hospital.entity.Admin;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//拦截器
public class AuthorizedInterceptor implements HandlerInterceptor
{
    //定义不拦截的请求 加入css image js是因为layui加载jsp页面的时候 会发送加载css这些的请求 同样被拦截了
    ///index.jsp也不会拦截 因为里面包含了字符串.js
    private static final String[] IGNORE_URI= {".css",".gif","image",".js","ico","login","register","newUser"};
    /**
     * 该方法需要preHandle方法的返回值为true时才会执行。
     * 该方法将在整个请求完成之后执行，主要作用是用于清理资源。
     */
    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception exception)
            throws Exception {

    }

    /**
     * 这个方法在preHandle方法返回值为true的时候才会执行。
     * 执行时间是在处理器进行处理之后，也就是在Controller的方法调用之后执行。
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView mv) throws Exception {

    }


    /**
     * preHandle方法是进行处理器拦截用的，该方法将在Controller处理之前进行调用，
     * 当preHandle的返回值为false的时候整个请求就结束了。
     * 如果preHandle的返回值为true，则会继续执行postHandle和afterCompletion。
     *
     */
    @Override
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response,
                             Object handler) throws Exception
    {
        //默认用户没有登录
        boolean flag=false;
        //获得请求的ServletPath
        String servletPath=request.getServletPath();

        for(String s:IGNORE_URI)
        {
            if(servletPath.contains(s)) //包含字符串
            {
                flag=true;
                break;
            }
        }
        //拦截请求
        if(!flag)
        {
            Admin admin=(Admin) request.getSession().getAttribute("user");
            if(admin==null)
            {
                // 如果是 ajax 请求，则设置 session 状态 、CONTEXTPATH 的路径值
                // 如果是ajax请求响应头会有，x-requested-with
                System.out.println(servletPath+"拦截成功");
                if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest"))
                {
                   // System.out.println("来自于ajax请求");
                    response.setHeader("SESSIONSTATUS", "TIMEOUT");
                    response.setHeader("CONTEXTPATH", "/hospital/index.jsp");
                    // FORBIDDEN，forbidden。也就是禁止、403
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                }else{
                    // 如果不是 ajax 请求，则直接跳转即可
                    response.sendRedirect("/hospital/index.jsp");
                }

                return flag;
            }
            else
            {
                flag=true;
            }

        }

        return flag;
    }

}

