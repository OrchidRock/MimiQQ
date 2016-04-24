drop table if exists users;
drop table if exists friend;
drop table if exists user_flock;
drop table if exists flock;
drop table if exists flock_number;
drop table if exists user_log;
drop table if exists logs;
create table users (
	ID char(8) not null primary key,
	nickname varchar(10) not null,
	password char(128) not null,
	ipaddress bigint,
	onlinestate boolean default false,
	email varchar(20),
	phonenumber char(11),
	image blob(6144)
);

create table friend(
	ID char(8) not null,
	friendID char(8) not null,
	groupname varchar(10) default 'myfriends',
	remark varchar(10),
	primary key(ID,friendID),
	foreign key(ID) references users(ID)
		on delete cascade,
	foreign key(friendID) references users(ID)
);
create table user_flock(
	ID char(8) not null,
	flockID char(8) not null,
	primary key(ID,flockID),
	foreign key(ID) references users(ID)
		on delete cascade
);
create table flock(
	flockID char(8) not null,
	flockname varchar(10) not null,
	createdate datetime not null,
	primary key(flockID),
	foreign key(flockID) references user_flock(flockID)
		on delete cascade
);
create table flock_number(
	flockID char(8) not null,
	ID char(8) not null,
	primary key(flockID,ID),
	foreign key(flockID) references user_flock(flockID)
		on delete cascade,
	foreign key(ID) references users(ID)
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
