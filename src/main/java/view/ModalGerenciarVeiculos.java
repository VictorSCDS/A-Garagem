package view;

import utils.Constantes;
import utils.PermissaoAcesso;

import controllers.VeiculoController;
import entities.Veiculo;
import exceptions.ControllerException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ModalGerenciarVeiculos extends JDialog {

    private static final long serialVersionUID = 1L;
    private String cpfCliente;
    private String nomeCliente;
    private VeiculoController veiculoController;
    private JTable tabelaMotos;
    private DefaultTableModel modeloTabela;

    public ModalGerenciarVeiculos(JFrame parent, String cpfCliente, String nomeCliente) {
        super(parent, "Gerenciar Veículos de " + nomeCliente.split(" ")[0], true);

        if (!PermissaoAcesso.autorizar(this, PermissaoAcesso.podeAcessarClientes(), "Clientes")) {
            javax.swing.SwingUtilities.invokeLater(() -> {
                new TelaHome().setVisible(true);
                dispose();
            });
            return;
        }


        this.cpfCliente = cpfCliente;
        this.nomeCliente = nomeCliente;
        this.veiculoController = new VeiculoController();

        setSize(650, 450);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(parent);

        JPanel painelSuperior = new JPanel();
        painelSuperior.setBackground(Constantes.PRETO_FOSCO);
        JLabel lblTitulo = new JLabel("Veículos Vinculados");
        lblTitulo.setForeground(Constantes.CINZA_CLARO);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        painelSuperior.add(lblTitulo);
        add(painelSuperior, BorderLayout.NORTH);

        String[] colunas = {"Placa", "Marca", "Modelo"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabelaMotos = new JTable(modeloTabela);
        tabelaMotos.setFont(new Font("SansSerif", Font.PLAIN, 16));
        tabelaMotos.setRowHeight(30);
        tabelaMotos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(tabelaMotos), BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnAdicionar = new JButton("Novo Veículo");
        btnAdicionar.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnAdicionar.setForeground(Constantes.AMARELO_OURO);
        
        JButton btnEditar = new JButton("Editar Veículo");
        btnEditar.setFont(new Font("SansSerif", Font.BOLD, 14));
        
        JButton btnRemover = new JButton("Remover Veículo");
        btnRemover.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnRemover.setForeground(Constantes.VERMELHO_FERRARI);
        
        JButton btnFechar = new JButton("Concluir");
        btnFechar.setFont(new Font("SansSerif", Font.BOLD, 14));

        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnRemover);
        painelBotoes.add(btnFechar);
        add(painelBotoes, BorderLayout.SOUTH);
        
        btnAdicionar.addActionListener(e -> {
            TelaCadastrarVeiculo telaCadastrar = new TelaCadastrarVeiculo(this.cpfCliente, this.nomeCliente);
            telaCadastrar.setVisible(true);
            dispose();
        });

        btnFechar.addActionListener(e -> dispose());

        btnRemover.addActionListener(e -> {
            int linhaSelecionada = tabelaMotos.getSelectedRow();
            if (linhaSelecionada == -1) {
                if (modeloTabela.getRowCount() > 0) {
                    linhaSelecionada = 0;
                    tabelaMotos.setRowSelectionInterval(0, 0);
                } else {
                    JOptionPane.showMessageDialog(this, "Não há veículos vinculados para apagar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }

            String placa = (String) modeloTabela.getValueAt(linhaSelecionada, 0);
            String modelo = (String) modeloTabela.getValueAt(linhaSelecionada, 2);

            int confirm = JOptionPane.showConfirmDialog(this, 
                "Deseja realmente remover o veículo " + modelo + " (Placa: " + placa + ")?\nEssa ação não poderá ser desfeita.", 
                "Confirmar Exclusão", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    veiculoController.excluir(placa);
                    JOptionPane.showMessageDialog(this, "Veículo removido com sucesso!");
                    carregarTabela();
                } catch (ControllerException ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao remover veículo: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnEditar.addActionListener(e -> {
            int linhaSelecionada = tabelaMotos.getSelectedRow();
            
            if (linhaSelecionada == -1) {
                if (modeloTabela.getRowCount() > 0) {
                    linhaSelecionada = 0;
                    tabelaMotos.setRowSelectionInterval(0, 0);
                } else {
                    JOptionPane.showMessageDialog(this, "Não há veículos vinculados para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }

            String placaAtual = (String) modeloTabela.getValueAt(linhaSelecionada, 0);
            String marcaAtual = (String) modeloTabela.getValueAt(linhaSelecionada, 1);
            String modeloAtual = (String) modeloTabela.getValueAt(linhaSelecionada, 2);
            
            JTextField campoPlaca = new JTextField(placaAtual);
            JTextField campoMarca = new JTextField(marcaAtual);
            JTextField campoModelo = new JTextField(modeloAtual);
            
            Object[] formulario = {
                "Placa:", campoPlaca,
                "Marca:", campoMarca,
                "Modelo/Ano:", campoModelo
            };

            int option = JOptionPane.showConfirmDialog(this, formulario, "Editar Dados da Moto", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            
            if (option == JOptionPane.OK_OPTION) {
                String novaPlaca = campoPlaca.getText().trim();
                String novaMarca = campoMarca.getText().trim();
                String novoModelo = campoModelo.getText().trim();

                if (novaPlaca.equals(placaAtual) && novaMarca.equals(marcaAtual) && novoModelo.equals(modeloAtual)) {
                    JOptionPane.showMessageDialog(this, "Nenhuma alteração foi feita no veículo.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                try {
                    veiculoController.editar(novaPlaca, novaMarca, novoModelo, placaAtual);
                    JOptionPane.showMessageDialog(this, "Veículo atualizado com sucesso!");
                    carregarTabela();
                } catch (ControllerException ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao editar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        carregarTabela();
    }

    private void carregarTabela() {
        try {
            List<Veiculo> motos = veiculoController.buscarPorCliente(cpfCliente);
            modeloTabela.setRowCount(0);
            for (Veiculo v : motos) {
                modeloTabela.addRow(new Object[]{v.getPlaca(), v.getMarca(), v.getModelo()});
            }
        } catch (ControllerException e) {
            JOptionPane.showMessageDialog(this, "Erro ao listar os veículos do cliente: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}