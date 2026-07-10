package view;

import utils.Constantes;
import utils.PermissaoAcesso;

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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import javax.swing.DefaultComboBoxModel;
import entities.OrdemServico;
import entities.Funcionario;
import entities.Item;
import entities.TipoServico;
import entities.enums.Estado;
import controllers.FuncionarioController;
import controllers.ItemController;
import controllers.OrdemServicoController;
import controllers.TipoServicoController;
import exceptions.ControllerException;
import utils.Sessao;

public class TelaEditarServico extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel painelPretoFundo;
    private JPanel faixaTitulo;
    private EstilizacaoRedonda.PainelRedondo painelBrancoFundo;
    private OrdemServico ordemServicoEditando;
    private OrdemServicoController servicoController;

    private EstilizacaoRedonda.ComboBoxRedondo<FuncionarioOpcao> funcionarioResponsavelComboBox;
    private EstilizacaoRedonda.CaixaTextoRedonda veiculoAreaText;
    private EstilizacaoRedonda.CaixaTextoRedonda placaAreaText;
    private JLabel custoAtualValorLabel;
    private EstilizacaoRedonda.CaixaTextoRedonda problemaAreaText;
    private EstilizacaoRedonda.CaixaTextoRedonda notaTecnicaAreaText;
    private EstilizacaoRedonda.ComboBoxRedondo<EstadoOpcao> statusComboBox;
    private java.util.ArrayList<TipoServico> listaServicos = new java.util.ArrayList<>();
    private java.util.ArrayList<Item> listaPecas = new java.util.ArrayList<>();
    private EstilizacaoRedonda.ComboBoxRedondo<TipoServicoOpcao> tipoServicoComboBox;
    private EstilizacaoRedonda.CaixaTextoRedonda servicosAdicionadosArea;
    private EstilizacaoRedonda.ComboBoxRedondo<ItemOpcao> pecasComboBox;
    private EstilizacaoRedonda.CaixaTextoRedonda pecasAdicionadasArea;
    private FuncionarioController funcionarioController = new FuncionarioController();
    private TipoServicoController tipoServicoController = new TipoServicoController();
    private ItemController itemController = new ItemController();

    public TelaEditarServico () {
        if (!PermissaoAcesso.autorizar(this, PermissaoAcesso.podeAcessarOrdemServico(), "Ordem de Serviço")) {
            javax.swing.SwingUtilities.invokeLater(() -> {
                new TelaHome().setVisible(true);
                dispose();
            });
            return;
        }


        this.servicoController = new OrdemServicoController();

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

        JLabel titulo = new JLabel("Editar Serviço");
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

        JLabel lblCliente = new JLabel("Func. responsável");
        lblCliente.setHorizontalAlignment(SwingConstants.CENTER);
        lblCliente.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        lblCliente.setBounds(160, 100, 200, 35);
        painelBrancoFundo.add(lblCliente);
        
        funcionarioResponsavelComboBox = new EstilizacaoRedonda.ComboBoxRedondo<>(new FuncionarioOpcao[]{FuncionarioOpcao.placeholder("Selecione um funcionário")}, Constantes.CINZA_CLARO, Constantes.PRETO_FOSCO, 2, 25);
        funcionarioResponsavelComboBox.setFont(new Font("SansSerif", Font.PLAIN, 18));
        funcionarioResponsavelComboBox.setBounds(100, 150, 320, 50);
        painelBrancoFundo.add(funcionarioResponsavelComboBox);
        carregarFuncionariosResponsaveis();

        JLabel lblVeiculo = new JLabel("Placa do veículo");
        lblVeiculo.setHorizontalAlignment(SwingConstants.CENTER);
        lblVeiculo.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        lblVeiculo.setBounds(540, 100, 200, 35);
        painelBrancoFundo.add(lblVeiculo);
        
        veiculoAreaText = new EstilizacaoRedonda.CaixaTextoRedonda("Placa do veículo", Constantes.PRETO_FOSCO, Constantes.CINZA_CLARO, Constantes.PRETO_FOSCO, 2, 25);
        veiculoAreaText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        veiculoAreaText.setBounds(480, 150, 320, 50);
        veiculoAreaText.setEditable(false);
        painelBrancoFundo.add(veiculoAreaText);

        JLabel lblPlaca = new JLabel("OS ID");
        lblPlaca.setHorizontalAlignment(SwingConstants.CENTER);
        lblPlaca.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        lblPlaca.setBounds(920, 100, 200, 35);
        painelBrancoFundo.add(lblPlaca);
        
        placaAreaText = new EstilizacaoRedonda.CaixaTextoRedonda("ID da ordem de serviço", Constantes.PRETO_FOSCO, Constantes.CINZA_CLARO, Constantes.PRETO_FOSCO, 2, 25);
        placaAreaText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        placaAreaText.setBounds(860, 150, 320, 50);
        placaAreaText.setEditable(false);
        painelBrancoFundo.add(placaAreaText);

        JLabel lblTipoServico = new JLabel("Tipo de serviço");
        lblTipoServico.setHorizontalAlignment(SwingConstants.CENTER);
        lblTipoServico.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        lblTipoServico.setBounds(160, 220, 200, 35);
        painelBrancoFundo.add(lblTipoServico);
        
        tipoServicoComboBox = new EstilizacaoRedonda.ComboBoxRedondo<>(new TipoServicoOpcao[]{TipoServicoOpcao.placeholder("Carregando serviços...")}, Constantes.CINZA_CLARO, Constantes.PRETO_FOSCO, 2, 25);
        tipoServicoComboBox.setFont(new Font("SansSerif", Font.PLAIN, 18));
        tipoServicoComboBox.setBounds(100, 260, 200, 40); 
        painelBrancoFundo.add(tipoServicoComboBox);
        carregarTiposServicoDoBanco();

        EstilizacaoRedonda.BotaoRedondo botaoAddServico = new EstilizacaoRedonda.BotaoRedondo("+", Constantes.VERMELHO_FERRARI, Constantes.AMARELO_OURO, Constantes.PRETO_FOSCO, 40);
        botaoAddServico.setForeground(Constantes.CINZA_CLARO);
        botaoAddServico.setFont(new Font("SansSerif", Font.BOLD, 24));
        botaoAddServico.setBounds(310, 260, 50, 40);
        botaoAddServico.setMargin(new java.awt.Insets(0, 0, 0, 0)); 
        painelBrancoFundo.add(botaoAddServico);

        EstilizacaoRedonda.BotaoRedondo botaoRemoverServico = new EstilizacaoRedonda.BotaoRedondo("-", Constantes.VERMELHO_FERRARI, Constantes.AMARELO_OURO, Constantes.PRETO_FOSCO, 40);
        botaoRemoverServico.setForeground(Constantes.CINZA_CLARO);
        botaoRemoverServico.setFont(new Font("SansSerif", Font.BOLD, 24));
        botaoRemoverServico.setBounds(370, 260, 50, 40);
        botaoRemoverServico.setMargin(new java.awt.Insets(0, 0, 0, 0));
        painelBrancoFundo.add(botaoRemoverServico);

        servicosAdicionadosArea = new EstilizacaoRedonda.CaixaTextoRedonda("", Constantes.CINZA_CLARO, Constantes.CINZA_CLARO, Constantes.PRETO_FOSCO, 2, 25);
        servicosAdicionadosArea.setFont(new Font("SansSerif", Font.ITALIC, 14));
        servicosAdicionadosArea.setBounds(100, 310, 320, 40); 
        servicosAdicionadosArea.setEditable(false); 
        painelBrancoFundo.add(servicosAdicionadosArea);
        botaoAddServico.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TipoServico tipoServico = getTipoServicoSelecionado();
                if (tipoServico != null && !contemTipoServico(tipoServico)) {
                    listaServicos.add(tipoServico);
                    atualizarTextoServicosSelecionados();
                    atualizarCustoEstimado();
                }
            }
        });
        botaoRemoverServico.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!listaServicos.isEmpty()) {
                    listaServicos.remove(listaServicos.size() - 1);
                    atualizarTextoServicosSelecionados();
                    atualizarCustoEstimado();
                }
            }
        });

        JLabel lblCustoAtual = new JLabel("Custo atual");
        lblCustoAtual.setHorizontalAlignment(SwingConstants.CENTER);
        lblCustoAtual.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        lblCustoAtual.setBounds(540, 230, 200, 35);
        painelBrancoFundo.add(lblCustoAtual);
        
        custoAtualValorLabel = criarLabelCustoAtual("R$ 0.00");
        custoAtualValorLabel.setBounds(480, 280, 320, 50);
        painelBrancoFundo.add(custoAtualValorLabel);

        JLabel lblStatus = new JLabel("Status");
        lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
        lblStatus.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        lblStatus.setBounds(920, 230, 200, 35);
        painelBrancoFundo.add(lblStatus);
        
        statusComboBox = new EstilizacaoRedonda.ComboBoxRedondo<>(new EstadoOpcao[]{new EstadoOpcao(Estado.NAO_PAGO)}, Constantes.CINZA_CLARO, Constantes.PRETO_FOSCO, 2, 25);
        statusComboBox.setFont(new Font("SansSerif", Font.PLAIN, 18));
        statusComboBox.setBounds(860, 280, 320, 50);
        painelBrancoFundo.add(statusComboBox);
        carregarStatusServicoDoBanco();

        JLabel lblPecas = new JLabel("Peças");
        lblPecas.setHorizontalAlignment(SwingConstants.CENTER);
        lblPecas.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        lblPecas.setBounds(160, 360, 200, 35);
        painelBrancoFundo.add(lblPecas);
        
        pecasComboBox = new EstilizacaoRedonda.ComboBoxRedondo<>(new ItemOpcao[]{ItemOpcao.placeholder("Carregando peças...")}, Constantes.CINZA_CLARO, Constantes.PRETO_FOSCO, 2, 25);
        pecasComboBox.setFont(new Font("SansSerif", Font.PLAIN, 18));
        pecasComboBox.setBounds(100, 400, 200, 40);
        painelBrancoFundo.add(pecasComboBox);
        carregarPecasDoBanco();

        EstilizacaoRedonda.BotaoRedondo botaoAddPeca = new EstilizacaoRedonda.BotaoRedondo("+", Constantes.VERMELHO_FERRARI, Constantes.AMARELO_OURO, Constantes.PRETO_FOSCO, 40);
        botaoAddPeca.setForeground(Constantes.CINZA_CLARO);
        botaoAddPeca.setFont(new Font("SansSerif", Font.BOLD, 24));
        botaoAddPeca.setBounds(310, 400, 50, 40);
        botaoAddPeca.setMargin(new java.awt.Insets(0, 0, 0, 0));
        painelBrancoFundo.add(botaoAddPeca);

        EstilizacaoRedonda.BotaoRedondo botaoRemoverPeca = new EstilizacaoRedonda.BotaoRedondo("-", Constantes.VERMELHO_FERRARI, Constantes.AMARELO_OURO, Constantes.PRETO_FOSCO, 40);
        botaoRemoverPeca.setForeground(Constantes.CINZA_CLARO);
        botaoRemoverPeca.setFont(new Font("SansSerif", Font.BOLD, 24));
        botaoRemoverPeca.setBounds(370, 400, 50, 40);
        botaoRemoverPeca.setMargin(new java.awt.Insets(0, 0, 0, 0));
        painelBrancoFundo.add(botaoRemoverPeca);

        pecasAdicionadasArea = new EstilizacaoRedonda.CaixaTextoRedonda("", Constantes.CINZA_CLARO, Constantes.CINZA_CLARO, Constantes.PRETO_FOSCO, 2, 25);
        pecasAdicionadasArea.setFont(new Font("SansSerif", Font.ITALIC, 14));
        pecasAdicionadasArea.setBounds(100, 450, 320, 50); 
        pecasAdicionadasArea.setEditable(false);
        painelBrancoFundo.add(pecasAdicionadasArea);
        botaoAddPeca.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Item item = getPecaSelecionada();
                if (item != null && !contemPeca(item)) {
                    listaPecas.add(item);
                    atualizarTextoPecasSelecionadas();
                    atualizarCustoEstimado();
                }
            }
        });
        botaoRemoverPeca.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!listaPecas.isEmpty()) {
                    listaPecas.remove(listaPecas.size() - 1);
                    atualizarTextoPecasSelecionadas();
                    atualizarCustoEstimado();
                }
            }
        });

        JLabel lblNotaTecnica = new JLabel("Nota técnica");
        lblNotaTecnica.setHorizontalAlignment(SwingConstants.CENTER);
        lblNotaTecnica.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        lblNotaTecnica.setBounds(540, 350, 200, 35);
        painelBrancoFundo.add(lblNotaTecnica);

        boolean podeEditarNotaTecnica = Sessao.usuarioPodeEditarNotaTecnica();
        notaTecnicaAreaText = new EstilizacaoRedonda.CaixaTextoRedonda("Digite a nota técnica", Constantes.PRETO_FOSCO, Constantes.CINZA_CLARO, Constantes.PRETO_FOSCO, 2, 25);
        notaTecnicaAreaText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        notaTecnicaAreaText.setBounds(480, 400, 320, 110);
        notaTecnicaAreaText.setEditable(podeEditarNotaTecnica);
        if (!podeEditarNotaTecnica) {
            notaTecnicaAreaText.setText("Apenas gerente ou mecânico pode editar");
            notaTecnicaAreaText.setForeground(Constantes.PRETO_FOSCO);
        }
        painelBrancoFundo.add(notaTecnicaAreaText);

        JLabel lblProblema = new JLabel("Problema");
        lblProblema.setHorizontalAlignment(SwingConstants.CENTER);
        lblProblema.setFont(new Font("Liberation Serif", Font.BOLD, 28));
        lblProblema.setBounds(920, 360, 200, 35);
        painelBrancoFundo.add(lblProblema);
        
        problemaAreaText = new EstilizacaoRedonda.CaixaTextoRedonda("Descreva o problema", Constantes.PRETO_FOSCO, Constantes.CINZA_CLARO, Constantes.PRETO_FOSCO, 2, 25);
        problemaAreaText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        problemaAreaText.setBounds(860, 410, 320, 100);
        painelBrancoFundo.add(problemaAreaText);

        EstilizacaoRedonda.BotaoRedondo botaoAdicionar = new EstilizacaoRedonda.BotaoRedondo("SALVAR", Constantes.VERMELHO_FERRARI, Constantes.AMARELO_OURO, Constantes.PRETO_FOSCO, 40);
        botaoAdicionar.setForeground(Constantes.CINZA_CLARO);
        botaoAdicionar.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoAdicionar.setBounds(510, 520, 260, 50);
        painelBrancoFundo.add(botaoAdicionar);
        botaoAdicionar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                salvarAlteracoesOrdemServico();
            }
        });
        
        EstilizacaoRedonda.BotaoRedondo botaoApagar = new EstilizacaoRedonda.BotaoRedondo("APAGAR", Constantes.VERMELHO_FERRARI,Constantes.AMARELO_OURO,Constantes.PRETO_FOSCO,40);
        botaoApagar.setForeground(Constantes.CINZA_CLARO);
        botaoApagar.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoApagar.setBounds(54, 520, 176, 50);
        painelBrancoFundo.add(botaoApagar);
        botaoApagar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                apagarOrdemServicoAtual();
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

    public TelaEditarServico (OrdemServico ordemServico) {
        this();
        this.ordemServicoEditando = ordemServico;
        preencherCampos(ordemServico);
    }

    private void preencherCampos(OrdemServico ordemServico) {
        if (ordemServico == null) {
            return;
        }

        selecionarFuncionarioPorId(ordemServico.getIdFuncionarioResponsavel());
        veiculoAreaText.setText(buscarPlacaVeiculo(ordemServico.getIdVeiculo()));
        placaAreaText.setText(String.valueOf(ordemServico.getId()));
        problemaAreaText.setText(ordemServico.getProblema());
        selecionarStatus(ordemServico.getEstado());

        if (Sessao.usuarioPodeEditarNotaTecnica()) {
            notaTecnicaAreaText.setText(ordemServico.getDescricao() != null ? ordemServico.getDescricao() : "");
            notaTecnicaAreaText.setForeground(Constantes.PRETO_FOSCO);
        }

        carregarServicosEPecasDaOrdem(ordemServico.getId());
        atualizarCustoEstimado();
    }

    private JLabel criarLabelCustoAtual(String texto) {
        JLabel label = new JLabel(texto);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 18));
        label.setOpaque(true);
        label.setBackground(Constantes.CINZA_CLARO);
        label.setForeground(Constantes.PRETO_FOSCO);
        label.setBorder(javax.swing.BorderFactory.createLineBorder(Constantes.PRETO_FOSCO, 2));
        return label;
    }

    private void atualizarTextoCusto(BigDecimal valor) {
        if (valor == null) {
            valor = BigDecimal.ZERO;
        }
        custoAtualValorLabel.setText("R$ " + valor.setScale(2, RoundingMode.HALF_UP));
        custoAtualValorLabel.setForeground(Constantes.PRETO_FOSCO);
    }

    private void carregarTiposServicoDoBanco() {
        try {
            java.util.List<TipoServico> tiposServico = tipoServicoController.listarTodos();

            if (tiposServico.isEmpty()) {
                tipoServicoComboBox.setModel(new DefaultComboBoxModel<>(new TipoServicoOpcao[]{TipoServicoOpcao.placeholder("Nenhum serviço cadastrado")}));
                tipoServicoComboBox.setEnabled(false);
                return;
            }

            TipoServicoOpcao[] opcoes = new TipoServicoOpcao[tiposServico.size() + 1];
            opcoes[0] = TipoServicoOpcao.placeholder("Selecione...");
            for (int i = 0; i < tiposServico.size(); i++) {
                opcoes[i + 1] = new TipoServicoOpcao(tiposServico.get(i));
            }

            tipoServicoComboBox.setModel(new DefaultComboBoxModel<>(opcoes));
            tipoServicoComboBox.setEnabled(true);
            tipoServicoComboBox.setSelectedIndex(0);

        } catch (ControllerException ex) {
            tipoServicoComboBox.setModel(new DefaultComboBoxModel<>(new TipoServicoOpcao[]{TipoServicoOpcao.placeholder("Erro ao carregar serviços")}));
            tipoServicoComboBox.setEnabled(false);
            JOptionPane.showMessageDialog(this, "Erro ao carregar serviços do banco: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarPecasDoBanco() {
        try {
            java.util.List<Item> pecas = itemController.listarTodos();

            if (pecas.isEmpty()) {
                pecasComboBox.setModel(new DefaultComboBoxModel<>(new ItemOpcao[]{ItemOpcao.placeholder("Nenhuma peça cadastrada")}));
                pecasComboBox.setEnabled(false);
                return;
            }

            ItemOpcao[] opcoes = new ItemOpcao[pecas.size() + 1];
            opcoes[0] = ItemOpcao.placeholder("Selecione...");
            for (int i = 0; i < pecas.size(); i++) {
                opcoes[i + 1] = new ItemOpcao(pecas.get(i));
            }

            pecasComboBox.setModel(new DefaultComboBoxModel<>(opcoes));
            pecasComboBox.setEnabled(true);
            pecasComboBox.setSelectedIndex(0);

        } catch (ControllerException ex) {
            pecasComboBox.setModel(new DefaultComboBoxModel<>(new ItemOpcao[]{ItemOpcao.placeholder("Erro ao carregar peças")}));
            pecasComboBox.setEnabled(false);
            JOptionPane.showMessageDialog(this, "Erro ao carregar peças do banco: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarStatusServicoDoBanco() {
        try {
            java.util.List<Estado> estados = servicoController.listarEstadosDisponiveis();
            carregarStatusNoCombo(estados);
        } catch (ControllerException ex) {
            carregarStatusNoCombo(java.util.Arrays.asList(Estado.values()));
            JOptionPane.showMessageDialog(this, "Erro ao carregar status do banco. Usando status padrão do sistema: " + ex.getMessage(), "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void carregarStatusNoCombo(java.util.List<Estado> estados) {
        if (estados == null || estados.isEmpty()) {
            estados = java.util.Arrays.asList(Estado.values());
        }

        EstadoOpcao[] opcoes = new EstadoOpcao[estados.size()];
        for (int i = 0; i < estados.size(); i++) {
            opcoes[i] = new EstadoOpcao(estados.get(i));
        }
        statusComboBox.setModel(new DefaultComboBoxModel<>(opcoes));
    }

    private void selecionarStatus(Estado estado) {
        if (estado == null || statusComboBox.getItemCount() == 0) {
            return;
        }

        for (int i = 0; i < statusComboBox.getItemCount(); i++) {
            EstadoOpcao opcao = statusComboBox.getItemAt(i);
            if (opcao != null && opcao.getEstado() == estado) {
                statusComboBox.setSelectedIndex(i);
                return;
            }
        }
    }

    private TipoServico getTipoServicoSelecionado() {
        Object selecionado = tipoServicoComboBox.getSelectedItem();
        if (selecionado instanceof TipoServicoOpcao) {
            return ((TipoServicoOpcao) selecionado).getTipoServico();
        }
        return null;
    }

    private Item getPecaSelecionada() {
        Object selecionado = pecasComboBox.getSelectedItem();
        if (selecionado instanceof ItemOpcao) {
            return ((ItemOpcao) selecionado).getItem();
        }
        return null;
    }

    private boolean contemTipoServico(TipoServico tipoServico) {
        if (tipoServico == null) return false;
        for (TipoServico selecionado : listaServicos) {
            if (selecionado.getId() == tipoServico.getId()) return true;
        }
        return false;
    }

    private boolean contemPeca(Item item) {
        if (item == null) return false;
        for (Item selecionado : listaPecas) {
            if (selecionado.getId() == item.getId()) return true;
        }
        return false;
    }

    private void atualizarTextoServicosSelecionados() {
        StringBuilder texto = new StringBuilder();
        for (TipoServico tipoServico : listaServicos) {
            if (texto.length() > 0) texto.append(", ");
            texto.append(tipoServico.getDescricao());
        }
        servicosAdicionadosArea.setText(texto.toString());
        servicosAdicionadosArea.setForeground(Constantes.PRETO_FOSCO);
    }

    private void atualizarTextoPecasSelecionadas() {
        StringBuilder texto = new StringBuilder();
        for (Item item : listaPecas) {
            if (texto.length() > 0) texto.append(", ");
            texto.append(item.getNome()).append(" (cód. ").append(item.getCodigo()).append(")");
        }
        pecasAdicionadasArea.setText(texto.toString());
        pecasAdicionadasArea.setForeground(Constantes.PRETO_FOSCO);
    }

    private void atualizarCustoEstimado() {
        BigDecimal total = BigDecimal.ZERO;

        for (TipoServico tipoServico : listaServicos) {
            total = total.add(tipoServico.getValorServico() != null ? tipoServico.getValorServico() : BigDecimal.ZERO);
        }

        for (Item item : listaPecas) {
            total = total.add(item.getValorCompra() != null ? item.getValorCompra() : BigDecimal.ZERO);
        }

        atualizarTextoCusto(total);
    }

    private void carregarServicosEPecasDaOrdem(int idOrdemServico) {
        listaServicos.clear();
        listaPecas.clear();

        try {
            for (TipoServico tipoServico : tipoServicoController.buscarPorOrdemServico(idOrdemServico)) {
                if (tipoServico != null && !contemTipoServico(tipoServico)) {
                    listaServicos.add(tipoServico);
                }
            }

            for (Item item : itemController.buscarPorOrdemServico(idOrdemServico)) {
                if (item != null && !contemPeca(item)) {
                    listaPecas.add(item);
                }
            }

            atualizarTextoServicosSelecionados();
            atualizarTextoPecasSelecionadas();

        } catch (ControllerException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar peças/serviços da OS: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarFuncionariosResponsaveis() {
        try {
            java.util.List<Funcionario> funcionarios = funcionarioController.listarTodos();
            FuncionarioOpcao[] opcoes = new FuncionarioOpcao[funcionarios.size() + 1];
            opcoes[0] = FuncionarioOpcao.placeholder("Selecione um funcionário");

            for (int i = 0; i < funcionarios.size(); i++) {
                opcoes[i + 1] = new FuncionarioOpcao(funcionarios.get(i));
            }

            funcionarioResponsavelComboBox.setModel(new DefaultComboBoxModel<>(opcoes));

        } catch (ControllerException ex) {
            funcionarioResponsavelComboBox.setModel(new DefaultComboBoxModel<>(new FuncionarioOpcao[]{FuncionarioOpcao.placeholder("Erro ao carregar funcionários")}));
            JOptionPane.showMessageDialog(this, "Erro ao carregar funcionários: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void selecionarFuncionarioPorId(int idFuncionario) {
        for (int i = 1; i < funcionarioResponsavelComboBox.getItemCount(); i++) {
            FuncionarioOpcao opcao = funcionarioResponsavelComboBox.getItemAt(i);
            if (opcao != null && opcao.getFuncionario() != null
                    && opcao.getFuncionario().getId() == idFuncionario) {
                funcionarioResponsavelComboBox.setSelectedIndex(i);
                return;
            }
        }

        funcionarioResponsavelComboBox.setSelectedIndex(0);
    }

    private Funcionario getFuncionarioResponsavelSelecionado() {
        Object selecionado = funcionarioResponsavelComboBox.getSelectedItem();

        if (selecionado instanceof FuncionarioOpcao) {
            return ((FuncionarioOpcao) selecionado).getFuncionario();
        }

        return null;
    }

    private void salvarAlteracoesOrdemServico() {
        if (ordemServicoEditando == null || ordemServicoEditando.getId() <= 0) {
            JOptionPane.showMessageDialog(this, "Nenhuma ordem de serviço foi carregada para salvar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Funcionario funcionario = getFuncionarioResponsavelSelecionado();
        if (funcionario == null) {
            JOptionPane.showMessageDialog(this, "Selecione um funcionário responsável antes de salvar.", "Funcionário não selecionado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String problema = textoSemPlaceholder(problemaAreaText.getText(), "Descreva o problema");
        if (problema.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Descreva o problema da ordem de serviço.", "Problema não informado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String notaTecnica = Sessao.usuarioPodeEditarNotaTecnica()
                ? textoSemPlaceholder(notaTecnicaAreaText.getText(), "Digite a nota técnica")
                : ordemServicoEditando.getDescricao();

        if (notaTecnica == null || notaTecnica.trim().isEmpty()) {
            notaTecnica = "Sem nota técnica";
        }

        try {
            servicoController.editar(
                    ordemServicoEditando.getId(),
                    problema,
                    obterEstadoSelecionado(),
                    notaTecnica,
                    ordemServicoEditando.getDataRegistro() != null ? ordemServicoEditando.getDataRegistro() : new Date(System.currentTimeMillis()),
                    ordemServicoEditando.getIdVeiculo(),
                    funcionario.getId(),
                    obterIdsItensSelecionados(),
                    obterIdsTiposServicoSelecionados()
            );

            JOptionPane.showMessageDialog(this, "Ordem de serviço atualizada com sucesso.");
            TelaOrdemServico telaOrdemServico = new TelaOrdemServico();
            telaOrdemServico.setVisible(true);
            dispose();

        } catch (ControllerException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar ordem de serviço: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private java.util.List<Integer> obterIdsTiposServicoSelecionados() throws ControllerException {
        java.util.List<Integer> ids = new java.util.ArrayList<>();
        for (TipoServico tipoServico : listaServicos) {
            ids.add(tipoServico.getId());
        }
        return ids;
    }

    private java.util.List<Integer> obterIdsItensSelecionados() throws ControllerException {
        java.util.List<Integer> ids = new java.util.ArrayList<>();
        for (Item item : listaPecas) {
            ids.add(item.getId());
        }
        return ids;
    }

    private Estado obterEstadoSelecionado() {
        Object selecionado = statusComboBox.getSelectedItem();
        if (selecionado instanceof EstadoOpcao) {
            Estado estado = ((EstadoOpcao) selecionado).getEstado();
            return estado != null ? estado : Estado.NAO_PAGO;
        }
        return Estado.NAO_PAGO;
    }

    private String textoSemPlaceholder(String texto, String placeholder) {
        if (texto == null) {
            return "";
        }

        String valor = texto.trim();
        if (valor.equalsIgnoreCase(placeholder)) {
            return "";
        }

        return valor;
    }

    private void apagarOrdemServicoAtual() {
        if (ordemServicoEditando == null || ordemServicoEditando.getId() <= 0) {
            JOptionPane.showMessageDialog(this, "Nenhuma ordem de serviço foi carregada para apagar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int opcao = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja apagar permanentemente a OS #" + ordemServicoEditando.getId() + "?",
                "Confirmar exclusão",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (opcao != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            servicoController.excluir(ordemServicoEditando.getId());
            JOptionPane.showMessageDialog(this, "Ordem de serviço apagada com sucesso.");

            TelaOrdemServico telaOrdemServico = new TelaOrdemServico();
            telaOrdemServico.setVisible(true);
            dispose();

        } catch (ControllerException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao apagar ordem de serviço: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }


    private String buscarNomeFuncionarioResponsavel(int idFuncionarioResponsavel) {
        try {
            return servicoController.buscarNomeFuncionarioResponsavel(idFuncionarioResponsavel);
        } catch (ControllerException ex) {
            return "Indisponível";
        }
    }

    private String buscarPlacaVeiculo(int idVeiculo) {
        try {
            return servicoController.buscarPlacaVeiculo(idVeiculo);
        } catch (ControllerException ex) {
            return "Indisponível";
        }
    }

    private static class FuncionarioOpcao {
        private final Funcionario funcionario;
        private final String texto;

        private FuncionarioOpcao(Funcionario funcionario) {
            this.funcionario = funcionario;
            this.texto = funcionario.getNome() + " - " + (funcionario.getCargo() != null ? funcionario.getCargo().name() : "Sem cargo");
        }

        private FuncionarioOpcao(String texto) {
            this.funcionario = null;
            this.texto = texto;
        }

        static FuncionarioOpcao placeholder(String texto) {
            return new FuncionarioOpcao(texto);
        }

        Funcionario getFuncionario() {
            return funcionario;
        }

        @Override
        public String toString() {
            return texto;
        }
    }


    private static class TipoServicoOpcao {
        private final TipoServico tipoServico;
        private final String texto;

        private TipoServicoOpcao(TipoServico tipoServico) {
            this.tipoServico = tipoServico;
            BigDecimal valor = tipoServico.getValorServico() != null ? tipoServico.getValorServico() : BigDecimal.ZERO;
            this.texto = tipoServico.getDescricao() + " - R$ " + valor.setScale(2, RoundingMode.HALF_UP);
        }

        private TipoServicoOpcao(String texto) {
            this.tipoServico = null;
            this.texto = texto;
        }

        static TipoServicoOpcao placeholder(String texto) {
            return new TipoServicoOpcao(texto);
        }

        TipoServico getTipoServico() {
            return tipoServico;
        }

        @Override
        public String toString() {
            return texto;
        }
    }

    private static class ItemOpcao {
        private final Item item;
        private final String texto;

        private ItemOpcao(Item item) {
            this.item = item;
            BigDecimal valor = item.getValorCompra() != null ? item.getValorCompra() : BigDecimal.ZERO;
            this.texto = item.getNome() + " - " + item.getMarca() + " - cód. " + item.getCodigo() + " - R$ " + valor.setScale(2, RoundingMode.HALF_UP);
        }

        private ItemOpcao(String texto) {
            this.item = null;
            this.texto = texto;
        }

        static ItemOpcao placeholder(String texto) {
            return new ItemOpcao(texto);
        }

        Item getItem() {
            return item;
        }

        @Override
        public String toString() {
            return texto;
        }
    }

    private static class EstadoOpcao {
        private final Estado estado;

        private EstadoOpcao(Estado estado) {
            this.estado = estado;
        }

        static EstadoOpcao placeholder(String texto) {
            return new EstadoOpcao(null) {
                @Override
                public String toString() {
                    return texto;
                }
            };
        }

        Estado getEstado() {
            return estado;
        }

        @Override
        public String toString() {
            if (estado == null) return "Selecione...";
            switch (estado) {
                case NAO_PAGO: return "Não pago";
                case PAGO: return "Pago";
                case CONCLUIDO: return "Concluído";
                case CANCELADO: return "Cancelado";
                default: return estado.getDescricaoTela();
            }
        }
    }

}