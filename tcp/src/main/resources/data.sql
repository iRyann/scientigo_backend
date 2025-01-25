-- Users (passwords are hashed with BCrypt)
-- admin@tcp.com / admin123
-- user@tcp.com / user123
INSERT INTO users (username, email, password_hash, user_role, created_at) VALUES 
('admin', 'admin@tcp.com', '$2a$10$rDkqD9p9mVXKBtGf5JvZx.YhGxLtH7qh9gzHzKQXv6P0IhFm5dC6q', 'ADMIN', CURRENT_TIMESTAMP),
('user', 'user@tcp.com', '$2a$10$rVK52LlwyL2rWPQr9QFxYe3ZJ5D9WzQSM8zRD9D.QEjGxHZHmJsSi', 'USER', CURRENT_TIMESTAMP);

-- Courses
INSERT INTO courses (title, description, subject, created_at) VALUES
-- Mathématiques
('Analyse Complexe', 'Étude approfondie des fonctions complexes et de leurs applications', 'Mathématiques', CURRENT_TIMESTAMP),
('Algèbre Linéaire Avancée', 'Espaces vectoriels, applications linéaires et formes quadratiques', 'Mathématiques', CURRENT_TIMESTAMP),
('Probabilités et Statistiques', 'Théorie des probabilités et inférence statistique', 'Mathématiques', CURRENT_TIMESTAMP),

-- Physique
('Mécanique Quantique', 'Principes fondamentaux de la physique quantique', 'Physique', CURRENT_TIMESTAMP),
('Électromagnétisme', 'Théorie des champs électromagnétiques et équations de Maxwell', 'Physique', CURRENT_TIMESTAMP),
('Thermodynamique', 'Principes et applications de la thermodynamique', 'Physique', CURRENT_TIMESTAMP),

-- Informatique
('Algorithmique Avancée', 'Conception et analyse d''algorithmes complexes', 'Informatique', CURRENT_TIMESTAMP),
('Architecture des Ordinateurs', 'Organisation et fonctionnement des systèmes informatiques', 'Informatique', CURRENT_TIMESTAMP),
('Bases de Données', 'Conception et optimisation des bases de données', 'Informatique', CURRENT_TIMESTAMP);

-- Lessons
INSERT INTO lessons (course_id, title, content, created_at) VALUES
-- Mathématiques
(1, 'Fonctions holomorphes', 'Définitions et propriétés des fonctions holomorphes', CURRENT_TIMESTAMP),
(1, 'Théorème des résidus', 'Applications au calcul d''intégrales complexes', CURRENT_TIMESTAMP),
(2, 'Diagonalisation', 'Valeurs propres, vecteurs propres et applications', CURRENT_TIMESTAMP),
(2, 'Formes de Jordan', 'Réduction de Jordan et applications', CURRENT_TIMESTAMP),
(3, 'Lois continues', 'Étude des principales lois de probabilité continues', CURRENT_TIMESTAMP),
(3, 'Tests statistiques', 'Méthodologie des tests d''hypothèses', CURRENT_TIMESTAMP),

-- Physique
(4, 'Équation de Schrödinger', 'Résolution pour différents potentiels', CURRENT_TIMESTAMP),
(4, 'Spin et moment angulaire', 'Théorie du moment cinétique quantique', CURRENT_TIMESTAMP),
(5, '��quations de Maxwell', 'Formulation et applications', CURRENT_TIMESTAMP),
(5, 'Ondes électromagnétiques', 'Propagation dans différents milieux', CURRENT_TIMESTAMP),
(6, 'Premier principe', 'Conservation de l''énergie et applications', CURRENT_TIMESTAMP),
(6, 'Machines thermiques', 'Cycles thermodynamiques et rendements', CURRENT_TIMESTAMP),

-- Informatique
(7, 'Complexité algorithmique', 'Analyse asymptotique et classes de complexité', CURRENT_TIMESTAMP),
(7, 'Programmation dynamique', 'Optimisation par sous-problèmes', CURRENT_TIMESTAMP),
(8, 'Pipeline d''instructions', 'Optimisation des performances processeur', CURRENT_TIMESTAMP),
(8, 'Hiérarchie mémoire', 'Caches et mémoire virtuelle', CURRENT_TIMESTAMP),
(9, 'Modèle relationnel', 'Conception de schémas relationnels', CURRENT_TIMESTAMP),
(9, 'Optimisation SQL', 'Techniques d''optimisation des requêtes', CURRENT_TIMESTAMP);

-- Items (création des items pour chaque cours et leçon)
INSERT INTO items (item_type, lesson_id, course_id)
SELECT 'course', NULL, course_id FROM courses
UNION ALL
SELECT 'lesson', lesson_id, NULL FROM lessons;

-- Favorites (quelques favoris d'exemple)
INSERT INTO favorites (user_id, item_id, created_at) VALUES
(1, 1, CURRENT_TIMESTAMP),
(1, 4, CURRENT_TIMESTAMP),
(2, 7, CURRENT_TIMESTAMP);

-- Comments
INSERT INTO comments (lesson_id, user_id, content, created_at) VALUES
(1, 1, 'Excellente explication des conditions de Cauchy-Riemann', CURRENT_TIMESTAMP),
(7, 2, 'La dérivation de l''équation de Schrödinger est très claire', CURRENT_TIMESTAMP),
(13, 1, 'Les exemples de complexité sont très pertinents', CURRENT_TIMESTAMP);

-- Forum Threads
INSERT INTO forum_threads (user_id, title, created_at, subject, content) VALUES
(1, 'Question sur les espaces de Hilbert', CURRENT_TIMESTAMP,'Mathématiques', ''),
(2, 'Problème d''optimisation SQL', CURRENT_TIMESTAMP,'Informatique', '');

-- Forum Posts
INSERT INTO forum_posts (thread_id, user_id, content, created_at) VALUES
(1, 1, 'Comment démontrer la complétude d''un espace de Hilbert ?', CURRENT_TIMESTAMP),
(1, 2, 'Voici une approche utilisant le critère de Cauchy...', CURRENT_TIMESTAMP),
(2, 2, 'Comment optimiser une requête avec plusieurs JOIN ?', CURRENT_TIMESTAMP),
(2, 1, 'L''utilisation des index et la réécriture des sous-requêtes peuvent aider...', CURRENT_TIMESTAMP);
