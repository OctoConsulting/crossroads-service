alter table TS.TRAVEL_RECORDS  drop column category_id;
alter table TS.TRAVEL_RECORDS  drop column status;
alter table TS.TRAVEL_RECORDS  drop column cost;
alter table TS.TRAVEL_RECORDS add air_fare decimal(10,2);
alter table TS.TRAVEL_RECORDS add mileage decimal(10,2);
alter table TS.TRAVEL_RECORDS add hotel decimal(10,2);
alter table TS.TRAVEL_RECORDS add rent_car decimal(10,2);
alter table TS.TRAVEL_RECORDS add per_diem decimal(10,2);
alter table TS.TRAVEL_RECORDS add status VARCHAR_IGNORECASE;

delete from ts.travel_records;

insert into ts.travel_records(id,requester,status,approver,date_from,date_to, location_from,location_to,comments,created_date,modified_Date,modified_by,air_fare,hotel,rent_car,per_diem) 
values (1,1,'Pending',2,current_date(),current_date(),'WashingTon DC','CA','Test',current_date(),current_date(),'Admin',659,340,120,700);

insert into ts.travel_records(id,requester,status,approver,date_from,date_to, location_from,location_to,comments,created_date,modified_Date,modified_by,air_fare,hotel,rent_car,per_diem) 
values (2,1,'Approved',2,current_date(),current_date(),'WashingTon DC','MD','Test',current_date(),current_date(),'Admin',654,240,20,568);

insert into ts.travel_records(id,requester,status,approver,date_from,date_to, location_from,location_to,comments,created_date,modified_Date,modified_by,air_fare,hotel,rent_car,per_diem) 
values (3,1,'Approved',2,current_date(),current_date(),'WashingTon DC','LA','Test',current_date(),current_date(),'Admin',654,87,45,900);

insert into ts.travel_records(id,requester,status,approver,date_from,date_to, location_from,location_to,comments,created_date,modified_Date,modified_by,air_fare,hotel,rent_car,per_diem) 
values (4,1,'Rejected',2,current_date(),current_date(),'WashingTon DC','FL','Test Rejected',current_date(),current_date(),'Admin',250,100,100,100);

insert into ts.travel_records(id,requester,status,approver,date_from,date_to, location_from,location_to,comments,created_date,modified_Date,modified_by,air_fare,hotel,rent_car,per_diem) 
values (5,1,'Approved',2,current_date(),current_date(),'WashingTon DC','TX','Test Rejected',current_date(),current_date(),'Admin',354,240,120,368);