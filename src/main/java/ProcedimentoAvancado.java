/**
 * Classe que representa o procedimento avançado com polimorfismo e valores parametrizados, herdeira de procedimento.
 * 
 * @author @euhenriquecosta16
 * 
 */

public class ProcedimentoAvancado extends Procedimento {

    public ProcedimentoAvancado() {
        super(TipoProcedimento.AVANCADO, 500.00f);
    }

    @Override
    public String getDescricao() {
        return "procedimento avançado";
    }
}