package com.example.dr0gi.task1users;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        for (int i = 0; i < COUNT_CLONE; i++) {
            if(i % 2 == 0) {
                mUserList.add(new User("John", "Smith " + i, new Date(90, 1, 12)));
            }
            else {
                mUserList.add(new User("Ivan", "Ivanov " + i, new Date(100, 5, 1)));
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
