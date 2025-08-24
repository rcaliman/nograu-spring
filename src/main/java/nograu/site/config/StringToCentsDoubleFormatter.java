package nograu.site.config;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;
import java.text.ParseException;
import java.util.Locale;

@Component
public class StringToCentsDoubleFormatter implements Formatter<Double> {

    /**
     * Converte a String vinda do formulário para Double.
     * Agora, ela recebe um formato padrão como "7495.12" ou "100".
     */
    @Override
    public Double parse(String text, Locale locale) throws ParseException {
        if (text == null || text.trim().isEmpty()) {
            return null;
        }
        try {
            // O Double.parseDouble do Java já entende o formato "1234.56"
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            throw new ParseException("Não foi possível converter o valor para Double: " + text, 0);
        }
    }

    @Override
    public String print(Double object, Locale locale) {
        return object != null ? object.toString() : "";
    }
}