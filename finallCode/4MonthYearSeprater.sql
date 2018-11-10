
----Add Year & Month Column To The Table ------
ALTER TABLE 
    [ClaimData7_Hamedan].[dbo].[SAR_NOSKHE]
    ADD
        MONTH_NOS INT
        YEAR_NOS  INT

----Fill Month Column in Table ------
UPDATE 
    [ClaimData7_Hamedan].[dbo].[SAR_NOSKHE] 
    SET 
        MONTH_NOS = CAST(SUBSTRING(cast(DATE_NOS as nvarchar),5,2) AS INT)
WHERE 
    LEN(DATE_NOS) = '8'


----Fill Year Column in Table ------
UPDATE
    [ClaimData7_Hamedan].[dbo].[SAR_NOSKHE] 
    SET 
        YEAR_NOS = CAST(SUBSTRING(cast(DATE_NOS as nvarchar),1,4) AS INT)
WHERE LEN(DATE_NOS) = '8'