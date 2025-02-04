USE [citybank]
GO
/****** Object:  Table [dbo].[account]    Script Date: 12/9/2024 20:26:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[account](
	[id_acc] [varchar](255) NOT NULL,
	[account_number] [varchar](255) NOT NULL,
	[account_type] [varchar](255) NOT NULL,
	[initial_balance] [float] NOT NULL,
	[state] [bit] NOT NULL,
	[id_cus] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id_acc] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[account_movements]    Script Date: 12/9/2024 20:26:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[account_movements](
	[id_mvn] [varchar](255) NOT NULL,
	[balance] [float] NOT NULL,
	[date] [datetime2](6) NOT NULL,
	[movement_type] [varchar](255) NOT NULL,
	[value] [float] NOT NULL,
	[id_acc] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id_mvn] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[customer]    Script Date: 12/9/2024 20:26:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[customer](
	[id_cus] [varchar](255) NOT NULL,
	[email] [varchar](255) NOT NULL,
	[password] [varchar](255) NOT NULL,
	[state] [bit] NOT NULL,
	[id_per] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id_cus] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[person]    Script Date: 12/9/2024 20:26:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[person](
	[id_per] [varchar](255) NOT NULL,
	[address] [varchar](255) NOT NULL,
	[age] [int] NOT NULL,
	[gender] [varchar](255) NOT NULL,
	[identification] [varchar](255) NOT NULL,
	[name] [varchar](255) NOT NULL,
	[phone] [varchar](255) NOT NULL,
	[surname] [varchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id_per] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[account]  WITH CHECK ADD  CONSTRAINT [FK_ACCOUNT_CUSTOMER] FOREIGN KEY([id_cus])
REFERENCES [dbo].[customer] ([id_cus])
GO
ALTER TABLE [dbo].[account] CHECK CONSTRAINT [FK_ACCOUNT_CUSTOMER]
GO
ALTER TABLE [dbo].[account_movements]  WITH CHECK ADD  CONSTRAINT [FK_ACCOUNT_MOVEMENTS_ACCOUNT] FOREIGN KEY([id_acc])
REFERENCES [dbo].[account] ([id_acc])
GO
ALTER TABLE [dbo].[account_movements] CHECK CONSTRAINT [FK_ACCOUNT_MOVEMENTS_ACCOUNT]
GO
ALTER TABLE [dbo].[customer]  WITH CHECK ADD  CONSTRAINT [FK_CUSTOMER_PERSON] FOREIGN KEY([id_per])
REFERENCES [dbo].[person] ([id_per])
GO
ALTER TABLE [dbo].[customer] CHECK CONSTRAINT [FK_CUSTOMER_PERSON]
GO
