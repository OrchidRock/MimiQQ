drop table if exists qflock_number;
drop table if exists qflock_record;
drop table if exists quser_apply_qflock;
drop table if exists qflock;

drop table if exists qfriend_record;
drop table if exists qfriend;

drop table if exists quser_login_log;
drop table if exists quser_invite_quser;
drop table if exists quser;

create table quser (
	ID char(8) not null primary key,
	nickname varchar(10) not null,
	password char(16) not null,
	email varchar(20),
	image blob(6144)
	onlinestate boolean default false,
	ipaddress bigint,
	openPort int,
);
create table quser_invite_quser(
	inviterID char(8) not null,
	retivinID char(8) not null,
	notes varchar(50),
	applyState enum('waiting','ok','failed') default 'waiting',
	primary key(inviterID,retivinID),
	foreign key(inviterID) references quser(ID)
		on delete cascade,
	foreign key(retivinID) references quser(ID)
		on delete cascade
);

create table qflock(
	qflockID char(8) not null primary key,
	qflockname varchar(10),
	createrID char(8),
	createDate datetime,
	image blob(6144),
	notes varchar(200),
	foreign key(createrID) references quser(ID)
			on delete set null
);
create table qflock_record(
	qflockID char(8) not null,
	senderID char(8) not null,
	recordID char(10) not null,
	recordType enum('message','picture','file','iamback','goodbay','iamquit') default 'message',	
	recordDate datetime not null,
	data varchar(512),
	primary key(qflockID,senderID,recordID),
	foreign key(senderID) references quser(ID),
	foreign key(qflockID) references qflock(qflockID)
		on delete cascade
);
create table quser_apply_qflock(
	qflockID char(8),
	applicantID char(8),
	notes varchar(50),
	applyState enum('waiting','ok','failed') default 'waiting',
	primary key(qflockID,applicantID),
	foreign key(qflockID) references qflock(qflockID)
		on delete cascade,
	foreign key(applicantID) references quser(ID)
);
create table qflock_number (
	qflockID char(8),
	numberID char(8),
	primary key(qflockID,numberID),
	foreign key(qflockID) references qflock(qflockID)
		on delete cascade,
	foreign key(numberID) references quser(ID)
);
create table qfriend(
	ownerID char(8) not null,
	qfriendID char(8) not null,
	groupname varchar(10) default 'myfriends',
	remark varchar(10),
	hasSession boolean default false,
	primary key(ownerID,qfriendID),
	foreign key(ownerID) references quser(ID)
		on delete cascade,
	foreign key(qfriendID) references quser(ID)
		on delete cascade
);
create table qfriend_record(
	ownerID char(8) not null,
	qfriendID char(8) not null,
	recordID char(10) not null,
	recordtype enum('message','picture','file','iamback','goodbay','breakoff','seehere') 
		default 'message',	
	recordDate datetime not null,
	data varchar(512),
	primary key(ownerID,qfriendID,recordID),
	foreign key(ownerID) references quser(ID)
		on delete cascade,
	foreign key(qfriendID) references quser(ID)
		on delete cascade

);
create table quser_login_log(
	ID char(8) not null,
	logID char(10) not null,
	logdate datetime not null,
	logIPaddress bigint,
	logstate boolean default true,
	primary key(ID,logID),
	foreign key(ID) references quser(ID)
		on delete cascade
);
