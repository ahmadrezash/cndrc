package com.company;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;

public class Main {

    static final String JDBC_ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";
    static final String ORACLE_DB_URL = "jdbc:oracle:thin:@//10.1.1.145:1521/ora" ;

    static final String JDBC_SQLSERVER_DRIVER = "macrosoft.jdbc.driver.sqlserver";
    static final String SQLSERVER_DB_URL1 = "jdbc:sqlserver://;databaseName=;" ;
    static final String SQLSERVER_DB_URL = "jdbc:sqlserver://;databaseName=;" ;


    static final String ORACLE_USER = "";
    static final String ORACLE_PASS = "";

    static final String SQLSERVER_USER = "";
    static final String SQLSERVER_PASS = "";

    public static void main(String[] args) {
        Connection conn_ORACLE = null;
        Statement stmt_ORACLE = null;

        Connection conn_SQLSERVER = null;
        Statement stmt_SQLSERVER = null;

        Connection conn_SQLSERVER1 = null;
        Statement stmt_SQLSERVER1 = null;



        ArrayList<String>drug_kindname = new ArrayList<String>();
        ArrayList<String>drug_code = new ArrayList<String>();
        ArrayList<String>drug_name = new ArrayList<String>();
        ArrayList<String>drug_kind = new ArrayList<String>();

        String province = "";
        try {
            //JDBC ORACLE
            Class.forName("oracle.jdbc.OracleDriver");

            System.out.println("Connecting to Oracle database...");
            conn_ORACLE = DriverManager.getConnection(ORACLE_DB_URL, ORACLE_USER, ORACLE_PASS);
            System.out.println("Connected database successfully...");

            System.out.println("Creating statement...");
            stmt_ORACLE = conn_ORACLE.createStatement();

            //JDBC SQL SERVER
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            System.out.println("Connecting to SqlServer database...");
            conn_SQLSERVER = DriverManager.getConnection(SQLSERVER_DB_URL, SQLSERVER_USER, SQLSERVER_PASS);
            conn_SQLSERVER1 = DriverManager.getConnection(SQLSERVER_DB_URL1, SQLSERVER_USER, SQLSERVER_PASS);
            System.out.println("Connected database successfully...");

            System.out.println("Creating statement...");
            stmt_SQLSERVER = conn_SQLSERVER.createStatement();
            stmt_SQLSERVER1 = conn_SQLSERVER1.createStatement();



            String sql = "SELECT DRUGSMASTER.INTERCODE,DRUGSMASTER.DRUGENGNAME ,DRUGKIND.DRUGKIND,  DRUGKIND.ENGDESCRIPTION FROM KERMANSHAH.DRUGSMASTER , KERMANSHAH.DRUGKIND "
                    + "WHERE DRUGSMASTER.INTERCODE IS NOT NULL AND DRUGSMASTER.DRUGKIND = DRUGKIND.DRUGKIND";
            ResultSet rs_oracle = stmt_ORACLE.executeQuery(sql);
            System.out.println(rs_oracle);


            while (rs_oracle.next()) {

                String Drug_name = rs_oracle.getString("DRUGENGNAME");
                String INTERCODE = rs_oracle.getString("INTERCODE");
                String Drug_kind = rs_oracle.getString("ENGDESCRIPTION");
                String Drug_kind_code = rs_oracle.getString("DRUGKIND");


                System.out.println(INTERCODE);
                System.out.println(Drug_name);
                System.out.println(Drug_kind);

                drug_code.add(INTERCODE);
                drug_name.add(Drug_name);
                drug_kindname.add(Drug_kind);
                drug_kind.add(Drug_kind_code);
            }
            int size = drug_code.size();

            String[]Year = new String[3];
            String[]Num = new String [3];

            for(int i=0;i<size;i=i+1) {
                if (i != 2099) {
                    for (int t =0;t<3;t = t+1){
                        Num[t]  = "";
                        Year[t] = "";
                    }
                    for (int y = 0; y < 11; y = y + 1) {
                        for( int j = 1;j <= 12;j=j+1){
                        int year = 1386 + y;
                        int month = j;

                        sql = "SELECT TOP 3 SUM([ClaimData].[dbo].[NOSKHE_ITEM].[TEDAD])AS NUM ,[ClaimData].[dbo].[SAR_NOSKHE].[DOCTOR_MAJOR]" +
                                "FROM [ClaimData].[dbo].[SAR_NOSKHE]" +
                                "INNER JOIN [ClaimData].[dbo].[NOSKHE_ITEM] ON SAR_NOSKHE.CODE = NOSKHE_ITEM.FK_SAR_NOS" +
                                " WHERE DRUG_CODE IS NOT NULL AND DRUG_CODE = '"
                                + drug_code.get(i)
                                + "' AND YEAR_NOS = "
                                + Integer.toString(year)
                                + " AND SEX = 1"
                                + " GROUP BY [ClaimData].[dbo].[SAR_NOSKHE].[DOCTOR_MAJOR]"
                                + " ORDER BY NUM DESC";


                        ResultSet rs_sqlserver = stmt_SQLSERVER.executeQuery(sql);
                        System.out.println(rs_sqlserver);
                        int counter = 0;
                        while (rs_sqlserver.next()) {
                            String name = rs_sqlserver.getString("DOCTOR_MAJOR");
                            String num = rs_sqlserver.getString("NUM");

                            Year[counter] = name;
                            Num[counter] = num;

                            counter = counter + 1;
                        }
                        if (counter == 1) {
                            sql = "UPDATE ClaimData2.dbo.REPORT set FirstSpeacialistName = '"
                                    + Year[0]
                                    + "',FirstSpeacialistNumber = '"
                                    + Num[0]
                                    + "' WHERE YEAR = "
                                    + year
                                    + " AND SERVICE_CODE = "
                                    + drug_code.get(i)
                                    + " AND SEX = 'Male'"
                                    + " AND AGE = 'All age'"
                                    + " AND LOC = 'Kermanshah'";
                            System.out.println(sql);
                            stmt_SQLSERVER1.executeUpdate(sql);
                        } else if (counter == 2) {
                            sql = "UPDATE ClaimData2.dbo.REPORT set FirstSpeacialistName = '"
                                    + Year[0]
                                    + "',FirstSpeacialistNumber = '"
                                    + Num[0]
                                    + "' WHERE YEAR = "
                                    + year
                                    + " AND SERVICE_CODE = "
                                    + drug_code.get(i)
                                    + " AND SEX = 'Male'"
                                    + " AND AGE = 'All age'"
                                    + " AND LOC = 'Kermanshah'";
                            System.out.println(sql);
                            stmt_SQLSERVER1.executeUpdate(sql);

                            sql = "UPDATE ClaimData2.dbo.REPORT set SecondSpeacialistName = '"
                                    + Year[1]
                                    + "',SecondSpeacialistNumber = '"
                                    + Num[1]
                                    + "' WHERE YEAR = "
                                    + year
                                    + " AND SERVICE_CODE = "
                                    + drug_code.get(i)
                                    + " AND SEX = 'Male'"
                                    + " AND AGE = 'All age'"
                                    + " AND LOC = 'Kermanshah'";
                            System.out.println(sql);
                            stmt_SQLSERVER1.executeUpdate(sql);
                        } else if (counter == 3) {
                            sql = "UPDATE ClaimData2.dbo.REPORT set FirstSpeacialistName = '"
                                    + Year[0]
                                    + "',FirstSpeacialistNumber = '"
                                    + Num[0]
                                    + "' WHERE YEAR = "
                                    + year
                                    + " AND SERVICE_CODE = "
                                    + drug_code.get(i)
                                    + " AND SEX = 'Male'"
                                    + " AND AGE = 'All age'"
                                    + " AND LOC = 'Kermanshah'";
                            System.out.println(sql);
                            stmt_SQLSERVER1.executeUpdate(sql);

                            sql = "UPDATE ClaimData2.dbo.REPORT set SecondSpeacialistName = '"
                                    + Year[1]
                                    + "',SecondSpeacialistNumber = '"
                                    + Num[1]
                                    + "' WHERE YEAR = "
                                    + year
                                    + " AND SERVICE_CODE = "
                                    + drug_code.get(i)
                                    + " AND SEX = 'Male'"
                                    + " AND AGE = 'All age'"
                                    + " AND LOC = 'Kermanshah'";
                            System.out.println(sql);
                            stmt_SQLSERVER1.executeUpdate(sql);

                            sql = "UPDATE ClaimData2.dbo.REPORT set ThirdSpeacialistName = '"
                                    + Year[2]
                                    + "',ThirdSpeacialistNumber = '"
                                    + Num[2]
                                    + "' WHERE YEAR = "
                                    + year
                                    + " AND SERVICE_CODE = "
                                    + drug_code.get(i)
                                    + " AND SEX = 'Male'"
                                    + " AND AGE = 'All age'"
                                    + " AND LOC = 'Kermanshah'";
                            stmt_SQLSERVER1.executeUpdate(sql);
                            System.out.println(sql);
                        }
                    }
                    }
                }
            }
        }
        catch(SQLException se)
        {
            //Handle errors for JDBC
            se.printStackTrace();
        }
        catch(Exception e)
        {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
        finally
        {
            //finally block used to close resources
            try{
                if(stmt_ORACLE!=null)
                {
                    conn_ORACLE.close();
                }
            }
            catch(SQLException se)
            {
                // do nothing
            }
            try
            {
                if(conn_ORACLE!=null)
                {
                    conn_ORACLE.close();
                }
            }
            catch(SQLException se)
            {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }
}