/**
 * Classe que representa o prontuario e o metodo carregar arquivo csv.
 * 
 * @author @euhenriquecosta16
 * 
 */


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class Prontuario {

    private String nomePaciente;
    private Internacao internacao;
    private final Set<Procedimento> procedimentos = new HashSet<>();

    public Prontuario(String nomePaciente) {
        this.nomePaciente = nomePaciente;
    }

    public void setNomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
    }

    public String getNomePaciente() {
        return this.nomePaciente;
    }

    public void setInternacao(Internacao internacao) {
        this.internacao = internacao;
    }

    public Internacao getInternacao() {
        return this.internacao;
    }

    public void addProcedimento(Procedimento procedimento) {
        this.procedimentos.add(procedimento);
    }

    public Set<Procedimento> getProcedimentos() {
        return this.procedimentos;
    }


     public static Prontuario carregarDeArquivo(String arquivoCsv) throws IOException {
        var prontuario = new Prontuario(null);
    
        Path path = Paths.get(arquivoCsv);
    
        try (Stream<String> linhas = Files.lines(path)) {
            linhas.forEach((str) -> {
                
                if (str == null || str.trim().isEmpty() || str.startsWith("qtde_dias_internacao")) {
                    return; 
                }
    
                String[] dados = str.split(",");
                String nomePaciente = dados[0].trim();
    
                TipoLeito tipoLeito = null;
                if (dados[1] != null && !dados[1].trim().isEmpty()) {
                    try {
                        tipoLeito = TipoLeito.valueOf(dados[1].trim());
                    } catch (IllegalArgumentException e) {
                        System.err.println("Erro ao converter tipo_leito: " + dados[1] + " para enum. Valor inválido.");
                    }
                }
    
                int qtdeDiasInternacao = -1;
                if (dados[2] != null && !dados[2].trim().isEmpty()) {
                    try {
                        qtdeDiasInternacao = Integer.parseInt(dados[2].trim());
                    } catch (NumberFormatException e) {
                        System.err.println("Erro ao converter qtde_dias_internacao: " + dados[2] + " para inteiro.");
                    }
                }
    
                TipoProcedimento tipoProcedimento = null;
                if (dados[3] != null && !dados[3].trim().isEmpty()) {
                    try {
                        tipoProcedimento = TipoProcedimento.valueOf(dados[3].trim());
                    } catch (IllegalArgumentException e) {
                        System.err.println("Erro ao converter tipo_procedimento: " + dados[3] + " para enum. Valor inválido.");
                    }
                }
    
                int qtdeProcedimentos = -1;
                if (dados.length == 5 && dados[4] != null && !dados[4].trim().isEmpty()) {
                    try {
                        qtdeProcedimentos = Integer.parseInt(dados[4].trim());
                    } catch (NumberFormatException e) {
                        System.err.println("Erro ao converter qtde_procedimentos: " + dados[4] + " para inteiro.");
                    }
                }
    
                prontuario.setNomePaciente(nomePaciente);
    
                if (tipoLeito != null && qtdeDiasInternacao > 0) {
                    prontuario.setInternacao(new Internacao(tipoLeito, qtdeDiasInternacao));
                }
    
                if (tipoProcedimento != null && qtdeProcedimentos > 0) {
                    for (int i = 0; i < qtdeProcedimentos; i++) {
                        prontuario.addProcedimento(ProcedimentoFactory.criarProcedimento(tipoProcedimento));
                    }
                }
            });
        }
    
        return prontuario;
    }
}