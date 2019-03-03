# SQL语法
## 数据库表
一个数据库通常包含一个或多个表，每个表由一个名字标识，表包含带有数据的记录。<br>


## 重要的SQL命令
SELECT - 从数据库中提取数据<br>
UPDATE - 更新数据库中的数据<br>
DELECT - 从数据库中删除数据<br>
INSERT INTO - 向数据库中插入新数据<br>
CREATE DATABASE - 创建新数据库<br>
ALTER DATABASE - 修改数据库<br>
CREATE TABLE - 创建新表<br>
ALTER TABLE - 修改新表<br>
DROP TABLE - 删除表<br>
CREATE INDEX - 创建索引<br>
DROP INDEX - 删除索引<br>


## SELECT 语句
    SELECT * FROM table_name
**问题1：在表中，一个列可能会含有多个重复值，仅列出不同的值**<br>

    SELECT DISTINCT country FROM Websites;
**问题2：从表“Websites”中选取国家为“CN”的所有网站**<br>

    SELECT * FROM Websites WHERE country='CN';
    SELECT * FROM Websites WHERE country IN 'CN','USA';
    SELECT * FROM Websites WHERE age BETWEEN 18 AND 80;
**问题3：从表“Websites”中选取国家为“CN”且alexa排名大于50的所有网站**<br>

    SELECT * FROM Websites WHERE country='CN' AND alexa>50;
**问题4：从表“Websites”中选取国家为“CN”或alexa排名大于50的所有网站**<br>

    SELECT * FROM Websites WHERE country='CN' AND alexa>50;
**问题5：从 "Websites" 表中选取所有网站，并按照 "alexa" 列排序**<br>

    SELECT * FROM Websites ORDER BY alexa ASC|DESC;


## INSERT INTO 语句
    INSERT INTO table_name (column1,column2,column3,...) VALUES (value1,value2,value3,...);
**问题6：向 "Websites" 表中插入一个新行。**<br>

    INSERT INTO Websites (name, url, alexa, country)VALUES('百度','https://www.baidu.com/','4','CN');

## UPDATE 语句
    UPDATE table_name
    SET column1=value1,column2=value2,...
    WHERE some_column=some_value;
**问题7：把 "速学堂" 的 alexa 排名更新为 5000，country 改为 USA。**<br>

    UPDATE Websites SET alexa='5000', country='USA' WHERE name='速学堂;

## DELETE 语句
    DELETE FROM table_name WHERE some_column=some_value;
**问题8：从 "Websites" 表中删除网站名为 "百度" 且国家为 CN 的网站**<br>

    DELETE FROM WebsitesWHERE name='百度' AND country='CN';

## SELECT TOP, LIMIT, ROWNUM语句
SELECT TOP 子句用于规定要返回的记录的数目。<br>

    SELECT TOP number|percent column_name(s) FROM table_name;
    SELECT * FROM Persons WHERE ROWNUM<=5;

## LIKE
LIKE 操作符用于在 WHERE 子句中搜索列中的指定模式。<br>
    SELECT column_name(s) FROM table_name WHERE column_name LIKE pattern;
**问题9：选取 name 以字母 "G" 开始的所有客户**<br>

    SELECT * FROM Websites WHERE name LIKE 'G%';
**问题10：选取 name 以字母 "K" 结尾的所有客户**<br>

    SELECT * FROM Websites WHERE name LIKE '%K';
**问题11：选取 name 包含字母 "oo" 的所有客户**<br>

    SELECT * FROM Websites WHERE name LIKE '%oo%';
**问题12：选取 name 以一个任意字符开始，然后是 "oogle" 的所有客户**<br>

    SELECT * FROM Websites WHERE name LIKE '_oogle';


## [charlist]通配符
MySQL 中使用 REGEXP 或 NOT REGEXP 运算符 (或 RLIKE 和 NOT RLIKE) 来操作正则表达式。<br>
**问题12：选取 name 以 "G"、"F" 或 "s" 开始的所有网站**<br>

    SELECT * FROM Websites WHERE name REGEXP '^[GFs]';
**问题13：选取 name  以 A 到 H 字母开头的网站**<br>

    SELECT * FROM Websites WHERE name REGEXP '^[A-H]';
**问题14：选取 name  不以 A 到 H 字母开头的网站**<br>

    SELECT * FROM Websites WHERE name REGEXP '^[^A-H]';


## IN
IN 操作符允许您在 WHERE 子句中规定多个值。<br>

    SELECT column_name(s) FROM table_name WHERE column_name IN (value1,value2,...);

## BETWEEN
BETWEEN 操作符选取介于两个值之间的数据范围内的值。这些值可以是数值、文本或者日期。<br>
    
    SELECT column_name(s) FROM table_name WHERE column_name BETWEEN value1 AND value2;

## 别名
通过使用 SQL，可以为表名称或列名称指定别名。基本上，创建别名是为了让列名称的可读性更强。<br>

    SELECT column_name AS alias_name FROM table_name;
    SELECT column_name(s) FROM table_name AS alias_name;

## FULL OUTER JOIN 关键字
FULL OUTER JOIN 关键字只要左表（table1）和右表（table2）其中一个表中存在匹配，则返回行.<br>
FULL OUTER JOIN 关键字结合了 LEFT JOIN 和 RIGHT JOIN 的结果。<br>

    SELECT column_name(s) FROM table1
    FULL OUTER JOIN table2
    ON table1.column_name=table2.column_name;

MySQL中不支持 FULL OUTER JOIN。<br>
INNER JOIN（内连接,或等值连接）：获取两个表中字段匹配关系的记录。<br>
LEFT JOIN（左连接）：获取左表所有记录，即使右表没有对应匹配的记录。<br>
RIGHT JOIN（右连接）： 与 LEFT JOIN 相反，用于获取右表所有记录，即使左表没有对应匹配的记录。<br>

    SELECT Websites.name, Websites.url, SUM(access_log.count) AS nums FROM 
    (access_log 
    INNER JOIN Websites 
    ON access_log.site_id=Websites.id)
    GROUP BY Websites.name
    HAVING SUM(access_log.count)>200


## UNION 操作符
UNION 操作符用于合并两个或多个 SELECT 语句的结果集。<br>
请注意，UNION 内部的每个 SELECT 语句必须拥有相同数量的列。列也必须拥有相似的数据类型。同时，每个 SELECT 语句中的列的顺序必须相同。<br>

    SELECT column_name(s) FROM table1
    UNION
    SELECT column_name(s) FROM table2;
如果允许重复的值：

    SELECT column_name(s) FROM table1
    UNION ALL
    SELECT column_name(s) FROM table2;

## SELECT INTO
通过 SQL，您可以从一个表复制信息到另一个表。<br>
SELECT INTO 语句从一个表复制数据，然后把数据插入到另一个新表中。<br>
**问题15：复制多个表中的数据插入到新表中**<br>

    SELECT Websites.name, access_log.count, access_log.date 
    INTO WebsitesBackup2016
    FROM Websites
    LEFT JOIN access_log
    ON Websites.id=access_log.site_id;

MYSQL仅支持INSERT INTO SELECT语句<br>

    INSERT INTO table2
    (column_name(s))
    SELECT column_name(s)
    FROM table1;

## CREATE
创建数据库

    CREATE DATABASE dbname;

创建表

    CREATE TABLE table_name
    (
    column_name1 data_type(size),
    column_name2 data_type(size),
    column_name3 data_type(size),
    ....
    );

比如：

    CREATE TABLE Persons
    (
    PersonID int,
    LastName varchar(255),
    FirstName varchar(255),
    Address varchar(255),
    City varchar(255)
    );

## SQL约束
SQL 约束用于规定表中的数据规则。<br>
如果存在违反约束的数据行为，行为会被约束终止。

    CREATE TABLE table_name
    (
    column_name1 data_type(size) constraint_name,
    column_name2 data_type(size) constraint_name,
    column_name3 data_type(size) constraint_name,
    ....
    );

通常有以下约束：

    NOT NULL

    UNIQUE - 保证某列的每行必须有唯一的值。
        增加约束：
        ALTER TABLE Persons
        ADD CONSTRAINT uc_PersonID UNIQUE (P_Id,LastName)
        删除约束:
        ALTER TABLE Persons
        DROP INDEX uc_PersonID

    PRIMARY KEY - NOT NULL + UNIQUE

    FOREIGN KEY - 保证一个表中的数据匹配到另一个表中的值的参照完整性。

    CHECK - 保证列中的值符合指定的条件
        创建表时添加约束：
        P_Id int NOT NULL CHECK (P_Id>0),
        普通添加约束：
        ALTER TABLE Persons
        ADD CHECK (P_Id>0)
        删除约束：
        ALTER TABLE Persons
        DROP CHECK chk_Person

    DEFAULT - 规定没有给列赋值时的默认值

## INDEX 索引
### 创建索引：
在不读取整个表的情况下，索引使数据库应用程序可以更快地查找数据。<br>
用户无法看到索引，它们只能被用来加速搜索/查询。

    CREATE UNIQUE INDEX index_name
    ON table_name (column_name)

比如：

    CREATE INDEX PIndex
    ON Persons (LastName)

### 删除索引：
    ALTER TABLE table_name DROP INDEX index_name

## DROP 删除
### 删表：
    DROP TABLE table_name
### 删数据库：
    DROP DATABASE database_name
### 仅仅需要删除表内的数据，但并不删除表本身
    TRUNCATE TABLE table_name

## ALTER 修改表
### 在表中加一列
    ALTER TABLE table_name
    ADD column_name datatype
### 删除表中某一列
    ALTER TABLE table_name
    DROP COLUMN column_name’
### 修改表中某一列的数据类型
    ALTER TABLE table_name
    MODIFY COLUMN column_name datatype

## AUTO INCREMENT
Auto-increment 会在新记录插入表中时生成一个唯一的数字。自动地创建主键字段的值。
    
    CREATE TABLE Persons
    (
    ID int NOT NULL AUTO_INCREMENT,
    LastName varchar(255) NOT NULL,
    FirstName varchar(255),
    Address varchar(255),
    City varchar(255),
    PRIMARY KEY (ID)
    )

## Views
在 SQL 中，视图是基于 SQL 语句的结果集的可视化的表。

    CREATE VIEW view_name AS
    SELECT column_name(s)
    FROM table_name
    WHERE condition

## Date
    NOW() - 返回当前的日期和时间
    CURDATE() - 返回当前日期
    CURTIME() - 返回当前时间
    DATE() - 提取日期或日期/时间表达式的日期部分
    EXTRACT() - 返回日期/时间的单独部分
    DATE_ADD() - 像日期添加指定的时间间隔
    DATE_SUB() - 从日期减去指定的时间间隔
    DATEDIFF() - 返回两个日期之间的天数
    DATE_FORMAT() - 用不同的格式显示日期/时间

    MYSQL中的日期格式为：YYYY-MM-DD HH:MM:SS

## NULL
无法使用比较运算符来测试 NULL 值，比如 =、< 或 <>。
我们必须使用 IS NULL 和 IS NOT NULL 操作符。

    SELECT LastName,FirstName,Address FROM Persons
    WHERE Address IS NULL

    SELECT LastName,FirstName,Address FROM Persons
    WHERE Address IS NOT NULL

## ISNULL(), NVL(), ISFULL(), COALESCE()
这四个函数是为了防止NULL对数据处理产生的影响。

    SELECT ProductName,UnitPrice*(UnitsInStock+IFNULL(UnitsOnOrder,0))
    FROM Products

    SELECT ProductName,UnitPrice*(UnitsInStock+COALESCE(UnitsOnOrder,0))
    FROM Products

## 数据类型：
MYSQL:

    boolean--N/A
    integer--Int
    float--Float
    currency--N/A
    string(fixed)--Char--255
    string(variable)--Varchar--255
    binary object-- Blob, Text--65535

## SQL 函数：
### SQL Aggregate函数
    AVG() - 返回平均值
        SELECT AVG(count) AS CountAverage FROM access_log;
    COUNT() - 返回行数
        SELECT COUNT(DISTINCT column_name) FROM table_name;
    FIRST() - 返回第一个记录的值
        MYSQL不支持该函数，可以替换为：
            SELECT column_name FROM table_name
            ORDER BY column_name ASC
            LIMIT 1;
    LAST() - 返回最后一个记录的值
        MYSQL不支持该函数，可以替换为：
            SELECT column_name FROM table_name
            ORDER BY column_name DESC
            LIMIT 1;
    MAX() - 返回最大值
        SELECT MAX(alexa) AS max_alexa FROM Websites;
    MIN() - 返回最小值
        SELECT MIN(column_name) FROM table_name;
    SUM() - 返回总和
        SELECT SUM(column_name) FROM table_name;
### SQL Scalar函数
    UCASE() - 将某个字段转换为大写
        SELECT UCASE(column_name) FROM table_name;
    LCASE() - 将某个字段转换为小写
        SELECT LCASE(column_name) FROM table_name;
    MID() - 从某个文本字段提取字符
        SELECT MID(column_name,start[,length]) FROM table_name;
    LEN() - 返回某个文本字段的长度
        SELECT LENGTH(column_name) FROM table_name;
    ROUND() - 对某个数值字段进行指定小数位数的四舍五入
        SELECT ROUND(column_name,decimals) FROM table_name;
    NOW() - 返回当前的系统日期和时间
        SELECT NOW() FROM table_name;
    FORMAT() - 格式化某个字段的显示方式
        SELECT FORMAT(column_name,format) FROM table_name;
        SELECT name, url, DATE_FORMAT(Now(),'%Y-%m-%d') AS date
FROM Websites;

## GROUP BY 语句
GROUP BY 语句用于结合聚合函数，根据一个或多个列对结果集进行分组。

    SELECT column_name, aggregate_function(column_name)
    FROM table_name
    WHERE column_name operator value
    GROUP BY column_name;

比如：统计access.log中各个site_id的访问量

    SELECT site_id, SUM(access_log.count) AS nums
    FROM access_log GROUP BY site_id;

**GROUP BY 多表连接**

    SELECT Websites.name,COUNT(access_log.aid) AS nums FROM access_log
    LEFT JOIN Websites
    ON access_log.site_id=Websites.id
    GROUP BY Websites.name;

## HAVING 子句
WHERE 关键字无法与聚合函数一起使用。<br>
HAVING 子句可以让我们筛选分组后的各组数据。

    SELECT cloumn_name, aggregate_function(column_name)
    FROM table_name
    WHERE column_name operator value
    GROUP BY column_name
    HAVING aggregate_function(column_name) operator value;

比如，需要查找总访问量大于200的网站

    SELECT Websites.name, Websites.url, SUM(access_log.count) AS nums FROM 
    (access_log 
    INNER JOIN Websites 
    ON access_log.site_id=Websites.id)
    GROUP BY Websites.name
    HAVING SUM(access_log.count)>200
