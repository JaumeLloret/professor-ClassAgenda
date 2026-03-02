CREATE TABLE [dbo].[TASKS] (
    [id]          INT           IDENTITY (1, 1) NOT NULL,
    [title]       VARCHAR (100) NOT NULL,
    [description] TEXT          NULL,
    [status]      VARCHAR (20)  NOT NULL,
    [priority]    VARCHAR (20)  NOT NULL,
    [owner_id]    INT           NOT NULL,
    [created_at]  DATETIME      NOT NULL,
    PRIMARY KEY CLUSTERED ([id] ASC)
);
GO

ALTER TABLE [dbo].[TASKS]
    ADD CONSTRAINT [FK_Task_User] FOREIGN KEY ([owner_id]) REFERENCES [dbo].[USERS] ([id]);
GO

