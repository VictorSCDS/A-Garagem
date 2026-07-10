package dao;

import entities.ResumoFinanceiro;
import exceptions.DatabaseException;
import utils.ConectorBD;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FinancasDAO {

    public ResumoFinanceiro buscarResumoPeriodo(Date dataInicio, Date dataFim) throws DatabaseException {
        String queryMaoObra = "SELECT COALESCE(SUM(ts.valor_servico), 0) AS total_servicos " +
                              "FROM ordem_servico os " +
                              "INNER JOIN servico_aplicado sa ON sa.id_ordem_servico = os.id " +
                              "INNER JOIN tipo_servico ts ON ts.id = sa.id_tipo_servico " +
                              "WHERE os.data_registro BETWEEN ? AND ? AND os.estado IN ('PAGO', 'CONCLUIDO');";

        String queryPecas = "SELECT COALESCE(SUM(i.valor_venda), 0) AS faturamento_pecas, " +
                            "COALESCE(SUM(i.valor_compra), 0) AS custo_real_pecas " +
                            "FROM ordem_servico os " +
                            "INNER JOIN item_servico its ON its.id_ordem_servico = os.id " +
                            "INNER JOIN item i ON i.id = its.id_item " +
                            "WHERE os.data_registro BETWEEN ? AND ? AND os.estado IN ('PAGO', 'CONCLUIDO');";

        BigDecimal totalServicos = BigDecimal.ZERO;
        BigDecimal faturamentoPecas = BigDecimal.ZERO;
        BigDecimal custoRealPecas = BigDecimal.ZERO;

        try (Connection con = ConectorBD.conectar()) {
            
            try (PreparedStatement psServico = con.prepareStatement(queryMaoObra)) {
                psServico.setDate(1, dataInicio);
                psServico.setDate(2, dataFim);
                try (ResultSet rs = psServico.executeQuery()) {
                    if (rs.next()) totalServicos = rs.getBigDecimal("total_servicos");
                }
            }

            try (PreparedStatement psPecas = con.prepareStatement(queryPecas)) {
                psPecas.setDate(1, dataInicio);
                psPecas.setDate(2, dataFim);
                try (ResultSet rs = psPecas.executeQuery()) {
                    if (rs.next()) {
                        faturamentoPecas = rs.getBigDecimal("faturamento_pecas");
                        custoRealPecas = rs.getBigDecimal("custo_real_pecas");
                    }
                }
            }

            BigDecimal faturamentoBruto = totalServicos.add(faturamentoPecas);
            BigDecimal lucroLiquido = faturamentoBruto.subtract(custoRealPecas);

            return new ResumoFinanceiro(faturamentoBruto, custoRealPecas, totalServicos, lucroLiquido);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Erro ao processar balanço financeiro no período informado.", e);
        }
    }
}