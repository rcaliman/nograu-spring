package nograu.site.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // NÃO PRECISAMOS MAIS INJETAR O FORMATTER ANTIGO
    // public WebConfig(StringToCentsDoubleFormatter stringToCentsDoubleFormatter) { ... }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        // Substitua o registro antigo por este:
        registry.addFormatterForFieldAnnotation(new CentsToDoubleAnnotationFormatterFactory());
    }
}