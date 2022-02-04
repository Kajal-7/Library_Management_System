   CREATE TABLE books(
  book_id INT NOT NULL ,
  book_name VARCHAR(100),
  book_author VARCHAR(100),
  issue_status INT,
  issuedby VARCHAR(100),
  PRIMARY KEY (book_id)
  );
    Create table issuelist(
       id INT NOT NULL auto_increment,
       student_id VARCHAR(100),
       book_id INT,
       issue_date date,
       fine int,
       PRIMARY KEY (id)
	);
    Create Table students(
   student_id VARCHAR(100) NOT NULL,
   student_name VARCHAR(100),
   countOfBooks INT,
   PRIMARY KEY (student_id)
   );
 Select * from books;
  Select * from students;
  Select * from issuelist;
