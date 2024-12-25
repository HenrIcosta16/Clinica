/**
 * Classe que utiliza polimorfismo do metodo da classe conta definindo o valor de cada tipo leito com o calculo
 * 
 * @author @euhenriquecosta16
 * 
 */

public enum TipoLeito {
    APARTAMENTO {
        @Override
        public float calcularValor(int qtdeDias) {
            float valorBase = 90.00f;
            return valorBase * qtdeDias;
        }
    },
    ENFERMARIA {
        @Override
        public float calcularValor(int qtdeDias) {
            float valorBase = 40.00f;
            return valorBase * qtdeDias;
        }
    };

    public abstract float calcularValor(int qtdeDias);


    public static TipoLeito obterTipoLeito(String tipoLeitoStr) throws IllegalArgumentException {
        if (tipoLeitoStr == null || tipoLeitoStr.trim().isEmpty()) {
            return null;
        }
    
        try {
            return TipoLeito.valueOf(tipoLeitoStr.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao converter tipo_leito: " + tipoLeitoStr + " para enum. Valor inválido.");
            throw new IllegalArgumentException("Tipo de leito inválido: " + tipoLeitoStr, e);
        }
    }
    
}
