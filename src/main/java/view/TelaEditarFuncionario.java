package view;

import utils.Constantes;
import utils.PermissaoAcesso;

import controllers.FuncionarioController;
import entities.Funcionario;
import entities.enums.Cargo;
import exceptions.ControllerException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

public class TelaEditarFuncionario extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel painelPretoFundo;
    private JPanel faixaTitulo;
    private EstilizacaoRedonda.PainelRedondo painelBrancoFundo;

    private EstilizacaoRedonda.CaixaTextoRedonda nomeAreaText;
    private EstilizacaoRedonda.CaixaTextoRedonda cpfAreaText;
    private EstilizacaoRedonda.ComboBoxRedondo<String> cargoComboBox;
    private EstilizacaoRedonda.CaixaTextoRedonda telefoneAreaText;
    private EstilizacaoRedonda.CaixaTextoRedonda emailAreaText;
    private EstilizacaoRedonda.CaixaTextoRedonda dataAdmissaoAreaText;
    
    private FuncionarioController funcionarioController;
    private Funcionario funcionarioOriginal;
    private String cpfOriginal;

    public TelaEditarFuncionario(Funcionario funcionario) {
        if (!PermissaoAcesso.autorizar(this, PermissaoAcesso.podeAcessarEquipe(), "Equipe")) {
            javax.swing.SwingUtilities.invokeLater(() -> {
                new TelaHome().setVisible(true);
                dispose();
            });
            return;
        }


        this.funcionarioOriginal = funcionario;
        this.cpfOriginal = funcionario.getCpf();
        this.funcionarioController = new FuncionarioController();

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

        JLabel titulo = new JLabel("Editar Funcionário");
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
        lblNome.setBounds(160, 110, 200, 35);
        painelBrancoFundo.add(lblNome);
        
        nomeAreaText = new EstilizacaoRedonda.CaixaTextoRedonda("Digite o nome", Constantes.PRETO_FOSCO, Constantes.CINZA_CLARO, Constantes.PRETO_FOSCO, 2, 25);
        nomeAreaText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        nomeAreaText.setBounds(100, 160, 320, 50);
        nomeAreaText.setText(funcionario.getNome());
        painelBrancoFundo.add(nomeAreaText);

        JLabel lblCpf = new JLabel("CPF");
        lblCpf.setHorizontalAlignment(SwingConstants.CENTER);
        lblCpf.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        lblCpf.setBounds(540, 110, 200, 35);
        painelBrancoFundo.add(lblCpf);
        
        cpfAreaText = new EstilizacaoRedonda.CaixaTextoRedonda("Digite o CPF", Constantes.PRETO_FOSCO, Constantes.CINZA_CLARO, Constantes.PRETO_FOSCO, 2, 25);
        cpfAreaText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        cpfAreaText.setBounds(480, 160, 320, 50);
        cpfAreaText.setText(funcionario.getCpf());
        painelBrancoFundo.add(cpfAreaText);

        JLabel lblCargo = new JLabel("Cargo");
        lblCargo.setHorizontalAlignment(SwingConstants.CENTER);
        lblCargo.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        lblCargo.setBounds(920, 110, 200, 35);
        painelBrancoFundo.add(lblCargo);
        
        Cargo[] cargosEnum = Cargo.values();
        String[] opcoesCargo = new String[cargosEnum.length];
        for (int i = 0; i < cargosEnum.length; i++) {
            opcoesCargo[i] = Cargo.cargoToString(cargosEnum[i]); 
        }
        
        cargoComboBox = new EstilizacaoRedonda.ComboBoxRedondo<>(opcoesCargo, Constantes.CINZA_CLARO, Constantes.PRETO_FOSCO, 2, 25);
        cargoComboBox.setFont(new Font("SansSerif", Font.PLAIN, 18));
        cargoComboBox.setBounds(860, 160, 320, 50);
        cargoComboBox.setSelectedItem(Cargo.cargoToString(funcionario.getCargo()));
        painelBrancoFundo.add(cargoComboBox);

        JLabel lblTelefone = new JLabel("Telefone");
        lblTelefone.setHorizontalAlignment(SwingConstants.CENTER);
        lblTelefone.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        lblTelefone.setBounds(160, 310, 200, 35);
        painelBrancoFundo.add(lblTelefone);
        
        telefoneAreaText = new EstilizacaoRedonda.CaixaTextoRedonda("Digite o telefone", Constantes.PRETO_FOSCO, Constantes.CINZA_CLARO, Constantes.PRETO_FOSCO, 2, 25);
        telefoneAreaText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        telefoneAreaText.setBounds(100, 360, 320, 50);
        telefoneAreaText.setText(funcionario.getTelefone());
        painelBrancoFundo.add(telefoneAreaText);

        JLabel lblEmail = new JLabel("E-mail");
        lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
        lblEmail.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        lblEmail.setBounds(540, 310, 200, 35);
        painelBrancoFundo.add(lblEmail);
        
        emailAreaText = new EstilizacaoRedonda.CaixaTextoRedonda("Digite o e-mail", Constantes.PRETO_FOSCO, Constantes.CINZA_CLARO, Constantes.PRETO_FOSCO, 2, 25);
        emailAreaText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        emailAreaText.setBounds(480, 360, 320, 50);
        emailAreaText.setText(funcionario.getEmail());
        painelBrancoFundo.add(emailAreaText);

        JLabel lblDataAdmissao = new JLabel("Data admissão");
        lblDataAdmissao.setHorizontalAlignment(SwingConstants.CENTER);
        lblDataAdmissao.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        lblDataAdmissao.setBounds(920, 310, 200, 35);
        painelBrancoFundo.add(lblDataAdmissao);
        
        dataAdmissaoAreaText = new EstilizacaoRedonda.CaixaTextoRedonda("DD/MM/AAAA", Constantes.CINZA_CLARO, Constantes.CINZA_CLARO, Constantes.PRETO_FOSCO, 2, 25);
        dataAdmissaoAreaText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        dataAdmissaoAreaText.setBounds(860, 360, 320, 50);

        if (funcionario.getDataAdmissao() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            dataAdmissaoAreaText.setText(sdf.format(funcionario.getDataAdmissao()));
        }
        dataAdmissaoAreaText.setEditable(false);
        painelBrancoFundo.add(dataAdmissaoAreaText);

        EstilizacaoRedonda.BotaoRedondo botaoSalvar = new EstilizacaoRedonda.BotaoRedondo("SALVAR", Constantes.VERMELHO_FERRARI, Constantes.AMARELO_OURO, Constantes.PRETO_FOSCO, 40);
        botaoSalvar.setForeground(Constantes.CINZA_CLARO);
        botaoSalvar.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoSalvar.setBounds(510, 520, 260, 50);
        painelBrancoFundo.add(botaoSalvar);
        
        botaoSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nome = nomeAreaText.getText().trim();
                String cpf = cpfAreaText.getText().trim();
                String telefone = telefoneAreaText.getText().trim();
                String email = emailAreaText.getText().trim();
                String cargoStr = (String) cargoComboBox.getSelectedItem();
                
                String cargoConvertido = cargoStr.replace(" ", "_").toUpperCase();

                boolean teveAlteracao = !nome.equals(funcionarioOriginal.getNome()) ||
                                        !cpf.equals(funcionarioOriginal.getCpf()) ||
                                        !telefone.equals(funcionarioOriginal.getTelefone()) ||
                                        !email.equals(funcionarioOriginal.getEmail()) ||
                                        !cargoConvertido.equals(funcionarioOriginal.getCargo().name());

                if (!teveAlteracao) {
                    JOptionPane.showMessageDialog(null, "Nenhuma alteração foi realizada.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                    TelaEquipe telaEquipe = new TelaEquipe();
                    telaEquipe.setVisible(true);
                    dispose();
                    return;
                }

                try {
                    funcionarioController.editar(nome, cpf, cargoConvertido, telefone, email, cpfOriginal);
                    
                    JOptionPane.showMessageDialog(null, "Funcionário atualizado com sucesso!");
                    TelaEquipe telaEquipe = new TelaEquipe();
                    telaEquipe.setVisible(true);
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
                        TelaEditarFuncionario.this, 
                        "Deseja realmente demitir/apagar o funcionário " + funcionarioOriginal.getNome() + " permanentemente?", 
                        "Confirmar Exclusão", 
                        JOptionPane.YES_NO_OPTION, 
                        JOptionPane.WARNING_MESSAGE
                );
                
                if (confirmacao == JOptionPane.YES_OPTION) {
                    try {
                        funcionarioController.excluir(cpfOriginal);
                        JOptionPane.showMessageDialog(null, "Funcionário apagado com sucesso.");
                        
                        TelaEquipe telaEquipe = new TelaEquipe();
                        telaEquipe.setVisible(true);
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
        botaoVoltar.setBounds(1100, 520, 90, 50);
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
                TelaEquipe telaEquipe = new TelaEquipe();
                telaEquipe.setVisible(true);
                dispose();
            }
        });
    }
}