create table Time_Sheet(
    id uuid unique default gen_random_uuid(),
    employee_id uuid references employee(id),
    date timestamp,
    hours_worked int,
    efficiency double precision
)