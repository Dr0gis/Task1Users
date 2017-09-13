package com.example.dr0gi.task1users;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class User implements Serializable {
    private int id;
    private String name;
    private String surname;
    private Date birthday;
    private int age;

    public User() {
        this.id = -1;
        this.name = "Name";
        this.surname = "Surname";
        this.birthday = new Date();
        this.age = calculateAge(this.birthday);
    }

    public User(int id, String name, String surname, Date birthday) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.surname = surname;
        this.age = calculateAge(this.birthday);
    }

    public User(User user) {
        this.id = user.id;
        this.name = user.name;
        this.birthday = user.birthday;
        this.surname = user.surname;
        this.age = calculateAge(this.birthday);
    }

    public  int getID() {
        return id;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setUser(User user) {
        this.id = user.id;
        this.name = user.name;
        this.birthday = user.birthday;
        this.surname = user.surname;
        this.age = calculateAge(this.birthday);
    }

    // Calculate age by birthday
    private Integer calculateAge(final Date birthday) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.setTime(birthday);
        dob.add(Calendar.DAY_OF_MONTH, -1);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) <= dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        return age;
    }

}
