package com.example.dr0gi.task1users;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


// Class for keeping and create data (List Users)
public class CloneFactory {
    private static CloneFactory sCloneFactory;
    private static List<User> mUserList;
    private final int COUNT_CLONE = 10;

    public static class User {
        private String name;
        private String surname;
        private Date birthday;

        public User() {
        }

        public User(String name, String surname, Date birthday) {
            this.name = name;
            this.birthday = birthday;
            this.surname = surname;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public Date getBirthday() {
            return birthday;
        }

        public void setBirthday(Date birthday) {
            this.birthday = birthday;
        }
    }

    private CloneFactory() {
        mUserList = new ArrayList<>(COUNT_CLONE);
        SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        Date birthday = new Date();
        for (int i = 0; i < COUNT_CLONE; i++) {
            if(i % 2 == 0) {
                try {
                    birthday = dt.parse("05/04/1990");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                mUserList.add(new User("John", "Smith " + i, birthday));
            }
            else {
                try {
                    birthday = dt.parse("05/04/2000");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                mUserList.add(new User("Ivan", "Ivanov " + i, birthday));
            }
        }

    }

    public static List<User> getCloneList() {
        if(sCloneFactory == null){
            sCloneFactory = new CloneFactory();
        }
        return mUserList;
    }
}
