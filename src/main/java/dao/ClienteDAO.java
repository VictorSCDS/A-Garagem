package dao;

import entities.Cliente;
import entities.Funcionario;
import exceptions.DatabaseException;
import utils.ConectorBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import java.util.List;

public class ClienteDAO implements GenericDAO<Cliente, String> {

    @Override
    public void criar(Cliente cliente) throws DatabaseException {
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
            throw new DatabaseException("Erro ao cadastrar cliente no banco de dados", e);
        }
    }

    @Override
    public List<Cliente> buscarTodos() throws DatabaseException {
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
            throw new DatabaseException("Erro ao listar clientes registrados no banco de dados", e);
        }
    }

    @Override
    public Optional<Cliente> buscarPorAtributoIdentificador(String cpf) throws DatabaseException {
        String query = "SELECT * FROM cliente WHERE cpf = ?";

        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query)){

            ps.setString(1, cpf);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()) return Optional.of(mapearCliente(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Erro ao buscar cliente no banco de dados", e);
        }

        return Optional.empty();
    }

    @Override
    public void atualizar(Cliente cliente, String cpf) throws DatabaseException {
        String query = "UPDATE cliente SET nome = ?, cpf = ?, telefone = ?, email = ? WHERE cpf = ?;";

        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query)){

            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getCpf());
            ps.setString(3, cliente.getTelefone());
            ps.setString(4, cliente.getEmail());
            ps.setString(5, cpf);

            ps.executeUpdate();

        } catch(SQLException e){
            e.printStackTrace();
            throw new DatabaseException("Erro ao atualizar cliente no banco de dados", e);
        }
    }

    @Override
    public void deletar(String cpf) throws DatabaseException {
        String selectVeiculos = "SELECT cv.id_veiculo FROM cliente_veiculo cv INNER JOIN cliente c ON cv.id_cliente = c.id WHERE c.cpf = ?;";
        String deleteVinculos = "DELETE FROM cliente_veiculo WHERE id_cliente = (SELECT id FROM cliente WHERE cpf = ?);";
        String deleteVeiculo = "DELETE FROM veiculo WHERE id = ?;";
        String deleteCliente = "DELETE FROM cliente WHERE cpf = ?;";

        try (Connection con = ConectorBD.conectar()) {
            con.setAutoCommit(false);
            try {
                List<Integer> idsVeiculos = new ArrayList<>();
                try (PreparedStatement psSelect = con.prepareStatement(selectVeiculos)) {
                    psSelect.setString(1, cpf);
                    try (ResultSet rs = psSelect.executeQuery()) {
                        while (rs.next()) {
                            idsVeiculos.add(rs.getInt("id_veiculo"));
                        }
                    }
                }
                try (PreparedStatement psVinculos = con.prepareStatement(deleteVinculos)) {
                    psVinculos.setString(1, cpf);
                    psVinculos.executeUpdate();
                }
                if (!idsVeiculos.isEmpty()) {
                    try (PreparedStatement psVeiculo = con.prepareStatement(deleteVeiculo)) {
                        for (Integer idVeiculo : idsVeiculos) {
                            psVeiculo.setInt(1, idVeiculo);
                            psVeiculo.addBatch();
                        }
                        psVeiculo.executeBatch();
                    }
                }
                try (PreparedStatement psCliente = con.prepareStatement(deleteCliente)) {
                    psCliente.setString(1, cpf);
                    psCliente.executeUpdate();
                }
                con.commit();

            } catch (SQLException e) {
                con.rollback();
                throw e; 
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Erro ao deletar o cliente e seus respectivos veículos no banco de dados.", e);
        }
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
            throw new DatabaseException("Erro ao buscar cliente no banco de dados", e);
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
            throw new DatabaseException("Erro ao buscar cliente no banco de dados", e);
        }
        return Optional.empty();
    }

    public Optional<Cliente> buscarClientePorEmail(String email) throws DatabaseException {
        String query = "SELECT * FROM cliente WHERE email = ?";

        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query)){

            ps.setString(1, email);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()) return Optional.of(mapearCliente(rs));
            }

        } catch (SQLException e){
            e.printStackTrace();
            throw new DatabaseException("Erro ao buscar cliente no banco de dados", e);
        }
        return Optional.empty();
    }
    
    public Optional<Cliente> buscarClientePorTelefone(String telefone) throws DatabaseException {
        String query = "SELECT * FROM cliente WHERE telefone = ?";

        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query)){

            ps.setString(1, telefone);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()) return Optional.of(mapearCliente(rs));
            }

        } catch (SQLException e){
            e.printStackTrace();
            throw new DatabaseException("Erro ao buscar cliente no banco de dados", e);
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
