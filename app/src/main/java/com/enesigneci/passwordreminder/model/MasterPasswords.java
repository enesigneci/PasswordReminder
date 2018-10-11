package com.enesigneci.passwordreminder.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "MasterPasswords")
public class MasterPasswords {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int masterPasswordId;
    private String masterPassword;

    public MasterPasswords() {
    }

    @NonNull
    public int getMasterPasswordId() {
        return masterPasswordId;
    }

    public void setMasterPasswordId(@NonNull int masterPasswordId) {
        this.masterPasswordId = masterPasswordId;
    }

    public String getMasterPassword() {
        return masterPassword;
    }

    public void setMasterPassword(String masterPassword) {
        this.masterPassword = masterPassword;
    }
}