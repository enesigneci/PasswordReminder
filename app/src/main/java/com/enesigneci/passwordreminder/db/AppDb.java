package com.enesigneci.passwordreminder.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.enesigneci.passwordreminder.model.MasterPasswords;
import com.enesigneci.passwordreminder.model.Passwords;

@Database(entities = {MasterPasswords.class, Passwords.class}, version = 7, exportSchema = false)
public abstract class AppDb extends RoomDatabase {
    public abstract MasterPasswordDao masterPasswordsDao() ;
    public abstract PasswordsDao passwordsDao();
}