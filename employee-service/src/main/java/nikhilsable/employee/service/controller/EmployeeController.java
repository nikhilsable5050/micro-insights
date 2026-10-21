package nikhilsable.employee.service.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import nikhilsable.employee.service.dto.DepartmentResponse;
import nikhilsable.employee.service.entity.Employee;
import nikhilsable.employee.service.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @GetMapping("/config-test")
    public String configTest(
            @Value("${app.message}") String message,
            @Value("${app.owner}") String owner) {
        return message + " | " + owner;
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(
            @RequestBody Employee employee) {

        Employee saved = employeeService.saveEmployee(employee);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(
            @PathVariable Long id) {

        Employee employee = employeeService.getEmployeeById(id);

        if (employee == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable Long id,
            @RequestBody Employee employee) {

        Employee updated = employeeService.updateEmployee(id, employee);

        if (updated == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(
            @PathVariable Long id) {

        if (employeeService.getEmployeeById(id) == null) {
            return ResponseEntity.notFound().build();
        }

        employeeService.deleteEmployee(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/department")
    public ResponseEntity<DepartmentResponse> getDepartmentByEmployeeId(
            @PathVariable Long id) {

        DepartmentResponse response =
                employeeService.getDepartmentByEmployeeId(id);

        if (response == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }

    // GET: Fetch all departments
    @GetMapping("/departments")
    public ResponseEntity<List<DepartmentResponse>> getAllDepartments() {
        return ResponseEntity.ok(employeeService.getAllDepartments());
    }

    // GET: Fetch department by ID
    @GetMapping("/departments/{id}")
    public ResponseEntity<DepartmentResponse> getDepartmentById(
            @PathVariable Long id) {

        DepartmentResponse response =
                employeeService.getDepartmentById(id);

        if (response == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }

    // POST: Create department
    @PostMapping("/departments")
    public ResponseEntity<DepartmentResponse> createDepartment(
            @RequestBody DepartmentResponse department) {

        DepartmentResponse response =
                employeeService.createDepartment(department);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // PUT: Update department
    @PutMapping("/departments/{id}")
    public ResponseEntity<DepartmentResponse> updateDepartment(
            @PathVariable Long id,
            @RequestBody DepartmentResponse department) {

        DepartmentResponse response =
                employeeService.updateDepartment(id, department);

        if (response == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }

    // DELETE: Delete department
    @DeleteMapping("/departments/{id}")
    public ResponseEntity<Void> deleteDepartment(
            @PathVariable Long id) {

        employeeService.deleteDepartment(id);

        return ResponseEntity.noContent().build();
    }

}