package nograu.site.annotation;


import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NoLinksValidator implements ConstraintValidator<NoLinks, String> {

    // Regex para encontrar "http", "https", "www" ou "href", ignorando maiúsculas/minúsculas
    private static final Pattern LINK_PATTERN = Pattern.compile("(?i)(http|https|www|href)");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true; // Campos em branco ou nulos são validados por @NotBlank
        }
        // Se o padrão for encontrado no valor, a validação falha (retorna false)
        return !LINK_PATTERN.matcher(value).find();
    }
}
