-- Use this to create test data for your local server. Feel free to change the name/value and user
INSERT IGNORE INTO PROPERTIES(KEY, VALUE, VERSION, CREATION_USER, CREATION_DATETIME, ODOMETER, 
       LAST_ACCESSED_DATETIME, LAST_ACCESSED_USER, LAST_MODIFIED_DATETIME, LAST_MODIFIED_USER)
  VALUES('abc', '123', 0, 'Brian E. Walsh', sysdate, 0, sysdate, 'Brian E. Walsh', sysdate, 'Brian E. Walsh');       

INSERT IGNORE INTO TAGS(KEY, TYPE)
VALUES('boom', 0);

INSERT IGNORE INTO PROPERTIES_TAG_MAPPING(PROPERTY_ID, TAG_ID)
VALUES(1, 1);