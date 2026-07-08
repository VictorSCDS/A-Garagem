package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import controllers.VeiculoController;
import entities.Veiculo;
import exceptions.ControllerException;

public class ModalVeiculos extends JDialog {

    private static final long serialVersionUID = 1L;
    private JPanel painelFundo;
    private VeiculoController veiculoController;
    private JTable tabelaMotos;
    private DefaultTableModel modeloTabela;

    public ModalVeiculos(JFrame parent, String cpfCliente, String nomeCliente) {
        super(parent, "Veículos do Cliente", true);
        this.veiculoController = new VeiculoController();

        setSize(700, 500);
        setLayout(null);
        setUndecorated(true);
        setLocationRelativeTo(parent);

        painelFundo = new JPanel();
        painelFundo.setBackground(Color.WHITE);
        painelFundo.setBounds(0, 0, 700, 500);
        painelFundo.setLayout(null);
        painelFundo.setBorder(new javax.swing.border.LineBorder(Color.BLACK, 2));
        add(painelFundo);

        JPanel barraSuperior = new JPanel();
        barraSuperior.setBackground(Color.DARK_GRAY);
        barraSuperior.setBounds(0, 0, 700, 60);
        barraSuperior.setLayout(null);
        painelFundo.add(barraSuperior);

        JLabel titulo = new JLabel("Motos de " + nomeCliente.split(" ")[0]);
        titulo.setForeground(Color.WHITE);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setFont(new Font("Liberation Serif", Font.BOLD, 24));
        titulo.setBounds(150, 10, 400, 40);
        barraSuperior.add(titulo);

        EstilizacaoRedonda.PainelRedondo painelTabela = new EstilizacaoRedonda.PainelRedondo(new BorderLayout(), 30, 2, Color.WHITE, Color.BLACK);
        painelTabela.setBounds(30, 80, 640, 320);
        painelFundo.add(painelTabela);

        String[] colunas = {"Placa", "Marca", "Modelo"};
        modeloTabela = new DefaultTableModel(colunas, 0);
        tabelaMotos = new JTable(modeloTabela);
        tabelaMotos.setFont(new Font("SansSerif", Font.PLAIN, 16));
        tabelaMotos.setRowHeight(30);
        
        JScrollPane scroll = new JScrollPane(tabelaMotos);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        painelTabela.add(scroll);

        EstilizacaoRedonda.BotaoRedondo botaoSair = new EstilizacaoRedonda.BotaoRedondo("FECHAR", Color.BLACK, Color.DARK_GRAY, Color.GRAY, 30);
        botaoSair.setForeground(Color.WHITE);
        botaoSair.setFont(new Font("SansSerif", Font.BOLD, 14));
        botaoSair.setBounds(275, 420, 150, 45);
        botaoSair.addActionListener(e -> dispose());
        painelFundo.add(botaoSair);
        buscarDadosNoBanco(cpfCliente);
    }

    private void buscarDadosNoBanco(String cpf) {
        try {
            List<Veiculo> motos = veiculoController.buscarPorCliente(cpf);
            modeloTabela.setRowCount(0);
            for (Veiculo v : motos) {
                modeloTabela.addRow(new Object[]{v.getPlaca(), v.getMarca(), v.getModelo()});
            }
        } catch (ControllerException e) {
            System.err.println("Erro ao carregar motos no modal: " + e.getMessage());
        }
    }
}