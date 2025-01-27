create database studyHall;

use studyHall;


create table user(
                     user_name varchar(30) primary key,
                     pass_word varchar(30),
                     email varchar(100),
                     phone_number int(10)
);


create table subject(
                        sub_id varchar(10) primary key,
                        sub_name varchar(50),
                        description varchar(100)
);


create table grade(
                      g_id varchar(10) primary key,
                      grade varchar(40)
);


create table subject_grade(
                              subject_id varchar(10),
                              grade_id varchar(10),
                              foreign key(subject_id) references subject(sub_id) on delete cascade on update cascade,
                              foreign key(grade_id) references grade(g_id) on delete cascade on update cascade
);


create table teacher(
                        t_id varchar(10) primary key,
                        name varchar(100),
                        phone_number varchar(10),
                        email varchar(100),
                        subject_id varchar(10),
                        foreign key(subject_id) references subject(sub_id) on delete cascade on update cascade
);


create table teacher_grade(
                              teacher_id varchar(10),
                              grade_id varchar(10),
                              foreign key(teacher_id) references teacher(t_id) on delete cascade on update cascade,
                              foreign key(grade_id) references grade(g_id) on delete cascade on update cascade
);


create table student(
                        s_id varchar(10) primary Key,
                        birthday date,
                        name varchar(100) ,
                        admission_fee decimal(10,2) ,
                        parent_name varchar(100) ,
                        email varchar(100) ,
                        phone_number int(10) ,
                        address varchar(300),
                        added_by varchar(10),
                        grade varchar(10),
                        foreign key(added_by) references user(user_name) on delete cascade on update cascade,
                        foreign key(grade) references grade(g_id) on delete cascade on update cascade
);


create table student_subject(
                                student_id varchar(10),
                                subject_id varchar(10),
                                foreign key(student_id) references student(s_id) on delete cascade on update cascade,
                                foreign key(subject_id) references subject(sub_id) on delete cascade on update cascade
);


create table exam(
                     exam_id varchar(10) primary key,
                     subject_id varchar(10),
                     exam_type varchar(20),
                     date date,
                     description varchar(50),
                     grade varchar(10),
                     foreign key(subject_id) references subject(sub_id) on delete cascade on update cascade
);



create table result(
                       result_id varchar(10) primary key,
                       exam_id varchar(10),
                       student_id varchar(10),
                       marks int(3) not null,
                       exam_grade varchar(3),
                       status varchar(20),
                       grade varchar(10),
                       foreign key(exam_id) references exam(exam_id) on delete cascade on update cascade,
                       foreign key(student_id) references student(s_id) on delete cascade on update cascade
);









