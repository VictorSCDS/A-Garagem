package view;

import utils.Constantes;
import utils.PermissaoAcesso;

import controllers.ItemController;
import entities.Item;
import exceptions.ControllerException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TelaEstoque extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel painelPretoFundo;
    private EstilizacaoRedonda.PainelRedondo painelProduto;

    private final ItemController itemController = new ItemController();
    private Item itemVisualizado;
    private String filtroAtual = null;

    private JLabel lblNome;
    private JLabel lblMarca;
    private JLabel lblCodigo;
    private JLabel lblValorVenda;
    private JLabel lblValorCompra;
    private JLabel lblQuantidade;

    public TelaEstoque() {
        if (!PermissaoAcesso.autorizar(this, PermissaoAcesso.podeAcessarEstoque(), "Estoque")) {
            javax.swing.SwingUtilities.invokeLater(() -> {
                new TelaHome().setVisible(true);
                dispose();
            });
            return;
        }

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

        JLabel titulo = new JLabel("Estoque");
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

        EstilizacaoRedonda.CaixaTextoRedonda campoBusca = new EstilizacaoRedonda.CaixaTextoRedonda("Buscar", Constantes.PRETO_FOSCO, Constantes.CINZA_CLARO, Constantes.PRETO_FOSCO, 2, 25);
        campoBusca.setFont(new Font("SansSerif", Font.PLAIN, 16));
        campoBusca.setBounds(340, 107, 600, 45);
        campoBusca.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (campoBusca.getText().startsWith("Buscar") || campoBusca.getText().contains("Filtro:")) {
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
        JMenuItem opTodos = new JMenuItem("Buscar Todos");
        JMenuItem opCodigo = new JMenuItem("Buscar por Código");
        JMenuItem opNome = new JMenuItem("Buscar por Nome");
        JMenuItem opMarca = new JMenuItem("Buscar por Marca");

        ActionListener acaoSelecionarFiltro = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                filtroAtual = e.getActionCommand().replace("Buscar por ", "").replace("Buscar ", "");
                if (filtroAtual.equals("Todos")) {
                    campoBusca.setText("Filtro: Todos (Clique na lupa para listar)");
                } else if (filtroAtual.equals("Código")) {
                    campoBusca.setText("Filtro: Código (Digite o código...)");
                } else if (filtroAtual.equals("Nome")) {
                    campoBusca.setText("Filtro: Nome (Digite o nome...)");
                } else if (filtroAtual.equals("Marca")) {
                    campoBusca.setText("Filtro: Marca (Digite a marca...)");
                }
            }
        };

        opTodos.addActionListener(acaoSelecionarFiltro);
        opCodigo.addActionListener(acaoSelecionarFiltro);
        opNome.addActionListener(acaoSelecionarFiltro);
        opMarca.addActionListener(acaoSelecionarFiltro);

        menuFiltros.add(opTodos);
        menuFiltros.addSeparator();
        menuFiltros.add(opCodigo);
        menuFiltros.add(opNome);
        menuFiltros.add(opMarca);

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
                buscarProdutos(campoBusca);
            }
        });

        EstilizacaoRedonda.BotaoRedondo botaoNovoProduto = new EstilizacaoRedonda.BotaoRedondo("ADICIONAR PRODUTO", Constantes.VERMELHO_FERRARI, Constantes.AMARELO_OURO, Constantes.PRETO_FOSCO, 40);
        botaoNovoProduto.setForeground(Constantes.CINZA_CLARO);
        botaoNovoProduto.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoNovoProduto.setBounds(430, 180, 420, 50);
        painelPretoFundo.add(botaoNovoProduto);
        botaoNovoProduto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TelaNovoEstoque telaNovoEstoque = new TelaNovoEstoque();
                telaNovoEstoque.setVisible(true);
                dispose();
            }
        });

        painelProduto = new EstilizacaoRedonda.PainelRedondo(null, 30, 4, Constantes.CINZA_CLARO, Constantes.PRETO_FOSCO);
        painelProduto.setBounds(30, 260, 1200, 140);
        painelProduto.setVisible(false);
        painelPretoFundo.add(painelProduto);

        lblNome = new JLabel("Nome:");
        lblNome.setFont(new Font("SansSerif", Font.PLAIN, 18));
        lblNome.setBounds(25, 20, 360, 50);
        painelProduto.add(lblNome);

        lblMarca = new JLabel("Marca:");
        lblMarca.setFont(new Font("SansSerif", Font.PLAIN, 18));
        lblMarca.setBounds(419, 20, 320, 50);
        painelProduto.add(lblMarca);

        lblCodigo = new JLabel("Código:");
        lblCodigo.setFont(new Font("SansSerif", Font.PLAIN, 18));
        lblCodigo.setBounds(25, 70, 360, 50);
        painelProduto.add(lblCodigo);

        lblValorVenda = new JLabel("Valor de venda:");
        lblValorVenda.setFont(new Font("SansSerif", Font.PLAIN, 18));
        lblValorVenda.setBounds(784, 63, 300, 65);
        painelProduto.add(lblValorVenda);

        lblValorCompra = new JLabel("Valor de compra:");
        lblValorCompra.setFont(new Font("SansSerif", Font.PLAIN, 18));
        lblValorCompra.setBounds(785, 20, 300, 50);
        painelProduto.add(lblValorCompra);

        lblQuantidade = new JLabel("Quantidade:");
        lblQuantidade.setFont(new Font("SansSerif", Font.PLAIN, 18));
        lblQuantidade.setBounds(419, 70, 320, 50);
        painelProduto.add(lblQuantidade);

        EstilizacaoRedonda.BotaoRedondo botaoEditar = new EstilizacaoRedonda.BotaoRedondo("", Constantes.VERMELHO_FERRARI, Constantes.AMARELO_OURO, Constantes.PRETO_FOSCO, 40);
        botaoEditar.setForeground(Constantes.CINZA_CLARO);
        botaoEditar.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoEditar.setBounds(1112, 20, 60, 50);
        java.net.URL urlIconeEditar = getClass().getResource("/assets/imagens/iconeEditar.png");
        if (urlIconeEditar != null) {
            java.awt.Image iconeOriginal = new javax.swing.ImageIcon(urlIconeEditar).getImage();
            java.awt.Image iconeRedimensionado = iconeOriginal.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
            botaoEditar.setIcon(new javax.swing.ImageIcon(iconeRedimensionado));
            botaoEditar.setIconTextGap(10);
        }
        painelProduto.add(botaoEditar);
        botaoEditar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (itemVisualizado != null) {
                    TelaEditarEstoque telaEditarEstoque = new TelaEditarEstoque(itemVisualizado);
                    telaEditarEstoque.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(TelaEstoque.this, "Busque e selecione um produto primeiro.");
                }
            }
        });

        EstilizacaoRedonda.BotaoRedondo botaoDetalhes = new EstilizacaoRedonda.BotaoRedondo("", Constantes.VERMELHO_FERRARI, Constantes.AMARELO_OURO, Constantes.PRETO_FOSCO, 40);
        botaoDetalhes.setForeground(Constantes.CINZA_CLARO);
        botaoDetalhes.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoDetalhes.setBounds(1112, 78, 60, 50);
        java.net.URL urlIconeDetalhes = getClass().getResource("/assets/imagens/iconeDetalhes.png");
        if (urlIconeDetalhes != null) {
            java.awt.Image iconeOriginal = new javax.swing.ImageIcon(urlIconeDetalhes).getImage();
            java.awt.Image iconeRedimensionado = iconeOriginal.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
            botaoDetalhes.setIcon(new javax.swing.ImageIcon(iconeRedimensionado));
            botaoDetalhes.setIconTextGap(10);
        }
        painelProduto.add(botaoDetalhes);
        botaoDetalhes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (itemVisualizado != null) {
                    exibirDetalhesProduto(itemVisualizado);
                } else {
                    JOptionPane.showMessageDialog(TelaEstoque.this, "Busque e selecione um produto primeiro.");
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

    private void buscarProdutos(EstilizacaoRedonda.CaixaTextoRedonda campoBusca) {
        if (filtroAtual == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma opção de busca no botão de filtros primeiro.", "Filtro Ausente", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            if (filtroAtual.equals("Todos")) {
                exibirResultadosBusca(itemController.listarTodos());
                return;
            }

            String textoBusca = campoBusca.getText().trim();
            if (textoBusca.isEmpty() || textoBusca.equals("Buscar") || textoBusca.startsWith("Filtro:")) {
                JOptionPane.showMessageDialog(this, "Digite a informação da busca no campo antes de clicar na lupa.", "Campo Vazio", JOptionPane.WARNING_MESSAGE);
                return;
            }

            switch (filtroAtual) {
                case "Código":
                    Optional<Item> itemOpt = itemController.buscarPorCodigo(textoBusca);
                    List<Item> resultadoCodigo = new ArrayList<>();
                    itemOpt.ifPresent(resultadoCodigo::add);
                    exibirResultadosBusca(resultadoCodigo);
                    return;
                case "Nome":
                    exibirResultadosBusca(itemController.buscarPorNome(textoBusca));
                    return;
                case "Marca":
                    exibirResultadosBusca(itemController.buscarPorMarca(textoBusca));
                    return;
                default:
                    JOptionPane.showMessageDialog(this, "Filtro inválido. Selecione código, nome, marca ou todos.", "Filtro Inválido", JOptionPane.WARNING_MESSAGE);
            }
        } catch (ControllerException ex) {
            JOptionPane.showMessageDialog(this, "Erro na busca: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void exibirResultadosBusca(List<Item> itens) {
        if (itens == null || itens.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum produto encontrado com o filtro informado.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            itemVisualizado = null;
            painelProduto.setVisible(false);
        } else if (itens.size() == 1) {
            carregarProdutoNaTela(itens.get(0));
        } else {
            exibirModalTodosProdutos(itens);
        }
    }

    private void carregarProdutoNaTela(Item item) {
        itemVisualizado = item;
        lblNome.setText("Nome: " + valorOuPadrao(item.getNome()));
        lblMarca.setText("Marca: " + valorOuPadrao(item.getMarca()));
        lblCodigo.setText("Código: " + valorOuPadrao(item.getCodigo()));
        lblQuantidade.setText("Quantidade: " + item.getQuantidade());
        lblValorCompra.setText("Valor de compra: R$ " + formatarMoeda(item.getValorCompra()));
        lblValorVenda.setText("Valor de venda: R$ " + formatarMoeda(item.getValorVenda()));
        painelProduto.setVisible(true);
    }

    private void exibirModalTodosProdutos(List<Item> itens) {
        JDialog dialog = new JDialog(this, "Listagem de Produtos", true);
        dialog.setSize(950, 500);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setLayout(new BorderLayout());

        JPanel painelSuperior = new JPanel();
        painelSuperior.setBackground(Constantes.PRETO_FOSCO);
        JLabel lblTitulo = new JLabel("Selecione um produto para carregar na tela principal");
        lblTitulo.setForeground(Constantes.CINZA_CLARO);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        painelSuperior.add(lblTitulo);
        dialog.getContentPane().add(painelSuperior, BorderLayout.NORTH);

        String[] colunas = {"ID", "Código", "Nome", "Marca", "Quantidade", "Valor Compra", "Valor Venda"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        for (Item item : itens) {
            model.addRow(new Object[]{
                    item.getId(),
                    item.getCodigo(),
                    item.getNome(),
                    item.getMarca(),
                    item.getQuantidade(),
                    "R$ " + formatarMoeda(item.getValorCompra()),
                    "R$ " + formatarMoeda(item.getValorVenda())
            });
        }

        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFont(new Font("SansSerif", Font.PLAIN, 16));
        table.setRowHeight(30);
        dialog.getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();
        JButton btnSelecionar = new JButton("Carregar Produto Selecionado");
        btnSelecionar.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnSelecionar.setBackground(Constantes.PRETO_FOSCO);
        btnSelecionar.setForeground(Constantes.CINZA_CLARO);

        btnSelecionar.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                carregarProdutoNaTela(itens.get(row));
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Selecione um produto na tabela primeiro.");
            }
        });

        painelBotoes.add(btnSelecionar);
        dialog.getContentPane().add(painelBotoes, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void exibirDetalhesProduto(Item item) {
        BigDecimal compra = item.getValorCompra() != null ? item.getValorCompra() : BigDecimal.ZERO;
        BigDecimal venda = item.getValorVenda() != null ? item.getValorVenda() : BigDecimal.ZERO;
        BigDecimal lucroUnitario = venda.subtract(compra);
        BigDecimal valorTotalVenda = venda.multiply(BigDecimal.valueOf(item.getQuantidade()));
        BigDecimal valorTotalCompra = compra.multiply(BigDecimal.valueOf(item.getQuantidade()));

        StringBuilder detalhes = new StringBuilder();
        detalhes.append("PRODUTO #").append(item.getId()).append("\n\n");
        detalhes.append("Nome: ").append(valorOuPadrao(item.getNome())).append("\n");
        detalhes.append("Código: ").append(valorOuPadrao(item.getCodigo())).append("\n");
        detalhes.append("Marca: ").append(valorOuPadrao(item.getMarca())).append("\n");
        detalhes.append("Quantidade em estoque: ").append(item.getQuantidade()).append("\n\n");
        detalhes.append("Valor de compra: R$ ").append(formatarMoeda(compra)).append("\n");
        detalhes.append("Valor de venda: R$ ").append(formatarMoeda(venda)).append("\n");
        detalhes.append("Lucro unitário estimado: R$ ").append(formatarMoeda(lucroUnitario)).append("\n\n");
        detalhes.append("Total em estoque pelo valor de compra: R$ ").append(formatarMoeda(valorTotalCompra)).append("\n");
        detalhes.append("Total em estoque pelo valor de venda: R$ ").append(formatarMoeda(valorTotalVenda)).append("\n");

        JDialog dialog = new JDialog(this, "Detalhes do Produto", true);
        dialog.setSize(640, 430);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setLayout(new BorderLayout());

        JTextArea areaDetalhes = new JTextArea(detalhes.toString());
        areaDetalhes.setEditable(false);
        areaDetalhes.setFont(new Font("Monospaced", Font.PLAIN, 14));
        areaDetalhes.setLineWrap(true);
        areaDetalhes.setWrapStyleWord(true);
        dialog.getContentPane().add(new JScrollPane(areaDetalhes), BorderLayout.CENTER);

        JButton btnFechar = new JButton("Fechar");
        btnFechar.addActionListener(e -> dialog.dispose());
        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnFechar);
        dialog.getContentPane().add(painelBotoes, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private String formatarMoeda(BigDecimal valor) {
        if (valor == null) valor = BigDecimal.ZERO;
        return valor.setScale(2, RoundingMode.HALF_UP).toString();
    }

    private String valorOuPadrao(String valor) {
        if (valor == null || valor.trim().isEmpty() || "null".equalsIgnoreCase(valor.trim())) {
            return "Não informado";
        }
        return valor;
    }
}