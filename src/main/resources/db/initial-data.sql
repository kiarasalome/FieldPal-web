-- USUARIOS (Admin y Jugador)
-- ============================================
INSERT INTO USERS (ID, NAME, EMAIL, PHONE, PASSWORD, ROLE, ACTIVE) VALUES ('U001', 'Admin FieldPal', 'admin@fieldpal.com', '+593990000001', 'admin123', 'ADMIN', true);
INSERT INTO USERS (ID, NAME, EMAIL, PHONE, PASSWORD, ROLE, ACTIVE) VALUES ('U002', 'Carlos Mendoza', 'jugador@fieldpal.com', '+593991234567', 'jugador123', 'PLAYER', true);

-- ORGANIZACIONES (complejos deportivos)
-- ============================================
INSERT INTO ORGANIZATIONS (ID, NAME, ZONE, ADDRESS, PHONE, EMAIL, RATING, DESCRIPTION, COURT_COUNT, LATITUDE, LONGITUDE) VALUES
    ('ORG001', 'Complejo Deportivo Loja Norte', 'NORTE', 'Av. Universitaria y Circunvalación', '+593987654321', 'contacto@lojanorte.com', 4.5, 'Complejo con canchas de fútbol y voley', 2, -3.9928, -79.2033);

-- CANCHAS (dependen de organizations)
-- ============================================
INSERT INTO COURTS (ID, ORG_ID, NAME, TYPE, PRICE_PER_HOUR, HAS_LIGHTING, COVERED, SURFACE, IMAGE_URL) VALUES ('C001', 'ORG001', 'Cancha Fútbol 1', 'FUTBOL', 15.00, true, false, 'Sintética', NULL);
INSERT INTO COURTS (ID, ORG_ID, NAME, TYPE, PRICE_PER_HOUR, HAS_LIGHTING, COVERED, SURFACE, IMAGE_URL) VALUES ('C002', 'ORG001', 'Cancha Voley 1', 'VOLEY', 10.00, false, true, 'Arena', NULL);

-- TIME SLOTS (franjas horarias por cancha)
-- ============================================
INSERT INTO TIME_SLOTS (ID, COURT_ID, SLOT_DATE, SLOT_HOUR, AVAILABLE) VALUES ('TS001', 'C001', '2026-07-25', '09:00:00', true);
INSERT INTO TIME_SLOTS (ID, COURT_ID, SLOT_DATE, SLOT_HOUR, AVAILABLE) VALUES ('TS002', 'C001', '2026-07-25', '10:00:00', true);

-- RESERVAS (dependen de users, organizations, courts)
-- ============================================
INSERT INTO RESERVATIONS (ID, USER_ID, ORG_ID, COURT_ID, RESERVATION_DATE, RESERVATION_HOUR, DURATION, PLAYER_COUNT, TOTAL_PRICE, STATUS, CONFIRMED, CONTACT_NAME, CONTACT_PHONE) VALUES
    ('R001', 'U002', 'ORG001', 'C001', '2026-07-25', '09:00:00', 2, 10, 30.00, 'UPCOMING', true, 'Carlos Mendoza', '+593991234567');

COMMIT;