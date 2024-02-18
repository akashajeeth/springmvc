package com.gl.MyController;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gl.model.Employee;

@Controller
public class EmployeeController {
	@RequestMapping("/")
	public String indexPage() {
		return "index";
	}
	
	@RequestMapping("/employee-page")
	public String showAll(Model data) {
		SessionFactory factory=new Configuration().configure().buildSessionFactory();
		Session session=factory.openSession();
		try {
			//select all
			Query q1=session.createQuery("from Employee");
			List<Employee> employees=q1.getResultList();
			data.addAttribute("employees",employees);
		}
		catch(Exception ex) {
			System.out.println("Hybernate error: "+ex.getMessage());
		}
		return "show-all";
	}
			
	@RequestMapping("/add-form")
	public String record() {
		return "AddRecordForm";
	}
	
	@PostMapping("/add-record")
	public String submitRecord(@RequestParam String employeeName,@RequestParam String Address,@RequestParam int employeePhone,@RequestParam float employeeSalary,Model data) {
		
SessionFactory factory=new Configuration().configure().buildSessionFactory();
		
		Session session=factory.openSession();
		try {
			Transaction tx=session.beginTransaction();
			//all the db work
			Employee s1=new Employee(employeeName,Address,employeePhone,employeeSalary);
			session.save(s1);//object converted to insert query
			
			Query q1=session.createQuery("from Employee");
			List<Employee> employees=q1.getResultList();
			data.addAttribute("employees",employees);
			
			tx.commit();
		}
		catch(Exception ex) {
			System.out.println("Hibernate error: "+ex.getMessage());
		}
		return "show-all";
	}
	
	@GetMapping("/update-employee-form")
	public String updateCarform(@RequestParam int id,Model data) {
		 SessionFactory factory=new Configuration().configure().buildSessionFactory();
			
			Session session=factory.openSession();
			try {
				//select * from car where reg=givenreg.no
				Employee updateEmp=session.get(Employee.class, id);
				data.addAttribute("employees",updateEmp);
				
			}
			catch(Exception ex) {
				System.out.println("Hibernate error: "+ex.getMessage());
			}
			return "update-employee-form";
	}
    @PostMapping("/update-save-employee")
	public String updatesavecar(@RequestParam int id,@RequestParam String employeeName,@RequestParam String Address,@RequestParam int employeePhone,@RequestParam float employeeSalary,Model data) {
		
		//session factory
		SessionFactory factory=new Configuration().configure().buildSessionFactory();
		
		Session session=factory.openSession();
		try {
			Transaction tx=session.beginTransaction();
			//all the db work
			Employee e1=new Employee(id,employeeName,Address,employeePhone,employeeSalary);
			session.update(e1);//object converted to update query
			
			Query q1=session.createQuery("from Employee");
			List<Employee> employees=q1.getResultList();
			data.addAttribute("employees",employees);
			
			tx.commit();
		}
		catch(Exception ex) {
			System.out.println("Hibernate error: "+ex.getMessage());
		}
		
		return "show-all";
	}
    @GetMapping("/delete-employee")
	public String deletecar(@RequestParam int id,Model data) {
		 SessionFactory factory=new Configuration().configure().buildSessionFactory();
			
			Session session=factory.openSession();
			try {
				Transaction tx=session.beginTransaction();
				Employee deleteCar=new Employee(id," ","",0, 0);
				session.delete(deleteCar);
				tx.commit();
			}
			catch(Exception ex) {
				System.out.println("Hibernate error: "+ex.getMessage());
			}
			return "index";
	} 

}
