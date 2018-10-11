package com.enesigneci.passwordreminder.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

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