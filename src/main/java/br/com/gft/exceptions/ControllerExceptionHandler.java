package br.com.gft.exceptions;

import java.time.Instant;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.gft.exceptions.erros.DataIntegrityError;
import br.com.gft.exceptions.erros.StandardError;

@ControllerAdvice
public class ControllerExceptionHandler {
	
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request){
		
	StandardError err = new StandardError();
	err.setTimestamp(Instant.now());
	err.setStatus(HttpStatus.NOT_FOUND.value());
	err.setError("Not Found");
	err.setMessage(e.getMessage());
	err.setPath(request.getRequestURI());
	return ResponseEntity.status(err.getStatus()).body(err);
	
	}
	
	@ExceptionHandler(BusinessRuleException.class)
	public ResponseEntity<StandardError> businessConstraint(BusinessRuleException e, HttpServletRequest request){
		
	StandardError err = new StandardError();
	err.setTimestamp(Instant.now());
	err.setStatus(HttpStatus.CONFLICT.value());
	err.setError("Conflict");
	err.setMessage(e.getMessage());
	err.setPath(request.getRequestURI());
	return ResponseEntity.status(err.getStatus()).body(err);
	
	}
	
	@ExceptionHandler(ArrayIndexOutOfBoundsException.class)
	public ResponseEntity<StandardError> businessConstraint(ArrayIndexOutOfBoundsException e, HttpServletRequest request){
		
	StandardError err = new StandardError();
	err.setTimestamp(Instant.now());
	err.setStatus(HttpStatus.BAD_REQUEST.value());
	err.setError("Bad Request");
	err.setMessage(e.getMessage());
	err.setPath(request.getRequestURI());
	return ResponseEntity.status(err.getStatus()).body(err);
	
	}
	
	
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<DataIntegrityError> dataIntegrityConstraint(DataIntegrityViolationException e, HttpServletRequest request){
		
	DataIntegrityError err = new DataIntegrityError();
	err.setTimestamp(Instant.now());
	err.setStatus(HttpStatus.BAD_REQUEST.value());
	err.setError("Bad Request");
	err.setMessage(Arrays.asList(e.getMessage()));
	err.setPath(request.getRequestURI());
	return ResponseEntity.status(err.getStatus()).body(err);
	
	}
	
	

}
