package br.com.personal.wishlist.domain.common.exception;

import br.com.personal.wishlist.domain.common.enumeration.MessageKey;
import br.com.personal.wishlist.domain.common.enumeration.MessageType;
import br.com.personal.wishlist.domain.common.utils.MessageResponse;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ResponseEntity<MessageResponse> handleException(Exception exception, WebRequest request) {
        Map<Class<? extends Exception>, ErrorResponse> errorMap = createErrorMap();

        ErrorResponse errorResponse = errorMap.get(exception.getClass());

        if (Objects.isNull(errorResponse)) {
            String code = "error-" + exception.getClass().getSimpleName().toLowerCase();
            String message = exception.getMessage() != null ? exception.getMessage() : "Unknown internal server error.";
            errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, code, message);
        }

        MessageResponse messageResponse = createMessageResponse(errorResponse.code, errorResponse.message);
        return new ResponseEntity<>(messageResponse, errorResponse.status);
    }

    @ExceptionHandler(CoreRuleException.class)
    @ResponseBody
    public ResponseEntity<MessageResponse> handleCoreRuleException(Exception ex, WebRequest request) {
        MessageKey messageKey = MessageKey.findByCode(((CoreRuleException) ex).getMessageError().getCode());
        MessageResponse messageResponse = null;
        if (Objects.nonNull(messageKey)) {
            messageResponse = MessageResponse.error(messageKey);
        } else {
            MessageResponse.builder()
                    .messageType(MessageType.ERROR)
                    .code("error-default")
                    .message("Error default.")
                    .build();
        }
        return new ResponseEntity<>(messageResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Map<Class<? extends Exception>, ErrorResponse> createErrorMap() {
        Map<Class<? extends Exception>, ErrorResponse> errorMap = new HashMap<>();
        errorMap.put(BadCredentialsException.class, new ErrorResponse(HttpStatus.UNAUTHORIZED, "error-bad-credentials", "The username or password is incorrect."));
        errorMap.put(AccountStatusException.class, new ErrorResponse(HttpStatus.FORBIDDEN, "error-account-locked", "The account is locked."));
        errorMap.put(AccessDeniedException.class, new ErrorResponse(HttpStatus.FORBIDDEN, "error-access-denied", "You are not authorized to access this resource."));
        errorMap.put(SignatureVerificationException.class, new ErrorResponse(HttpStatus.FORBIDDEN, "error-jwt-signature", "The JWT signature is invalid."));
        errorMap.put(TokenExpiredException.class, new ErrorResponse(HttpStatus.FORBIDDEN, "error-jwt-expired", "The JWT token has expired."));
        errorMap.put(JWTDecodeException.class, new ErrorResponse(HttpStatus.FORBIDDEN, "error-jwt-decode", "The JWT decode is invalid."));
        errorMap.put(NoResourceFoundException.class, new ErrorResponse(HttpStatus.BAD_REQUEST, "error-bad-request", "Bad request."));
        return errorMap;
    }

    private MessageResponse createMessageResponse(String code, String message) {
        return MessageResponse.builder()
                .messageType(MessageType.ERROR)
                .code(code)
                .message(message)
                .build();
    }

    private record ErrorResponse(HttpStatus status, String code, String message) {
    }
}