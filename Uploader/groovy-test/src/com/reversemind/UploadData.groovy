package com.reversemind

import groovy.sql.Sql

import java.text.ParseException
import java.text.SimpleDateFormat;

/**
 *
 * Date: 5/14/13
 * Time: 4:44 PM
 *
 * @author konilovsky
 * @since 1.0
 */
class UploadData {

    public static SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZ yyyy");

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
                Date date = sdf.parse(str[3].trim());
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

    public static void main(String... args){

        def sqlSource = Sql.newInstance(
                "jdbc:postgresql://$PostgresSettings.HOST_PORT/$PostgresSettings.DB_NAME_SOURCE",
                PostgresSettings.LOGIN,
                PostgresSettings.PASSWORD,
                'org.postgresql.Driver' )


        // INSERT INTO tuser(id, screen_name, created_at, url, statuses) VALUES (?, ?, ?, ?, ?);


        String fileName = "/opt/DATA/upload/NAMES.txt";
        BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));

        String string = "";
        int counter = 0;
        while((string = br.readLine()) != null){
            User user = parse(string);
            if (counter++ % 1000 == 0){
                println user;
            }

            if (user != null){

                StringBuilder stringBuilder = new StringBuilder();

                stringBuilder.append("INSERT INTO tuser(id, screen_name, created_at, url, statuses) VALUES(");
                stringBuilder.append(user.getId()).append(", ")
                stringBuilder.append("'" + user.getScreenName() + "'").append(", ")
//                stringBuilder.append("'" + user.getCreatedAt() + "'").append(", ")
                stringBuilder.append("'" + user.getCreatedAt() + "'").append(", ")


                if (user.getURL() != null && user.getURL().length()>0){
                    stringBuilder.append("'"+user.getURL()+"'").append(", ")
                }else{
                    stringBuilder.append(null).append(", ")
                }

                stringBuilder.append(user.getStatuses())
                stringBuilder.append(");")

                sqlSource.execute(stringBuilder.toString());
            }

            sqlSource.execute("commit;");

        }

    }// ----

}

/*
-- Table: tuser

-- DROP TABLE tuser;

CREATE TABLE tuser
(
  id bigint NOT NULL,
  screen_name character varying(128),
  created_at timestamp without time zone,
  url character varying(1024),
  statuses integer,
  CONSTRAINT pk PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE tuser
  OWNER TO test;

 */
