package com.hzm.boot.config;

import com.alibaba.druid.support.http.WebStatFilter;
import com.dianping.cat.servlet.CatFilter;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Cat监控
 *
 * @author xiaoqian.wen
 * @create 2017-01-04 14:57
 **/
@Configuration
public class CatConfig {

    /**
     * 注册一个：filterRegistrationBean
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean registCatFilter() {

        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();

        //添加过滤规则.
        filterRegistrationBean.addUrlPatterns("/*");

        filterRegistrationBean.setFilter(new CatFilter());
        return filterRegistrationBean;
    }

}
