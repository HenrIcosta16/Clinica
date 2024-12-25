/**
 * Classe que abstrata que representa o encapsulamento de procedimento
 * 
 * @author @euhenriquecosta16
 * 
 */

public abstract class Procedimento {
    private final TipoProcedimento tipoProcedimento;
    private final float valor;

    public Procedimento(TipoProcedimento tipoProcedimento, float valor) {
        this.tipoProcedimento = tipoProcedimento;
        this.valor = valor;
    }

    public TipoProcedimento getTipoProcedimento() {
        return tipoProcedimento;
    }

    public float getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return "Procedimento{" + "tipoProcedimento=" + tipoProcedimento + ", valor=" + valor + '}';
    }

    public abstract String getDescricao();
}
