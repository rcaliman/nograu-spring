document.addEventListener('DOMContentLoaded', function () {
    // --- 1. REFERÊNCIAS AOS ELEMENTOS DO FORMULÁRIO ---
    const form = document.getElementById('form-calculo');
    const proximaParcelaInput = document.getElementById('proximaParcela');
    const ultimaParcelaInput = document.getElementById('ultimaParcela');
    const valorParcelaInput = document.getElementById('valorParcela');
    const valorEmprestadoInput = document.getElementById('valorEmprestado');

    // --- 2. FUNÇÕES DE MÁSCARA VISUAL PARA O USUÁRIO ---
    const mascaraData = (event) => {
        let input = event.target;
        input.value = input.value.replace(/\D/g, '');
        input.value = input.value.replace(/(\d{2})(\d)/, '$1/$2');
        input.value = input.value.replace(/(\d{2})\/(\d{2})(\d)/, '$1/$2/$3');
    };

    const mascaraDinheiro = (event) => {
        const input = event.target;
        // 1. Remove todos os caracteres que não são dígitos
        let value = input.value.replace(/\D/g, '');

        // 2. Se o valor estiver vazio, limpa o campo e sai da função
        if (value === '') {
            input.value = '';
            return;
        }

        // 3. Converte o valor (em centavos) para um número e divide por 100
        // Ex: "749512" -> 749512 -> 7495.12
        const valorNumerico = parseFloat(value) / 100;

        // 4. Formata o número para o padrão monetário brasileiro (BRL)
        // Ex: 7495.12 -> "R$ 7.495,12"
        input.value = new Intl.NumberFormat('pt-BR', {
            style: 'currency',
            currency: 'BRL'
        }).format(valorNumerico);
    };

    // Adiciona as máscaras visuais aos campos
    proximaParcelaInput.addEventListener('input', mascaraData);
    ultimaParcelaInput.addEventListener('input', mascaraData);
    valorParcelaInput.addEventListener('input', mascaraDinheiro);
    valorEmprestadoInput.addEventListener('input', mascaraDinheiro);

    // --- 3. FUNÇÃO PARA LIMPAR AS MÁSCARAS ANTES DO ENVIO ---
    form.addEventListener('submit', function (event) {
        // Remove a máscara dos campos de valor, enviando apenas os dígitos
        // Ex: "R$ 203,07" se torna "20307"
        valorParcelaInput.value = valorParcelaInput.value.replace(/\D/g, '');
        valorEmprestadoInput.value = valorEmprestadoInput.value.replace(/\D/g, '');

        // Remove a máscara dos campos de data, enviando apenas os dígitos
        // Ex: "05/11/2025" se torna "05112025"
        proximaParcelaInput.value = proximaParcelaInput.value.replace(/\D/g, '');
        ultimaParcelaInput.value = ultimaParcelaInput.value.replace(/\D/g, '');
    });
});