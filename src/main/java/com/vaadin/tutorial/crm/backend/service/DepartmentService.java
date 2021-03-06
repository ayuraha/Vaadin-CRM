package com.vaadin.tutorial.crm.backend.service;

import com.vaadin.tutorial.crm.backend.entity.Department;
import com.vaadin.tutorial.crm.backend.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DepartmentService {

    private CompanyRepository companyRepository;

    public DepartmentService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Department> findAll() {
        return companyRepository.findAll();
    }

    public Map<String, Integer> getStats() {
        HashMap<String, Integer> stats = new HashMap<>();
        findAll().forEach(company -> stats.put(company.getName(), company.getEmployees().size()));
        return stats;
    }
}
