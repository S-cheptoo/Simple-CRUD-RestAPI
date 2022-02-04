package com.restfulapicrud.controller;

import com.restfulapicrud.exceptions.ResourceNotFoundException;
import com.restfulapicrud.model.Employee;
import com.restfulapicrud.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    //get all employees
    @GetMapping("/employees")
    public List<Employee> getEmployees(@Valid @RequestBody Employee employee){
        return employeeRepository.findAll();
    }

    //create an employee
    @PostMapping("/create-employees")
    public Employee saveEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    //get employee by id
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee>  getEmployeeById(@PathVariable (value="id") long employeeId)
            throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " +employeeId));
            return ResponseEntity.ok().body(employee);
    }

    //update an employee
    @PutMapping("/employees/edit/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable (value="id") long employeeId
    , @RequestBody Employee employeeDetails)
                          throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id ::" + employeeId));
        employee.setFirstname(employeeDetails.getFirstname());
        employee.setLastname(employeeDetails.getLastname());
        employee.setEmail(employeeDetails.getEmail());
        employeeRepository.save(employee);
        return ResponseEntity.ok().body(employee);
    }

//    //delete employee
//    @DeleteMapping("/employees/delete/{id}")
//    public ResponseEntity<?> deleteEmployee(@PathVariable (value="id") long employeeId)
//            throws ResourceNotFoundException {
//        Employee employee = employeeRepository.findById(employeeId)
//                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id ::" + employeeId));
//        employeeRepository.deleteById(employeeId);
//        return ResponseEntity.ok().build();
//    }
}
