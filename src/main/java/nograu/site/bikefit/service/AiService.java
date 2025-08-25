package nograu.site.bikefit.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AiService {

    @Value("${ai.google.api.key}")
    private String apiKey;

    @Value("${ai.google.api.url}")
    private String apiUrl;

    /**
     * Envia uma pergunta para a API do Google Gemini e retorna a resposta.
     * 
     * @param question A pergunta a ser enviada.
     * @return A resposta da IA.
     */
    public String getAnswer(String question) {
        RestTemplate restTemplate = new RestTemplate();
        String fullUrl = apiUrl + "?key=" + apiKey;

        // 1. Definir o cabeçalho da requisição
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 2. Construir o corpo da requisição usando objetos Java (DTOs)
        // Isso garante que qualquer caractere especial na "question" seja tratado
        // corretamente.
        Part part = new Part(question);
        Content content = new Content(new Part[] { part });
        GeminiRequest geminiRequest = new GeminiRequest(new Content[] { content });

        // 3. Montar a requisição HTTP com cabeçalho e corpo
        HttpEntity<GeminiRequest> entity = new HttpEntity<>(geminiRequest, headers);

        try {
            // 4. Fazer a chamada POST e receber a resposta como String
            ResponseEntity<String> response = restTemplate.postForEntity(fullUrl, entity, String.class);

            // 5. Extrair o texto da resposta JSON
            return extractContentFromResponse(response.getBody());

        } catch (Exception e) {
            // É uma boa prática logar o erro para depuração
            System.err.println("Erro ao chamar a API da IA: " + e.getMessage());
            e.printStackTrace();
            return "Ocorreu um erro ao se comunicar com a IA. Por favor, tente novamente mais tarde.";
        }
    }

    /**
     * Método auxiliar para extrair o texto de dentro da resposta JSON complexa da
     * API.
     * ATENÇÃO: Para aplicações em produção, é fortemente recomendado usar uma
     * biblioteca
     * de parsing JSON, como o Jackson ObjectMapper, para mais robustez.
     * 
     * @param responseBody O corpo da resposta da API em formato String.
     * @return O texto da resposta extraído.
     */
    private String extractContentFromResponse(String responseBody) {
        if (responseBody == null || responseBody.isEmpty()) {
            return "A IA retornou uma resposta vazia.";
        }

        try {
            // Procura pelo campo "text" dentro da resposta
            int textIndex = responseBody.indexOf("\"text\": \"");
            if (textIndex == -1) {
                // Se não encontrar, pode ser uma resposta de erro da API
                return "Não foi possível encontrar o texto na resposta da IA. Resposta recebida: " + responseBody;
            }

            // Extrai o conteúdo do campo "text"
            int startIndex = textIndex + 9; // Pula o `"text": "`
            int endIndex = responseBody.indexOf("\"", startIndex);

            if (endIndex == -1) {
                return "Formato de resposta inesperado.";
            }

            // Decodifica caracteres especiais comuns em JSON, como \n (nova linha) e \"
            // (aspas)
            return responseBody.substring(startIndex, endIndex)
                    .replace("\\n", "\n")
                    .replace("\\\"", "\"");
        } catch (Exception e) {
            System.err.println("Erro ao parsear a resposta da IA: " + e.getMessage());
            return "Erro ao processar a resposta da IA.";
        }
    }

    // --- Classes DTO (Data Transfer Objects) para construir o JSON ---
    // Estas classes representam a estrutura do JSON que a API do Gemini espera.
    // Colocá-las aqui dentro torna a classe AiService autossuficiente.

    private static class GeminiRequest {
        private Content[] contents;

        public GeminiRequest(Content[] contents) {
            this.contents = contents;
        }

        public Content[] getContents() {
            return contents;
        }
    }

    private static class Content {
        private Part[] parts;

        public Content(Part[] parts) {
            this.parts = parts;
        }

        public Part[] getParts() {
            return parts;
        }
    }

    private static class Part {
        private String text;

        public Part(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }
}