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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
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
				contStream.showText("OS #" + os.getId() + " — Estado: " + os.getEstado().name());
				pularLinha(contStream, cursorY);

				contStream.setFont(fontNormal, 12);
				contStream.showText("Cliente: " + nomeCliente + "  |  Placa: " + placaVeiculo);
				pularLinha(contStream, cursorY);
				contStream.showText("Funcionário responsável: " + nomeFuncionario);
				pularLinha(contStream, cursorY);
				contStream.showText("Problema: " + os.getProblema());
				pularLinha(contStream, cursorY);
				contStream.showText("Data de registro: " + (os.getDataRegistro() != null
						? os.getDataRegistro().toLocalDate().format(FORMATTER) : "N/A"));
				pularLinha(contStream, cursorY);
				contStream.showText("Custo total: R$ " + custo);
				pularDuasLinhas(contStream, cursorY);

				contStream = verificarNovaPagina(doc, contStream, cursorY, fontNormal);
			}

			gerarLinhaHorizontal(contStream, cursorY[0]);
			cursorY[0] -= 20;

			contStream.setFont(fontBold, 14);
			contStream.newLineAtOffset(0, -20);
			contStream.showText("Total geral dos serviços: R$ " + totalGeral);

			contStream.endText();
			contStream.close();

			PDDocumentInformation info = doc.getDocumentInformation();
			info.setTitle("Relatorio_Financeiro_" + LocalDate.now().format(FORMATTER));
			info.setAuthor("A Garagem");
			info.setCreationDate(Calendar.getInstance());

			String caminho = System.getProperty("user.home") + "/Documents/RelatorioFinanceiro_AGaragem.pdf";
			doc.save(caminho);

			return caminho;
		}
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
}