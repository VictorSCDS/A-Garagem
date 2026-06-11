package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Rectangle;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaCadastrarSenha extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel painelPretoFundo;
    private EstilizacaoRedonda.PainelRedondo painelBrancoFundo;
    private EstilizacaoRedonda.CaixaTextoRedonda emailAreaText;

    public TelaCadastrarSenha() {
        setBackground(Color.DARK_GRAY);
        setSize(1280, 720);
        setMaximizedBounds(new Rectangle(0, 0, 1280, 720));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        painelPretoFundo = new JPanel();
        painelPretoFundo.setBackground(Color.DARK_GRAY);
        painelPretoFundo.setLayout(null);
        setContentPane(painelPretoFundo);

        painelBrancoFundo = new EstilizacaoRedonda.PainelRedondo(null, 40, 0, Color.WHITE, Color.WHITE);
        painelBrancoFundo.setBounds(50, 40, 1180, 600);
        painelPretoFundo.add(painelBrancoFundo);

        JLabel logoGaragem = new JLabel();
        logoGaragem.setHorizontalAlignment(SwingConstants.CENTER);
        logoGaragem.setBounds(1050, 20, 100, 70); 
        java.net.URL urlImagem = getClass().getResource("/assets/imagens/logo.png");
        if (urlImagem != null) {
            java.awt.Image imagemOriginal = new javax.swing.ImageIcon(urlImagem).getImage();
            java.awt.Image imagemRedimensionada = imagemOriginal.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH);
            logoGaragem.setIcon(new javax.swing.ImageIcon(imagemRedimensionada));
        } else {
            logoGaragem.setText("Logo Aqui");
            logoGaragem.setForeground(Color.BLACK); 
        }
        painelBrancoFundo.add(logoGaragem);

        JLabel tituloCadas = new JLabel("Cadastrar senha");
        tituloCadas.setHorizontalAlignment(SwingConstants.CENTER);
        tituloCadas.setFont(new Font("Liberation Serif", Font.BOLD, 46));
        tituloCadas.setBounds(390, 60, 400, 50);
        painelBrancoFundo.add(tituloCadas);

        JLabel textoEmail = new JLabel("Digite o e-mail cadastrado");
        textoEmail.setHorizontalAlignment(SwingConstants.CENTER);
        textoEmail.setFont(new Font("SansSerif", Font.BOLD, 18));
        textoEmail.setBounds(390, 130, 400, 30);
        painelBrancoFundo.add(textoEmail);

        JLabel tituloEmail = new JLabel("E-mail");
        tituloEmail.setHorizontalAlignment(SwingConstants.CENTER);
        tituloEmail.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        tituloEmail.setBounds(390, 200, 400, 35);
        painelBrancoFundo.add(tituloEmail);

        emailAreaText = new EstilizacaoRedonda.CaixaTextoRedonda("Digite seu e-mail", Color.GRAY, Color.WHITE,Color.GRAY,2, 25);
        emailAreaText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        emailAreaText.setBounds(290, 250, 600, 50);
        painelBrancoFundo.add(emailAreaText);

        EstilizacaoRedonda.BotaoRedondo botaoEnviar = new EstilizacaoRedonda.BotaoRedondo("ENVIAR CODIGO", Color.DARK_GRAY, Color.GRAY, Color.BLACK, 40);
        botaoEnviar.setForeground(Color.WHITE);
        botaoEnviar.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoEnviar.setBounds(465, 360, 250, 50);
        painelBrancoFundo.add(botaoEnviar);
        botaoEnviar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TelaCadastrarSenhaCodigo telaCodigo = new TelaCadastrarSenhaCodigo();
                telaCodigo.setVisible(true);
                dispose();
            }
        });

        EstilizacaoRedonda.BotaoRedondo botaoSair = new EstilizacaoRedonda.BotaoRedondo("", Color.BLACK, Color.DARK_GRAY, Color.GRAY, 40);
        botaoSair.setForeground(Color.WHITE);
        botaoSair.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoSair.setBounds(1020, 520, 90, 50);
        java.net.URL urlIconeSair = getClass().getResource("/assets/imagens/iconVoltar.png"); 
        if (urlIconeSair != null) {
            java.awt.Image iconeOriginal = new javax.swing.ImageIcon(urlIconeSair).getImage();
            java.awt.Image iconeRedimensionado = iconeOriginal.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
            botaoSair.setIcon(new javax.swing.ImageIcon(iconeRedimensionado));
            botaoSair.setIconTextGap(10); 
        } else {
            System.out.println("Ícone do botão sair não encontrado!");
        }
        painelBrancoFundo.add(botaoSair);
        botaoSair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TelaLogin telaLogin = new TelaLogin();
                telaLogin.setVisible(true);
                dispose();
            }
        });
    }
}