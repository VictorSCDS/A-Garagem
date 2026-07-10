package entities;

import java.math.BigDecimal;

public class ResumoFinanceiro {
    private BigDecimal faturamentoBruto;
    private BigDecimal custoPecas;
    private BigDecimal custoMaoObra;
    private BigDecimal lucroLiquido;

    public ResumoFinanceiro(BigDecimal faturamentoBruto, BigDecimal custoPecas, BigDecimal custoMaoObra, BigDecimal lucroLiquido) {
        this.faturamentoBruto = faturamentoBruto;
        this.custoPecas = custoPecas;
        this.custoMaoObra = custoMaoObra;
        this.lucroLiquido = lucroLiquido;
    }

    public BigDecimal getFaturamentoBruto() { return faturamentoBruto; }
    public BigDecimal getCustoPecas() { return custoPecas; }
    public BigDecimal getCustoMaoObra() { return custoMaoObra; }
    public BigDecimal getLucroLiquido() { return lucroLiquido; }
}