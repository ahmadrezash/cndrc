/****** Script for SelectTopNRows command from SSMS  ******/
SELECT Location
  FROM [ClaimData2_Report].[dbo].[FinalReport]
  group by Location
  order by Location

UPDATE [ClaimData2_Report].[dbo].[FinalReport]
SET [population] = 1167786
where Location = 'Charmahal' 

ALTER TABLE [ClaimData2_Report].[dbo].[FinalReport]
ADD population int; 
