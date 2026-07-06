package Services;

import dao.VeiculoDAO;
import entities.Veiculo;
import exceptions.DatabaseException;
import exceptions.ServiceException;
import utils.DocumentUtils;
import utils.ValidationUtils;

import java.util.List;
import java.util.Optional;

public class VeiculoService {

    VeiculoDAO veiculoDao;

    public VeiculoService(VeiculoDAO veiculoDao){
        this.veiculoDao = veiculoDao;
    }

    public void criarVeiculo(Veiculo veiculo) throws ServiceException {
        validarCampos(veiculo);
        try{
            veiculoDao.criar(veiculo);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ServiceException("Erro ao criar veículo");
        }
    }

    public List<Veiculo> buscarTodosVeiculosDeCliente(String cpf) throws ServiceException {
        if(!DocumentUtils.isCpfValido(cpf)) throw new ServiceException("CPF inválido");
        try{
            return veiculoDao.buscarTodosVeiculosDeCliente(cpf);

        } catch (IllegalArgumentException e){
            e.printStackTrace();
            throw new ServiceException(e.getMessage());

        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ServiceException("Erro ao buscar veículos do cliente de CPF " + cpf);
        }
    }

    public Optional<Veiculo> buscarVeiculoPorPlaca(String placa) throws ServiceException {
        validarPlaca(placa);
        try{
            return veiculoDao.buscarPorAtributoIdentificador(placa);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ServiceException("Erro ao buscar veículo de placa " + placa);
        }
    }

    public void atualizarVeiculo(Veiculo veiculo, String placaAntiga) throws ServiceException {
        validarPlaca(placaAntiga);
        validarCampos(veiculo);

        try{
            veiculoDao.atualizar(veiculo, placaAntiga);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ServiceException("Erro ao veiculo de placa " + placaAntiga);
        }
    }

    public void deletarVeiculo(String placa) throws ServiceException {
        validarPlaca(placa);
        try{
            veiculoDao.deletar(placa);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ServiceException("Erro ao deleta veículo de placa " + placa);
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

    private void validarCampos(Veiculo veiculo){

        validarPlaca(veiculo.getPlaca());

        if(veiculo.getMarca() == null || veiculo.getMarca().isEmpty()){
            throw new ServiceException("Campo de marca vazio");
        }

        if(veiculo.getModelo() == null || veiculo.getModelo().isEmpty()){
            throw new ServiceException("Campo de modelo vazio");
        }
    }

}
