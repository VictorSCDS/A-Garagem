package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaNovoEstoque extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel painelPretoFundo;
    private JPanel faixaTitulo;
    private EstilizacaoRedonda.PainelRedondo painelBrancoFundo;

    private EstilizacaoRedonda.CaixaTextoRedonda clienteAreaText;
    private EstilizacaoRedonda.CaixaTextoRedonda veiculoAreaText;
    private EstilizacaoRedonda.CaixaTextoRedonda placaAreaText;
    
    private EstilizacaoRedonda.CaixaTextoRedonda custoCompraAreaText;
    private EstilizacaoRedonda.CaixaTextoRedonda custoVendaAreaText;
    
    private int quantidadeEstoque = 10;
    private EstilizacaoRedonda.CaixaTextoRedonda quantidadeDisplayArea;

    public TelaNovoEstoque () {

        setBackground(Color.WHITE);
        setSize(1280, 720);
        setMaximizedBounds(new Rectangle(0, 0, 1280, 720));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        painelPretoFundo = new JPanel();
        painelPretoFundo.setBackground(Color.WHITE);
        painelPretoFundo.setLayout(null);
        setContentPane(painelPretoFundo);

        faixaTitulo = new JPanel();
        faixaTitulo.setBackground(Color.DARK_GRAY);
        faixaTitulo.setLayout(null);
        faixaTitulo.setBounds(0, 0, 1280, 80);
        painelPretoFundo.add(faixaTitulo);

        JLabel titulo = new JLabel("Adicionar Produto");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setForeground(Color.WHITE);
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
            logoGaragem.setForeground(Color.WHITE);
        }
        faixaTitulo.add(logoGaragem);
        
        painelBrancoFundo = new EstilizacaoRedonda.PainelRedondo(null, 0, 0, Color.WHITE, Color.WHITE);
        painelBrancoFundo.setBounds(0, 80, 1280, 640);
        painelBrancoFundo.setLayout(null);
        painelPretoFundo.add(painelBrancoFundo);

        JLabel lblCliente = new JLabel("Nome");
        lblCliente.setHorizontalAlignment(SwingConstants.CENTER);
        lblCliente.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        lblCliente.setBounds(160, 100, 200, 35);
        painelBrancoFundo.add(lblCliente);
        
        clienteAreaText = new EstilizacaoRedonda.CaixaTextoRedonda("Digite o nome do produto", Color.GRAY, Color.WHITE, Color.GRAY, 2, 25);
        clienteAreaText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        clienteAreaText.setBounds(100, 150, 320, 50);
        painelBrancoFundo.add(clienteAreaText);

        JLabel lblVeiculo = new JLabel("ID");
        lblVeiculo.setHorizontalAlignment(SwingConstants.CENTER);
        lblVeiculo.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        lblVeiculo.setBounds(160, 285, 200, 35);
        painelBrancoFundo.add(lblVeiculo);
        
        veiculoAreaText = new EstilizacaoRedonda.CaixaTextoRedonda("ID", Color.GRAY, Color.WHITE, Color.GRAY, 2, 25);
        veiculoAreaText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        veiculoAreaText.setBounds(100, 332, 320, 50);
        painelBrancoFundo.add(veiculoAreaText);

        JLabel lblPlaca = new JLabel("Marca");
        lblPlaca.setHorizontalAlignment(SwingConstants.CENTER);
        lblPlaca.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        lblPlaca.setBounds(537, 100, 200, 35);
        painelBrancoFundo.add(lblPlaca);
        
        placaAreaText = new EstilizacaoRedonda.CaixaTextoRedonda("Digite a marca do produto", Color.GRAY, Color.WHITE, Color.GRAY, 2, 25);
        placaAreaText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        placaAreaText.setBounds(485, 150, 320, 50);
        painelBrancoFundo.add(placaAreaText);

        JLabel lblQuantidade = new JLabel("Quantidade em estoque");
        lblQuantidade.setHorizontalAlignment(SwingConstants.CENTER);
        lblQuantidade.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        lblQuantidade.setBounds(485, 285, 320, 35);
        painelBrancoFundo.add(lblQuantidade);
        
        quantidadeDisplayArea = new EstilizacaoRedonda.CaixaTextoRedonda(String.valueOf(quantidadeEstoque), Color.LIGHT_GRAY, new Color(245, 245, 245), Color.BLACK, 2, 25);
        quantidadeDisplayArea.setFont(new Font("SansSerif", Font.BOLD, 22));
        quantidadeDisplayArea.setBounds(485, 332, 160, 50);
        quantidadeDisplayArea.setEditable(false);
        painelBrancoFundo.add(quantidadeDisplayArea);

        EstilizacaoRedonda.BotaoRedondo botaoRemoverQtd = new EstilizacaoRedonda.BotaoRedondo("-", new Color(220, 53, 69), new Color(237, 86, 100), new Color(176, 42, 55), 40);
        botaoRemoverQtd.setForeground(Color.WHITE);
        botaoRemoverQtd.setFont(new Font("SansSerif", Font.BOLD, 28));
        botaoRemoverQtd.setBounds(660, 337, 60, 40);
        botaoRemoverQtd.setMargin(new java.awt.Insets(0, 0, 0, 0));
        painelBrancoFundo.add(botaoRemoverQtd);

        EstilizacaoRedonda.BotaoRedondo botaoAdicionarQtd = new EstilizacaoRedonda.BotaoRedondo("+", Color.DARK_GRAY, Color.GRAY, Color.BLACK, 40);
        botaoAdicionarQtd.setForeground(Color.WHITE);
        botaoAdicionarQtd.setFont(new Font("SansSerif", Font.BOLD, 26));
        botaoAdicionarQtd.setBounds(735, 337, 60, 40);
        botaoAdicionarQtd.setMargin(new java.awt.Insets(0, 0, 0, 0));
        painelBrancoFundo.add(botaoAdicionarQtd);

        botaoRemoverQtd.addActionListener(e -> {
            if (quantidadeEstoque > 0) {
                quantidadeEstoque--;
                quantidadeDisplayArea.setText(String.valueOf(quantidadeEstoque));
            }
        });

        botaoAdicionarQtd.addActionListener(e -> {
            quantidadeEstoque++;
            quantidadeDisplayArea.setText(String.valueOf(quantidadeEstoque));
        });

        JLabel lblCustoVenda = new JLabel("Valor de venda");
        lblCustoVenda.setHorizontalAlignment(SwingConstants.CENTER);
        lblCustoVenda.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        lblCustoVenda.setBounds(934, 100, 200, 35);
        painelBrancoFundo.add(lblCustoVenda);
        
        custoVendaAreaText = new EstilizacaoRedonda.CaixaTextoRedonda("Digite o valor de venda", Color.GRAY, Color.WHITE, Color.GRAY, 2, 25);
        custoVendaAreaText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        custoVendaAreaText.setBounds(876, 150, 320, 50);
        painelBrancoFundo.add(custoVendaAreaText);
        
        JLabel lblCustoCompra = new JLabel("Valor de compra");
        lblCustoCompra.setHorizontalAlignment(SwingConstants.CENTER);
        lblCustoCompra.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        lblCustoCompra.setBounds(934, 285, 200, 35);
        painelBrancoFundo.add(lblCustoCompra);
        
        custoCompraAreaText = new EstilizacaoRedonda.CaixaTextoRedonda("Digite o valor de compra", Color.GRAY, Color.WHITE, Color.GRAY, 2, 25);
        custoCompraAreaText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        custoCompraAreaText.setBounds(876, 332, 320, 50);
        painelBrancoFundo.add(custoCompraAreaText);

        EstilizacaoRedonda.BotaoRedondo botaoAdicionar = new EstilizacaoRedonda.BotaoRedondo("ADICIONAR", Color.BLACK, Color.DARK_GRAY, Color.GRAY, 40);
        botaoAdicionar.setForeground(Color.WHITE);
        botaoAdicionar.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoAdicionar.setBounds(510, 520, 260, 50);
        painelBrancoFundo.add(botaoAdicionar);
        botaoAdicionar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                 TelaEstoque telaEstoque = new TelaEstoque();
                 telaEstoque.setVisible(true);
                dispose();
            }
        });
        
        EstilizacaoRedonda.BotaoRedondo botaoVoltar = new EstilizacaoRedonda.BotaoRedondo("", Color.BLACK, Color.DARK_GRAY, Color.GRAY, 40);
        botaoVoltar.setForeground(Color.WHITE);
        botaoVoltar.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoVoltar.setBounds(1100, 520, 90, 50);
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
                 TelaEstoque telaEstoque = new TelaEstoque();
                 telaEstoque.setVisible(true);
                dispose();
            }
        });
    }
}