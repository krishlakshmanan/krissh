package com.krissh.samples;

import com.krissh.convertz.Convertz;
import com.krissh.convertz.IConvertz;
import com.krissh.dtos.EmployeeDto;
import com.krissh.entities.Employee;

public class ConvertzSample {
	
	private static IConvertz convertz = new Convertz();
	
	public static void main(String args[]) {
	
		Employee employee = new Employee();
		employee.setId(1);
		employee.setName("Krish Lakshmanan");
		employee.setAge(24);
		employee.setGender("Male");
		
		EmployeeDto employeeDto = new EmployeeDto();
		
		convertz.ctrlC(employee, employeeDto);
		
		System.out.println("Employee - "+employee.getName() + " : Employee dto - "+employeeDto.getName());
	}
}
