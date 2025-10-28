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

INSERT INTO electrical_material (name) VALUES
('3 Phased inverter 3 kW'),('3 Phased inverter 4 kW'),('3 Phased inverter 5 kW'),('3 Phased inverter 6 kW'),('3 Phased inverter 8 kW'),('3 Phased inverter 10 kW'),
('3 Phased inverter 12 kW'),('3 Phased inverter 15 kW'),('3 Phased inverter 17 kW'),('3 Phased inverter 20 kW'),('3 Phased inverter 25 kW'),('3 Phased inverter 30 kW'),
('3 Phased inverter 40 kW'),('3 Phased inverter 50 kW'),('Photovoltaic module'),('DC cable 4 mm2'),('DC cable 6 mm2'),
('1 Phased inverter 2.00 kW'),('1 Phased inverter 2.50 kW'),('1 Phased inverter 3.00 kW'), ('1 Phased inverter 3.60 kW'),
('AC cable 5x2.5 mm2'),('AC cable 5x4.0 mm2'),('AC cable 5x6.0 mm2'),('AC cable 5x10.0 mm2'),('AC cable 5x16.0 mm2'),('AC cable 5x25.0 mm2'),
('AC cable 3x2.5 mm2'),('AC cable 3x4.0 mm2'),('AC cable 3x6.0 mm2'),('LgY cable 6 mm2'),('LgY cable 16 mm2'),
('Overcurrent circuit breaker 3p B6'),('Overcurrent circuit breaker 3p C6'),('Overcurrent circuit breaker 3p B10'),('Overcurrent circuit breaker 3p C10'),
('Overcurrent circuit breaker 3p B16'),('Overcurrent circuit breaker 3p C16'),('Overcurrent circuit breaker 3p B20'),('Overcurrent circuit breaker 3p C20'),
('Overcurrent circuit breaker 3p B25'),('Overcurrent circuit breaker 3p C25'),('Overcurrent circuit breaker 3p B32'),('Overcurrent circuit breaker 3p C32'),
('Overcurrent circuit breaker 3p B40'),('Overcurrent circuit breaker 3p C40'),('Overcurrent circuit breaker 3p B50'),('Overcurrent circuit breaker 3p C50'),
('Overcurrent circuit breaker 1p B20'), ('Overcurrent circuit breaker 1p B10'), ('Overcurrent circuit breaker 1p B16'),
('Overcurrent circuit breaker 1p C20'), ('Overcurrent circuit breaker 1p C10'), ('Overcurrent circuit breaker 1p C16'),
('Differential circuit breaker 4p 20/0,1A'),('Differential circuit breaker 4p 40/0,1A'),('Differential circuit breaker 4p 50/0,1A'),('Differential circuit breaker 4p 80/0,1A'),
('Differential circuit breaker 4p 100/0,1A'),('Differential circuit breaker 2P 40/0,1A'),('Differential circuit breaker 2P 20/0,1A'),
('Surge arrester AC 4P T2'),('Surge arrester AC 4P T1+2'),('Surge arrester DC 3P T2'),('Surge arrester DC 3P T1+2'), ('Surge arrester AC 2p T2'), ('Surge arrester AC 2p T1+2'),
('Surge arrester 4P T2'), ('DC fuse holder 2p'), ('DC fuse 15A'),('DC Switchboard 8P'),('DC Switchboard 12P'),('DC Switchboard 24P'),('DC Switchboard 36P'),
('AC Switchboard 4P'),('AC Switchboard 8P'),('AC Switchboard 12P'),('AC Switchboard 24P');