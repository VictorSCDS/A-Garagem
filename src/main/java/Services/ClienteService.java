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

    public void criarCliente(Cliente cliente) throws ServiceException{
        try{

            validarCampos(cliente);

            clienteDao.criar(cliente);

        } catch(DatabaseException e){
            e.printStackTrace();
            throw new ServiceException("Erro ao cadastrar cliente");
        }
    }

    public List<Cliente> buscarTodosClientes() throws ServiceException {
        try{
            return clienteDao.buscarTodos();

        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ServiceException("Erro ao listar clientes");
        }
    }

    public Optional<Cliente> buscarClientePorAtributoIdentificador(String cpf) throws ServiceException {
        try{
            return clienteDao.buscarPorAtributoIdentificador(cpf);

        } catch (DatabaseException e){
            e.printStackTrace();
            throw new ServiceException("Erro ao buscar cliente de CPF " + cpf);
        }
    }

    public void ataulizarCliente(Cliente cliente, String cpfAntigo) throws ServiceException{

        validarCamposUpdate(cliente, cpfAntigo);

        try{
            clienteDao.atualizar(cliente, cpfAntigo);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ServiceException("Erro ao atualizar cliente de CPF " + cpfAntigo);
        }
    }

    public void deletarCliente(String cpf) throws ServiceException {
        try{
            if(!DocumentUtils.isCpfValido(cpf)) throw new ServiceException("CPF inválido");

            clienteDao.deletar(cpf);
        } catch (IllegalArgumentException e){
            throw new ServiceException(e.getMessage());
        } catch (DatabaseException e){
            throw new ServiceException("Erro ao deletar cliente de CPF " + cpf);
        }
    }

    public List<Cliente> buscarClientePorNome(String nome) throws ServiceException {
        try{
            return clienteDao.buscarClientePorNome(nome);
        } catch (DatabaseException e){
            throw new ServiceException("Erro ao encontrar cliente de nome " + nome);
        }
    }

    public Optional<Cliente> buscarClientePorPlaca(String placa) throws ServiceException {
        validarPlaca(placa);

        try{
            return clienteDao.buscarClientePorPlaca(placa);
        } catch (DatabaseException e) {
            throw new ServiceException("Erro ao buscar cliente por placa de veículo " + placa);
        }
    }

    private void validarCampos(Cliente cliente){
        if(cliente.getNome().isEmpty()) throw new ServiceException("Nome vazio");

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
        } catch (IllegalArgumentException e){
            throw new ServiceException(e.getMessage());
        }
    }

    private void validarCamposUpdate(Cliente cliente, String cpfAntigo){
        if(cliente.getNome().isEmpty()) throw new ServiceException("Nome vazio");

        try{
            if(!DocumentUtils.isCpfValido(cpfAntigo)){
                throw new ServiceException("CPF inválido");
            }

            if(!ValidationUtils.isEmailValido(cliente.getEmail())){
                throw new ServiceException("E-Mail inválido");
            }

            if(!ValidationUtils.isTelefoneValido(cliente.getTelefone())){
                throw new ServiceException("Telefone inválido");
            }
        } catch (IllegalArgumentException e){
            throw new ServiceException(e.getMessage());
        }
    }

    private void validarPlaca(String placa) {
        try{
            if(!ValidationUtils.isPlacaValida(placa)){
                throw new ServiceException("Placa inválida");
            }
        } catch (IllegalArgumentException e){
            throw new ServiceException(e.getMessage());
        }
    }
}
