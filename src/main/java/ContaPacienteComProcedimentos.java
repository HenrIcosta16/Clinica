
import java.text.NumberFormat;
import java.util.Map;

/**
 * Classe que representa a Conta do paciente com os procedimentos imprimidos, herdeira de conta.
 * 
 * @author @euhenriquecosta16
 * 
 */

class ContaPacienteComProcedimentos extends Conta {

    public ContaPacienteComProcedimentos(Prontuario prontuario) {
        super(prontuario);
    }

    @Override
    public String gerarResumo() {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
    
        float valorDiarias = calcularValorDiarias();
        Map<TipoProcedimento, Long> procedimentos = contarProcedimentos();
    
        float valorTotalProcedimentos = calcularValorTotalProcedimentos(procedimentos);
        float valorTotal = valorDiarias + valorTotalProcedimentos;
    
        StringBuilder relatorio = new StringBuilder("----------------------------------------------------------------------------------------------\n");
        relatorio.append("A conta do(a) paciente ").append(prontuario.getNomePaciente())
                .append(" tem valor total de __ ").append(formatter.format(valorTotal)).append(" __\n")
                .append("\nConforme os detalhes abaixo:\n\n");
    
        if (valorDiarias > 0) {
            Internacao internacao = prontuario.getInternacao();
            relatorio.append("Valor Total Diárias:\t\t\t").append(formatter.format(valorDiarias)).append("\n")
                    .append("\t\t\t\t\t").append(internacao.getQtdeDias()).append(" diárias em ")
                    .append(internacao.getTipoLeito().toString().toLowerCase()).append("\n\n");
        }
    
        if (valorTotalProcedimentos > 0) {
            relatorio.append("Valor Total Procedimentos:\t\t").append(formatter.format(valorTotalProcedimentos)).append("\n");
    
            procedimentos.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(entry -> {
                        String tipo = entry.getKey().toString().toLowerCase()
                                .replace("basico", "básico")
                                .replace("comum", "comuns") // Alteração aqui
                                .replace("avancado", "avançado");
                        relatorio.append("\t\t\t\t\t").append(entry.getValue()).append(" procedimento ").append(tipo).append("\n");
                    });
        }
    
        relatorio.append("\nVolte sempre, a casa é sua!\n");
        relatorio.append("----------------------------------------------------------------------------------------------");
    
        return relatorio.toString();
    }
}
