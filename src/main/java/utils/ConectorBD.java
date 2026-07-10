package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import exceptions.DatabaseException;
import io.github.cdimascio.dotenv.Dotenv;

public  class ConectorBD {
	private static final Dotenv ENV = Dotenv.configure().directory("./").filename(".env").ignoreIfMissing().load();
	private static final String NOME_BD = ENV.get("NOME_BD");
	private static final String USUARIO_BD = ENV.get("USUARIO_BD");
	private static final String SENHA_BD = ENV.get("SENHA_BD");
	private static final int LOCALHOST_BD = Integer.valueOf(ENV.get("LOCALHOST_BD"));
	private static final String URL_BD = "jdbc:mysql://localhost:" + LOCALHOST_BD + "/" + NOME_BD;
	
	static {
	    System.out.println("NOME_BD = " + NOME_BD);
	    System.out.println("USUARIO_BD = " + USUARIO_BD);
	    System.out.println("SENHA_BD = " + SENHA_BD);
	    System.out.println("LOCALHOST_BD = " + LOCALHOST_BD);
	}
	
	private static Connection conn;
	
	private ConectorBD() {}
	
	public static Connection conectar(){
		try {
			if(conn == null || conn.isClosed()) conn = DriverManager.getConnection(URL_BD, USUARIO_BD, SENHA_BD);
			return conn;

		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}