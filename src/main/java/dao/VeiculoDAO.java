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

public class VeiculoDAO {

    public void cadastrarVeiculo(Veiculo veiculo) throws DatabaseException{
        String query = "INSERT INTO veiculo (placa, marca, modelo) VALUES (?, ?, ?);";

        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query)){

            ps.setString(1, veiculo.getPlaca());
            ps.setString(2, veiculo.getMarca());
            ps.setString(3, veiculo.getModelo());

            ps.executeUpdate();

        } catch(SQLException e){
            e.printStackTrace();
            throw new DatabaseException("Erro ao cadastrar veículo");
        }
    }

    public Optional<Veiculo> buscarVeiculoPorPlaca(String placa) throws DatabaseException{
        String query = "SELECT * FROM veiculo WHERE placa = ?";

        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query)){

            ps.setString(1, placa);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()) return Optional.of(mapearVeiculo(rs));
            }

        } catch(SQLException e){
            e.printStackTrace();
            throw new DatabaseException("Erro ao buscar o veículo de placa " + placa);
        }

        return Optional.empty();
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
            throw new DatabaseException("Erro ao buscar veículos do clinte de CPF " + cpf);
        }
    }

    public void deletarVeiculoPorPlaca(String placa) throws DatabaseException{
        String query = "DELETE FROM veiculo WHERE placa = ?";

        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query)){

            ps.setString(1, placa);
            ps.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
            throw new DatabaseException("Erro ao deletar veículo de placa " + placa);
        }
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
