package utils;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;

public class DocumentUtils {

    private DocumentUtils() { throw new UnsupportedOperationException("Classe utilitária"); }

    public static boolean isCpfValido(String cpf) {
        if(cpf.trim().isEmpty() || cpf.isBlank()) throw new IllegalArgumentException("CPF vazio.");

        try{
            CPFValidator validator = new CPFValidator();
            validator.assertValid(cpf);
            return true;

        } catch (InvalidStateException e) {
            e.printStackTrace();
            return false;
        }
    }
}
