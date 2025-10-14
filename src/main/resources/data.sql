INSERT INTO role (name) VALUES ('USER') ON CONFLICT DO NOTHING;
INSERT INTO role (name) VALUES ('ADMIN') ON CONFLICT DO NOTHING;

INSERT INTO user_entity(username, password, created_at) VALUES ('user', '{noop}User123!', NOW()) ON CONFLICT DO NOTHING;
INSERT INTO roles_and_users (role_id, user_id) VALUES (1, 1) ON CONFLICT DO NOTHING;

INSERT INTO project (user_id, title, investor, country, voivodeship, city, created_at, module_power, module_length, module_width, module_frame)
VALUES (1, 'Title1', 'Investor', 'Country', 'Voivodeship', 'City', now(), 500, 1800, 1100, 35),
(1, 'Title2', 'Investor', 'Country', 'Voivodeship', 'City', now(), 500, 1800, 1100, 35),
(1, 'Title3', 'Investor', 'Country', 'Voivodeship', 'City', now(), 500, 1800, 1100, 35),
(1, 'Title4', 'Investor', 'Country', 'Voivodeship', 'City', now(), 500, 1800, 1100, 35),
(1, 'Title5', 'Investor', 'Country', 'Voivodeship', 'City', now(), 500, 1800, 1100, 35),
(1, 'Title6', 'Investor', 'Country', 'Voivodeship', 'City', now(), 500, 1800, 1100, 35),
(1, 'Title7', 'Investor', 'Country', 'Voivodeship', 'City', now(), 500, 1800, 1100, 35),
(1, 'Title8', 'Investor', 'Country', 'Voivodeship', 'City', now(), 500, 1800, 1100, 35),
(1, 'Title9', 'Investor', 'Country', 'Voivodeship', 'City', now(), 500, 1800, 1100, 35),
(1, 'Title10', 'Investor', 'Country', 'Voivodeship', 'City', now(), 500, 1800, 1100, 35),
(1, 'Title11', 'Investor', 'Country', 'Voivodeship', 'City', now(), 500, 1800, 1100, 35),
(1, 'Title12', 'Investor', 'Country', 'Voivodeship', 'City', now(), 500, 1800, 1100, 35);

INSERT INTO construction_material (name) VALUES
('End clamp 30 mm'), ('End clamp 35 mm'), ('End clamp 40 mm'), ('Mid clamp'), ('Alle screw 20 mm'), ('Allen screw 25 mm'),
('Allen screw 30 mm'), ('Sliding key'), ('Sleeve for threaded rod'), ('Chemical anchor'), ('EPDM M10'),
('Threaded rod M10'), ('Aluminium angle bar 40x3'), ('Trapeze screws'), ('Trapeze'), ('Screws for vario hook'),
('Adapter'), ('Double threaded screw L=250mm'), ('Vario hook'), ('Hexagon nut M10'), ('Hexagon screw M10x250'),
('Profile joiner'), ('Aluminium profile 40x40');
