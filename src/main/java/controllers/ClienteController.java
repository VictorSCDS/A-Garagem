package controllers;

import Services.ClienteService;
import dao.ClienteDAO;
import entities.Cliente;
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
            throws ServiceException {

        Cliente cliente = new Cliente(0, nome, cpf, telefone, email);
        clienteService.criarCliente(cliente);
    }

    public void editar(String nome,
                       String cpfNovo,
                       String telefone,
                       String email,
                       String cpfAntigo)
            throws ServiceException {

        Cliente cliente = new Cliente(0, nome, cpfNovo, telefone, email);
        clienteService.atualizarCliente(cliente, cpfAntigo);
    }

    public void excluir(String cpf) throws ServiceException {
        clienteService.deletarCliente(cpf);
    }

    public Optional<Cliente> buscarPorCpf(String cpf) throws ServiceException {
        return clienteService.buscarClientePorAtributoIdentificador(cpf);
    }

    public List<Cliente> buscarPorNome(String nome) throws ServiceException {
        return clienteService.buscarClientePorNome(nome);
    }

    public Optional<Cliente> buscarPorPlaca(String placa) throws ServiceException {
        return clienteService.buscarClientePorPlaca(placa);
    }

    public List<Cliente> listarTodos() throws ServiceException {
        return clienteService.buscarTodosClientes();
    }

    public boolean cpfExiste(String cpf) throws ServiceException {
        return clienteService.buscarClientePorAtributoIdentificador(cpf).isPresent();
    }
}