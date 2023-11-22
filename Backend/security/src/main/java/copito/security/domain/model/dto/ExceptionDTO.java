package copito.security.domain.model.dto;

import lombok.Builder;

@Builder
public record ExceptionDTO(
        String detail,
        String type,
        Integer status
) {
}
