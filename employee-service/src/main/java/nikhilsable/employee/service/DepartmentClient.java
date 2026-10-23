package nikhilsable.employee.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import nikhilsable.employee.service.dto.DepartmentResponse;
import nikhilsable.employee.service.exception.DepartmentServiceUnavailableException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.List;

@Service
public class DepartmentClient {

    private final WebClient webClient;

    public DepartmentClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    // GET: Fetch all departments
    @CircuitBreaker(
            name = "departmentService",
            fallbackMethod = "getAllDepartmentsFallback"
    )
    @Retry(name = "departmentService")
    public List<DepartmentResponse> getAllDepartments() {
        return webClient.get()
                .uri("http://dept-service/api/departments")
                .retrieve()
                .bodyToFlux(DepartmentResponse.class)
                .collectList()
                .timeout(Duration.ofSeconds(3))
                .block();
    }

    public List<DepartmentResponse> getAllDepartmentsFallback(
            Throwable exception) {
        throw new DepartmentServiceUnavailableException(
                "Department Service is unavailable. "
                        + "Unable to fetch departments.",
                exception
        );
    }

    // GET: Fetch department by ID
    @CircuitBreaker(
            name = "departmentService",
            fallbackMethod = "getDepartmentByIdFallback"
    )
    @Retry(name = "departmentService")
    public DepartmentResponse getDepartmentById(Long departmentId) {
        return webClient.get()
                .uri("http://dept-service/api/departments/{id}", departmentId)
                .retrieve()
                .bodyToMono(DepartmentResponse.class)
                .timeout(Duration.ofSeconds(3))
                .block();
    }

    public DepartmentResponse getDepartmentByIdFallback(
            Long departmentId, Throwable exception) {
        throw new DepartmentServiceUnavailableException(
                "Department Service is unavailable. "
                        + "Unable to fetch department " + departmentId + ".",
                exception
        );
    }

    // POST: Create department
    @CircuitBreaker(
            name = "departmentService",
            fallbackMethod = "createDepartmentFallback"
    )
    @Retry(name = "departmentService")
    public DepartmentResponse createDepartment(
            DepartmentResponse department) {
        return webClient.post()
                .uri("http://dept-service/api/departments")
                .bodyValue(department)
                .retrieve()
                .bodyToMono(DepartmentResponse.class)
                .timeout(Duration.ofSeconds(3))
                .block();
    }

    public DepartmentResponse createDepartmentFallback(
            DepartmentResponse department, Throwable exception) {
        throw new DepartmentServiceUnavailableException(
                "Department Service is unavailable. "
                        + "Department creation was not confirmed.",
                exception
        );
    }

    // PUT: Update department
    @CircuitBreaker(
            name = "departmentService",
            fallbackMethod = "updateDepartmentFallback"
    )
    @Retry(name = "departmentService")
    public DepartmentResponse updateDepartment(
            Long departmentId, DepartmentResponse department) {
        return webClient.put()
                .uri("http://dept-service/api/departments/{id}", departmentId)
                .bodyValue(department)
                .retrieve()
                .bodyToMono(DepartmentResponse.class)
                .timeout(Duration.ofSeconds(3))
                .block();
    }

    public DepartmentResponse updateDepartmentFallback(
            Long departmentId,
            DepartmentResponse department,
            Throwable exception) {
        throw new DepartmentServiceUnavailableException(
                "Department Service is unavailable. "
                        + "Department update was not confirmed.",
                exception
        );
    }

    // DELETE: Delete department
    @CircuitBreaker(
            name = "departmentService",
            fallbackMethod = "deleteDepartmentFallback"
    )
    @Retry(name = "departmentService")
    public void deleteDepartment(Long departmentId) {
        webClient.delete()
                .uri("http://dept-service/api/departments/{id}", departmentId)
                .retrieve()
                .toBodilessEntity()
                .timeout(Duration.ofSeconds(3))
                .block();
    }

    public void deleteDepartmentFallback(
            Long departmentId, Throwable exception) {
        throw new DepartmentServiceUnavailableException(
                "Department Service is unavailable. "
                        + "Department deletion was not confirmed.",
                exception
        );
    }
}
