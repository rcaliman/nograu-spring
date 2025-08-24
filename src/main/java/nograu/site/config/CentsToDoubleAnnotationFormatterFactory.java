package nograu.site.config;


import nograu.site.annotation.CentsToDouble;
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.util.Collections;
import java.util.Locale;
import java.util.Set;

public class CentsToDoubleAnnotationFormatterFactory implements AnnotationFormatterFactory<CentsToDouble> {

    @Override
    public Set<Class<?>> getFieldTypes() {
        // Tipos de campo que esta anotação suporta
        return Collections.singleton(Double.class);
    }

    @Override
    public Printer<?> getPrinter(CentsToDouble annotation, Class<?> fieldType) {
        return (object, locale) -> object.toString();
    }

    @Override
    public Parser<?> getParser(CentsToDouble annotation, Class<?> fieldType) {
        // Retorna o nosso conversor de String para Double
        return new CentsParser();
    }

    // Classe interna com a lógica de conversão
    private static class CentsParser implements Parser<Double> {
        @Override
        public Double parse(String text, Locale locale) {
            if (text == null || text.trim().isEmpty()) {
                return null;
            }
            try {
                long valueInCents = Long.parseLong(text);
                return (double) valueInCents / 100.0;
            } catch (NumberFormatException e) {
                // Se a conversão falhar, retorna null ou lança uma exceção
                return null;
            }
        }
    }
}
