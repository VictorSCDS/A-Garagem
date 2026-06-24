package dao;

import entities.Cliente;
import exceptions.DatabaseException;
import utils.ConectorBD;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import java.util.List;

public class ClienteDAO {

    public static void cadastrarCliente(Cliente cliente) throws DatabaseException{
        String query = "INSERT INTO cliente (nome, cpf, telefone, email) VALUES (?, ?, ?, ?);";

        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query)){

            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getCpf());
            ps.setString(3, cliente.getTelefone());
            ps.setString(4, cliente.getEmail());

            ps.execute();

        } catch (SQLException e){
            throw new DatabaseException("Erro ao conectar com o banco de dados: " + e.getMessage());
        }
    }

    public void deletarCliente(String cpf) throws DatabaseException{
        String query = "DELETE FROM cliente WHERE cpf = ?;";

        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query)){

            ps.setString(1, cpf);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Erro ao conectar com o banco de dados: " + e.getMessage());
        }
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
            throw new DatabaseException("Erro ao conectar com o banco de dados: " + e.getMessage());
        }

    }

    public List<Cliente> listarClientes() throws DatabaseException{
        String query = "SELECT * FROM cliente;";
        List<Cliente> clientes = new ArrayList<>();

        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery()){

            while(rs.next()) {
                Cliente c = mapearCliente(rs);
                clientes.add(c);
            }

            return clientes;

        } catch (SQLException e) {
            throw new DatabaseException("Erro ao conectar com o banco de dados: " + e.getMessage());
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
            throw new DatabaseException("Erro ao conectar com o banco de dados: " + e.getMessage());
        }

        return Optional.empty();
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
