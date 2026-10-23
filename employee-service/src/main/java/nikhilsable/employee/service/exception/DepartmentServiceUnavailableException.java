package nikhilsable.employee.service.exception;

public class DepartmentServiceUnavailableException
        extends RuntimeException {

    public DepartmentServiceUnavailableException(String message,
                                                 Throwable cause) {
        super(message, cause);
    }
}