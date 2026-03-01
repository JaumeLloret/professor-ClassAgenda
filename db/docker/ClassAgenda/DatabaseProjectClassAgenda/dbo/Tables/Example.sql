CREATE TABLE [dbo].[Example] (
    [id]         INT            IDENTITY (1, 1) NOT NULL,
    [message]    NVARCHAR (255) NOT NULL,
    [created_at] DATETIME2 (7)  DEFAULT (sysdatetime()) NULL,
    PRIMARY KEY CLUSTERED ([id] ASC)
);


GO

