package com.example.dr0gi.task1users;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class User implements Serializable {
    private long id;
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

    public User(long id, String name, String surname, Date birthday) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.surname = surname;
        this.age = calculateAge(this.birthday);
    }

    public User(long id, String name, String surname, String birthdayStr) {
        this.id = id;
        this.name = name;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        try {
            this.birthday = dateFormat.parse(birthdayStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
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


    public long getID() {
        return id;
    }
    public void setID(long id) {
         this.id = id;
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
        age = calculateAge(birthday);
    }

    public String getBirthdayStr() {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        return df.format(birthday);
    }
    public void setBirthdayStr(String birthdayStr) {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        try {
            birthday = dateFormat.parse(birthdayStr);
            age = calculateAge(birthday);
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
