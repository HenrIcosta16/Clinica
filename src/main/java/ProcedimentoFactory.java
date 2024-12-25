/**
 * Classe que representa o ProcedimentoFactory que realiza o metodo que cria o precedimento baseado no tipo
 * 
 * @author @euhenriquecosta16
 * 
 */

public class ProcedimentoFactory {

    public static Procedimento criarProcedimento(TipoProcedimento tipoProcedimento) {
        return switch (tipoProcedimento) {
            case BASICO -> new ProcedimentoBasico();
            case COMUM -> new ProcedimentoComum();
            case AVANCADO -> new ProcedimentoAvancado();
            default -> throw new IllegalArgumentException("Tipo de procedimento desconhecido");
        };
    }
}
