package nograu.site.pcd.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDate;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import nograu.site.pcd.model.Banco;
import nograu.site.pcd.repository.BancoRepository;

@Service
public class BancoService {

    private BancoRepository bancoRepository;

    BancoService(BancoRepository bancoRepository) {
        this.bancoRepository = bancoRepository;
    }

    public LocalDate dataPrimeiroRegistro() {
        return bancoRepository.findFirstByOrderByIdAsc().getCreatedAt();
    }

    @Transactional
    public void salvaBancosNovos() throws FileNotFoundException, IOException {

        String arquivo = "/tmp/bancos.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {

            br.readLine();
            String line;
            
            while ((line = br.readLine()) != null) {
                Iterable<CSVRecord> records = CSVFormat.DEFAULT
                .parse(new StringReader(line));

                records.forEach(i -> {
                    Banco banco = Banco.builder()
                                        .ispb(i.values()[0])
                                        .nomeReduzido(i.values()[1])
                                        .numeroCodigo(i.values()[2])
                                        .participaCompe(i.values()[3])
                                        .acessoPrincipal(i.values()[4])
                                        .nomeExtenso(i.values()[5])
                                        .inicioOperacao(i.values()[6])
                                        .build();
                    bancoRepository.save(banco);
                });
            }
        }
    }

    @Transactional
    public void apagaBancosAntigos() {
        bancoRepository.deleteAll();
    }

    public Banco buscaBanco(String numeroCodigo) throws IOException, InterruptedException {
        return bancoRepository.findByNumeroCodigo(formataCodigo(numeroCodigo));
    }

    private String formataCodigo(String codigo) {
        if(codigo.length() == 1) {
            return "00" + codigo;
        }
        if(codigo.length() == 2) {
            return "0" + codigo;
        }
        if(codigo.length() > 3) {
            return codigo.substring(codigo.length() -3);
        }
        return codigo;
    }

}
