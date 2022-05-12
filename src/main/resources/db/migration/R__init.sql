DROP TABLE IF EXISTS MOVIE;
DROP TABLE IF EXISTS Director;

CREATE TABLE Director(
DirectorId int IDENTITY(1,1),
CONSTRAINT PK_DIRECTOR PRIMARY KEY (DirectorId),
Name VARCHAR(50) NOT NULL,
BirthYear int NOT NUll
)
go
CREATE TABLE Movie(
MovieId int IDENTITY(1,1),
CONSTRAINT PK_MOVIE PRIMARY KEY (MovieId),
Title NVARCHAR(80) NOT NULL,
DirectorId int NOT NULL,
ReleaseYear int NOT NULL,
CONSTRAINT FK_Movie_DirectorId FOREIGN KEY (DirectorId) REFERENCES Director(DirectorId)
)
go
  insert into Director
  values('Martin Scorsese',1954), ('Martin Jones',1958)

  insert into movie values('The Departed', 1,2010),('Goodfellas', 1,1980),('Fake movie',2,1911)

