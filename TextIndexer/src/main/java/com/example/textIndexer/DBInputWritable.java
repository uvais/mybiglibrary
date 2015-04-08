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
public class DBInputWritable implements Writable, DBWritable
{
    private int id;
    private String name;

    public void readFields(DataInput in) throws IOException {   }

    public void readFields(ResultSet rs) throws SQLException
    {
        id = rs.getInt(1);
        name = rs.getString(2);
    }

    public void write(DataOutput out) throws IOException {  }

    public void write(PreparedStatement ps) throws SQLException
    {
        ps.setInt(1, id);
        ps.setString(2, name);
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }
}