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

public class TelaNovaSenha extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel painelPretoFundo;
    private EstilizacaoRedonda.PainelRedondo painelBrancoFundo;
    private EstilizacaoRedonda.CaixaSenhaRedonda senhaAreaText;
    private EstilizacaoRedonda.CaixaSenhaRedonda confirmarSenhaAreaText;

    public TelaNovaSenha() {
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

        JLabel titulo = new JLabel("Nova Senha");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setFont(new Font("Liberation Serif", Font.BOLD, 46));
        titulo.setBounds(390, 50, 400, 50);
        painelBrancoFundo.add(titulo);

        JLabel tituloSenha = new JLabel("Senha");
        tituloSenha.setHorizontalAlignment(SwingConstants.CENTER);
        tituloSenha.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        tituloSenha.setBounds(390, 130, 400, 35);
        painelBrancoFundo.add(tituloSenha);

        senhaAreaText = new EstilizacaoRedonda.CaixaSenhaRedonda("", Color.GRAY, Color.GRAY, Color.WHITE, 0, 40);
        senhaAreaText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        senhaAreaText.setBounds(290, 180, 600, 50);
        painelBrancoFundo.add(senhaAreaText);

        JLabel tituloConfirmar = new JLabel("Confirmar Senha");
        tituloConfirmar.setHorizontalAlignment(SwingConstants.CENTER);
        tituloConfirmar.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        tituloConfirmar.setBounds(390, 260, 400, 35);
        painelBrancoFundo.add(tituloConfirmar);

        confirmarSenhaAreaText = new EstilizacaoRedonda.CaixaSenhaRedonda("", Color.GRAY, Color.GRAY, Color.WHITE, 0, 40);
        confirmarSenhaAreaText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        confirmarSenhaAreaText.setBounds(290, 310, 600, 50);
        painelBrancoFundo.add(confirmarSenhaAreaText);

        EstilizacaoRedonda.BotaoRedondo botaoSalvar = new EstilizacaoRedonda.BotaoRedondo("SALVAR", Color.DARK_GRAY, Color.GRAY, Color.BLACK, 40);
        botaoSalvar.setForeground(Color.WHITE);
        botaoSalvar.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoSalvar.setBounds(465, 410, 250, 50);
        painelBrancoFundo.add(botaoSalvar);
        botaoSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TelaLogin telaLogin = new TelaLogin();
                telaLogin.setVisible(true);
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
                TelaCadastrarSenhaCodigo telaCodigo = new TelaCadastrarSenhaCodigo();
                telaCodigo.setVisible(true);
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
