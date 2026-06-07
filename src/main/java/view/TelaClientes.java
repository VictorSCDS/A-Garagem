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

        JLabel logoGaragem = new JLabel("Logo Aqui");
        logoGaragem.setForeground(Color.WHITE);
        logoGaragem.setHorizontalAlignment(SwingConstants.CENTER);
        logoGaragem.setBounds(1120, 5, 100, 70);
        barraSuperior.add(logoGaragem);

        EstilizacaoRedonda.BotaoRedondo botaoFiltros = new EstilizacaoRedonda.BotaoRedondo("FILTROS", Color.BLACK, Color.DARK_GRAY, Color.GRAY, 40);
        botaoFiltros.setForeground(Color.WHITE);
        botaoFiltros.setFont(new Font("SansSerif", Font.BOLD, 16));
        botaoFiltros.setBounds(80, 100, 160, 45);
        painelPretoFundo.add(botaoFiltros);

        EstilizacaoRedonda.CaixaTextoRedonda campoBusca = new EstilizacaoRedonda.CaixaTextoRedonda("Buscar", Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.DARK_GRAY, 0, 40);
        campoBusca.setFont(new Font("SansSerif", Font.PLAIN, 16));
        campoBusca.setBounds(300, 100, 600, 45);
        painelPretoFundo.add(campoBusca);

        EstilizacaoRedonda.BotaoRedondo botaoBuscar = new EstilizacaoRedonda.BotaoRedondo("BUSCAR", Color.BLACK, Color.DARK_GRAY, Color.GRAY, 40);
        botaoBuscar.setForeground(Color.WHITE);
        botaoBuscar.setFont(new Font("SansSerif", Font.BOLD, 16));
        botaoBuscar.setBounds(920, 100, 160, 45);
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

        JLabel quantidadeVeiculos = new JLabel("Quantidade de veículos:");
        quantidadeVeiculos.setFont(new Font("SansSerif", Font.PLAIN, 24));
        quantidadeVeiculos.setBounds(30, 90, 350, 60);
        painelCliente.add(quantidadeVeiculos);

        JLabel placaVeiculo = new JLabel("Placa do(os) Veículo(os):");
        placaVeiculo.setFont(new Font("SansSerif", Font.PLAIN, 24));
        placaVeiculo.setBounds(420, 90, 360, 60);
        painelCliente.add(placaVeiculo);

        JLabel modeloVeiculo = new JLabel("Modelo do veículo:");
        modeloVeiculo.setFont(new Font("SansSerif", Font.PLAIN, 24));
        modeloVeiculo.setBounds(730, 90, 250, 60);
        painelCliente.add(modeloVeiculo);

        EstilizacaoRedonda.BotaoRedondo botaoEditar = new EstilizacaoRedonda.BotaoRedondo("EDITAR", Color.BLACK, Color.DARK_GRAY, Color.GRAY, 40);
        botaoEditar.setForeground(Color.WHITE);
        botaoEditar.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoEditar.setBounds(980, 45, 140, 50);
        painelCliente.add(botaoEditar);
        botaoEditar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TelaEditarCliente telaEditarCliente = new TelaEditarCliente();
                telaEditarCliente.setVisible(true);
                dispose();
            }
        });

        EstilizacaoRedonda.BotaoRedondo botaoVoltar = new EstilizacaoRedonda.BotaoRedondo( "VOLTAR", Color.BLACK, Color.DARK_GRAY, Color.GRAY, 40);
        botaoVoltar.setForeground(Color.WHITE);
        botaoVoltar.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoVoltar.setBounds(1080, 600, 140, 50);
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