###tasks:
1. The implementation must support Constructor Inject
2. The implementation must support Setter Inject
3. The implementation must support container scope
4. The implementation must provide a configuration mechanism

---
####configuration
**java code dsl:**

1. constructor inject:

		injector.bind('phone')
		.type(com.example.Phone)
		.withConstructorArg()
		.constructorArg('type', 'Samsung')
		.constructorArg('serialNo', 'HJKLERTYUFGHJK2234567')
		.in('prototype')

####supported data type:
+ primitive type: char, int, long, boolean, float,  double
+ user defined type



####tasks:
1. create named bean through constructor inject only support primitive data type DONE

2. create named bean through setter inject DONE

3. create named bean that depends on another bean DONE

4. inject bean depends on type DONE
    4.1 create and get bean by type DONE

5. do not need to type cast when get bean DONE

6. parent container scope DONE


7. bind a interface to provided implementation  ZXY


7. support collection inject

8. annotation @Component