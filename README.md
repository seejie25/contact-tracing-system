# contact-tracing-system

You are developing a simplified contact tracing system for COVID-19. The three (3) main roles in the system are listed below:
<br />
<br />
1. **Customer** – a customer has name, phone, and status. A customer can: \
   	a) Register an account in the system \
   	b) Sign in the system \
	c) Check-in a shop \
	d) View the history of the shops he/she visited \
	e) View his/her status. A customer status can have 3 possible values: Normal, Case, Close \
		&emsp; i. Normal – The customer is normal. \
		&emsp; ii. Case – The customer is a case of COVID-19 positive. \
		&emsp; iii. Close – The customer is a close contact of a case. For simplicity, a close contact is anyone who checks in the same shop within one-hour range 
		(inclusive) with a case. The seconds in check-in time are ignored. \
		&emsp; &emsp; For example, if a case checks in a shop today at 12pm, then: \
		&emsp; &emsp; - A customer who checks in yesterday is not a close contact. \
		&emsp; &emsp; - A customer who checks in today at 10.59am is not a close contact. \
		&emsp; &emsp; - A customer who checks in today at 11am is a close contact. \
		&emsp; &emsp; - A customer who checks in today at 1pm is a close contact. \
		&emsp; &emsp; - A customer who checks in today at 1.01pm is not a close contact. 
		

\
2. **Shop** – A shop has name, phone, status, and manager. A shop status can have 2 possible values: Normal, Case.


\
3. **Admin** – a government agency who can: \
	a) View the details of all customers, the details of all shops, the master visit history. \
	b) Flag (change status) a customer. When a customer is flagged as a Case, the system shall automatically flag: \
		&emsp; i. The shop as Case. \
		&emsp; ii. All close contacts as Close.


\
Your program shall have the following features:
- Never clear screen.
- Pre-load with 5 customers (when the program starts).
-  Pre-load with 4 shops.
-  Have a feature to add 30 random visits to the master visit history (random shops, random visitors, time ranging from yesterday until current system time). 
	Sort the master visit history by date and time.

		
