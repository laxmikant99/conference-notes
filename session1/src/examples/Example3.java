package examples;

import java.util.Arrays;
import java.util.List;

//Problem statement: Given a list of values , doulbe the even number and get the total. (imperative vs functional) 
public class Example3 {
	public static void main(String[] args) {

		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);

		// functional style
		int sum = numbers.stream()
				.reduce(0, (total, e) -> Integer.sum(total, e));
		System.out.println(sum);

		// method reference with two parameters. parameter order is important
		sum = numbers.stream()
				.reduce(0, Integer::sum);
		System.out.println(sum);
		
		// another example of method reference
		String s = numbers.stream()
				.map(String::valueOf)
				.reduce("", (carry, str) -> carry.concat(str));
		System.out.println(s);
		s = numbers.stream()
				.map(String::valueOf)
				.reduce("", String::concat);
		System.out.println(s);

		// anonymous class
		EmployeeFactory empFactory = new EmployeeFactory() {

			@Override
			public Employee getEmployee(int id, String name, int age) {
				return new Employee(id, name, age);
			}
		};
		// lamda
		empFactory = (id, name, age) -> (new Employee(id, name, age));
		// Constructor reference.
		empFactory = Employee::new;
		System.out.println(empFactory.getEmployee(1, "Ram", 25));
	}

}
