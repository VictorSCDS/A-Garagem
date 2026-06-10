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

public class TelaEquipe extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel painelPretoFundo;
    private EstilizacaoRedonda.PainelRedondo painelFuncionario;

    public TelaEquipe() {

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

        JLabel titulo = new JLabel("Equipe");
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
        botaoFiltros.setBounds(80, 100, 140, 45);
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

        EstilizacaoRedonda.BotaoRedondo botaoCadastrar = new EstilizacaoRedonda.BotaoRedondo("CADASTRAR FUNCIONÁRIO", Color.BLACK, Color.DARK_GRAY, Color.GRAY, 40);
        botaoCadastrar.setForeground(Color.WHITE);
        botaoCadastrar.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoCadastrar.setBounds(390, 170, 500, 50);
        painelPretoFundo.add(botaoCadastrar);
        botaoCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TelaCadastrarFuncionario telaCadastrarFuncionario = new TelaCadastrarFuncionario();
                telaCadastrarFuncionario.setVisible(true);
                dispose();
            }
        });

        painelFuncionario = new EstilizacaoRedonda.PainelRedondo(null, 30, 4, Color.WHITE, Color.BLACK);
        painelFuncionario.setBounds(30, 250, 1200, 150);
        painelPretoFundo.add(painelFuncionario);

        JLabel nomeFuncionario = new JLabel("Nome:");
        nomeFuncionario.setFont(new Font("SansSerif", Font.PLAIN, 20));
        nomeFuncionario.setBounds(25, 20, 120, 50);
        painelFuncionario.add(nomeFuncionario);

        JLabel cpfFuncionario = new JLabel("CPF:");
        cpfFuncionario.setFont(new Font("SansSerif", Font.PLAIN, 20));
        cpfFuncionario.setBounds(160, 20, 220, 50);
        painelFuncionario.add(cpfFuncionario);

        JLabel emailFuncionario = new JLabel("E-mail:");
        emailFuncionario.setFont(new Font("SansSerif", Font.PLAIN, 20));
        emailFuncionario.setBounds(380, 20, 280, 50);
        painelFuncionario.add(emailFuncionario);

        JLabel telefoneFuncionario = new JLabel("Telefone:");
        telefoneFuncionario.setFont(new Font("SansSerif", Font.PLAIN, 20));
        telefoneFuncionario.setBounds(650, 20, 220, 50);
        painelFuncionario.add(telefoneFuncionario);

        JLabel cargoFuncionario = new JLabel("Cargo:");
        cargoFuncionario.setFont(new Font("SansSerif", Font.PLAIN, 20));
        cargoFuncionario.setBounds(870, 20, 150, 50);
        painelFuncionario.add(cargoFuncionario);

        JLabel admissaoFuncionario = new JLabel("Admissão:");
        admissaoFuncionario.setFont(new Font("SansSerif", Font.PLAIN, 20));
        admissaoFuncionario.setBounds(1020, 20, 160, 50);
        painelFuncionario.add(admissaoFuncionario);

        EstilizacaoRedonda.BotaoRedondo botaoEditar = new EstilizacaoRedonda.BotaoRedondo("EDITAR", Color.BLACK, Color.DARK_GRAY, Color.GRAY, 40);
        botaoEditar.setForeground(Color.WHITE);
        botaoEditar.setFont(new Font("SansSerif", Font.BOLD, 16));
        botaoEditar.setBounds(520, 90, 180, 40);
        painelFuncionario.add(botaoEditar);
        botaoEditar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TelaEditarFuncionario telaEditarFuncionario = new TelaEditarFuncionario();
                telaEditarFuncionario.setVisible(true);
                dispose();
            }
        });

        EstilizacaoRedonda.BotaoRedondo botaoVoltar = new EstilizacaoRedonda.BotaoRedondo("VOLTAR", Color.BLACK, Color.DARK_GRAY, Color.GRAY, 40);
        botaoVoltar.setForeground(Color.WHITE);
        botaoVoltar.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoVoltar.setBounds(1050, 600, 140, 50);
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