ALTER TABLE [ClaimData12_Charmahal].[dbo].[SAR_NOSKHE]
ADD BIMEH_ID VARCHAR(255);

ALTER TABLE ClaimData12_Charmahal.dbo.SAR_NOSKHE
ADD YEAR_NOS INT;

ALTER TABLE ClaimData12_Charmahal.dbo.SAR_NOSKHE
ADD YEAR_BIRTH INT ;

ALTER TABLE Bushehr.[BUSHEHR].SAR_NOSKHE
ADD DOCTOR_MAJOR NVARCHAR(255); 

UPDATE [ClaimData12_Charmahal].[dbo].[SAR_NOSKHE] SET
    BIMEH_ID = CASE 
        WHEN [ClaimData12_Charmahal].[dbo].[SAR_NOSKHE].CODE_BIM NOT LIKE '%-%'  -- If the literal does not contain a hyphen
            THEN STUFF(
                [ClaimData12_Charmahal].[dbo].[SAR_NOSKHE].CODE_BIM,             -- Put in the literal
                LEN([ClaimData12_Charmahal].[dbo].[SAR_NOSKHE].CODE_BIM),        -- Just before the last character
                0,                          -- Replacing 0 characters ("stuffing" in between)
                '-')                        -- Placing a hypen
        ELSE
            [ClaimData12_Charmahal].[dbo].[SAR_NOSKHE].CODE_BIM END

UPDATE [ClaimData12_Charmahal].[dbo].[SAR_NOSKHE] SET AGE = 999

UPDATE [ClaimData12_Charmahal].[dbo].[SAR_NOSKHE] SET SEX = 3

Update ClaimData8_Lorestan.dbo.SAR_NOSKHE
Set SEX = t2.sex
From ClaimData8_Lorestan.dbo.SAR_NOSKHE t1 
Inner Join ClaimData.dbo.bimehshode as t2
    On t1.BIMEH_ID = t2.BIMEH_ID

UPDATE [ClaimData12_Charmahal].[dbo].[SAR_NOSKHE] SET YEAR_NOS = CAST(LEFT(DATE_NOS, 4) AS INT)
WHERE LEN(DATE_NOS) = '8'

UPDATE [ClaimData12_Charmahal].[dbo].[SAR_NOSKHE] SET [YEAR_BIRTH] = CAST(LEFT(t2.[birthdate] , 4) AS INT)
FROM [ClaimData12_Charmahal].[dbo].[SAR_NOSKHE] t1
INNER JOIN [ClaimData].[dbo].[bimehshode] AS t2
on  t1.[BIMEH_ID] = t2.[BIMEH_ID]
WHERE LEN(t2.[birthdate]) = '8'

UPDATE [ClaimData12_Charmahal].[dbo].[SAR_NOSKHE] SET AGE = YEAR_NOS - YEAR_BIRTH 
WHERE (YEAR_NOS - YEAR_BIRTH)>= 0 AND (YEAR_NOS - YEAR_BIRTH)<= 150

update t1
set t1.[DOCTOR_MAJOR] = t2.[DOCTOR_MAJOR]
from [Bushehr].[BUSHEHR].[SAR_NOSKHE] t1
INNER join [ClaimData].[dbo].[DOCTOR] t2 
on (t1.NEZAM = t2.NEZAM)