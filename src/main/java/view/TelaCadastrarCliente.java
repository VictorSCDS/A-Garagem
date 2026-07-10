package view;

import utils.Constantes;
import utils.PermissaoAcesso;

import controllers.ClienteController;
import exceptions.ControllerException;
import javax.swing.JOptionPane;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TelaCadastrarCliente extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel painelPretoFundo;
    private JPanel faixaTitulo;
    private EstilizacaoRedonda.PainelRedondo painelBrancoFundo;

    private EstilizacaoRedonda.CaixaTextoRedonda nomeAreaText;
    private EstilizacaoRedonda.CaixaTextoRedonda cpfAreaText;
    private EstilizacaoRedonda.CaixaTextoRedonda telefoneAreaText;
    private EstilizacaoRedonda.CaixaTextoRedonda emailAreaText;
    
    private ClienteController clienteController;

    public TelaCadastrarCliente() {
        if (!PermissaoAcesso.autorizar(this, PermissaoAcesso.podeAcessarClientes(), "Clientes")) {
            javax.swing.SwingUtilities.invokeLater(() -> {
                new TelaHome().setVisible(true);
                dispose();
            });
            return;
        }


        this.clienteController = new ClienteController();

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

        JLabel titulo = new JLabel("Cadastrar Cliente");
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

        JLabel lblNome = new JLabel("Nome");
        lblNome.setHorizontalAlignment(SwingConstants.CENTER);
        lblNome.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        lblNome.setBounds(266, 63, 200, 35);
        painelBrancoFundo.add(lblNome);
        
        nomeAreaText = new EstilizacaoRedonda.CaixaTextoRedonda("Digite o nome", Constantes.PRETO_FOSCO, Constantes.CINZA_CLARO,Constantes.PRETO_FOSCO,2, 25);
        nomeAreaText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        nomeAreaText.setBounds(139, 110, 462, 50);
        nomeAreaText.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(nomeAreaText.getText().equals("Digite o nome")) nomeAreaText.setText("");
            }
        });
        painelBrancoFundo.add(nomeAreaText);

        JLabel lblCpf = new JLabel("CPF");
        lblCpf.setHorizontalAlignment(SwingConstants.CENTER);
        lblCpf.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        lblCpf.setBounds(793, 63, 200, 35);
        painelBrancoFundo.add(lblCpf);
        
        cpfAreaText = new EstilizacaoRedonda.CaixaTextoRedonda("Digite o CPF", Constantes.PRETO_FOSCO, Constantes.CINZA_CLARO,Constantes.PRETO_FOSCO,2, 25);
        cpfAreaText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        cpfAreaText.setBounds(662, 110, 462, 50);
        cpfAreaText.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(cpfAreaText.getText().equals("Digite o CPF")) cpfAreaText.setText("");
            }
        });
        painelBrancoFundo.add(cpfAreaText);

        JLabel lblTelefone = new JLabel("Telefone");
        lblTelefone.setHorizontalAlignment(SwingConstants.CENTER);
        lblTelefone.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        lblTelefone.setBounds(266, 283, 200, 35);
        painelBrancoFundo.add(lblTelefone);
        
        telefoneAreaText = new EstilizacaoRedonda.CaixaTextoRedonda("Digite o telefone", Constantes.PRETO_FOSCO, Constantes.CINZA_CLARO,Constantes.PRETO_FOSCO,2, 25);
        telefoneAreaText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        telefoneAreaText.setBounds(139, 330, 462, 50);
        telefoneAreaText.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(telefoneAreaText.getText().equals("Digite o telefone")) telefoneAreaText.setText("");
            }
        });
        painelBrancoFundo.add(telefoneAreaText);

        JLabel lblEmail = new JLabel("E-mail");
        lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
        lblEmail.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        lblEmail.setBounds(793, 280, 200, 35);
        painelBrancoFundo.add(lblEmail);
        
        emailAreaText = new EstilizacaoRedonda.CaixaTextoRedonda("Digite o e-mail", Constantes.PRETO_FOSCO, Constantes.CINZA_CLARO,Constantes.PRETO_FOSCO,2, 25);
        emailAreaText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        emailAreaText.setBounds(662, 330, 462, 50);
        emailAreaText.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(emailAreaText.getText().equals("Digite o e-mail")) emailAreaText.setText("");
            }
        });
        painelBrancoFundo.add(emailAreaText);

        EstilizacaoRedonda.BotaoRedondo botaoAdicionar = new EstilizacaoRedonda.BotaoRedondo("ADICIONAR", Constantes.VERMELHO_FERRARI, Constantes.AMARELO_OURO, Constantes.PRETO_FOSCO, 40);
        botaoAdicionar.setForeground(Constantes.CINZA_CLARO);
        botaoAdicionar.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoAdicionar.setBounds(511, 480, 257, 50);
        painelBrancoFundo.add(botaoAdicionar);
        
        botaoAdicionar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nome = nomeAreaText.getText().trim();
                String cpf = cpfAreaText.getText().trim();
                String telefone = telefoneAreaText.getText().trim();
                String email = emailAreaText.getText().trim();

                if (nome.isEmpty() || nome.equals("Digite o nome") ||
                    cpf.isEmpty() || cpf.equals("Digite o CPF") ||
                    telefone.isEmpty() || telefone.equals("Digite o telefone") ||
                    email.isEmpty() || email.equals("Digite o e-mail")) {
                    
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos para cadastrar o cliente.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    clienteController.cadastrar(nome, cpf, telefone, email);
                    JOptionPane.showMessageDialog(null, "Cliente cadastrado! Agora, vamos registrar a moto dele.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    TelaCadastrarVeiculo telaVeiculo = new TelaCadastrarVeiculo(cpf, nome);
                    telaVeiculo.setVisible(true);
                    dispose();
                    
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
        } else {
            System.out.println("Ícone do botão sair não encontrado!");
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