drop table if exists logs;
drop table if exists quser_log;
drop table if exists qflcok_number;
drop table if exists qflcok;
drop table if exists invite;
drop table if exists qfriend;
drop table if exists record;
drop table if exists quser;

create table quser (
	ID char(8) not null primary key,
	nickname varchar(10) not null,
	password char(128) not null,
	ipaddress bigint,
	openPort int,
	onlinestate boolean default false,
	email varchar(20),
	image blob(6144)
);
create table record(
	recordID char(8) not null,
	subRecordID char(8) not null,
	subRecordType enum('message','picture','file') default 'message',	
	currday datetime not null,
	recordData blob(102400),
	primary key(recordID,subRecordID)
);
create table qfriend(
	ownerID char(8) not null,
	qfriendID char(8) not null,
	groupname varchar(10) default 'myfriends',
	remark varchar(10),
	hasSession boolean default false,
	recordID char(8),
	primary key(ownerID,qfriendID),
	foreign key(recordID) references record(recordID),
	foreign key(ownerID) references quser(ID)
		on delete cascade,
	foreign key(qfriendID) references quser(ID)
		on delete cascade
);
create table invite(
	inviterID char(8) not null,
	retivinID char(8) not null,
	primary key(inviterID,retivinID),
	foreign key(inviterID) references quser(ID)
		on delete cascade,
	foreign key(retivinID) references quser(ID)
		on delete cascade
);
create table qflcok(
	qflcokID char(8) primary key,
	qflcokname varchar(10),
	createrID char(8),
	createDate datetime,
	image blob(6144),
	recordID char(8),
	foreign key(recordID) references record(recordID),
	foreign key(createrID) references quser(ID)
		on delete cascade
);
create table qflcok_number (
	qflcokID char(8),
	numberID char(8),
	numberIDRole enum('creater','nnumber','applicant') default 'nnumber',
	primary key(qflcokID,numberID),
	foreign key(qflcokID) references qflcok(qflcokID)
		on delete cascade,
	foreign key(numberID) references quser(ID)
);
create table quser_log(
	ID char(8) not null,
	logID char(10) not null,
	primary key(ID,logID),
	foreign key(ID) references quser(ID)
		on delete cascade
);
create table logs(
	logID char(10) not null primary key,
	logdate datetime not null,
	logtype enum('login','exit') default 'login',
	logIPaddress bigint,
	logstate boolean default true
);
