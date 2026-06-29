package dao;

import entities.Cliente;
import exceptions.DatabaseException;
import utils.ConectorBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import java.util.List;

public class ClienteDAO {

    public void cadastrarCliente(Cliente cliente) throws DatabaseException{
        String query = "INSERT INTO cliente (nome, cpf, telefone, email) VALUES (?, ?, ?, ?);";

        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query)){

            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getCpf());
            ps.setString(3, cliente.getTelefone());
            ps.setString(4, cliente.getEmail());

            ps.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
            throw new DatabaseException("Erro ao cadastrar cliente");
        }
    }

    public List<Cliente> listarClientes() throws DatabaseException{
        String query = "SELECT * FROM cliente;";
        List<Cliente> clientes = new ArrayList<>();

        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query)){

            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()) {
                    Cliente c = mapearCliente(rs);
                    clientes.add(c);
                }
            }

            return clientes;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Erro ao listar clientes");
        }
    }

    public Optional<Cliente> buscarClientePorCpf(String cpf) throws DatabaseException{
        String query = "SELECT * FROM cliente WHERE cpf = ?";

        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query)){

            ps.setString(1, cpf);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()) return Optional.of(mapearCliente(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Erro ao buscar cliente de CPF " + cpf);
        }

        return Optional.empty();
    }

    public List<Cliente> buscarClientePorNome(String nome) throws DatabaseException {
        String query = "SELECT * FROM cliente WHERE nome LIKE ?";
        List<Cliente> clientes = new ArrayList<>();

        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query)){

            ps.setString(1, "%" + nome + "%");

            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()) clientes.add(mapearCliente(rs));
            }

            return clientes;

        } catch (SQLException e){
            e.printStackTrace();
            throw new DatabaseException("Erro ao buscar cliente de nome " + nome);
        }
    }

    public Optional<Cliente> buscarClientePorPlaca(String placa) throws DatabaseException {
        String query = "SELECT cliente.* FROM cliente " +
                "INNER JOIN cliente_veiculo ON cliente.id = cliente_veiculo.id_cliente " +
                "INNER JOIN veiculo ON cliente_veiculo.id_veiculo = veiculo.id " +
                "WHERE veiculo.placa = ?";

        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query)){

            ps.setString(1, placa);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()) return Optional.of(mapearCliente(rs));
            }

        } catch (SQLException e){
            e.printStackTrace();
            throw new DatabaseException("Erro ao buscar cliente pela placa " + placa);
        }

        return Optional.empty();
    }

    public void atualizarCliente(Cliente cliente) throws DatabaseException{
        String query = "UPDATE cliente SET nome = ?, cpf = ?, telefone = ?, email = ? WHERE cpf = ?;";

        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query)){

            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getCpf());
            ps.setString(3, cliente.getTelefone());
            ps.setString(4, cliente.getEmail());
            ps.setString(5, cliente.getCpf());

            ps.executeUpdate();

        } catch(SQLException e){
            e.printStackTrace();
            throw new DatabaseException("Erro ao atualizar cliente de CPF " + cliente.getCpf());
        }

    }

    public void deletarCliente(String cpf) throws DatabaseException{
        String query = "DELETE FROM cliente WHERE cpf = ?;";

        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query)){

            ps.setString(1, cpf);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Erro ao deletar cliente de CPF " + cpf);
        }
    }

    private Cliente mapearCliente(ResultSet rs) throws SQLException{
        return new Cliente(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("cpf"),
                rs.getString("telefone"),
                rs.getString("email")
        );
    }
}
