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
import utils.Sessao;

public class TelaCadastrarSenhaCodigo extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel painelPretoFundo;
    private EstilizacaoRedonda.PainelRedondo painelBrancoFundo;
    private EstilizacaoRedonda.CaixaTextoRedonda codigoAreaText;

    public TelaCadastrarSenhaCodigo() {
        if (Sessao.email == null || Sessao.codigo == null) {
            JOptionPane.showMessageDialog(null,
                    "Solicite o código pelo e-mail antes de continuar.",
                    "Código não solicitado",
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

        JLabel tituloCadastrar = new JLabel("Cadastrar senha");
        tituloCadastrar.setHorizontalAlignment(SwingConstants.CENTER);
        tituloCadastrar.setFont(new Font("Liberation Serif", Font.BOLD, 46));
        tituloCadastrar.setBounds(390, 60, 400, 50);
        painelBrancoFundo.add(tituloCadastrar);

        JLabel textoCodigo = new JLabel("Digite o código enviado para " + emailMascarado());
        textoCodigo.setHorizontalAlignment(SwingConstants.CENTER);
        textoCodigo.setFont(new Font("SansSerif", Font.BOLD, 18));
        textoCodigo.setBounds(290, 130, 600, 30);
        painelBrancoFundo.add(textoCodigo);

        JLabel tituloCodigo = new JLabel("Código");
        tituloCodigo.setHorizontalAlignment(SwingConstants.CENTER);
        tituloCodigo.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        tituloCodigo.setBounds(390, 200, 400, 35);
        painelBrancoFundo.add(tituloCodigo);

        codigoAreaText = new EstilizacaoRedonda.CaixaTextoRedonda("Digite o código", Constantes.PRETO_FOSCO, Constantes.CINZA_CLARO,Constantes.PRETO_FOSCO,2, 25);
        codigoAreaText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        codigoAreaText.setBounds(290, 250, 600, 50);
        painelBrancoFundo.add(codigoAreaText);

        EstilizacaoRedonda.BotaoRedondo botaoVerificar = new EstilizacaoRedonda.BotaoRedondo("VERIFICAR CÓDIGO", Constantes.VERMELHO_FERRARI, Constantes.AMARELO_OURO, Constantes.PRETO_FOSCO, 40);
        botaoVerificar.setForeground(Constantes.CINZA_CLARO);
        botaoVerificar.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoVerificar.setBounds(465, 360, 250, 50);
        painelBrancoFundo.add(botaoVerificar);
        botaoVerificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                verificarCodigo();
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
                TelaCadastrarSenha telaEmail = new TelaCadastrarSenha();
                telaEmail.setVisible(true);
                dispose();
            }
        });
    }

    private void verificarCodigo() {
        if (Sessao.email == null || Sessao.codigo == null) {
            JOptionPane.showMessageDialog(this,
                    "Nenhum código foi solicitado. Informe o e-mail novamente.",
                    "Código não solicitado",
                    JOptionPane.WARNING_MESSAGE);
            TelaCadastrarSenha telaEmail = new TelaCadastrarSenha();
            telaEmail.setVisible(true);
            dispose();
            return;
        }

        String codigoDigitado = codigoAreaText.getText().trim();

        if (codigoDigitado.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite o código recebido por e-mail.", "Código obrigatório", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if(codigoDigitado.equals(Sessao.codigo)) {
            Sessao.codigoSenhaValidado = true;
            TelaNovaSenha telaNova = new TelaNovaSenha();
            telaNova.setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,"Código inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String emailMascarado() {
        if (Sessao.email == null || Sessao.email.trim().isEmpty()) {
            return "seu e-mail";
        }
        return Sessao.email;
    }
}