package br.com.personal.wishlist.domain.common.exception;


import br.com.personal.wishlist.domain.common.enumeration.MessageKey;
import br.com.personal.wishlist.domain.common.utils.MessageResponse;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@Getter
@Setter
public class ValidationException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    private final MessageResponse messageError;

    public ValidationException(MessageResponse messageError) {
        this.messageError = messageError;
    }

    public ValidationException(MessageResponse messageError, Exception cause) {
        super(MessageKey.findByCode(messageError.getCode()).getCode() + " - " + MessageKey.findByCode(messageError.getCode()).getMessage(), cause);
        this.messageError = messageError;
    }
}
