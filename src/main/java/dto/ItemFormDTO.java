package dto;

import java.math.BigDecimal;

public record ItemFormDTO(String nome,
                          String codigo,
                          String marca,
                          int quantidade,
                          BigDecimal valorCompra,
                          BigDecimal valorVenda) {
}
