CREATE TABLE UserTB (
    userId VARCHAR2(20) NULL,
    userPw VARCHAR2(20) NULL,
    name VARCHAR2(50) NULL,
    createdDt VARCHAR2(50) NULL
);

CREATE TABLE WordTB (
    wordId INT NOT NULL,
    spelling VARCHAR2(100) NULL,
    wordLen INT NULL
);

CREATE TABLE LevelTB (
    levelId INT NOT NULL,
    difficulty VARCHAR2(50) NOT NULL,
    lenStart INT NULL,
    lenEnd INT NULL,
    levelScore INT NULL
);

CREATE TABLE RankTB (
    userId VARCHAR2(20) NULL,
    allScore INT NULL,
    RankNo INT NULL
);

CREATE TABLE ScoreTB (
    ScoreId INT NOT NULL,
    userId VARCHAR2(20) NULL,
    gameId INT NOT NULL,
    TotalScore INT NULL,
    TFScore INT NULL,
    bonusScore INT NULL,
    wordScore INT NULL
);

CREATE TABLE GameTB (
    gameId INT NOT NULL,
    userId VARCHAR2(20) NULL,
    datetime VARCHAR2(50) NULL
);

CREATE TABLE RoundTB (
    roundId INT NOT NULL,
    wordID INT NOT NULL,
    levelId INT NOT NULL,
    gameId INT NOT NULL,
    userId VARCHAR2(20) NULL
);

CREATE TABLE roundDetailTB (
    roundId INT NOT NULL,
    input VARCHAR2(50) NULL,
    wordtf VARCHAR2(50) NULL,
    iswin VARCHAR2(1) NULL
);

ALTER TABLE UserTB ADD CONSTRAINT PK_USERTB PRIMARY KEY (userId);

ALTER TABLE WordTB ADD CONSTRAINT PK_WORDTB PRIMARY KEY (wordId);

ALTER TABLE LevelTB ADD CONSTRAINT PK_LEVELTB PRIMARY KEY (levelId);

ALTER TABLE ScoreTB ADD CONSTRAINT PK_SCORETB PRIMARY KEY (ScoreId);

ALTER TABLE GameTB ADD CONSTRAINT PK_GAME PRIMARY KEY (gameId);

ALTER TABLE RoundTB ADD CONSTRAINT PK_ROUND PRIMARY KEY (roundId);

ALTER TABLE RankTB ADD CONSTRAINT FK_ScoreTB_TO_RankTB_2 FOREIGN KEY (userId)
REFERENCES ScoreTB (userId);

ALTER TABLE ScoreTB ADD CONSTRAINT FK_UserTB_TO_ScoreTB_1 FOREIGN KEY (userId)
REFERENCES UserTB (userId);

ALTER TABLE ScoreTB ADD CONSTRAINT FK_Game_TO_ScoreTB_1 FOREIGN KEY (gameId)
REFERENCES GameTB (gameId);

ALTER TABLE GameTB ADD CONSTRAINT FK_UserTB_TO_Game_1 FOREIGN KEY (userId)
REFERENCES UserTB (userId);

ALTER TABLE RoundTB ADD CONSTRAINT FK_WordTB_TO_Round_1 FOREIGN KEY (wordID)
REFERENCES WordTB (wordId);

ALTER TABLE RoundTB ADD CONSTRAINT FK_LevelTB_TO_Round_1 FOREIGN KEY (levelId)
REFERENCES LevelTB (levelId);

ALTER TABLE RoundTB ADD CONSTRAINT FK_Game_TO_Round_1 FOREIGN KEY (gameId)
REFERENCES GameTB (gameId);

ALTER TABLE roundDetailTB ADD CONSTRAINT FK_Round_TO_roundDetail_1 FOREIGN KEY (roundId)
REFERENCES RoundTB (roundId);
