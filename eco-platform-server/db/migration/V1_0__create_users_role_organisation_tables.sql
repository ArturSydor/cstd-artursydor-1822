create table if not exists role
(
    id   serial primary key,
    name varchar(255) not null unique
    );

create table if not exists users
(
    id              bigserial primary key,
    first_name      varchar(50)  not null,
    last_name       varchar(50)  not null,
    email           varchar(255) not null unique,
    password        varchar(255) not null,
    active          boolean      not null default true,
    deleted         boolean      not null default false,
    organisation_id bigint       not null,
    role_id         integer      not null
    );

create table if not exists organisation
(
    id                          bigserial primary key,
    name                        varchar(255) not null unique,
    email                       varchar(255) unique,
    member_approval_required    boolean not null default true,
    deleted                     boolean not null default false,
    creator_id                  bigint
    );

alter table users
    add constraint users_role_id_fk
        foreign key (role_id)
            references role (id);

alter table users
    add constraint users_organisation_id_fk
        foreign key (organisation_id)
            references organisation (id);

alter table organisation
    add constraint organisation_creator_id_fk
        foreign key (creator_id)
            references users (id);