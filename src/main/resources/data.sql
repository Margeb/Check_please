insert into groups (id, name) values
      (gen_random_uuid(), 'Group 1'),
      (gen_random_uuid(), 'Group 2'),
      (gen_random_uuid(), 'Group 3'),
      (gen_random_uuid(), 'Group 4'),
      (gen_random_uuid(), 'Group 5'),
      (gen_random_uuid(), 'Group 6'),
      (gen_random_uuid(), 'Group 7');

insert into bills (id, name, date, group_id) values
        (gen_random_uuid(), 'Bill 1', '2022-12-20', (SELECT id FROM groups WHERE name = 'Group 1')),
        (gen_random_uuid(), 'Bill 2', '2022-12-21', (SELECT id FROM groups WHERE name = 'Group 1')),
        (gen_random_uuid(), 'Bill 3', '2022-12-22', (SELECT id FROM groups WHERE name = 'Group 1'));

insert into people (id, name, balance, group_id) values
        (gen_random_uuid(), 'Person 1', 75.00, (SELECT id FROM groups WHERE name = 'Group 1')),
        (gen_random_uuid(), 'Person 2', -25.00, (SELECT id FROM groups WHERE name = 'Group 1')),
        (gen_random_uuid(), 'Person 3', -50.00, (SELECT id FROM groups WHERE name = 'Group 1')),
        (gen_random_uuid(), 'Person 4', 0.00, (SELECT id FROM groups WHERE name = 'Group 1')),
        (gen_random_uuid(), 'Person 5', 0.00, (SELECT id FROM groups WHERE name = 'Group 1'));

insert into bill_operations (id, bill_id, person_id, deposit, cost) values
        (gen_random_uuid(), (SELECT id FROM bills WHERE name = 'Bill 1'), (SELECT id FROM people WHERE name = 'Person 1'), 100, 25),
        (gen_random_uuid(), (SELECT id FROM bills WHERE name = 'Bill 1'), (SELECT id FROM people WHERE name = 'Person 2'), 0, 25),
        (gen_random_uuid(), (SELECT id FROM bills WHERE name = 'Bill 1'), (SELECT id FROM people WHERE name = 'Person 3'), 0, 50);