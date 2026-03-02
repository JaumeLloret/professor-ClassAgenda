DROP TABLE Example;

USE ClassAgenda;
GO

CREATE TABLE Example (
    id INT IDENTITY(1,1) PRIMARY KEY,
    message NVARCHAR(255) NOT NULL,
    created_at DATETIME2 DEFAULT SYSDATETIME()
);
GO

USE ClassAgenda;
GO

CREATE TABLE USERS (
    id INT IDENTITY(1,1) PRIMARY KEY,
    name VARCHAR(80) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    created_at DATETIME NOT NULL
);
GO

USE ClassAgenda;

SELECT* FROM USERS;

GO

USE ClassAgenda;
CREATE TABLE TASKS (
    id INT IDENTITY(1,1) PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    status VARCHAR(20) NOT NULL,
    priority VARCHAR(20) NOT NULL,
    owner_id INT NOT NULL,
    created_at DATETIME NOT NULL,
    
    -- Esta es la Clave Foránea que une la tarea con su dueño
    CONSTRAINT FK_Task_User FOREIGN KEY (owner_id) REFERENCES USERS(id)
);
GO