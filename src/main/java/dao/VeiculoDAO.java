package dao;

import entities.Veiculo;
import exceptions.DatabaseException;
import utils.ConectorBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VeiculoDAO implements GenericDAO<Veiculo, String> {

    @Override
    public void criar(Veiculo veiculo) throws DatabaseException {
        String query = "INSERT INTO veiculo (placa, marca, modelo) VALUES (?, ?, ?);";

        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query)){

            ps.setString(1, veiculo.getPlaca());
            ps.setString(2, veiculo.getMarca());
            ps.setString(3, veiculo.getModelo());

            ps.executeUpdate();

        } catch(SQLException e){
            e.printStackTrace();
            throw new DatabaseException("Erro ao cadastrar veículo no banco de dados", e);
        }
    }

    @Override
    public List<Veiculo> buscarTodos() throws DatabaseException {
        throw new UnsupportedOperationException("Método inválido!");
    }

    @Override
    public Optional<Veiculo> buscarPorAtributoIdentificador(String placa) throws DatabaseException {
        String query = "SELECT * FROM veiculo WHERE placa = ?";

        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query)){

            ps.setString(1, placa);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()) return Optional.of(mapearVeiculo(rs));
            }

        } catch(SQLException e){
            e.printStackTrace();
            throw new DatabaseException("Erro ao buscar veículo no banco de dados", e);
        }

        return Optional.empty();
    }

    @Override
    public void atualizar(Veiculo veiculo, String placaAntiga) throws DatabaseException {
        String query = "UPDATE veiculo SET placa = ?, marca = ?, modelo = ? WHERE placa = ?;";

        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query)){

            ps.setString(1, veiculo.getPlaca());
            ps.setString(2, veiculo.getMarca());
            ps.setString(3, veiculo.getModelo());
            ps.setString(4, placaAntiga);

            ps.executeUpdate();

        } catch(SQLException e){
            e.printStackTrace();
            throw new DatabaseException("Erro ao atualizar veículo registrado no banco de dados", e);
        }
    }

    @Override
    public void deletar(String placa) throws DatabaseException {
        String query = "DELETE FROM veiculo WHERE placa = ?";

        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query)){

            ps.setString(1, placa);
            ps.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
            throw new DatabaseException("Erro ao deletar veículo registrado no banco de dados", e);
        }
    }

    public List<Veiculo> buscarTodosVeiculosDeCliente(String cpf) throws DatabaseException{
        String query = "SELECT veiculo.* FROM veiculo " +
                "INNER JOIN cliente_veiculo ON veiculo.id = cliente_veiculo.id_veiculo " +
                "INNER JOIN cliente ON cliente_veiculo.id_cliente = cliente.id " +
                "WHERE cliente.cpf = ?";
        List<Veiculo> veiculos = new ArrayList<>();

        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query)){

            ps.setString(1, cpf);

            try(ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Veiculo c = mapearVeiculo(rs);
                    veiculos.add(c);
                }
            }

            return veiculos;

        }catch(SQLException e){
            e.printStackTrace();
            throw new DatabaseException("Erro ao buscar veículos no banco de dados", e);
        }
    }

    public String buscarPlacaPorIdVeiculo(int idVeiculo) throws DatabaseException {
        String query = "SELECT placa FROM veiculo WHERE id = ?";
        try (Connection con = ConectorBD.conectar();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, idVeiculo);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getString("placa");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Erro ao buscar placa do veículo");
        }
        return "Não encontrada";
    }

    private Veiculo mapearVeiculo(ResultSet rs) throws SQLException{
        return new Veiculo(
                rs.getInt("id"),
                rs.getString("placa"),
                rs.getString("marca"),
                rs.getString("modelo")
        );
    }
}
