package io.github.sudoitir.dddcqrstoolkit.cqs.config;

import io.github.sudoitir.dddcqrstoolkit.cqs.BusAspect;
import io.github.sudoitir.dddcqrstoolkit.cqs.command.CommandRegistry;
import io.github.sudoitir.dddcqrstoolkit.cqs.command.SpringCommandBus;
import io.github.sudoitir.dddcqrstoolkit.cqs.module.LoggingModule;
import io.github.sudoitir.dddcqrstoolkit.cqs.module.ModuleRegistry;
import io.github.sudoitir.dddcqrstoolkit.cqs.module.ValidationModule;
import io.github.sudoitir.dddcqrstoolkit.cqs.query.QueryRegistry;
import io.github.sudoitir.dddcqrstoolkit.cqs.query.SpringQueryBus;
import io.github.sudoitir.dddcqrstoolkit.cqs.strategy.HeaderBasedStrategyKeyProvider;
import io.github.sudoitir.dddcqrstoolkit.cqs.strategy.StrategyKeyProvider;
import io.github.sudoitir.dddcqrstoolkit.event.EventPublisher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Validator;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@AutoConfiguration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class BusAutoConfig {

    @Bean
    public CommandRegistry commandRegistry() {
        return new CommandRegistry();
    }

    @Bean
    public QueryRegistry queryRegistry() {
        return new QueryRegistry();
    }

    @Bean
    public SpringCommandBus springCommandBus(CommandRegistry commandRegistry) {
        return new SpringCommandBus(commandRegistry);
    }

    @Bean
    public SpringQueryBus springQueryBus(QueryRegistry queryRegistry) {
        return new SpringQueryBus(queryRegistry);
    }

    @Bean
    public BusAspect busAspect(ModuleRegistry moduleRegistry) {
        return new BusAspect(moduleRegistry);
    }

    @Bean
    public EventPublisher eventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        return new EventPublisher(applicationEventPublisher);
    }

    @Bean
    @ConditionalOnMissingBean
    public StrategyKeyProvider strategyKeyProvider(HttpServletRequest httpServletRequest) {
        return new HeaderBasedStrategyKeyProvider(httpServletRequest);
    }

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
