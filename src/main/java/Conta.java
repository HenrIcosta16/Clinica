import java.util.Map;
import java.util.stream.Collectors;

/**
 * Classe que representa conta com os metodos de calculos da conta
 * 
 * @author @euhenriquecosta16
 * 
 */


public abstract class Conta {

    protected final Prontuario prontuario;

    public Conta(Prontuario prontuario) {
        if (prontuario == null) {
            throw new IllegalArgumentException("Prontuário não pode ser nulo");
        }
        this.prontuario = prontuario;
    }

    public abstract String gerarResumo();

    float calcularValorDiarias() {
        Internacao internacao = prontuario.getInternacao();
        if (internacao == null) {
            return 0;
        }

        return internacao.getTipoLeito().calcularValor(internacao.getQtdeDias());
    }

    float calcularValorTotalProcedimentos(Map<TipoProcedimento, Long> procedimentos) {
        float total = 0;

        
        for (Map.Entry<TipoProcedimento, Long> entry : procedimentos.entrySet()) {
            Procedimento procedimento = ProcedimentoFactory.criarProcedimento(entry.getKey());
            total += procedimento.getValor() * entry.getValue();
        }

        return total;
    }

    Map<TipoProcedimento, Long> contarProcedimentos() {
        if (prontuario.getProcedimentos() == null) {
            return Map.of();
        }

        
        return prontuario.getProcedimentos().stream()
                .collect(Collectors.groupingBy(Procedimento::getTipoProcedimento, Collectors.counting()));
    }

    public Prontuario getProntuario() {
        return prontuario;
    }
    
}
