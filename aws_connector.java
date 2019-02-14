/*
 * Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 *  This file was modified by SeHoon Yang(sehoon.yang@e4net.net), under Apache2.0 License
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.*;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public class aws_connector
{
    BasicAWSCredentials credentials;
    AmazonS3 s3;

    /* Referenced by the test function */
    public static String bucketName = "bucket-test-yr";

    public aws_connector()
    {
        try
        {
            credentials = new BasicAWSCredentials("AKIAJHOUM7ZZV6KZ7ZGA", "+Nb5FpJlYRcmy9/KlcyVMk0Q38A2no81ILw0LHFm");
        }
        catch (Exception e)
        {
            throw new AmazonClientException("Key error", e);
        }
        AWSStaticCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);

        s3 = AmazonS3ClientBuilder.standard().withCredentials(credentialsProvider).withRegion("ap-northeast-2").build();
    }

    public boolean validate_user(String id, String pw) throws Exception
    {
        Class.forName("org.mariadb.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://yrdb.cyowmivkbanp.ap-northeast-2.rds.amazonaws.com:3306/yrdb", "yerim", "1994diq_");
        Statement stmt = connection.createStatement();
   
        String query = "select user_name from user where user_name = '"+id+"'"+"and user_pwd = "+"'"+pw+"'";
        
      
        	stmt = connection.createStatement();
        	ResultSet rs = stmt.executeQuery(query);
        	while(rs.next()) {
        		return true;}
        	
        connection.close();
        return false;
    }

    /*
     *  Reference function
     */
    private static File createSampleFile() throws IOException
    {
        File file = File.createTempFile("aws-java-sdk-", ".txt");
        file.deleteOnExit();

        Writer writer = new OutputStreamWriter(new FileOutputStream(file));
        writer.write("abcdefghijklmnopqrstuvwxyz\n");
        writer.write("01234567890112345678901234\n");
        writer.write("!@#$%^&*()-=[]{};':',.<>/?\n");
        writer.write("01234567890112345678901234\n");
        writer.write("abcdefghijklmnopqrstuvwxyz\n");
        writer.close();

        return file;
    }
}
