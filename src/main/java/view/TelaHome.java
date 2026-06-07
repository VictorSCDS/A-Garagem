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

public class TelaHome extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel painelPretoFundo;
    private EstilizacaoRedonda.PainelRedondo painelBrancoFundo;

    public TelaHome() {
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

        JLabel titulo = new JLabel("Home");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setFont(new Font("Liberation Serif", Font.BOLD, 46));
        titulo.setBounds(390, 30, 400, 50);
        painelBrancoFundo.add(titulo);

        JLabel tituloIterat = new JLabel("Bem-vindo Gerente [Nome]");
        tituloIterat.setHorizontalAlignment(SwingConstants.CENTER);
        tituloIterat.setFont(new Font("SansSerif", Font.BOLD, 22));
        tituloIterat.setBounds(390, 90, 400, 30);
        painelBrancoFundo.add(tituloIterat);

        EstilizacaoRedonda.BotaoRedondo botaoOrdemServico = new EstilizacaoRedonda.BotaoRedondo("<html><center>Ordem de<br>Serviço</center></html>", Color.DARK_GRAY, Color.GRAY, Color.BLACK, 40);
        botaoOrdemServico.setForeground(Color.WHITE);
        botaoOrdemServico.setFont(new Font("SansSerif", Font.BOLD, 24));
        botaoOrdemServico.setBounds(150, 160, 260, 150);
        painelBrancoFundo.add(botaoOrdemServico);

        EstilizacaoRedonda.BotaoRedondo botaoClientes = new EstilizacaoRedonda.BotaoRedondo("Clientes", Color.DARK_GRAY, Color.GRAY, Color.BLACK, 40);
        botaoClientes.setForeground(Color.WHITE);
        botaoClientes.setFont(new Font("SansSerif", Font.BOLD, 24));
        botaoClientes.setBounds(460, 160, 260, 150);
        painelBrancoFundo.add(botaoClientes);
        botaoClientes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TelaClientes telaClientes = new TelaClientes();
                telaClientes.setVisible(true);
                dispose();
            }
        });

        EstilizacaoRedonda.BotaoRedondo botaoEstoque = new EstilizacaoRedonda.BotaoRedondo("Estoque", Color.DARK_GRAY, Color.GRAY, Color.BLACK, 40);
        botaoEstoque.setForeground(Color.WHITE);
        botaoEstoque.setFont(new Font("SansSerif", Font.BOLD, 24));
        botaoEstoque.setBounds(770, 160, 260, 150);
        painelBrancoFundo.add(botaoEstoque);

        EstilizacaoRedonda.BotaoRedondo botaoEquipe = new EstilizacaoRedonda.BotaoRedondo("Equipe", Color.DARK_GRAY, Color.GRAY, Color.BLACK, 40);
        botaoEquipe.setForeground(Color.WHITE);
        botaoEquipe.setFont(new Font("SansSerif", Font.BOLD, 24));
        botaoEquipe.setBounds(305, 340, 260, 150);
        painelBrancoFundo.add(botaoEquipe);

        EstilizacaoRedonda.BotaoRedondo botaoFinanceiro = new EstilizacaoRedonda.BotaoRedondo("Financeiro", Color.DARK_GRAY, Color.GRAY, Color.BLACK, 40);
        botaoFinanceiro.setForeground(Color.WHITE);
        botaoFinanceiro.setFont(new Font("SansSerif", Font.BOLD, 24));
        botaoFinanceiro.setBounds(615, 340, 260, 150);
        painelBrancoFundo.add(botaoFinanceiro);

        EstilizacaoRedonda.BotaoRedondo botaoSair = new EstilizacaoRedonda.BotaoRedondo("SAIR", Color.BLACK, Color.DARK_GRAY, Color.GRAY, 40);
        botaoSair.setForeground(Color.WHITE);
        botaoSair.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoSair.setBounds(1020, 520, 130, 50);
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