create database menu CHARACTER SET = 'utf8'; 
use menu;

create table staffid (
	staffid char(5) primary key,
	staffname varchar(50),
	passwordd char(16),
	datecreate datetime,
	position varchar(50),
	birthday date,
	gender char(1),
	del char(1) default 1
);
create table class (
	classid char(3) primary key,
	classname varchar(20)
);
create table statusid (
	statusid int primary key,
	statusname varchar(30)
);

create table menuitem (
	itemid char(5) primary key,
	itemname varchar(50),
	itemdescribe varchar(200),
	itemprice float,
	classid char(3),
	del char(1) default 1,
	foreign key (classid) references class(classid)
);
create table dinnertable (
	tableid char(8) primary key,
	tablename varchar(20),
	statusid int,
	del char(1) default 1,
	foreign key (statusid) references statusid(statusid)
);
create table orderdetail (
	orderidname int primary key AUTO_INCREMENT,
	orderid int,
	itemid char(5),
	itemqty int,
	amount float,
	tableid char(8),
	staffid char(5),
	foreign key (itemid) references menuitem(itemid),
	foreign key (tableid) references dinnertable(tableid),
	foreign key (staffid) references staffid(staffid)
);
create table orderhistory (
	orderidhistory int primary key,
	orderidh int ,
	itemidh char(5),
	itemqtyh int,
	amounth float,
	tableidh char(8),
	staffidh char(5)
);
create table bill (
	billid int primary key AUTO_INCREMENT,
	orderidhistory int,
	orderidh int,
	billdate datetime,
	itemidname varchar(50),
	itemqty int,
	amount float,
	totalamount float,
	tablename varchar(8),
	staffname varchar(50),
	discount float,
	del char(1) default 1,
	foreign key (orderidhistory) references orderhistory(orderidhistory)
);

/*create table dinnertabletrack (
	serialid int primary key ,
	tableid char(5),
	orderid int,
	foreign key (tableid) references dinnertable(tableid),
	foreign key (orderid) references orderdetail(orderid)
);*/
/*create table staffdel (
	staffid char(5) primary key,
	staffname varchar(50),
	position varchar(50),
	birthday date,
	gender char(1),
	accid char(5),
	username char(16),
	passwordd char(16),
	datecreate date,
	foreign key (staffid) references staff(staffid)
);
create table menuitemdel (
	itemid char(5) primary key,
	itemname varchar(50),
	itemdescribe varchar(200),
	itemprice float,
	classid char(3),
	foreign key (itemid) references menuitem(itemid)
);*/

insert into staffid values('nv001','Nguy???n T???n ?????t','000000','2019/10/13','nhanvien','1997/02/13','M','1');
insert into staffid values('nv002','H??? H???u Uy??n','111111','2019/05/13','nhanvien','1991/04/17','M','1');
insert into staffid values('nv003','Nguy???n V??n A','222222','2019/10/13','nhanvien','1995/07/23','F','1');
insert into staffid values('bos01','Thu Hi???n','999999','2017/01/16','quanly','1989/02/23','F','1');

insert into class values('0','unclassify');
insert into class values('1','food');
insert into class values('2','drink');
insert into class values('3','other');

insert into statusid values('0','unavailable');
insert into statusid values('1','empty');
insert into statusid values('2','inuse');
insert into statusid values('3','paying');

insert into menuitem values('dr001','N?????c Su???i','','8000','2','1');
insert into menuitem values('dr002','Sting','','12000','2','1');
insert into menuitem values('dr003','7 up','','12000','2','1');
insert into menuitem values('dr004','Pepsi','','12000','2','1');
insert into menuitem values('dr005','D???a t????i','','15000','2','1');
insert into menuitem values('dr006','Sprite','','12000','2','1');
insert into menuitem values('dr007','Bia S??i G??n','','17000','2','1');
insert into menuitem values('dr008','Bia Heineken','','20000','2','1');
insert into menuitem values('dr009','Bia Tiger','','17000','2','1');
insert into menuitem values('dr010','Strong Bow','','20000','2','1');
insert into menuitem values('fo001','M??? ?? s???t b??','','40000','1','1');
insert into menuitem values('fo002','M??? ?? s???t c?? chua','','32000','1','1');
insert into menuitem values('fo003','X?? l??ch tr???n c?? ng???','','48000','1','1');
insert into menuitem values('fo004','X?? l??ch tr???n th???t b??','','50000','1','1');
insert into menuitem values('fo005','B?? Beefsteak','','58000','1','1');
insert into menuitem values('fo006','B?? l??c l???c','','88000','1','1');
insert into menuitem values('fo007','????i g?? chi??n gi??n','','48000','1','1');
insert into menuitem values('fo008','C?? ch??m n?????ng l?? chu???i','','48000','1','1');
insert into menuitem values('fo009','C?? ch??m n?????ng ti??u','','45000','1','1');
insert into menuitem values('fo010','T??m n???u xo??i','','68000','1','1');
insert into menuitem values('fo011','T??m lu???c n?????c d???a','','68000','1','1');
insert into menuitem values('fo012','M???c x??o chua ng???t','','68000','1','1');
insert into menuitem values('fo013','M???c sate n?????ng','','48000','1','1');
insert into menuitem values('fo014','Ch??? r?? r???','','24000','1','1');
insert into menuitem values('fo015','Ch??? chi??n H?? N???i','','28000','1','1');
insert into menuitem values('fo016','Soup c?? chua v???i b??nh m??','','24000','1','1');
insert into menuitem values('fo017','Kem vani','','15000','1','1');
insert into menuitem values('fo018','Kem socola','','15000','1','1');
insert into menuitem values('fo019','Tr??i c??y d??a','','25000','1','1');
insert into menuitem values('fo020','B??nh Flan','','20000','1','1');
insert into menuitem values('or001','Kh??n ?????t','','2000','3','1');

insert into dinnertable values('tb001','B??n 1','1','1');
insert into dinnertable values('tb002','B??n 2','1','1');
insert into dinnertable values('tb003','B??n 3','1','1');
insert into dinnertable values('tb004','B??n 4','1','1');
insert into dinnertable values('tb005','B??n 5','1','1');
insert into dinnertable values('tb006','B??n 6','1','1');
insert into dinnertable values('tb007','B??n 7','1','1');
insert into dinnertable values('tb008','B??n 8','1','1');
insert into dinnertable values('tb009','B??n 9','1','1');
insert into dinnertable values('tb010','B??n 10','1','1');
insert into dinnertable values('tb011','B??n 11','1','1');
insert into dinnertable values('tb012','B??n 12','1','1');
insert into dinnertable values('tb013','B??n 13','1','1');
insert into dinnertable values('tb014','B??n 14','1','1');
insert into dinnertable values('tb015','B??n 15','1','1');















