package com.frechousky.cvapi;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@Component
public class CustomBeanPostProcessor implements BeanPostProcessor {

    @Value("${datasource.init.fail.timeout}")
    public long initializationFailTimeout;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof HikariDataSource) {
            // Set a timeout of x ms when trying to connect to database
            log.info("Set initialization fail timeout to {} ms to bean {}", initializationFailTimeout, beanName);
            ((HikariDataSource) bean).setInitializationFailTimeout(initializationFailTimeout);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}

