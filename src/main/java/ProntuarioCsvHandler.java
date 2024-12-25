import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que vai ler o arquivo csv o retornando e salvando.
 * 
 * @author @euhenriquecosta16
 * 
 */

public class ProntuarioCsvHandler {

    public Prontuario lerArquivo(String arquivoCsv) throws IOException {
        Path path = Paths.get(arquivoCsv);

        if (!Files.exists(path)) {
            throw new IOException("Arquivo não encontrado: " + arquivoCsv);
        }

        List<String> linhas = Files.readAllLines(path);

        if (linhas.isEmpty()) {
            throw new IOException("O arquivo CSV está vazio.");
        }

        Prontuario prontuario = new Prontuario(linhas.get(0).split(",")[0].trim());

        for (String linha : linhas.subList(1, linhas.size())) {  
            String[] dados = linha.split(",");

            if (dados.length >= 3 && !dados[1].isEmpty() && !dados[2].isEmpty()) {
                prontuario.setInternacao(new Internacao(
                        TipoLeito.valueOf(dados[1].trim()),
                        Integer.parseInt(dados[2].trim())));
            }

            if (dados.length >= 5 && !dados[3].isEmpty() && !dados[4].isEmpty()) {
                TipoProcedimento tipoProcedimento = TipoProcedimento.valueOf(dados[3].trim());
                int qtdeProcedimentos = Integer.parseInt(dados[4].trim());

                for (int i = 0; i < qtdeProcedimentos; i++) {
                    Procedimento procedimento = ProcedimentoFactory.criarProcedimento(tipoProcedimento);
                    prontuario.addProcedimento(procedimento);
                }
            }
        }

        return prontuario;
    }

    public String salvarArquivo(Prontuario prontuario) throws IOException {
        List<String> linhas = new ArrayList<>();
        linhas.add("nome_paciente,tipo_leito,qtde_dias_internacao,tipo_procedimento,qtde_procedimentos");

        if (prontuario.getInternacao() != null) {
            Internacao internacao = prontuario.getInternacao();
            linhas.add(String.format("%s,%s,%d,,", 
                    prontuario.getNomePaciente(),
                    internacao.getTipoLeito(),
                    internacao.getQtdeDias()));
        }

        for (Procedimento procedimento : prontuario.getProcedimentos()) {
            linhas.add(String.format("%s,,, %s,%d", 
                    prontuario.getNomePaciente(),
                    procedimento.getTipoProcedimento(),
                    1));
        }

        Path path = Paths.get(prontuario.getNomePaciente() + ".csv");
        Files.write(path, linhas);
        return path.toString();
    }
}
