INSERT INTO persons VALUES (0, 'Фамилия1', 'Имя1', 'Отчество1', 'Должность1');
INSERT INTO persons VALUES (1, 'Фамилия2', 'Имя2', 'Отчество2', 'Должность2');
INSERT INTO persons VALUES (2, 'Фамилия3', 'Имя3', 'Отчество3', 'Должность3');
INSERT INTO persons VALUES (3, 'Фамилия4', 'Имя4', 'Отчество4', 'Должность4');

INSERT INTO projects VALUES (0, 'Проект0', 'ПР0', 'Описание1');
INSERT INTO projects VALUES (1, 'Проект1', 'ПР1', 'Описание2');
INSERT INTO projects VALUES (2, 'Проект2', 'ПР2', 'Описание3');
INSERT INTO projects VALUES (3, 'Проект3', 'ПР3', 'Описание4');

INSERT INTO tasks VALUES (0, 'Задача0', 60, '2017-01-30', '2018-01-30', 'NOT_STARTED', 1, 0);
INSERT INTO tasks VALUES (1, 'Задача1', 120, '2017-01-30', '2018-01-30', 'IN_PROCESS', 2, 1);
INSERT INTO tasks VALUES (2, 'Задача2', 120, '2017-01-30', '2018-01-30', 'IN_PROCESS', 3, 2);
INSERT INTO tasks VALUES (3, 'Задача3', 120, '2017-01-30', '2018-01-30', 'COMPLETED', 3, 3);
INSERT INTO tasks VALUES (4, 'Задача4', 180, '2017-01-30', '2018-01-30', 'DELAYED', 2, 1);
INSERT INTO tasks VALUES (5, 'Задача5', 90, '2017-01-30', '2018-01-30', 'COMPLETED', 1, 2);
INSERT INTO tasks VALUES (6, 'Задача6', 150, '2017-01-30', '2018-01-30', 'COMPLETED', 0, 0);
COMMIT;