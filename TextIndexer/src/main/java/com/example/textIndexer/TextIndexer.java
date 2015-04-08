package com.example.textIndexer;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import org.apache.hadoop.mapreduce.Job;
import java.io.IOException;
import java.sql.*;

/**
 * Created by jithinoc on 14/3/15.
 */

public class TextIndexer {

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException{
        textIndexerJob textJob = new textIndexerJob();
        Job job = textJob.createJob(args[0]);
        job.waitForCompletion(true);

        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        String url = "jdbc:mysql://localhost:3306/testDb";
        String user = "root";
        String password = "root";

        String file;
        String word;
        int count;

        MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
        DB db = mongoClient.getDB( "lemon" );
        DBCollection coll = db.getCollection("wordcount");

        try {
            con = DriverManager.getConnection(url, user, password);
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM lemon");


            while (rs.next()) {
                file = rs.getString("file");
                word = rs.getString("word");
                count = rs.getInt("count");

                BasicDBObject doc = new BasicDBObject("name", "MongoDB");
                doc.put("file", file);
                doc.put("word", word);
                doc.put("count", count);

                coll.insert(doc);
            }
            con.close();

        } catch (SQLException ex) {

        }



    }
}
