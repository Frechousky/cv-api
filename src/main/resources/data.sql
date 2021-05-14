/** 
    Populate database on application start-up.
    This script must not be executed on production.
*/
INSERT INTO company
(name                       , field                 , country   , city          ) VALUES
('Sopra Banking Software'   , 'Banking'             , 'France'  , 'Tours'       ),
('Sopra Banking Software'   , 'Banking'             , 'France'  , 'Toulouse'    ),
('Viveris Technologies'     , 'Software engineering', 'France'  , 'Toulouse'    );

INSERT INTO contact_information 
(first_name , last_name , address               , zip_code  , city      , phone                 , mail                          , date_of_birth , driver_licence    ) VALUES
('Alexandre', 'Frèche'  , '20 rue des changes'  , '31000'   , 'Toulouse', '(+33|0)608963234'    , 'alexandre.freche@gmail.com'  , '1991/03/21'  , 'Driver license B');

INSERT INTO cv
(job_title                      , contact_information_id    ) VALUES
('Fullstack software engineer'  , 1                         );

INSERT INTO language
(label      , level ) VALUES
('English'  , 4     ),
('French'   , 5     ),
('Spanish'  , 2     );

INSERT INTO project
(title                          , year          , url                                   ) VALUES
('Family restaurant website'    , '2017/01/01'  , 'https://www.restaurantlacdazur.fr'   ),
('CV with Springboot / React'   , '2021/01/01'  , NULL                                  );

INSERT INTO project_description
(project_id , description       ) VALUES
(1          , 'Description 1'   ),
(1          , 'Description 2'   ),
(1          , 'Description 3'   ),
(2          , 'Description 1'   ),
(2          , 'Description 2'   ),
(2          , 'Description 3'   );

INSERT INTO skill
(label          , level , icon) VALUES
('Java'         , 4     , NULL),
('Python'       , 3.5   , NULL),
('PHP'          , 3     , NULL),
('JavaScript'   , 3.5   , NULL),
('React'        , 2     , NULL);

INSERT INTO study
(degree                                         , specialization                , url                               , school                        , start         , end           , city      ) VALUES
('Master degree in computer science'            , 'Software engineering'        , 'https://www.cci.univ-tours.fr/'  , 'University François Rabelais', '2016/09/01'  , '2017/09/01'  , 'Tours'   ),
('Master degree in mathematics and electronics' , 'Signal and image processing' , NULL                              , 'University of Bordeaux'      , '2013/09/01'  , '2015/09/01'  , 'Bordeaux'),
('Bachelor degree in applied mathematics'       , 'Mathematics and engineering' , NULL                              , 'University of Bordeaux'      , '2009/09/01'  , '2013/09/01'  , 'Bordeaux');

INSERT INTO study_description
(study_id   , description       ) VALUES
(1          , 'Description 1'   ),
(1          , 'Description 2'   ),
(1          , 'Description 3'   ),
(2          , 'Description 1'   ),
(2          , 'Description 2'   ),
(2          , 'Description 3'   ),
(3          , 'Description 1'   ),
(3          , 'Description 2'   ),
(3          , 'Description 3'   );

INSERT INTO work_experience
(company_id , position                                  , start         , end           ) VALUES
(1          , 'Fullstack software engineer internship'  , '2017/04/01'  , '2017/09/01'  ),
(1          , 'Junior fullstack software engineer'      , '2017/09/01'  , '2019/06/01'  ),
(2          , 'Junior fullstack software engineer'      , '2019/06/01'  , '2020/06/01'  ),
(3          , 'Fullstack software engineer'             , '2020/06/01'  , NULL          );

INSERT INTO work_experience_description
(work_experience_id , description       ) VALUES
(1                  , 'Description 1'   ),
(1                  , 'Description 2'   ),
(1                  , 'Description 3'   );

INSERT INTO cv_hobbies
(cv_id  , hobbies               ) VALUES
(1      , 'Music'               ),
(1      , 'Computer sciences'   ),
(1      , 'Gaming'              );

INSERT INTO cv_languages
(cv_id  , languages_id  ) VALUES
(1      , 1             ),
(1      , 2             ),
(1      , 3             );

INSERT INTO cv_projects
(cv_id  , projects_id   ) VALUES
(1      , 1             ),
(1      , 2             );

INSERT INTO cv_skills
(cv_id  , skills_id ) VALUES
(1      , 1         ),
(1      , 2         ),
(1      , 3         ),
(1      , 4         ),
(1      , 5         );

INSERT INTO cv_studies
(cv_id  , studies_id    ) VALUES
(1      , 1             ),
(1      , 2             ),
(1      , 3             );

INSERT INTO cv_work_experiences
(cv_id  , work_experiences_id   ) VALUES
(1      , 1                     ),
(1      , 2                     ),
(1      , 3                     ),
(1      , 4                     );