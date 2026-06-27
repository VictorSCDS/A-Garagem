package dao;

import java.sql.*;
import java.util.Optional;

import entities.Cargo;
import entities.Funcionario;
import exceptions.DatabaseException;
import utils.ConectorBD;
import utils.Hash;

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

            ps.execute();

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
    
    public void atualizarSenha(String email, String senhaHash) throws DatabaseException {
        String sql = "UPDATE funcionario SET senha_hash = ? WHERE email = ?";

        try (Connection conn = ConectorBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setString(1, senhaHash);
            stmt.setString(2, email);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Erro no banco de dados: " + e);
        }
    }
   
}