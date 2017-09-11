package com.example.dr0gi.task1users;

import java.util.Calendar;
import java.util.Date;

public class User {
    private String name;
    private String surname;
    private Date birthday;
    private int age;

    public User() {

    }

    public User(String name, String surname, Date birthday) {
        this.name = name;
        this.birthday = birthday;
        this.surname = surname;
        this.age = calculateAge(this.birthday);
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
