package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Usuario;
import utils.ConectorBD;

public class UsuarioDAO {

    public Usuario buscarPorEmail(String email) {

        try {
            Connection conn = ConectorBD.conectar();
            String sql = "SELECT * FROM funcionario WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                Usuario usuario = new Usuario();

                usuario.setId(rs.getInt("id"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenhaHash(rs.getString("senha_hash"));
                return usuario;
            }

        } 
        catch(Exception e) {
           e.printStackTrace();
        }

        return null;
    }
    
    public void atualizarSenha(String email, String senhaHash) {

        try {

            Connection conn = ConectorBD.conectar();
            String sql = "UPDATE funcionario " + "SET senha_hash = ? " + "WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, senhaHash);
            stmt.setString(2, email);
            stmt.executeUpdate();

        } 
        catch(Exception e) {
            e.printStackTrace();
        }
    }
   
}