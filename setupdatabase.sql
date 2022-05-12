
CREATE DATABASE MoviesDatabase
GO

CREATE LOGIN FlywayUser WITH PASSWORD = 'abc123'
GO
CREATE LOGIN ApiUser WITH PASSWORD = 'abc12345'
GO
use MoviesDatabase
GO
CREATE USER [FlywayUser] FOR LOGIN [FlywayUser]
EXEC sp_addrolemember N'db_owner', N'FlywayUser'
GO
CREATE USER [ApiUser] FOR LOGIN [ApiUser]
EXEC sp_addrolemember N'db_datawriter', N'ApiUser'
EXEC sp_addrolemember N'db_datareader', N'ApiUser'
GO
