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

public class TelaEditarFuncionario extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel painelPretoFundo;
    private EstilizacaoRedonda.CaixaTextoRedonda campoNome;
    private EstilizacaoRedonda.CaixaTextoRedonda campoCpf;
    private EstilizacaoRedonda.CaixaTextoRedonda campoCargo;
    private EstilizacaoRedonda.CaixaTextoRedonda campoTelefone;
    private EstilizacaoRedonda.CaixaTextoRedonda campoEmail;
    private EstilizacaoRedonda.CaixaTextoRedonda campoDataAdmissao;

    public TelaEditarFuncionario() {

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

        JLabel titulo = new JLabel("Editar Funcionário");
        titulo.setForeground(Color.WHITE);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setFont(new Font("Liberation Serif", Font.BOLD, 34));
        titulo.setBounds(340, 15, 600, 40);
        barraSuperior.add(titulo);

        JLabel logoGaragem = new JLabel("Logo Aqui");
        logoGaragem.setForeground(Color.WHITE);
        logoGaragem.setHorizontalAlignment(SwingConstants.CENTER);
        logoGaragem.setBounds(1130, 5, 100, 70);
        barraSuperior.add(logoGaragem);

        JLabel tituloNome = new JLabel("Nome");
        tituloNome.setFont(new Font("SansSerif", Font.BOLD, 24));
        tituloNome.setHorizontalAlignment(SwingConstants.CENTER);
        tituloNome.setBounds(150, 150, 260, 30);
        painelPretoFundo.add(tituloNome);

        JLabel tituloCpf = new JLabel("CPF");
        tituloCpf.setFont(new Font("SansSerif", Font.BOLD, 24));
        tituloCpf.setHorizontalAlignment(SwingConstants.CENTER);
        tituloCpf.setBounds(510, 150, 260, 30);
        painelPretoFundo.add(tituloCpf);

        JLabel tituloCargo = new JLabel("Cargo");
        tituloCargo.setFont(new Font("SansSerif", Font.BOLD, 24));
        tituloCargo.setHorizontalAlignment(SwingConstants.CENTER);
        tituloCargo.setBounds(870, 150, 260, 30);
        painelPretoFundo.add(tituloCargo);

        campoNome = new EstilizacaoRedonda.CaixaTextoRedonda("", Color.GRAY, Color.GRAY, Color.WHITE, 0, 40);
        campoNome.setFont(new Font("SansSerif", Font.PLAIN, 18));
        campoNome.setBounds(150, 210, 260, 40);
        painelPretoFundo.add(campoNome);

        campoCpf = new EstilizacaoRedonda.CaixaTextoRedonda("", Color.GRAY, Color.GRAY, Color.WHITE, 0, 40);
        campoCpf.setFont(new Font("SansSerif", Font.PLAIN, 18));
        campoCpf.setBounds(510, 210, 260, 40);
        painelPretoFundo.add(campoCpf);

        campoCargo = new EstilizacaoRedonda.CaixaTextoRedonda("", Color.GRAY, Color.GRAY, Color.WHITE, 0, 40);
        campoCargo.setFont(new Font("SansSerif", Font.PLAIN, 18));
        campoCargo.setBounds(870, 210, 260, 40);
        painelPretoFundo.add(campoCargo);

        JLabel tituloTelefone = new JLabel("Telefone");
        tituloTelefone.setFont(new Font("SansSerif", Font.BOLD, 24));
        tituloTelefone.setHorizontalAlignment(SwingConstants.CENTER);
        tituloTelefone.setBounds(150, 360, 260, 30);
        painelPretoFundo.add(tituloTelefone);

        JLabel tituloEmail = new JLabel("E-mail");
        tituloEmail.setFont(new Font("SansSerif", Font.BOLD, 24));
        tituloEmail.setHorizontalAlignment(SwingConstants.CENTER);
        tituloEmail.setBounds(510, 360, 260, 30);
        painelPretoFundo.add(tituloEmail);

        JLabel tituloData = new JLabel("Data admissão");
        tituloData.setFont(new Font("SansSerif", Font.BOLD, 24));
        tituloData.setHorizontalAlignment(SwingConstants.CENTER);
        tituloData.setBounds(870, 360, 260, 30);
        painelPretoFundo.add(tituloData);

        campoTelefone = new EstilizacaoRedonda.CaixaTextoRedonda("", Color.GRAY, Color.GRAY, Color.WHITE, 0, 40);
        campoTelefone.setFont(new Font("SansSerif", Font.PLAIN, 18));
        campoTelefone.setBounds(150, 420, 260, 40);
        painelPretoFundo.add(campoTelefone);

        campoEmail = new EstilizacaoRedonda.CaixaTextoRedonda("", Color.GRAY, Color.GRAY, Color.WHITE, 0, 40);
        campoEmail.setFont(new Font("SansSerif", Font.PLAIN, 18));
        campoEmail.setBounds(510, 420, 260, 40);
        painelPretoFundo.add(campoEmail);

        campoDataAdmissao = new EstilizacaoRedonda.CaixaTextoRedonda("", Color.GRAY, Color.GRAY, Color.WHITE, 0, 40);
        campoDataAdmissao.setFont(new Font("SansSerif", Font.PLAIN, 18));
        campoDataAdmissao.setBounds(870, 420, 260, 40);
        painelPretoFundo.add(campoDataAdmissao);
        EstilizacaoRedonda.BotaoRedondo botaoSalvar = new EstilizacaoRedonda.BotaoRedondo("SALVAR", Color.BLACK, Color.DARK_GRAY, Color.GRAY, 40);
        botaoSalvar.setForeground(Color.WHITE);
        botaoSalvar.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoSalvar.setBounds(520, 560, 260, 50);
        painelPretoFundo.add(botaoSalvar);

        EstilizacaoRedonda.BotaoRedondo botaoVoltar = new EstilizacaoRedonda.BotaoRedondo("VOLTAR", Color.BLACK, Color.DARK_GRAY, Color.GRAY, 40);
        botaoVoltar.setForeground(Color.WHITE);
        botaoVoltar.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoVoltar.setBounds(1100, 600, 140, 50);
        painelPretoFundo.add(botaoVoltar);

        botaoVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TelaEquipe telaEquipe = new TelaEquipe();
                telaEquipe.setVisible(true);
                dispose();
            }
        });
    }
}