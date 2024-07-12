create table Users(
                id uuid unique default gen_random_uuid(),
                username varchar,
                password varchar,
                role varchar default 'ROLE_USER'
);