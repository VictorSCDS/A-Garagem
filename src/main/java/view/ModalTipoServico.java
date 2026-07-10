package view;

import utils.Constantes;
import utils.PermissaoAcesso;

import controllers.TipoServicoController;
import entities.TipoServico;
import exceptions.ControllerException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class ModalTipoServico extends JDialog {

    private static final long serialVersionUID = 1L;
    private TipoServicoController tipoServicoController;
    private JTable tabelaServicos;
    private DefaultTableModel modeloTabela;

    public ModalTipoServico(JFrame parent) {
        super(parent, "Gerenciar Tipos de Serviço", true);

        if (!PermissaoAcesso.autorizar(this, PermissaoAcesso.podeGerenciarTiposServico(), "Tipos de Serviço")) {
            javax.swing.SwingUtilities.invokeLater(() -> {
                new TelaHome().setVisible(true);
                dispose();
            });
            return;
        }


        this.tipoServicoController = new TipoServicoController();

        setSize(750, 500);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(parent);

        JPanel painelSuperior = new JPanel();
        painelSuperior.setBackground(Constantes.PRETO_FOSCO);
        JLabel lblTitulo = new JLabel("Tipos de Serviço e Mão de Obra");
        lblTitulo.setForeground(Constantes.CINZA_CLARO);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        painelSuperior.add(lblTitulo);
        add(painelSuperior, BorderLayout.NORTH);

        String[] colunas = {"ID", "Descrição do Serviço", "Custo Padrão (R$)"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabelaServicos = new JTable(modeloTabela);
        tabelaServicos.setFont(new Font("SansSerif", Font.PLAIN, 16));
        tabelaServicos.setRowHeight(30);
        tabelaServicos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        tabelaServicos.getColumnModel().getColumn(0).setPreferredWidth(50);
        tabelaServicos.getColumnModel().getColumn(1).setPreferredWidth(450);
        tabelaServicos.getColumnModel().getColumn(2).setPreferredWidth(150);
        
        add(new JScrollPane(tabelaServicos), BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        
        JButton btnNovo = new JButton("Novo Serviço");
        btnNovo.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnNovo.setBackground(Constantes.AMARELO_OURO); 
        btnNovo.setForeground(Constantes.CINZA_CLARO);
        
        JButton btnEditar = new JButton("Editar Serviço");
        btnEditar.setFont(new Font("SansSerif", Font.BOLD, 14));
        
        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnExcluir.setForeground(Constantes.VERMELHO_FERRARI);
        
        JButton btnFechar = new JButton("Fechar");
        btnFechar.setFont(new Font("SansSerif", Font.BOLD, 14));

        painelBotoes.add(btnNovo);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnFechar);
        add(painelBotoes, BorderLayout.SOUTH);

        btnFechar.addActionListener(e -> dispose());

        btnNovo.addActionListener(e -> abrirFormularioServico(null));

        btnEditar.addActionListener(e -> {
            int linha = tabelaServicos.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um serviço na tabela para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int id = (int) modeloTabela.getValueAt(linha, 0);
            String descricaoAtual = (String) modeloTabela.getValueAt(linha, 1);
            String valorAtualStr = ((String) modeloTabela.getValueAt(linha, 2)).replace(",", ".");
            BigDecimal valorAtual = new BigDecimal(valorAtualStr);
            
            TipoServico ts = new TipoServico(id, descricaoAtual, valorAtual);
            abrirFormularioServico(ts);
        });

        btnExcluir.addActionListener(e -> {
            int linha = tabelaServicos.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um serviço na tabela para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int id = (int) modeloTabela.getValueAt(linha, 0);
            String descricao = (String) modeloTabela.getValueAt(linha, 1);

            int confirm = JOptionPane.showConfirmDialog(this, 
                "Tem certeza que deseja excluir o serviço '" + descricao + "'?\nCaso já tenha sido usado em uma OS, a exclusão será bloqueada.", 
                "Confirmar Exclusão", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    tipoServicoController.excluir(id);
                    JOptionPane.showMessageDialog(this, "Serviço excluído com sucesso.");
                    carregarTabela();
                } catch (ControllerException ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        carregarTabela();
    }

    private void carregarTabela() {
        try {
            modeloTabela.setRowCount(0);
            List<TipoServico> servicos = tipoServicoController.listarTodos();
            for (TipoServico ts : servicos) {
                BigDecimal valor = ts.getValorServico() != null ? ts.getValorServico() : BigDecimal.ZERO;
                modeloTabela.addRow(new Object[]{
                    ts.getId(), 
                    ts.getDescricao(), 
                    valor.setScale(2, RoundingMode.HALF_UP).toString().replace(".", ",")
                });
            }
        } catch (ControllerException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar tipos de serviço: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirFormularioServico(TipoServico servicoExistente) {
        JTextField campoDescricao = new JTextField(servicoExistente != null ? servicoExistente.getDescricao() : "");
        JTextField campoValor = new JTextField(servicoExistente != null ? servicoExistente.getValorServico().setScale(2, RoundingMode.HALF_UP).toString() : "");

        Object[] message = {
            "Descrição do Serviço:", campoDescricao,
            "Custo (R$):", campoValor
        };

        String titulo = servicoExistente == null ? "Cadastrar Novo Serviço" : "Editar Serviço";
        int option = JOptionPane.showConfirmDialog(this, message, titulo, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            String descricao = campoDescricao.getText().trim();
            String valorStr = campoValor.getText().trim().replace(",", ".");

            if (descricao.isEmpty() || valorStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                BigDecimal valor = new BigDecimal(valorStr);

                if (servicoExistente == null) {
                    tipoServicoController.cadastrar(descricao, valor);
                    JOptionPane.showMessageDialog(this, "Serviço cadastrado com sucesso!");
                } else {
                    tipoServicoController.editar(servicoExistente.getId(), descricao, valor);
                    JOptionPane.showMessageDialog(this, "Serviço atualizado com sucesso!");
                }
                carregarTabela();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "O valor deve ser numérico.", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (ControllerException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar serviço: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}