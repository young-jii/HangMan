-- ������ ���� >> wordtb - wordid
CREATE SEQUENCE seq_wordtb
START WITH 1
INCREMENT BY 1
NOMAXVALUE;

-- ������ ���� ���̺��� �÷��� �ڵ����� �Ҵ� (�ɼ�: BEFORE INSERT�� ���� ���� �Ҵ�, AFTER INSERT�� ���� �Ŀ� �Ҵ�)
CREATE TRIGGER wordtb_trigger
BEFORE INSERT ON wordtb
FOR EACH ROW
BEGIN
    SELECT seq_wordtb.NEXTVAL
    INTO :new.wordid
    FROM dual;
END;

-- ������ ���� >> leveltb > levelid
CREATE SEQUENCE seq_leveltb
START WITH 1
INCREMENT BY 1
NOMAXVALUE;

-- ������ ���� ���̺��� �÷��� �ڵ����� �Ҵ� (�ɼ�: BEFORE INSERT�� ���� ���� �Ҵ�, AFTER INSERT�� ���� �Ŀ� �Ҵ�)
CREATE TRIGGER leveltb_trigger
BEFORE INSERT ON leveltb
FOR EACH ROW
BEGIN
    SELECT seq_leveltb.NEXTVAL
    INTO :new.levelid
    FROM dual;
END;

-- ������ ���� >> gametb > gameid
CREATE SEQUENCE seq_gametb
START WITH 1
INCREMENT BY 1
NOMAXVALUE;

-- ������ ���� ���̺��� �÷��� �ڵ����� �Ҵ� (�ɼ�: BEFORE INSERT�� ���� ���� �Ҵ�, AFTER INSERT�� ���� �Ŀ� �Ҵ�)
CREATE TRIGGER gametb_trigger
BEFORE INSERT ON gametb
FOR EACH ROW
BEGIN
    SELECT seq_gametb.NEXTVAL
    INTO :new.gameid
    FROM dual;
END;

-- ������ ���� >> roundtb  > roundid
CREATE SEQUENCE seq_roundtb
START WITH 1
INCREMENT BY 1
NOMAXVALUE;

-- ������ ���� ���̺��� �÷��� �ڵ����� �Ҵ� (�ɼ�: BEFORE INSERT�� ���� ���� �Ҵ�, AFTER INSERT�� ���� �Ŀ� �Ҵ�)
CREATE TRIGGER roundtb_trigger
BEFORE INSERT ON roundtb
FOR EACH ROW
BEGIN
    SELECT seq_roundtb.NEXTVAL
    INTO :new.roundid
    FROM dual;
END;

-- ������ ���� >> scoretb  > scoreid
CREATE SEQUENCE seq_scoretb
START WITH 1
INCREMENT BY 1
NOMAXVALUE;

-- ������ ���� ���̺��� �÷��� �ڵ����� �Ҵ� (�ɼ�: BEFORE INSERT�� ���� ���� �Ҵ�, AFTER INSERT�� ���� �Ŀ� �Ҵ�)
CREATE TRIGGER scoretb_trigger
BEFORE INSERT ON scoretb
FOR EACH ROW
BEGIN
    SELECT seq_scoretb.NEXTVAL
    INTO :new.scoreid
    FROM dual;
END;