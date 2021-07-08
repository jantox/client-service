package au.com.vodafone.clientservice.error;

import au.com.vodafone.clientservice.error.ErrorMessage;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.web.util.HtmlUtils.htmlEscape;

@ControllerAdvice
public class SpringExceptionHandlerAdvice {

    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessage> handleException(Exception exception) {
        return new ResponseEntity<>(new ErrorMessage(htmlEscape(exception.getMessage())), HttpStatus.BAD_REQUEST);
    }
}
