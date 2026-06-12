package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaCadastrarFuncionario extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel painelPretoFundo;
    private JPanel faixaTitulo;
    private EstilizacaoRedonda.PainelRedondo painelBrancoFundo;

    private EstilizacaoRedonda.CaixaTextoRedonda nomeAreaText;
    private EstilizacaoRedonda.CaixaTextoRedonda cpfAreaText;
    private EstilizacaoRedonda.CaixaTextoRedonda cargoAreaText;
    private EstilizacaoRedonda.CaixaTextoRedonda telefoneAreaText;
    private EstilizacaoRedonda.CaixaTextoRedonda emailAreaText;
    private EstilizacaoRedonda.CaixaTextoRedonda dataAdmissaoAreaText;

    public TelaCadastrarFuncionario() {

        setBackground(Color.WHITE);
        setSize(1280, 720);
        setMaximizedBounds(new Rectangle(0, 0, 1280, 720));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        painelPretoFundo = new JPanel();
        painelPretoFundo.setBackground(Color.WHITE);
        painelPretoFundo.setLayout(null);
        setContentPane(painelPretoFundo);

        faixaTitulo = new JPanel();
        faixaTitulo.setBackground(Color.DARK_GRAY);
        faixaTitulo.setLayout(null);
        faixaTitulo.setBounds(0, 0, 1280, 80);
        painelPretoFundo.add(faixaTitulo);

        JLabel titulo = new JLabel("Cadastrar Funcionário");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 32));
        titulo.setBounds(340, 10, 600, 50);
        faixaTitulo.add(titulo);

        JLabel logoGaragem = new JLabel();
        logoGaragem.setHorizontalAlignment(SwingConstants.CENTER);
        logoGaragem.setBounds(1170, 5, 100, 70);
        java.net.URL urlImagem = getClass().getResource("/assets/imagens/logo.png");
        if (urlImagem != null) {
            java.awt.Image imagemOriginal = new javax.swing.ImageIcon(urlImagem).getImage();
            java.awt.Image imagemRedimensionada = imagemOriginal.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH);
            logoGaragem.setIcon(new javax.swing.ImageIcon(imagemRedimensionada));
        } else {
            logoGaragem.setText("Logo Aqui");
            logoGaragem.setForeground(Color.WHITE);
        }
        faixaTitulo.add(logoGaragem);
        
        painelBrancoFundo = new EstilizacaoRedonda.PainelRedondo(null, 0, 0, Color.WHITE, Color.WHITE);
        painelBrancoFundo.setBounds(0, 80, 1280, 640);
        painelBrancoFundo.setLayout(null);
        painelPretoFundo.add(painelBrancoFundo);

        JLabel lblNome = new JLabel("Nome");
        lblNome.setHorizontalAlignment(SwingConstants.CENTER);
        lblNome.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        lblNome.setBounds(160, 110, 200, 35);
        painelBrancoFundo.add(lblNome);
        
        nomeAreaText = new EstilizacaoRedonda.CaixaTextoRedonda("Digite o nome", Color.GRAY, Color.WHITE, Color.GRAY, 2, 25);
        nomeAreaText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        nomeAreaText.setBounds(100, 160, 320, 50);
        painelBrancoFundo.add(nomeAreaText);

        JLabel lblCpf = new JLabel("CPF");
        lblCpf.setHorizontalAlignment(SwingConstants.CENTER);
        lblCpf.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        lblCpf.setBounds(540, 110, 200, 35);
        painelBrancoFundo.add(lblCpf);
        
        cpfAreaText = new EstilizacaoRedonda.CaixaTextoRedonda("Digite o CPF", Color.GRAY, Color.WHITE, Color.GRAY, 2, 25);
        cpfAreaText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        cpfAreaText.setBounds(480, 160, 320, 50);
        painelBrancoFundo.add(cpfAreaText);

        JLabel lblCargo = new JLabel("Cargo");
        lblCargo.setHorizontalAlignment(SwingConstants.CENTER);
        lblCargo.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        lblCargo.setBounds(920, 110, 200, 35);
        painelBrancoFundo.add(lblCargo);
        
        cargoAreaText = new EstilizacaoRedonda.CaixaTextoRedonda("Digite o cargo", Color.GRAY, Color.WHITE, Color.GRAY, 2, 25);
        cargoAreaText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        cargoAreaText.setBounds(860, 160, 320, 50);
        painelBrancoFundo.add(cargoAreaText);

        JLabel lblTelefone = new JLabel("Telefone");
        lblTelefone.setHorizontalAlignment(SwingConstants.CENTER);
        lblTelefone.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        lblTelefone.setBounds(160, 310, 200, 35);
        painelBrancoFundo.add(lblTelefone);
        
        telefoneAreaText = new EstilizacaoRedonda.CaixaTextoRedonda("Digite o telefone", Color.GRAY, Color.WHITE, Color.GRAY, 2, 25);
        telefoneAreaText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        telefoneAreaText.setBounds(100, 360, 320, 50);
        painelBrancoFundo.add(telefoneAreaText);

        JLabel lblEmail = new JLabel("E-mail");
        lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
        lblEmail.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        lblEmail.setBounds(540, 310, 200, 35);
        painelBrancoFundo.add(lblEmail);
        
        emailAreaText = new EstilizacaoRedonda.CaixaTextoRedonda("Digite o e-mail", Color.GRAY, Color.WHITE, Color.GRAY, 2, 25);
        emailAreaText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        emailAreaText.setBounds(480, 360, 320, 50);
        painelBrancoFundo.add(emailAreaText);

        JLabel lblDataAdmissao = new JLabel("Data admissão");
        lblDataAdmissao.setHorizontalAlignment(SwingConstants.CENTER);
        lblDataAdmissao.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        lblDataAdmissao.setBounds(920, 310, 200, 35);
        painelBrancoFundo.add(lblDataAdmissao);
        
        dataAdmissaoAreaText = new EstilizacaoRedonda.CaixaTextoRedonda("DD/MM/AAAA", Color.GRAY, Color.WHITE, Color.GRAY, 2, 25);
        dataAdmissaoAreaText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        dataAdmissaoAreaText.setBounds(860, 360, 320, 50);
        painelBrancoFundo.add(dataAdmissaoAreaText);

        EstilizacaoRedonda.BotaoRedondo botaoAdicionar = new EstilizacaoRedonda.BotaoRedondo("ADICIONAR", Color.BLACK, Color.DARK_GRAY, Color.GRAY, 40);
        botaoAdicionar.setForeground(Color.WHITE);
        botaoAdicionar.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoAdicionar.setBounds(510, 520, 260, 50);
        painelBrancoFundo.add(botaoAdicionar);
        botaoAdicionar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	TelaEquipe telaEquipe = new TelaEquipe();
                telaEquipe.setVisible(true);
                dispose();
            }
        });
        
        EstilizacaoRedonda.BotaoRedondo botaoVoltar = new EstilizacaoRedonda.BotaoRedondo("", Color.BLACK, Color.DARK_GRAY, Color.GRAY, 40);
        botaoVoltar.setForeground(Color.WHITE);
        botaoVoltar.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoVoltar.setBounds(1100, 520, 90, 50);
        java.net.URL urlIconeSair = getClass().getResource("/assets/imagens/iconVoltar.png"); 
        if (urlIconeSair != null) {
            java.awt.Image iconeOriginal = new javax.swing.ImageIcon(urlIconeSair).getImage();
            java.awt.Image iconeRedimensionado = iconeOriginal.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
            botaoVoltar.setIcon(new javax.swing.ImageIcon(iconeRedimensionado));
            botaoVoltar.setIconTextGap(10); 
        } else {
            System.out.println("Ícone do botão sair não encontrado!");
        }
        painelBrancoFundo.add(botaoVoltar);
        botaoVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TelaEquipe telaEquipe = new TelaEquipe();
                telaEquipe.setVisible(true);
                dispose();
            }
        });
    }
}