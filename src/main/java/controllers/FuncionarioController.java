package controllers;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import Services.FuncionarioService;
import dao.FuncionarioDAO;
import entities.Funcionario;
import entities.enums.Cargo;
import exceptions.DatabaseException;
import utils.Hash;

public class FuncionarioController {

    private final FuncionarioDAO funcionarioDAO;
    private final FuncionarioService funcionarioService;

    public FuncionarioController() {
        funcionarioDAO = new FuncionarioDAO();
        funcionarioService = new FuncionarioService(funcionarioDAO);
    }


    public boolean login(String email, String senha)  throws DatabaseException {

        Optional<Funcionario> funcionarioOptional = funcionarioDAO.buscarPorAtributoIdentificador(email);
        
        if (funcionarioOptional.isEmpty()) {
            return false;
        }

        Funcionario funcionario = funcionarioOptional.get();
        String senhaHash = Hash.gerarHash(senha);
        return senhaHash.equals(funcionario.getSenhaHash());
    }

    public void cadastrar(String nome,
                           String cpf,
                           String cargo,
                           String telefone,
                           String email,
                           String dataAdmissao)
            throws DatabaseException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate data = LocalDate.parse(dataAdmissao, formatter);
        Funcionario funcionario = new Funcionario(0, nome, cpf, Cargo.fromString(cargo), telefone, email, Date.valueOf(data), null);

        funcionarioService.validarFuncionario(funcionario);
        funcionarioDAO.criar(funcionario);
    }


    public void editar(String nome,
                       String cpfNovo,
                       String cargo,
                       String telefone,
                       String email,
                       String cpfAntigo)
            throws DatabaseException {

        Funcionario funcionario = new Funcionario(0, nome, cpfNovo, Cargo.fromString(cargo), telefone, email, null, null);
        funcionarioService.validarFuncionario(funcionario);
        funcionarioDAO.atualizar(funcionario, cpfAntigo);
    }

    public void excluir(String cpf) throws DatabaseException {
        funcionarioDAO.deletar(cpf);
    }

    public Optional<Funcionario> buscarFuncionario(String email) throws DatabaseException {
        return funcionarioDAO.buscarPorAtributoIdentificador(email);
    }

    public boolean emailExiste(String email) throws DatabaseException {
        return funcionarioDAO.buscarPorAtributoIdentificador(email).isPresent();
    }

    public void alterarSenha(String email, String novaSenha) throws DatabaseException {
        String senhaHash = Hash.gerarHash(novaSenha);
        funcionarioDAO.atualizarSenha(email, senhaHash);
    }
}