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

public class TelaCadastrarSenhaCodigo extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel painelPretoFundo;
    private EstilizacaoRedonda.PainelRedondo painelBrancoFundo;
    private EstilizacaoRedonda.CaixaTextoRedonda codigoAreaText;

    public TelaCadastrarSenhaCodigo() {
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

        JLabel logoGaragem = new JLabel("Logo Aqui");
        logoGaragem.setHorizontalAlignment(SwingConstants.CENTER);
        logoGaragem.setBounds(1050, 20, 100, 100);
        painelBrancoFundo.add(logoGaragem);

        JLabel tituloCadastrar = new JLabel("Cadastrar senha");
        tituloCadastrar.setHorizontalAlignment(SwingConstants.CENTER);
        tituloCadastrar.setFont(new Font("Liberation Serif", Font.BOLD, 46));
        tituloCadastrar.setBounds(390, 60, 400, 50);
        painelBrancoFundo.add(tituloCadastrar);

        JLabel textoCodigo = new JLabel("Digite o código enviado");
        textoCodigo.setHorizontalAlignment(SwingConstants.CENTER);
        textoCodigo.setFont(new Font("SansSerif", Font.BOLD, 18));
        textoCodigo.setBounds(390, 130, 400, 30);
        painelBrancoFundo.add(textoCodigo);

        JLabel tituloCodigo = new JLabel("Código");
        tituloCodigo.setHorizontalAlignment(SwingConstants.CENTER);
        tituloCodigo.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        tituloCodigo.setBounds(390, 200, 400, 35);
        painelBrancoFundo.add(tituloCodigo);

        codigoAreaText = new EstilizacaoRedonda.CaixaTextoRedonda("Digite o código", Color.GRAY, Color.WHITE,Color.GRAY,2, 25);
        codigoAreaText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        codigoAreaText.setBounds(290, 250, 600, 50);
        painelBrancoFundo.add(codigoAreaText);

        EstilizacaoRedonda.BotaoRedondo botaoVerificar = new EstilizacaoRedonda.BotaoRedondo("VERIFICAR CÓDIGO", Color.DARK_GRAY, Color.GRAY, Color.BLACK, 40);
        botaoVerificar.setForeground(Color.WHITE);
        botaoVerificar.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoVerificar.setBounds(465, 360, 250, 50);
        painelBrancoFundo.add(botaoVerificar);
        botaoVerificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TelaNovaSenha telaNova = new TelaNovaSenha();
                telaNova.setVisible(true);
                dispose();
            }
        });

        EstilizacaoRedonda.BotaoRedondo botaoVoltar = new EstilizacaoRedonda.BotaoRedondo("VOLTAR", Color.DARK_GRAY, Color.GRAY, Color.BLACK, 40);
        botaoVoltar.setForeground(Color.WHITE);
        botaoVoltar.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoVoltar.setBounds(870, 520, 130, 50);
        painelBrancoFundo.add(botaoVoltar);
        botaoVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TelaCadastrarSenha telaEmail = new TelaCadastrarSenha();
                telaEmail.setVisible(true);
                dispose();
            }
        });

        EstilizacaoRedonda.BotaoRedondo botaoSair = new EstilizacaoRedonda.BotaoRedondo("SAIR", Color.BLACK, Color.DARK_GRAY, Color.GRAY, 40);
        botaoSair.setForeground(Color.WHITE);
        botaoSair.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoSair.setBounds(1020, 520, 130, 50);
        painelBrancoFundo.add(botaoSair);
        botaoSair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}
