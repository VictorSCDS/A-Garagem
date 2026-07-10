package view;

import utils.Constantes;
import utils.PermissaoAcesso;

import controllers.ClienteController;
import entities.Cliente;
import exceptions.ControllerException;
import javax.swing.JOptionPane;

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
    
    private ClienteController clienteController;
    private String cpfOriginal;
    
    public TelaEditarCliente(Cliente cliente) {
        if (!PermissaoAcesso.autorizar(this, PermissaoAcesso.podeAcessarClientes(), "Clientes")) {
            javax.swing.SwingUtilities.invokeLater(() -> {
                new TelaHome().setVisible(true);
                dispose();
            });
            return;
        }


        this.clienteController = new ClienteController();
        this.cpfOriginal = cliente.getCpf();

        setBackground(Constantes.CINZA_CLARO);
        setSize(1280, 720);
        setMaximizedBounds(new Rectangle(0, 0, 1280, 720));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        painelPretoFundo = new JPanel();
        painelPretoFundo.setBackground(Constantes.CINZA_CLARO);
        painelPretoFundo.setLayout(null);
        setContentPane(painelPretoFundo);

        faixaTitulo = new JPanel();
        faixaTitulo.setBackground(Constantes.PRETO_FOSCO);
        faixaTitulo.setLayout(null);
        faixaTitulo.setBounds(0, 0, 1280, 80);
        painelPretoFundo.add(faixaTitulo);
        
        JLabel titulo = new JLabel("Editar Cliente");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setForeground(Constantes.CINZA_CLARO);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 32));
        titulo.setBounds(340, 10, 600, 50);
        faixaTitulo.add(titulo);
        
        JLabel logoGaragem = new JLabel();
        logoGaragem.setHorizontalAlignment(SwingConstants.CENTER);
        logoGaragem.setBounds(1170, 5, 100, 70);
        java.net.URL urlImagem = getClass().getResource("/assets/imagens/logo.png");
        if (urlImagem != null) {
            java.awt.Image imagemOriginal = new javax.swing.ImageIcon(urlImagem).getImage();
            java.awt.Image imagemRedimensionada = imagemOriginal.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH);
            logoGaragem.setIcon(new javax.swing.ImageIcon(imagemRedimensionada));
        }
        faixaTitulo.add(logoGaragem); 
        
        painelBrancoFundo = new EstilizacaoRedonda.PainelRedondo(null, 0, 0, Constantes.CINZA_CLARO, Constantes.CINZA_CLARO);
        painelBrancoFundo.setBounds(0, 80, 1280, 640);
        painelBrancoFundo.setLayout(null);
        painelPretoFundo.add(painelBrancoFundo);

        JLabel lblNome = new JLabel("Nome");
        lblNome.setHorizontalAlignment(SwingConstants.CENTER);
        lblNome.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        lblNome.setBounds(266, 63, 200, 35);
        painelBrancoFundo.add(lblNome);
        
        nomeAreaText = new EstilizacaoRedonda.CaixaTextoRedonda("Digite o nome", Constantes.PRETO_FOSCO, Constantes.CINZA_CLARO,Constantes.PRETO_FOSCO,2, 25);
        nomeAreaText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        nomeAreaText.setBounds(139, 110, 462, 50);
        nomeAreaText.setText(cliente.getNome());
        painelBrancoFundo.add(nomeAreaText);

        JLabel lblCpf = new JLabel("CPF");
        lblCpf.setHorizontalAlignment(SwingConstants.CENTER);
        lblCpf.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        lblCpf.setBounds(793, 63, 200, 35);
        painelBrancoFundo.add(lblCpf);
        
        cpfAreaText = new EstilizacaoRedonda.CaixaTextoRedonda("Digite o CPF", Constantes.PRETO_FOSCO, Constantes.CINZA_CLARO,Constantes.PRETO_FOSCO,2, 25);
        cpfAreaText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        cpfAreaText.setBounds(662, 110, 462, 50);
        cpfAreaText.setText(cliente.getCpf());
        painelBrancoFundo.add(cpfAreaText);

        JLabel lblTelefone = new JLabel("Telefone");
        lblTelefone.setHorizontalAlignment(SwingConstants.CENTER);
        lblTelefone.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        lblTelefone.setBounds(266, 283, 200, 35);
        painelBrancoFundo.add(lblTelefone);
        
        telefoneAreaText = new EstilizacaoRedonda.CaixaTextoRedonda("Digite o telefone", Constantes.PRETO_FOSCO, Constantes.CINZA_CLARO,Constantes.PRETO_FOSCO,2, 25);
        telefoneAreaText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        telefoneAreaText.setBounds(139, 330, 462, 50);
        telefoneAreaText.setText(cliente.getTelefone());
        painelBrancoFundo.add(telefoneAreaText);

        JLabel lblEmail = new JLabel("E-mail");
        lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
        lblEmail.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        lblEmail.setBounds(793, 280, 200, 35);
        painelBrancoFundo.add(lblEmail);
        
        emailAreaText = new EstilizacaoRedonda.CaixaTextoRedonda("Digite o e-mail", Constantes.PRETO_FOSCO, Constantes.CINZA_CLARO,Constantes.PRETO_FOSCO,2, 25);
        emailAreaText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        emailAreaText.setBounds(662, 330, 462, 50);
        emailAreaText.setText(cliente.getEmail());
        painelBrancoFundo.add(emailAreaText);

        EstilizacaoRedonda.BotaoRedondo botaoSalvar = new EstilizacaoRedonda.BotaoRedondo("SALVAR", Constantes.VERMELHO_FERRARI, Constantes.AMARELO_OURO, Constantes.PRETO_FOSCO, 40);
        botaoSalvar.setForeground(Constantes.CINZA_CLARO);
        botaoSalvar.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoSalvar.setBounds(502, 480, 257, 50);
        painelBrancoFundo.add(botaoSalvar);
        
        botaoSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nomeNovo = nomeAreaText.getText().trim();
                String cpfNovo = cpfAreaText.getText().trim();
                String telefoneNovo = telefoneAreaText.getText().trim();
                String emailNovo = emailAreaText.getText().trim();

                try {
                    boolean teveAlteracao = !nomeNovo.equals(cliente.getNome()) ||
                                            !cpfNovo.equals(cliente.getCpf()) ||
                                            !telefoneNovo.equals(cliente.getTelefone()) ||
                                            !emailNovo.equals(cliente.getEmail());

                    if (teveAlteracao) {
                        clienteController.editar(nomeNovo, cpfNovo, telefoneNovo, emailNovo, cpfOriginal);
                    }
                    
                    int resposta = JOptionPane.showConfirmDialog(
                            TelaEditarCliente.this, 
                            (teveAlteracao ? "Cliente atualizado com sucesso!\n\n" : "Nenhuma alteração nos dados do cliente.\n\n") + 
                            "Deseja gerenciar (editar/remover) as motos deste cliente agora?", 
                            "Gerenciar Veículos", 
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE
                    );
                    
                    if (resposta == JOptionPane.YES_OPTION) {
                        ModalGerenciarVeiculos modal = new ModalGerenciarVeiculos(TelaEditarCliente.this, cpfNovo, nomeNovo);
                        modal.setVisible(true);
                    }
                    
                    TelaClientes telaClientes = new TelaClientes();
                    telaClientes.setVisible(true);
                    dispose();
                    
                } catch (ControllerException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro ao Editar", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        EstilizacaoRedonda.BotaoRedondo botaoApagar = new EstilizacaoRedonda.BotaoRedondo("APAGAR", Constantes.VERMELHO_FERRARI,Constantes.AMARELO_OURO,Constantes.PRETO_FOSCO,40);
        botaoApagar.setForeground(Constantes.CINZA_CLARO);
        botaoApagar.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoApagar.setBounds(54, 520, 176, 50);
        painelBrancoFundo.add(botaoApagar);
        
        botaoApagar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int confirmacao = JOptionPane.showConfirmDialog(
                        TelaEditarCliente.this, 
                        "Tem certeza que deseja apagar o cliente " + cliente.getNome() + " permanentemente?", 
                        "Confirmar Exclusão", 
                        JOptionPane.YES_NO_OPTION, 
                        JOptionPane.WARNING_MESSAGE
                );
                
                if (confirmacao == JOptionPane.YES_OPTION) {
                    try {
                        clienteController.excluir(cpfOriginal);
                        JOptionPane.showMessageDialog(null, "Cliente apagado com sucesso.");
                        
                        TelaClientes telaClientes = new TelaClientes();
                        telaClientes.setVisible(true);
                        dispose();
                    } catch (ControllerException ex) {
                        JOptionPane.showMessageDialog(null, "Erro ao apagar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        EstilizacaoRedonda.BotaoRedondo botaoVoltar = new EstilizacaoRedonda.BotaoRedondo("", Constantes.VERMELHO_FERRARI, Constantes.AMARELO_OURO, Constantes.PRETO_FOSCO, 40);
        botaoVoltar.setForeground(Constantes.CINZA_CLARO);
        botaoVoltar.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoVoltar.setBounds(1130, 520, 90, 50);
        java.net.URL urlIconeSair = getClass().getResource("/assets/imagens/iconVoltar.png"); 
        if (urlIconeSair != null) {
            java.awt.Image iconeOriginal = new javax.swing.ImageIcon(urlIconeSair).getImage();
            java.awt.Image iconeRedimensionado = iconeOriginal.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
            botaoVoltar.setIcon(new javax.swing.ImageIcon(iconeRedimensionado));
            botaoVoltar.setIconTextGap(10); 
        }
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