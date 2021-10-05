create table students
(
    id   serial primary key,
    name text
);

create table subjects
(
    id   serial primary key,
    name text
);

create table students_subjects
(
    id     serial primary key,
    mark   int,   -- оценка
    std_id int references students (id),
    sbj_id int references subjects (id)
);

insert into students(name)
values ('Аня'),
       ('Ваня'),
       ('Боря');
insert into subjects(name)
values ('Математика'),
       ('Русский'),
       ('Информатика');

insert into students_subjects(std_id, sbj_id, mark)
values (1, 1, 5),
       (1, 2, 5),
       (1, 3, 5);
insert into students_subjects(std_id, sbj_id, mark)
values (2, 1, 5),
       (2, 2, 4),
       (2, 3, 4);
insert into students_subjects(std_id, sbj_id, mark)
values (3, 1, 3),
       (3, 2, 5),
       (3, 3, 3);


-- получить по каждому студенту его среднюю оценку
select s.name, avg(ss.mark) from students as s join students_subjects as ss on s.id = ss.std_id group by s.name;

-- получить по каждому предмету среднюю оценк
select s.name, avg(ss.mark) from subjects as s join students_subjects ss on s.id = ss.sbj_id group by s.name;

-- получить имя студента чьи средние оценки выше 4.5
select s.name, avg(ss.mark) from students as s join students_subjects as ss on s.id = ss.std_id
group by s.name having avg(ss.mark) > 4.5;


-- найти  студента у которого оценка по математике больше 4
select name from students where id in (select std_id from students_subjects where (sbj_id = 1) AND mark > 4);

select *from students full join students_subjects ss on students.id = ss.std_id;