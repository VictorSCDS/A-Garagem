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
import java.util.Optional;

public class TipoServicoDAO implements GenericDAO<TipoServico, Integer> {

    @Override
    public void criar(TipoServico tipoServico) throws DatabaseException {
        throw new UnsupportedOperationException("Método inválido para este caso! Utilize registrarTipoServico(...).");
    }

    @Override
    public List<TipoServico> buscarTodos() throws DatabaseException {
        String query = "SELECT * FROM tipo_servico;";
        List<TipoServico> tipoServicos = new ArrayList<>();

        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery()){

            while(rs.next()) tipoServicos.add(mapearTipoServico(rs));

            return tipoServicos;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Erro ao buscar tipos de serviço no banco de dados", e);
        }
    }

    @Override
    public Optional<TipoServico> buscarPorAtributoIdentificador(Integer id) throws DatabaseException {
        throw new UnsupportedOperationException("Método inválido!");
    }

    public int registrarTipoServico(TipoServico tipoServico) throws DatabaseException {
        String query = "INSERT INTO tipo_servico (descricao, valor_servico) VALUES (?, ?);";
        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)){

            ps.setString(1, tipoServico.getDescricao());
            ps.setBigDecimal(2, tipoServico.getValorServico());
            ps.executeUpdate();

            try(ResultSet rs = ps.getGeneratedKeys()){
                if (rs.next()) return rs.getInt(1);
            }
            throw new DatabaseException("Erro ao recuperar ID do tipo de serviço");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Erro ao cadastrar tipo de serviço no banco de dados", e);
        }
    }

    @Override
    public void atualizar(TipoServico tipoServico, Integer id) throws DatabaseException {
        String query = "UPDATE tipo_servico SET descricao = ?, valor_servico = ? WHERE id = ?;";
        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query)){
            
            ps.setString(1, tipoServico.getDescricao());
            ps.setBigDecimal(2, tipoServico.getValorServico());
            ps.setInt(3, id);
            ps.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
            throw new DatabaseException("Erro ao editar o serviço no banco de dados", e);
        }
    }

    @Override
    public void deletar(Integer id) throws DatabaseException {
        String query = "DELETE FROM tipo_servico WHERE id = ?";
        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query)){
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Erro ao deletar o serviço. Verifique se ele não está vinculado a uma OS existente.", e);
        }
    }

    public List<TipoServico> buscarTiposServicoEmOrdemServicoPorId(int id) throws DatabaseException {
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
            throw new DatabaseException("Erro ao buscar tipos de serviço registrados no banco de dados", e);
        }
    }

    private TipoServico mapearTipoServico(ResultSet rs) throws SQLException {
        return new TipoServico(
                rs.getInt("id"),
                rs.getString("descricao"),
                rs.getBigDecimal("valor_servico")
        );
    }
}
