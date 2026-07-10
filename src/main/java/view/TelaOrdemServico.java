package view;

import utils.Constantes;

import controllers.OrdemServicoController;
import entities.OrdemServico;
import entities.Cliente;
import entities.Funcionario;
import entities.Veiculo;
import exceptions.ControllerException;
import java.util.Optional;
import java.util.List;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.swing.JOptionPane;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import java.awt.BorderLayout;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import utils.GeradorPDF;
import utils.Email;
import utils.PermissaoAcesso;

public class TelaOrdemServico extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel painelPretoFundo;
    private EstilizacaoRedonda.PainelRedondo painelServico;
    
    private OrdemServicoController servicoController;
    private OrdemServico servicoVisualizado;
    private String filtroAtual = null; 
    
    private JLabel lblCliente;
    private JLabel lblVeiculo;
    private JLabel lblPlaca;
    private JLabel lblProblema;
    private JLabel lblCusto;
    private JLabel lblStatus;

    public TelaOrdemServico() {
        if (!PermissaoAcesso.autorizar(this, PermissaoAcesso.podeAcessarOrdemServico(), "Ordem de Serviço")) {
            javax.swing.SwingUtilities.invokeLater(() -> {
                new TelaHome().setVisible(true);
                dispose();
            });
            return;
        }


        this.servicoController = new OrdemServicoController(); 

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

        JLabel titulo = new JLabel("Ordem de Serviço");
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
        JMenuItem opCpfCliente = new JMenuItem("Buscar por CPF do Cliente");
        JMenuItem opPlacaVeiculo = new JMenuItem("Buscar por Placa do Veículo");
        JMenuItem opNomeCliente = new JMenuItem("Buscar por Nome do Cliente");
        JMenuItem opTodos = new JMenuItem("Buscar Todos");

        ActionListener acaoSelecionarFiltro = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                filtroAtual = e.getActionCommand().replace("Buscar por ", "").replace("Buscar ", "");
                if (filtroAtual.equals("Todos")) {
                    campoBusca.setText("Filtro: Todos (Clique na lupa para listar)");
                } else if (filtroAtual.equals("CPF do Cliente")) {
                    campoBusca.setText("Filtro: CPF do Cliente (Digite o CPF...)");
                } else if (filtroAtual.equals("Placa do Veículo")) {
                    campoBusca.setText("Filtro: Placa do Veículo (Digite a placa...)");
                } else if (filtroAtual.equals("Nome do Cliente")) {
                    campoBusca.setText("Filtro: Nome do Cliente (Digite o nome...)");
                }
            }
        };

        opCpfCliente.addActionListener(acaoSelecionarFiltro);
        opPlacaVeiculo.addActionListener(acaoSelecionarFiltro);
        opNomeCliente.addActionListener(acaoSelecionarFiltro);
        opTodos.addActionListener(acaoSelecionarFiltro);

        menuFiltros.add(opTodos);
        menuFiltros.addSeparator();
        menuFiltros.add(opCpfCliente);
        menuFiltros.add(opPlacaVeiculo);
        menuFiltros.add(opNomeCliente);

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
                    JOptionPane.showMessageDialog(null, "Selecione uma opção de busca no botão de filtros primeiro.", "Filtro Ausente", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    if (filtroAtual.equals("Todos")) {
                        List<OrdemServico> todasOrdens = servicoController.listarTodos();
                        exibirResultadosBusca(todasOrdens);
                        return;
                    }

                    String textoBusca = campoBusca.getText().trim();
                    if (textoBusca.isEmpty() || textoBusca.equals("Buscar") || textoBusca.startsWith("Filtro:")) {
                        JOptionPane.showMessageDialog(null, "Digite a informação da busca no campo antes de clicar na lupa.", "Campo Vazio", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    switch (filtroAtual) {
                        case "CPF do Cliente":
                            exibirResultadosBusca(servicoController.buscarPorCpfCliente(textoBusca));
                            return;

                        case "Placa do Veículo":
                            exibirResultadosBusca(servicoController.buscarPorPlacaVeiculo(textoBusca));
                            return;

                        case "Nome do Cliente":
                            exibirResultadosBusca(servicoController.buscarPorNomeCliente(textoBusca));
                            return;

                        default:
                            JOptionPane.showMessageDialog(null, "Filtro inválido. Selecione CPF, placa ou nome do cliente.", "Filtro Inválido", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (ControllerException ex) {
                    JOptionPane.showMessageDialog(null, "Erro na busca: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        EstilizacaoRedonda.BotaoRedondo botaoNovoServico = new EstilizacaoRedonda.BotaoRedondo("NOVO SERVIÇO", Constantes.VERMELHO_FERRARI, Constantes.AMARELO_OURO, Constantes.PRETO_FOSCO, 40);
        botaoNovoServico.setForeground(Constantes.CINZA_CLARO);
        botaoNovoServico.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoNovoServico.setBounds(430, 180, 420, 50);
        painelPretoFundo.add(botaoNovoServico);
        botaoNovoServico.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TelaNovoServico telaNovoServico = new TelaNovoServico();
                telaNovoServico.setVisible(true);
                dispose();
            }
        });
        
        EstilizacaoRedonda.BotaoRedondo botaoTipoServico = new EstilizacaoRedonda.BotaoRedondo("TIPOS DE SERVIÇO", Constantes.VERMELHO_FERRARI, Constantes.AMARELO_OURO, Constantes.PRETO_FOSCO, 40);
        botaoTipoServico.setForeground(Constantes.CINZA_CLARO);
        botaoTipoServico.setFont(new Font("SansSerif", Font.BOLD, 14));
        botaoTipoServico.setBounds(32, 108, 192, 45);
        painelPretoFundo.add(botaoTipoServico);
        botaoTipoServico.setVisible(PermissaoAcesso.podeGerenciarTiposServico());
        botaoTipoServico.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!PermissaoAcesso.autorizar(TelaOrdemServico.this, PermissaoAcesso.podeGerenciarTiposServico(), "Tipos de Serviço")) return;
                ModalTipoServico modal = new ModalTipoServico(TelaOrdemServico.this);
                modal.setVisible(true);
            }
        });
        

        painelServico = new EstilizacaoRedonda.PainelRedondo(null, 30, 4, Constantes.CINZA_CLARO, Constantes.PRETO_FOSCO);
        painelServico.setBounds(30, 260, 1200, 140);
        painelServico.setVisible(false);
        painelPretoFundo.add(painelServico);

        lblVeiculo = new JLabel("Placa do veículo:");
        lblVeiculo.setFont(new Font("SansSerif", Font.PLAIN, 18));
        lblVeiculo.setBounds(29, -1, 337, 50);
        painelServico.add(lblVeiculo);
        
        lblCliente = new JLabel("Func. responsável:");
        lblCliente.setFont(new Font("SansSerif", Font.PLAIN, 18));
        lblCliente.setBounds(25, 78, 439, 50);
        painelServico.add(lblCliente);

        lblPlaca = new JLabel("OS ID:");
        lblPlaca.setFont(new Font("SansSerif", Font.PLAIN, 18));
        lblPlaca.setBounds(459, 38, 175, 50);
        painelServico.add(lblPlaca);

        lblProblema = new JLabel("Problema:");
        lblProblema.setFont(new Font("SansSerif", Font.PLAIN, 18));
        lblProblema.setBounds(719, 13, 375, 115);
        lblProblema.setVerticalAlignment(SwingConstants.TOP); 
        painelServico.add(lblProblema);

        lblCusto = new JLabel("Custo atual:");
        lblCusto.setFont(new Font("SansSerif", Font.PLAIN, 18));
        lblCusto.setBounds(459, -1, 231, 50);
        painelServico.add(lblCusto);

        lblStatus = new JLabel("Status:");
        lblStatus.setFont(new Font("SansSerif", Font.PLAIN, 18));
        lblStatus.setBounds(459, 78, 231, 50);
        painelServico.add(lblStatus);

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
        painelServico.add(botaoEditar);
        
        botaoEditar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (servicoVisualizado != null) {
                    TelaEditarServico telaEditarServico = new TelaEditarServico(servicoVisualizado);
                    telaEditarServico.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Busque e selecione uma ordem primeiro.");
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
        painelServico.add(botaoDetalhes);
        
        botaoDetalhes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (servicoVisualizado != null) {
                    exibirDetalhesOrdemServico();
                } else {
                    JOptionPane.showMessageDialog(null, "Busque e selecione uma ordem primeiro.");
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

    private void exibirDetalhesOrdemServico() {
        try {
            Optional<Cliente> clienteOpt = servicoController.buscarClientePorOrdemServico(servicoVisualizado.getId());
            Optional<Veiculo> veiculoOpt = servicoController.buscarVeiculoPorId(servicoVisualizado.getIdVeiculo());
            Optional<Funcionario> funcionarioOpt = servicoController.buscarFuncionarioResponsavelCompleto(servicoVisualizado.getIdFuncionarioResponsavel());
            List<String> servicosAplicados = servicoController.buscarServicosAplicadosDetalhados(servicoVisualizado.getId());
            List<String> pecasAplicadas = servicoController.buscarPecasAplicadasDetalhadas(servicoVisualizado.getId());
            BigDecimal custoAtual = obterCustoAtual(servicoVisualizado.getId());

            Cliente cliente = clienteOpt.orElse(null);
            Veiculo veiculo = veiculoOpt.orElse(null);
            Funcionario funcionario = funcionarioOpt.orElse(null);
            String emailCliente = cliente != null ? cliente.getEmail() : null;

            StringBuilder detalhes = new StringBuilder();
            detalhes.append("ORDEM DE SERVIÇO #").append(servicoVisualizado.getId()).append("\n\n");
            detalhes.append("Data de registro: ").append(valorOuPadrao(String.valueOf(servicoVisualizado.getDataRegistro()))).append("\n");
            detalhes.append("Status: ").append(valorOuPadrao(String.valueOf(servicoVisualizado.getEstado()))).append("\n");
            detalhes.append("Custo atual: R$ ").append(formatarMoeda(custoAtual)).append("\n\n");

            detalhes.append("CLIENTE\n");
            detalhes.append("Nome: ").append(valorOuPadrao(cliente != null ? cliente.getNome() : null)).append("\n");
            detalhes.append("CPF: ").append(valorOuPadrao(cliente != null ? cliente.getCpf() : null)).append("\n");
            detalhes.append("Telefone: ").append(valorOuPadrao(cliente != null ? cliente.getTelefone() : null)).append("\n");
            detalhes.append("E-mail: ").append(valorOuPadrao(emailCliente)).append("\n\n");

            detalhes.append("VEÍCULO\n");
            detalhes.append("Placa: ").append(valorOuPadrao(veiculo != null ? veiculo.getPlaca() : null)).append("\n");
            detalhes.append("Marca: ").append(valorOuPadrao(veiculo != null ? veiculo.getMarca() : null)).append("\n");
            detalhes.append("Modelo: ").append(valorOuPadrao(veiculo != null ? veiculo.getModelo() : null)).append("\n\n");

            detalhes.append("FUNCIONÁRIO RESPONSÁVEL\n");
            detalhes.append("Nome: ").append(valorOuPadrao(funcionario != null ? funcionario.getNome() : null)).append("\n");
            detalhes.append("Cargo: ").append(valorOuPadrao(funcionario != null && funcionario.getCargo() != null ? funcionario.getCargo().name() : null)).append("\n\n");

            detalhes.append("PROBLEMA\n").append(valorOuPadrao(servicoVisualizado.getProblema())).append("\n\n");
            detalhes.append("NOTA TÉCNICA\n").append(valorOuPadrao(servicoVisualizado.getDescricao())).append("\n\n");

            detalhes.append("SERVIÇOS APLICADOS\n");
            if (servicosAplicados.isEmpty()) {
                detalhes.append("Nenhum serviço aplicado informado.\n");
            } else {
                for (String servico : servicosAplicados) detalhes.append("- ").append(servico).append("\n");
            }

            detalhes.append("\nPEÇAS UTILIZADAS\n");
            if (pecasAplicadas.isEmpty()) {
                detalhes.append("Nenhuma peça utilizada informada.\n");
            } else {
                for (String peca : pecasAplicadas) detalhes.append("- ").append(peca).append("\n");
            }

            JDialog dialog = new JDialog(this, "Detalhes da Ordem de Serviço", true);
            dialog.setSize(760, 620);
            dialog.setLocationRelativeTo(this);
            dialog.getContentPane().setLayout(new BorderLayout());

            JTextArea areaDetalhes = new JTextArea(detalhes.toString());
            areaDetalhes.setEditable(false);
            areaDetalhes.setFont(new Font("Monospaced", Font.PLAIN, 14));
            areaDetalhes.setLineWrap(true);
            areaDetalhes.setWrapStyleWord(true);
            dialog.getContentPane().add(new JScrollPane(areaDetalhes), BorderLayout.CENTER);

            JPanel painelBotoes = new JPanel();
            JButton btnGerarOrcamento = new JButton("Gerar orçamento PDF / enviar e-mail");
            btnGerarOrcamento.setFont(new Font("SansSerif", Font.BOLD, 14));
            btnGerarOrcamento.setBackground(Constantes.PRETO_FOSCO);
            btnGerarOrcamento.setForeground(Constantes.CINZA_CLARO);

            btnGerarOrcamento.addActionListener(ev -> {
                try {
                    String caminhoPdf = GeradorPDF.gerarOrcamentoOrdemServico(servicoVisualizado);

                    if (emailCliente == null || emailCliente.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(dialog,
                                "Orçamento gerado em:\n" + caminhoPdf + "\n\nO cliente não possui e-mail cadastrado.",
                                "PDF gerado", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }

                    int opcao = JOptionPane.showConfirmDialog(dialog,
                            "Orçamento gerado em:\n" + caminhoPdf + "\n\nDeseja enviar para o e-mail do cliente?\n" + emailCliente,
                            "Enviar orçamento por e-mail", JOptionPane.YES_NO_OPTION);

                    if (opcao == JOptionPane.YES_OPTION) {
                        boolean enviado = Email.enviarComAnexo(
                                emailCliente,
                                "Orçamento da Ordem de Serviço #" + servicoVisualizado.getId() + " - A Garagem",
                                "Olá! Segue em anexo o orçamento da sua ordem de serviço.\n\nAtenciosamente,\nA Garagem",
                                caminhoPdf
                        );

                        if (enviado) {
                            JOptionPane.showMessageDialog(dialog, "Orçamento enviado com sucesso para " + emailCliente + ".");
                        } else {
                            JOptionPane.showMessageDialog(dialog, "Não foi possível enviar o e-mail. Verifique as credenciais no .env e a conexão.", "Erro no envio", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Erro ao gerar/enviar orçamento: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            });

            painelBotoes.add(btnGerarOrcamento);
            dialog.getContentPane().add(painelBotoes, BorderLayout.SOUTH);
            dialog.setVisible(true);

        } catch (ControllerException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar detalhes da OS: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private BigDecimal obterCustoAtual(int idOrdemServico) {
        try {
            return servicoController.buscarCustoAtualServico(idOrdemServico).orElse(BigDecimal.ZERO);
        } catch (ControllerException ex) {
            return BigDecimal.ZERO;
        }
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

    private void exibirResultadosBusca(List<OrdemServico> ordens) {
        if (ordens == null || ordens.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhuma ordem de serviço encontrada com o filtro informado.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            servicoVisualizado = null;
            painelServico.setVisible(false);
        } else if (ordens.size() == 1) {
            carregarOrdemNaTela(ordens.get(0));
        } else {
            exibirModalTodasOrdens(ordens);
        }
    }

    private void carregarOrdemNaTela(OrdemServico ordemServico) {
        servicoVisualizado = ordemServico;
        lblCliente.setText("Func. responsável: " + buscarNomeFuncionarioResponsavel(servicoVisualizado.getIdFuncionarioResponsavel()));
        lblVeiculo.setText("Placa do veículo: " + buscarPlacaVeiculo(servicoVisualizado.getIdVeiculo()));
        lblPlaca.setText("OS ID: " + servicoVisualizado.getId());
        lblProblema.setText("<html>Problema: " + servicoVisualizado.getProblema() + "</html>");
        lblStatus.setText("Status: " + servicoVisualizado.getEstado());

        try {
            Optional<BigDecimal> custo = servicoController.buscarCustoAtualServico(servicoVisualizado.getId());
            if(custo.isPresent()) {
                lblCusto.setText("Custo atual: R$ " + formatarMoeda(custo.get()));
            } else {
                lblCusto.setText("Custo atual: R$ " + formatarMoeda(BigDecimal.ZERO));
            }
        } catch (ControllerException ex) {
            lblCusto.setText("Custo atual: R$ Indisponível");
        }

        painelServico.setVisible(true);
    }

    private void exibirModalTodasOrdens(List<OrdemServico> ordens) {
        JDialog dialog = new JDialog(this, "Listagem de Ordens de Serviço", true);
        dialog.setSize(850, 500);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setLayout(new BorderLayout());

        JPanel painelSuperior = new JPanel();
        painelSuperior.setBackground(Constantes.PRETO_FOSCO);
        JLabel lblTitulo = new JLabel("Selecione uma ordem para carregar na tela principal");
        lblTitulo.setForeground(Constantes.CINZA_CLARO);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        painelSuperior.add(lblTitulo);
        dialog.getContentPane().add(painelSuperior, BorderLayout.NORTH);

        String[] colunas = {"Nº (ID)", "Placa do Veículo", "Func. Responsável", "Data Registro", "Status"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        for (OrdemServico os : ordens) {
            model.addRow(new Object[]{
                os.getId(),
                buscarPlacaVeiculo(os.getIdVeiculo()),
                buscarNomeFuncionarioResponsavel(os.getIdFuncionarioResponsavel()),
                os.getDataRegistro(),
                os.getEstado()
            });
        }

        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFont(new Font("SansSerif", Font.PLAIN, 16));
        table.setRowHeight(30);
        dialog.getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();
        JButton btnSelecionar = new JButton("Carregar Ordem Selecionada");
        btnSelecionar.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnSelecionar.setBackground(Constantes.PRETO_FOSCO);
        btnSelecionar.setForeground(Constantes.CINZA_CLARO);

        btnSelecionar.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                carregarOrdemNaTela(ordens.get(row));
                dialog.dispose(); 
            } else {
                JOptionPane.showMessageDialog(dialog, "Selecione uma ordem na tabela primeiro.");
            }
        });

        painelBotoes.add(btnSelecionar);
        dialog.getContentPane().add(painelBotoes, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
}