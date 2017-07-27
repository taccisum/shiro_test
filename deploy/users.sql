CREATE TABLE shiro_test.users
(
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    `password` VARCHAR(50) NOT NULL,
    insert_time DATETIME NOT NULL
);