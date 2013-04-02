###tasks:
1. The implementation must support Constructor Inject
2. The implementation must support Setter Inject
3. The implementation must support container scope
4. The implementation must provide a configuration mechanism

---
####configuration
**java code dsl:**

1. constructor inject:

		ApplicationLoader.create('phone')
		.type(com.example.Phone)
		.constructorArg('type', 'Samsung')
		.constructorArg('serialNo', 'HJKLERTYUFGHJK2234567')
		.in('prototype')

####supported data type:
+ primitive type: char, int, long, boolean, float,  double
+ user defined type



####tasks:
1. create named bean through constructor inject only support primitive data type DONE
2. create named bean through setter inject DONE
3. create named bean that depends on another bean
4. inject bean depends on type
5. do not need to type cast when get bean
5. create named bean support collection inject