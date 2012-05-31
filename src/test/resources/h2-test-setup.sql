drop table if exists USERS;
create table USERS (
ID identity,
USERNAME varchar(256),
CREATION_USER varchar(256),
CREATION_DATETIME timestamp,
constraint PK_USER_ID primary key (ID));

drop table if exists TAGS;
create table TAGS (
ID identity,
KEY varchar(256),
VALUE varchar(256),
constraint PK_TAG_ID primary key (ID));

drop table if exists PROPERTIES;
create table PROPERTIES (
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
constraint PK_PROP_ID primary key (ID),
constraint UC_PROP_KEY unique (VALUE));