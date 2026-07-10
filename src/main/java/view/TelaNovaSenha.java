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
import javax.swing.JOptionPane;
import controllers.FuncionarioController;
import utils.Sessao;
import exceptions.ControllerException;

public class TelaNovaSenha extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel painelPretoFundo;
    private EstilizacaoRedonda.PainelRedondo painelBrancoFundo;
    private EstilizacaoRedonda.CaixaSenhaRedonda senhaAreaText;
    private EstilizacaoRedonda.CaixaSenhaRedonda confirmarSenhaAreaText;

    public TelaNovaSenha() {
        if (Sessao.email == null || !Sessao.codigoSenhaValidado) {
            JOptionPane.showMessageDialog(null,
                    "Confirme o código enviado por e-mail antes de cadastrar a senha.",
                    "Código não confirmado",
                    JOptionPane.WARNING_MESSAGE);
        }

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
        logoGaragem.setBounds(1050, 20, 100, 70); 
        java.net.URL urlImagem = getClass().getResource("/assets/imagens/logo.png");
        if (urlImagem != null) {
            java.awt.Image imagemOriginal = new javax.swing.ImageIcon(urlImagem).getImage();
            java.awt.Image imagemRedimensionada = imagemOriginal.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH);
            logoGaragem.setIcon(new javax.swing.ImageIcon(imagemRedimensionada));
        } else {
            logoGaragem.setText("Logo Aqui");
            logoGaragem.setForeground(Constantes.PRETO_FOSCO); 
        }
        painelBrancoFundo.add(logoGaragem);

        JLabel titulo = new JLabel("Nova Senha");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setFont(new Font("Liberation Serif", Font.BOLD, 46));
        titulo.setBounds(390, 50, 400, 50);
        painelBrancoFundo.add(titulo);

        JLabel textoOrientacao = new JLabel("Crie a senha de acesso para " + emailAtual());
        textoOrientacao.setHorizontalAlignment(SwingConstants.CENTER);
        textoOrientacao.setFont(new Font("SansSerif", Font.BOLD, 18));
        textoOrientacao.setBounds(290, 110, 600, 30);
        painelBrancoFundo.add(textoOrientacao);

        JLabel tituloSenha = new JLabel("Senha");
        tituloSenha.setHorizontalAlignment(SwingConstants.CENTER);
        tituloSenha.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        tituloSenha.setBounds(390, 150, 400, 35);
        painelBrancoFundo.add(tituloSenha);

        senhaAreaText = new EstilizacaoRedonda.CaixaSenhaRedonda("Digite sua nova senha", Constantes.PRETO_FOSCO, Constantes.CINZA_CLARO,Constantes.PRETO_FOSCO,2, 25);
        senhaAreaText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        senhaAreaText.setBounds(290, 200, 600, 50);
        painelBrancoFundo.add(senhaAreaText);

        EstilizacaoRedonda.BotaoRedondo botaoOlhoSenha = EstilizacaoRedonda.criarBotaoAlternarVisibilidadeSenha(senhaAreaText);
        botaoOlhoSenha.setBounds(900, 200, 50, 50);
        painelBrancoFundo.add(botaoOlhoSenha);

        JLabel tituloConfirmar = new JLabel("Confirmar Senha");
        tituloConfirmar.setHorizontalAlignment(SwingConstants.CENTER);
        tituloConfirmar.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        tituloConfirmar.setBounds(390, 280, 400, 35);
        painelBrancoFundo.add(tituloConfirmar);

        confirmarSenhaAreaText = new EstilizacaoRedonda.CaixaSenhaRedonda("Confirme sua senha", Constantes.PRETO_FOSCO, Constantes.CINZA_CLARO,Constantes.PRETO_FOSCO,2, 25);
        confirmarSenhaAreaText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        confirmarSenhaAreaText.setBounds(290, 330, 600, 50);
        painelBrancoFundo.add(confirmarSenhaAreaText);

        EstilizacaoRedonda.BotaoRedondo botaoOlhoConfirmarSenha = EstilizacaoRedonda.criarBotaoAlternarVisibilidadeSenha(confirmarSenhaAreaText);
        botaoOlhoConfirmarSenha.setBounds(900, 330, 50, 50);
        painelBrancoFundo.add(botaoOlhoConfirmarSenha);

        EstilizacaoRedonda.BotaoRedondo botaoSalvar = new EstilizacaoRedonda.BotaoRedondo("SALVAR", Constantes.VERMELHO_FERRARI, Constantes.AMARELO_OURO, Constantes.PRETO_FOSCO, 40);
        botaoSalvar.setForeground(Constantes.CINZA_CLARO);
        botaoSalvar.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoSalvar.setBounds(465, 430, 250, 50);
        painelBrancoFundo.add(botaoSalvar);
        botaoSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                salvarNovaSenha();
            }
        });
        EstilizacaoRedonda.BotaoRedondo botaoVoltar = new EstilizacaoRedonda.BotaoRedondo("", Constantes.VERMELHO_FERRARI, Constantes.AMARELO_OURO, Constantes.PRETO_FOSCO, 40);
        botaoVoltar.setForeground(Constantes.CINZA_CLARO);
        botaoVoltar.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoVoltar.setBounds(1020, 520, 90, 50);
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
                TelaCadastrarSenhaCodigo telaCodigo = new TelaCadastrarSenhaCodigo();
                telaCodigo.setVisible(true);
                dispose();
            }
        });
    }

    private void salvarNovaSenha() {
        if (Sessao.email == null || !Sessao.codigoSenhaValidado) {
            JOptionPane.showMessageDialog(this,
                    "Confirme o código enviado por e-mail antes de cadastrar a senha.",
                    "Código não confirmado",
                    JOptionPane.WARNING_MESSAGE);
            TelaCadastrarSenha telaEmail = new TelaCadastrarSenha();
            telaEmail.setVisible(true);
            dispose();
            return;
        }

        String senha = new String(senhaAreaText.getPassword());
        String confirmar = new String(confirmarSenhaAreaText.getPassword());

        if(senha.isEmpty() || confirmar.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.", "Campos obrigatórios", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if(senha.length() < 6) {
            JOptionPane.showMessageDialog(this, "A senha deve ter pelo menos 6 caracteres.", "Senha fraca", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if(!senha.equals(confirmar)) {
            JOptionPane.showMessageDialog(this, "As senhas não coincidem.", "Confirmação inválida", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        FuncionarioController controller = new FuncionarioController();

        try {
            controller.alterarSenha(Sessao.email, senha);

        } catch (ControllerException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar a senha: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            return;
        }

        Sessao.limparDadosCadastroSenha();
        JOptionPane.showMessageDialog(this, "Senha cadastrada com sucesso! Faça login para acessar o sistema.");
        TelaLogin telaLogin = new TelaLogin();
        telaLogin.setVisible(true);
        dispose();
    }

    private String emailAtual() {
        if (Sessao.email == null || Sessao.email.trim().isEmpty()) {
            return "o funcionário";
        }
        return Sessao.email;
    }
}