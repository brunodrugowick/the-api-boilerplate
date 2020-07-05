package dev.drugowick.theapiboilerplate.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class ValidationConfig {

    /**
     * This bean configures Spring's MessageSource as the default resource bundle, avoiding confusion with Hibernate's
     * resource bundle (ValidationMessages.properties). One could use Hibernate's, but with this configuration that's
     * not an option anymore. This is simpler and I prefer it.
     *
     * What this allows is to use a specific validation at the annotation above a domain class property, for example
     * `@PositiveOrZero(message = "{message.specific.invalidNumber}")`, and create the `message.specific.invalidNumber`
     * key on Spring's `messages.properties` file.
     *
     * @param messageSource is a source for messages.
     *
     * @return a {@link LocalValidatorFactoryBean} defining "messages.properties" as default.
     */
    @Bean
    LocalValidatorFactoryBean validator(MessageSource messageSource) {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource);
        return bean;
    }
}
