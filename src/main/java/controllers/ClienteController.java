package controllers;

import Services.ClienteService;
import dao.ClienteDAO;
import entities.Cliente;
import exceptions.ControllerException;
import exceptions.ServiceException;

import java.util.List;
import java.util.Optional;

public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController() {
        clienteService = new ClienteService(new ClienteDAO());
    }

    public void cadastrar(String nome,
                          String cpf,
                          String telefone,
                          String email)
            throws ControllerException {

        Cliente cliente = new Cliente(0, nome, cpf, telefone, email);

        try {
            clienteService.criarCliente(cliente);

        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }

    public void editar(String nome,
                       String cpfNovo,
                       String telefone,
                       String email,
                       String cpfAntigo)
            throws ControllerException {

        Cliente cliente = new Cliente(0, nome, cpfNovo, telefone, email);

        try {
            clienteService.atualizarCliente(cliente, cpfAntigo);

        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }

    public void excluir(String cpf) throws ControllerException {

        try {
            clienteService.deletarCliente(cpf);

        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }

    public Optional<Cliente> buscarPorCpf(String cpf) throws ControllerException {

        try {
            return clienteService.buscarClientePorAtributoIdentificador(cpf);

        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }

    public List<Cliente> buscarPorNome(String nome) throws ControllerException {

        try {
            return clienteService.buscarClientePorNome(nome);

        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }

    public Optional<Cliente> buscarPorPlaca(String placa) throws ControllerException {

        try {
            return clienteService.buscarClientePorPlaca(placa);

        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }

    public List<Cliente> listarTodos() throws ControllerException {

        try {
            return clienteService.buscarTodosClientes();

        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }
    
    public Optional<Cliente> buscarPorEmail(String email) throws ControllerException {
        try {
            return clienteService.buscarClientePorEmail(email);

        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }

    public Optional<Cliente> buscarPorTelefone(String telefone) throws ControllerException {
        try {
            return clienteService.buscarClientePorTelefone(telefone);

        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }

    public boolean cpfExiste(String cpf) throws ControllerException {
        try {
            return clienteService.buscarClientePorAtributoIdentificador(cpf).isPresent();

        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }
}