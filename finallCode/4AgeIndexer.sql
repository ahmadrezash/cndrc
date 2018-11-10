ALTER TABLE [ClaimData10_Kordestan].[dbo].[SAR_NOSKHE]
ADD Age_index int;
 
update [ClaimData10_Kordestan].[dbo].[SAR_NOSKHE]
    set Age_index = 1
    where AGE>=0 and AGE <1

update [ClaimData10_Kordestan].[dbo].[SAR_NOSKHE]
    set Age_index = 2
    where AGE>=1 and AGE <5

update [ClaimData10_Kordestan].[dbo].[SAR_NOSKHE]
    set Age_index = 3
    where AGE>=5 and AGE <19

update [ClaimData10_Kordestan].[dbo].[SAR_NOSKHE]
    set Age_index = 4
    where AGE>=19 and AGE <65

update [ClaimData10_Kordestan].[dbo].[SAR_NOSKHE]
    set Age_index = 5
    where AGE>=65

update [ClaimData10_Kordestan].[dbo].[SAR_NOSKHE]
    set Age_index = 7
    where AGE=999