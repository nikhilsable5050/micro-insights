
package nikhilsable.employee.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class DepartmentResponse {

    private Long id;
    private String name;
    private String description;
}
