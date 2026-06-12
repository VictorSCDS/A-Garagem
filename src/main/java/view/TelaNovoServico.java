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

public class TelaNovoServico extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel painelPretoFundo;
    private JPanel faixaTitulo;
    private EstilizacaoRedonda.PainelRedondo painelBrancoFundo;

    private EstilizacaoRedonda.CaixaTextoRedonda clienteAreaText;
    private EstilizacaoRedonda.CaixaTextoRedonda veiculoAreaText;
    private EstilizacaoRedonda.CaixaTextoRedonda placaAreaText;
    private EstilizacaoRedonda.CaixaTextoRedonda custoAtualAreaText;
    private EstilizacaoRedonda.CaixaTextoRedonda dataAlteracaoAreaText;
    private EstilizacaoRedonda.CaixaTextoRedonda problemaAreaText;
    private EstilizacaoRedonda.ComboBoxRedondo<String> statusComboBox;
    private java.util.ArrayList<String> listaServicos = new java.util.ArrayList<>();
    private java.util.ArrayList<String> listaPecas = new java.util.ArrayList<>();
    private EstilizacaoRedonda.ComboBoxRedondo<String> tipoServicoComboBox;
    private EstilizacaoRedonda.CaixaTextoRedonda servicosAdicionadosArea;
    private EstilizacaoRedonda.ComboBoxRedondo<String> pecasComboBox;
    private EstilizacaoRedonda.CaixaTextoRedonda pecasAdicionadasArea;

    public TelaNovoServico() {

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

        JLabel titulo = new JLabel("Novo Serviço");
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

        JLabel lblCliente = new JLabel("Cliente");
        lblCliente.setHorizontalAlignment(SwingConstants.CENTER);
        lblCliente.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        lblCliente.setBounds(160, 100, 200, 35);
        painelBrancoFundo.add(lblCliente);
        
        clienteAreaText = new EstilizacaoRedonda.CaixaTextoRedonda("Digite o nome do cliente", Color.GRAY, Color.WHITE, Color.GRAY, 2, 25);
        clienteAreaText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        clienteAreaText.setBounds(100, 150, 320, 50);
        painelBrancoFundo.add(clienteAreaText);

        JLabel lblVeiculo = new JLabel("Veículo");
        lblVeiculo.setHorizontalAlignment(SwingConstants.CENTER);
        lblVeiculo.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        lblVeiculo.setBounds(540, 100, 200, 35);
        painelBrancoFundo.add(lblVeiculo);
        
        veiculoAreaText = new EstilizacaoRedonda.CaixaTextoRedonda("Digite o nome do veículo", Color.GRAY, Color.WHITE, Color.GRAY, 2, 25);
        veiculoAreaText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        veiculoAreaText.setBounds(480, 150, 320, 50);
        painelBrancoFundo.add(veiculoAreaText);

        JLabel lblPlaca = new JLabel("Placa");
        lblPlaca.setHorizontalAlignment(SwingConstants.CENTER);
        lblPlaca.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        lblPlaca.setBounds(920, 100, 200, 35);
        painelBrancoFundo.add(lblPlaca);
        
        placaAreaText = new EstilizacaoRedonda.CaixaTextoRedonda("Digite o Tipo de veículo", Color.GRAY, Color.WHITE, Color.GRAY, 2, 25);
        placaAreaText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        placaAreaText.setBounds(860, 150, 320, 50);
        painelBrancoFundo.add(placaAreaText);

        JLabel lblTipoServico = new JLabel("Tipo de serviço");
        lblTipoServico.setHorizontalAlignment(SwingConstants.CENTER);
        lblTipoServico.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        lblTipoServico.setBounds(160, 220, 200, 35);
        painelBrancoFundo.add(lblTipoServico);
        
        String[] opcoesServicos = {"Selecione...", "Troca de Óleo", "Alinhamento", "Balanceamento", "Revisão Geral", "Motor"};
        tipoServicoComboBox = new EstilizacaoRedonda.ComboBoxRedondo<>(opcoesServicos, Color.WHITE, Color.GRAY, 2, 25);
        tipoServicoComboBox.setFont(new Font("SansSerif", Font.PLAIN, 18));
        tipoServicoComboBox.setBounds(100, 260, 200, 40); 
        painelBrancoFundo.add(tipoServicoComboBox);

        EstilizacaoRedonda.BotaoRedondo botaoAddServico = new EstilizacaoRedonda.BotaoRedondo("+", Color.DARK_GRAY, Color.GRAY, Color.BLACK, 40);
        botaoAddServico.setForeground(Color.WHITE);
        botaoAddServico.setFont(new Font("SansSerif", Font.BOLD, 24));
        botaoAddServico.setBounds(310, 260, 50, 40);
        botaoAddServico.setMargin(new java.awt.Insets(0, 0, 0, 0)); 
        painelBrancoFundo.add(botaoAddServico);

        EstilizacaoRedonda.BotaoRedondo botaoRemoverServico = new EstilizacaoRedonda.BotaoRedondo("-", new Color(220, 53, 69), new Color(237, 86, 100), new Color(176, 42, 55), 40);
        botaoRemoverServico.setForeground(Color.WHITE);
        botaoRemoverServico.setFont(new Font("SansSerif", Font.BOLD, 24));
        botaoRemoverServico.setBounds(370, 260, 50, 40);
        botaoRemoverServico.setMargin(new java.awt.Insets(0, 0, 0, 0));
        painelBrancoFundo.add(botaoRemoverServico);

        servicosAdicionadosArea = new EstilizacaoRedonda.CaixaTextoRedonda("", Color.LIGHT_GRAY, new Color(245, 245, 245), Color.BLACK, 2, 25);
        servicosAdicionadosArea.setFont(new Font("SansSerif", Font.ITALIC, 14));
        servicosAdicionadosArea.setBounds(100, 310, 320, 40); 
        servicosAdicionadosArea.setEditable(false); 
        painelBrancoFundo.add(servicosAdicionadosArea);
        botaoAddServico.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selecionado = (String) tipoServicoComboBox.getSelectedItem();
                if (selecionado != null && !selecionado.equals("Selecione...") && !listaServicos.contains(selecionado)) {
                    listaServicos.add(selecionado);
                    servicosAdicionadosArea.setText(String.join(", ", listaServicos));
                    servicosAdicionadosArea.setForeground(Color.BLACK);
                }
            }
        });
        botaoRemoverServico.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!listaServicos.isEmpty()) {
                    listaServicos.remove(listaServicos.size() - 1);
                    if (listaServicos.isEmpty()) {
                        servicosAdicionadosArea.setText("");
                    } else {
                        servicosAdicionadosArea.setText(String.join(", ", listaServicos));
                    }
                }
            }
        });

        JLabel lblCustoAtual = new JLabel("Custo atual");
        lblCustoAtual.setHorizontalAlignment(SwingConstants.CENTER);
        lblCustoAtual.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        lblCustoAtual.setBounds(540, 230, 200, 35);
        painelBrancoFundo.add(lblCustoAtual);
        
        custoAtualAreaText = new EstilizacaoRedonda.CaixaTextoRedonda("Digite o custo atual em reais", Color.GRAY, Color.WHITE, Color.GRAY, 2, 25);
        custoAtualAreaText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        custoAtualAreaText.setBounds(480, 280, 320, 50);
        painelBrancoFundo.add(custoAtualAreaText);

        JLabel lblStatus = new JLabel("Status");
        lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
        lblStatus.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        lblStatus.setBounds(920, 230, 200, 35);
        painelBrancoFundo.add(lblStatus);
        
        String[] opcoesStatus = {"Em análise", "Em andamento", "Já finalizado"};
        EstilizacaoRedonda.ComboBoxRedondo<String> statusComboBox = new EstilizacaoRedonda.ComboBoxRedondo<>(opcoesStatus, Color.WHITE, Color.GRAY, 2, 25);
        statusComboBox.setFont(new Font("SansSerif", Font.PLAIN, 18));
        statusComboBox.setBounds(860, 280, 320, 50);
        painelBrancoFundo.add(statusComboBox);

        JLabel lblPecas = new JLabel("Peças");
        lblPecas.setHorizontalAlignment(SwingConstants.CENTER);
        lblPecas.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        lblPecas.setBounds(160, 360, 200, 35);
        painelBrancoFundo.add(lblPecas);
        
        String[] opcoesPecas = {"Selecione...", "Filtro de Óleo", "Pastilha de Freio", "Correia Dentada", "Bateria", "Vela de Ignição"};
        pecasComboBox = new EstilizacaoRedonda.ComboBoxRedondo<>(opcoesPecas, Color.WHITE, Color.GRAY, 2, 25);
        pecasComboBox.setFont(new Font("SansSerif", Font.PLAIN, 18));
        pecasComboBox.setBounds(100, 400, 200, 40);
        painelBrancoFundo.add(pecasComboBox);

        EstilizacaoRedonda.BotaoRedondo botaoAddPeca = new EstilizacaoRedonda.BotaoRedondo("+", Color.DARK_GRAY, Color.GRAY, Color.BLACK, 40);
        botaoAddPeca.setForeground(Color.WHITE);
        botaoAddPeca.setFont(new Font("SansSerif", Font.BOLD, 24));
        botaoAddPeca.setBounds(310, 400, 50, 40);
        botaoAddPeca.setMargin(new java.awt.Insets(0, 0, 0, 0));
        painelBrancoFundo.add(botaoAddPeca);

        EstilizacaoRedonda.BotaoRedondo botaoRemoverPeca = new EstilizacaoRedonda.BotaoRedondo("-", new Color(220, 53, 69), new Color(237, 86, 100), new Color(176, 42, 55), 40);
        botaoRemoverPeca.setForeground(Color.WHITE);
        botaoRemoverPeca.setFont(new Font("SansSerif", Font.BOLD, 24));
        botaoRemoverPeca.setBounds(370, 400, 50, 40);
        botaoRemoverPeca.setMargin(new java.awt.Insets(0, 0, 0, 0));
        painelBrancoFundo.add(botaoRemoverPeca);

        pecasAdicionadasArea = new EstilizacaoRedonda.CaixaTextoRedonda("", Color.LIGHT_GRAY, new Color(245, 245, 245), Color.BLACK, 2, 25);
        pecasAdicionadasArea.setFont(new Font("SansSerif", Font.ITALIC, 14));
        pecasAdicionadasArea.setBounds(100, 450, 320, 50); 
        pecasAdicionadasArea.setEditable(false);
        painelBrancoFundo.add(pecasAdicionadasArea);
        botaoAddPeca.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selecionado = (String) pecasComboBox.getSelectedItem();
                if (selecionado != null && !selecionado.equals("Selecione...") && !listaPecas.contains(selecionado)) {
                    listaPecas.add(selecionado);
                    pecasAdicionadasArea.setText(String.join(", ", listaPecas));
                    pecasAdicionadasArea.setForeground(Color.BLACK);
                }
            }
        });
        botaoRemoverPeca.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!listaPecas.isEmpty()) {
                    listaPecas.remove(listaPecas.size() - 1);
                    if (listaPecas.isEmpty()) {
                        pecasAdicionadasArea.setText("");
                    } else {
                        pecasAdicionadasArea.setText(String.join(", ", listaPecas));
                    }
                }
            }
        });

        JLabel lblDataAlt = new JLabel("<html><center>Data da ultima<br>alteração</center></html>");
        lblDataAlt.setHorizontalAlignment(SwingConstants.CENTER);
        lblDataAlt.setFont(new Font("Liberation Serif", Font.BOLD, 24));
        lblDataAlt.setBounds(490, 350, 300, 60);
        painelBrancoFundo.add(lblDataAlt);
        
        dataAlteracaoAreaText = new EstilizacaoRedonda.CaixaTextoRedonda("DD/MM/AAAA", Color.GRAY, Color.WHITE, Color.GRAY, 2, 25);
        dataAlteracaoAreaText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        dataAlteracaoAreaText.setBounds(480, 435, 320, 50);
        painelBrancoFundo.add(dataAlteracaoAreaText);

        JLabel lblProblema = new JLabel("Problema");
        lblProblema.setHorizontalAlignment(SwingConstants.CENTER);
        lblProblema.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        lblProblema.setBounds(920, 360, 200, 35);
        painelBrancoFundo.add(lblProblema);
        
        problemaAreaText = new EstilizacaoRedonda.CaixaTextoRedonda("Descreva o problema", Color.GRAY, Color.WHITE, Color.GRAY, 2, 25);
        problemaAreaText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        problemaAreaText.setBounds(860, 410, 320, 100);
        painelBrancoFundo.add(problemaAreaText);

        EstilizacaoRedonda.BotaoRedondo botaoAdicionar = new EstilizacaoRedonda.BotaoRedondo("ADICIONAR", Color.BLACK, Color.DARK_GRAY, Color.GRAY, 40);
        botaoAdicionar.setForeground(Color.WHITE);
        botaoAdicionar.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoAdicionar.setBounds(510, 520, 260, 50);
        painelBrancoFundo.add(botaoAdicionar);
        botaoAdicionar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                TelaOrdemServico telaOrdemServico = new TelaOrdemServico();
                telaOrdemServico.setVisible(true);
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
                TelaOrdemServico telaOrdemServico = new TelaOrdemServico();
                telaOrdemServico.setVisible(true);
                dispose();
            }
        });
    }
}