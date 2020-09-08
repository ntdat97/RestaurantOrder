/*insert into orderdetail values('','2','fo001','2','500000','tb001','bos01');
update orderdetail set itemid='fo001',itemqty='3',amount='760000' where itemid='fo001';*/
delimiter $$
create procedure deleteft(tableid char(5))
begin
	if exists (select * from orderdetail s where s.tableid=tableid)
	then 
	delete from orderdetail where orderdetail.tableid=tableid;
	end if;
end$$
create function returnname(staffid char(5))
returns varchar(50)
begin
	return (select staffname from staffid s where s.staffid=staffid);
end$$
create function returnamefo(itemid char(5))
returns varchar(50)
begin
	return (select itemname from menuitem s where s.itemid=itemid);
end$$
create function returtable(tableid char(5))
returns varchar(50)
begin
	return (select tablename from dinnertable s where s.tableid=tableid);
end$$
create function priceofone(itemid char(5),qty int)
returns float
begin
	declare price float DEFAULT null;
	select itemprice into price from menuitem s where s.itemid=itemid;
	return price*qty;
end$$
create procedure setstatus(tableid char(8))
begin
	if (select statusid from dinnertable s where s.tableid=tableid) = 1
	then
	update dinnertable set statusid=2 where dinnertable.tableid=tableid;
	end if;
end$$
create function addorderid(tableid char(5))
returns int
begin
	DECLARE t int DEFAULT NULL;
	DECLARE ex char(9) DEFAULT 0;
	DECLARE curmax int DEFAULT NULL;
	declare history int default 0;
	select max(orderidh) into history from orderhistory;
	select max(orderid) into ex from orderdetail;
	select max(orderid) into t from orderdetail s where s.tableid=tableid;
	if (history>=ex)
	then
		set curmax = history;
	else
		set curmax = ex;
	end if;
	if (curmax is null)
	then 
		return 1;
	end if;
	if (t is not null)
	then
	return (select max(orderid) from orderdetail s where s.tableid=tableid);
	else
	return curmax+1;
	end if;
end$$
create procedure installval(in itemid char(5),itemqty int,amount float,tableid char(8),staffid char(5))
begin
	declare t int default 1;
	select addorderid(tableid) into t;
	insert into orderdetail values('',t,itemid,itemqty,amount,tableid,staffid);
end$$
create procedure ttpd(tableid char(5))
begin
	/*insert into temp ()*/
	declare orderidhistory1 int;
	declare orderidh1 int;
	declare billdate1 datetime;
	declare itemid1 char(5);
	declare itemidname varchar(50);
	declare itemqty1 int;
	declare amount1 float;
	declare totalamount1 float;
	declare tableidh1 char(8);
	declare tablename varchar(8);
	declare staffidh1 char(5);
	declare staffname char(5);
	declare discount1 float;
	declare curname int;
	DECLARE done INT DEFAULT FALSE;
	DECLARE cur1 CURSOR FOR select orderidname from orderdetail s where s.tableid=tableid;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
	insert into orderhistory select * from orderdetail s where s.tableid=tableid;
	select sum(amounth) into totalamount1 from orderhistory s where s.tableidh=tableid;
	open cur1;
	myloop: loop
	fetch cur1 into curname;
	 IF done THEN
      LEAVE myloop;
    END IF;
	select orderidhistory,orderidh,itemidh,itemqtyh,amounth,tableidh,staffidh into 
	orderidhistory1,orderidh1,itemid1,itemqty1,amount1,tableidh1,staffidh1 from orderhistory m where m.orderidhistory=curname;
	select returnamefo(itemid1) into itemidname;
	select returnname(staffidh1) into tablename;
	select returtable(tableid) into staffname;
	set billdate1 = CURRENT_TIMESTAMP();
	set discount1 = 0;
	insert into bill values('',orderidhistory1,orderidh1,billdate1,itemidname,itemqty1,amount1,totalamount1,staffname,tablename,discount1,1);
	end loop;
	close cur1;
	delete from orderdetail where orderdetail.tableid=tableid;
	update dinnertable set statusid=1 where dinnertable.tableid=tableid;
end$$
create procedure setst(tableid char(5))
begin
	update dinnertable set statusid = 1 where dinnertable.tableid=tableid;
end$$
delimiter ;
insert into orderhistory select * from orderdetail where tableid='tb002';
drop function addorderid;
drop procedure ttpd;
select addorderid('tb001');
delete from orderhistory where tableidh='tb002';
select sum(amounth)  from orderhistory where tableidh='tb007';
call deleteft('tb013');
call setstatus('tb006');
select priceofone('fo008',1);
select returnamefo('fo008');
select returnname('bos01');
call ttpd('tb007');
SELECT CURRENT_TIMESTAMP();
select orderid from orderdetail s where s.tableid='tb007';
select max(orderid) from orderdetail s where s.tableid='tb013';
call installval('fo005','5','145500','tb004','bos01');
update orderdetail set orderdetail.itemid=itemid,orderdetail.itemqty=itemqty,orderdetail.amount
			=amount where orderdetail.itemid=itemid;
ALTER TABLE `menu`.`staff`  
CHANGE COLUMN `birthday` `birthday` DATE NULL DEFAULT NULL  ;
ALTER TABLE staff
DROP COLUMN username;
select * from staff;

select count(1) from (select count(distinct orderidh) from bill);
select distinct orderidh from bill order by orderidh;
select * from bill where orderidh=1;
select * from staffid where concat(staffid, '',staffname, '' , passwordd, '' ,datecreate, '' ,position,'',birthday,''
,gender,'',del) like '%nhanvien%';
/*declare orderidnamed int;
	declare	orderidd int;
	declare itemidd char(5);
	declare itemqtyd int;
	declare amountd float;
	declare tableidd char(8);
	declare staffidd char(5);
	select orderidname,orderid,itemid,itemqty,amount,tableid,staffid 
	into orderidnamed,orderidd,itemidd,itemqtyd,amountd,tableidd,staffidd from orderdetail s where s.tableid=tableid;
*/