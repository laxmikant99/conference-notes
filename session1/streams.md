**Stream api**

1.  When lamda is cool but stream is awesome.  Stream is abstraction. Stream is not physical object , it does not contain any data unlike list or map but it is bunch of function which will be evaluated eventually.
2.  Stream is  non mutating pipeline (you create a pipe line of transformation using fucntion composition but without  mutating the data  )
3.  Stream filter:  no. of input <= no. of o/p; Stream<T> will take predicate<T>..as lamdas are backed by FI so predicate FI it will be used.
4.  Stream map :  is transforming fuction :  no. of i/p == no. of o/p ; however no guaranty of type equality : map of stream<T> takes Function<T,R> to return Stream<T>
5.  Both filter and map stay within their swim-lane :  minding
6.  Stream reduce  : cut across the swim-lane :  it checks what my initial value is , and next value is dependent of previous result (becomes feedback to next op) while evolution (example product  of numbers )

           filter   map                reduce(0)
    
        -----------------------------------\
        X1  |                                \
        --------------------------------------\
        X2  ->       x2`                       +
        -----------------------------------------\
        X3  ->        x3`                          + 
        --------------------------------------------\

Reduce on stream<T> will take 2 paramters  first is the paramter of type T and next is BiFucntion(R,T,R)

1.  Some special reduce function example sum, count, average, Collectors apis
2.  Reduce bring value together where filter/map move forward/transform;  Reduce may transform to a single value or a single non stream and concrete type

**Collect :** A special reduce operation :

1. Double the even valeus an put them into list!


        List<Integer> doubleValueList = new ArrayList<>();
    		numbers.stream()
    				.filter(n -> n % 2 == 0)
    				.map(n -> n * 2)
    				.forEach(n -> doubleValueList.add(n)); // Don’t do this !!

 
 mutablity is ok, sharing is nice but shared mutability is devils work!

#Life lesson :) Friends don’t let friend do shared mutation . What's wrong ? Parallel stream-> multiple threads changing at same time -> race condition.

Collect provides you thread safety as you are not sharing here.
   
1.  Collect -> toList, toSet etc.          

        // A stream operation in itself is a purely local operation however
	    // above piece is not thread safe however below operation is.
	           numbers.stream()
			        .filter(n -> n % 2 == 0)
			         .map(n -> n * 2)
			         .collect(toList());

2.  Collet toMap Given a list of employee : Create a map with name +'-'+ age a key , and employee as value.



         Map<String, Employee> map = getEmployees().stream()
        .collect(Collectors.toMap(e -> e.getName() + "_" + e.getAge(), e -> e));
	 
 **Collect groupBy**

3. Given a list of employee: Create a map with name as key and value is all the employee with that name

       Map<Object, List<Employee>> map = getEmployees().stream()
    				.collect(groupingBy(Employee::getName));
    		System.out.println(map);

1.  Grouping and further mapping : Given a list of person : Create a map with name as key and value is all the ages with that name

        Map<Object, List<Integer>> map1 = getEmployees().stream()
    				.collect(groupingBy(Employee::getName,
    								mapping(Employee::getAge, toList())));
    		System.out.println(map1);

1. problem:  Given an ordered list find the double of the first even number greater than 3


       List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
				11, 12, 13, 14, 15, 16, 17, 18, 19, 20);

		int result = 0;
		// how much work?  8 unit of work
		for (int n : numbers) {
			if (n > 3 && n % 2 == 0) {
				result = 2 * n;
				break;
			}
		}
		System.out.println(result);
		// how much work?  
		Optional<Integer> result1 = numbers.stream()
				.filter(Example8::isGT3)
				.filter(Example8::isEven)
				.map(Example8::doubleIt)
				.findFirst();
		System.out.println(result1.get());

Functional look good ; But **what about the performance** ?

1.  Streams are lazy and efficient (eg: kid, mom commands but it will be executed only if daddy comes) 
2. it just builds pipe line of function and does nothing but when you call the terminator only  it executes the pipeline

Ex: find first person wearing: black t- shirt, white shoes, hat : 1 way is to call all ppl who is wearing black come on stage (2 list) , then white shoes (3 list) -> then wearing hat (4 list) and find fist. But that not how stream works.

Rather You take one element and apply intire vertical sequence  of fuction on that elelent and then move next. If not terminal op then no evaluation will take plane.

Hence it give efficient performance , cleaner and  readable

  **Characteristics of stream**:  sized/unbound , ordered/unordered,  distinct/duplicate, sorted/unsorted

Characteristics depends on source or some typical functions (sorted, distint)

1.  What about size? You can also have infinite stream!

        // It is infinite stream but is very lazy. It won't do any work until you demand to do it.
             System.out.println(Stream.iterate(10, e ->e+1));

1.  Given a number k , and a count n, find the total of the  double of n even number starting with k whr sqarroot of each number > 20


	    // imperative style
	    public static int compute(int k, int n) {

		int result = 0;
		int index = k;
		while (n < 0) { // Confusions:is it less or less than equal to ? Am
							// I going to get "off by 1" error?

			if (index % 2 == 0 && Math.sqrt(index) > 20)

				result += 2 * index;

			n++;
			index++;
		}
		return result;

	    }
        //functional way
	    public static int computeFunc(int k, int n) {
		return Stream.iterate(k, e -> e + 1)
				// gives an infinite series of stream starting form k
				.filter(e -> e % 2 == 0)
				.filter(e ->Math.sqrt(e) > 20)
				.mapToInt(e -> e * 2)
				.limit(n)
				.sum();
	    }
        // it is readable, concise, expressive,  no confusion, all methods are lazy except sum


1.  How to know if a function is lazy or eager?  If methods return stream from stream itself then it is lazy
