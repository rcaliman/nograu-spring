package nograu.site.bikefit.service;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.springframework.stereotype.Service;

@Service
public class MarkdownService {

    private final Parser parser;
    private final HtmlRenderer renderer;
    private final PolicyFactory policyFactory;

    public MarkdownService() {
        this.parser = Parser.builder().build();
        this.renderer = HtmlRenderer.builder().build();
        
        // Define uma política de segurança para o HTML.
        // Permite tags comuns de formatação, links e imagens, mas remove qualquer coisa perigosa (como <script>).
        this.policyFactory = Sanitizers.FORMATTING
            .and(Sanitizers.BLOCKS)
            .and(Sanitizers.STYLES)
            .and(Sanitizers.TABLES)
            .and(Sanitizers.LINKS)
            .and(Sanitizers.IMAGES);
    }

    /**
     * Converte uma string Markdown para uma string HTML.
     * @param markdownContent O conteúdo em Markdown.
     * @return O conteúdo convertido para HTML.
     */
    public String convertMarkdownToHtml(String markdownContent) {
        if (markdownContent == null || markdownContent.isEmpty()) {
            return "";
        }
        Node document = parser.parse(markdownContent);
        return renderer.render(document);
    }

    /**
     * "Limpa" uma string HTML para remover qualquer código potencialmente malicioso.
     * @param htmlContent O HTML "sujo" vindo da conversão.
     * @return Uma string de HTML segura para ser renderizada.
     */
    public String sanitizeHtml(String htmlContent) {
        if (htmlContent == null || htmlContent.isEmpty()) {
            return "";
        }
        return policyFactory.sanitize(htmlContent);
    }
}