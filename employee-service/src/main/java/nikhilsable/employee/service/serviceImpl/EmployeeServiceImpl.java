package nikhilsable.employee.service.serviceImpl;

import nikhilsable.employee.service.DepartmentClient;
import nikhilsable.employee.service.dto.DepartmentResponse;
import nikhilsable.employee.service.entity.Employee;
import nikhilsable.employee.service.repository.EmployeeRepository;
import nikhilsable.employee.service.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentClient departmentClient;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, DepartmentClient departmentClient) {
        this.employeeRepository = employeeRepository;
        this.departmentClient = departmentClient;
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    public Employee updateEmployee(Long id, Employee employee) {
        Employee existing = employeeRepository.findById(id).orElse(null);

        if (existing == null) {
            return null;
        }

        existing.setFirstName(employee.getFirstName());
        existing.setLastName(employee.getLastName());
        existing.setEmail(employee.getEmail());
        existing.setDepartmentId(employee.getDepartmentId());

        return employeeRepository.save(existing);
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
    @Override
    public DepartmentResponse getDepartmentByEmployeeId(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElse(null);

        if (employee == null) {
            return null;
        }

        return departmentClient.getDepartmentById(
                employee.getDepartmentId()
        );
    }
}