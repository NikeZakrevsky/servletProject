INSERT INTO persons VALUES (0, 'Фамилия1', 'Имя1', 'Отчество1', 'Должность1');
INSERT INTO persons VALUES (1, 'Фамилия2', 'Имя2', 'Отчество2', 'Должность2');
INSERT INTO persons VALUES (2, 'Фамилия3', 'Имя3', 'Отчество3', 'Должность3');
INSERT INTO persons VALUES (3, 'Фамилия4', 'Имя4', 'Отчество4', 'Должность4');

INSERT INTO projects VALUES (0, 'Проект0', 'ПР0', 'Описание1');
INSERT INTO projects VALUES (1, 'Проект1', 'ПР1', 'Описание2');
INSERT INTO projects VALUES (2, 'Проект2', 'ПР2', 'Описание3');
INSERT INTO projects VALUES (3, 'Проект3', 'ПР3', 'Описание4');

INSERT INTO tasks VALUES (0, 'Задача0', 1, '2017-01-30', '2018-01-30', 'Not started', 1, 0);
INSERT INTO tasks VALUES (1, 'Задача1', 2, '2017-01-30', '2018-01-30', 'In progress', 2, 1);
INSERT INTO tasks VALUES (2, 'Задача2', 3, '2017-01-30', '2018-01-30', 'Completed', 3, 2);
INSERT INTO tasks VALUES (3, 'Задача3', 4, '2017-01-30', '2018-01-30', 'Delayed', 3, 3);
INSERT INTO tasks VALUES (4, 'Задача4', 5, '2017-01-30', '2018-01-30', 'Not started', 2, 1);
INSERT INTO tasks VALUES (5, 'Задача5', 6, '2017-01-30', '2018-01-30', 'Completed', 1, 2);
INSERT INTO tasks VALUES (6, 'Задача6', 7, '2017-01-30', '2018-01-30', 'Completed', 0, 0);
COMMIT;