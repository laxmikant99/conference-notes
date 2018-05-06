package examples;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Example7 {
	public static void main(String[] args) {

		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

		// Problem statement: Double the even values an put them into list!
		List<Integer> doubleValueList = new ArrayList<>();
		numbers.stream()
				.filter(n -> n % 2 == 0)
				.map(n -> n * 2)
				.forEach(n -> doubleValueList.add(n)); // Don’t do this !!

		// A stream operation in itself is a purely local operation however
		// above piece is not thread safe however below operation is.

		numbers.stream()
				.filter(n -> n % 2 == 0)
				.map(n -> n * 2)
				.collect(toList());

		// Create a map with name +'-'+ age a key , and employee as value. toMap
		// expects only unique key in case duplicate key use groupBy
		/*
		 * Map<String, Employee> map = getEmployees().stream()
		 * .collect(Collectors.toMap(e -> e.getName() + "_" + e.getAge(), e ->
		 * e));
		 */

		// Given a list of employees : Create a map with name as key and value
		// is all the people with that name.
		Map<Object, List<Employee>> map = getEmployees().stream()
				.collect(groupingBy(Employee::getName));

		System.out.println(map);

		// Grouping and further mapping : Given a list of employees : Create a map
		// with name as key and value is all the ages with that name
		Map<Object, List<Integer>> map1 = getEmployees().stream()
				.collect(
						groupingBy(Employee::getName,
								mapping(Employee::getAge, toList())));
		System.out.println(map1);
		
		//Extra1: Given a list of employees : Find the average age of employees group by name.
		
		Map<Object, Double> map2 = getEmployees().stream()
				.collect(
						groupingBy(Employee::getName,
								Collectors.averagingInt(Employee::getAge)));
		System.out.println(map2);
		
		//Extra2: Given a list of employees : Find the average age of employees group by name and sorted by name.
		
		Map<Object, Double> map3 = getEmployees().stream()
				.collect(
						groupingBy(Employee::getName, TreeMap::new,
								Collectors.averagingInt(Employee::getAge)));
		System.out.println(map3);
		
		//Extra3 sort a map by value : keymapper, valuemapper, merger for value and mapSupplier
		
		Map<Object,Object> sortedMap = 
				getUnsortedMap().entrySet().stream()
			    .sorted(Entry.comparingByValue())
			    .collect(Collectors.toMap(Entry::getKey, Entry::getValue,
			                              (e1, e2) -> e1, LinkedHashMap::new));
		
		System.out.println(sortedMap);
		
		
	}

	private static Map<String, Integer> getUnsortedMap() {
		Map<String, Integer> unsortedMap = new HashMap<String, Integer>();
        unsortedMap.put("B", 55);
        unsortedMap.put("A", 80);
        unsortedMap.put("D", 20);
        unsortedMap.put("C", 70);
        unsortedMap.put("M", 70);		
        return unsortedMap;
	}

	private static List<Employee> getEmployees() {
		EmployeeFactory empFactory = Employee::new;
		List<Employee> e = Arrays.asList(empFactory.getEmployee(1, "Ram", 25),
				empFactory.getEmployee(2, "John", 40),
				empFactory.getEmployee(3, "Geeta", 25),
				empFactory.getEmployee(3, "Sam", 35),
				empFactory.getEmployee(4, "Geeta", 25));
		return e;
	}

}
