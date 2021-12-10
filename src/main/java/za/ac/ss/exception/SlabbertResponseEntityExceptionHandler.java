package za.ac.ss.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import za.ac.ss.dto.ErrorMessageDTO;

@RestControllerAdvice
public class SlabbertResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<ErrorMessageDTO> handleAllExceptions(RuntimeException ex, WebRequest request) {
        ErrorMessageDTO error = new ErrorMessageDTO(LocalDateTime.now(), "Sorry, Something went wrong", ex.getMessage().toString());
        return new ResponseEntity<ErrorMessageDTO>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
 
    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ErrorMessageDTO> handleUserNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorMessageDTO error = new ErrorMessageDTO(LocalDateTime.now(), "Record not Found", ex.getMessage());
        return new ResponseEntity<ErrorMessageDTO>(error, HttpStatus.NOT_FOUND);
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        ErrorMessageDTO error = new ErrorMessageDTO(LocalDateTime.now(), "validation", ex.getBindingResult().getFieldError().getField());
//        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
//    }

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErrorMessageDTO error = new ErrorMessageDTO(LocalDateTime.now(), "validation",
				ex.getBindingResult().getFieldError().getDefaultMessage());
		return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
	}

}
