INSERT INTO EMPLOYEE (ID, VISA, FIRST_NAME, LAST_NAME, BIRTH_DATE, VERSION)
VALUES (111, 'JOH', 'John', 'Doe', '1990-05-15', 1),
       (222, 'JAN', 'Jane', 'Smith', '1985-08-20', 1),
       (333, 'MIC', 'Michael', 'Johnson', '1992-03-10', 1);

INSERT INTO COMPANY_GROUP (ID, GROUP_LEADER_ID, VERSION)
VALUES (11, 111, 1),
       (22, 222, 1),
       (33, 333, 1);

INSERT INTO PROJECT (ID, GROUP_ID, PROJECT_NUMBER, NAME, CUSTOMER, START_DATE, END_DATE, VERSION)
VALUES (1, 11, '123', 'Sample Project 1', 'ABC Corp', '2024-03-05', '2024-07-31', 1),
       (2, 22, '124', 'Sample Project 2', 'XYZ Corp', '2024-02-15', '2024-06-30', 1),
       (3, 11, '125', 'Sample Project 3', 'DEF Corp', '2024-04-10', NULL, 1);



INSERT INTO PROJECT_EMPLOYEE(PROJECT_ID, EMPLOYEE_ID)
VALUES (1, 111),
       (1, 222),
       (1, 333),
       (2, 111),
       (2, 222),

