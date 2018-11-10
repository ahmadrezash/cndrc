/****** Script for SelectTopNRows command from SSMS  ******/
ALTER TABLE [TestReport].[dbo].[FinalReportIlam]
ADD Location NVARCHAR(255);

update[TestReport].[dbo].[FinalReportIlam]
set Location = 'Ilam'

update [TestReport].[dbo].[FinalReportIlam]
set Gender = 'Both' where Gender is null

update [TestReport].[dbo].[FinalReportIlam]
set Gender = 'Male' where Gender = '1'

update [TestReport].[dbo].[FinalReportIlam]
set Gender = 'Female' where Gender = '0'

update [TestReport].[dbo].[FinalReportIlam]
set Gender = 'Unknown' where Gender = '3'

update [TestReport].[dbo].[FinalReportIlam]
set AgeIndex = 6 where AgeIndex is null

update [TestReport].[dbo].[FinalReportIlam]
set Year = 'All year' where Year is null

update [TestReport].[dbo].[FinalReportIlam]
set Month = 'All Month' where Month is null