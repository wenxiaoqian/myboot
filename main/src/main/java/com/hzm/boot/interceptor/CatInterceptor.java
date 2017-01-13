package com.hzm.boot.interceptor;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Message;
import com.dianping.cat.message.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cat监控拦截
 *
 * @author xiaoqian.wen
 * @create 2017-01-04 18:27
 **/
public class CatInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private ThreadLocal<Transaction> tranLocal = new ThreadLocal<Transaction>();
    private ThreadLocal<Transaction> pageLocal = new ThreadLocal<Transaction>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {

        String uri = request.getRequestURI();
        Transaction t = Cat.newTransaction("URL", uri);
        Cat.logEvent("URL.Method", request.getMethod(), Message.SUCCESS,request.getRequestURL().toString());
        Cat.logEvent("URL.Host", request.getMethod(),Message.SUCCESS,request.getRemoteHost());
        tranLocal.set(t);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

        String viewName = modelAndView != null?modelAndView.getViewName():"无";
        Transaction t = Cat.newTransaction("View", viewName);
        pageLocal.set(t);
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

        //请求-页面渲染前
        Transaction pt = pageLocal.get();
        pageLocal.remove(); //need remove
        pt.setStatus(Transaction.SUCCESS);
        pt.complete();

        //总计
        Transaction t = tranLocal.get();
        tranLocal.remove(); //need remove
        t.setStatus(Transaction.SUCCESS);
        t.complete();
    }
}
