-- Use this to create test data for your local server. Feel free to change the name/value and user
MERGE INTO PROPERTIES(ID, KEY, VALUE, VERSION, CREATION_USER, CREATION_DATETIME, ODOMETER, 
       LAST_ACCESSED_DATETIME, LAST_ACCESSED_USER, LAST_MODIFIED_DATETIME, LAST_MODIFIED_USER)
  VALUES(1,'abc', '123', 0, 'Brian E. Walsh', sysdate, 0, sysdate, 'Brian E. Walsh', sysdate, 'Brian E. Walsh');       

MERGE INTO TAGS(ID, KEY, TYPE)
VALUES(1,'boom', 0);

MERGE INTO PROPERTIES_TAG_MAPPING(PROPERTY_ID, TAG_ID)
VALUES(1, 1);