Assessment application for Ingenico epayment.

Application is built on a base of Spring Boot. 
In-memory database H2 is used as persistence level. 
Maven is used as built tool.
Architecture represents classical three layer structure: controller layer, 
service (or business) layer and database layer.

I omitted any validation of input data, so I did not introduce any validators. 

For demonstration purposes only, I included barrier transfer method. It accumulates 10 incoming transfer requests 
and then release them all simultaneously. It make sense to send 10 contradicting requests such as only one of them 
could be performed. In my example all 10 try to withdraw 51 coins from the account with balance of 100. 

System has two database entities: account and transfer. Transfer entity has status field with values 
{Initialized, Completed, Error}

To build application type from root dir 
./mvnw clean package  

To execute application type from the same dir
java -jar ./target/ingenico-0.1.0.jar

Application is started with four created accounts {a, b, c, d} with 100 balance each.

Here are some useful commands:
curl -i -X POST localhost:8080/account/create/name/100 
will create accout [name] with balance [100] or return error if such account already exists.

curl localhost:8080/account
Returns list of created accounts in system

curl localhost:8080/transfer
Returns list of created transfers with their statuses

curl -i -X POST localhost:8080/transfer/a/b/51
curl -i -X POST localhost:8080/transfer/barrier/a/b/51 &
curl -i -X POST localhost:8080/transfer/barrier/c/d/51 &
curl -i -X POST localhost:8080/transfer/c/d/1
Commands to transfer coins from the first account to the second one in normal mode and barrier mode.