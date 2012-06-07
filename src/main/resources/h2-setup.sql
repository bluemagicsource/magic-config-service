create table USERS (
ID identity,
USERNAME varchar(256),
CREATION_USER varchar(256) DEFAULT 'System',
CREATION_DATETIME timestamp,
constraint PK_USER_ID primary key (ID));

create table TAGS (
ID identity,
KEY varchar(256),
VALUE varchar(256),
constraint PK_TAG_ID primary key (ID));

create table PROPERTIES (
ID identity,
KEY varchar(256),
VALUE varchar(256),
VERSION int,
CREATION_USER varchar(256),
CREATION_DATETIME timestamp,
ODOMETER int DEFAULT 0,
LAST_ACCESSED_DATETIME timestamp,
LAST_ACCESSED_USER varchar(256),
LAST_MODIFIED_DATETIME timestamp,
LAST_MODIFIED_USER varchar(256),
constraint PK_PROP_ID primary key (ID),
constraint UC_PROP_KEY unique (VALUE));

create table HISTORICAL_PROPERTIES (
ID identity,
KEY varchar(256),
VALUE varchar(256),
VERSION int,
CREATION_USER varchar(256),
CREATION_DATETIME timestamp,
ODOMETER int,
LAST_ACCESSED_DATETIME timestamp,
LAST_ACCESSED_USER varchar(256),
LAST_MODIFIED_DATETIME timestamp,
LAST_MODIFIED_USER varchar(256),
constraint PK_HPROP_ID primary key (ID),
constraint UC_HPROP_KEY unique (VALUE));

create table PROPERTIES_TAG_MAPPING (
PROPERTY_ID bigint,
TAG_ID bigint,
constraint PK_PROPTAG_ID primary key (PROPERTY_ID, TAG_ID),
constraint FK_PROP_ID foreign key (PROPERTY_ID) references PROPERTIES(ID),
constraint FK_TAG_ID foreign key (TAG_ID) references TAGS(ID));
