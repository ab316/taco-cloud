CREATE TABLE IF NOT EXISTS Ingredient (
    id      VARCHAR(4)      NOT NULL,
    name    VARCHAR(25)     NOT NULL,
    type    VARCHAR(10)     NOT NULL
);

CREATE TABLE IF NOT EXISTS Taco (
    id          IDENTITY,
    name        VARCHAR(50)     NOT NULL,
    createdAt   TIMESTAMP       NOT NULL
);

CREATE TABLE IF NOT EXISTS Taco_Ingredients (
    taco        BIGINT          NOT NULL,
    ingredient  VARCHAR(4)      NOT NULL
);

CREATE TABLE IF NOT EXISTS Taco_Order (
    id              IDENTITY,
    name            VARCHAR(50) NOT NULL,
    street          VARCHAR(50) NOT NULL,
    city            VARCHAR(50) NOT NULL,
    state           VARCHAR(50)  NOT NULL,
    zip             VARCHAR(10) NOT NULL,
    ccNumber        VARCHAR(16) NOT NULL,
    ccExpiration    VARCHAR(5)  NOT NULL,
    ccCVV           VARCHAR(3)  NOT NULL,
    placedAt        TIMESTAMP   NOT NULL
);

CREATE TABLE IF NOT EXISTS Taco_Order_Tacos (
    tacoOrder   BIGINT  NOT NULL,
    taco        BIGINT  NOT NULL
);


ALTER TABLE Taco_Ingredients
ADD FOREIGN KEY (taco) REFERENCES Taco(id);

ALTER TABLE Taco_Ingredients
ADD FOREIGN KEY (ingredient) REFERENCES Ingredient(id);

ALTER TABLE Taco_Order_Tacos
ADD FOREIGN KEY (tacoOrder) REFERENCES Taco_Order(id);

ALTER TABLE Taco_Order_Tacos
ADD FOREIGN KEY (taco) REFERENCES Taco(id);
