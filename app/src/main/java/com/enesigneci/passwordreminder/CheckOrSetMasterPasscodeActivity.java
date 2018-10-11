package com.enesigneci.passwordreminder;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.enesigneci.passwordreminder.db.AppDb;
import com.enesigneci.passwordreminder.model.MasterPasswords;

import online.devliving.passcodeview.PasscodeView;

public class CheckOrSetMasterPasscodeActivity extends AppCompatActivity {
    private AppDb appDb;
    String password,confirmPassword;
    PasscodeView setPasscodeView, checkPasscodeView;
    View setPasscodeLayout, checkPasscodeLayout;
    SharedPreferences prefs;
    int passcodeCount=4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_or_set_master_passcode);
        prefs = getSharedPreferences("com.enesigneci.passwordreminder", MODE_PRIVATE);
        setPasscodeView = findViewById(R.id.set_passcode_view);
        checkPasscodeView = findViewById(R.id.check_passcode_view);
        checkPasscodeLayout=findViewById(R.id.check_passcode_layout);
        setPasscodeLayout=findViewById(R.id.set_passcode_layout);
        appDb = Room.databaseBuilder(getApplicationContext(),
                AppDb.class, "Passwords")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
        if (prefs.getBoolean("firstRun", true)) {
            startActivity(new Intent(CheckOrSetMasterPasscodeActivity.this,InfoActivity.class));
            prefs.edit().putBoolean("firstRun", false).apply();
        }
        if (appDb.masterPasswordsDao().getAllMasterPasswords().isEmpty()){
            setPasscodeLayout.setVisibility(View.VISIBLE);
        }else{
            checkPasscodeLayout.setVisibility(View.VISIBLE);
        }
        checkPasscodeView.setPasscodeEntryListener(new PasscodeView.PasscodeEntryListener() {
            @Override
            public void onPasscodeEntered(String passcode) {
                if (!appDb.masterPasswordsDao().getAllMasterPasswords().isEmpty()){
                    if(appDb.masterPasswordsDao().getAllMasterPasswords().get(0).getMasterPassword().equals(passcode)){
                        finish();
                        startActivity(new Intent(CheckOrSetMasterPasscodeActivity.this,MainActivity.class));
                    }else{
                        if (passcodeCount == 4) {
                            checkPasscodeView.clearText();
                            Toast.makeText(CheckOrSetMasterPasscodeActivity.this, R.string.passcode_count_4, Toast.LENGTH_SHORT).show();
                            passcodeCount--;

                        } else if (passcodeCount == 3) {
                            checkPasscodeView.clearText();
                            Toast.makeText(CheckOrSetMasterPasscodeActivity.this, R.string.passcode_count_3, Toast.LENGTH_SHORT).show();
                            passcodeCount--;

                        } else if (passcodeCount == 2) {
                            checkPasscodeView.clearText();
                            Toast.makeText(CheckOrSetMasterPasscodeActivity.this, R.string.passcode_count_2, Toast.LENGTH_SHORT).show();
                            passcodeCount--;

                        } else if (passcodeCount == 1) {
                            checkPasscodeView.clearText();
                            Toast.makeText(CheckOrSetMasterPasscodeActivity.this, R.string.passcode_count_1, Toast.LENGTH_SHORT).show();
                            passcodeCount--;

                        } else if (passcodeCount == 0) {
                            checkPasscodeView.clearText();
                            Toast.makeText(CheckOrSetMasterPasscodeActivity.this, R.string.passcode_count_0, Toast.LENGTH_SHORT).show();
                            appDb.passwordsDao().deleteAllPasswords();
                            appDb.masterPasswordsDao().deleteMasterPassword();
                            passcodeCount--;

                        } else {
                            Toast.makeText(CheckOrSetMasterPasscodeActivity.this, R.string.passcode_count_0, Toast.LENGTH_SHORT).show();

                        }
                    }
                }else{
                    Toast.makeText(CheckOrSetMasterPasscodeActivity.this, R.string.passwords_was_deleted, Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });
        setPasscodeView.setPasscodeEntryListener(new PasscodeView.PasscodeEntryListener() {
            @Override
            public void onPasscodeEntered(String passcode) {
                if(password==null){
                    password=passcode;
                    setPasscodeView.clearText();
                }else if (confirmPassword==null){
                    confirmPassword=passcode;
                    if (password.equals(confirmPassword)){
                        MasterPasswords masterPassword=new MasterPasswords();
                        masterPassword.setMasterPassword(passcode);
                        appDb.masterPasswordsDao().insertOnlySingleMasterPassword(masterPassword);
                        if (!appDb.masterPasswordsDao().getAllMasterPasswords().isEmpty()){
                            startActivity(new Intent(CheckOrSetMasterPasscodeActivity.this,MainActivity.class));
                        }
                    }else{
                        setPasscodeView.clearText();
                        Toast.makeText(CheckOrSetMasterPasscodeActivity.this, R.string.passwords_didnt_matched, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
