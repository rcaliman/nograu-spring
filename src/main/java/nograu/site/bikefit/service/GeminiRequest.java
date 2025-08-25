package nograu.site.bikefit.service;

// Crie esta classe, pode ser dentro do seu pacote de serviço
// Representa o objeto "parts"
class Part {
    private String text;

    public Part(String text) {
        this.text = text;
    }
    
    // Getters e Setters
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
}

// Representa o objeto "contents"
class Content {
    private Part[] parts;

    public Content(Part[] parts) {
        this.parts = parts;
    }

    // Getters e Setters
    public Part[] getParts() { return parts; }
    public void setParts(Part[] parts) { this.parts = parts; }
}

// Classe principal que representa o corpo da requisição
class GeminiRequest {
    private Content[] contents;

    public GeminiRequest(Content[] contents) {
        this.contents = contents;
    }

    // Getters e Setters
    public Content[] getContents() { return contents; }
    public void setContents(Content[] contents) { this.contents = contents; }
}