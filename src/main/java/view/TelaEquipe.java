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

public class TelaEquipe extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel painelPretoFundo;
    private EstilizacaoRedonda.PainelRedondo painelFuncionario;

    public TelaEquipe() {

        setBackground(Color.DARK_GRAY);
        setSize(1280, 720);
        setMaximizedBounds(new Rectangle(0, 0, 1280, 720));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        painelPretoFundo = new JPanel();
        painelPretoFundo.setBackground(Color.WHITE);
        painelPretoFundo.setLayout(null);
        setContentPane(painelPretoFundo);

        JPanel barraSuperior = new JPanel();
        barraSuperior.setBackground(Color.DARK_GRAY);
        barraSuperior.setBounds(0, 0, 1280, 80);
        barraSuperior.setLayout(null);
        painelPretoFundo.add(barraSuperior);

        JLabel titulo = new JLabel("Equipe");
        titulo.setForeground(Color.WHITE);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setFont(new Font("Liberation Serif", Font.BOLD, 34));
        titulo.setBounds(440, 15, 400, 40);
        barraSuperior.add(titulo);

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
        barraSuperior.add(logoGaragem);

        EstilizacaoRedonda.BotaoRedondo botaoFiltros = new EstilizacaoRedonda.BotaoRedondo("", Color.BLACK, Color.DARK_GRAY, Color.GRAY, 40);
        botaoFiltros.setForeground(Color.WHITE);
        botaoFiltros.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoFiltros.setBounds(191, 92, 70, 60);
        java.net.URL urlIconeFiltro = getClass().getResource("/assets/imagens/iconeFiltro.png"); 
        if (urlIconeFiltro != null) {
            java.awt.Image iconeOriginal = new javax.swing.ImageIcon(urlIconeFiltro).getImage();
            java.awt.Image iconeRedimensionado = iconeOriginal.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
            botaoFiltros.setIcon(new javax.swing.ImageIcon(iconeRedimensionado));
            botaoFiltros.setIconTextGap(10); 
        } else {
            System.out.println("Ícone do botão filtro não encontrado!");
        }
        painelPretoFundo.add(botaoFiltros);
        
        EstilizacaoRedonda.CaixaTextoRedonda campoBusca = new EstilizacaoRedonda.CaixaTextoRedonda("Buscar", Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.DARK_GRAY, 0, 40);
        campoBusca.setFont(new Font("SansSerif", Font.PLAIN, 16));
        campoBusca.setBounds(300, 100, 600, 45);
        painelPretoFundo.add(campoBusca);

        EstilizacaoRedonda.BotaoRedondo botaoBuscar = new EstilizacaoRedonda.BotaoRedondo("", Color.BLACK, Color.DARK_GRAY, Color.GRAY, 40);
        botaoBuscar.setForeground(Color.WHITE);
        botaoBuscar.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoBuscar.setBounds(944, 92, 70, 60);
        java.net.URL urlIconeBuscar = getClass().getResource("/assets/imagens/iconeBuscar.png"); 
        if (urlIconeBuscar != null) {
            java.awt.Image iconeOriginal = new javax.swing.ImageIcon(urlIconeBuscar).getImage();
            java.awt.Image iconeRedimensionado = iconeOriginal.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
            botaoBuscar.setIcon(new javax.swing.ImageIcon(iconeRedimensionado));
            botaoBuscar.setIconTextGap(10); 
        } else {
            System.out.println("Ícone do botão buscar não encontrado!");
        }
        painelPretoFundo.add(botaoBuscar);

        EstilizacaoRedonda.BotaoRedondo botaoCadastrar = new EstilizacaoRedonda.BotaoRedondo("CADASTRAR FUNCIONÁRIO", Color.BLACK, Color.DARK_GRAY, Color.GRAY, 40);
        botaoCadastrar.setForeground(Color.WHITE);
        botaoCadastrar.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoCadastrar.setBounds(390, 170, 500, 50);
        painelPretoFundo.add(botaoCadastrar);
        botaoCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        painelFuncionario = new EstilizacaoRedonda.PainelRedondo(null, 30, 4, Color.WHITE, Color.BLACK);
        painelFuncionario.setBounds(30, 250, 1200, 150);
        painelPretoFundo.add(painelFuncionario);

        JLabel nomeFuncionario = new JLabel("Nome:");
        nomeFuncionario.setFont(new Font("SansSerif", Font.PLAIN, 20));
        nomeFuncionario.setBounds(25, 20, 120, 50);
        painelFuncionario.add(nomeFuncionario);

        JLabel cpfFuncionario = new JLabel("CPF:");
        cpfFuncionario.setFont(new Font("SansSerif", Font.PLAIN, 20));
        cpfFuncionario.setBounds(25, 82, 220, 50);
        painelFuncionario.add(cpfFuncionario);

        JLabel emailFuncionario = new JLabel("E-mail:");
        emailFuncionario.setFont(new Font("SansSerif", Font.PLAIN, 20));
        emailFuncionario.setBounds(317, 20, 280, 50);
        painelFuncionario.add(emailFuncionario);

        JLabel telefoneFuncionario = new JLabel("Telefone:");
        telefoneFuncionario.setFont(new Font("SansSerif", Font.PLAIN, 20));
        telefoneFuncionario.setBounds(317, 82, 220, 50);
        painelFuncionario.add(telefoneFuncionario);

        JLabel cargoFuncionario = new JLabel("Cargo:");
        cargoFuncionario.setFont(new Font("SansSerif", Font.PLAIN, 20));
        cargoFuncionario.setBounds(806, 20, 150, 50);
        painelFuncionario.add(cargoFuncionario);

        JLabel admissaoFuncionario = new JLabel("Admissão:");
        admissaoFuncionario.setFont(new Font("SansSerif", Font.PLAIN, 20));
        admissaoFuncionario.setBounds(806, 82, 160, 50);
        painelFuncionario.add(admissaoFuncionario);

        EstilizacaoRedonda.BotaoRedondo botaoEditar = new EstilizacaoRedonda.BotaoRedondo("", Color.BLACK, Color.DARK_GRAY, Color.GRAY, 40);
        botaoEditar.setForeground(Color.WHITE);
        botaoEditar.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoEditar.setBounds(1115, 50, 60, 50);
        java.net.URL urlIconeEditar = getClass().getResource("/assets/imagens/iconeEditar.png"); 
        if (urlIconeEditar != null) {
            java.awt.Image iconeOriginal = new javax.swing.ImageIcon(urlIconeEditar).getImage();
            java.awt.Image iconeRedimensionado = iconeOriginal.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
            botaoEditar.setIcon(new javax.swing.ImageIcon(iconeRedimensionado));
            botaoEditar.setIconTextGap(10); 
        } else {
            System.out.println("Ícone do botão editar não encontrado!");
        }
        painelFuncionario.add(botaoEditar);
        botaoEditar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TelaEditarCliente telaEditarCliente = new TelaEditarCliente();
                telaEditarCliente.setVisible(true);
                dispose();
            }
        });

        EstilizacaoRedonda.BotaoRedondo botaoVoltar = new EstilizacaoRedonda.BotaoRedondo("VOLTAR", Color.BLACK, Color.DARK_GRAY, Color.GRAY, 40);
        botaoVoltar.setForeground(Color.WHITE);
        botaoVoltar.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoVoltar.setBounds(1050, 600, 140, 50);
        painelPretoFundo.add(botaoVoltar);
        botaoVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TelaHome telaHome = new TelaHome();
                telaHome.setVisible(true);
                dispose();
            }
        });
    }
}