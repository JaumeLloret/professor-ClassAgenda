CREATE TABLE [dbo].[USERS] (
    [id]         INT           IDENTITY (1, 1) NOT NULL,
    [name]       VARCHAR (80)  NOT NULL,
    [email]      VARCHAR (255) NOT NULL,
    [created_at] DATETIME      NOT NULL,
    PRIMARY KEY CLUSTERED ([id] ASC),
    UNIQUE NONCLUSTERED ([email] ASC)
);


GO

