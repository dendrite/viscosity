package com.reversemind.fetcher;

import com.reversemind.Settings;
import com.reversemind.User;
import org.jsoup.*;

import java.io.IOException;
import java.io.Serializable;
import java.sql.*;
import java.sql.Connection;

/**
 * Date: 5/15/13
 * Time: 10:27 AM
 *
 * @author konilovsky
 * @since 1.0
 */
public class UpdateValue implements Serializable {


    public static String getStatus(String name){
        try{
            org.jsoup.Connection.Response response = Jsoup.connect("https://www.twitter.com/" + name)
                    .userAgent(HttpFetch.getAgent())
                    .timeout(5000)
                    .execute();
            System.out.println("CODE:" +response.statusCode() );
            if(response.statusCode() == 200){
                return "OK";
            }
        }catch(IOException ex){
            System.out.println("NOPE");
        }

        return "BAD";
    }

    public static void createOrUpdateStatus(Connection connection, Long id, String status, java.util.Date date) throws SQLException {
        String query = "SELECT * FROM tstatus WHERE id = "+id;
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet != null && resultSet.next()){
            // update
            preparedStatement.close();
            preparedStatement = null;
            preparedStatement = connection.prepareStatement("UPDATE tstatus SET status='"+status+"', updated='"+new Timestamp(date.getTime())+"' WHERE id = " + id);
            preparedStatement.execute();
            connection.commit();

        }else{
            // create
            preparedStatement.close();
            preparedStatement = null;
            preparedStatement = connection.prepareStatement("INSERT INTO tstatus(id, status, updated) VALUES ("+id+", '"+ status +"', '"+new Timestamp(date.getTime())+"');");
            preparedStatement.execute();
            connection.commit();
        }
    }

    public static void processPart(Connection connection, int startIndex, int count) throws SQLException {
        String query = "SELECT *  FROM tuser ORDER BY created_at OFFSET "+startIndex+" LIMIT "+count+";";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        //preparedStatement.setInt(1, 1001);
        ResultSet resultSet = preparedStatement.executeQuery();

        User user = null;
        while (resultSet.next()) {
            user = new User(resultSet.getLong("id"), resultSet.getString("screen_name"), resultSet.getTimestamp("created_at"),resultSet.getString("url"),resultSet.getInt("statuses"));
            if (user != null){
                System.out.println(user);
                String STATUS = getStatus(user.getScreenName());
                System.out.println(STATUS+"\n\n");
                createOrUpdateStatus(connection, user.getId(), STATUS, new java.util.Date());
                try {
                    Thread.sleep(Math.round(Math.random() * 5000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        if (preparedStatement != null) {
            preparedStatement.close();
        }
    }

    public static void main(String... args) throws SQLException {

        Connection connection = Settings.getDBConnection();
        connection.setAutoCommit(false);
//        createOrUpdateStatus(connection,1L,"GO", new java.util.Date());
//        createOrUpdateStatus(connection,1L,"GO2", new java.util.Date());
//
//
//        System.exit(0);


        System.setProperty("http.proxyHost", "10.105.0.217");
        System.setProperty("http.proxyPort", "3128");

        System.setProperty("https.proxyHost", "10.105.0.217");
        System.setProperty("https.proxyPort", "3128");


        int SIZE = 2267277;
        int STEP = 100;
        int MAX = SIZE / STEP;
        int DELTA = SIZE - (MAX*STEP);

        System.out.println("SIZE:" + SIZE);
        System.out.println("STEP:" + STEP);
        System.out.println("MAX:" + MAX);
        System.out.println("DELTA:" + DELTA);

        if(connection != null){

            for(int i=0; i<MAX; i++){
                processPart(connection,i*STEP,STEP);
            }

            if(DELTA >0){
                processPart(connection,MAX*STEP,STEP);
            }

        }

        if (connection != null) {
            connection.close();
        }

    }

}
