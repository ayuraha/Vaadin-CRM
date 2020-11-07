package com.vaadin.tutorial.crm.backend.service;

import com.vaadin.tutorial.crm.backend.entity.Department;
import com.vaadin.tutorial.crm.backend.entity.Employee;
import com.vaadin.tutorial.crm.backend.enums.Role;
import com.vaadin.tutorial.crm.backend.enums.Status;
import com.vaadin.tutorial.crm.backend.repository.CompanyRepository;
import com.vaadin.tutorial.crm.backend.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor =@__(@Autowired))
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final CompanyRepository companyRepository;

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public long count() {
        return employeeRepository.count();
    }

    public void delete(Employee employee) {
        employeeRepository.delete(employee);
    }

    public void save(Employee employee) {
        if (employee == null) {
            log.debug("Contact is null. Are you sure you have connected your form to the application?");
            return;
        }
        employeeRepository.save(employee);
    }

    public List<Employee> findAll(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return employeeRepository.findAll();
        } else {
            return employeeRepository.search(stringFilter);
        }
    }

    @PostConstruct
    public void populateTestData() {
        if (companyRepository.count() == 0) {
            companyRepository.saveAll(
                    Stream.of("Path-Way Electronics", "E-Tech Management", "Path-E-Tech Management")
                            .map(Department::new)
                            .collect(Collectors.toList()));
        }

        if (employeeRepository.count() == 0) {
            Random r = new Random(0);
            List<Department> companies = companyRepository.findAll();
            employeeRepository.saveAll(
                    Stream.of("Gabrielle Gabrielle Patel", "Brian Brian Robinson", "Eduardo Eduardo Haugen",
                            "Koen Koen Johansen", "Alejandro Alejandro Macdonald", "Angel Angel Karlsson", "Yahir Yahir Gustavsson", "Haiden Haiden Svensson",
                            "Emily Emily Stewart", "Corinne Corinne Davis", "Ryann Ryann Davis", "Yurem Yurem Jackson", "Kelly Kelly Gustavsson",
                            "Eileen Eileen Walker", "Katelyn Katelyn Martin", "Israel Israel Carlsson", "Quinn Quinn Hansson", "Makena Makena Smith",
                            "Danielle Danielle Watson", "Leland Leland Harris", "Gunner Gunner Karlsen", "Jamar Jamar Olsson", "Lara Lara Martin",
                            "Ann Ann Andersson", "Remington Remington Andersson", "Rene Rene Carlsson", "Elvis Elvis Olsen", "Solomon Solomon Olsen",
                            "Jaydan Jaydan Jackson", "Bernard Bernard Nilsen")
                            .map(name -> {
                                String[] split = name.split(" ");
                                Employee employee = new Employee();
                                employee.setUsername(split[0]);
                                employee.setFirstName(split[1]);
                                employee.setLastName(split[2]);
                                employee.setDepartment(companies.get(r.nextInt(companies.size())));
                                employee.setStatus(Status.values()[r.nextInt(Status.values().length)]);
                                employee.setRole(Role.Employee);
                                String email = (employee.getFirstName() + "." + employee.getLastName() + "@" + employee.getDepartment().getName().replaceAll("[\\s-]", "") + ".com").toLowerCase();
                                employee.setEmail(email);
                                return employee;
                            }).collect(Collectors.toList()));
        }
    }
}
