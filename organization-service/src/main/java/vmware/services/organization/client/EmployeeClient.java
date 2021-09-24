package vmware.services.organization.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import vmware.services.organization.model.Employee;

import java.util.List;

@FeignClient(name = "employee")
public interface EmployeeClient {

	@GetMapping("/organization/{organizationId}")
	List<Employee> findByOrganization(@PathVariable("organizationId") String organizationId);

	@GetMapping("/{id}")
	Employee findById(@PathVariable("id") String id);

	@PostMapping("/")
	public Employee add(@RequestBody Employee employee);
}
