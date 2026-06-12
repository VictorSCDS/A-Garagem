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

public class TelaOrdemServico extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel painelPretoFundo;
    private EstilizacaoRedonda.PainelRedondo painelServico;

    public TelaOrdemServico() {

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

        JLabel titulo = new JLabel("Ordem de Serviço");
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
        botaoFiltros.setBounds(236, 102, 70, 60);
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
        
        EstilizacaoRedonda.CaixaTextoRedonda campoBusca = new EstilizacaoRedonda.CaixaTextoRedonda("Buscar", Color.GRAY, Color.WHITE,Color.GRAY,2, 25);
        campoBusca.setFont(new Font("SansSerif", Font.PLAIN, 16));
        campoBusca.setBounds(340, 107, 600, 45);
        painelPretoFundo.add(campoBusca);
        
        EstilizacaoRedonda.BotaoRedondo botaoBuscar = new EstilizacaoRedonda.BotaoRedondo("", Color.BLACK, Color.DARK_GRAY, Color.GRAY, 40);
        botaoBuscar.setForeground(Color.WHITE);
        botaoBuscar.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoBuscar.setBounds(979, 102, 70, 60);
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

        EstilizacaoRedonda.BotaoRedondo botaoNovoServico = new EstilizacaoRedonda.BotaoRedondo("NOVO SERVIÇO", Color.BLACK, Color.DARK_GRAY, Color.GRAY, 40);
        botaoNovoServico.setForeground(Color.WHITE);
        botaoNovoServico.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoNovoServico.setBounds(430, 180, 420, 50);
        painelPretoFundo.add(botaoNovoServico);
        botaoNovoServico.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	TelaNovoServico telaNovoServico = new TelaNovoServico();
                telaNovoServico.setVisible(true);
                dispose();
            }
        });

        painelServico = new EstilizacaoRedonda.PainelRedondo(null, 30, 4, Color.WHITE, Color.BLACK);
        painelServico.setBounds(30, 260, 1200, 140);
        painelPretoFundo.add(painelServico);

        JLabel lblCliente = new JLabel("Cliente:");
        lblCliente.setFont(new Font("SansSerif", Font.PLAIN, 18));
        lblCliente.setBounds(25, 20, 100, 50);
        painelServico.add(lblCliente);

        JLabel lblVeiculo = new JLabel("Veículo:");
        lblVeiculo.setFont(new Font("SansSerif", Font.PLAIN, 18));
        lblVeiculo.setBounds(291, 20, 100, 50);
        painelServico.add(lblVeiculo);

        JLabel lblPlaca = new JLabel("Placa:");
        lblPlaca.setFont(new Font("SansSerif", Font.PLAIN, 18));
        lblPlaca.setBounds(25, 78, 110, 50);
        painelServico.add(lblPlaca);

        JLabel lblProblema = new JLabel("Problema:");
        lblProblema.setFont(new Font("SansSerif", Font.PLAIN, 18));
        lblProblema.setBounds(719, 28, 181, 65);
        painelServico.add(lblProblema);

        JLabel lblCusto = new JLabel("Custo atual:");
        lblCusto.setFont(new Font("SansSerif", Font.PLAIN, 18));
        lblCusto.setBounds(473, 35, 130, 50);
        painelServico.add(lblCusto);

        JLabel lblStatus = new JLabel("Status:");
        lblStatus.setFont(new Font("SansSerif", Font.PLAIN, 18));
        lblStatus.setBounds(291, 78, 150, 50);
        painelServico.add(lblStatus);

        EstilizacaoRedonda.BotaoRedondo botaoEditar = new EstilizacaoRedonda.BotaoRedondo("", Color.BLACK, Color.DARK_GRAY, Color.GRAY, 40);
        botaoEditar.setForeground(Color.WHITE);
        botaoEditar.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoEditar.setBounds(1112, 20, 60, 50);
        java.net.URL urlIconeEditar = getClass().getResource("/assets/imagens/iconeEditar.png"); 
        if (urlIconeEditar != null) {
            java.awt.Image iconeOriginal = new javax.swing.ImageIcon(urlIconeEditar).getImage();
            java.awt.Image iconeRedimensionado = iconeOriginal.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
            botaoEditar.setIcon(new javax.swing.ImageIcon(iconeRedimensionado));
            botaoEditar.setIconTextGap(10); 
        } else {
            System.out.println("Ícone do botão editar não encontrado!");
        }
        painelServico.add(botaoEditar);
        botaoEditar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TelaEditarServico telaEditarServico = new TelaEditarServico();
                telaEditarServico.setVisible(true);
                dispose();
            }
        });

        EstilizacaoRedonda.BotaoRedondo botaoDetalhes = new EstilizacaoRedonda.BotaoRedondo("", Color.BLACK, Color.DARK_GRAY, Color.GRAY, 40);
        botaoDetalhes.setForeground(Color.WHITE);
        botaoDetalhes.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoDetalhes.setBounds(1112, 78, 60, 50);
        java.net.URL urlIconeDetalhes = getClass().getResource("/assets/imagens/iconeDetalhes.png"); 
        if (urlIconeDetalhes != null) {
            java.awt.Image iconeOriginal = new javax.swing.ImageIcon(urlIconeDetalhes).getImage();
            java.awt.Image iconeRedimensionado = iconeOriginal.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
            botaoDetalhes.setIcon(new javax.swing.ImageIcon(iconeRedimensionado));
            botaoDetalhes.setIconTextGap(10); 
        } else {
            System.out.println("Ícone do botão detalhes não encontrado!");
        }
        painelServico.add(botaoDetalhes);

        EstilizacaoRedonda.BotaoRedondo botaoVoltar = new EstilizacaoRedonda.BotaoRedondo("", Color.BLACK, Color.DARK_GRAY, Color.GRAY, 40);
        botaoVoltar.setForeground(Color.WHITE);
        botaoVoltar.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoVoltar.setBounds(1130, 600, 90, 50);
        java.net.URL urlIconeSair = getClass().getResource("/assets/imagens/iconVoltar.png"); 
        if (urlIconeSair != null) {
            java.awt.Image iconeOriginal = new javax.swing.ImageIcon(urlIconeSair).getImage();
            java.awt.Image iconeRedimensionado = iconeOriginal.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
            botaoVoltar.setIcon(new javax.swing.ImageIcon(iconeRedimensionado));
            botaoVoltar.setIconTextGap(10); 
        } else {
            System.out.println("Ícone do botão sair não encontrado!");
        }
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