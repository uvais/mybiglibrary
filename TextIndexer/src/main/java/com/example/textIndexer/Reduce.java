package com.example.textIndexer;



import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;



import java.io.IOException;

/**
 * Created by jithinoc on 14/3/15.
 */
public class Reduce extends Reducer<Text, LongWritable, DBOutputWritable, NullWritable>
{
    protected void reduce(Text key, Iterable<LongWritable> values, Context context)
    {

        String[] tokens = (key.toString()).split("####");
        String filename = tokens[0];
        String word;
        if(tokens.length!=2)
            word = key.toString();
        else
            word = tokens[1];
        int sum = 0;

        for(LongWritable value : values)
        {
            sum += value.get();
        }

        try
        {
            context.write(new DBOutputWritable(filename, word, sum), NullWritable.get());
        } catch(IOException e)
        {
            e.printStackTrace();
        } catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}