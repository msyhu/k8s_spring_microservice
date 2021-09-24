package vmware.services.organization.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DepartmentEmployeeOrganization {
    private Long id;
    private String departName;
    private List<Employee> employees = new ArrayList<>();

    private String employeeName;
    private int age;
    private String position;

    private String organizationName;
    private String address;
    private List<Department> departments = new ArrayList<>();

    @Override
    public String toString() {
        return "Department [id=" + id + ", name=" + departName + "]"
                + "Employee [id=" + id + ", name=" + employeeName + ", position=" + position + "]"
                + "Organization [id=" + id + ", name=" + organizationName + ", address=" + address + "]"
                ;
    }
}
