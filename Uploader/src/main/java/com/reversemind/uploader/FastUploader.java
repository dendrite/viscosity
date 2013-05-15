package com.reversemind.uploader;

import com.reversemind.User;

import java.io.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Date: 5/15/13
 * Time: 8:41 AM
 *
 * @author konilovsky
 * @since 1.0
 */
public class FastUploader implements Serializable {

    public static SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZ yyyy", Locale.US);

    private static final int STEP   = 10000;

    private static final String JDBC_HOST   = "localhost";
    private static final int    JDBC_PORT   = 5432;
    private static final String JDBC_DB     = "twitter";

    private static final String DB_DRIVER       = "org.postgresql.Driver";
    private static final String DB_CONNECTION   = "jdbc:postgresql://" + JDBC_HOST + ":" + JDBC_PORT + "/" + JDBC_DB;
    private static final String DB_USER         = "test";
    private static final String DB_PASSWORD     = "test";

    static String query = "INSERT INTO tuser(id, screen_name, created_at, url, statuses) VALUES(?,?,?,?,?);";

    private static Connection getDBConnection() {
        Connection dbConnection = null;

        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dbConnection;
    }

    public static PreparedStatement createBatch(Connection connection, List<User> list) throws SQLException {

        if(list != null && list.size()>0){
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            for(User user: list){
                preparedStatement.setLong(1, user.getId());
                preparedStatement.setString(2, user.getScreenName());

                if(user.getCreatedAt() != null){
                    preparedStatement.setTimestamp(3, new Timestamp(user.getCreatedAt().getTime()));
                }else{
                    preparedStatement.setDate(3, null);
                }

                if(user.getURL() != null){
                    preparedStatement.setString(4, user.getURL());
                }else{
                    preparedStatement.setString(4, null);
                }
                preparedStatement.setInt(5, user.getStatuses());

                preparedStatement.addBatch();
            }

            return preparedStatement;
        }

        return null;

    }

    public static void insert(Connection connection, List<User> users){
        if(connection != null){

            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = createBatch(connection, users);

                if(preparedStatement != null){
                    preparedStatement.executeBatch();
                    connection.commit();
                    preparedStatement.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();

                //--------------------------------------------------------
                if(preparedStatement != null){
                    try {
                        preparedStatement.close();
                        preparedStatement = null;
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }

                try {
                    preparedStatement = connection.prepareStatement(query);
                    // make it step-by-step
                    if(users != null && users.size()>0){
                        for(User user: users){


                            try {
                                preparedStatement.setLong(1, user.getId());
                                preparedStatement.setString(2, user.getScreenName());

                                if(user.getCreatedAt() != null){
                                    preparedStatement.setTimestamp(3, new Timestamp(user.getCreatedAt().getTime()));
                                }else{
                                    preparedStatement.setDate(3, null);
                                }

                                if(user.getURL() != null){
                                    preparedStatement.setString(4, user.getURL());
                                }else{
                                    preparedStatement.setString(4, null);
                                }
                                preparedStatement.setInt(5, user.getStatuses());
                                preparedStatement.execute();
                                connection.commit();
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }

                        }
                    }

                    if(preparedStatement != null){
                        preparedStatement.close();
                    }

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

            }


        }
    }

    public static User parse(String tmpString) throws ParseException {
        String[] str = tmpString.split("\\|");

        if (str.length >= 6) {
//            for (int i = 0; i < str.length; i++) {
//                System.out.println(str[i]);
//            }

            User user = null;
            try{
                user = new User();
                user.setId(new Long(str[1].trim()));
                user.setScreenName(str[2].trim());
                java.util.Date date = sdf.parse(str[3].trim());
                user.setCreatedAt(date);

                String url = str[4].trim();
                if(url != null && url.length() >0){
                    user.setURL(url);
                }else{
                    user.setURL("");
                }

                user.setStatuses(new Integer(str[5].trim()));

            }catch(Exception ex){
                ex.printStackTrace();
            }

            return user;
        }

        return null;
    }

    public static void main(String... args) throws IOException {


        String fileName = "/opt/DATA/upload/NAMES.txt";
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(fileName)));

        Connection connection = getDBConnection();
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        long counter = 0;
        List<User> users = new ArrayList<User>();

        String string = "";
        while((string = bufferedReader.readLine()) != null){

            User user = null;
            try {
                user = parse(string);
            } catch (ParseException e) {
                e.printStackTrace();
                System.out.println("Could not to parse:" + string);
            }

            if (user != null){
                counter++;
                users.add(user);
            }

            if (counter % STEP == 0){
                System.out.println(user);
                insert(connection,users);
                users = new ArrayList<User>();
            }

        }

        if(users != null && users.size() >0){
            insert(connection,users);
        }

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
