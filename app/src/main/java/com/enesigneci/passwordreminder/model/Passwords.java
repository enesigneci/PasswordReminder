package com.enesigneci.passwordreminder.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "Passwords")
public class Passwords {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int passwordId;
    private String passwordUsername;
    private String passwordName;
    private String passwordText;
    private String passwordCategory;
    private int passwordType;

    @Ignore
    public Passwords(String passwordUsername, String passwordName, String passwordText, String passwordCategory, int passwordType) {
        this.passwordUsername = passwordUsername;
        this.passwordName = passwordName;
        this.passwordText = passwordText;
        this.passwordCategory = passwordCategory;
        this.passwordType = passwordType;
    }

    public Passwords() {
    }

    public int getPasswordId() { return passwordId; }
    public void setPasswordId(int passwordId) { this.passwordId = passwordId; }

    public String getPasswordUsername() {
        return passwordUsername;
    }

    public void setPasswordUsername(String passwordUsername) {
        this.passwordUsername = passwordUsername;
    }

    public String getPasswordName() {
        return passwordName;
    }

    public void setPasswordName(String passwordName) {
        this.passwordName = passwordName;
    }

    public String getPasswordText() {
        return passwordText;
    }

    public void setPasswordText(String passwordText) {
        this.passwordText = passwordText;
    }

    public String getPasswordCategory() {
        return passwordCategory;
    }

    public void setPasswordCategory(String passwordCategory) {
        this.passwordCategory = passwordCategory;
    }

    public int getPasswordType() {
        return passwordType;
    }

    public void setPasswordType(int passwordType) {
        this.passwordType = passwordType;
    }
}