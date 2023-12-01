CREATE TABLE IF NOT EXISTS dealer (
    iddealer SERIAL PRIMARY KEY,
    nameDealer VARCHAR(255) NOT NULL,
    emailDealer VARCHAR(255),
    representativename VARCHAR(255)
);


CREATE TABLE IF NOT EXISTS owner (
    idowner SERIAL PRIMARY KEY,
    fullnameowner VARCHAR(255) NOT NULL,
    phonenumberowner VARCHAR(20),
    emailowner VARCHAR(255),
    iddealer BIGINT,
    FOREIGN KEY (iddealer) REFERENCES dealer(iddealer)
);


CREATE TABLE IF NOT EXISTS car (
    idcar SERIAL PRIMARY KEY,
    uniquenumber VARCHAR(255) NOT NULL,
    assemblydate DATE,
    idowner BIGINT,
    FOREIGN KEY (idowner) REFERENCES owner(idowner)
);
