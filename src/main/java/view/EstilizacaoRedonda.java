package view;

import javax.swing.*;
import javax.swing.JPasswordField;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EstilizacaoRedonda {
    public static class BotaoRedondo extends JButton {
        private final int raioArredondado;
        private final Color corSemInteracao;
        private final Color corMouseInteracao;
        private final Color corPressionada;

        public BotaoRedondo (String label, Color normal, Color mouseI, Color pressionada, int raio){
            super(label);
            this.raioArredondado = raio;
            this.corSemInteracao = normal;
            this.corMouseInteracao = mouseI;
            this.corPressionada = pressionada;

            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setOpaque(false);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    repaint();
                }
                @Override
                public void mouseExited(MouseEvent e){
                    repaint();
                }
                @Override
                public void mousePressed(MouseEvent e){
                    repaint();
                }
                @Override
                public void mouseReleased(MouseEvent e){
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics graficoBasico) {
            Graphics2D graficoAvancado = (Graphics2D) graficoBasico.create();
            graficoAvancado.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

            if (getModel().isPressed()){
                graficoAvancado.setColor(corPressionada);
            }
            else if (getModel().isRollover()){
                graficoAvancado.setColor(corMouseInteracao);
            }
            else{
                graficoAvancado.setColor(corSemInteracao);
            }
            graficoAvancado.fillRoundRect(0,0,getWidth(),getHeight(),raioArredondado,raioArredondado);
            super.paintComponent(graficoBasico);
            graficoAvancado.dispose();
        }
    }
    public static class PainelRedondo extends JPanel {
        private final int raioArredondado;
        private final Color corPreenchimento;
        private final Color corContorno;
        private final int espessuraBorda;

        public PainelRedondo(LayoutManager layout, int raio, int espessura , Color preenchimento, Color contorno){
            super(layout);
            this.raioArredondado = raio;
            this.espessuraBorda = espessura;
            this.corPreenchimento = preenchimento;
            this.corContorno = contorno;

            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics graficoBasico) {
            Graphics2D graficoAvancado = (Graphics2D) graficoBasico.create();
            graficoAvancado.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            graficoAvancado.setColor(corPreenchimento);
            graficoAvancado.fillRoundRect(0,0,getWidth(),getHeight(),raioArredondado,raioArredondado);
            graficoAvancado.setColor(corContorno);
            graficoAvancado.setStroke(new BasicStroke(espessuraBorda));
            graficoAvancado.drawRoundRect(espessuraBorda/2,espessuraBorda/2,getWidth() - espessuraBorda, getHeight()- espessuraBorda, raioArredondado, raioArredondado);
            super.paintComponent(graficoBasico);
            graficoAvancado.dispose();
        }
    }
    public static class CaixaTextoRedonda extends JTextField {
        private final String espacoTexto;
        private final Color espacoCor;
        private final Color corContorno;
        private final int espessuraBorda;
        private final int raioArredondado;
        private final String textoFantasma;

        public CaixaTextoRedonda(String texto, Color contorno, Color fundo, Color corBase, int espessura, int raio) {
            super();
            this.espacoTexto = texto;
            this.corContorno = contorno;
            this.espessuraBorda = espessura;
            this.raioArredondado = raio;
            this.espacoCor = corBase;
            this.textoFantasma = espacoTexto;

            setOpaque(false);
            setBackground(fundo);
            setBorder(BorderFactory.createEmptyBorder(5, raioArredondado, 5, raioArredondado));
            addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (CaixaTextoRedonda.super.getText().equals(espacoTexto)) {
                        setText("");
                        setForeground(Color.BLACK);
                    }
                }
            });

            setText(espacoTexto);
            setForeground(espacoCor);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics graficoBasico) {
            Graphics2D graficoAvancado = (Graphics2D) graficoBasico.create();
            graficoAvancado.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            graficoAvancado.setColor(getBackground());
            graficoAvancado.fillRoundRect(0, 0, getWidth(), getHeight(), raioArredondado, raioArredondado);
            super.paintComponent(graficoBasico);
            graficoAvancado.dispose();
        }
        @Override
        protected void paintBorder(Graphics graficoBasico) {
            Graphics2D graficoAvancado = (Graphics2D) graficoBasico.create();
            graficoAvancado.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            graficoAvancado.setStroke(new BasicStroke(espessuraBorda));
            graficoAvancado.setColor(corContorno);
            graficoAvancado.drawRoundRect(espessuraBorda / 2, espessuraBorda / 2, getWidth() - espessuraBorda, getHeight() - espessuraBorda, raioArredondado, raioArredondado);
            graficoAvancado.dispose();
        }
        @Override
        public String getText() {
            String txt = super.getText();
            return txt.equals(textoFantasma) ? "" : txt;
        }
    }
    public static class CaixaSenhaRedonda extends JPasswordField{
        private final String espacoTextoSenha;
        private final Color espacoCorSenha;
        private final Color corContornoSenha;
        private final int espessuraBordaSenha;
        private final int raioArredondadoSenha;
        private final String senhaFantasma;

        public CaixaSenhaRedonda(String texto, Color contorno, Color fundo, Color corBase, int espessura, int raio) {
            super();
            this.espacoTextoSenha = texto;
            this.corContornoSenha = contorno;
            this.espessuraBordaSenha = espessura;
            this.raioArredondadoSenha = raio;
            this.espacoCorSenha = corBase;
            this.senhaFantasma = espacoTextoSenha;

            setOpaque(false);
            setBackground(fundo);
            setBorder(BorderFactory.createEmptyBorder(5, raioArredondadoSenha, 5, raioArredondadoSenha));

            addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (CaixaSenhaRedonda.super.getText().equals(espacoTextoSenha)) {
                        setText("");
                        setForeground(Color.BLACK);
                    }
                }
            });
            setText(espacoTextoSenha);
            setForeground(espacoCorSenha);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics graficoBasico) {
            Graphics2D graficoAvancado = (Graphics2D) graficoBasico.create();
            graficoAvancado.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            graficoAvancado.setColor(getBackground());
            graficoAvancado.fillRoundRect(0, 0, getWidth(), getHeight(), raioArredondadoSenha, raioArredondadoSenha);
            super.paintComponent(graficoBasico);
            graficoAvancado.dispose();
        }

        @Override
        protected void paintBorder(Graphics graficoBasico) {
            Graphics2D graficoAvancado = (Graphics2D) graficoBasico.create();
            graficoAvancado.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            graficoAvancado.setStroke(new BasicStroke(espessuraBordaSenha));
            graficoAvancado.setColor(corContornoSenha);
            graficoAvancado.drawRoundRect(espessuraBordaSenha / 2, espessuraBordaSenha / 2, getWidth() - espessuraBordaSenha, getHeight() - espessuraBordaSenha, raioArredondadoSenha, raioArredondadoSenha);
            graficoAvancado.dispose();
        }
        @Override
        public char[] getPassword() {
            String txt = new String(super.getPassword());
            return txt.equals(senhaFantasma) ? new char[0] : super.getPassword();
        }
    }
    public static class ComboBoxRedondo<E> extends JComboBox<E> {

        private final int raioArredondado;
        private final Color corFundo;
        private final Color corContorno;
        private final int espessuraBorda;

        public ComboBoxRedondo(E[] itens, Color fundo, Color contorno, int espessura, int raio) {
            super(itens);
            this.corFundo = fundo;
            this.corContorno = contorno;
            this.espessuraBorda = espessura;
            this.raioArredondado = raio;

            setOpaque(false);
            setFocusable(false);

            setBackground(fundo);
            setForeground(Color.BLACK);

            setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

            setUI(new javax.swing.plaf.basic.BasicComboBoxUI() {
                @Override
                protected JButton createArrowButton() {
                    JButton botao = new JButton("▼");
                    botao.setBorder(BorderFactory.createEmptyBorder());
                    botao.setFocusable(false);
                    botao.setContentAreaFilled(false);
                    botao.setForeground(Color.BLACK);
                    return botao;
                }
            });
        }
        @Override
        protected void paintComponent(Graphics graficoBasico) {
            Graphics2D graficoAvancado = (Graphics2D) graficoBasico.create();
            graficoAvancado.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            graficoAvancado.setColor(corFundo);
            graficoAvancado.fillRoundRect(0, 0, getWidth(), getHeight(), raioArredondado, raioArredondado);
            super.paintComponent(graficoBasico);
            graficoAvancado.dispose();
        }

        @Override
        protected void paintBorder(Graphics graficoBasico) {
            Graphics2D graficoAvancado = (Graphics2D) graficoBasico.create();
            graficoAvancado.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            graficoAvancado.setColor(corContorno);
            graficoAvancado.setStroke(new BasicStroke(espessuraBorda));
            graficoAvancado.drawRoundRect(espessuraBorda / 2, espessuraBorda / 2,  getWidth() - espessuraBorda, getHeight() - espessuraBorda, raioArredondado, raioArredondado);
            graficoAvancado.dispose();
        }
    }
}