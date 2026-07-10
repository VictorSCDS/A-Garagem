package controllers;

import Services.VeiculoService;
import dao.VeiculoDAO;
import entities.Veiculo;
import exceptions.ControllerException;
import exceptions.ServiceException;

import java.util.List;
import java.util.Optional;

public class VeiculoController {

    private final VeiculoService veiculoService;

    public VeiculoController() {
        this.veiculoService = new VeiculoService(new VeiculoDAO());
    }

    public void cadastrar(String placa,
                           String marca,
                           String modelo)
            throws ControllerException {

        Veiculo veiculo = new Veiculo(0, placa, marca, modelo);

        try {
            veiculoService.criarVeiculo(veiculo);

        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }

    public void editar(String placaNova,
                       String marca,
                       String modelo,
                       String placaAntiga)
            throws ControllerException {

        Veiculo veiculo = new Veiculo(0, placaNova, marca, modelo);

        try {
            veiculoService.atualizarVeiculo(veiculo, placaAntiga);

        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }

    public void excluir(String placa) throws ControllerException {

        try {
            veiculoService.deletarVeiculo(placa);

        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }
    
    public void vincularVeiculoAoCliente(String placa, String cpf) throws ControllerException {
        try {
            veiculoService.vincularVeiculoAoCliente(placa, cpf);

        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }

    public Optional<Veiculo> buscarPorPlaca(String placa) throws ControllerException {

        try {
            return veiculoService.buscarVeiculoPorPlaca(placa);

        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }

    public List<Veiculo> buscarPorCliente(String cpf) throws ControllerException {

        try {
            return veiculoService.buscarTodosVeiculosDeCliente(cpf);

        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }

    public boolean placaExiste(String placa) throws ControllerException {

        try {
            return veiculoService.buscarVeiculoPorPlaca(placa).isPresent();

        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }
}