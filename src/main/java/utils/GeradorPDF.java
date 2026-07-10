package utils;

import dao.FuncionarioDAO;
import dao.ClienteDAO;
import dao.VeiculoDAO;
import dao.OrdemServicoDAO;
import entities.Funcionario;
import entities.Cliente;
import entities.Veiculo;
import entities.OrdemServico;
import exceptions.DatabaseException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GeradorPDF {

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static final float MARGEM = 50f;
	private static final float LEADING = 16f;

	public static String gerarRelatorioFinanceiro() throws IOException {
		Logger.getLogger("org.apache.pdfbox").setLevel(Level.SEVERE);

		OrdemServicoDAO osDAO = new OrdemServicoDAO();
		ClienteDAO clienteDAO = new ClienteDAO();
		VeiculoDAO veiculoDAO = new VeiculoDAO();
		FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

		List<OrdemServico> ordens;
		try {
			ordens = osDAO.buscarTodos();
		} catch (DatabaseException e) {
			throw new IOException("Erro ao buscar ordens de serviço: " + e.getMessage());
		}

		try (PDDocument doc = new PDDocument()) {

			PDPage page = new PDPage(PDRectangle.A4);
			doc.addPage(page);

			PDType1Font fontNormal = new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN);
			PDType1Font fontBold = new PDType1Font(Standard14Fonts.FontName.TIMES_BOLD);

			float[] cursorY = {PDRectangle.A4.getHeight() - MARGEM - 100};
			PDPageContentStream contStream = new PDPageContentStream(doc, page);

			gerarCabecalho(doc, page, contStream, fontBold, fontNormal);

			contStream.beginText();
			contStream.setFont(fontNormal, 12);
			contStream.setLeading(LEADING);
			contStream.newLineAtOffset(MARGEM, cursorY[0]);

			BigDecimal totalGeral = BigDecimal.ZERO;

			for (OrdemServico os : ordens) {
				contStream = verificarNovaPagina(doc, contStream, cursorY, fontNormal);

				String nomeCliente = "Não encontrado";
				String placaVeiculo = "Não encontrada";
				String nomeFuncionario = "Não encontrado";
				BigDecimal custo = BigDecimal.ZERO;

				try {
					Optional<Veiculo> veiculoOpt = veiculoDAO.buscarPorAtributoIdentificador(
							veiculoDAO.buscarPlacaPorIdVeiculo(os.getIdVeiculo()));
					if (veiculoOpt.isPresent()) {
						placaVeiculo = veiculoOpt.get().getPlaca();
						Optional<Cliente> clienteOpt = clienteDAO.buscarClientePorPlaca(placaVeiculo);
						if (clienteOpt.isPresent()) nomeCliente = clienteOpt.get().getNome();
					}

					Optional<Funcionario> funcionarioOpt = funcionarioDAO.buscarFuncionarioPorId(os.getIdFuncionarioResponsavel());
					if (funcionarioOpt.isPresent()) nomeFuncionario = funcionarioOpt.get().getNome();

					Optional<BigDecimal> custoOpt = osDAO.buscarCustoAtualServico(os.getId());
					custo = custoOpt.orElse(BigDecimal.ZERO);
					totalGeral = totalGeral.add(custo);

				} catch (DatabaseException e) {
					e.printStackTrace();
				}

				contStream.setFont(fontBold, 12);
				contStream.showText(textoSeguro("OS #" + os.getId() + " - Estado: " + os.getEstado().name()));
				pularLinha(contStream, cursorY);

				contStream.setFont(fontNormal, 12);
				contStream.showText(textoSeguro("Cliente: " + nomeCliente + "  |  Placa: " + placaVeiculo));
				pularLinha(contStream, cursorY);
				contStream.showText(textoSeguro("Funcionário responsável: " + nomeFuncionario));
				pularLinha(contStream, cursorY);
				contStream.showText(textoSeguro("Problema: " + os.getProblema()));
				pularLinha(contStream, cursorY);
				contStream.showText(textoSeguro("Data de registro: " + (os.getDataRegistro() != null
						? os.getDataRegistro().toLocalDate().format(FORMATTER) : "N/A")));
				pularLinha(contStream, cursorY);
				contStream.showText(textoSeguro("Custo total: R$ " + custo));
				pularDuasLinhas(contStream, cursorY);

				contStream = verificarNovaPagina(doc, contStream, cursorY, fontNormal);
			}

			contStream.endText();

			gerarLinhaHorizontal(contStream, cursorY[0]);
			cursorY[0] -= 20;

			contStream.beginText();
			contStream.setFont(fontBold, 14);
			contStream.setLeading(LEADING);
			contStream.newLineAtOffset(MARGEM, cursorY[0]);
			contStream.showText(textoSeguro("Total geral dos serviços: R$ " + totalGeral));
			contStream.endText();
			contStream.close();

			PDDocumentInformation info = doc.getDocumentInformation();
			info.setTitle("Relatorio_Financeiro_" + LocalDate.now().format(FORMATTER));
			info.setAuthor("A Garagem");
			info.setCreationDate(Calendar.getInstance());

			File pastaDocumentos = new File(System.getProperty("user.home"), "Documents");
			if (!pastaDocumentos.exists()) {
				pastaDocumentos.mkdirs();
			}

			String caminho = new File(pastaDocumentos, "RelatorioFinanceiro_AGaragem.pdf").getAbsolutePath();
			doc.save(caminho);

			return caminho;
		}
	}


    public static String gerarOrcamentoOrdemServico(OrdemServico os) throws IOException {
        if (os == null) {
            throw new IOException("Ordem de serviço não informada para gerar o orçamento.");
        }

        Logger.getLogger("org.apache.pdfbox").setLevel(Level.SEVERE);

        OrdemServicoDAO osDAO = new OrdemServicoDAO();

        Cliente cliente = null;
        Veiculo veiculo = null;
        Funcionario funcionario = null;
        List<String> servicos = new ArrayList<>();
        List<String> pecas = new ArrayList<>();
        BigDecimal custo = BigDecimal.ZERO;

        try {
            cliente = osDAO.buscarClientePorOrdemServico(os.getId()).orElse(null);
            veiculo = osDAO.buscarVeiculoPorId(os.getIdVeiculo()).orElse(null);
            funcionario = osDAO.buscarFuncionarioResponsavelCompleto(os.getIdFuncionarioResponsavel()).orElse(null);
            servicos = osDAO.buscarServicosAplicadosDetalhados(os.getId());
            pecas = osDAO.buscarPecasAplicadasDetalhadas(os.getId());
            custo = osDAO.buscarCustoAtualServico(os.getId()).orElse(BigDecimal.ZERO);
        } catch (DatabaseException e) {
            throw new IOException("Erro ao buscar informações para gerar o orçamento: " + e.getMessage(), e);
        }

        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            doc.addPage(page);

            PDType1Font fontNormal = new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN);
            PDType1Font fontBold = new PDType1Font(Standard14Fonts.FontName.TIMES_BOLD);

            float[] cursorY = {PDRectangle.A4.getHeight() - MARGEM - 120};
            PDPageContentStream contStream = new PDPageContentStream(doc, page);

            gerarCabecalhoPersonalizado(doc, page, contStream, fontBold, fontNormal, "A Garagem - Orçamento de Serviço");

            contStream.beginText();
            contStream.setFont(fontNormal, 12);
            contStream.setLeading(LEADING);
            contStream.newLineAtOffset(MARGEM, cursorY[0]);

            contStream = escreverLinha(doc, contStream, cursorY, fontBold, 14, "Ordem de Serviço #" + os.getId());
            contStream = escreverLinha(doc, contStream, cursorY, fontNormal, 12, "Data de registro: " + formatarData(os));
            contStream = escreverLinha(doc, contStream, cursorY, fontNormal, 12, "Status: " + (os.getEstado() != null ? os.getEstado().name() : "Não informado"));
            contStream = pularLinhaComRetorno(doc, contStream, cursorY, fontNormal);

            contStream = escreverLinha(doc, contStream, cursorY, fontBold, 13, "Dados do cliente");
            contStream = escreverLinha(doc, contStream, cursorY, fontNormal, 12, "Nome: " + valorOuPadrao(cliente != null ? cliente.getNome() : null));
            contStream = escreverLinha(doc, contStream, cursorY, fontNormal, 12, "CPF: " + valorOuPadrao(cliente != null ? cliente.getCpf() : null));
            contStream = escreverLinha(doc, contStream, cursorY, fontNormal, 12, "Telefone: " + valorOuPadrao(cliente != null ? cliente.getTelefone() : null));
            contStream = escreverLinha(doc, contStream, cursorY, fontNormal, 12, "E-mail: " + valorOuPadrao(cliente != null ? cliente.getEmail() : null));
            contStream = pularLinhaComRetorno(doc, contStream, cursorY, fontNormal);

            contStream = escreverLinha(doc, contStream, cursorY, fontBold, 13, "Dados do veículo");
            contStream = escreverLinha(doc, contStream, cursorY, fontNormal, 12, "Placa: " + valorOuPadrao(veiculo != null ? veiculo.getPlaca() : null));
            contStream = escreverLinha(doc, contStream, cursorY, fontNormal, 12, "Marca: " + valorOuPadrao(veiculo != null ? veiculo.getMarca() : null));
            contStream = escreverLinha(doc, contStream, cursorY, fontNormal, 12, "Modelo: " + valorOuPadrao(veiculo != null ? veiculo.getModelo() : null));
            contStream = pularLinhaComRetorno(doc, contStream, cursorY, fontNormal);

            contStream = escreverLinha(doc, contStream, cursorY, fontBold, 13, "Responsável técnico");
            contStream = escreverLinha(doc, contStream, cursorY, fontNormal, 12, "Funcionário: " + valorOuPadrao(funcionario != null ? funcionario.getNome() : null));
            contStream = pularLinhaComRetorno(doc, contStream, cursorY, fontNormal);

            contStream = escreverTextoQuebrado(doc, contStream, cursorY, fontBold, 13, "Problema informado");
            contStream = escreverTextoQuebrado(doc, contStream, cursorY, fontNormal, 12, valorOuPadrao(os.getProblema()));
            contStream = pularLinhaComRetorno(doc, contStream, cursorY, fontNormal);

            contStream = escreverTextoQuebrado(doc, contStream, cursorY, fontBold, 13, "Nota técnica");
            contStream = escreverTextoQuebrado(doc, contStream, cursorY, fontNormal, 12, valorOuPadrao(os.getDescricao()));
            contStream = pularLinhaComRetorno(doc, contStream, cursorY, fontNormal);

            contStream = escreverLinha(doc, contStream, cursorY, fontBold, 13, "Serviços aplicados");
            if (servicos.isEmpty()) {
                contStream = escreverLinha(doc, contStream, cursorY, fontNormal, 12, "Nenhum serviço aplicado informado.");
            } else {
                for (String servico : servicos) {
                    contStream = escreverTextoQuebrado(doc, contStream, cursorY, fontNormal, 12, "- " + servico);
                }
            }
            contStream = pularLinhaComRetorno(doc, contStream, cursorY, fontNormal);

            contStream = escreverLinha(doc, contStream, cursorY, fontBold, 13, "Peças utilizadas");
            if (pecas.isEmpty()) {
                contStream = escreverLinha(doc, contStream, cursorY, fontNormal, 12, "Nenhuma peça utilizada informada.");
            } else {
                for (String peca : pecas) {
                    contStream = escreverTextoQuebrado(doc, contStream, cursorY, fontNormal, 12, "- " + peca);
                }
            }
            contStream = pularLinhaComRetorno(doc, contStream, cursorY, fontNormal);

            contStream = pularLinhaComRetorno(doc, contStream, cursorY, fontNormal);
            contStream = escreverLinha(doc, contStream, cursorY, fontBold, 16, "Valor total do orçamento: R$ " + custo);

            contStream.endText();
            contStream.close();

            PDDocumentInformation info = doc.getDocumentInformation();
            info.setTitle("Orcamento_OS_" + os.getId() + "_AGaragem");
            info.setAuthor("A Garagem");
            info.setCreationDate(Calendar.getInstance());

            File pastaDocumentos = new File(System.getProperty("user.home"), "Documents");
            if (!pastaDocumentos.exists()) {
                pastaDocumentos.mkdirs();
            }

            String caminho = new File(pastaDocumentos, "Orcamento_OS_" + os.getId() + "_AGaragem.pdf").getAbsolutePath();
            doc.save(caminho);
            return caminho;
        }
    }


    private static void gerarCabecalhoPersonalizado(PDDocument doc,
                                                   PDPage page,
                                                   PDPageContentStream stream,
                                                   PDType1Font fontBold,
                                                   PDType1Font fontNormal,
                                                   String titulo) throws IOException {
        float topo = page.getMediaBox().getHeight();

        try (InputStream inpStream = GeradorPDF.class.getResourceAsStream("/assets/imagens/logo.png")) {
            if (inpStream != null) {
                BufferedImage bufferedImage = ImageIO.read(inpStream);
                PDImageXObject logo = LosslessFactory.createFromImage(doc, bufferedImage);
                stream.drawImage(logo, MARGEM, topo - 100, 80, 80);
            }
        }

        stream.beginText();
        stream.setFont(fontBold, 20);
        stream.setLeading(LEADING);
        stream.newLineAtOffset(140, topo - 60);
        stream.showText(textoSeguro(titulo));
        stream.newLine();
        stream.setFont(fontNormal, 14);
        stream.showText("Gerado em: " + LocalDate.now().format(FORMATTER));
        stream.endText();

        gerarLinhaHorizontal(stream, topo - 110);
    }


	private static void gerarCabecalho(PDDocument doc,
	                                   PDPage page,
	                                   PDPageContentStream stream,
	                                   PDType1Font fontBold,
	                                   PDType1Font fontNormal) throws IOException {
		float topo = page.getMediaBox().getHeight();

		try (InputStream inpStream = GeradorPDF.class.getResourceAsStream("/assets/imagens/logo.png")) {
			if (inpStream == null) throw new IOException("Imagem não encontrada: /assets/imagens/logo.png");
			BufferedImage bufferedImage = ImageIO.read(inpStream);
			PDImageXObject logo = LosslessFactory.createFromImage(doc, bufferedImage);
			stream.drawImage(logo, MARGEM, topo - 100, 80, 80);
		}

		stream.beginText();
		stream.setFont(fontBold, 20);
		stream.setLeading(LEADING);
		stream.newLineAtOffset(140, topo - 60);
		stream.showText("A Garagem — Relatório Financeiro");
		stream.newLine();
		stream.setFont(fontNormal, 14);
		stream.showText("Gerado em: " + LocalDate.now().format(FORMATTER));
		stream.endText();

		gerarLinhaHorizontal(stream, topo - 110);
	}

	private static void gerarLinhaHorizontal(PDPageContentStream stream, float y) throws IOException {
		stream.moveTo(MARGEM, y);
		stream.lineTo(PDRectangle.A4.getWidth() - MARGEM, y);
		stream.stroke();
	}

	private static PDPageContentStream verificarNovaPagina(PDDocument doc,
	                                                       PDPageContentStream stream,
	                                                       float[] cursorY,
	                                                       PDType1Font font) throws IOException {
		if (cursorY[0] <= MARGEM + 50) {
			stream.endText();
			stream.close();

			PDPage novaPage = new PDPage(PDRectangle.A4);
			doc.addPage(novaPage);
			cursorY[0] = PDRectangle.A4.getHeight() - MARGEM;

			stream = new PDPageContentStream(doc, novaPage);
			stream.beginText();
			stream.setFont(font, 12);
			stream.setLeading(LEADING);
			stream.newLineAtOffset(MARGEM, cursorY[0]);
		}
		return stream;
	}

	private static void pularLinha(PDPageContentStream stream, float[] cursorY) throws IOException {
		stream.newLine();
		cursorY[0] -= LEADING;
	}

	private static void pularDuasLinhas(PDPageContentStream stream, float[] cursorY) throws IOException {
		pularLinha(stream, cursorY);
		pularLinha(stream, cursorY);
	}

    private static PDPageContentStream escreverLinha(PDDocument doc,
                                                     PDPageContentStream stream,
                                                     float[] cursorY,
                                                     PDType1Font font,
                                                     int tamanhoFonte,
                                                     String texto) throws IOException {
        stream = verificarNovaPagina(doc, stream, cursorY, font);
        stream.setFont(font, tamanhoFonte);
        stream.showText(textoSeguro(texto));
        pularLinha(stream, cursorY);
        return stream;
    }

    private static PDPageContentStream escreverTextoQuebrado(PDDocument doc,
                                                             PDPageContentStream stream,
                                                             float[] cursorY,
                                                             PDType1Font font,
                                                             int tamanhoFonte,
                                                             String texto) throws IOException {
        for (String linha : quebrarTexto(textoSeguro(texto), 92)) {
            stream = escreverLinha(doc, stream, cursorY, font, tamanhoFonte, linha);
        }
        return stream;
    }

    private static PDPageContentStream pularLinhaComRetorno(PDDocument doc,
                                                            PDPageContentStream stream,
                                                            float[] cursorY,
                                                            PDType1Font font) throws IOException {
        stream = verificarNovaPagina(doc, stream, cursorY, font);
        pularLinha(stream, cursorY);
        return stream;
    }

    private static List<String> quebrarTexto(String texto, int limite) {
        List<String> linhas = new ArrayList<>();
        if (texto == null || texto.isEmpty()) {
            linhas.add("");
            return linhas;
        }

        String restante = texto;
        while (restante.length() > limite) {
            int corte = restante.lastIndexOf(' ', limite);
            if (corte <= 0) corte = limite;
            linhas.add(restante.substring(0, corte).trim());
            restante = restante.substring(corte).trim();
        }
        if (!restante.isEmpty()) linhas.add(restante);
        return linhas;
    }

    private static String textoSeguro(String texto) {
        if (texto == null) return "";
        return texto.replace('\n', ' ')
                .replace('\r', ' ')
                .replace('—', '-')
                .replace('–', '-');
    }

    private static String valorOuPadrao(String valor) {
        if (valor == null || valor.trim().isEmpty()) return "Não informado";
        return valor;
    }

    private static String formatarData(OrdemServico os) {
        if (os.getDataRegistro() == null) return "Não informada";
        return os.getDataRegistro().toLocalDate().format(FORMATTER);
    }

}