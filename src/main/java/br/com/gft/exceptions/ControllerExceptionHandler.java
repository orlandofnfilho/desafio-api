package br.com.gft.exceptions;

import java.time.Instant;
import java.util.Arrays;

import javax.persistence.NonUniqueResultException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.gft.exceptions.erros.DataIntegrityError;
import br.com.gft.exceptions.erros.StandardError;

@ControllerAdvice
public class ControllerExceptionHandler {
	
	
	private static final String BAD_REQUEST = "Bad Request";
	private static final String CONFLICT = "Conflict";
	private static final String NOT_FOUND = "Not Found";

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request){
		
	StandardError err = new StandardError();
	err.setTimestamp(Instant.now());
	err.setStatus(HttpStatus.NOT_FOUND.value());
	err.setError(NOT_FOUND);
	err.setMessage(e.getMessage());
	err.setPath(request.getRequestURI());
	return ResponseEntity.status(err.getStatus()).body(err);
	
	}
	
	@ExceptionHandler(BusinessRuleException.class)
	public ResponseEntity<StandardError> businessConstraint(BusinessRuleException e, HttpServletRequest request){
		
	StandardError err = new StandardError();
	err.setTimestamp(Instant.now());
	err.setStatus(HttpStatus.CONFLICT.value());
	err.setError(CONFLICT);
	err.setMessage(e.getMessage());
	err.setPath(request.getRequestURI());
	return ResponseEntity.status(err.getStatus()).body(err);
	
	}
	
	@ExceptionHandler(ArrayIndexOutOfBoundsException.class)
	public ResponseEntity<StandardError> businessConstraint(ArrayIndexOutOfBoundsException e, HttpServletRequest request){
		
	StandardError err = new StandardError();
	err.setTimestamp(Instant.now());
	err.setStatus(HttpStatus.BAD_REQUEST.value());
	err.setError(BAD_REQUEST);
	err.setMessage(e.getMessage());
	err.setPath(request.getRequestURI());
	return ResponseEntity.status(err.getStatus()).body(err);
	
	}
	
	
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<DataIntegrityError> dataIntegrityConstraint(DataIntegrityViolationException e, HttpServletRequest request){
		
	DataIntegrityError err = new DataIntegrityError();
	err.setTimestamp(Instant.now());
	err.setStatus(HttpStatus.BAD_REQUEST.value());
	err.setError(BAD_REQUEST);
	err.setMessage(Arrays.asList(e.getMessage()));
	err.setPath(request.getRequestURI());
	return ResponseEntity.status(err.getStatus()).body(err);
	
	}
	
	
	@ExceptionHandler(NonUniqueResultException.class)
	public ResponseEntity<StandardError> nonUniqueResult(NonUniqueResultException e, HttpServletRequest request){		
	StandardError err = new StandardError();
	err.setTimestamp(Instant.now());
	err.setStatus(HttpStatus.BAD_REQUEST.value());
	err.setError(BAD_REQUEST);
	err.setMessage(e.getMessage());
	err.setPath(request.getRequestURI());
	return ResponseEntity.status(err.getStatus()).body(err);
	
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<StandardError> invalidDateFormat(HttpMessageNotReadableException e, HttpServletRequest request){		
	StandardError err = new StandardError();
	err.setTimestamp(Instant.now());
	err.setStatus(HttpStatus.BAD_REQUEST.value());
	err.setError(BAD_REQUEST);
	err.setMessage("Formato de data inválido");
	err.setPath(request.getRequestURI());
	return ResponseEntity.status(err.getStatus()).body(err);
	
	}

}
