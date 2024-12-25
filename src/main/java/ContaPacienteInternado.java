
import java.text.NumberFormat;
import java.util.Map;

/**
 * Classe que representa a Conta do paciente internado imprimida, herdeira de conta.
 * 
 * @author @euhenriquecosta16
 * 
 */


class ContaPacienteInternado extends Conta {

    public ContaPacienteInternado(Prontuario prontuario) {
        super(prontuario);
    }

    @Override
        public String gerarResumo() {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
    
        float valorDiarias = calcularValorDiarias();
        Map<TipoProcedimento, Long> procedimentos = contarProcedimentos();
    
        float valorTotalProcedimentos = calcularValorTotalProcedimentos(procedimentos);
        float valorTotal = valorDiarias + valorTotalProcedimentos;
    
        StringBuilder resumo = new StringBuilder("----------------------------------------------------------------------------------------------\n");
        resumo.append("A conta do(a) paciente ").append(prontuario.getNomePaciente())
                .append(" tem valor total de __ ").append(formatter.format(valorTotal)).append(" __\n")
                .append("\nConforme os detalhes abaixo:\n\n");
    
        if (valorDiarias > 0) {
            Internacao internacao = prontuario.getInternacao();
            resumo.append("Valor Total Diárias:\t\t\t").append(formatter.format(valorDiarias)).append("\n")
                    .append("\t\t\t\t\t").append(internacao.getQtdeDias()).append(" diária(s) em ")
                    .append(internacao.getTipoLeito().toString().toLowerCase()).append("\n\n");
        }
    
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
