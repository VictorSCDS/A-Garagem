package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TelaFinancas extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel painelPretoFundo;
    private EstilizacaoRedonda.PainelRedondo painelGrafico1;
    private EstilizacaoRedonda.PainelRedondo painelGrafico2;
    private EstilizacaoRedonda.PainelRedondo painelGrafico3;
    private EstilizacaoRedonda.CaixaTextoRedonda txtDateInicio;
    private EstilizacaoRedonda.CaixaTextoRedonda txtDateFim;

    public TelaFinancas() {

        setBackground(Color.DARK_GRAY);
        setSize(1280, 720);
        setMaximizedBounds(new Rectangle(0, 0, 1280, 720));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        painelPretoFundo = new JPanel();
        painelPretoFundo.setBackground(Color.WHITE);
        painelPretoFundo.setLayout(null);
        setContentPane(painelPretoFundo);

        JPanel barraSuperior = new JPanel();
        barraSuperior.setBackground(Color.DARK_GRAY);
        barraSuperior.setBounds(0, 0, 1280, 80);
        barraSuperior.setLayout(null);
        painelPretoFundo.add(barraSuperior);

        JLabel titulo = new JLabel("Finanças");
        titulo.setForeground(Color.WHITE);
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
            logoGaragem.setForeground(Color.WHITE);
        }
        barraSuperior.add(logoGaragem);

        JLabel lblPeriodo = new JLabel("Período:");
        lblPeriodo.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblPeriodo.setForeground(Color.BLACK);
        lblPeriodo.setBounds(92, 110, 90, 30);
        painelPretoFundo.add(lblPeriodo);

        txtDateInicio = new EstilizacaoRedonda.CaixaTextoRedonda("dd/MM/yyyy", Color.GRAY, Color.WHITE, Color.GRAY, 2, 25);
        txtDateInicio.setBounds(176, 106, 174, 40);
        txtDateInicio.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtDateInicio.setHorizontalAlignment(JTextField.CENTER);
        painelPretoFundo.add(txtDateInicio);

        JLabel lblAte = new JLabel("até");
        lblAte.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblAte.setForeground(Color.BLACK);
        lblAte.setBounds(358, 110, 70, 30);
        painelPretoFundo.add(lblAte);

        txtDateFim = new EstilizacaoRedonda.CaixaTextoRedonda("dd/MM/yyyy", Color.GRAY, Color.WHITE, Color.GRAY, 2, 25);
        txtDateFim.setBounds(397, 106, 174, 40);
        txtDateFim.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtDateFim.setHorizontalAlignment(JTextField.CENTER);
        painelPretoFundo.add(txtDateFim);
        
        EstilizacaoRedonda.BotaoRedondo botaoFiltros = new EstilizacaoRedonda.BotaoRedondo("", Color.BLACK, Color.DARK_GRAY, Color.GRAY, 40);
        botaoFiltros.setForeground(Color.WHITE);
        botaoFiltros.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoFiltros.setBounds(583, 106, 70, 60);
        java.net.URL urlIconeFiltro = getClass().getResource("/assets/imagens/iconeFiltro.png"); 
        if (urlIconeFiltro != null) {
            java.awt.Image iconeOriginal = new javax.swing.ImageIcon(urlIconeFiltro).getImage();
            java.awt.Image iconeRedimensionado = iconeOriginal.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
            botaoFiltros.setIcon(new javax.swing.ImageIcon(iconeRedimensionado));
            botaoFiltros.setIconTextGap(10); 
        } else {
            System.out.println("Ícone do botão filtro não encontrado!");
        }
        painelPretoFundo.add(botaoFiltros);

        EstilizacaoRedonda.BotaoRedondo botaoGerarPDF = new EstilizacaoRedonda.BotaoRedondo("GERAR PDF", Color.BLACK, Color.DARK_GRAY, Color.GRAY, 40);
        botaoGerarPDF.setForeground(Color.WHITE);
        botaoGerarPDF.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoGerarPDF.setBounds(837, 100, 282, 50);
        painelPretoFundo.add(botaoGerarPDF);
        botaoGerarPDF.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        painelGrafico1 = new EstilizacaoRedonda.PainelRedondo(null, 30, 4, Color.WHITE, Color.BLACK);
        painelGrafico1.setBounds(699, 211, 420, 317);
        painelPretoFundo.add(painelGrafico1);
        
        painelGrafico2 = new EstilizacaoRedonda.PainelRedondo(null, 30, 4, Color.WHITE, Color.BLACK);
        painelGrafico2.setBounds(186, 460, 420, 216);
        painelPretoFundo.add(painelGrafico2);
        
        painelGrafico3 = new EstilizacaoRedonda.PainelRedondo(null, 30, 4, Color.WHITE, Color.BLACK);
        painelGrafico3.setBounds(186, 211, 420, 228);
        painelPretoFundo.add(painelGrafico3);

        EstilizacaoRedonda.BotaoRedondo botaoVoltar = new EstilizacaoRedonda.BotaoRedondo("", Color.BLACK, Color.DARK_GRAY, Color.GRAY, 40);
        botaoVoltar.setForeground(Color.WHITE);
        botaoVoltar.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoVoltar.setBounds(1130, 600, 90, 50);
        java.net.URL urlIconeSair = getClass().getResource("/assets/imagens/iconVoltar.png"); 
        if (urlIconeSair != null) {
            java.awt.Image iconeOriginal = new javax.swing.ImageIcon(urlIconeSair).getImage();
            java.awt.Image iconeRedimensionado = iconeOriginal.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
            botaoVoltar.setIcon(new javax.swing.ImageIcon(iconeRedimensionado));
            botaoVoltar.setIconTextGap(10); 
        } else {
            System.out.println("Ícone do botão sair não encontrado!");
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

    private void aplicarFiltroDatas() {
        String strInicio = txtDateInicio.getText().trim();
        String strFim = txtDateFim.getText().trim();
        if (strInicio.isEmpty() || strInicio.equals("dd/MM/yyyy") || 
            strFim.isEmpty() || strFim.equals("dd/MM/yyyy")) {
            JOptionPane.showMessageDialog(
                    this,
                    "Preencha as duas datas (início e fim).",
                    "Período incompleto",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            LocalDate dataInicio = LocalDate.parse(strInicio, formatter);
            LocalDate dataFim = LocalDate.parse(strFim, formatter);

            if (dataFim.isBefore(dataInicio)) {
                JOptionPane.showMessageDialog(
                        this,
                        "A data final não pode ser menor que a data inicial.",
                        "Período inválido",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            // Se tudo estiver ok, aplica o filtro
            JOptionPane.showMessageDialog(this, "Filtro aplicado com sucesso!\nBuscando dados de " + strInicio + " até " + strFim, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            
            // Aqui chamaria o método para atualizar os gráficos
            // carregarGraficos(dataInicio, dataFim);
            
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Formato de data inválido. Use dd/MM/yyyy (Ex: 25/12/2023).",
                    "Erro de Formatação",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}