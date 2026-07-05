package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import entities.enums.Cargo;
import entities.Funcionario;
import exceptions.DatabaseException;
import utils.ConectorBD;

public class FuncionarioDAO implements GenericDAO<Funcionario, String> {

    @Override
    public void criar(Funcionario funcionario) throws DatabaseException {
        String query = "INSERT INTO funcionario (nome, cpf, cargo, telefone, email, data_admissao, senha_hash) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?);";

        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query)){

            ps.setString(1, funcionario.getNome());
            ps.setString(2, funcionario.getCpf());
            ps.setString(3, Cargo.cargoToString(funcionario.getCargo()));
            ps.setString(4, funcionario.getTelefone());
            ps.setString(5, funcionario.getEmail());
            ps.setString(6, String.valueOf(funcionario.getDataAdmissao()));
            ps.setString(7, funcionario.getSenhaHash());

            ps.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
            throw new DatabaseException("Erro ao cadastrar funcionário no banco de dados", e);
        }
    }

    @Override
    public List<Funcionario> buscarTodos() throws DatabaseException {
        String query = "SELECT * FROM funcionario;";
        List<Funcionario> funcionarios = new ArrayList<>();

        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query)){

            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    Funcionario funcionario = mapearFuncionario(rs);
                    funcionarios.add(funcionario);
                }
            }

            return funcionarios;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Erro ao listar funcionários registrados no banco de dados", e);
        }
    }

    @Override
    public Optional<Funcionario> buscarPorAtributoIdentificador(String email) throws DatabaseException {
        String sql = "SELECT * FROM funcionario WHERE email = ?";

        try (Connection conn = ConectorBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()) return Optional.of(mapearFuncionario(rs));
            }
        } catch(SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Erro ao buscar funcionário no banco de dados", e);
        }
        return Optional.empty();
    }

    @Override
    public void atualizar(Funcionario funcionario, String cpfAntigo) throws DatabaseException {
        String query = "UPDATE funcionario SET nome = ?, cpf = ?, cargo = ?, telefone = ?, email = ? WHERE cpf = ?;";

        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query)){

            ps.setString(1, funcionario.getNome());
            ps.setString(2, funcionario.getCpf());
            ps.setString(3, Cargo.cargoToString(funcionario.getCargo()));
            ps.setString(4, funcionario.getTelefone());
            ps.setString(5, funcionario.getEmail());
            ps.setString(6, cpfAntigo);

            ps.executeUpdate();

        } catch(SQLException e){
            e.printStackTrace();
            throw new DatabaseException("Erro ao atualizar funcionário no banco de dados", e);
        }
    }

    @Override
    public void deletar(String cpf) throws DatabaseException {
        String query = "DELETE FROM funcionario WHERE cpf = ?;";

        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query)){

            ps.setString(1, cpf);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Erro ao deletar funcionario no banco de dados", e);
        }
    }
    
    public void atualizarSenha(String email, String novaSenhaHash) throws DatabaseException{
        String sql = "UPDATE funcionario SET senha_hash = ? WHERE email = ?";

        try (Connection conn = ConectorBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setString(1, novaSenhaHash);
            stmt.setString(2, email);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Erro ao atualizar senha do funcionário no banco de dados", e);
        }
    }

    private Funcionario mapearFuncionario(ResultSet rs) throws SQLException{
        return new Funcionario(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("cpf"),
                Cargo.fromString(rs.getString("cargo")),
                rs.getString("telefone"),
                rs.getString("email"),
                rs.getDate("data_admissao"),
                rs.getString("senha_hash")
        );
    }
}