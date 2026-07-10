package view;

import utils.Constantes;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Rectangle;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import java.awt.Image;
import entities.Funcionario;
import entities.enums.Cargo;
import utils.Sessao;
import utils.PermissaoAcesso;

public class TelaHome extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel painelPretoFundo;
    private EstilizacaoRedonda.PainelRedondo painelBrancoFundo;

    public TelaHome() {
        setBackground(Constantes.PRETO_FOSCO);
        setSize(1280, 720);
        setMaximizedBounds(new Rectangle(0, 0, 1280, 720));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        painelPretoFundo = new JPanel();
        painelPretoFundo.setBackground(Constantes.PRETO_FOSCO);
        painelPretoFundo.setLayout(null);
        setContentPane(painelPretoFundo);

        painelBrancoFundo = new EstilizacaoRedonda.PainelRedondo(null, 40, 0, Constantes.CINZA_CLARO, Constantes.CINZA_CLARO);
        painelBrancoFundo.setBounds(50, 40, 1180, 600);
        painelPretoFundo.add(painelBrancoFundo);

        JLabel logoGaragem = new JLabel();
        logoGaragem.setHorizontalAlignment(SwingConstants.CENTER);
        logoGaragem.setBounds(921, 20, 229, 100);
        java.net.URL urlImagem = getClass().getResource("/assets/imagens/logo.png");
        if (urlImagem != null) {
            ImageIcon iconeInicial = new ImageIcon(urlImagem);
            Image imagemRedimensiona = iconeInicial.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            logoGaragem.setIcon(new ImageIcon(imagemRedimensiona));
        } else {
            logoGaragem.setText("Logo Aqui");
            logoGaragem.setForeground(Constantes.PRETO_FOSCO);
        }
        painelBrancoFundo.add(logoGaragem);

        JLabel titulo = new JLabel("Home");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setFont(new Font("Liberation Serif", Font.BOLD, 46));
        titulo.setBounds(390, 30, 400, 50);
        painelBrancoFundo.add(titulo);

        JLabel tituloIterat = new JLabel(obterTituloUsuarioLogado());
        tituloIterat.setHorizontalAlignment(SwingConstants.CENTER);
        tituloIterat.setFont(new Font("SansSerif", Font.BOLD, 22));
        tituloIterat.setBounds(240, 90, 700, 30);
        painelBrancoFundo.add(tituloIterat);

        EstilizacaoRedonda.BotaoRedondo botaoOrdemServico = new EstilizacaoRedonda.BotaoRedondo("Ordem Serviço", Constantes.VERMELHO_FERRARI, Constantes.AMARELO_OURO, Constantes.PRETO_FOSCO, 40);
        botaoOrdemServico.setForeground(Constantes.CINZA_CLARO);
        botaoOrdemServico.setFont(new Font("SansSerif", Font.BOLD, 24));
        botaoOrdemServico.setBounds(150, 160, 260, 150);
        painelBrancoFundo.add(botaoOrdemServico);
        botaoOrdemServico.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!PermissaoAcesso.autorizar(TelaHome.this, PermissaoAcesso.podeAcessarOrdemServico(), "Ordem de Serviço")) return;
                TelaOrdemServico telaOrdemServico = new TelaOrdemServico();
                telaOrdemServico.setVisible(true);
                dispose();
            }
        });

        EstilizacaoRedonda.BotaoRedondo botaoClientes = new EstilizacaoRedonda.BotaoRedondo("Clientes", Constantes.VERMELHO_FERRARI, Constantes.AMARELO_OURO, Constantes.PRETO_FOSCO, 40);
        botaoClientes.setForeground(Constantes.CINZA_CLARO);
        botaoClientes.setFont(new Font("SansSerif", Font.BOLD, 24));
        botaoClientes.setBounds(460, 160, 260, 150);
        painelBrancoFundo.add(botaoClientes);
        botaoClientes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!PermissaoAcesso.autorizar(TelaHome.this, PermissaoAcesso.podeAcessarClientes(), "Clientes")) return;
                TelaClientes telaClientes = new TelaClientes();
                telaClientes.setVisible(true);
                dispose();
            }
        });

        EstilizacaoRedonda.BotaoRedondo botaoEstoque = new EstilizacaoRedonda.BotaoRedondo("Estoque", Constantes.VERMELHO_FERRARI, Constantes.AMARELO_OURO, Constantes.PRETO_FOSCO, 40);
        botaoEstoque.setForeground(Constantes.CINZA_CLARO);
        botaoEstoque.setFont(new Font("SansSerif", Font.BOLD, 24));
        botaoEstoque.setBounds(770, 160, 260, 150);
        painelBrancoFundo.add(botaoEstoque);
        botaoEstoque.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!PermissaoAcesso.autorizar(TelaHome.this, PermissaoAcesso.podeAcessarEstoque(), "Estoque")) return;
                TelaEstoque telaEstoque = new TelaEstoque();
                telaEstoque.setVisible(true);
                dispose();
            }
        });

        EstilizacaoRedonda.BotaoRedondo botaoEquipe = new EstilizacaoRedonda.BotaoRedondo("Equipe", Constantes.VERMELHO_FERRARI, Constantes.AMARELO_OURO, Constantes.PRETO_FOSCO, 40);
        botaoEquipe.setForeground(Constantes.CINZA_CLARO);
        botaoEquipe.setFont(new Font("SansSerif", Font.BOLD, 24));
        botaoEquipe.setBounds(305, 340, 260, 150);
        painelBrancoFundo.add(botaoEquipe);
        botaoEquipe.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!PermissaoAcesso.autorizar(TelaHome.this, PermissaoAcesso.podeAcessarEquipe(), "Equipe")) return;
                TelaEquipe telaEquipe = new TelaEquipe();
                telaEquipe.setVisible(true);
                dispose();
            }
        });

        EstilizacaoRedonda.BotaoRedondo botaoFinanceiro = new EstilizacaoRedonda.BotaoRedondo("Finanças", Constantes.VERMELHO_FERRARI, Constantes.AMARELO_OURO, Constantes.PRETO_FOSCO, 40);
        botaoFinanceiro.setForeground(Constantes.CINZA_CLARO);
        botaoFinanceiro.setFont(new Font("SansSerif", Font.BOLD, 24));
        botaoFinanceiro.setBounds(615, 340, 260, 150);
        painelBrancoFundo.add(botaoFinanceiro);
        botaoFinanceiro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!PermissaoAcesso.autorizar(TelaHome.this, PermissaoAcesso.podeAcessarFinancas(), "Finanças")) return;
                TelaFinancas telaFinancas = new TelaFinancas();
                telaFinancas.setVisible(true);
                dispose();
            }
        });

        configurarPermissoesBotoes(botaoOrdemServico, botaoClientes, botaoEstoque, botaoEquipe, botaoFinanceiro);

        EstilizacaoRedonda.BotaoRedondo botaoSair = new EstilizacaoRedonda.BotaoRedondo("", Constantes.VERMELHO_FERRARI, Constantes.AMARELO_OURO, Constantes.PRETO_FOSCO, 40);
        botaoSair.setForeground(Constantes.CINZA_CLARO);
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
                Sessao.encerrarSessao();
                TelaLogin telaLogin = new TelaLogin();
                telaLogin.setVisible(true);
                dispose();
            }
        });
    }

    private void configurarPermissoesBotoes(EstilizacaoRedonda.BotaoRedondo botaoOrdemServico,
                                            EstilizacaoRedonda.BotaoRedondo botaoClientes,
                                            EstilizacaoRedonda.BotaoRedondo botaoEstoque,
                                            EstilizacaoRedonda.BotaoRedondo botaoEquipe,
                                            EstilizacaoRedonda.BotaoRedondo botaoFinanceiro) {
        botaoOrdemServico.setVisible(PermissaoAcesso.podeAcessarOrdemServico());
        botaoClientes.setVisible(PermissaoAcesso.podeAcessarClientes());
        botaoEstoque.setVisible(PermissaoAcesso.podeAcessarEstoque());
        botaoEquipe.setVisible(PermissaoAcesso.podeAcessarEquipe());
        botaoFinanceiro.setVisible(PermissaoAcesso.podeAcessarFinancas());

        java.util.List<EstilizacaoRedonda.BotaoRedondo> botoesVisiveis = new java.util.ArrayList<>();
        if (botaoOrdemServico.isVisible()) botoesVisiveis.add(botaoOrdemServico);
        if (botaoClientes.isVisible()) botoesVisiveis.add(botaoClientes);
        if (botaoEstoque.isVisible()) botoesVisiveis.add(botaoEstoque);
        if (botaoEquipe.isVisible()) botoesVisiveis.add(botaoEquipe);
        if (botaoFinanceiro.isVisible()) botoesVisiveis.add(botaoFinanceiro);

        int[][] posicoes = {
            {150, 160}, {460, 160}, {770, 160},
            {305, 340}, {615, 340}
        };

        for (int i = 0; i < botoesVisiveis.size() && i < posicoes.length; i++) {
            botoesVisiveis.get(i).setBounds(posicoes[i][0], posicoes[i][1], 260, 150);
        }
    }

    private String obterTituloUsuarioLogado() {
        Funcionario funcionario = Sessao.funcionarioLogado;

        if (funcionario == null) {
            return "Bem-vindo";
        }

        String nome = funcionario.getNome() != null && !funcionario.getNome().trim().isEmpty()
                ? funcionario.getNome().trim()
                : "Usuário";

        String cargo = formatarCargo(funcionario.getCargo());

        return "Bem-vindo, " + nome + " - " + cargo;
    }

    private String formatarCargo(Cargo cargo) {
        if (cargo == null) {
            return "Cargo não informado";
        }

        String texto = cargo.name().toLowerCase().replace("_", " ");
        String[] partes = texto.split(" ");
        StringBuilder formatado = new StringBuilder();

        for (String parte : partes) {
            if (parte.isEmpty()) continue;
            if (formatado.length() > 0) formatado.append(" ");
            formatado.append(Character.toUpperCase(parte.charAt(0))).append(parte.substring(1));
        }

        return formatado.toString();
    }
}