/**
 * Classe que representa o procedimento comum com polimorfismo e valores parametrizados, herdeira de procedimento.
 * 
 * @author @euhenriquecosta16
 * 
 */

public class ProcedimentoComum extends Procedimento {

    public ProcedimentoComum() {
        super(TipoProcedimento.COMUM, 150.00f); 
    }

    @Override
    public String getDescricao() {
        return "procedimento comum";
    }
}