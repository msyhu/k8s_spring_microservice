package vmware.services.organization.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vmware.services.organization.client.DepartmentClient;
import vmware.services.organization.client.EmployeeClient;
import vmware.services.organization.model.Department;
import vmware.services.organization.model.DepartmentEmployeeOrganization;
import vmware.services.organization.model.Employee;
import vmware.services.organization.model.Organization;
import vmware.services.organization.repository.OrganizationRepository;

import java.util.Optional;

@RestController
public class OrganizationController {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationController.class);
	
	@Autowired
	OrganizationRepository repository;
	@Autowired
    DepartmentClient departmentClient;
	@Autowired
	EmployeeClient employeeClient;
	
	@PostMapping
	public Organization add(@RequestBody Organization organization) {
		LOGGER.info("Organization add: {}", organization);
		return repository.save(organization);
	}
	
	@GetMapping
	public Iterable<Organization> findAll() {
		LOGGER.info("Organization find");
		return repository.findAll();
	}
	
	@GetMapping("/{id}")
	public Organization findById(@PathVariable("id") String id) {
		LOGGER.info("Organization find: id={}", id);
		return repository.findById(id).get();
	}

	@GetMapping("/{id}/with-departments")
	public Organization findByIdWithDepartments(@PathVariable("id") String id) {
		LOGGER.info("Organization find: id={}", id);
		Optional<Organization> organization = repository.findById(id);
		if (organization.isPresent()) {
			Organization o = organization.get();
			o.setDepartments(departmentClient.findByOrganization(o.getId()));
			return o;
		} else {
			return null;
		}
	}
	
	@GetMapping("/{id}/with-departments-and-employees")
	public Organization findByIdWithDepartmentsAndEmployees(@PathVariable("id") String id) {
		LOGGER.info("Organization find: id={}", id);
		Optional<Organization> organization = repository.findById(id);
		if (organization.isPresent()) {
			Organization o = organization.get();
			o.setDepartments(departmentClient.findByOrganizationWithEmployees(o.getId()));
			return o;
		} else {
			return null;
		}
	}
	
	@GetMapping("/{id}/with-employees")
	public Organization findByIdWithEmployees(@PathVariable("id") String id) {
		LOGGER.info("Organization find: id={}", id);
		Optional<Organization> organization = repository.findById(id);
		if (organization.isPresent()) {
			Organization o = organization.get();
			o.setEmployees(employeeClient.findByOrganization(o.getId()));
			return o;
		} else {
			return null;
		}
	}

	@PostMapping("/addorganizationdepartmentemployee")
	public DepartmentEmployeeOrganization addOrganizationDepartmentEmployee(@RequestBody DepartmentEmployeeOrganization deo) {
		LOGGER.info("addOrganizationDepartmentEmployee={}", deo);

		Organization o = new Organization();
		o.setId(String.valueOf(deo.getId()));
		o.setName(deo.getOrganizationName());
		o.setAddress(deo.getAddress());
		o.setDepartments(deo.getDepartments());
		o.setEmployees(deo.getEmployees());
		add(o);

		Employee e = new Employee();
		e.setId(deo.getId());
		e.setName(deo.getEmployeeName());
		e.setAge(deo.getAge());
		e.setPosition(deo.getPosition());
		employeeClient.add(e);

		Department d = new Department();
		d.setId(deo.getId());
		d.setName(deo.getDepartName());
		d.setEmployees(deo.getEmployees());
		departmentClient.add(d);

		return deo;
	}

	@GetMapping("/findorganizationdepartmentemployee/{id}")
	public String findOrganizationDepartmentEmployee(@PathVariable("id") String id) {
		LOGGER.info("findOrganizationDepartmentEmployee : id={}", id);
		Organization o = repository.findById(id).get();
		Employee e = employeeClient.findById(id);
		Department d = departmentClient.findById(id);

		return o.toString() + e.toString() + d.toString();
	}

}


