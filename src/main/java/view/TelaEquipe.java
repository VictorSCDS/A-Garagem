package view;

import utils.Constantes;
import utils.PermissaoAcesso;

import controllers.FuncionarioController;
import entities.Funcionario;
import exceptions.ControllerException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TelaEquipe extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel painelPretoFundo;
    private EstilizacaoRedonda.PainelRedondo painelFuncionario;
    
    private FuncionarioController funcionarioController;
    private Funcionario funcionarioVisualizado;
    private String filtroAtual = null;

    private JLabel nomeFuncionario;
    private JLabel cpfFuncionario;
    private JLabel emailFuncionario;
    private JLabel telefoneFuncionario;
    private JLabel cargoFuncionario;
    private JLabel admissaoFuncionario;

    public TelaEquipe() {
        if (!PermissaoAcesso.autorizar(this, PermissaoAcesso.podeAcessarEquipe(), "Equipe")) {
            javax.swing.SwingUtilities.invokeLater(() -> {
                new TelaHome().setVisible(true);
                dispose();
            });
            return;
        }


        this.funcionarioController = new FuncionarioController();

        setBackground(Constantes.PRETO_FOSCO);
        setSize(1280, 720);
        setMaximizedBounds(new Rectangle(0, 0, 1280, 720));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        painelPretoFundo = new JPanel();
        painelPretoFundo.setBackground(Constantes.CINZA_CLARO);
        painelPretoFundo.setLayout(null);
        setContentPane(painelPretoFundo);

        JPanel barraSuperior = new JPanel();
        barraSuperior.setBackground(Constantes.PRETO_FOSCO);
        barraSuperior.setBounds(0, 0, 1280, 80);
        barraSuperior.setLayout(null);
        painelPretoFundo.add(barraSuperior);

        JLabel titulo = new JLabel("Equipe");
        titulo.setForeground(Constantes.CINZA_CLARO);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setFont(new Font("Liberation Serif", Font.BOLD, 34));
        titulo.setBounds(440, 15, 400, 40);
        barraSuperior.add(titulo);

        JLabel logoGaragem = new JLabel();
        logoGaragem.setHorizontalAlignment(SwingConstants.CENTER);
        logoGaragem.setBounds(1170, 5, 100, 70);
        java.net.URL urlImagem = getClass().getResource("/assets/imagens/logo.png");
        if (urlImagem != null) {
            java.awt.Image imagemOriginal = new javax.swing.ImageIcon(urlImagem).getImage();
            java.awt.Image imagemRedimensionada = imagemOriginal.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH);
            logoGaragem.setIcon(new javax.swing.ImageIcon(imagemRedimensionada));
        } else {
            logoGaragem.setText("Logo Aqui");
            logoGaragem.setForeground(Constantes.CINZA_CLARO);
        }
        barraSuperior.add(logoGaragem);

        EstilizacaoRedonda.CaixaTextoRedonda campoBusca = new EstilizacaoRedonda.CaixaTextoRedonda("Buscar", Constantes.PRETO_FOSCO, Constantes.CINZA_CLARO,Constantes.PRETO_FOSCO,2, 25);
        campoBusca.setFont(new Font("SansSerif", Font.PLAIN, 16));
        campoBusca.setBounds(340, 107, 600, 45);
        campoBusca.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(campoBusca.getText().startsWith("Buscar") || campoBusca.getText().contains("Filtro:")) {
                    if (!"Todos".equals(filtroAtual)) {
                        campoBusca.setText("");
                    }
                }
            }
        });
        painelPretoFundo.add(campoBusca);

        EstilizacaoRedonda.BotaoRedondo botaoFiltros = new EstilizacaoRedonda.BotaoRedondo("", Constantes.VERMELHO_FERRARI, Constantes.AMARELO_OURO, Constantes.PRETO_FOSCO, 40);
        botaoFiltros.setForeground(Constantes.CINZA_CLARO);
        botaoFiltros.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoFiltros.setBounds(236, 102, 70, 60);
        java.net.URL urlIconeFiltro = getClass().getResource("/assets/imagens/iconeFiltro.png"); 
        if (urlIconeFiltro != null) {
            java.awt.Image iconeOriginal = new javax.swing.ImageIcon(urlIconeFiltro).getImage();
            java.awt.Image iconeRedimensionado = iconeOriginal.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
            botaoFiltros.setIcon(new javax.swing.ImageIcon(iconeRedimensionado));
            botaoFiltros.setIconTextGap(10); 
        }
        painelPretoFundo.add(botaoFiltros);
        
        JPopupMenu menuFiltros = new JPopupMenu();
        JMenuItem opEmail = new JMenuItem("Buscar por E-mail");
        JMenuItem opCpf = new JMenuItem("Buscar por CPF");
        JMenuItem opCargo = new JMenuItem("Buscar por Cargo");
        JMenuItem opTodos = new JMenuItem("Buscar Todos");

        ActionListener acaoSelecionarFiltro = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                filtroAtual = e.getActionCommand().replace("Buscar por ", "").replace("Buscar ", ""); 
                if (filtroAtual.equals("Todos")) {
                    campoBusca.setText("Filtro: Todos (Clique na lupa para listar)");
                } else if (filtroAtual.equals("Cargo")) {
                    campoBusca.setText("Filtro: Cargo (Ex: GERENTE, MECANICO...)");
                } else {
                    campoBusca.setText("Filtro: " + filtroAtual + " (Digite aqui...)");
                }
            }
        };

        opEmail.addActionListener(acaoSelecionarFiltro);
        opCpf.addActionListener(acaoSelecionarFiltro);
        opCargo.addActionListener(acaoSelecionarFiltro);
        opTodos.addActionListener(acaoSelecionarFiltro);

        menuFiltros.add(opTodos);
        menuFiltros.addSeparator();
        menuFiltros.add(opCargo);
        menuFiltros.add(opCpf);
        menuFiltros.add(opEmail);

        botaoFiltros.addActionListener(e -> menuFiltros.show(botaoFiltros, 0, botaoFiltros.getHeight()));

        EstilizacaoRedonda.BotaoRedondo botaoBuscar = new EstilizacaoRedonda.BotaoRedondo("", Constantes.VERMELHO_FERRARI, Constantes.AMARELO_OURO, Constantes.PRETO_FOSCO, 40);
        botaoBuscar.setForeground(Constantes.CINZA_CLARO);
        botaoBuscar.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoBuscar.setBounds(979, 102, 70, 60);
        java.net.URL urlIconeBuscar = getClass().getResource("/assets/imagens/iconeBuscar.png"); 
        if (urlIconeBuscar != null) {
            java.awt.Image iconeOriginal = new javax.swing.ImageIcon(urlIconeBuscar).getImage();
            java.awt.Image iconeRedimensionado = iconeOriginal.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
            botaoBuscar.setIcon(new javax.swing.ImageIcon(iconeRedimensionado));
            botaoBuscar.setIconTextGap(10); 
        }
        painelPretoFundo.add(botaoBuscar);
        
        botaoBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (filtroAtual == null) {
                    JOptionPane.showMessageDialog(null, "Por favor, selecione uma opção no botão de filtros primeiro.", "Filtro Ausente", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    if (filtroAtual.equals("Todos")) {
                        List<Funcionario> todosFuncionarios = funcionarioController.listarTodos();
                        if (todosFuncionarios.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Nenhum funcionário cadastrado no sistema.");
                        } else {
                            exibirModalTodosFuncionarios(todosFuncionarios);
                        }
                        return;
                    }

                    String textoBusca = campoBusca.getText().trim();
                    if (textoBusca.isEmpty() || textoBusca.equals("Buscar") || textoBusca.startsWith("Filtro:")) {
                        JOptionPane.showMessageDialog(null, "Por favor, digite a informação no campo de busca.", "Campo Vazio", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    Optional<Funcionario> resultado = Optional.empty();

                    switch (filtroAtual) {
                        case "E-mail":
                            resultado = funcionarioController.buscarFuncionario(textoBusca);
                            break;
                        case "CPF":
                            resultado = funcionarioController.buscarFuncionarioPorCpf(textoBusca);
                            break;
                        case "Cargo":
                            List<Funcionario> todosPorCargo = funcionarioController.listarTodos();
                            List<Funcionario> resultadosCargo = new ArrayList<>();
                            
                            String cargoBuscado = textoBusca.replace(" ", "_").toUpperCase();
                            
                            for (Funcionario f : todosPorCargo) {
                                if (f.getCargo() != null && f.getCargo().name().equals(cargoBuscado)) {
                                    resultadosCargo.add(f);
                                }
                            }
                            
                            if (resultadosCargo.isEmpty()) {
                                break;
                            } else if (resultadosCargo.size() == 1) {
                                resultado = Optional.of(resultadosCargo.get(0));
                            } else {
                                exibirModalTodosFuncionarios(resultadosCargo);
                                return;
                            }
                            break;
                    }

                    if (resultado.isPresent()) {
                        funcionarioVisualizado = resultado.get();
                        atualizarPainelFuncionario();
                    } else {
                        JOptionPane.showMessageDialog(null, "Nenhum funcionário encontrado utilizando " + filtroAtual + ": " + textoBusca, "Aviso", JOptionPane.INFORMATION_MESSAGE);
                        funcionarioVisualizado = null;
                        painelFuncionario.setVisible(false);
                    }
                } catch (ControllerException ex) {
                    JOptionPane.showMessageDialog(null, "Erro na busca: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        EstilizacaoRedonda.BotaoRedondo botaoCadastrar = new EstilizacaoRedonda.BotaoRedondo("CADASTRAR FUNCIONÁRIO", Constantes.VERMELHO_FERRARI, Constantes.AMARELO_OURO, Constantes.PRETO_FOSCO, 40);
        botaoCadastrar.setForeground(Constantes.CINZA_CLARO);
        botaoCadastrar.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoCadastrar.setBounds(430, 170, 420, 50);
        painelPretoFundo.add(botaoCadastrar);
        botaoCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TelaCadastrarFuncionario telaCadastrarFuncionario = new TelaCadastrarFuncionario();
                telaCadastrarFuncionario.setVisible(true);
                dispose();
            }
        });

        painelFuncionario = new EstilizacaoRedonda.PainelRedondo(null, 30, 4, Constantes.CINZA_CLARO, Constantes.PRETO_FOSCO);
        painelFuncionario.setBounds(30, 250, 1200, 150);
        painelFuncionario.setVisible(false);
        painelPretoFundo.add(painelFuncionario);

        nomeFuncionario = new JLabel("Nome:");
        nomeFuncionario.setFont(new Font("SansSerif", Font.PLAIN, 20));
        nomeFuncionario.setBounds(25, 20, 317, 50);
        painelFuncionario.add(nomeFuncionario);

        cpfFuncionario = new JLabel("CPF:");
        cpfFuncionario.setFont(new Font("SansSerif", Font.PLAIN, 20));
        cpfFuncionario.setBounds(25, 82, 317, 50);
        painelFuncionario.add(cpfFuncionario);

        emailFuncionario = new JLabel("E-mail:");
        emailFuncionario.setFont(new Font("SansSerif", Font.PLAIN, 20));
        emailFuncionario.setBounds(354, 20, 402, 50);
        painelFuncionario.add(emailFuncionario);

        telefoneFuncionario = new JLabel("Telefone:");
        telefoneFuncionario.setFont(new Font("SansSerif", Font.PLAIN, 20));
        telefoneFuncionario.setBounds(354, 82, 402, 50);
        painelFuncionario.add(telefoneFuncionario);

        cargoFuncionario = new JLabel("Cargo:");
        cargoFuncionario.setFont(new Font("SansSerif", Font.PLAIN, 20));
        cargoFuncionario.setBounds(806, 20, 291, 50);
        painelFuncionario.add(cargoFuncionario);

        admissaoFuncionario = new JLabel("Admissão:");
        admissaoFuncionario.setFont(new Font("SansSerif", Font.PLAIN, 20));
        admissaoFuncionario.setBounds(806, 82, 291, 50);
        painelFuncionario.add(admissaoFuncionario);

        EstilizacaoRedonda.BotaoRedondo botaoEditar = new EstilizacaoRedonda.BotaoRedondo("", Constantes.VERMELHO_FERRARI, Constantes.AMARELO_OURO, Constantes.PRETO_FOSCO, 40);
        botaoEditar.setForeground(Constantes.CINZA_CLARO);
        botaoEditar.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoEditar.setBounds(1115, 50, 60, 50);
        java.net.URL urlIconeEditar = getClass().getResource("/assets/imagens/iconeEditar.png"); 
        if (urlIconeEditar != null) {
            java.awt.Image iconeOriginal = new javax.swing.ImageIcon(urlIconeEditar).getImage();
            java.awt.Image iconeRedimensionado = iconeOriginal.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
            botaoEditar.setIcon(new javax.swing.ImageIcon(iconeRedimensionado));
            botaoEditar.setIconTextGap(10); 
        }
        painelFuncionario.add(botaoEditar);
        
        botaoEditar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (funcionarioVisualizado != null) {
                    TelaEditarFuncionario telaEditar = new TelaEditarFuncionario(funcionarioVisualizado);
                    telaEditar.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Busque um funcionário primeiro.");
                }
            }
        });

        EstilizacaoRedonda.BotaoRedondo botaoVoltar = new EstilizacaoRedonda.BotaoRedondo("", Constantes.VERMELHO_FERRARI, Constantes.AMARELO_OURO, Constantes.PRETO_FOSCO, 40);
        botaoVoltar.setForeground(Constantes.CINZA_CLARO);
        botaoVoltar.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoVoltar.setBounds(1130, 600, 90, 50);
        java.net.URL urlIconeSair = getClass().getResource("/assets/imagens/iconVoltar.png"); 
        if (urlIconeSair != null) {
            java.awt.Image iconeOriginal = new javax.swing.ImageIcon(urlIconeSair).getImage();
            java.awt.Image iconeRedimensionado = iconeOriginal.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
            botaoVoltar.setIcon(new javax.swing.ImageIcon(iconeRedimensionado));
            botaoVoltar.setIconTextGap(10); 
        }
        painelPretoFundo.add(botaoVoltar);
        botaoVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TelaHome telaHome = new TelaHome();
                telaHome.setVisible(true);
                dispose();
            }
        });
    }

    private void atualizarPainelFuncionario() {
        if (funcionarioVisualizado != null) {
            nomeFuncionario.setText("Nome: " + funcionarioVisualizado.getNome());
            cpfFuncionario.setText("CPF: " + funcionarioVisualizado.getCpf());
            emailFuncionario.setText("E-mail: " + funcionarioVisualizado.getEmail());
            telefoneFuncionario.setText("Telefone: " + funcionarioVisualizado.getTelefone());
            cargoFuncionario.setText("Cargo: " + (funcionarioVisualizado.getCargo() != null ? funcionarioVisualizado.getCargo().name() : "Não definido"));
            admissaoFuncionario.setText("Admissão: " + funcionarioVisualizado.getDataAdmissao());
            
            painelFuncionario.setVisible(true);
        }
    }

    private void exibirModalTodosFuncionarios(List<Funcionario> funcionarios) {
        JDialog dialog = new JDialog(this, "Lista de Funcionários", true);
        dialog.setSize(800, 450);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel painelSuperior = new JPanel();
        painelSuperior.setBackground(Constantes.PRETO_FOSCO);
        JLabel lblTitulo = new JLabel("Selecione um funcionário para carregar na tela principal");
        lblTitulo.setForeground(Constantes.CINZA_CLARO);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        painelSuperior.add(lblTitulo);
        dialog.add(painelSuperior, BorderLayout.NORTH);

        String[] colunas = {"Nome", "Cargo", "CPF", "E-mail"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        for (Funcionario f : funcionarios) {
            String cargoNome = f.getCargo() != null ? f.getCargo().name() : "N/A";
            model.addRow(new Object[]{f.getNome(), cargoNome, f.getCpf(), f.getEmail()});
        }

        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFont(new Font("SansSerif", Font.PLAIN, 16));
        table.setRowHeight(30);
        dialog.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();
        JButton btnSelecionar = new JButton("Carregar Selecionado");
        btnSelecionar.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnSelecionar.setBackground(Constantes.PRETO_FOSCO);
        btnSelecionar.setForeground(Constantes.CINZA_CLARO);

        btnSelecionar.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                funcionarioVisualizado = funcionarios.get(row);
                atualizarPainelFuncionario();
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Selecione um funcionário na tabela primeiro.");
            }
        });

        painelBotoes.add(btnSelecionar);
        dialog.add(painelBotoes, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
}