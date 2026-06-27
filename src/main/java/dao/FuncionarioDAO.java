package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import entities.Cargo;
import entities.Funcionario;
import exceptions.DatabaseException;
import utils.ConectorBD;

import javax.xml.crypto.Data;

public class FuncionarioDAO {

    public void cadastrarFuncionario(Funcionario funcionario) throws DatabaseException{
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
            throw new DatabaseException("Erro no banco de dados: " + e);
        }

    }

    public Optional<Funcionario> buscarPorEmail(String email) throws DatabaseException {
        String sql = "SELECT * FROM funcionario WHERE email = ?";

        try (Connection conn = ConectorBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()) {
                    Funcionario funcionario = new Funcionario(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("cpf"),
                            Cargo.fromString(rs.getString("cargo")),
                            rs.getString("telefone"),
                            rs.getString("email"),
                            Date.valueOf(rs.getString("data_admissao")),
                            rs.getString("senha_hash")
                    );
                    return Optional.of(funcionario);
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Erro no banco de dados: " + e);
        }
        return Optional.empty();
    }
    
    public void atualizarSenha(String email, String senhaHash) throws DatabaseException{
        String sql = "UPDATE funcionario SET senha_hash = ? WHERE email = ?";

        try (Connection conn = ConectorBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setString(1, senhaHash);
            stmt.setString(2, email);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Erro no banco de dados:" + e);
        }
    }

    public void deletarFuncionario(String cpf) throws DatabaseException{
        String query = "DELETE FROM funcionario WHERE cpf = ?;";

        try(Connection con = ConectorBD.conectar();
        PreparedStatement ps = con.prepareStatement(query)){

            ps.setString(1, cpf);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Erro no banco de dados:" + e);
        }

    }

    public List<Funcionario> listarFuncionarios() throws DatabaseException{
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
            throw new DatabaseException("Erro no banco de dados:" + e);
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