package com.example.textIndexer;


import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

/**
 * Created by jithinoc on 14/3/15.
 */
public class Map extends Mapper<LongWritable, Text, Text, LongWritable> {
    private final static LongWritable one = new LongWritable(1);
    private Text word = new Text();

    public void map(LongWritable key, Text value, Context context) throws InterruptedException, IOException {
        String line = value.toString();
        String[] words = line.split(" ");
        String fileName = ((FileSplit) context.getInputSplit()).getPath().getName();
        for (String s : words) {
            s = s.replaceAll("^[^a-zA-Z0-9\\s]+|[^a-zA-Z0-9\\s]+$", "");
            word.set(fileName+"####"+s.toLowerCase());
            context.write(word, one);
        }

    }

}