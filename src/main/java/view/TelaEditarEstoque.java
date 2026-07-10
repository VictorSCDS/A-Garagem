package view;

import utils.Constantes;
import utils.PermissaoAcesso;

import controllers.ItemController;
import entities.Item;
import exceptions.ControllerException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class TelaEditarEstoque extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel painelPretoFundo;
    private JPanel faixaTitulo;
    private EstilizacaoRedonda.PainelRedondo painelBrancoFundo;

    private EstilizacaoRedonda.CaixaTextoRedonda nomeAreaText;
    private EstilizacaoRedonda.CaixaTextoRedonda codigoAreaText;
    private EstilizacaoRedonda.CaixaTextoRedonda marcaAreaText;
    private EstilizacaoRedonda.CaixaTextoRedonda valorCompraAreaText;
    private EstilizacaoRedonda.CaixaTextoRedonda valorVendaAreaText;

    private int quantidadeEstoque = 0;
    private EstilizacaoRedonda.CaixaTextoRedonda quantidadeDisplayArea;

    private final ItemController itemController = new ItemController();
    private Item itemEditando;
    private String codigoOriginal;

    public TelaEditarEstoque() {
        this(null);
    }

    public TelaEditarEstoque(Item item) {
        if (!PermissaoAcesso.autorizar(this, PermissaoAcesso.podeAcessarEstoque(), "Estoque")) {
            javax.swing.SwingUtilities.invokeLater(() -> {
                new TelaHome().setVisible(true);
                dispose();
            });
            return;
        }

        this.itemEditando = item;
        this.codigoOriginal = item != null ? item.getCodigo() : null;
        construirTela();
        preencherCampos(item);
    }

    private void construirTela() {
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

        JLabel titulo = new JLabel("Editar Produto");
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
        } else {
            logoGaragem.setText("Logo Aqui");
            logoGaragem.setForeground(Constantes.CINZA_CLARO);
        }
        faixaTitulo.add(logoGaragem);

        painelBrancoFundo = new EstilizacaoRedonda.PainelRedondo(null, 0, 0, Constantes.CINZA_CLARO, Constantes.CINZA_CLARO);
        painelBrancoFundo.setBounds(0, 80, 1280, 640);
        painelBrancoFundo.setLayout(null);
        painelPretoFundo.add(painelBrancoFundo);

        JLabel lblNome = new JLabel("Nome");
        lblNome.setHorizontalAlignment(SwingConstants.CENTER);
        lblNome.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        lblNome.setBounds(160, 100, 200, 35);
        painelBrancoFundo.add(lblNome);

        nomeAreaText = new EstilizacaoRedonda.CaixaTextoRedonda("Digite o nome do produto", Constantes.PRETO_FOSCO, Constantes.CINZA_CLARO, Constantes.PRETO_FOSCO, 2, 25);
        nomeAreaText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        nomeAreaText.setBounds(100, 150, 320, 50);
        painelBrancoFundo.add(nomeAreaText);

        JLabel lblCodigo = new JLabel("Código");
        lblCodigo.setHorizontalAlignment(SwingConstants.CENTER);
        lblCodigo.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        lblCodigo.setBounds(160, 285, 200, 35);
        painelBrancoFundo.add(lblCodigo);

        codigoAreaText = new EstilizacaoRedonda.CaixaTextoRedonda("Digite o código do produto", Constantes.PRETO_FOSCO, Constantes.CINZA_CLARO, Constantes.PRETO_FOSCO, 2, 25);
        codigoAreaText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        codigoAreaText.setBounds(100, 332, 320, 50);
        painelBrancoFundo.add(codigoAreaText);

        JLabel lblMarca = new JLabel("Marca");
        lblMarca.setHorizontalAlignment(SwingConstants.CENTER);
        lblMarca.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        lblMarca.setBounds(537, 100, 200, 35);
        painelBrancoFundo.add(lblMarca);

        marcaAreaText = new EstilizacaoRedonda.CaixaTextoRedonda("Digite a marca do produto", Constantes.PRETO_FOSCO, Constantes.CINZA_CLARO, Constantes.PRETO_FOSCO, 2, 25);
        marcaAreaText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        marcaAreaText.setBounds(485, 150, 320, 50);
        painelBrancoFundo.add(marcaAreaText);

        JLabel lblQuantidade = new JLabel("Quantidade em estoque");
        lblQuantidade.setHorizontalAlignment(SwingConstants.CENTER);
        lblQuantidade.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        lblQuantidade.setBounds(485, 285, 320, 35);
        painelBrancoFundo.add(lblQuantidade);

        quantidadeDisplayArea = new EstilizacaoRedonda.CaixaTextoRedonda(String.valueOf(quantidadeEstoque), Constantes.PRETO_FOSCO, Constantes.CINZA_CLARO, Constantes.PRETO_FOSCO, 2, 25);
        quantidadeDisplayArea.setFont(new Font("SansSerif", Font.BOLD, 22));
        quantidadeDisplayArea.setBounds(485, 332, 160, 50);
        quantidadeDisplayArea.setEditable(true);
        painelBrancoFundo.add(quantidadeDisplayArea);

        EstilizacaoRedonda.BotaoRedondo botaoRemoverQtd = new EstilizacaoRedonda.BotaoRedondo("-", Constantes.VERMELHO_FERRARI, Constantes.AMARELO_OURO, Constantes.PRETO_FOSCO, 40);
        botaoRemoverQtd.setForeground(Constantes.CINZA_CLARO);
        botaoRemoverQtd.setFont(new Font("SansSerif", Font.BOLD, 28));
        botaoRemoverQtd.setBounds(660, 337, 60, 40);
        botaoRemoverQtd.setMargin(new java.awt.Insets(0, 0, 0, 0));
        painelBrancoFundo.add(botaoRemoverQtd);

        EstilizacaoRedonda.BotaoRedondo botaoAdicionarQtd = new EstilizacaoRedonda.BotaoRedondo("+", Constantes.VERMELHO_FERRARI, Constantes.AMARELO_OURO, Constantes.PRETO_FOSCO, 40);
        botaoAdicionarQtd.setForeground(Constantes.CINZA_CLARO);
        botaoAdicionarQtd.setFont(new Font("SansSerif", Font.BOLD, 26));
        botaoAdicionarQtd.setBounds(735, 337, 60, 40);
        botaoAdicionarQtd.setMargin(new java.awt.Insets(0, 0, 0, 0));
        painelBrancoFundo.add(botaoAdicionarQtd);

        botaoRemoverQtd.addActionListener(e -> alterarQuantidade(-1));
        botaoAdicionarQtd.addActionListener(e -> alterarQuantidade(1));

        JLabel lblValorVenda = new JLabel("Valor de venda");
        lblValorVenda.setHorizontalAlignment(SwingConstants.CENTER);
        lblValorVenda.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        lblValorVenda.setBounds(934, 100, 200, 35);
        painelBrancoFundo.add(lblValorVenda);

        valorVendaAreaText = new EstilizacaoRedonda.CaixaTextoRedonda("Digite o valor de venda", Constantes.PRETO_FOSCO, Constantes.CINZA_CLARO, Constantes.PRETO_FOSCO, 2, 25);
        valorVendaAreaText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        valorVendaAreaText.setBounds(876, 150, 320, 50);
        painelBrancoFundo.add(valorVendaAreaText);

        JLabel lblValorCompra = new JLabel("Valor de compra");
        lblValorCompra.setHorizontalAlignment(SwingConstants.CENTER);
        lblValorCompra.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        lblValorCompra.setBounds(934, 285, 200, 35);
        painelBrancoFundo.add(lblValorCompra);

        valorCompraAreaText = new EstilizacaoRedonda.CaixaTextoRedonda("Digite o valor de compra", Constantes.PRETO_FOSCO, Constantes.CINZA_CLARO, Constantes.PRETO_FOSCO, 2, 25);
        valorCompraAreaText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        valorCompraAreaText.setBounds(876, 332, 320, 50);
        painelBrancoFundo.add(valorCompraAreaText);

        EstilizacaoRedonda.BotaoRedondo botaoSalvar = new EstilizacaoRedonda.BotaoRedondo("SALVAR", Constantes.VERMELHO_FERRARI, Constantes.AMARELO_OURO, Constantes.PRETO_FOSCO, 40);
        botaoSalvar.setForeground(Constantes.CINZA_CLARO);
        botaoSalvar.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoSalvar.setBounds(510, 520, 260, 50);
        painelBrancoFundo.add(botaoSalvar);
        botaoSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                salvarProduto();
            }
        });

        EstilizacaoRedonda.BotaoRedondo botaoApagar = new EstilizacaoRedonda.BotaoRedondo("APAGAR", Constantes.VERMELHO_FERRARI, Constantes.AMARELO_OURO, Constantes.PRETO_FOSCO, 40);
        botaoApagar.setForeground(Constantes.CINZA_CLARO);
        botaoApagar.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoApagar.setBounds(54, 520, 176, 50);
        painelBrancoFundo.add(botaoApagar);
        botaoApagar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                apagarProduto();
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
                voltarParaEstoque();
            }
        });
    }

    private void preencherCampos(Item item) {
        if (item == null) {
            JOptionPane.showMessageDialog(this, "Nenhum produto foi carregado para edição.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        nomeAreaText.setText(item.getNome());
        nomeAreaText.setForeground(Constantes.PRETO_FOSCO);
        codigoAreaText.setText(item.getCodigo());
        codigoAreaText.setForeground(Constantes.PRETO_FOSCO);
        marcaAreaText.setText(item.getMarca());
        marcaAreaText.setForeground(Constantes.PRETO_FOSCO);
        quantidadeEstoque = item.getQuantidade();
        quantidadeDisplayArea.setText(String.valueOf(quantidadeEstoque));
        valorVendaAreaText.setText(formatarMoeda(item.getValorVenda()));
        valorVendaAreaText.setForeground(Constantes.PRETO_FOSCO);
        valorCompraAreaText.setText(formatarMoeda(item.getValorCompra()));
        valorCompraAreaText.setForeground(Constantes.PRETO_FOSCO);
    }

    private void alterarQuantidade(int delta) {
        try {
            quantidadeEstoque = lerQuantidadeObrigatoria();
        } catch (IllegalArgumentException ex) {
            quantidadeEstoque = 0;
        }

        quantidadeEstoque += delta;
        if (quantidadeEstoque < 0) quantidadeEstoque = 0;
        quantidadeDisplayArea.setText(String.valueOf(quantidadeEstoque));
        quantidadeDisplayArea.setForeground(Constantes.PRETO_FOSCO);
    }

    private int lerQuantidadeObrigatoria() {
        String texto = quantidadeDisplayArea.getText() != null ? quantidadeDisplayArea.getText().trim() : "";

        if (texto.isEmpty()) {
            throw new IllegalArgumentException("Informe a quantidade em estoque.");
        }

        try {
            int quantidade = Integer.parseInt(texto);
            if (quantidade < 0) {
                throw new IllegalArgumentException("A quantidade em estoque não pode ser negativa.");
            }
            return quantidade;
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Informe uma quantidade válida. Use apenas números inteiros, por exemplo: 10.");
        }
    }

    private void salvarProduto() {
        if (itemEditando == null || codigoOriginal == null || codigoOriginal.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum produto foi carregado para salvar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String nome = lerCampoObrigatorio(nomeAreaText, "Digite o nome do produto", "nome");
            String codigoNovo = lerCampoObrigatorio(codigoAreaText, "Digite o código do produto", "código");
            String marca = lerCampoObrigatorio(marcaAreaText, "Digite a marca do produto", "marca");
            BigDecimal valorCompra = lerValorObrigatorio(valorCompraAreaText, "Digite o valor de compra", "valor de compra");
            BigDecimal valorVenda = lerValorObrigatorio(valorVendaAreaText, "Digite o valor de venda", "valor de venda");
            quantidadeEstoque = lerQuantidadeObrigatoria();

            itemController.editar(nome, codigoNovo, marca, quantidadeEstoque, valorCompra, valorVenda, codigoOriginal);
            JOptionPane.showMessageDialog(this, "Produto atualizado com sucesso.");
            voltarParaEstoque();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Dados inválidos", JOptionPane.WARNING_MESSAGE);
        } catch (ControllerException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar produto: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void apagarProduto() {
        if (itemEditando == null || codigoOriginal == null || codigoOriginal.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum produto foi carregado para apagar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja apagar permanentemente o produto " + itemEditando.getNome() + "?",
                "Confirmar exclusão",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirmacao != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            itemController.excluir(codigoOriginal);
            JOptionPane.showMessageDialog(this, "Produto apagado com sucesso.");
            voltarParaEstoque();
        } catch (ControllerException ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao apagar produto: " + ex.getMessage() + "\n\nSe ele já foi usado em uma ordem de serviço, o banco pode impedir a exclusão para preservar o histórico.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String lerCampoObrigatorio(EstilizacaoRedonda.CaixaTextoRedonda campo, String placeholder, String nomeCampo) {
        String valor = campo.getText() != null ? campo.getText().trim() : "";
        if (valor.isEmpty() || valor.equalsIgnoreCase(placeholder)) {
            throw new IllegalArgumentException("Informe o campo " + nomeCampo + ".");
        }
        return valor;
    }

    private BigDecimal lerValorObrigatorio(EstilizacaoRedonda.CaixaTextoRedonda campo, String placeholder, String nomeCampo) {
        String texto = lerCampoObrigatorio(campo, placeholder, nomeCampo)
                .replace("R$", "")
                .replace(" ", "");

        if (texto.contains(",")) {
            texto = texto.replace(".", "").replace(",", ".");
        }

        try {
            BigDecimal valor = new BigDecimal(texto).setScale(2, RoundingMode.HALF_UP);
            if (valor.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("O campo " + nomeCampo + " deve ser maior que zero.");
            }
            return valor;
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Informe um valor válido para " + nomeCampo + ". Exemplo: 120,50");
        }
    }

    private String formatarMoeda(BigDecimal valor) {
        if (valor == null) valor = BigDecimal.ZERO;
        return valor.setScale(2, RoundingMode.HALF_UP).toString();
    }

    private void voltarParaEstoque() {
        TelaEstoque telaEstoque = new TelaEstoque();
        telaEstoque.setVisible(true);
        dispose();
    }
}