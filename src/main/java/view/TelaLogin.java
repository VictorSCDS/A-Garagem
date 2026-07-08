package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Rectangle;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.SwingConstants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import controllers.FuncionarioController;
import exceptions.ControllerException;


public class TelaLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel painelPretoFundo;
	private EstilizacaoRedonda.CaixaTextoRedonda emailAreaText;
	private EstilizacaoRedonda.CaixaSenhaRedonda senhaAreaText;
	
	public TelaLogin() {
		setBackground(Color.DARK_GRAY);
		setSize(1280, 720);
		setMaximizedBounds(new Rectangle(0, 0, 1280, 720));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		
		painelPretoFundo = new JPanel();
		painelPretoFundo.setBackground(Color.DARK_GRAY);
		painelPretoFundo.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPretoFundo);
		painelPretoFundo.setLayout(null);
		
		JPanel painelBrancoFundo = new JPanel();
		painelBrancoFundo.setBackground(Color.WHITE);
		painelBrancoFundo.setBounds(640, 0, 640, 720);
		painelPretoFundo.add(painelBrancoFundo);
		painelBrancoFundo.setLayout(null);
		
		JLabel tituloBemvind = new JLabel("Bem-Vindo!");
		tituloBemvind.setBackground(Color.DARK_GRAY);
		tituloBemvind.setBounds(170, 82, 300, 50);
		painelBrancoFundo.add(tituloBemvind);
		tituloBemvind.setHorizontalAlignment(SwingConstants.CENTER);
		tituloBemvind.setForeground(Color.DARK_GRAY);
		tituloBemvind.setFont(new Font("Liberation Serif", Font.BOLD, 42));
		
		JTextPane textoFacaLog = new JTextPane();
		textoFacaLog.setBounds(160, 150, 320, 28);
		painelBrancoFundo.add(textoFacaLog);
		textoFacaLog.setText("Faça Login para acessar o sistema.");
		textoFacaLog.setOpaque(false);
		textoFacaLog.setForeground(Color.DARK_GRAY);
		textoFacaLog.setFont(new Font("SansSerif", Font.PLAIN, 18));
		textoFacaLog.setEditable(false);
		
		JLabel tituloEmail = new JLabel("E-mail");
		tituloEmail.setHorizontalAlignment(SwingConstants.CENTER);
		tituloEmail.setForeground(Color.DARK_GRAY);
		tituloEmail.setFont(new Font("Liberation Serif", Font.BOLD, 28));
		tituloEmail.setBounds(100, 220, 440, 35);
		painelBrancoFundo.add(tituloEmail);
		
		emailAreaText = new EstilizacaoRedonda.CaixaTextoRedonda("Digite seu e-mail", Color.GRAY, Color.WHITE,Color.GRAY,2, 25);
		emailAreaText.setFont(new Font("SansSerif", Font.PLAIN, 16));
		emailAreaText.setBounds(100, 260, 440, 45);
		painelBrancoFundo.add(emailAreaText);
	
		JLabel tituloSenha = new JLabel("Senha");
		tituloSenha.setHorizontalAlignment(SwingConstants.CENTER);
		tituloSenha.setForeground(Color.DARK_GRAY);
		tituloSenha.setFont(new Font("Liberation Serif", Font.BOLD, 28));
		tituloSenha.setBounds(100, 340, 440, 35);
		painelBrancoFundo.add(tituloSenha);
		
		senhaAreaText = new EstilizacaoRedonda.CaixaSenhaRedonda("Digite sua senha",Color.GRAY,Color.WHITE,Color.GRAY,2,25);
		senhaAreaText.setFont(new Font("SansSerif", Font.PLAIN, 16));
		senhaAreaText.setBounds(100, 380, 440, 45);
		painelBrancoFundo.add(senhaAreaText);
		
		EstilizacaoRedonda.BotaoRedondo botaoCadastrarSenha = new EstilizacaoRedonda.BotaoRedondo("Cadastrar Senha", Color.WHITE, new Color(240, 240, 240), Color.LIGHT_GRAY, 20);
		botaoCadastrarSenha.setForeground(Color.DARK_GRAY);
		botaoCadastrarSenha.setFont(new Font("SansSerif", Font.BOLD, 14));
		botaoCadastrarSenha.setBounds(220, 437, 190, 35);
		painelBrancoFundo.add(botaoCadastrarSenha);
		botaoCadastrarSenha.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TelaCadastrarSenha telaEmail = new TelaCadastrarSenha();
				telaEmail.setVisible(true);
				dispose();
			}
		});
		
		EstilizacaoRedonda.BotaoRedondo botaoEntrar = new EstilizacaoRedonda.BotaoRedondo("Entrar", Color.DARK_GRAY, Color.GRAY, Color.BLACK, 30);
		botaoEntrar.setForeground(Color.WHITE);
		botaoEntrar.setFont(new Font("SansSerif", Font.BOLD, 18));
		botaoEntrar.setBounds(220, 550, 200, 50);
		painelBrancoFundo.add(botaoEntrar);
		botaoEntrar.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {

		        String email = emailAreaText.getText();
		        String senha = new String(senhaAreaText.getPassword());

		        if(email.isEmpty() || senha.isEmpty()) {
		            JOptionPane.showMessageDialog(null, "Preencha todos os campos.");
		            return;
		        }

		        FuncionarioController controller = new FuncionarioController();

		        try {

		            if(controller.login(email, senha)) {
		            	
		                TelaHome telaHome = new TelaHome();
		                telaHome.setVisible(true);
		                dispose();

		            } 
		            else {
		                JOptionPane.showMessageDialog(null, "E-mail ou senha incorretos.");
		            }

		        } catch (ControllerException ex) {
		            JOptionPane.showMessageDialog(null, "Erro ao realizar login: " + ex.getMessage());
		            ex.printStackTrace();
		        }
		    }
		});

		java.net.URL urlImagem = getClass().getResource("/assets/imagens/logo.png");
		if (urlImagem != null) {
			java.awt.Image imagemOriginal = new javax.swing.ImageIcon(urlImagem).getImage();
			java.awt.image.BufferedImage imgRedimensionada = new java.awt.image.BufferedImage(
					150, 150, java.awt.image.BufferedImage.TYPE_INT_ARGB
			);
			java.awt.Graphics2D g2d = imgRedimensionada.createGraphics();
			g2d.setRenderingHint(java.awt.RenderingHints.KEY_INTERPOLATION, java.awt.RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2d.setRenderingHint(java.awt.RenderingHints.KEY_RENDERING, java.awt.RenderingHints.VALUE_RENDER_QUALITY);
			g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.drawImage(imagemOriginal, 0, 0, 150, 150, null);
			g2d.dispose();
			javax.swing.JLabel logoGaragem = new javax.swing.JLabel(new javax.swing.ImageIcon(imgRedimensionada));
			logoGaragem.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			logoGaragem.setBounds(250, 50, 150, 150);
			painelPretoFundo.add(logoGaragem);
			painelPretoFundo.revalidate();
			painelPretoFundo.repaint();
		} else {
			System.out.println("Imagem não encontrada. Problem no caminho!");
		}
		
		JLabel tituloGaragem = new JLabel("A Garagem");
		tituloGaragem.setHorizontalAlignment(SwingConstants.CENTER);
		tituloGaragem.setFont(new Font("Liberation Serif", Font.BOLD, 42));
		tituloGaragem.setForeground(new Color(240, 240, 240));
		tituloGaragem.setBounds(170, 200, 300, 50);
		painelPretoFundo.add(tituloGaragem);
		
		JTextPane textoParagrafo = new JTextPane();
		String textoBemv = "Bem-vindo ao sistema da A-Garagem. Por favor, insira suas " +
		                  "credenciais abaixo para acessar o painel de controle e " +
		                  "gerenciar seus veículos.";
		textoParagrafo.setText(textoBemv);
		textoParagrafo.setEditable(false);
		textoParagrafo.setOpaque(false);
		textoParagrafo.setForeground(Color.WHITE);
		textoParagrafo.setFont(new Font("SansSerif", Font.PLAIN, 18));
		StyledDocument doc = textoParagrafo.getStyledDocument();
		SimpleAttributeSet formatoJustificado = new SimpleAttributeSet();
		StyleConstants.setAlignment(formatoJustificado, StyleConstants.ALIGN_JUSTIFIED);
		doc.setParagraphAttributes(0, doc.getLength(), formatoJustificado, false);
		textoParagrafo.setBounds(120, 280, 400, 150); 
		painelPretoFundo.add(textoParagrafo);
	}
}
