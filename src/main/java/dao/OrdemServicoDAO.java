package dao;

import entities.enums.Estado;
import entities.OrdemServico;
import exceptions.DatabaseException;
import utils.ConectorBD;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrdemServicoDAO implements GenericDAO<OrdemServico, Integer> {

    @Override
    public void criar(OrdemServico ordemServico) throws DatabaseException {
        throw new UnsupportedOperationException("Método inválido para este caso! Utilize cadastrarOrdemServico(...).");
    }

    @Override
    public List<OrdemServico> buscarTodos() throws DatabaseException {
        String query = "SELECT * FROM ordem_servico;";
        List<OrdemServico> ordemServicos = new ArrayList<>();

        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery()){

            while(rs.next()) ordemServicos.add(mapearOrdemServico(rs));

            return ordemServicos;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Erro ao buscar ordens de serviço");
        }
    }

    @Override
    public Optional<OrdemServico> buscarPorAtributoIdentificador(Integer id) throws DatabaseException {
        String query = "SELECT * FROM ordem_servico WHERE id = ?";

        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query)){

            ps.setInt(1, id);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()) return Optional.of(mapearOrdemServico(rs));
            }

            return Optional.empty();

        } catch(SQLException e){
            e.printStackTrace();
            throw new DatabaseException("Erro ao buscar ordem de serviço");
        }
    }

    @Override
    public void atualizar(OrdemServico ordemServico, Integer identificador) throws DatabaseException {
        throw new UnsupportedOperationException("Método inválido para este caso! Utilize cancelarOrdemServico(...) ou cancelarOrdemServico(...).");
    }

    @Override
    public void deletar(Integer id) throws DatabaseException {
        String query = "DELETE FROM ordem_servico WHERE id = ?;";

        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query)){

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Erro ao deletar ordem de serviço");
        }
    }

    public void cadastrarOrdemServico(OrdemServico ordemServico, int idVeiculo, int idFuncionario)
            throws DatabaseException{
        String query = "INSERT INTO ordem_servico " +
                "(problema, estado, descricao, data_registro, id_veiculo, id_funcionario_responsavel) " +
                "VALUES (?, ?, ?, ?, ?, ?);";

        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query)){

            ps.setString(1, ordemServico.getProblema());
            ps.setString(2, Estado.estadoToString(ordemServico.getEstado()));
            ps.setString(3, ordemServico.getDescricao());
            ps.setDate(4, ordemServico.getDataRegistro());
            ps.setInt(5, idVeiculo);
            ps.setInt(6, idFuncionario);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Erro ao cadastrar ordem de serviço");
        }

    }

    public List<OrdemServico> buscarHistoricoVeiculo(int idVeiculo) throws DatabaseException{
        String query = "SELECT * FROM ordem_servico WHERE id_veiculo = ?";
        List<OrdemServico> ordemServicos = new ArrayList<>();

        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query)){

            ps.setInt(1, idVeiculo);

            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()) ordemServicos.add(mapearOrdemServico(rs));
            }

            return ordemServicos;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Erro ao buscar ordens de serviço");
        }
    }

    public Optional<BigDecimal> buscarCustoAtualServico(int idOrdemServico) throws DatabaseException {
        String query = "SELECT \n" +
                "200.00 +  COALESCE(SUM(i.valor_compra * is.quantidade), 0) AS custo_atual\n" +
                "FROM ordem_servico os\n" +
                "LEFT JOIN item_servico is ON is.id_ordem_servico = os.id\n" +
                "LEFT JOIN item i ON i.id = is.id_item\n" +
                "WHERE os.id = ?";

        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query)){

            ps.setInt(1, idOrdemServico);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()) return Optional.of(rs.getBigDecimal("custo_atual"));
            }

            return Optional.empty();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Erro ao buscar custo do serviço");
        }

    }

    public void cancelarOrdemServico(int id) throws DatabaseException {
        String query = "UPDATE ordem_servico SET estado = ? WHERE id = ?;";

        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query)){

            ps.setString(1, Estado.estadoToString(Estado.CANCELADO));
            ps.setInt(2, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Erro ao cancelar ordem de serviço");
        }
    }

    public void editarOrdemServico(OrdemServico ordemServico, List<Integer> idsItensPecas, List<Integer> idsTiposServico)
            throws DatabaseException{
        String queryOrdemServico = "UPDATE ordem_servico SET problema = ?, estado = ?, descricao = ? WHERE id = ?";
        String queryDeleteItemServico = "DELETE FROM item_servico WHERE id_ordem_servico = ?";
        String queryInsertItemServico = "INSERT INTO item_servico (id_ordem_servico, id_item) VALUES (?, ?)";
        String queryDeleteServicoAplicado = "DELETE FROM servico_aplicado WHERE id_ordem_servico = ?";
        String queryInsertServicoAplicado = "INSERT INTO servico_aplicado (id_ordem_servico, id_tipo_servico) VALUES (?, ?)";

        try(Connection con = ConectorBD.conectar();
            PreparedStatement psOrdemServico = con.prepareStatement(queryOrdemServico);
            PreparedStatement psDeleteItemServico = con.prepareStatement(queryDeleteItemServico);
            PreparedStatement psInsertItemServico = con.prepareStatement(queryInsertItemServico);
            PreparedStatement psDeleteServicoAplicado = con.prepareStatement(queryDeleteServicoAplicado);
            PreparedStatement psInsertServicoAplicado = con.prepareStatement(queryInsertServicoAplicado)){

            psOrdemServico.setString(1, ordemServico.getProblema());
            psOrdemServico.setString(2, Estado.estadoToString(ordemServico.getEstado()));
            psOrdemServico.setString(3, ordemServico.getDescricao());
            psOrdemServico.setInt(4, ordemServico.getId());

            psDeleteItemServico.setInt(1, ordemServico.getId());

            psOrdemServico.executeUpdate();
            psDeleteItemServico.executeUpdate();

            for(Integer idItem : idsItensPecas){
                psInsertItemServico.setInt(1, ordemServico.getId());
                psInsertItemServico.setInt(2, idItem);
                psInsertItemServico.executeUpdate();
            }

            psDeleteServicoAplicado.setInt(1, ordemServico.getId());
            psDeleteServicoAplicado.executeUpdate();

            for(Integer idTipo : idsTiposServico) {
                psInsertServicoAplicado.setInt(1, ordemServico.getId());
                psInsertServicoAplicado.setInt(2, idTipo);
                psInsertServicoAplicado.executeUpdate();
            }

        } catch (SQLException e){
            e.printStackTrace();
            throw new DatabaseException("Erro ao editar ordem de serviço");
        }

    }

    private OrdemServico mapearOrdemServico(ResultSet rs) throws SQLException {
        return new OrdemServico(
                rs.getInt("id"),
                rs.getString("problema"),
                Estado.fromString(rs.getString("estado")),
                rs.getString("descricao"),
                rs.getDate("data_registro"),
                rs.getInt("id_veiculo"),
                rs.getInt("id_funcionario_responsavel")
        );
    }
}
