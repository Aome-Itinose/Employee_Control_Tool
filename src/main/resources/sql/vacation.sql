create table Vacation(
    id uuid unique default gen_random_uuid(),
    employee_id uuid references employee(id),
    start_date timestamp,
    end_date timestamp check ( start_date < Vacation.end_date )
)