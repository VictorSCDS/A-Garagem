package Services;

import dao.ClienteDAO;
import entities.Cliente;
import exceptions.DatabaseException;
import exceptions.ServiceException;
import utils.DocumentUtils;
import utils.ValidationUtils;

import java.util.List;
import java.util.Optional;

public class ClienteService {

    private ClienteDAO clienteDao;

    public ClienteService(ClienteDAO clienteDao) {
        this.clienteDao = clienteDao;
    }

    public void criar(Cliente cliente) throws ServiceException{
        try{
            if(!DocumentUtils.isCpfValido(cliente.getCpf())){
                throw new ServiceException("CPF inválido");
            }

            if(!ValidationUtils.isEmailValido(cliente.getEmail())){
                throw new ServiceException("E-Mail inválido");
            }

            if(!ValidationUtils.isTelefoneValido(cliente.getTelefone())){
                throw new ServiceException("Telefone inválido");
            }

            clienteDao.criar(cliente);

        } catch(IllegalArgumentException e){
            throw new ServiceException(e.getMessage());
        } catch(DatabaseException e){
            e.printStackTrace();
            throw new ServiceException("Erro ao cadastrar cliente");
        }
    }

    public List<Cliente> buscarTodos() throws ServiceException {
        try{
            return clienteDao.buscarTodos();

        } catch (DatabaseException e) {
            throw new ServiceException("Erro ao buscar todos os clientes cadastrados");
        }
    }

    public Optional<Cliente> buscarPorAtributoIdentificador(String cpf) throws ServiceException {
        try{
            return clienteDao.buscarPorAtributoIdentificador(cpf);

        } catch (DatabaseException e){
            throw new ServiceException("Erro ao buscar cliente de CPF " + cpf);
        }
    }
}
