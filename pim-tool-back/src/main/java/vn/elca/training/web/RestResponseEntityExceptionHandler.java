package vn.elca.training.web;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import vn.elca.training.model.entity.ErrorResponse;
import vn.elca.training.model.exception.*;

import javax.persistence.*;

@ControllerAdvice
@CrossOrigin(origins = "http://localhost:4200")
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { PersistenceException.class})
    protected ResponseEntity<Object> databaseExceptionHandle(RuntimeException ex, WebRequest request){
        ErrorResponse errorResponse = new ErrorResponse("database error", ex.getMessage());
        return super.handleExceptionInternal(ex, errorResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
    @ExceptionHandler(value = {GroupNotFoundException.class, ProjectNotFoundException.class})
    protected ResponseEntity<Object> objectNotFoundHandle(Exception ex, WebRequest request){
        ErrorResponse errorResponse = new ErrorResponse("object not found", ex.getMessage());
        return super.handleExceptionInternal(ex, errorResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
    @ExceptionHandler(EndDateB4StartDateException.class)
    protected ResponseEntity<Object> endDateBeforeStartDateHandle(Exception ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse("end date conflicts", ex.getMessage());
        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
    @ExceptionHandler(ProjectNumberAlreadyExistsException.class)
    protected ResponseEntity<Object> projectNumberIsAlreadyExistHandle(Exception ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse("project number exist", ex.getMessage());
        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
    @ExceptionHandler(StatusNotAvailableException.class)
    protected ResponseEntity<Object> statusNotAvailableHandle(Exception ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse("status not available", ex.getMessage());
        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE, request);
    }
    @ExceptionHandler(VisaNotExistException.class)
    protected ResponseEntity<Object> visaNotExistHandle(VisaNotExistException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse("visa not available", ex.getVisas());
        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

//    @ExceptionHandler(ApplicationUnexpectedException.class)
//    protected ResponseEntity<Object> applicationUnexpectedHandle(RuntimeException ex, WebRequest request) {
//        String bodyResponse = ex.getMessage();
//        return handleExceptionInternal(ex, bodyResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
//    }

}