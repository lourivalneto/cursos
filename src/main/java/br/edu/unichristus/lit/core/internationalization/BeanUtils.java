package br.edu.unichristus.lit.core.internationalization;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class BeanUtils implements ApplicationContextAware {

    private static ApplicationContext context;

    public static MessageSource getMessageSource() {
        return context.getBean(MessageSource.class);
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}