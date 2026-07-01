package dto;

import java.util.List;

public record OrdemServicoFormDTO(String problema,
                                  String estado,
                                  String descricao,
                                  String placaVeiculo,
                                  List<String> tiposServico,
                                  List<String> pecas) {
}
