package controllers;

import Services.FuncionarioService;
import dao.FuncionarioDAO;
import entities.Funcionario;
import entities.enums.Cargo;
import exceptions.ControllerException;
import exceptions.ServiceException;
import utils.Hash;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    public FuncionarioController() {
        this.funcionarioService = new FuncionarioService(new FuncionarioDAO());
    }

    public void cadastrar(String nome,
                           String cpf,
                           String cargo,
                           String telefone,
                           String email,
                           String dataAdmissao,
                           String senhaHash)
            throws ControllerException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate data = LocalDate.parse(dataAdmissao, formatter);
        Funcionario funcionario = new Funcionario(0, nome, cpf, Cargo.fromString(cargo), telefone, email, Date.valueOf(data), senhaHash);

        try {
            funcionarioService.criarfuncionario(funcionario);

        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }

    public void editar(String nome,
                       String cpfNovo,
                       String cargo,
                       String telefone,
                       String email,
                       String cpfAntigo)
            throws ControllerException {

        Funcionario funcionario = new Funcionario(0, nome, cpfNovo, Cargo.fromString(cargo), telefone, email, null, null);

        try {
            funcionarioService.atualizarFuncionario(funcionario, cpfAntigo);

        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }

    public void excluir(String cpf) throws ControllerException {

        try {
            funcionarioService.deletarFuncionario(cpf);

        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }

    public Optional<Funcionario> buscarFuncionario(String email) throws ControllerException {

        try {
            return funcionarioService.buscarFuncionarioPorAtributoIdentificador(email);

        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }

    public Optional<Funcionario> buscarFuncionarioPorCpf(String cpf) throws ControllerException {

        try {
            return funcionarioService.buscarFuncionarioPorCpf(cpf);

        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }

    public List<Funcionario> listarTodos() throws ControllerException {

        try {
            return funcionarioService.buscarTodosFuncionarios();

        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }

    public void alterarSenha(String email, String novaSenha) throws ControllerException {

        try {
            funcionarioService.atualizarSenha(email, novaSenha);

        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }

    public boolean emailExiste(String email) throws ControllerException {

        try {
            return funcionarioService.buscarFuncionarioPorAtributoIdentificador(email).isPresent();

        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }

    public boolean cpfExiste(String cpf) throws ControllerException {

        try {
            return funcionarioService.buscarFuncionarioPorCpf(cpf).isPresent();

        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }
    
    public boolean login(String email, String senha) throws ControllerException {
        try {
            Optional<Funcionario> funcionarioOpt = funcionarioService.buscarFuncionarioPorAtributoIdentificador(email);
            
            if (funcionarioOpt.isPresent()) {
                Funcionario funcionario = funcionarioOpt.get();
                
                String senhaDigitadaHash = Hash.gerarHash(senha);
                
                if (funcionario.getSenhaHash().equals(senhaDigitadaHash)) {
                    return true;
                }
            }
            return false; 

        } catch (ServiceException e) {
            throw new ControllerException("Erro ao processar o login: " + e.getMessage(), e);
        }
    }
}