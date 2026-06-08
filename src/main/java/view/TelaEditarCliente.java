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

public class TelaEditarCliente extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel painelPretoFundo;
    private JPanel faixaTitulo;
    private EstilizacaoRedonda.PainelRedondo painelBrancoFundo;

    private EstilizacaoRedonda.CaixaTextoRedonda nomeAreaText;
    private EstilizacaoRedonda.CaixaTextoRedonda cpfAreaText;
    private EstilizacaoRedonda.CaixaTextoRedonda telefoneAreaText;
    private EstilizacaoRedonda.CaixaTextoRedonda emailAreaText;

    public TelaEditarCliente() {

        setBackground(Color.WHITE);
        setSize(1280, 720);
        setMaximizedBounds(new Rectangle(0, 0, 1280, 720));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        painelPretoFundo = new JPanel();
        painelPretoFundo.setBackground(Color.WHITE);
        painelPretoFundo.setLayout(null);
        setContentPane(painelPretoFundo);

        faixaTitulo = new JPanel();
        faixaTitulo.setBackground(Color.DARK_GRAY);
        faixaTitulo.setLayout(null);
        faixaTitulo.setBounds(0, 0, 1280, 80);
        painelPretoFundo.add(faixaTitulo);

        JLabel titulo = new JLabel("Editar Cliente");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 32));
        titulo.setBounds(340, 10, 600, 50);
        faixaTitulo.add(titulo);

        JLabel logoGaragem = new JLabel("Logo Aqui");
        logoGaragem.setHorizontalAlignment(SwingConstants.CENTER);
        logoGaragem.setForeground(Color.WHITE);
        logoGaragem.setBounds(1140, 5, 100, 70);
        faixaTitulo.add(logoGaragem);
        painelBrancoFundo = new EstilizacaoRedonda.PainelRedondo(null, 0, 0, Color.WHITE, Color.WHITE);
        painelBrancoFundo.setBounds(0, 80, 1280, 640);
        painelPretoFundo.add(painelBrancoFundo);

        JLabel lblNome = new JLabel("Nome");
        lblNome.setHorizontalAlignment(SwingConstants.CENTER);
        lblNome.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        lblNome.setBounds(266, 63, 200, 35);
        painelBrancoFundo.add(lblNome);
        nomeAreaText = new EstilizacaoRedonda.CaixaTextoRedonda("Digite o nome", Color.GRAY, Color.WHITE,Color.GRAY,2, 25);
        nomeAreaText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        nomeAreaText.setBounds(139, 110, 462, 50);
        painelBrancoFundo.add(nomeAreaText);

        JLabel lblCpf = new JLabel("CPF");
        lblCpf.setHorizontalAlignment(SwingConstants.CENTER);
        lblCpf.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        lblCpf.setBounds(793, 63, 200, 35);
        painelBrancoFundo.add(lblCpf);
        cpfAreaText = new EstilizacaoRedonda.CaixaTextoRedonda("Digite o CPF", Color.GRAY, Color.WHITE,Color.GRAY,2, 25);
        cpfAreaText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        cpfAreaText.setBounds(662, 110, 462, 50);
        painelBrancoFundo.add(cpfAreaText);

        JLabel lblTelefone = new JLabel("Telefone");
        lblTelefone.setHorizontalAlignment(SwingConstants.CENTER);
        lblTelefone.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        lblTelefone.setBounds(266, 283, 200, 35);
        painelBrancoFundo.add(lblTelefone);
        telefoneAreaText = new EstilizacaoRedonda.CaixaTextoRedonda("Digite o telefone", Color.GRAY, Color.WHITE,Color.GRAY,2, 25);
        telefoneAreaText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        telefoneAreaText.setBounds(139, 330, 462, 50);
        painelBrancoFundo.add(telefoneAreaText);

        JLabel lblEmail = new JLabel("E-mail");
        lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
        lblEmail.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        lblEmail.setBounds(793, 280, 200, 35);
        painelBrancoFundo.add(lblEmail);
        emailAreaText = new EstilizacaoRedonda.CaixaTextoRedonda("Digite o e-mail", Color.GRAY, Color.WHITE,Color.GRAY,2, 25);
        emailAreaText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        emailAreaText.setBounds(662, 330, 462, 50);
        painelBrancoFundo.add(emailAreaText);

        EstilizacaoRedonda.BotaoRedondo botaoAdicionar = new EstilizacaoRedonda.BotaoRedondo("SALVAR E AVANÇAR", Color.BLACK, Color.DARK_GRAY, Color.GRAY, 40);
        botaoAdicionar.setForeground(Color.WHITE);
        botaoAdicionar.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoAdicionar.setBounds(450, 480, 400, 50);
        painelBrancoFundo.add(botaoAdicionar);
        botaoAdicionar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        EstilizacaoRedonda.BotaoRedondo botaoVoltar = new EstilizacaoRedonda.BotaoRedondo("VOLTAR", Color.BLACK, Color.DARK_GRAY, Color.GRAY, 40);
        botaoVoltar.setForeground(Color.WHITE);
        botaoVoltar.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoVoltar.setBounds(1100, 520, 140, 50);
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