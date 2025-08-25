package nograu.site.exception;


import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Esta classe captura exceções de forma global para todos os controllers.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Este método é chamado especificamente quando um método HTTP
     * não suportado é usado em um endpoint (ex: um GET em um @PostMapping).
     *
     * @param ex A exceção capturada.
     * @return Uma string de redirecionamento para a página inicial do bikefit.
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public String handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        // Log do erro (opcional, mas recomendado)
        System.err.println("Método HTTP não suportado: " + ex.getMessage());

        // Redireciona para o método @GetMapping do seu BikeFitController
        return "redirect:/bikefit";
    }
}