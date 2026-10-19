package nikhilsable.employee.service;

import nikhilsable.employee.service.dto.DepartmentResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class DepartmentClient {

    private final WebClient webClient;

    public DepartmentClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public DepartmentResponse getDepartmentById(Long departmentId) {

        return webClient.get()
                .uri("http://localhost:8081/api/departments/{id}", departmentId)
                .retrieve()
                .bodyToMono(DepartmentResponse.class)
                .block();
    }
}

