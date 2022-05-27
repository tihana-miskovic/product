# Getting Started

1. 	U application.properties izmijeniti url/username/password datasourcea

2. 	Za automatsko kreiranje tablice products u bazi, u application.properties se moze postaviti
	spring.jpa.hibernate.ddl-auto=create
	Ovo ce kreirati na schemi public, u app properties se moze izmijeniti default_schema
	Ili se moze na bazi pokrenuti CREATE skripta. U INSERT skripti su inserti za 3 stavke.

3.	Aplikaciju pokrenuti u command lineu pomocu Mavena naredbom 
	mvn spring-boot:run

4. Aplikacija ce se pokrenuti na localhost:8080, port se moze promijeniti u application.properties (server.port)

5. URL dohvaca sve iz tablice products: http://localhost:8080/api/products