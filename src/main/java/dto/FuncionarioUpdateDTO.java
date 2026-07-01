package dto;

public record FuncionarioUpdateDTO(String nome,
                                   String cpf,
                                   String cargo,
                                   String telefone,
                                   String email) {
}
