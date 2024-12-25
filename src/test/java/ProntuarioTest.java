import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProntuarioTest {
	private String normalize(String input) {
		return input.replaceAll("\\s+", " ").trim();
	}

	@After
	public void cleanUp() {

		DirectoryStream<Path> stream = null;
		try {
			stream = Files.newDirectoryStream(Paths.get("."), "*.csv");
			for (Path path : stream) {
				Files.delete(path);
			}
		} catch (IOException ioException) {
			ioException.printStackTrace();
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException ioException) {
					ioException.printStackTrace();
				}
			}
		}
	}

    @Test
    public void testSomenteProcedimentos() {
        Prontuario prontuario = new Prontuario("Paul McCartney");
        ProcedimentoBasico procedimentoBasico = new ProcedimentoBasico();
        ProcedimentoAvancado procedimentoAvancado = new ProcedimentoAvancado();
		
		ContaPaciente conta = new ContaPaciente(prontuario); 
    
        prontuario.addProcedimento(procedimentoBasico);
        prontuario.addProcedimento(procedimentoAvancado);
		final String respostaEsperada = "----------------------------------------------------------------------------------------------" +
				"\nA conta do(a) paciente Paul McCartney tem valor total de __ R$ 550,00 __" +
				"\n" +
				"\nConforme os detalhes abaixo:" +
				"\n" +
				"\nValor Total Procedimentos:		R$ 550,00" +
				"\n					1 procedimento básico" +
				"\n					1 procedimento avançado" +
				"\n" +
				"\nVolte sempre, a casa é sua!" +
				"\n----------------------------------------------------------------------------------------------";

		assertEquals(respostaEsperada, conta.gerarResumo());
		
	}

	@Test
	public void testInternacaoComProcedimentos() {
		Prontuario prontuario = new Prontuario("Nando Reis");
        Internacao internacao = new Internacao(TipoLeito.APARTAMENTO, 4);

        Procedimento procedimentoBasico = ProcedimentoFactory.criarProcedimento(TipoProcedimento.BASICO);
    	Procedimento procedimentoComum1 = ProcedimentoFactory.criarProcedimento(TipoProcedimento.COMUM);
    	Procedimento procedimentoComum2 = ProcedimentoFactory.criarProcedimento(TipoProcedimento.COMUM);
    	Procedimento procedimentoAvancado = ProcedimentoFactory.criarProcedimento(TipoProcedimento.AVANCADO);
		
        prontuario.setInternacao(internacao);
        prontuario.addProcedimento(procedimentoBasico);
        prontuario.addProcedimento(procedimentoComum1);
        prontuario.addProcedimento(procedimentoComum2);
        prontuario.addProcedimento(procedimentoAvancado);

        ContaPacienteInternado contaPaciente = new ContaPacienteInternado(prontuario);


		final String respostaEsperada = "----------------------------------------------------------------------------------------------" +
				"\nA conta do(a) paciente Nando Reis tem valor total de __ R$ 1.210,00 __" +
				"\n" +
				"\nConforme os detalhes abaixo:" +
				"\n" +
				"\nValor Total Diárias:			R$ 360,00" +
				"\n					4 diárias em apartamento" +
				"\n" +
				"\nValor Total Procedimentos:		R$ 850,00" +
				"\n					1 procedimento básico" +
				"\n					2 procedimentos comuns" +
				"\n					1 procedimento avançado" +
				"\n" +
				"\nVolte sempre, a casa é sua!" +
				"\n----------------------------------------------------------------------------------------------";

			
			
	}

	@Test
	public void testSomenteInternacao() {
		Prontuario prontuario = new Prontuario("MC Criolo");
        Internacao internacao = new Internacao(TipoLeito.ENFERMARIA, 1);
        prontuario.setInternacao(internacao);

		Conta conta = new ContaPacienteInternado(prontuario);

		ImprimeConta imprimeConta = new ImprimeConta(conta);

		final String respostaEsperada = "----------------------------------------------------------------------------------------------" +
				"\nA conta do(a) paciente MC Criolo tem valor total de __ R$ 40,00 __" +
				"\n" +
				"\nConforme os detalhes abaixo:" +
				"\n" +
				"\nValor Total Diárias:			R$ 40,00" +
				"\n					1 diária em enfermaria" +
				"\n" +
				"\nVolte sempre, a casa é sua!" +
				"\n----------------------------------------------------------------------------------------------";

				
			assertEquals(normalize(respostaEsperada), normalize(imprimeConta.gerarResumo()));
	}

	@Test
    public void testCarregarArquivoSemInternacao() throws IOException {
        String path = "src/test/resources/prontuario_exportado_sem_internacao.csv";
        
        Prontuario prontuario = Prontuario.carregarDeArquivo(path);

        assertEquals("Ermenegildo Godofredo", prontuario.getNomePaciente());
        assertNull(prontuario.getInternacao());

        Map<TipoProcedimento, Long> procedimentosAgrupados = prontuario.getProcedimentos().stream().collect(
                Collectors.groupingBy(Procedimento::getTipoProcedimento, Collectors.counting()));

        assertEquals(10L, procedimentosAgrupados.get(TipoProcedimento.BASICO).longValue());
        assertEquals(2L, procedimentosAgrupados.get(TipoProcedimento.COMUM).longValue());
        assertNull(procedimentosAgrupados.get(TipoProcedimento.AVANCADO));
    }

    @Test
    public void testCarregarArquivoSemProcedimentos() throws IOException {
        String path = "src/test/resources/prontuario_exportado_sem_procedimentos.csv";
        
        Prontuario prontuario = Prontuario.carregarDeArquivo(path);

        assertEquals("Ruither Silveira", prontuario.getNomePaciente());
        assertEquals(0, prontuario.getProcedimentos().size());

        Internacao internacao = prontuario.getInternacao();
		assertNotNull(internacao);
        assertEquals(10, internacao.getQtdeDias());
        assertEquals(TipoLeito.APARTAMENTO, internacao.getTipoLeito());
    }

    @Test
    public void testCarregarArquivoCompleto() throws IOException {
        String path = "src/test/resources/prontuario_exportado_completo.csv";
        
        Prontuario prontuario = Prontuario.carregarDeArquivo(path);

        assertEquals("Adalgisa da Silva", prontuario.getNomePaciente());
        Internacao internacao = prontuario.getInternacao();
		assertNotNull(internacao);
        assertEquals(20, internacao.getQtdeDias());
        assertEquals(TipoLeito.ENFERMARIA, internacao.getTipoLeito());

        
        Map<TipoProcedimento, Long> procedimentosAgrupados = prontuario.getProcedimentos().stream().collect(
                Collectors.groupingBy(Procedimento::getTipoProcedimento, Collectors.counting()));

        assertEquals(20L, procedimentosAgrupados.get(TipoProcedimento.BASICO).longValue());
        assertEquals(15L, procedimentosAgrupados.get(TipoProcedimento.AVANCADO).longValue());
        assertNull(procedimentosAgrupados.get(TipoProcedimento.COMUM));
    }
    
    @Test
    public void testSalvarProntuario() throws IOException {
        
        Prontuario prontuario = new Prontuario("Teste Paciente");
        prontuario.setInternacao(new Internacao(TipoLeito.APARTAMENTO, 5));
        prontuario.addProcedimento(ProcedimentoFactory.criarProcedimento(TipoProcedimento.BASICO));
        prontuario.addProcedimento(ProcedimentoFactory.criarProcedimento(TipoProcedimento.COMUM));

        
        ProntuarioCsvHandler handler = new ProntuarioCsvHandler();
        String path = handler.salvarArquivo(prontuario);

        assertNotNull(path);
        assertTrue(Files.exists(Paths.get(path)));
    }
}