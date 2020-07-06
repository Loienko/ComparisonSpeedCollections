-- -- Creating a table according to 1 NF
create table if not exists array_and_linked_list_collections
(
    id                 bigserial not null
        constraint array_and_linked_list_collections_pkey
            primary key,
    amount             bigint,
    end_of_list        varchar(255),
    middle_of_list     varchar(255),
    name_of_collection varchar(255),
    start_of_list      varchar(255),
    type_of_action     varchar(255),
    created            timestamp default now()
);

alter table array_and_linked_list_collections
    owner to postgres;

-- Creating a table according to 1 NF
create table if not exists set_and_map_collections
(
    id                 bigserial not null
        constraint set_and_map_collections_pkey
            primary key,
    amount             bigint,
    insert             varchar(255),
    name_of_collection varchar(255),
    remove             varchar(255),
    retrieve           varchar(255),
    created            timestamp default now()
);

alter table set_and_map_collections
    owner to postgres;