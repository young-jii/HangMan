-- 시퀀스 생성 >> wordtb - wordid
CREATE SEQUENCE seq_wordtb
START WITH 1
INCREMENT BY 1
NOMAXVALUE;

-- 시퀀스 값을 테이블의 컬럼에 자동으로 할당 (옵션: BEFORE INSERT는 삽입 전에 할당, AFTER INSERT는 삽입 후에 할당)
CREATE TRIGGER wordtb_trigger
BEFORE INSERT ON wordtb
FOR EACH ROW
BEGIN
    SELECT seq_wordtb.NEXTVAL
    INTO :new.wordid
    FROM dual;
END;

-- 시퀀스 생성 >> leveltb > levelid
CREATE SEQUENCE seq_leveltb
START WITH 1
INCREMENT BY 1
NOMAXVALUE;

-- 시퀀스 값을 테이블의 컬럼에 자동으로 할당 (옵션: BEFORE INSERT는 삽입 전에 할당, AFTER INSERT는 삽입 후에 할당)
CREATE TRIGGER leveltb_trigger
BEFORE INSERT ON leveltb
FOR EACH ROW
BEGIN
    SELECT seq_leveltb.NEXTVAL
    INTO :new.levelid
    FROM dual;
END;

-- 시퀀스 생성 >> gametb > gameid
CREATE SEQUENCE seq_gametb
START WITH 1
INCREMENT BY 1
NOMAXVALUE;

-- 시퀀스 값을 테이블의 컬럼에 자동으로 할당 (옵션: BEFORE INSERT는 삽입 전에 할당, AFTER INSERT는 삽입 후에 할당)
CREATE TRIGGER gametb_trigger
BEFORE INSERT ON gametb
FOR EACH ROW
BEGIN
    SELECT seq_gametb.NEXTVAL
    INTO :new.gameid
    FROM dual;
END;

-- 시퀀스 생성 >> roundtb  > roundid
CREATE SEQUENCE seq_roundtb
START WITH 1
INCREMENT BY 1
NOMAXVALUE;

-- 시퀀스 값을 테이블의 컬럼에 자동으로 할당 (옵션: BEFORE INSERT는 삽입 전에 할당, AFTER INSERT는 삽입 후에 할당)
CREATE TRIGGER roundtb_trigger
BEFORE INSERT ON roundtb
FOR EACH ROW
BEGIN
    SELECT seq_roundtb.NEXTVAL
    INTO :new.roundid
    FROM dual;
END;

-- 시퀀스 생성 >> scoretb  > scoreid
CREATE SEQUENCE seq_scoretb
START WITH 1
INCREMENT BY 1
NOMAXVALUE;

-- 시퀀스 값을 테이블의 컬럼에 자동으로 할당 (옵션: BEFORE INSERT는 삽입 전에 할당, AFTER INSERT는 삽입 후에 할당)
CREATE TRIGGER scoretb_trigger
BEFORE INSERT ON scoretb
FOR EACH ROW
BEGIN
    SELECT seq_scoretb.NEXTVAL
    INTO :new.scoreid
    FROM dual;
END;