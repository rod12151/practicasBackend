package practicas.gestion_personal.api.controllers.error_handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import practicas.gestion_personal.api.models.response.ErrorResponse;
import practicas.gestion_personal.api.models.response.ErrorsResponse;
import practicas.gestion_personal.utils.IdDuplicate;
import practicas.gestion_personal.utils.IdNotFoundException;
import practicas.gestion_personal.utils.UserDuplicate;

import java.util.ArrayList;

@RestControllerAdvice
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestController {
    @ExceptionHandler(IdNotFoundException.class)
    public ErrorResponse handleIdNotFound(IdNotFoundException exception){
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST.name())
                .code(HttpStatus.BAD_REQUEST.value())
                .build();

    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorsResponse handleIdNotFound(MethodArgumentNotValidException exception){
        var errors =new ArrayList<String>();
        exception.getAllErrors()
                .forEach(error->errors.add(error.getDefaultMessage()));
        return ErrorsResponse.builder()
                .errors(errors)
                .status(HttpStatus.BAD_REQUEST.name())
                .code(HttpStatus.BAD_REQUEST.value())
                .build();

    }
    @ExceptionHandler(IdDuplicate.class)
    public ErrorResponse idDuplicate(IdDuplicate exception){
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST.name())
                .code(HttpStatus.BAD_REQUEST.value())
                .build();

    }
    @ExceptionHandler(UserDuplicate.class)
    public ErrorResponse userHaveRole(UserDuplicate exception){
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST.name())
                .code(HttpStatus.BAD_REQUEST.value())
                .build();

    }

    }
