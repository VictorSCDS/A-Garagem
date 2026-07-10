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
import utils.Email;
import utils.Codigo;
import utils.Sessao;
import controllers.FuncionarioController;
import exceptions.ControllerException;

public class TelaCadastrarSenha extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel painelPretoFundo;
    private EstilizacaoRedonda.PainelRedondo painelBrancoFundo;
    private EstilizacaoRedonda.CaixaTextoRedonda emailAreaText;

    public TelaCadastrarSenha() {
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

        JLabel tituloCadas = new JLabel("Cadastrar senha");
        tituloCadas.setHorizontalAlignment(SwingConstants.CENTER);
        tituloCadas.setFont(new Font("Liberation Serif", Font.BOLD, 46));
        tituloCadas.setBounds(390, 60, 400, 50);
        painelBrancoFundo.add(tituloCadas);

        JLabel textoEmail = new JLabel("Digite o e-mail cadastrado do funcionário");
        textoEmail.setHorizontalAlignment(SwingConstants.CENTER);
        textoEmail.setFont(new Font("SansSerif", Font.BOLD, 18));
        textoEmail.setBounds(340, 130, 500, 30);
        painelBrancoFundo.add(textoEmail);

        JLabel tituloEmail = new JLabel("E-mail");
        tituloEmail.setHorizontalAlignment(SwingConstants.CENTER);
        tituloEmail.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        tituloEmail.setBounds(390, 200, 400, 35);
        painelBrancoFundo.add(tituloEmail);

        emailAreaText = new EstilizacaoRedonda.CaixaTextoRedonda("Digite seu e-mail", Constantes.PRETO_FOSCO, Constantes.CINZA_CLARO,Constantes.PRETO_FOSCO,2, 25);
        emailAreaText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        emailAreaText.setBounds(290, 250, 600, 50);
        painelBrancoFundo.add(emailAreaText);

        EstilizacaoRedonda.BotaoRedondo botaoEnviar = new EstilizacaoRedonda.BotaoRedondo("ENVIAR CÓDIGO", Constantes.VERMELHO_FERRARI, Constantes.AMARELO_OURO, Constantes.PRETO_FOSCO, 40);
        botaoEnviar.setForeground(Constantes.CINZA_CLARO);
        botaoEnviar.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoEnviar.setBounds(465, 360, 250, 50);
        painelBrancoFundo.add(botaoEnviar);
        botaoEnviar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                enviarCodigoCadastroSenha();
            }
         });
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
                Sessao.limparDadosCadastroSenha();
                TelaLogin telaLogin = new TelaLogin();
                telaLogin.setVisible(true);
                dispose();
            }
        });
    }

    private void enviarCodigoCadastroSenha() {
        String email = emailAreaText.getText().trim();

        if(email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite um e-mail.", "E-mail obrigatório", JOptionPane.WARNING_MESSAGE);
            return;
        }

        FuncionarioController controller = new FuncionarioController();

        try {
            if(!controller.emailExiste(email)) {
                JOptionPane.showMessageDialog(this, "E-mail não encontrado na equipe.", "Funcionário não encontrado", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if(!controller.precisaCadastrarSenha(email)) {
                JOptionPane.showMessageDialog(this,
                        "Este funcionário já possui senha cadastrada.\nUse a tela de login normalmente.",
                        "Senha já cadastrada",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            String codigo = Codigo.gerar();
            Sessao.email = email;
            Sessao.codigo = codigo;
            Sessao.codigoSenhaValidado = false;

            boolean enviado = Email.enviar(email,
                    "Código para cadastrar senha - A Garagem",
                    "Olá!\n\nSeu código para cadastrar sua senha no sistema A Garagem é: " + codigo +
                            "\n\nSe você não solicitou este código, ignore este e-mail.");

            if (!enviado) {
                Sessao.limparDadosCadastroSenha();
                JOptionPane.showMessageDialog(this,
                        "Não foi possível enviar o código.\n\nMotivo:\n" + Email.getUltimoErro(),
                        "Erro no envio",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(this, "Código enviado para o e-mail informado.");

            TelaCadastrarSenhaCodigo telaCodigo = new TelaCadastrarSenhaCodigo();
            telaCodigo.setVisible(true);
            dispose();

        } catch (ControllerException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao consultar o funcionário: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}