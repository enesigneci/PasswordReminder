package com.enesigneci.passwordreminder.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.enesigneci.passwordreminder.model.Passwords;

import java.util.List;

@Dao
public interface PasswordsDao {

    @Insert
    void insertOnlySinglePassword (Passwords passwords);
    @Insert
    void insertMultiplePasswords (List<Passwords> passwordsList);
    @Query("SELECT * FROM Passwords")
    List<Passwords> getAllPasswords ();
    @Query("SELECT * FROM Passwords WHERE passwordCategory = :passwordCategory")
    List<Passwords> getByCategory (String passwordCategory);
    @Query("SELECT * FROM Passwords WHERE passwordType = :passwordType")
    List<Passwords> getByType (String passwordType);
    @Query("SELECT * FROM Passwords WHERE passwordId = :passwordId")
    Passwords fetchOnePasswordbyPasswordId (int passwordId);
    @Update
    void updatePassword (Passwords passwords);
    @Delete
    void deletePassword (Passwords passwords);
    @Query("DELETE FROM Passwords")
    void deleteAllPasswords();
}