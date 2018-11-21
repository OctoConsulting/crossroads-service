

alter table ts.travel_records drop column comments;

create table ts.notifications (id number primary key, for_user number references user(id), notification_text VARCHAR_IGNORECASE, created_date TIMESTAMP, MODIFIED_DATE TIMESTAMP);

create table ts.comments (id number primary key, travel_record_id number references travel_records(id), comment_text VARCHAR_IGNORECASE, added_by number references user(id), created_date TIMESTAMP, MODIFIED_DATE TIMESTAMP);

create sequence TS.NOTIFICATIONS_SEQ start with 2000;

create sequence TS.COMMENTS_SEQ start with 2000;