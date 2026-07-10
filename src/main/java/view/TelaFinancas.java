package view;

import utils.Constantes;
import utils.PermissaoAcesso;

import controllers.FinancasController;
import entities.ResumoFinanceiro;
import exceptions.ControllerException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TelaFinancas extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel painelPretoFundo;
    
    private JPanel painelGrafico1;
    private JPanel painelGrafico2;
    private JPanel painelGrafico3;
    
    private EstilizacaoRedonda.CaixaTextoRedonda txtDateInicio;
    private EstilizacaoRedonda.CaixaTextoRedonda txtDateFim;
    
    private FinancasController financasController;
    private ResumoFinanceiro dadosAtuais;

    public TelaFinancas() {
        if (!PermissaoAcesso.autorizar(this, PermissaoAcesso.podeAcessarFinancas(), "Finanças")) {
            javax.swing.SwingUtilities.invokeLater(() -> {
                new TelaHome().setVisible(true);
                dispose();
            });
            return;
        }

        this.financasController = new FinancasController();

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

        JLabel titulo = new JLabel("Finanças");
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
        }
        barraSuperior.add(logoGaragem);

        JLabel lblPeriodo = new JLabel("Período:");
        lblPeriodo.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblPeriodo.setForeground(Constantes.PRETO_FOSCO);
        lblPeriodo.setBounds(92, 110, 90, 30);
        painelPretoFundo.add(lblPeriodo);

        txtDateInicio = new EstilizacaoRedonda.CaixaTextoRedonda("dd/MM/yyyy", Constantes.PRETO_FOSCO, Constantes.CINZA_CLARO, Constantes.PRETO_FOSCO, 2, 25);
        txtDateInicio.setBounds(176, 106, 174, 40);
        txtDateInicio.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtDateInicio.setHorizontalAlignment(JTextField.CENTER);
        txtDateInicio.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if(txtDateInicio.getText().equals("dd/MM/yyyy")) txtDateInicio.setText("");
            }
        });
        painelPretoFundo.add(txtDateInicio);

        JLabel lblAte = new JLabel("até");
        lblAte.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblAte.setForeground(Constantes.PRETO_FOSCO);
        lblAte.setBounds(358, 110, 70, 30);
        painelPretoFundo.add(lblAte);

        txtDateFim = new EstilizacaoRedonda.CaixaTextoRedonda("dd/MM/yyyy", Constantes.PRETO_FOSCO, Constantes.CINZA_CLARO, Constantes.PRETO_FOSCO, 2, 25);
        txtDateFim.setBounds(397, 106, 174, 40);
        txtDateFim.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtDateFim.setHorizontalAlignment(JTextField.CENTER);
        txtDateFim.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if(txtDateFim.getText().equals("dd/MM/yyyy")) txtDateFim.setText("");
            }
        });
        painelPretoFundo.add(txtDateFim);
        
        EstilizacaoRedonda.BotaoRedondo botaoFiltros = new EstilizacaoRedonda.BotaoRedondo("", Constantes.VERMELHO_FERRARI, Constantes.AMARELO_OURO, Constantes.PRETO_FOSCO, 40);
        botaoFiltros.setBounds(583, 106, 70, 60);
        java.net.URL urlIconeFiltro = getClass().getResource("/assets/imagens/iconeFiltro.png"); 
        if (urlIconeFiltro != null) {
            java.awt.Image iconeOriginal = new javax.swing.ImageIcon(urlIconeFiltro).getImage();
            java.awt.Image iconeRedimensionado = iconeOriginal.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
            botaoFiltros.setIcon(new javax.swing.ImageIcon(iconeRedimensionado));
        }
        botaoFiltros.addActionListener(e -> aplicarFiltroDatas());
        painelPretoFundo.add(botaoFiltros);

        EstilizacaoRedonda.BotaoRedondo botaoGerarPDF = new EstilizacaoRedonda.BotaoRedondo("GERAR PDF", Constantes.VERMELHO_FERRARI, Constantes.AMARELO_OURO, Constantes.PRETO_FOSCO, 40);
        botaoGerarPDF.setForeground(Constantes.CINZA_CLARO);
        botaoGerarPDF.setFont(new Font("SansSerif", Font.BOLD, 18));
        botaoGerarPDF.setBounds(837, 100, 282, 50);
        painelPretoFundo.add(botaoGerarPDF);
        botaoGerarPDF.addActionListener(e -> {
            try {
                JOptionPane.showMessageDialog(this, "Gerando relatório... Clique em OK e aguarde.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                String caminhoArquivo = utils.GeradorPDF.gerarRelatorioFinanceiro();
                JOptionPane.showMessageDialog(this, 
                    "Relatório Financeiro exportado com sucesso!\n\nSalvo em:\n" + caminhoArquivo, 
                    "PDF Gerado", 
                    JOptionPane.INFORMATION_MESSAGE);
                    
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Erro ao gerar o relatório PDF: " + ex.getMessage(), 
                    "Erro na Exportação", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });


        painelGrafico1 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2.setColor(Constantes.CINZA_CLARO);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.setColor(Constantes.PRETO_FOSCO);
                g2.setStroke(new java.awt.BasicStroke(3));
                g2.drawRoundRect(2, 2, getWidth()-5, getHeight()-5, 30, 30);

                g2.setFont(new Font("SansSerif", Font.BOLD, 22));
                g2.drawString("Resumo do Fluxo", 30, 40);

                g2.setFont(new Font("SansSerif", Font.PLAIN, 16));
                g2.drawString("Faturamento Bruto:", 30, 90);
                g2.drawString("Custos de Peças:", 30, 150);
                g2.drawString("Mão de Obra:", 30, 210);

                g2.setFont(new Font("SansSerif", Font.BOLD, 16));
                g2.setColor(Constantes.AMARELO_OURO); // Verde
                String fat = dadosAtuais != null ? "R$ " + formatarMoeda(dadosAtuais.getFaturamentoBruto()) : "R$ 0,00";
                g2.drawString(fat, 230, 90);

                g2.setColor(Constantes.VERMELHO_FERRARI);
                String cst = dadosAtuais != null ? "R$ " + formatarMoeda(dadosAtuais.getCustoPecas()) : "R$ 0,00";
                g2.drawString(cst, 230, 150);

                g2.setColor(Constantes.AMARELO_OURO);
                String mObra = dadosAtuais != null ? "R$ " + formatarMoeda(dadosAtuais.getCustoMaoObra()) : "R$ 0,00";
                g2.drawString(mObra, 230, 210);
            }
        };
        painelGrafico1.setBounds(699, 211, 420, 317);
        painelGrafico1.setOpaque(false);
        painelPretoFundo.add(painelGrafico1);
        
        painelGrafico2 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2.setColor(Constantes.CINZA_CLARO);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.setColor(Constantes.PRETO_FOSCO);
                g2.drawRoundRect(2, 2, getWidth()-5, getHeight()-5, 30, 30);

                g2.setFont(new Font("SansSerif", Font.BOLD, 18));
                g2.drawString("Composição da Receita", 20, 35);

                double total = dadosAtuais != null ? dadosAtuais.getFaturamentoBruto().doubleValue() : 0;
                int anguloPecas = 0;
                int anguloMaoObra = 0;

                if (total > 0) {
                    anguloPecas = (int) ((dadosAtuais.getCustoPecas().doubleValue() / total) * 360);
                    anguloMaoObra = 360 - anguloPecas;
                }

                g2.setColor(Constantes.VERMELHO_FERRARI);
                g2.fillArc(50, 60, 120, 120, 0, anguloPecas == 0 && total == 0 ? 180 : anguloPecas);
                g2.setColor(Constantes.AMARELO_OURO);
                g2.fillArc(50, 60, 120, 120, anguloPecas, anguloPecas == 0 && total == 0 ? 180 : anguloMaoObra);

                g2.setColor(Constantes.CINZA_CLARO);
                g2.fillArc(85, 95, 50, 50, 0, 360);

                g2.setFont(new Font("SansSerif", Font.PLAIN, 14));
                g2.setColor(Constantes.VERMELHO_FERRARI);
                g2.fillRect(200, 80, 15, 15);
                g2.setColor(Constantes.PRETO_FOSCO);
                g2.drawString("Peças", 225, 93);

                g2.setColor(Constantes.AMARELO_OURO);
                g2.fillRect(200, 120, 15, 15);
                g2.setColor(Constantes.PRETO_FOSCO);
                g2.drawString("Mão de Obra", 225, 133);
            }
        };
        painelGrafico2.setBounds(186, 460, 420, 216);
        painelGrafico2.setOpaque(false);
        painelPretoFundo.add(painelGrafico2);
        
        painelGrafico3 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2.setColor(Constantes.CINZA_CLARO);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.setColor(Constantes.PRETO_FOSCO);
                g2.drawRoundRect(2, 2, getWidth()-5, getHeight()-5, 30, 30);

                g2.setFont(new Font("SansSerif", Font.BOLD, 18));
                g2.drawString("Lucro Líquido Real", 20, 35);

                String lucroStr = dadosAtuais != null ? "R$ " + formatarMoeda(dadosAtuais.getLucroLiquido()) : "R$ 0,00";
                g2.setFont(new Font("SansSerif", Font.BOLD, 26));
                g2.setColor(Constantes.AMARELO_OURO);
                g2.drawString(lucroStr, 40, 90);

                g2.setColor(Constantes.CINZA_CLARO);
                g2.fillRect(40, 130, 340, 25);
                
                double faturamento = dadosAtuais != null ? dadosAtuais.getFaturamentoBruto().doubleValue() : 0;
                double lucro = dadosAtuais != null ? dadosAtuais.getLucroLiquido().doubleValue() : 0;
                
                int larguraLucro = 0;
                if (faturamento > 0) {
                    larguraLucro = (int) ((lucro / faturamento) * 340);
                }
                
                g2.setColor(Constantes.AMARELO_OURO);
                g2.fillRect(40, 130, larguraLucro, 25);
                
                g2.setFont(new Font("SansSerif", Font.ITALIC, 13));
                g2.setColor(Constantes.PRETO_FOSCO);
                g2.drawString("Margem de rentabilidade líquida do período", 40, 185);
            }
        };
        painelGrafico3.setBounds(186, 211, 420, 228);
        painelGrafico3.setOpaque(false);
        painelPretoFundo.add(painelGrafico3);

        EstilizacaoRedonda.BotaoRedondo botaoVoltar = new EstilizacaoRedonda.BotaoRedondo("", Constantes.VERMELHO_FERRARI, Constantes.AMARELO_OURO, Constantes.PRETO_FOSCO, 40);
        botaoVoltar.setBounds(1130, 600, 90, 50);
        java.net.URL urlIconeSair = getClass().getResource("/assets/imagens/iconVoltar.png"); 
        if (urlIconeSair != null) {
            java.awt.Image iconeOriginal = new javax.swing.ImageIcon(urlIconeSair).getImage();
            java.awt.Image iconeRedimensionado = iconeOriginal.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
            botaoVoltar.setIcon(new javax.swing.ImageIcon(iconeRedimensionado));
        }
        painelPretoFundo.add(botaoVoltar);
        botaoVoltar.addActionListener(e -> {
             new TelaHome().setVisible(true);
             dispose();
        });
        
        executarBuscaAutomaticaMesAtual();
    }

    private void aplicarFiltroDatas() {
        String strInicio = txtDateInicio.getText().trim();
        String strFim = txtDateFim.getText().trim();
        if (strInicio.isEmpty() || strInicio.equals("dd/MM/yyyy") || 
            strFim.isEmpty() || strFim.equals("dd/MM/yyyy")) {
            JOptionPane.showMessageDialog(this, "Preencha as duas datas (início e fim).", "Período incompleto", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            LocalDate dataInicio = LocalDate.parse(strInicio, formatter);
            LocalDate dataFim = LocalDate.parse(strFim, formatter);

            if (dataFim.isBefore(dataInicio)) {
                JOptionPane.showMessageDialog(this, "A data final não pode ser menor que a data inicial.", "Período inválido", JOptionPane.WARNING_MESSAGE);
                return;
            }

            java.sql.Date sqlInicio = java.sql.Date.valueOf(dataInicio);
            java.sql.Date sqlFim = java.sql.Date.valueOf(dataFim);
            
            dadosAtuais = financasController.consultarResumo(sqlInicio, sqlFim);

            painelGrafico1.repaint();
            painelGrafico2.repaint();
            painelGrafico3.repaint();
            
            JOptionPane.showMessageDialog(this, "Dados financeiros atualizados com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Formato de data inválido. Use dd/MM/yyyy (Ex: 25/12/2026).", "Erro de Formatação", JOptionPane.ERROR_MESSAGE);
        } catch (ControllerException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao processar balanço: " + ex.getMessage(), "Erro no Banco", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void executarBuscaAutomaticaMesAtual() {
        try {
            LocalDate hoje = LocalDate.now();
            LocalDate inicioMes = hoje.withDayOfMonth(1);
            
            txtDateInicio.setText(inicioMes.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            txtDateFim.setText(hoje.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            
            dadosAtuais = financasController.consultarResumo(java.sql.Date.valueOf(inicioMes), java.sql.Date.valueOf(hoje));
        } catch (Exception ignored) {}
    }

    private String formatarMoeda(BigDecimal valor) {
        if (valor == null) valor = BigDecimal.ZERO;
        return valor.setScale(2, RoundingMode.HALF_UP).toString().replace(".", ",");
    }
}