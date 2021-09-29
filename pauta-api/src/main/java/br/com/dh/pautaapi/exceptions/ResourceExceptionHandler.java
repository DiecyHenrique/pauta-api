package br.com.dh.pautaapi.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.dh.pautaapi.model.StandardError;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(EntityNotFoundException err, HttpServletRequest request) {

		StandardError error = new StandardError();
		error.setTimestamp(Instant.now());
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setError("Não encontrado");
		error.setMessage(err.getMessage());
		error.setPath(request.getRequestURI());

		return new ResponseEntity<StandardError>(error, HttpStatus.NOT_FOUND);
	}

}
