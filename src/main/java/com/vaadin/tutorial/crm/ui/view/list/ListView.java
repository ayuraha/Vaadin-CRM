package com.vaadin.tutorial.crm.ui.view.list;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.tutorial.crm.backend.entity.Department;
import com.vaadin.tutorial.crm.backend.entity.Employee;
import com.vaadin.tutorial.crm.backend.service.DepartmentService;
import com.vaadin.tutorial.crm.backend.service.EmployeeService;
import com.vaadin.tutorial.crm.ui.MainLayout;

@Route(value="", layout = MainLayout.class)
@PageTitle("Employee | Vaadin CRM")
public class ListView extends VerticalLayout {

    private final ContactForm form;
    Grid<Employee> grid = new Grid<>(Employee.class);
    TextField filterText = new TextField();

    private EmployeeService employeeService;

    public ListView(EmployeeService employeeService,
                    DepartmentService departmentService) {
        this.employeeService = employeeService;
        addClassName("list-view");
        setSizeFull();

        configureGrid();

        HorizontalLayout toolbar = getToolbar();

        form = new ContactForm(departmentService.findAll());
        form.addListener(ContactForm.SaveEvent.class, this::saveContact);
        form.addListener(ContactForm.DeleteEvent.class, this::deleteContact);
        form.addListener(ContactForm.CloseEvent.class, e -> closeEditor());

        Div content = new Div(grid, form);
        content.addClassName("content");
        content.setSizeFull();

        add(toolbar, content);
        updateList();

        closeEditor();
    }

    private void saveContact(ContactForm.SaveEvent event) {
        employeeService.save(event.getContact());
        updateList();
        closeEditor();
    }

    private void deleteContact(ContactForm.DeleteEvent event) {
        employeeService.delete(event.getContact());
        updateList();
        closeEditor();
    }

    private void configureGrid() {
        grid.addClassName("contact-grid");
        grid.setSizeFull();
        grid.removeColumnByKey("department");
        grid.setColumns("username", "firstName", "lastName", "email", "status");
        grid.addColumn(contact -> {
            Department department = contact.getDepartment();
            return department == null ? "-" : department.getName();
        }).setHeader("Department");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event -> editContact(event.getValue())) ;
    }

    public void editContact(Employee employee) {
        if (employee == null) {
            closeEditor();
        } else {
            form.setContact(employee);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addContactButton = new Button("Add contact");
        addContactButton.addClickListener(click -> addContact());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void updateList() {
        grid.setItems(employeeService.findAll(filterText.getValue()));
    }

    void addContact() {
        grid.asSingleSelect().clear();
        editContact(new Employee());
    }

    private void closeEditor() {
        form.setContact(null);
        form.setVisible(false);
        removeClassName("editing");
    }
}