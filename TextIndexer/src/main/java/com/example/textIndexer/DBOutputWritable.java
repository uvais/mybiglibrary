package com.example.textIndexer;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by jithinoc on 15/3/15.
 */

public class DBOutputWritable implements Writable, DBWritable
{
    private String file;
    private String word;
    private int count;

    public DBOutputWritable(String file, String word, int count)
    {
        this.file = file;
        this.word = word;
        this.count = count;
    }

    public void readFields(DataInput in) throws IOException {   }

    public void readFields(ResultSet rs) throws SQLException
    {
        file = rs.getString(1);
        word = rs.getString(2);
        count = rs.getInt(3);
    }

    public void write(DataOutput out) throws IOException {    }

    public void write(PreparedStatement ps) throws SQLException
    {
        ps.setString(1, file);
        ps.setString(2, word);
        ps.setInt(3, count);
    }
}