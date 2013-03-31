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
		.constructorArg('serialNo', 'HJKLERTYUFGHJK2234567')		.in('prototype')

####supported data type:
+ primitive type: char, int, long, boolean, float,  double
+ user defined type