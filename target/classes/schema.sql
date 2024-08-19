drop table if exists sk_example_table;

create table if not exists sk_example_table
(
    id serial,
    obj jsonb not null,
    primary key (id)
);

insert into sk_example_table (obj) values ('{"current":0}'::JSONB);