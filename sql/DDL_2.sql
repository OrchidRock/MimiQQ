drop table if exists logs;
drop table if exists user_log;
drop table if exists flock_number;
drop table if exists flock;
drop table if exists friend;
drop table if exists record;
drop table if exists users;

create table users (
	ID char(8) not null primary key,
	nickname varchar(10) not null,
	password char(128) not null,
	ipaddress bigint,
	openPort int,
	onlinestate boolean default false,
	email varchar(20),
	phonenumber char(11),
	image blob(6144)
);
create table record(
	recordID char(8) not null primary key,
	currday datetime not null,
	recordData blob(102400)
);
create table friend(
	ID char(8) not null,
	friendID char(8) not null,
	friendRole enum('real','invitee','inviter') default 'real',
	groupname varchar(10) default 'myfriends',
	remark varchar(10),
	hasSession boolean default false,
	recordid char(8),
	primary key(ID,friendID),
	foreign key(recordid) references record(recordID),
	foreign key(ID) references users(ID)
		on delete cascade,
	foreign key(friendID) references users(ID)
);
create table flock(
	flockID char(8) primary key,
	flockname varchar(10),
	createrID char(8),
	createData datetime,
	image blob(6144),
	recordid char(8),
	foreign key(recordid) references record(recordID),
	foreign key(createrID) references users(ID)
		on delete cascade
);
create table flock_number (
	flockID char(8),
	numberID char(8),
	numberIDRole enum('creater','nnumber','applicant') default 'nnumber',
	primary key(flockID,numberID),
	foreign key(flockID) references flock(flockID)
		on delete cascade,
	foreign key(numberID) references users(ID)
);
create table user_log(
	ID char(8) not null,
	logID char(10) not null,
	primary key(ID,logID),
	foreign key(ID) references users(ID)
		on delete cascade
);
create table logs(
	logID char(10) not null primary key,
	logdate datetime not null,
	logIPaddress bigint,
	logstate boolean default true
);
