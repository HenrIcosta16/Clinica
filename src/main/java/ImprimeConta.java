import java.text.NumberFormat;
import java.util.Map;

/**
 * Classe que representa a Impressão da conta do paciente que faz parte do 3 teste 
 * 
 * @author @euhenriquecosta16
 * 
 */


class ImprimeConta extends Conta {

    private final Conta contaPaciente;

    public ImprimeConta(Conta contaPaciente) {
        super(contaPaciente != null ? contaPaciente.getProntuario() : null);
        if (contaPaciente == null) {
            throw new IllegalArgumentException("A conta do paciente não pode ser nula.");
        }
        this.contaPaciente = contaPaciente;
    }

    @Override
    public String gerarResumo() {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();

        Prontuario prontuarioPaciente = this.contaPaciente.getProntuario();
        if (prontuarioPaciente == null) {
            return "Prontuário não encontrado para o paciente.";
        }

        float valorDiarias = contaPaciente.calcularValorDiarias();
        Map<TipoProcedimento, Long> procedimentos = contaPaciente.contarProcedimentos();
        float valorTotalProcedimentos = contaPaciente.calcularValorTotalProcedimentos(procedimentos);

        StringBuilder conta = new StringBuilder();
        conta.append("----------------------------------------------------------------------------------------------\n")
                .append("A conta do(a) paciente ").append(prontuarioPaciente.getNomePaciente())
                .append(" tem valor total de __ ").append(formatter.format(valorDiarias + valorTotalProcedimentos)).append(" __\n")
                .append("\nConforme os detalhes abaixo:\n");

        if (prontuarioPaciente.getInternacao() != null) {
            Internacao internacao = prontuarioPaciente.getInternacao();
            conta.append(String.format("\n%-40s\t%s\n", "Valor Total Diárias:", formatter.format(valorDiarias)))
                    .append(String.format("\t\t\t%s\n", "1 diária em " + internacao.getTipoLeito().toString().toLowerCase()));
        }

        conta.append("\nVolte sempre, a casa é sua!\n")
                .append("----------------------------------------------------------------------------------------------");

        return conta.toString();
    }
}
