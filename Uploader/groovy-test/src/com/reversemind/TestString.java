package com.reversemind;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date: 5/14/13
 * Time: 4:52 PM
 *
 * @author konilovsky
 * @since 1.0
 */
public class TestString implements Serializable {

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

    public static void main(String... args) throws ParseException {
    /*
        50263|200520475|zuluzka1975|Sat Oct 09 18:30:47 MSD 2010||0
        50264|532794591|bakatnuk|Thu Mar 22 04:11:54 MSK 2012||0
        50265|111893900|Anastas16|Sat Feb 06 17:12:33 MSK 2010||285
        50266|232267373|BarbaraKaulitz4|Thu Dec 30 20:51:11 MSK 2010||129
        50267|448167901|BobiKinta|Tue Dec 27 21:01:39 MSK 2011||1
        50268|11403012|millosh|Fri Dec 21 15:58:03 MSK 2007||432
        50269|187984093|Nadya_pavlova|Tue Sep 07 21:06:10 MSD 2010|http://polymedia.ru|155
        50270|477718116|StasV12|Sun Jan 29 16:56:42 MSK 2012||1
        50271|155518191|RusGuru|Mon Jun 14 14:39:02 MSD 2010|http://www.rusgu.ru/|1270
        50272|66161947|sashasoshnikov|Sun Aug 16 22:19:07 MSD 2009|http://vkontakte.ru/sashasoshnikov|52

     */

        String[] strings = {
                "50263|200520475|zuluzka1975|Sat Oct 09 18:30:47 MSD 2010||0",
                "50264|532794591|bakatnuk|Thu Mar 22 04:11:54 MSK 2012||0",
                "50265|111893900|Anastas16|Sat Feb 06 17:12:33 MSK 2010||285",
                "50266|232267373|BarbaraKaulitz4|Thu Dec 30 20:51:11 MSK 2010||129",
                "50267|448167901|BobiKinta|Tue Dec 27 21:01:39 MSK 2011||1",
                "50268|11403012|millosh|Fri Dec 21 15:58:03 MSK 2007||432",
                "50269|187984093|Nadya_pavlova|Tue Sep 07 21:06:10 MSD 2010|http://polymedia.ru|155",
                "50270|477718116|StasV12|Sun Jan 29 16:56:42 MSK 2012||1",
                "50271|155518191|RusGuru|Mon Jun 14 14:39:02 MSD 2010|http://www.rusgu.ru/|1270",
                "50272|66161947|sashasoshnikov|Sun Aug 16 22:19:07 MSD 2009|http://vkontakte.ru/sashasoshnikov|52"
        };


        for (int i = 0; i < strings.length; i++) {
            System.out.println(strings[i]);
        }


        String tmp = strings[0];


        String date = "Fri Dec 21 15:58:03 MSK 2007";
        String date2 = "Sun Aug 16 22:19:07 MSD 2009";

        System.out.println(sdf.parse(date2));


        System.out.println("\n\n\n");



        for (int i = 0; i < strings.length; i++) {
            System.out.println(parse(strings[i]));
        }


    }

}
