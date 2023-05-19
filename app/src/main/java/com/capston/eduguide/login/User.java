package com.capston.eduguide.login;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class User {
    private String id;
    private String password;
    private String email;
    private String phone;
    private String name;
    private String age;

    public User(String id, String password, String email, String phone, String name, String age) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.name = name;
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }


    public void saveToFirebase() {
        // Get the Firebase database reference
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();

        // Save the user object to the "users" collection in Firebase
        databaseRef.child("users").child(id).setValue(this);
    }
}

