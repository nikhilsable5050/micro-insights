package nikhilsable.dept.service.serviceImpl;

import nikhilsable.dept.service.entity.Department;
import nikhilsable.dept.service.repository.DepartmentRepository;
import nikhilsable.dept.service.service.DepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id).orElse(null);
    }

    @Override
    public Department updateDepartment(Long id, Department department) {
        Department existing = departmentRepository.findById(id).orElse(null);

        if (existing == null) {
            return null;
        }

        existing.setName(department.getName());
        existing.setDescription(department.getDescription());

        return departmentRepository.save(existing);
    }

    @Override
    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }
}
