/**
 * Classe que utiliza polimorfismo do metodo da classe conta definindo o valor de cada tipo de procedimento com o calculo
 * 
 * @author @euhenriquecosta16
 * 
 */

public enum TipoProcedimento {
    BASICO {
        @Override
        public float calcularValor(int quantidade) {
            return 50.00f * quantidade;
        }

        @Override
        public String descricao() {
            return "básico";
        }
    },
    COMUM {
        @Override
        public float calcularValor(int quantidade) {
            return 150.00f * quantidade;
        }

        @Override
        public String descricao() {
            return "comum";
        }
    },
    AVANCADO {
        @Override
        public float calcularValor(int quantidade) {
            return 500.00f * quantidade;
        }

        @Override
        public String descricao() {
            return "avançado";
        }
    };

    public abstract float calcularValor(int quantidade);

    public abstract String descricao();
    
    @Override
    public String toString() {
        return descricao();
    }
}
