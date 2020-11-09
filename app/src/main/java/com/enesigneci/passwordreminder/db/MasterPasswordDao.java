package com.enesigneci.passwordreminder.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.enesigneci.passwordreminder.model.MasterPasswords;

import java.util.List;

@Dao
public interface MasterPasswordDao {

    @Query("SELECT * FROM MasterPasswords")
    List<MasterPasswords> getAllMasterPasswords();
    @Insert
    void insertOnlySingleMasterPassword (MasterPasswords masterPasswords);
    @Update
    void updateMasterPassword (MasterPasswords masterPasswords);
    @Query("DELETE FROM MasterPasswords")
    void deleteMasterPassword();
}