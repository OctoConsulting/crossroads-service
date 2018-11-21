
create schema ts;


create table ts.roles (role_id number(3) primary key,role_name VARCHAR_IGNORECASE(50));

insert into ts.roles(role_id,role_name) values(1,'Employee');
insert into ts.roles(role_id,role_name) values(2,'Approver');
insert into ts.roles(role_id,role_name) values(3,'Reporter');
insert into ts.roles(role_id,role_name) values(4,'Administrator');

create table ts.travel_status (id number primary key, status VARCHAR_IGNORECASE, is_deleted number default 0, created_date TIMESTAMP, MODIFIED_DATE TIMESTAMP);

insert into ts.travel_status(id,status) values (1,'Draft');
insert into ts.travel_status(id,status) values (2,'Pending');
insert into ts.travel_status(id,status) values (3,'Approved');
insert into ts.travel_status(id,status) values (4,'Rejected');
insert into ts.travel_status(id,status) values (5,'Processed');

create table ts.travel_categories (id number  primary key, category_name VARCHAR_IGNORECASE, Cost_Threshold decimal(20,2), is_deleted number default 0, 
created_date TIMESTAMP default CURRENT_TIMESTAMP(), modified_Date TIMESTAMP, modified_by VARCHAR_IGNORECASE) ;

insert into ts.travel_categories( id,category_name,cost_threshold) values (1,'Air Fare',1000.00);
insert into ts.travel_categories( id,category_name,cost_threshold) values (2,'Mileage',1000.00);
insert into ts.travel_categories( id,category_name,cost_threshold) values (3,'Hotel',200.00);
insert into ts.travel_categories( id,category_name,cost_threshold) values (4,'Rental Car',100.00);
insert into ts.travel_categories( id,category_name,cost_threshold) values (5,'Per Diem',100.00);



create table ts.user (id number  primary key, first_name VARCHAR_IGNORECASE, last_name VARCHAR_IGNORECASE, username  VARCHAR_IGNORECASE, password 
  VARCHAR_IGNORECASE, supervisor number, email VARCHAR_IGNORECASE, created_date TIMESTAMP default CURRENT_TIMESTAMP(),
    modified_date TIMESTAMP, modified_by VARCHAR_IGNORECASE);

alter table ts.user add CONSTRAINT username UNIQUE(username);

insert into ts.user(id,first_name,last_name, username, password, supervisor, email, created_date, modified_by, modified_date)  values(1,'Smith','Tim','smith','timpwd',2,null,CURRENT_TIMESTAMP(),null,null);
insert into ts.user(id,first_name,last_name, username, password, supervisor, email, created_date, modified_by, modified_date)  values(2,'Warner','Loretta','warner','warnerpwd',2,null,CURRENT_TIMESTAMP(),null,null);
insert into ts.user(id,first_name,last_name, username, password, supervisor, email, created_date, modified_by, modified_date)  values(3,'Lorenzo','Sophia','lorenzo','Lorenzopwd',2,null,CURRENT_TIMESTAMP(),null,null);
insert into ts.user(id,first_name,last_name, username, password, supervisor, email, created_date, modified_by, modified_date)  values(4,'Miller','Rick','miller','millerpwd',1,null,CURRENT_TIMESTAMP(),null,null);
insert into ts.user(id,first_name,last_name, username, password, supervisor, email, created_date, modified_by, modified_date)  values(5,'Walden','Marcus','walden','waldenpwd',3,null,CURRENT_TIMESTAMP(),null,null);


create table ts.user_roles (id number primary key, uid number references user(id), role_id number references roles(role_id), is_deleted number default 0,
 created_date TIMESTAMP default CURRENT_TIMESTAMP(), modified_Date TIMESTAMP, modified_by VARCHAR_IGNORECASE);
 
 insert into ts.user_roles(id, uid, role_id, is_deleted, created_date, modified_date, modified_by) values(1, 5,1,0,CURRENT_TIMESTAMP(),null,null);
 insert into ts.user_roles(id, uid, role_id, is_deleted, created_date, modified_date, modified_by) values(2, 3,2,0,CURRENT_TIMESTAMP(),null,null);
 insert into ts.user_roles(id, uid, role_id, is_deleted, created_date, modified_date, modified_by) values(3, 3,3,0,CURRENT_TIMESTAMP(),null,null);
 insert into ts.user_roles(id, uid, role_id, is_deleted, created_date, modified_date, modified_by) values(4, 2,4,0,CURRENT_TIMESTAMP(),null,null);
 insert into ts.user_roles(id, uid, role_id, is_deleted, created_date, modified_date, modified_by) values(5, 1,2,0,CURRENT_TIMESTAMP(),null,null);


create table ts.travel_records (id number  primary key, requester number references user(id), status number references travel_status(id), 
approver number references user(id),  
date_from date, date_to date, 
category_id number references TRAVEL_CATEGORIES(id),
location_from VARCHAR_IGNORECASE, location_to VARCHAR_IGNORECASE, cost decimal(20,2), comments VARCHAR_IGNORECASE, 
created_date TIMESTAMP default CURRENT_TIMESTAMP(), modified_Date TIMESTAMP, modified_by VARCHAR_IGNORECASE) ;

insert into ts.travel_records(id,requester,status,approver,date_from,date_to, category_id, location_from,location_to,cost,comments,created_date,modified_Date,modified_by) 
values (1,1,1,2,current_date(),current_date(),1,'WashingTon DC','CA',100,'Test',current_date(),current_date(),'Admin');

create table ts.travel_record_history(id number  primary key, travel_record_id  number references travel_records(id), uid number references user(id),  comments VARCHAR_IGNORECASE, 
created_date TIMESTAMP default CURRENT_TIMESTAMP()) ;

create table ts.travel_record_documents(id number  primary key, travel_record_id  number references travel_records(id), document_name VARCHAR_IGNORECASE,  url VARCHAR_IGNORECASE, 
created_date TIMESTAMP default CURRENT_TIMESTAMP()) ;








