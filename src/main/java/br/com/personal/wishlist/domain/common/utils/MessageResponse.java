package br.com.personal.wishlist.domain.common.utils;

import br.com.personal.wishlist.domain.common.enumeration.MessageKey;
import br.com.personal.wishlist.domain.common.enumeration.MessageType;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private MessageType messageType;
    private String code;
    private String message;
    private List<Detail> details;

    public MessageResponse(MessageType messageType, String code, String message) {
        super();
        this.messageType = messageType;
        this.code = code;
        this.message = message;
    }

    public static MessageResponse error(MessageKey messageKey) {
        return new MessageResponse(MessageType.ERROR, messageKey.getCode(), messageKey.getMessage());
    }

    public static MessageResponse warning(MessageKey messageKey) {
        return new MessageResponse(MessageType.WARNING, messageKey.getCode(), messageKey.getMessage());
    }

    public static MessageResponse success(MessageKey messageKey) {
        return new MessageResponse(MessageType.SUCCESS, messageKey.getCode(), messageKey.getMessage());
    }
}
