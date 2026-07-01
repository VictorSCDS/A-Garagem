package dto;

public record ClienteResumoDTO(String nome,
                               String cpf,
                               String email,
                               String telefone,
                               int quantidadeVeiculos,
                               String placa,
                               String modelo) {
}