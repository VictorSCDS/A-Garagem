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

public class TelaClientes extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel painelPretoFundo;
    private EstilizacaoRedonda.PainelRedondo painelCliente;

    public TelaClientes() {

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

        JLabel titulo = new JLabel("Clientes");
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

        EstilizacaoRedonda.BotaoRedondo botaoCadastrar = new EstilizacaoRedonda.BotaoRedondo("CADASTRAR CLIENTE",Color.BLACK, Color.DARK_GRAY, Color.GRAY,40);
        botaoCadastrar.setForeground(Color.WHITE);
        botaoCadastrar.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoCadastrar.setBounds(430, 170, 420, 50);
        botaoCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TelaCadastrarCliente telaCadastrarCliente = new TelaCadastrarCliente();
                telaCadastrarCliente.setVisible(true);
                dispose();
            }
        });
        
        painelPretoFundo.add(botaoCadastrar);
        painelCliente = new EstilizacaoRedonda.PainelRedondo(null, 30, 4, Color.WHITE, Color.BLACK);
        painelCliente.setBounds(40, 250, 1160, 170);
        painelPretoFundo.add(painelCliente);

        JLabel nomeCliente = new JLabel("Nome:");
        nomeCliente.setFont(new Font("SansSerif", Font.PLAIN, 24));
        nomeCliente.setBounds(30, 20, 180, 60);
        painelCliente.add(nomeCliente);

        JLabel cpfCliente = new JLabel("CPF:");
        cpfCliente.setFont(new Font("SansSerif", Font.PLAIN, 24));
        cpfCliente.setBounds(190, 20, 220, 60);
        painelCliente.add(cpfCliente);

        JLabel emailCliente = new JLabel("E-mail:");
        emailCliente.setFont(new Font("SansSerif", Font.PLAIN, 24));
        emailCliente.setBounds(430, 20, 300, 60);
        painelCliente.add(emailCliente);

        JLabel telefoneCliente = new JLabel("Telefone:");
        telefoneCliente.setFont(new Font("SansSerif", Font.PLAIN, 24));
        telefoneCliente.setBounds(730, 20, 250, 60);
        painelCliente.add(telefoneCliente);

        JLabel placaVeiculo = new JLabel("Placa do Veículo:");
        placaVeiculo.setFont(new Font("SansSerif", Font.PLAIN, 24));
        placaVeiculo.setBounds(30, 90, 360, 60);
        painelCliente.add(placaVeiculo);

        JLabel modeloVeiculo = new JLabel("Modelo do veículo:");
        modeloVeiculo.setFont(new Font("SansSerif", Font.PLAIN, 24));
        modeloVeiculo.setBounds(430, 98, 250, 60);
        painelCliente.add(modeloVeiculo);
        
        EstilizacaoRedonda.BotaoRedondo botaoEditar = new EstilizacaoRedonda.BotaoRedondo("", Color.BLACK, Color.DARK_GRAY, Color.GRAY, 40);
        botaoEditar.setForeground(Color.WHITE);
        botaoEditar.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoEditar.setBounds(1070, 58, 60, 50);
        java.net.URL urlIconeEditar = getClass().getResource("/assets/imagens/iconeEditar.png"); 
        if (urlIconeEditar != null) {
            java.awt.Image iconeOriginal = new javax.swing.ImageIcon(urlIconeEditar).getImage();
            java.awt.Image iconeRedimensionado = iconeOriginal.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
            botaoEditar.setIcon(new javax.swing.ImageIcon(iconeRedimensionado));
            botaoEditar.setIconTextGap(10); 
        } else {
            System.out.println("Ícone do botão editar não encontrado!");
        }
        painelCliente.add(botaoEditar);
        botaoEditar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TelaEditarCliente telaEditarCliente = new TelaEditarCliente();
                telaEditarCliente.setVisible(true);
                dispose();
            }
        });

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