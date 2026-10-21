package nikhilsable.employee.service;

import nikhilsable.employee.service.dto.DepartmentResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class DepartmentClient {

    private final WebClient webClient;

    public DepartmentClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    // GET: Fetch all departments
    public List<DepartmentResponse> getAllDepartments() {
        return webClient.get()
                .uri("http://dept-service/api/departments")
                .retrieve()
                .bodyToFlux(DepartmentResponse.class)
                .collectList()
                .block();
    }

    // GET: Fetch department by ID
    public DepartmentResponse getDepartmentById(Long departmentId) {
        return webClient.get()
                .uri("http://dept-service/api/departments/{id}", departmentId)
                .retrieve()
                .bodyToMono(DepartmentResponse.class)
                .block();
    }

    // POST: Create department
    public DepartmentResponse createDepartment(
            DepartmentResponse department) {
        return webClient.post()
                .uri("http://dept-service/api/departments")
                .bodyValue(department)
                .retrieve()
                .bodyToMono(DepartmentResponse.class)
                .block();
    }

    // PUT: Update department
    public DepartmentResponse updateDepartment(
            Long departmentId, DepartmentResponse department) {
        return webClient.put()
                .uri("http://dept-service/api/departments/{id}", departmentId)
                .bodyValue(department)
                .retrieve()
                .bodyToMono(DepartmentResponse.class)
                .block();
    }

    // DELETE: Delete department
    public void deleteDepartment(Long departmentId) {
        webClient.delete()
                .uri("http://dept-service/api/departments/{id}", departmentId)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
