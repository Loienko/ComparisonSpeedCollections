-- Drop column name_of_collection from tables
DO
$$
    BEGIN
        BEGIN
            ALTER TABLE array_and_linked_list_collections
                DROP COLUMN name_of_collection;
            ALTER TABLE set_and_map_collections
                DROP COLUMN name_of_collection;
        EXCEPTION
            WHEN undefined_column THEN RAISE NOTICE 'Column does not exist in table. Skipping.';
        END;
    END;
$$;

-- Create the table name_of_collections
DO
$$
    BEGIN
        create table if not exists name_of_collections
        (
            id   bigserial not null
                constraint name_of_collections_pkey
                    primary key,
            name varchar(255)
        ) WITH (OIDS= FALSE);
        ALTER TABLE name_of_collections
            OWNER TO postgres;
    EXCEPTION
        WHEN duplicate_table THEN RAISE NOTICE 'Table name_of_collections already exists. Skipping';
    END;
$$;

-- Add column name_of_collections_id to tables
DO
$$
    BEGIN
        BEGIN
            ALTER TABLE array_and_linked_list_collections
                ADD COLUMN name_of_collection bigint
                    constraint fk_name_of_collection_array_and_linked_list_collections
                        references name_of_collections;
        EXCEPTION
            WHEN duplicate_column THEN RAISE NOTICE 'Column already exists in table. Skipping.';
        end;
    end;
$$;

DO
$$
    BEGIN
        BEGIN
            ALTER TABLE set_and_map_collections
                ADD COLUMN name_of_collection bigint
                    constraint fk_name_of_collection_set_and_map_collections
                        references name_of_collections;
        EXCEPTION
            WHEN duplicate_column THEN RAISE NOTICE 'Column already exists in table. Skipping.';
        end;
    end;
$$;