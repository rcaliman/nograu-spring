package nograu.site.pcd.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import nograu.site.pcd.dto.EmprestimoRequest;
import nograu.site.pcd.dto.EmprestimoResponse;
import nograu.site.pcd.mapper.BancoMapper;
import nograu.site.pcd.model.Emprestimo;
import nograu.site.pcd.repository.EmprestimoRepository;

@Service
public class EmprestimoService {

    private BancoService bancoService;
    private EmprestimoRepository emprestimoRepository;
    private CalculadoraFinanceiraService calculadoraFinanceiraService;

    EmprestimoService(BancoService bancoService,
            EmprestimoRepository emprestimoReponsitory,
            FileDownloadService fileDownloadService, 
            CalculadoraFinanceiraService calculadoraFinanceiraService) {

        this.bancoService = bancoService;
        this.emprestimoRepository = emprestimoReponsitory;
        this.calculadoraFinanceiraService = calculadoraFinanceiraService;
    }

    public Long contaCalculos() {
        return emprestimoRepository.countByCreatedAt(LocalDate.now());
    }

    @Transactional
    public Emprestimo gravar(Emprestimo emprestimo) {
        return emprestimoRepository.save(emprestimo);
    }

    public EmprestimoResponse emprestimoToDto(Emprestimo emprestimo) throws IOException, InterruptedException {
        return montaDto(emprestimo);
    }

    public Emprestimo calcularEmprestimo(EmprestimoRequest request) {
        var taxaJuros = calculadoraFinanceiraService.rate(request.quantidadeParcelas(), -request.valorParcela(), request.valorEmprestado());
        var mesesEmSer = calculadoraFinanceiraService.calculaMeses(request.proximaParcela(), request.ultimaParcela());
        var saldoDevedor = calculadoraFinanceiraService.pv(taxaJuros, mesesEmSer, request.valorParcela());
        Emprestimo emprestimo = new Emprestimo(null,
            request.codigoBanco(), 
            request.proximaParcela(), 
            request.ultimaParcela(), 
            request.quantidadeParcelas(), 
            request.valorParcela(), 
            request.valorEmprestado(),
            taxaJuros,
            mesesEmSer,
            saldoDevedor,
            LocalDate.now());
            return emprestimo;
    }

    public List<EmprestimoResponse> listar() {

        List<EmprestimoResponse> listResponse = new ArrayList<>();

        emprestimoRepository.findByCreatedAtOrderByIdDesc(LocalDate.now()).forEach(emprestimo -> {
            try {
                listResponse.add(montaDto(emprestimo));
            } catch (IOException | InterruptedException e) {
                e.getMessage();
            }
        });

        return listResponse;
    }

    private EmprestimoResponse montaDto(Emprestimo emprestimo) throws IOException, InterruptedException {
        var banco = bancoService.buscaBanco(emprestimo.getCodigoBanco());
        return EmprestimoResponse.builder()
                .proximaParcela(emprestimo.getProximaParcela())
                .ultimaParcela(emprestimo.getUltimaParcela())
                .quantidadeParcelas(emprestimo.getQuantidadeParcelas())
                .valorParcela(emprestimo.getValorParcela())
                .valorEmprestado(emprestimo.getValorEmprestado())
                .taxaJuros(emprestimo.getTaxaJuros())
                .mesesEmSer(emprestimo.getMesesEmSer())
                .saldoDevedor(emprestimo.getSaldoDevedor())
                .banco(BancoMapper.INSTANCE.toBancoResponse(banco))
                .createdAt(emprestimo.getCreatedAt())
                .build();
    }

}