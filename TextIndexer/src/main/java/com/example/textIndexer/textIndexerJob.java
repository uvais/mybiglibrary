package com.example.textIndexer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;

import java.io.IOException;

/**
 * Created by qbuser on 4/4/15.
 */
public class textIndexerJob {

    public Job createJob(String hdfsLocation) throws IOException{
        Configuration conf = new Configuration();
        DBConfiguration.configureDB(conf,
                "com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost:3306/testDb",
                "root",
                "root");

        Job job = new Job(conf);
        job.setJarByClass(TextIndexer.class);
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);

        job.setOutputKeyClass(DBOutputWritable.class);
        job.setOutputValueClass(NullWritable.class);

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(DBOutputFormat.class);

        FileInputFormat.addInputPath(job, new Path(hdfsLocation));


        DBOutputFormat.setOutput(
                job,
                "lemon",
                new String[] { "file", "word","count" }
        );

        return job;
    }
}
