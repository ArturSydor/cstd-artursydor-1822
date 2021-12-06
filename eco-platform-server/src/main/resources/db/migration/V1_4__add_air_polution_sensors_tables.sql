create table if not exists air_pollution_sensors
(
    id                  bigserial primary key,
    external_identifier varchar(255) not null unique,
    latitude            varchar(255) not null,
    longitude           varchar(255) not null,
    organisation_id     bigint       not null,
    creator_id          bigint       not null
);

alter table air_pollution_sensors
    add constraint air_pollution_sensors_organisation_id_fk
        foreign key (organisation_id) references organisation (id);

alter table air_pollution_sensors
    add constraint air_pollution_sensors_creator_id_fk
        foreign key (creator_id) references users (id);

create table if not exists air_pollution_sensors_data
(
    id        bigserial primary key,
    sensor_id bigint         not null,
    date      timestamp      not null default now(),
    n2o       decimal(10, 3) not null default 0.0,
    o3        decimal(10, 3) not null default 0.0,
    co2       decimal(10, 3) not null default 0.0,
    so2       decimal(10, 3) not null default 0.0,
    dust      decimal(10, 3) not null default 0.0
    );

alter table air_pollution_sensors_data
    add constraint air_pollution_sensors_data_sensor_id_fk
        foreign key (sensor_id) references air_pollution_sensors (id);