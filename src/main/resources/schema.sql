DROP TABLE IF EXISTS player;
CREATE TABLE player(
    id INT AUTO_INCREMENT  PRIMARY KEY,
    first_name VARCHAR(250) NOT NULL,
    last_name VARCHAR(250) NOT NULL,
    age INTEGER NOT NULL,
    player_position VARCHAR(250) NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
     username VARCHAR(100) NOT NULL,
     password VARCHAR(100) NOT NULL,
     email VARCHAR(100) NOT NULL,
     PRIMARY KEY (username)
);