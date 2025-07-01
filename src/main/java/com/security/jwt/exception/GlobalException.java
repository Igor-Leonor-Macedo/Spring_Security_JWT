    package com.security.jwt.exception;

    import com.security.jwt.model.ErrorResponse;
    import com.security.jwt.model.ValidationErrorResponse;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.validation.FieldError;
    import org.springframework.web.bind.MethodArgumentNotValidException;
    import org.springframework.web.bind.annotation.ExceptionHandler;
    import org.springframework.web.bind.annotation.RestControllerAdvice;
    import org.springframework.web.context.request.WebRequest;

    import java.time.LocalDateTime;
    import java.util.HashMap;
    import java.util.Map;

    @RestControllerAdvice
        public class GlobalException {

            // NOVO: Handler para erros de validação (@Valid)
            @ExceptionHandler(MethodArgumentNotValidException.class)
            public ResponseEntity<ValidationErrorResponse> handleValidationErrors(
                    MethodArgumentNotValidException ex, WebRequest request) {

                System.out.println("🔴 VALIDATION ERROR CAPTURADO!");
                Map<String, String> errors = new HashMap<>();

                // Captura todos os erros de campo
                ex.getBindingResult().getAllErrors().forEach((error) -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);

                    System.out.println("Campo: " + fieldName + " | Erro: " + errorMessage);
                });
                ValidationErrorResponse errorResponse = new ValidationErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        "Dados de entrada inválidos",
                        errors
                );
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
            }

            @ExceptionHandler(ExistingUserException.class)
                public ResponseEntity<ErrorResponse> Existing_User(
                    ExistingUserException ex, WebRequest request) {
                    ErrorResponse error = new ErrorResponse(LocalDateTime.now(), HttpStatus.CONFLICT.value(), ex.getMessage());
                    return new ResponseEntity<>(error, HttpStatus.CONFLICT);
                }

            @ExceptionHandler(UserNotFoundException.class)
            public ResponseEntity<ErrorResponse> User_Not_Found(
                    UserNotFoundException ex, WebRequest request) {
                ErrorResponse error = new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage());
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            }

            // OPCIONAL: Handler para exceções não tratadas
            @ExceptionHandler(Exception.class)
            public ResponseEntity<ErrorResponse> handleGenericException(
                    Exception ex, WebRequest request) {

                System.out.println("🔴 ERRO GENÉRICO: " + ex.getMessage());
                ex.printStackTrace();

                ErrorResponse error = new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Erro interno do servidor"
                );
                return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
