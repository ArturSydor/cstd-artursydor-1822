create table if not exists eco_projects
(
    id                          bigserial primary key,
    name                        varchar(255) not null,
    description                 text         not null,
    points                      integer      not null default 0,
    max_allowed_points_per_user integer      not null default 1,
    published                   boolean      not null default false,
    closed                      boolean      not null default false,
    creator_id                  bigint       not null,
    organisation_id             bigint       not null
    );

alter table eco_projects
    add constraint eco_projects_unique_name_per_organisation unique (name, organisation_id);

alter table eco_projects
    add constraint eco_projects_creator_id_fk
        foreign key (creator_id) references users (id);

alter table eco_projects
    add constraint eco_projects_organisation_id_fk
        foreign key (organisation_id) references organisation (id);

create table if not exists user_eco_project_points
(
    user_id    bigint  not null,
    project_id bigint  not null,
    points     integer not null default 0
);

alter table user_eco_project_points
    add constraint user_eco_project_points_pk primary key (user_id, project_id);