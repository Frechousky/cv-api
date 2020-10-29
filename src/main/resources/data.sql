INSERT INTO company (id, name, field, country, city) VALUES (-2, 'Sopra Banking Software', 'Banking', 'France', 'Tours');
INSERT INTO company (id, name, field, country, city) VALUES (-1, 'Sopra Banking Software', 'Banking', 'France', 'Toulouse');
INSERT INTO company (id, name, field, country, city) VALUES ( 0, 'Viveris Technologies', 'Various', 'France', 'Toulouse');

INSERT INTO contact_information (id, full_name, address, zip_code, city, phone, mail, age, driver_licence)
VALUES (-2, 'Alexandre Frèche', '20 rue des changes', '31000', 'Toulouse', '0608963234', 'alexandre.freche@gmail.com', '29', 'permis B');

INSERT INTO language (id, label, level) VALUES(-2, 'Français', 5);
INSERT INTO language (id, label, level) VALUES(-1, 'Anglais', 3);
INSERT INTO language (id, label, level) VALUES(0, 'Espagnol', 1);

INSERT INTO work_experience (id, company_id, position, start, end) VALUES (-2, -2, 'Développeur fullstack junior', '2017-09-01 00:00:00.00', '2019-06-10 00:00:00.00');

INSERT INTO work_experience_description (work_experience_id, description) VALUES (-2, 'Description 1');
INSERT INTO work_experience_description (work_experience_id, description) VALUES (-2, 'Description 2');
INSERT INTO work_experience_description (work_experience_id, description) VALUES (-2, 'Description 3');

