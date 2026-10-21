package nikhilsable.employee.service.service;

import nikhilsable.employee.service.dto.DepartmentResponse;
import nikhilsable.employee.service.entity.Employee;

import java.util.List;

public interface EmployeeService {

    // Employee CRUD
    Employee saveEmployee(Employee employee);

    List<Employee> getAllEmployees();

    Employee getEmployeeById(Long id);

    Employee updateEmployee(Long id, Employee employee);

    void deleteEmployee(Long id);

    // Get department associated with an employee
    DepartmentResponse getDepartmentByEmployeeId(Long employeeId);

    // Department CRUD using WebClient
    List<DepartmentResponse> getAllDepartments();

    DepartmentResponse getDepartmentById(Long departmentId);

    DepartmentResponse createDepartment(DepartmentResponse department);

    DepartmentResponse updateDepartment(
            Long departmentId, DepartmentResponse department);

    void deleteDepartment(Long departmentId);
}