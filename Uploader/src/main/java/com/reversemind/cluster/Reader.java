package com.reversemind.cluster;

import com.reversemind.Settings;
import com.reversemind.User;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Date: 5/16/13
 * Time: 10:59 AM
 *
 * @author konilovsky
 * @since 1.0
 */
public class Reader implements Serializable {

    public static List<Values> go(Connection connection) throws SQLException {

        List<Values> list = new ArrayList<Values>();

        String query = "SELECT * FROM tmetrics;";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {

            long id = resultSet.getLong("id");

            long updates = resultSet.getLong("updates");
            long links = resultSet.getLong("links");
            long replies = resultSet.getLong("replies");
            long retweets = resultSet.getLong("retweets");

            long total = updates + links + replies + retweets;

            Date startDate = resultSet.getDate("stop_date");
            Date stopDate = resultSet.getDate("start_date");

            long delta = stopDate.getTime() - startDate.getTime();


            Values values = new Values(total, updates, links, replies, retweets, startDate, stopDate, delta, id);
            list.add(values);
        }

        return list;
    }

    /**
     *
     * @param args
     */
    public static void main(String... args) throws Exception {

        Connection connection = Settings.getDBConnection();
        connection.setAutoCommit(false);


        List<Values> list = go(connection);


        System.out.println(list.size());

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("/matrix")));
        for(Values values: list){
            bufferedWriter.write(values.print2() + "\n");
        }

        bufferedWriter.flush();
        bufferedWriter.close();
    }

}
