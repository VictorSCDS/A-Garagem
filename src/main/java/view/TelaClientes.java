package view;

import utils.Constantes;
import utils.PermissaoAcesso;

import controllers.ClienteController;
import entities.Cliente;
import exceptions.ControllerException;
import java.util.Optional;
import java.util.List;
import javax.swing.JOptionPane;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
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

public class TelaClientes extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel painelPretoFundo;
    private EstilizacaoRedonda.PainelRedondo painelCliente;
    private ClienteController clienteController;
    private Cliente clienteVisualizado;
    private JLabel nomeCliente;
    private JLabel cpfCliente;
    private JLabel emailCliente;
    private JLabel telefoneCliente;
    
    private String filtroAtual = null; 

    public TelaClientes() {
        if (!PermissaoAcesso.autorizar(this, PermissaoAcesso.podeAcessarClientes(), "Clientes")) {
            javax.swing.SwingUtilities.invokeLater(() -> {
                new TelaHome().setVisible(true);
                dispose();
            });
            return;
        }


        this.clienteController = new ClienteController();
        
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

        JLabel titulo = new JLabel("Clientes");
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
                    if(!"Todos".equals(filtroAtual)) {
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
        JMenuItem opCpf = new JMenuItem("Buscar por CPF");
        JMenuItem opNome = new JMenuItem("Buscar por Nome");
        JMenuItem opPlaca = new JMenuItem("Buscar por Placa do Veículo");
        JMenuItem opTelefone = new JMenuItem("Buscar por Telefone");
        JMenuItem opEmail = new JMenuItem("Buscar por E-mail");
        JMenuItem opTodos = new JMenuItem("Buscar Todos");

        ActionListener acaoSelecionarFiltro = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                filtroAtual = e.getActionCommand().replace("Buscar por ", "").replace("Buscar ", ""); 
                
                if (filtroAtual.equals("Todos")) {
                    campoBusca.setText("Filtro: Todos (Clique na lupa para listar)");
                } else {
                    campoBusca.setText("Filtro: " + filtroAtual + " (Digite aqui...)");
                }
            }
        };

        opCpf.addActionListener(acaoSelecionarFiltro);
        opNome.addActionListener(acaoSelecionarFiltro);
        opPlaca.addActionListener(acaoSelecionarFiltro);
        opTelefone.addActionListener(acaoSelecionarFiltro);
        opEmail.addActionListener(acaoSelecionarFiltro);
        opTodos.addActionListener(acaoSelecionarFiltro);

        menuFiltros.add(opTodos);
        menuFiltros.addSeparator();
        menuFiltros.add(opCpf);
        menuFiltros.add(opNome);
        menuFiltros.add(opPlaca);
        menuFiltros.add(opTelefone);
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
                    JOptionPane.showMessageDialog(null, 
                        "Por favor, clique no botão de filtros e selecione uma opção de busca primeiro.", 
                        "Filtro Ausente", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    if (filtroAtual.equals("Todos")) {
                        List<Cliente> todosClientes = clienteController.listarTodos();
                        if (todosClientes.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Nenhum cliente cadastrado no sistema.");
                        } else {
                            exibirModalTodosClientes(todosClientes);
                        }
                        return; 
                    }

                    String textoBusca = campoBusca.getText().trim();
                    if (textoBusca.isEmpty() || textoBusca.equals("Buscar") || textoBusca.startsWith("Filtro:")) {
                        JOptionPane.showMessageDialog(null, 
                            "Por favor, digite a informação no campo antes de realizar a busca.", 
                            "Campo Vazio", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    Optional<Cliente> resultado = Optional.empty();

                    switch (filtroAtual) {
                        case "CPF":
                            resultado = clienteController.buscarPorCpf(textoBusca);
                            break;
                        case "Nome":
                            List<Cliente> listaNomes = clienteController.buscarPorNome(textoBusca);
                            if (!listaNomes.isEmpty()) {
                                resultado = Optional.of(listaNomes.get(0)); 
                            }
                            break;
                        case "Placa do Veículo":
                            resultado = clienteController.buscarPorPlaca(textoBusca);
                            break;
                        case "Telefone":
                            resultado = clienteController.buscarPorTelefone(textoBusca); 
                            break;
                        case "E-mail":
                            resultado = clienteController.buscarPorEmail(textoBusca); 
                            break;
                    }
                    
                    if (resultado.isPresent()) {
                        clienteVisualizado = resultado.get();
                        
                        nomeCliente.setText("Nome: " + clienteVisualizado.getNome());
                        cpfCliente.setText("CPF: " + clienteVisualizado.getCpf());
                        emailCliente.setText("E-mail: " + clienteVisualizado.getEmail());
                        telefoneCliente.setText("Telefone: " + clienteVisualizado.getTelefone());
                        
                        painelCliente.setVisible(true);
                        
                    } else {
                        JOptionPane.showMessageDialog(null, 
                            "Nenhum cliente encontrado utilizando " + filtroAtual + ": " + textoBusca, 
                            "Aviso", JOptionPane.INFORMATION_MESSAGE);
                        clienteVisualizado = null;
                        painelCliente.setVisible(false);
                    }
                } catch (ControllerException ex) {
                    JOptionPane.showMessageDialog(null, "Erro na busca: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        EstilizacaoRedonda.BotaoRedondo botaoCadastrar = new EstilizacaoRedonda.BotaoRedondo("CADASTRAR CLIENTE",Constantes.VERMELHO_FERRARI, Constantes.AMARELO_OURO, Constantes.PRETO_FOSCO,40);
        botaoCadastrar.setForeground(Constantes.CINZA_CLARO);
        botaoCadastrar.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoCadastrar.setBounds(430, 170, 420, 50);
        botaoCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TelaCadastrarCliente telaCadastrarCliente = new TelaCadastrarCliente();
                telaCadastrarCliente.setVisible(true);
                dispose();
            }
        });
        
        painelPretoFundo.add(botaoCadastrar);
        
        painelCliente = new EstilizacaoRedonda.PainelRedondo(null, 30, 4, Constantes.CINZA_CLARO, Constantes.PRETO_FOSCO);
        painelCliente.setBounds(40, 250, 1160, 170);
        painelCliente.setVisible(false);
        painelPretoFundo.add(painelCliente);

        nomeCliente = new JLabel("Nome:");
        nomeCliente.setFont(new Font("SansSerif", Font.PLAIN, 24));
        nomeCliente.setBounds(27, 20, 550, 60);
        painelCliente.add(nomeCliente);

        cpfCliente = new JLabel("CPF:");
        cpfCliente.setFont(new Font("SansSerif", Font.PLAIN, 24));
        cpfCliente.setBounds(27, 92, 550, 60);
        painelCliente.add(cpfCliente);

        emailCliente = new JLabel("E-mail:");
        emailCliente.setFont(new Font("SansSerif", Font.PLAIN, 24));
        emailCliente.setBounds(589, 20, 434, 60);
        painelCliente.add(emailCliente);

        telefoneCliente = new JLabel("Telefone:");
        telefoneCliente.setFont(new Font("SansSerif", Font.PLAIN, 24));
        telefoneCliente.setBounds(589, 92, 434, 60);
        painelCliente.add(telefoneCliente);

        EstilizacaoRedonda.BotaoRedondo botaoEditar = new EstilizacaoRedonda.BotaoRedondo("", Constantes.VERMELHO_FERRARI, Constantes.AMARELO_OURO, Constantes.PRETO_FOSCO, 40);
        botaoEditar.setForeground(Constantes.CINZA_CLARO);
        botaoEditar.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoEditar.setBounds(1061, 95, 60, 50);
        java.net.URL urlIconeEditar = getClass().getResource("/assets/imagens/iconeEditar.png"); 
        if (urlIconeEditar != null) {
            java.awt.Image iconeOriginal = new javax.swing.ImageIcon(urlIconeEditar).getImage();
            java.awt.Image iconeRedimensionado = iconeOriginal.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
            botaoEditar.setIcon(new javax.swing.ImageIcon(iconeRedimensionado));
            botaoEditar.setIconTextGap(10); 
        }
        painelCliente.add(botaoEditar);
        botaoEditar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (clienteVisualizado != null) {
                    TelaEditarCliente telaEditarCliente = new TelaEditarCliente(clienteVisualizado);
                    telaEditarCliente.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Busque e selecione um cliente primeiro para poder editá-lo.");
                }
            }
        });
        
        EstilizacaoRedonda.BotaoRedondo botaoDetalhes = new EstilizacaoRedonda.BotaoRedondo("", Constantes.VERMELHO_FERRARI, Constantes.AMARELO_OURO, Constantes.PRETO_FOSCO, 40);
        botaoDetalhes.setForeground(Constantes.CINZA_CLARO);
        botaoDetalhes.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoDetalhes.setBounds(1061, 30, 60, 50);
        java.net.URL urlIconeDetalhes = getClass().getResource("/assets/imagens/iconeDetalhes.png"); 
        if (urlIconeDetalhes != null) {
            java.awt.Image iconeOriginal = new javax.swing.ImageIcon(urlIconeDetalhes).getImage();
            java.awt.Image iconeRedimensionado = iconeOriginal.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
            botaoDetalhes.setIcon(new javax.swing.ImageIcon(iconeRedimensionado));
            botaoDetalhes.setIconTextGap(10); 
        }
        painelCliente.add(botaoDetalhes);
        
        botaoDetalhes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (clienteVisualizado != null) {
                    String cpf = clienteVisualizado.getCpf();
                    String nome = clienteVisualizado.getNome();
                    ModalVeiculos modal = new ModalVeiculos(TelaClientes.this, cpf, nome);
                    modal.setVisible(true); 
                } else {
                    JOptionPane.showMessageDialog(null, "Busque e selecione um cliente primeiro para ver seus veículos.");
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

    private void exibirModalTodosClientes(List<Cliente> clientes) {
        JDialog dialog = new JDialog(this, "Lista de Todos os Clientes", true);
        dialog.setSize(800, 450);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel painelSuperior = new JPanel();
        painelSuperior.setBackground(Constantes.PRETO_FOSCO);
        JLabel lblTitulo = new JLabel("Selecione um cliente para carregar na tela principal");
        lblTitulo.setForeground(Constantes.CINZA_CLARO);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        painelSuperior.add(lblTitulo);
        dialog.add(painelSuperior, BorderLayout.NORTH);

        String[] colunas = {"Nome", "CPF", "Telefone", "E-mail"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        for (Cliente c : clientes) {
            model.addRow(new Object[]{c.getNome(), c.getCpf(), c.getTelefone(), c.getEmail()});
        }

        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFont(new Font("SansSerif", Font.PLAIN, 16));
        table.setRowHeight(30);

        dialog.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();
        JButton btnSelecionar = new JButton("Carregar Cliente Selecionado");
        btnSelecionar.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnSelecionar.setBackground(Constantes.PRETO_FOSCO);
        btnSelecionar.setForeground(Constantes.CINZA_CLARO);

        btnSelecionar.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                Cliente selecionado = clientes.get(row);
                clienteVisualizado = selecionado;
                nomeCliente.setText("Nome: " + clienteVisualizado.getNome());
                cpfCliente.setText("CPF: " + clienteVisualizado.getCpf());
                emailCliente.setText("E-mail: " + clienteVisualizado.getEmail());
                telefoneCliente.setText("Telefone: " + clienteVisualizado.getTelefone());
                
                painelCliente.setVisible(true);
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Selecione um cliente na tabela primeiro.");
            }
        });

        painelBotoes.add(btnSelecionar);
        dialog.add(painelBotoes, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
}