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

    private FuncionarioDAO funcionarioDao;

    public FuncionarioService(FuncionarioDAO funcionarioDao){this.funcionarioDao = funcionarioDao;}

    public void criarfuncionario(Funcionario funcionario){
        try{
            validarFuncionario(funcionario);

            Optional<Funcionario> funcionarioOpt = funcionarioDao.buscarPorAtributoIdentificador(funcionario.getCpf());
            if (funcionarioOpt.isEmpty()){
                funcionarioDao.criar(funcionario);

            }
            else {
                if(funcionarioOpt.get().getEmail().equals(funcionario.getEmail())) throw new ServiceException("E-mail " + funcionario.getEmail() + " já cadastrado");

                throw new ServiceException("Funcionario de cpf " + funcionario.getCpf() + " já cadastrado");
            }

        } catch(DatabaseException e){
            e.printStackTrace();
            throw new ServiceException("Erro ao cadastrar funcionario");
        }
    }

    public List<Funcionario> buscarTodosFuncionarios() throws ServiceException {
        try{
            return funcionarioDao.buscarTodos();

        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ServiceException("Erro ao listar clientes");
        }
    }


    public Optional<Funcionario> buscarFuncionarioPorAtributoIdentificador(String email) throws ServiceException {
        try{
            Optional<Funcionario> funcionarioOpt = funcionarioDao.buscarPorAtributoIdentificador(email);

            if(funcionarioOpt.isEmpty()) throw new ServiceException("Funcionario de email " + email + " inexistente");

            return funcionarioOpt;

        } catch (DatabaseException e){
            e.printStackTrace();
            throw new ServiceException("Erro ao buscar funcionario de email " + email);
        }
    }

    public Optional<Funcionario> buscarFuncionarioPorCpf(String cpf) throws ServiceException{
        validarCPF(cpf);
        try{
            return funcionarioDao.buscarFuncionarioPorCpf(cpf);
        } catch (DatabaseException e) {
            throw new ServiceException("Erro ao buscar funcionario de cpf: " + cpf);
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
            throw new ServiceException("Erro ao atualizar cliente de CPF " + cpfAntigo);
        }
    }

    public void deletarFuncionario(String cpf) throws ServiceException {
        try{
            validarCPF(cpf);

            funcionarioDao.deletar(cpf);

        } catch (IllegalArgumentException e){
            throw new ServiceException(e.getMessage());
        } catch (DatabaseException e){
            throw new ServiceException("Erro ao deletar funcionario de CPF " + cpf);
        }
    }

    public void atualizarSenha(String email, String novaSenha) throws ServiceException{
        try{
            validarEmail(email);
            Optional<Funcionario> funcionarioOpt = funcionarioDao.buscarPorAtributoIdentificador(email);
            if (funcionarioOpt.isEmpty()){
                throw new ServiceException("Não existe funcionário cadastrado com o Email: " + email);
            }
            String novaSenhaHash =  Hash.gerarHash(novaSenha);
            funcionarioDao.atualizarSenha(email, novaSenhaHash);

        }
        catch (DatabaseException e) {
            e.printStackTrace();
            throw new ServiceException("Erro ao atualizar senha do funcionário", e);
        }
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
            if (!ValidationUtils.isEmailValido(email)){
                throw new ServiceException("Email Inválido: " + email);
            };
    }

    private void validarTel(String telefone){
        if (!ValidationUtils.isTelefoneValido(telefone)){
            throw new ServiceException("Telefone inválido: " + telefone);
        };
    }

}
