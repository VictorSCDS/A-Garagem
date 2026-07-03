package Services;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import entities.Funcionario;
import entities.enums.Cargo;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;

import java.util.Objects;

public class FuncionarioService {

    private Funcionario funcionario;

    public void validarFuncionario(Funcionario funcionario){

        validarCPF(funcionario.getCpf());

        validarEmail(funcionario.getEmail());

        validarTel(funcionario.getTelefone());
    }
    private void validarCPF(String cpf){
        CPFValidator validador = new CPFValidator();
        try {
            validador.assertValid(cpf);
        } catch (InvalidStateException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("CPF Invalido: " + cpf);
        }
    }

    private void validarEmail(String email){
        try {
            InternetAddress internetAddress = new InternetAddress(email);
            internetAddress.validate();
        } catch (AddressException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Email Invalido: " + email);
        }
    }

    private void validarTel(String telefone){
        if (!telefone.matches("\\d{11}")){
            if (telefone.length() > 11){
                throw new IllegalArgumentException("Número de telefone muito longo, o tamanho não deve ser maior que 11: " + telefone);
            }
            else if (telefone.length() < 11) {
                throw new IllegalArgumentException("Número de telefone muito curto, o tamanho não deve ser menor que 11: " + telefone);
            }
            else {

            }
        }
    }

}
