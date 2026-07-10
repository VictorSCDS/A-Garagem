package Services;

import dao.FuncionarioDAO;
import entities.Funcionario;
import exceptions.DatabaseException;
import exceptions.ServiceException;
import utils.DocumentUtils;
import utils.Hash;
import utils.ValidationUtils;

import java.util.List;
import java.util.Optional;

public class FuncionarioService {

    public static final String SENHA_INICIAL_FUNCIONARIO = "__FUNCIONARIO_AGUARDANDO_CADASTRO_DE_SENHA__";

    private FuncionarioDAO funcionarioDao;

    public FuncionarioService(FuncionarioDAO funcionarioDao){this.funcionarioDao = funcionarioDao;}

    public void criarfuncionario(Funcionario funcionario){
        try{
            validarFuncionario(funcionario);

            Optional<Funcionario> funcionarioComCpf = funcionarioDao.buscarFuncionarioPorCpf(funcionario.getCpf());
            if (funcionarioComCpf.isPresent()) {
                throw new ServiceException("Funcionario de cpf " + funcionario.getCpf() + " já cadastrado");
            }

            Optional<Funcionario> funcionarioComEmail = funcionarioDao.buscarPorAtributoIdentificador(funcionario.getEmail());
            if (funcionarioComEmail.isPresent()) {
                throw new ServiceException("E-mail " + funcionario.getEmail() + " já cadastrado");
            }

            funcionarioDao.criar(funcionario);

        } catch(DatabaseException e){
            e.printStackTrace();
            throw new ServiceException("Erro ao cadastrar funcionario", e);
        }
    }

    public List<Funcionario> buscarTodosFuncionarios() throws ServiceException {
        try{
            return funcionarioDao.buscarTodos();

        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ServiceException("Erro ao listar funcionários", e);
        }
    }


    public Optional<Funcionario> buscarFuncionarioPorAtributoIdentificador(String email) throws ServiceException {
        validarEmail(email);
        try{
            return funcionarioDao.buscarPorAtributoIdentificador(email.trim());

        } catch (DatabaseException e){
            e.printStackTrace();
            throw new ServiceException("Erro ao buscar funcionario de email " + email, e);
        }
    }

    public Optional<Funcionario> buscarFuncionarioPorCpf(String cpf) throws ServiceException{
        validarCPF(cpf);
        try{
            return funcionarioDao.buscarFuncionarioPorCpf(cpf);
        } catch (DatabaseException e) {
            throw new ServiceException("Erro ao buscar funcionario de cpf: " + cpf, e);
        }
    }


    public void atualizarFuncionario(Funcionario funcionario, String cpfAntigo) throws ServiceException {
        validarFuncionario(funcionario);

        try {
            Optional<Funcionario> funcionarioComCpf = funcionarioDao.buscarFuncionarioPorCpf(funcionario.getCpf());
            Optional<Funcionario> funcionarioComEmail = funcionarioDao.buscarPorAtributoIdentificador(funcionario.getEmail());

            if(funcionarioComCpf.isPresent() && !funcionarioComCpf.get().getCpf().equals(cpfAntigo))
                throw new ServiceException("CPF " + funcionario.getCpf() + " já registrado");

            if(funcionarioComEmail.isPresent() && !funcionarioComEmail.get().getCpf().equals(cpfAntigo))
                throw new ServiceException("E-mail " + funcionario.getEmail() + " já registrado");

            funcionarioDao.atualizar(funcionario, cpfAntigo);

        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ServiceException("Erro ao atualizar funcionário de CPF " + cpfAntigo, e);
        }
    }

    public void deletarFuncionario(String cpf) throws ServiceException {
        try{
            validarCPF(cpf);

            funcionarioDao.deletar(cpf);

        } catch (IllegalArgumentException e){
            throw new ServiceException(e.getMessage());
        } catch (DatabaseException e){
            throw new ServiceException("Erro ao deletar funcionario de CPF " + cpf, e);
        }
    }

    public void atualizarSenha(String email, String novaSenha) throws ServiceException{
        try{
            validarEmail(email);
            validarNovaSenha(novaSenha);

            Optional<Funcionario> funcionarioOpt = funcionarioDao.buscarPorAtributoIdentificador(email.trim());
            if (funcionarioOpt.isEmpty()){
                throw new ServiceException("Não existe funcionário cadastrado com o Email: " + email);
            }
            String novaSenhaHash =  Hash.gerarHash(novaSenha);
            funcionarioDao.atualizarSenha(email.trim(), novaSenhaHash);

        }
        catch (DatabaseException e) {
            e.printStackTrace();
            throw new ServiceException("Erro ao atualizar senha do funcionário", e);
        }
    }

    public boolean funcionarioPrecisaCadastrarSenha(String email) throws ServiceException {
        Optional<Funcionario> funcionarioOpt = buscarFuncionarioPorAtributoIdentificador(email);
        if (funcionarioOpt.isEmpty()) {
            return false;
        }

        String senhaHash = funcionarioOpt.get().getSenhaHash();
        if (senhaHash == null || senhaHash.trim().isEmpty()) {
            return true;
        }

        String hashSenhaInicial = Hash.gerarHash(SENHA_INICIAL_FUNCIONARIO);
        String hashSenhaPadraoAntiga = Hash.gerarHash("123456");

        return senhaHash.equals(hashSenhaInicial) || senhaHash.equals(hashSenhaPadraoAntiga);
    }

    public static String gerarHashSenhaInicialFuncionario() {
        return Hash.gerarHash(SENHA_INICIAL_FUNCIONARIO);
    }


    public void validarFuncionario(Funcionario funcionario){

        validarCPF(funcionario.getCpf());

        validarEmail(funcionario.getEmail());

        validarTel(funcionario.getTelefone());
    }
    private void validarCPF(String cpf){
        if (!DocumentUtils.isCpfValido(cpf)) {
            throw new ServiceException("Cpf inválido: " + cpf);
        }
    }

    private void validarEmail(String email){
        if (email == null || email.trim().isEmpty()) {
            throw new ServiceException("Email não informado");
        }
        if (!ValidationUtils.isEmailValido(email.trim())){
            throw new ServiceException("Email Inválido: " + email);
        }
    }

    private void validarTel(String telefone){
        if (!ValidationUtils.isTelefoneValido(telefone)){
            throw new ServiceException("Telefone inválido: " + telefone);
        }
    }

    private void validarNovaSenha(String senha) {
        if (senha == null || senha.trim().isEmpty()) {
            throw new ServiceException("A senha não pode ficar vazia.");
        }
        if (senha.length() < 6) {
            throw new ServiceException("A senha deve ter pelo menos 6 caracteres.");
        }
    }

}