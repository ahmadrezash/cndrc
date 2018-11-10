

------Creat Report Table-----

CREATE TABLE TestReport.dbo.FinalReportKordestan(
    Number     bigint,
    TotalCost  bigint ,
    DrugCode   varchar(255),
    Gender     varchar(25),
    AgeIndex   int,
    Year       varchar(25),
	Month      varchar(25)
);

------Fill Report Table-----

insert INTO TestReport.dbo.FinalReportKordestan(
    Number,
    TotalCost,
    DrugCode,
    Gender,
    AgeIndex,
    Year,
    Month)
select
    sum(t1.[TEDAD]),
    sum(t1.[TOTAL]) ,
    t1.[DRUG_CODE],
    cast(t2.[SEX] as varchar) ,
    t2.[Age_index] ,
    Cast(t2.YEAR_NOS as varchar) ,
    Cast(t2.MONTH_NOS as varchar)
FROM
    [ClaimData10_Kordestan].[dbo].[NOSKHE_ITEM]  t1
    INNER JOIN
        [ClaimData10_Kordestan].[dbo].[SAR_NOSKHE] t2
        ON t2.[CODE] = t1.[FK_SAR_NOS]
    WHERE [DRUG_CODE] IS NOT NULL and 
            YEAR_NOS>=1386 and
            YEAR_NOS<=1396

GROUP BY CUBE (t2.[SEX] ,
                t2.[Age_index],
                t1.[DRUG_CODE],
                t2.YEAR_NOS,
                t2.MONTH_NOS)