package br.com.gft.exceptions;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.NonUniqueResultException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintDefinitionException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.gft.exceptions.erros.ArgumentNotValidError;
import br.com.gft.exceptions.erros.StandardError;

@ControllerAdvice
public class ControllerExceptionHandler {

	private static final String REQUISICAO_INVALIDA = "Requisição inválida";
	private static final String BAD_REQUEST = "Bad Request";
	private static final String CONFLICT = "Conflict";
	private static final String NOT_FOUND = "Not Found";
	private static final String FORBIDDEN = "Forbidden";

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> catchResourceNotFound(ResourceNotFoundException e,
			HttpServletRequest request) {

		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setStatus(HttpStatus.NOT_FOUND.value());
		err.setError(NOT_FOUND);
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		return ResponseEntity.status(err.getStatus()).body(err);

	}

	@ExceptionHandler(BusinessRuleException.class)
	public ResponseEntity<StandardError> catchBusinessRule(BusinessRuleException e, HttpServletRequest request) {

		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setStatus(HttpStatus.CONFLICT.value());
		err.setError(CONFLICT);
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		return ResponseEntity.status(err.getStatus()).body(err);

	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<StandardError> catchIllegalArgument(IllegalArgumentException e, HttpServletRequest request) {
		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setStatus(HttpStatus.BAD_REQUEST.value());
		err.setError(BAD_REQUEST);
		err.setMessage(REQUISICAO_INVALIDA);
		err.setPath(request.getRequestURI());
		return ResponseEntity.status(err.getStatus()).body(err);

	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<StandardError> catchOthers(RuntimeException e, HttpServletRequest request) {
		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setStatus(HttpStatus.BAD_REQUEST.value());
		err.setError(BAD_REQUEST);
		err.setMessage(REQUISICAO_INVALIDA);
		err.setPath(request.getRequestURI());
		return ResponseEntity.status(err.getStatus()).body(err);

	}

	@ExceptionHandler(ArrayIndexOutOfBoundsException.class)
	public ResponseEntity<StandardError> catchArrayIndexOutOfBounds(ArrayIndexOutOfBoundsException e,
			HttpServletRequest request) {

		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setStatus(HttpStatus.BAD_REQUEST.value());
		err.setError(BAD_REQUEST);
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		return ResponseEntity.status(err.getStatus()).body(err);

	}

	@ExceptionHandler(NonUniqueResultException.class)
	public ResponseEntity<StandardError> catchNonUniqueResult(NonUniqueResultException e, HttpServletRequest request) {
		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setStatus(HttpStatus.BAD_REQUEST.value());
		err.setError(BAD_REQUEST);
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		return ResponseEntity.status(err.getStatus()).body(err);

	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<StandardError> catchInvalidDate(HttpMessageNotReadableException e,
			HttpServletRequest request) {
		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setStatus(HttpStatus.BAD_REQUEST.value());
		err.setError(BAD_REQUEST);
		err.setMessage("Formato de data inválido");
		err.setPath(request.getRequestURI());
		return ResponseEntity.status(err.getStatus()).body(err);

	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<StandardError> catchDataIntegrityViolation(DataIntegrityViolationException e,
			HttpServletRequest request) {
		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setStatus(HttpStatus.BAD_REQUEST.value());
		err.setError(BAD_REQUEST);
		err.setMessage(REQUISICAO_INVALIDA);
		err.setPath(request.getRequestURI());
		return ResponseEntity.status(err.getStatus()).body(err);

	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<StandardError> dataIntegratyViolation(AccessDeniedException e, HttpServletRequest request) {

		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setStatus(HttpStatus.FORBIDDEN.value());
		err.setError(FORBIDDEN);
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		return ResponseEntity.status(err.getStatus()).body(err);

	}

	@ExceptionHandler(ConstraintDefinitionException.class)
	public ResponseEntity<ArgumentNotValidError> catchConstraintDefinition(ConstraintDefinitionException e,
			HttpServletRequest request) {
		ArgumentNotValidError err = new ArgumentNotValidError();
		err.setTimestamp(Instant.now());
		err.setStatus(HttpStatus.BAD_REQUEST.value());
		err.setError(BAD_REQUEST);
		err.setMessage(List.of(e.getMessage()));
		err.setPath(request.getRequestURI());
		return ResponseEntity.status(err.getStatus()).body(err);

	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ArgumentNotValidError> catchMethodArgumentNotValid(MethodArgumentNotValidException e,
			HttpServletRequest request) {
		ArgumentNotValidError err = new ArgumentNotValidError();
		err.setTimestamp(Instant.now());
		err.setStatus(HttpStatus.BAD_REQUEST.value());
		err.setError(BAD_REQUEST);
		err.setMessage(e.getBindingResult().getAllErrors().stream().map(er -> er.getDefaultMessage())
				.collect(Collectors.toList()));
		err.setPath(request.getRequestURI());
		return ResponseEntity.status(err.getStatus()).body(err);

	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ArgumentNotValidError> catchConstraintViolation(ConstraintViolationException e,
			HttpServletRequest request) {
		ArgumentNotValidError err = new ArgumentNotValidError();
		err.setTimestamp(Instant.now());
		err.setStatus(HttpStatus.BAD_REQUEST.value());
		err.setError(BAD_REQUEST);
		err.setMessage(
				e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.toList()));
		err.setPath(request.getRequestURI());
		return ResponseEntity.status(err.getStatus()).body(err);

	}

}
