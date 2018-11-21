

CREATE SEQUENCE feedback_id_seq
	START WITH 1
	INCREMENT BY 1
	NO MINVALUE
	NO MAXVALUE
	CACHE 1;


CREATE TABLE feedback(
	feedback_id bigint DEFAULT nextval('feedback_id_seq' :: regclass) NOT NULL,
	feedback_path character varying(256) NOT NULL,
	question_id bigint NOT NULL,
	user_id character varying(100),
	feedback_response jsonb NOT NULL,
	created_by character varying(100) NOT NULL,
	created_date timestamp without time zone DEFAULT clock_timestamp() NOT NULL,
	last_modified_by character varying(100) NOT NULL,
	last_modified_date timestamp without time zone DEFAULT clock_timestamp() NOT NULL
);

ALTER TABLE ONLY feedback ADD CONSTRAINT feedback_pkey PRIMARY KEY (feedback_id);


-- Adding Table Descriptions
COMMENT ON TABLE feedback IS 'This table contains information of all the feedback responses provided by the end users';


-- Adding Table column description
COMMENT ON column feedback.feedback_id IS 'This field is auto incremented id for feedback table.';
COMMENT ON column feedback.question_id IS 'This field is a foriegn key relationship with question_id of question table';

