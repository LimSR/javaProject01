--���̺������� ����
DELETE FROM SALE;
DELETE FROM INVENTORY;
DELETE FROM WARECHOUSING;
DELETE FROM DELIVERY;
DELETE FROM MEAT;

--���̺� ����
DROP TABLE SALE;
DROP TABLE INVENTORY;
DROP TABLE WARECHOUSING;
DROP TABLE MEAT;
DROP TABLE DELIVERY;

--���޾�ü
CREATE TABLE DELIVERY(
	DCODE NUMBER(5) PRIMARY KEY,
	DCOMPANYNAME VARCHAR2(30) NOT NULL,
	DCOMPANYADDR VARCHAR(200),
	CONTRACTENDDATE DATE
);

--����
CREATE TABLE MEAT(
	MEATCODE VARCHAR2(8) PRIMARY KEY,
	MEATTYPE VARCHAR2(15) NOT NULL CHECK(MEATTYPE IN('�߰���','��������','�����')),
	MEATAREA VARCHAR2(15) NOT NULL,
	ORIGIN VARCHAR2(10) NOT NULL,
	GRADE VARCHAR2(10) NOT NULL,
	TOTAL_STOCK NUMBER(7),
	PRICE NUMBER(7) NOT NULL
);

--�԰�
CREATE TABLE WARECHOUSING(
	WCODE VARCHAR2(10) PRIMARY KEY,
	MEATCODE VARCHAR2(8) REFERENCES MEAT(MEATCODE) NOT NULL,
	MEATAMOUNT NUMBER(7) NOT NULL,
	ARRIVAL_TIME DATE NOT NULL,
	EXPIRATION_DATE DATE NOT NULL,
	PRICE NUMBER(7) NOT NULL,
	DCODE NUMBER(5) REFERENCES DELIVERY(DCODE)
);

--���
CREATE TABLE INVENTORY(
	WCODE VARCHAR2(10) PRIMARY KEY REFERENCES WARECHOUSING(WCODE),
	REMAININGAMOUNT NUMBER(7) NOT NULL
);

--�Ǹ�
CREATE TABLE SALE(
	SCODE VARCHAR2(10) PRIMARY KEY,
	WCODE VARCHAR2(10) NOT NULL REFERENCES INVENTORY(WCODE),
	SALEMOUNT NUMBER(7) NOT NULL,
	SPRICE NUMBER(7) NOT NULL,
	SALEDATE DATE NOT NULL
); 

--������ ����
DROP SEQUENCE DEL_SEQ;
CREATE SEQUENCE DEL_SEQ;
DROP SEQUENCE WAR_SEQ;
CREATE SEQUENCE WAR_SEQ;
DROP SEQUENCE SALE_SEQ;
CREATE SEQUENCE SALE_SEQ;

--��� Ʈ����
--�԰��Ǹ� ����� �߰��Ǵ� Ʈ����
CREATE OR REPLACE TRIGGER INVENROTYADD
AFTER INSERT ON WARECHOUSING
FOR EACH ROW
DECLARE
BEGIN
		    
	INSERT INTO INVENTORY VALUES(:NEW.WCODE,:NEW.MEATAMOUNT);
    
    UPDATE MEAT SET TOTAL_STOCK=TOTAL_STOCK+:NEW.MEATAMOUNT 
    WHERE MEATCODE=:NEW.MEATCODE;
END;
/

--�ǸŵǸ� �Ǹ��� �縸ŭ ���������� Ʈ����
CREATE OR REPLACE TRIGGER INVENTORYDEL
AFTER INSERT ON SALE
FOR EACH ROW
DECLARE
    VMEATCODE MEAT.MEATCODE%TYPE;
    VREMA INVENTORY.REMAININGAMOUNT%TYPE;
BEGIN
    SELECT M.MEATCODE INTO VMEATCODE
    FROM MEAT M,WARECHOUSING W
    WHERE M.MEATCODE=W.MEATCODE AND W.WCODE=:NEW.WCODE;

    UPDATE INVENTORY SET REMAININGAMOUNT=REMAININGAMOUNT-:NEW.SALEMOUNT
    WHERE WCODE=:NEW.WCODE;
    
    UPDATE MEAT SET TOTAL_STOCK=TOTAL_STOCK-:NEW.SALEMOUNT 
    WHERE MEATCODE=VMEATCODE;
    
    SELECT REMAININGAMOUNT INTO VREMA
    FROM INVENTORY WHERE WCODE=:NEW.WCODE;
    
    IF VREMA=0 THEN
		DELETE FROM INVENTORY WHERE WCODE=:NEW.WCODE;
	END IF;
END;
/

--����ȭ�� �� ����
CREATE OR REPLACE VIEW MAINVIEW
AS
SELECT W.WCODE,M.MEATTYPE,M.MEATAREA,M.GRADE,M.ORIGIN,
    TO_CHAR(W.ARRIVAL_TIME,'YYYY/MM/DD HH24:MI:SS') AS "ARRIVAL_TIME",
    TO_CHAR(W.MEATAMOUNT,'999,999') AS "MEATAMOUNT" ,
    TO_CHAR(W.EXPIRATION_DATE,'YYYY/MM/DD') AS EXPIRATION_DATE,
    TO_CHAR(I.REMAININGAMOUNT,'999,999') AS REMAININGAMOUNT,
    TO_CHAR(M.PRICE,'999,999') AS "PRICE"
FROM WARECHOUSING W,MEAT M,INVENTORY I
WHERE W.MEATCODE=M.MEATCODE AND W.WCODE=I.WCODE;

SELECT * FROM MAINVIEW;