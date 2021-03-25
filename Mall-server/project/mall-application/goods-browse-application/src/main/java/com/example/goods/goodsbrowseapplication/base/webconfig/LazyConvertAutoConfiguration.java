package com.example.goods.goodsbrowseapplication.base.webconfig;

import com.example.mallcommon.lazyload.listener.CglibMethodInvokeListener;
import com.example.mallcommon.lazyload.container.MybatisTargetEnhancer;
import com.example.mallcommon.loadcontroller.LoadDataSelector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * <p>使用它来注入延迟传递加载类</p>
 *
 * @author WuHao
 * @since 2021/3/12 15:05
 */
@Configuration
public class LazyConvertAutoConfiguration {

    @Bean
    @Scope("prototype")
    public CglibMethodInvokeListener cglibInvokeListener() {
        return new CglibMethodInvokeListener();
    }

    @Bean
    @Scope("prototype")
    public MybatisTargetEnhancer<?> targetEnhancerContainer() {
        return new MybatisTargetEnhancer<>();
    }

    @Bean
    public LoadDataSelector loadDataSelector() {
        return new LoadDataSelector();
    }
}