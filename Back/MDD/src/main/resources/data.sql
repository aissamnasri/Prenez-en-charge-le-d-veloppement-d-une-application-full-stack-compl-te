INSERT INTO topics(name, description)
SELECT 'Java', 'Java programming language'
WHERE NOT EXISTS (SELECT 1 FROM topics WHERE name = 'Java');

INSERT INTO topics(name, description)
SELECT 'Spring Boot', 'Spring Boot backend framework'
WHERE NOT EXISTS (SELECT 1 FROM topics WHERE name = 'Spring Boot');

INSERT INTO topics(name, description)
SELECT 'Angular', 'Angular frontend framework'
WHERE NOT EXISTS (SELECT 1 FROM topics WHERE name = 'Angular');

INSERT INTO topics(name, description)
SELECT 'Docker', 'Containerization platform'
WHERE NOT EXISTS (SELECT 1 FROM topics WHERE name = 'Docker');

INSERT INTO topics(name, description)
SELECT 'DevOps', 'CI/CD and deployment'
WHERE NOT EXISTS (SELECT 1 FROM topics WHERE name = 'DevOps');
