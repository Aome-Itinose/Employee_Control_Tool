create table Employee(
                         id uuid unique default gen_random_uuid(),
                         user_id uuid references Users(id) not null,
                         first_name varchar not null,
                         last_name varchar not null,
                         position varchar not null,
                         department varchar not null,
                         date_of_hire timestamp  not null,
                         ceo_link varchar not null
)