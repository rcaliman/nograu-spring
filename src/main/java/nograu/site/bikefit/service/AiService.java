package nograu.site.bikefit.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class AiService {

    private final WebClient webClient;
    private final String apiKey;

    /**
     * Construtor do serviço que injeta as dependências e configura o WebClient.
     * 
     * @param webClientBuilder O builder para criar instâncias de WebClient.
     * @param apiKey           A chave da API do Google.
     * @param apiUrl           A URL base da API do Google.
     */
    public AiService(WebClient.Builder webClientBuilder,
            @Value("${ai.google.api.key}") String apiKey,
            @Value("${ai.google.api.url}") String apiUrl) {
        this.apiKey = apiKey;
        this.webClient = webClientBuilder.baseUrl(apiUrl).build();
    }

    /**
     * Envia uma pergunta para a API do Google Gemini usando WebClient e retorna a
     * resposta.
     * A chamada é bloqueante (.block()) para ser compatível com ambientes síncronos
     * (ex: Thymeleaf).
     *
     * @param question A pergunta a ser enviada.
     * @return A resposta da IA.
     */
    public String getAnswer(String question) {
        // 2. Construir o corpo da requisição usando DTOs.
        GeminiRequest geminiRequest = new GeminiRequest(
                new Content[] { new Content(new Part[] { new Part(question) }) });

        try {
            // 3. Fazer a chamada POST com WebClient e receber a resposta.
            // O WebClient fará o parse do JSON de resposta para o objeto GeminiResponse.
            GeminiResponse response = webClient.post()
                    .uri(uriBuilder -> uriBuilder.queryParam("key", this.apiKey).build())
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(geminiRequest)
                    .retrieve() // Inicia a recuperação da resposta
                    .bodyToMono(GeminiResponse.class) // Converte o corpo para um Mono<GeminiResponse>
                    .block(); // Bloqueia a execução até que a resposta chegue

            // 4. Extrair o texto da resposta usando o objeto populado.
            return extractContentFromResponse(response);

        } catch (WebClientResponseException e) {
            // Erro específico do WebClient (ex: 4xx, 5xx)
            System.err.println("Erro ao chamar a API da IA: " + e.getStatusCode());
            System.err.println("Corpo da resposta de erro: " + e.getResponseBodyAsString());
            e.printStackTrace();
            return "Ocorreu um erro ao se comunicar com a IA. Código: " + e.getStatusCode();
        } catch (Exception e) {
            // Outros erros (ex: timeout, DNS)
            System.err.println("Erro ao chamar a API da IA: " + e.getMessage());
            e.printStackTrace();
            return "Ocorreu um erro ao se comunicar com a IA. Por favor, tente novamente mais tarde.";
        }
    }

    /**
     * Método auxiliar para extrair o texto de dentro do objeto de resposta da API.
     * Este método é mais seguro pois opera em objetos tipados em vez de String.
     *
     * @param response O objeto de resposta deserializado da API.
     * @return O texto da resposta extraído.
     */
    private String extractContentFromResponse(GeminiResponse response) {
        if (response == null || response.getCandidates() == null || response.getCandidates().length == 0) {
            return "A IA retornou uma resposta vazia ou em formato inesperado.";
        }
        try {
            // Navega de forma segura pela estrutura da resposta
            String text = response.getCandidates()[0].getContent().getParts()[0].getText();
            // O parser JSON (Jackson) já decodifica caracteres como \n e \",
            // então a decodificação manual não é mais necessária.
            return text;
        } catch (NullPointerException e) {
            System.err.println("Estrutura da resposta da IA inesperada: " + e.getMessage());
            return "Não foi possível encontrar o texto na resposta da IA. Resposta recebida: " + response;
        }
    }

    // --- Classes DTO (Data Transfer Objects) para a REQUISIÇÃO ---
    // Representam a estrutura do JSON que a API do Gemini espera.

    private static class GeminiRequest {
        private final Content[] contents;

        public GeminiRequest(Content[] contents) {
            this.contents = contents;
        }

        public Content[] getContents() {
            return contents;
        }
    }

    private static class Content {
        private final Part[] parts;

        public Content(Part[] parts) {
            this.parts = parts;
        }

        public Part[] getParts() {
            return parts;
        }
    }

    private static class Part {
        private final String text;

        public Part(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }

    // --- Classes DTO (Data Transfer Objects) para a RESPOSTA ---
    // Representam a estrutura do JSON que esperamos receber da API.
    // Usar "static" evita a necessidade de uma instância de AiService para
    // criá-las.
    // Construtor padrão e setters são necessários para a deserialização do Jackson.

    private static class GeminiResponse {
        private Candidate[] candidates;

        public Candidate[] getCandidates() {
            return candidates;
        }

        public void setCandidates(Candidate[] candidates) {
            this.candidates = candidates;
        }

        @Override
        public String toString() {
            return "GeminiResponse{...}";
        } // Evitar logar a resposta inteira
    }

    private static class Candidate {
        private Content content;

        public Content getContent() {
            return content;
        }

        public void setContent(Content content) {
            this.content = content;
        }
    }
}