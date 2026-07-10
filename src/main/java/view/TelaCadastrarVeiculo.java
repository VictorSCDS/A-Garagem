package view;

import utils.Constantes;
import utils.PermissaoAcesso;

import controllers.VeiculoController;
import exceptions.ControllerException;
import javax.swing.JOptionPane;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TelaCadastrarVeiculo extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel painelPretoFundo;
    private JPanel faixaTitulo;
    private EstilizacaoRedonda.PainelRedondo painelBrancoFundo;

    private EstilizacaoRedonda.CaixaTextoRedonda placaAreaText;
    private EstilizacaoRedonda.CaixaTextoRedonda marcaAreaText;
    private EstilizacaoRedonda.CaixaTextoRedonda modeloAreaText;
    
    private VeiculoController veiculoController;
    
    private String cpfClienteAtual;
    private String nomeClienteAtual;

    public TelaCadastrarVeiculo(String cpfCliente, String nomeCliente) {
        if (!PermissaoAcesso.autorizar(this, PermissaoAcesso.podeAcessarClientes(), "Clientes")) {
            javax.swing.SwingUtilities.invokeLater(() -> {
                new TelaHome().setVisible(true);
                dispose();
            });
            return;
        }

        
        this.cpfClienteAtual = cpfCliente;
        this.nomeClienteAtual = nomeCliente;
        this.veiculoController = new VeiculoController();

        setBackground(Constantes.CINZA_CLARO);
        setSize(1280, 720);
        setMaximizedBounds(new Rectangle(0, 0, 1280, 720));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        painelPretoFundo = new JPanel();
        painelPretoFundo.setBackground(Constantes.CINZA_CLARO);
        painelPretoFundo.setLayout(null);
        setContentPane(painelPretoFundo);

        faixaTitulo = new JPanel();
        faixaTitulo.setBackground(Constantes.PRETO_FOSCO);
        faixaTitulo.setLayout(null);
        faixaTitulo.setBounds(0, 0, 1280, 80);
        painelPretoFundo.add(faixaTitulo);

        JLabel titulo = new JLabel("Registrar Moto de " + nomeCliente.split(" ")[0]);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setForeground(Constantes.CINZA_CLARO);
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
            logoGaragem.setForeground(Constantes.CINZA_CLARO);
        }
        faixaTitulo.add(logoGaragem);
        
        painelBrancoFundo = new EstilizacaoRedonda.PainelRedondo(null, 0, 0, Constantes.CINZA_CLARO, Constantes.CINZA_CLARO);
        painelBrancoFundo.setBounds(0, 80, 1280, 640);
        painelPretoFundo.add(painelBrancoFundo);

        JLabel lblPlaca = new JLabel("Placa");
        lblPlaca.setHorizontalAlignment(SwingConstants.CENTER);
        lblPlaca.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        lblPlaca.setBounds(266, 63, 200, 35);
        painelBrancoFundo.add(lblPlaca);
        
        placaAreaText = new EstilizacaoRedonda.CaixaTextoRedonda("Digite a placa", Constantes.PRETO_FOSCO, Constantes.CINZA_CLARO,Constantes.PRETO_FOSCO,2, 25);
        placaAreaText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        placaAreaText.setBounds(139, 110, 462, 50);
        placaAreaText.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(placaAreaText.getText().equals("Digite a placa")) placaAreaText.setText("");
            }
        });
        painelBrancoFundo.add(placaAreaText);

        JLabel lblMarca = new JLabel("Marca");
        lblMarca.setHorizontalAlignment(SwingConstants.CENTER);
        lblMarca.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        lblMarca.setBounds(793, 63, 200, 35);
        painelBrancoFundo.add(lblMarca);
        
        marcaAreaText = new EstilizacaoRedonda.CaixaTextoRedonda("Ex: Honda, Yamaha", Constantes.PRETO_FOSCO, Constantes.CINZA_CLARO,Constantes.PRETO_FOSCO,2, 25);
        marcaAreaText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        marcaAreaText.setBounds(662, 110, 462, 50);
        marcaAreaText.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(marcaAreaText.getText().equals("Ex: Honda, Yamaha")) marcaAreaText.setText("");
            }
        });
        painelBrancoFundo.add(marcaAreaText);

        JLabel lblModelo = new JLabel("Modelo e Ano");
        lblModelo.setHorizontalAlignment(SwingConstants.CENTER);
        lblModelo.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        lblModelo.setBounds(538, 282, 200, 35);
        painelBrancoFundo.add(lblModelo);
        
        modeloAreaText = new EstilizacaoRedonda.CaixaTextoRedonda("Ex: CG 160 Fan 2022", Constantes.PRETO_FOSCO, Constantes.CINZA_CLARO,Constantes.PRETO_FOSCO,2, 25);
        modeloAreaText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        modeloAreaText.setBounds(411, 329, 462, 50);
        modeloAreaText.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(modeloAreaText.getText().equals("Ex: CG 160 Fan 2022")) modeloAreaText.setText("");
            }
        });
        painelBrancoFundo.add(modeloAreaText);

        EstilizacaoRedonda.BotaoRedondo botaoAdicionar = new EstilizacaoRedonda.BotaoRedondo("ADICIONAR VEÍCULO", Constantes.VERMELHO_FERRARI, Constantes.AMARELO_OURO, Constantes.PRETO_FOSCO, 40);
        botaoAdicionar.setForeground(Constantes.CINZA_CLARO);
        botaoAdicionar.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoAdicionar.setBounds(511, 480, 257, 50);
        painelBrancoFundo.add(botaoAdicionar);
        
        botaoAdicionar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String placa = placaAreaText.getText().trim();
                String marca = marcaAreaText.getText().trim();
                String modelo = modeloAreaText.getText().trim();

                if (placa.isEmpty() || placa.equals("Digite a placa") ||
                    marca.isEmpty() || marca.equals("Ex: Honda, Yamaha") ||
                    modelo.isEmpty() || modelo.equals("Ex: CG 160 Fan 2022")) {
                    
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos do veículo.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    veiculoController.cadastrar(placa, marca, modelo);
                    
                    veiculoController.vincularVeiculoAoCliente(placa, cpfClienteAtual);
                    
                    int resposta = JOptionPane.showConfirmDialog(
                            TelaCadastrarVeiculo.this, 
                            "Moto placa " + placa + " adicionada e vinculada com sucesso!\n\nDeseja registrar outra moto para " + nomeClienteAtual + "?", 
                            "Novo Veículo", 
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE
                    );
                    
                    if (resposta == JOptionPane.YES_OPTION) {
                        placaAreaText.setText("Digite a placa");
                        marcaAreaText.setText("Ex: Honda, Yamaha");
                        modeloAreaText.setText("Ex: CG 160 Fan 2022");
                    } else {
                        TelaClientes telaClientes = new TelaClientes();
                        telaClientes.setVisible(true);
                        dispose();
                    }
                    
                } catch (ControllerException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Falha no Cadastro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
     
        EstilizacaoRedonda.BotaoRedondo botaoVoltar = new EstilizacaoRedonda.BotaoRedondo("", Constantes.VERMELHO_FERRARI, Constantes.AMARELO_OURO, Constantes.PRETO_FOSCO, 40);
        botaoVoltar.setForeground(Constantes.CINZA_CLARO);
        botaoVoltar.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoVoltar.setBounds(1100, 520, 90, 50);
        java.net.URL urlIconeSair = getClass().getResource("/assets/imagens/iconVoltar.png"); 
        if (urlIconeSair != null) {
            java.awt.Image iconeOriginal = new javax.swing.ImageIcon(urlIconeSair).getImage();
            java.awt.Image iconeRedimensionado = iconeOriginal.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
            botaoVoltar.setIcon(new javax.swing.ImageIcon(iconeRedimensionado));
            botaoVoltar.setIconTextGap(10); 
        }
        painelBrancoFundo.add(botaoVoltar);
        
        botaoVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TelaClientes telaClientes = new TelaClientes();
                telaClientes.setVisible(true);
                dispose();
            }
        });
    }
}