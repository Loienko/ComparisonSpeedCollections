-- Drop column type_of_action
DO
$$
    BEGIN
        BEGIN
            ALTER TABLE array_and_linked_list_collections
                DROP COLUMN type_of_action;
        EXCEPTION
            WHEN undefined_column THEN RAISE NOTICE 'Column does not exist in table. Skipping.';
        END;
    END;
$$;

-- Create the table type_of_action
DO
$$
    BEGIN
        create table if not exists type_of_action
        (
            id   bigserial      not null
                constraint type_of_action_id_pkey
                    primary key,
            name varchar(50)
        ) WITH (OIDS= FALSE);
        ALTER TABLE type_of_action
            OWNER TO postgres;
    EXCEPTION
        WHEN duplicate_table THEN RAISE NOTICE 'Table speed_collection_name_of_collection already exists. Skipping';
    END;
$$;

-- Add column type_of_action_id
DO
$$
    BEGIN
        BEGIN
            ALTER TABLE array_and_linked_list_collections
                ADD COLUMN type_of_action bigint
                    constraint fk_type_of_action
                        references type_of_action;
        EXCEPTION
            WHEN duplicate_column THEN RAISE NOTICE 'Column already exists in table. Skipping.';
        end;
    end;
$$;


-- Insert data to name_of_collection table
INSERT INTO public.name_of_collections (id, name) VALUES (1, 'ArrayList');
INSERT INTO public.name_of_collections (id, name) VALUES (2, 'LinkedList');
INSERT INTO public.name_of_collections (id, name) VALUES (3, 'Map');
INSERT INTO public.name_of_collections (id, name) VALUES (4, 'Set');

-- Insert data to type_of_action table
INSERT INTO public.type_of_action (id, name) VALUES (3, 'Delete');
INSERT INTO public.type_of_action (id, name) VALUES (1, 'Insert');
INSERT INTO public.type_of_action (id, name) VALUES (4, 'Retrieve');
INSERT INTO public.type_of_action (id, name) VALUES (2, 'Update');