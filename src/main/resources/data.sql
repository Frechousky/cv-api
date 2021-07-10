/** 
    Populate database on application start-up.
    This script must not be executed on production.
*/
INSERT INTO company
(name                       , field                 , country   , city          ) VALUES
('sopra banking software'   , 'banking'             , 'france'  , 'tours'       ),
('sopra banking software'   , 'banking'             , 'france'  , 'toulouse'    ),
('viveris technologies'     , 'software engineering', 'france'  , 'toulouse'    );

INSERT INTO contact_information 
(first_name , last_name , address               , zip_code  , city      , phone                 , mail                          , date_of_birth , driver_licence    ) VALUES
('alexandre', 'frèche'  , '20 rue des changes'  , '31000'   , 'toulouse', '(+33)608963234'      , 'alexandre.freche@gmail.com'  , '1991/03/21'  , 'driver license b');

INSERT INTO cv
(job_title                      , contact_information_id    ) VALUES
('fullstack software engineer'  , 1                         );

INSERT INTO language
(label      , level ) VALUES
('english'  , 4     ),
('french'   , 5     ),
('spanish'  , 2     );

INSERT INTO project
(title                          , year          , url                                   ) VALUES
('family restaurant website'    , '2017/01/01'  , 'https://www.restaurantlacdazur.fr'   ),
('cv application'               , '2021/01/01'  , NULL                                  );

INSERT INTO project_description
(project_id , description       ) VALUES
(1          , 'description 1'   ),
(1          , 'description 2'   ),
(1          , 'description 3'   ),
(2          , 'description 1'   ),
(2          , 'description 2'   ),
(2          , 'description 3'   );

INSERT INTO skill
(label          , level ) VALUES
('java'         , 4     ),
('python'       , 3.5   ),
('php'          , 3     ),
('javascript'   , 3.5   ),
('react'        , 2     );

INSERT INTO study
(degree    , field                        , specialization               , url                               , school                        , start         , end           , city      ) VALUES
('MASTER'  , 'computer sciences'          , 'software engineering'       , 'https://www.cci.univ-tours.fr/'  , 'university françois rabelais', '2016/09/01'  , '2017/09/01'  , 'tours'   ),
('MASTER'  , 'mathematics and electronics', 'signal and image processing', NULL                              , 'university of bordeaux'      , '2013/09/01'  , '2015/09/01'  , 'bordeaux'),
('BACHELOR', 'applied mathematics'        , 'mathematics and engineering', NULL                              , 'university of bordeaux'      , '2009/09/01'  , '2013/09/01'  , 'bordeaux');

INSERT INTO study_description
(study_id   , description       ) VALUES
(1          , 'description 1'   ),
(1          , 'description 2'   ),
(1          , 'description 3'   ),
(2          , 'description 1'   ),
(2          , 'description 2'   ),
(2          , 'description 3'   ),
(3          , 'description 1'   ),
(3          , 'description 2'   ),
(3          , 'description 3'   );

INSERT INTO work_experience
(company_id , position                                  , start         , end           ) VALUES
(1          , 'fullstack software engineer intern'      , '2017/04/01'  , '2017/09/01'  ),
(1          , 'junior fullstack software engineer'      , '2017/09/01'  , '2019/06/01'  ),
(2          , 'junior fullstack software engineer'      , '2019/06/01'  , '2020/06/01'  ),
(3          , 'fullstack software engineer'             , '2020/06/01'  , NULL          );

INSERT INTO work_experience_description
(work_experience_id , description       ) VALUES
(1                  , 'description 1'   ),
(1                  , 'description 2'   ),
(1                  , 'description 3'   );

INSERT INTO cv_hobbies
(cv_id  , hobbies               ) VALUES
(1      , 'music'               ),
(1      , 'computer sciences'   ),
(1      , 'gaming'              );

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