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
import javax.swing.JOptionPane;
import utils.Hash;
import dao.UsuarioDAO;
import utils.Sessao;

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
            logoGaragem.setForeground(Color.BLACK); 
        }
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

        senhaAreaText = new EstilizacaoRedonda.CaixaSenhaRedonda("Digite sua nova senha", Color.GRAY, Color.WHITE,Color.GRAY,2, 25);
        senhaAreaText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        senhaAreaText.setBounds(290, 180, 600, 50);
        painelBrancoFundo.add(senhaAreaText);

        JLabel tituloConfirmar = new JLabel("Confirmar Senha");
        tituloConfirmar.setHorizontalAlignment(SwingConstants.CENTER);
        tituloConfirmar.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        tituloConfirmar.setBounds(390, 260, 400, 35);
        painelBrancoFundo.add(tituloConfirmar);

        confirmarSenhaAreaText = new EstilizacaoRedonda.CaixaSenhaRedonda("Confirme sua senha", Color.GRAY, Color.WHITE,Color.GRAY,2, 25);
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
                String senha = new String(senhaAreaText.getPassword());
                String confirmar = new String(confirmarSenhaAreaText.getPassword());

                if(senha.isEmpty() || confirmar.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos.");
                    return;
                }

                if(!senha.equals(confirmar)) {
                	JOptionPane.showMessageDialog(null, "As senhas não coincidem.");
                    return;
                }

                String hash = Hash.gerarHash(senha);
                UsuarioDAO usuarioDAO = new UsuarioDAO();
                usuarioDAO.atualizarSenha(Sessao.email, hash);
                Sessao.codigo = null;
                Sessao.email = null;
                JOptionPane.showMessageDialog(null, "Senha cadastrada com sucesso!");

                TelaLogin telaLogin = new TelaLogin();
                telaLogin.setVisible(true);
                dispose();
            }
        });

        EstilizacaoRedonda.BotaoRedondo botaoVoltar = new EstilizacaoRedonda.BotaoRedondo("", Color.BLACK, Color.DARK_GRAY, Color.GRAY, 40);
        botaoVoltar.setForeground(Color.WHITE);
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
}
