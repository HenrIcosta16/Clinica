import java.text.NumberFormat;
import java.util.Map;

/**
 * Classe que representa a Conta do paciente ao ser gerada, herdeira de conta.
 *
 * @author @euhenriquecosta16
 *
 */


public class ContaPaciente extends Conta {
    
    public ContaPaciente(Prontuario prontuario) {
        super(prontuario);
    }

    @Override
    public String gerarResumo() {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
    
        Map<TipoProcedimento, Long> procedimentos = contarProcedimentos();
        float valorTotalProcedimentos = calcularValorTotalProcedimentos(procedimentos);
    
        StringBuilder resumo = new StringBuilder("----------------------------------------------------------------------------------------------\n");
        resumo.append("A conta do(a) paciente ").append(prontuario.getNomePaciente())
                .append(" tem valor total de __ ").append(formatter.format(valorTotalProcedimentos)).append(" __\n")
                .append("\nConforme os detalhes abaixo:\n\n");
    
        if (valorTotalProcedimentos > 0) {
            resumo.append("Valor Total Procedimentos:\t\t").append(formatter.format(valorTotalProcedimentos)).append("\n");
    
            procedimentos.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(entry -> {
                        String tipoFormatado = entry.getKey().toString().toLowerCase().replace("basico", "básico").replace("avancado", "avançado");
                        resumo.append("\t\t\t\t\t").append(entry.getValue()).append(" procedimento ").append(tipoFormatado).append("\n");
                    });
        }
    
        resumo.append("\nVolte sempre, a casa é sua!\n");
        resumo.append("----------------------------------------------------------------------------------------------");
    
        return resumo.toString();
    }

}
