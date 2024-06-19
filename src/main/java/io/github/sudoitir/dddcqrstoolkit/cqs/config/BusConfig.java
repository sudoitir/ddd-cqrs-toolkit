package io.github.sudoitir.dddcqrstoolkit.cqs.config;

import io.github.sudoitir.dddcqrstoolkit.cqs.module.LoggingModule;
import io.github.sudoitir.dddcqrstoolkit.cqs.module.ModuleRegistry;
import io.github.sudoitir.dddcqrstoolkit.cqs.module.ValidationModule;
import jakarta.validation.Validator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class BusConfig {

    @Bean
    @ConditionalOnMissingBean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    @ConditionalOnMissingBean
    public ModuleRegistry moduleRegistry(Validator validator) {
        ValidationModule validationModule = new ValidationModule(validator);
        LoggingModule loggingModule = new LoggingModule();
        return new ModuleRegistry(validationModule, loggingModule);
    }

}
