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

            Optional<Cliente> clienteOpt = clienteDao.buscarPorAtributoIdentificador(cliente.getCpf());

            if(!clienteOpt.isPresent()){
                clienteDao.criar(cliente);

            } else {
                if(clienteOpt.get().getEmail().equals(cliente.getEmail())) throw new ServiceException("E-mail " + cliente.getEmail() + " já cadastrado");

                throw new ServiceException("Cliente de cpf " + cliente.getCpf() + " já cadastrado");
            }

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
            Optional<Cliente> clienteOpt = clienteDao.buscarPorAtributoIdentificador(cpf);

            if(clienteOpt.isEmpty()) throw new ServiceException("Cliente de cpf " + cpf + " inexistente");

            return clienteOpt;

        } catch (DatabaseException e){
            e.printStackTrace();
            throw new ServiceException("Erro ao buscar cliente de CPF " + cpf);
        }
    }

    public void atualizarCliente(Cliente cliente, String cpfAntigo) throws ServiceException {
        validarCamposUpdate(cliente, cpfAntigo);

        try {
            Optional<Cliente> clienteComCpf = clienteDao.buscarPorAtributoIdentificador(cliente.getCpf());
            Optional<Cliente> clienteComEmail = clienteDao.buscarClientePorEmail(cliente.getEmail());

            if(clienteComCpf.isPresent() && !clienteComCpf.get().getCpf().equals(cpfAntigo))
                throw new ServiceException("CPF " + cliente.getCpf() + " já registrado");

            if(clienteComEmail.isPresent() && !clienteComEmail.get().getCpf().equals(cpfAntigo))
                throw new ServiceException("E-mail " + cliente.getEmail() + " já registrado");

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
            if(nome.isEmpty()) throw new ServiceException("Campo de nome vazio");

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
    
    public Optional<Cliente> buscarClientePorEmail(String email) throws ServiceException {
        try{
            if(!ValidationUtils.isEmailValido(email)){
                throw new ServiceException("Formato de E-mail inválido para busca");
            }
            return clienteDao.buscarClientePorEmail(email);

        } catch (IllegalArgumentException e){
            throw new ServiceException(e.getMessage());
        } catch (DatabaseException e) {
            throw new ServiceException("Erro ao buscar cliente por e-mail " + email);
        }
    }

    public Optional<Cliente> buscarClientePorTelefone(String telefone) throws ServiceException {
        try{
            if(!ValidationUtils.isTelefoneValido(telefone)){
                throw new ServiceException("Formato de Telefone inválido para busca");
            }
            return clienteDao.buscarClientePorTelefone(telefone);

        } catch (IllegalArgumentException e){
            throw new ServiceException(e.getMessage());
        } catch (DatabaseException e) {
            throw new ServiceException("Erro ao buscar cliente por telefone " + telefone);
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
