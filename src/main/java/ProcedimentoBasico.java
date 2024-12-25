/**
 * Classe que representa o procedimento basico com polimorfismo e valores parametrizados, herdeira de procedimento.
 * 
 * @author @euhenriquecosta16
 * 
 */

public class ProcedimentoBasico extends Procedimento {

    public ProcedimentoBasico() {
        super(TipoProcedimento.BASICO, 50.00f);
    }

    @Override
    public String getDescricao() {
        return "procedimento básico";
    }
}