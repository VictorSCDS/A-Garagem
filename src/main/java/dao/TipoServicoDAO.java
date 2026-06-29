package dao;

import entities.TipoServico;
import exceptions.DatabaseException;
import utils.ConectorBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TipoServicoDAO {

    public int registrarTipoServico(String tipoServico) throws DatabaseException{
        String query = "INSERT INTO tipo_servico (descricao) VALUES (?);";

        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)){

            ps.setString(1, tipoServico);
            ps.executeUpdate();

            try(ResultSet rs = ps.getGeneratedKeys()){
                if (rs.next()) return rs.getInt(1);
            }

            throw new DatabaseException("Erro ao recuperar ID do tipo de serviço");

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Erro ao cadastrar tipo de serviço.");
        }
    }

    public List<TipoServico> buscarTipoServicoEmOrdemServicoPorId(int id) throws DatabaseException {
        String query = "SELECT tipo_servico.* FROM tipo_servico " +
                "INNER JOIN servico_aplicado ON tipo_servico.id = servico_aplicado.id_tipo_servico " +
                "WHERE servico_aplicado.id_ordem_servico = ?";
        List<TipoServico> tipoServicos = new ArrayList<>();

        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query)){

            ps.setInt(1, id);

            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()) tipoServicos.add(mapearTipoServico(rs));
            }

            return tipoServicos;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Erro ao buscar tipos de serviço.");
        }
    }

    private TipoServico mapearTipoServico(ResultSet rs) throws SQLException {
        return new TipoServico(
                rs.getInt("id"),
                rs.getString("descricao")
        );
    }

}
