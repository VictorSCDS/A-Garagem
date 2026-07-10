package main;

import java.awt.EventQueue;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import controllers.FuncionarioController;
import entities.Funcionario;
import entities.enums.Cargo;
import io.github.cdimascio.dotenv.Dotenv;
import utils.Hash;
import view.TelaLogin;

public class Main {
	private static final Dotenv DOTENV = Dotenv.load();
	private static final String SENHA_ADMIN = DOTENV.get("SENHA_ADMIN");
	private static final FuncionarioController FUNCIONARIO_CONTROLLER = new FuncionarioController();
	private static List<Funcionario> funcionarios;
	private static String dataAtual = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					funcionarios = FUNCIONARIO_CONTROLLER.listarTodos();

					if(funcionarios.isEmpty()){
						FUNCIONARIO_CONTROLLER.cadastrar("admin",
								"00000000191",
								"gerente",
								"(00) 000000000",
								"admin.administrador@email.com",
								dataAtual,
								Hash.gerarHash(SENHA_ADMIN));

						System.out.printf("[DEBUG]: TAMANHO DA LISTA: %S\nFUNCIONARIO ADMIN CRIADO\n\n", funcionarios.size());
					}

					System.out.printf("[DEBUG]: TAMANHO DA LISTA: %S\n\n", funcionarios.size());

					TelaLogin frame = new TelaLogin();
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
}
