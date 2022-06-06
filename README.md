# HibernateDemo
Hibernate  Sample application using PostgreSQL database including interceptor and statement inspector.

To create table

~~~
create sequence student_details_seq MINVALUE 1;

CREATE  TABLE student_details ( 
student_id INT NOT NULL ,
student_name VARCHAR(45) NOT NULL ,
student_age INT NOT NULL ,
tenant_id varchar,
PRIMARY KEY (student_id) );
~~~
