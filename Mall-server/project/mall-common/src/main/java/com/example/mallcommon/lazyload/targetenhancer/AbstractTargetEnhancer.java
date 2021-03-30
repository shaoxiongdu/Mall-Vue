package com.example.mallcommon.lazyload.targetenhancer;

import com.example.mallcommon.lazyload.LazyProperty;
import com.example.mallcommon.lazyload.interceptor.GetterInvokeInterceptor;
import org.springframework.cglib.proxy.Enhancer;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 用于存放方法增强器 {@code MethodInvokeListener} 与属性持有者 {@code LazyPropertyHolder} 的抽象类
 *
 * <p>存放了{@code expectCls与proxySource} </p>
 * <p>存放了{@code methodInvokeListener} 方法增强器 </p>
 * <p>存放了{@code lazyProperties} 属性持有者集合 </p>
 *
 * @author WuHao
 * @see GetterInvokeInterceptor 方法增强器
 * @see LazyProperty 懒加载属性
 * @since 2021/3/12 11:51
 */
public abstract class AbstractTargetEnhancer implements TargetEnhancer {

    /**
     * 属性来源的源对象
     */
    protected Object proxySource;

    /**
     * 用于对代理对象进行拦截器的对象
     */
    private GetterInvokeInterceptor getterInvokeInterceptor;

    /**
     * 用于存放源对象中的已有属性
     * <p>
     * {@code sourceFieldName,LazyProperty{lazyLoader} }
     *
     * @see LazyProperty
     */
    protected Map<String, LazyProperty> lazyProperties = new LinkedHashMap<>();


    /**
     * 在初始化阶段，获得必要的参数，并且不允许子类重写
     *
     * @param source 需要进行转换增强的原始对象
     */
    private void initialize(Object source) {
        this.proxySource = source;
        lazyProperties = getAllRequireLazyLoadProperties();
        getterInvokeInterceptor = configInterceptor();
    }

    /**
     * 计算所有需要懒加载的属性，并使用对应的属性持有者填充
     *
     * @return 需要懒加载的属性集合
     */
    protected abstract Map<String, LazyProperty> getAllRequireLazyLoadProperties();

    protected abstract GetterInvokeInterceptor configInterceptor();

    public final <T> T getTarget(Object source, Class<T> expectCls) {
        initialize(source);

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(expectCls);
        enhancer.setCallback(getterInvokeInterceptor);
        return (T) enhancer.create();
    }


}
