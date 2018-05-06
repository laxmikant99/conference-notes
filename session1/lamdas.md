**Lamda Expression**

1.  Lamda is an anonymous function
2.  Function typically has 4 parts (name (can be anonymous),parameter list, body and return type (inferred) ) however lamda has only parameter list and body
3.  An example

		// java 7 style
		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println("In new thread");

			}
		});
		// intention is to print a line in another thread ..but we are doing lot
		// of things here.. Like kid can go to park alone he will have go with parent
		// java 8 style
		th = new Thread(() -> System.out.println("In new thread"));
		th.start();
		System.out.println("In main thread");

5.  Java supports backward compatibility however c# and scala not.
6.  When you transform your code from java7 to java8 ..your code go through a weight loss program and you see 40-50% code weight loss depending on what part of code it is.

**Lamda Under the hood:**

6. Don’t get an illusion that lamdas are syntactic sugar . Like compiler will convert back the lamdas to anonymous Runnable. That means 100 Runnable lamda ..means 100 classname.$N.class. 
Issues:  Big jar file, Loading  jar file will take more time and loading will require more memory foot print,  more garbage, more time spent in GC.
7.  InvokeDynamic feature introduced in java7, allows the JVM to generate that class bytecode in runtime. The subsequent calls to the same statement use a cached version.

 **Imparative and fuctional style : example for loop**

8.  Exteral iterator : you have complete control on iteration. 
 Disadvantage : The more you have control more you have to change if needed ! It is not simple for loop but it is familiar. Simple means it is easy to use, no unnecessary complexity, it don’t burdan you to do which u don’t want to do .
9.  Internal iterator: How to iterate is hidden behind the implementation of object and you just need to tell what to do not how to do.

10.  Beautiful Transformation from anonymous to lamda

    List<Integer> list = Arrays.asList(1, 2, 3);
    		// external iterator
    		/*
    		 * for (int i = 0; i < list.size(); i++) {
    		 * System.out.println(list.get(i)); }
    		 */
    		// still external
    		for (Integer integer : list) {
    			System.out.println(integer);
    		}
    
    		// internal
    		/*
    		 * list.forEach(new Consumer<Integer>() {
    		 * 
    		 * @Override public void accept(Integer t) { System.out.println(t);
    		 * 
    		 * } });
    		 */
    
    		// internal iterator
    		// list.forEach((Integer t) -> System.out.println(t));
    
    		// type inference is allowed in lamda
    		// list.forEach( (t) -> System.out.println(t));
    
    		// Parenthesis is optional for single parameter
    		// list.forEach( t -> System.out.println(t));
    
    		// For simple pass through, method reference can be used. method reference can be passed as argument in method
    		list.forEach(System.out::println);

11.  Lamda are cute so keep it that way . Don’t write 20-30 code so that easier to read and understand .  Multiple line of code causes cluttering, noisy and cause duplicate code. Lamda are glue code 2 liner is too many :)

 **Method reference:**

12.  Method reference (instance or static) can be passed as argument in method (eg: above in for loop)

    numbers.stream.map(String::valueOf).forEach( System.out::print);

13.  Method reference: What if more than 1 paratmers ?

            // functional style
    		int sum = numbers.stream()
    				.reduce(0, (total, e) -> Integer.sum(total, e));
    		System.out.println(sum);
    
    		// method reference with two parameters. parameter order is important
    		sum = numbers.stream()
    				.reduce(0, Integer::sum);
    		System.out.println(sum);

14.  Given a list of values , doulbe the even number and get the total. (imperative vs functional)

//  Spaghetti you have to go through back and forth to understand the code
               

      for (int integer : numbers) {
		if (integer % 2 == 0) {
			sum = sum += 2 * integer;
		}
	}
	System.out.println(sum);

// think of stream as a fancy iterator


		sum = numbers.stream()
				.filter(n -> n % 2 == 0)
				.map(n -> n * 2)
				.reduce(0, Integer::sum);

//no more spaghetti a ,single pass through :  Given a values: filter them: double them and reduce  or better Given a values: fiter them: double them and sum .So increases the readabity .

		sum = numbers.stream()
			.filter(n -> n % 2 == 0)
			.mapToInt(n -> n * 2)
			.sum();



15. .  Another example  and survey

        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
		// Imperative style (study 85 sec, 82 % correct as dev are familiar)
		int result = 0;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) > 5 && isEven(list.get(i)) && list.get(i) < 9
					&& list.get(i) * 2 > 15) {
				result = list.get(i);
				break;
			}
		}

		System.out.println(result);
		/* functional style:
		(study 55 sec to understand but 75 % gave correct
		 answer, interesting thing ppl got declarative wrong got it very
		 quicky..so I would like to fail fast rather than fail slow )*/
		result = list.stream()
				.filter(n -> n > 5)
				.filter(n -> isEven(n))
				.filter(n -> n < 9)
				.filter(n -> n * 2 > 15)
				.findFirst()
				.get();

		System.out.println(result);
1.  Above is an example of function composition . Don’t write horizontally but rather write vertically so that every single line does one thing cohesively.

**ParallelStream** example ; Be very careful while using parallel stream, (eg. How to search your missing phone in  a hall ? if you tell all then u employed all resources)

1.  It makes sense when problem is parallel-eligible
2.  When data size is big enough that gives benefit in performance
3.  When computation is big enough that gives benefit in performance

4.  ParallelStream Example ( sleep in one compute and check the advantace )


		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

		Runnable seqTask = () -> numbers.stream()
				.filter(n -> n % 2 == 0)
				.mapToInt(Example6::compute)
				.sum();
		Timewait.get(seqTask);

		Runnable parallelTask = () -> numbers.parallelStream()
				.filter(n -> n % 2 == 0)
				.mapToInt(Example6::compute)
				.sum();
		Timewait.get(parallelTask);
    	}

    	private static int compute(int n) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return n * 2;
	    }

	    static class Timewait {
		public static void get(Runnable block) {
			Long start = System.nanoTime();
			try {
				block.run();
			} finally {
				Long end = System.nanoTime();
				System.out.println("time taken :" + (end - start) / 1.0e9);
			}
		}
	    }
