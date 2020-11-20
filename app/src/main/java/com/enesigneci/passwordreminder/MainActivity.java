package com.enesigneci.passwordreminder;

import androidx.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.enesigneci.passwordreminder.adapter.ReminderViewPagerAdapter;
import com.enesigneci.passwordreminder.db.AppDb;
import com.enesigneci.passwordreminder.listener.DataRefreshListener;
import com.enesigneci.passwordreminder.model.Passwords;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;


public class MainActivity extends AppCompatActivity {
    Context mContext;
    TabLayout tabLayout;
    ViewPager viewPager;
    ReminderViewPagerAdapter pagerAdapter;
    Passwords passwordToAdd;
    DataRefreshListener socialMediaRefreshListener;
    DataRefreshListener emailRefreshListener;
    DataRefreshListener deviceRefreshListener;
    private AppDb db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = MainActivity.this;
        passwordToAdd=new Passwords();
        final SecurityManager securityManager = new SecurityManager();

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.pager);

        tabLayout.addTab(tabLayout.newTab().setText(R.string.SOCIAL_MEDIA_TAB));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.EMAIL_TAB));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.DEVICE_TAB));
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);

        pagerAdapter = new ReminderViewPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setOffscreenPageLimit(4);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        ImageView infoButton=findViewById(R.id.info);
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,InfoActivity.class);
                intent.putExtra("from","MainActivity");
                startActivity(intent);
            }
        });
        // in Activity Context
        final ImageView icon = new ImageView(this); // Create an icon
        icon.setImageDrawable(getResources().getDrawable(R.drawable.add));
        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setTheme(FloatingActionButton.THEME_DARK)
                .setContentView(icon)
                .setBackgroundDrawable(R.drawable.fab_background)
                .build();
        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        // repeat many times:
        ImageView socialMediaIcon = new ImageView(this);
        socialMediaIcon.setImageDrawable(getResources().getDrawable(R.drawable.social));
        FloatingActionButton.LayoutParams floatingButtonLayoutParams=new FloatingActionButton.LayoutParams(64,64);
        SubActionButton addSocialMedia = itemBuilder.setContentView(socialMediaIcon).setBackgroundDrawable(getResources().getDrawable(R.drawable.sub_fab_background)).setTheme(SubActionButton.THEME_DARK).setLayoutParams(floatingButtonLayoutParams).build();
        ImageView emailIcon = new ImageView(this);
        emailIcon.setImageDrawable(getResources().getDrawable(R.drawable.email));
        SubActionButton addEmail = itemBuilder.setContentView(emailIcon).setLayoutParams(floatingButtonLayoutParams).setBackgroundDrawable(getResources().getDrawable(R.drawable.sub_fab_background)).setTheme(SubActionButton.THEME_DARK).build();
        ImageView deviceIcon = new ImageView(this);
        deviceIcon.setImageDrawable(getResources().getDrawable(R.drawable.small_device));
        SubActionButton addDevice = itemBuilder.setContentView(deviceIcon).setLayoutParams(floatingButtonLayoutParams).setBackgroundDrawable(getResources().getDrawable(R.drawable.sub_fab_background)).setTheme(SubActionButton.THEME_DARK).build();

        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(addSocialMedia)
                .addSubActionView(addEmail)
                .addSubActionView(addDevice)
                .attachTo(actionButton)
                .build();
        addSocialMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MaterialDialog addSocialMediaAccountPasswordDialog = new MaterialDialog.Builder(MainActivity.this).customView(R.layout.add_password_dialog,true).build();
                MaterialSpinner spinner = (MaterialSpinner) addSocialMediaAccountPasswordDialog.getCustomView().findViewById(R.id.spinner);
                spinner.setItems(getString(R.string.choose_type),"Facebook", "Twitter", "Instagram", "Linkedin", "Medium");
                spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                    @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                        if (item.equals("Facebook"))
                            passwordToAdd.setPasswordType(R.drawable.facebook);
                        else if (item.equals("Twitter"))
                            passwordToAdd.setPasswordType(R.drawable.twitter);
                        else if (item.equals("Instagram"))
                            passwordToAdd.setPasswordType(R.drawable.instagram);
                        else if (item.equals("Linkedin"))
                            passwordToAdd.setPasswordType(R.drawable.linkedin);
                        else if (item.equals("Medium"))
                            passwordToAdd.setPasswordType(R.drawable.medium);
                    }
                });
                addSocialMediaAccountPasswordDialog.getCustomView().setPadding(0,0,0,0);
                addSocialMediaAccountPasswordDialog.getCustomView().setTop(0);
                addSocialMediaAccountPasswordDialog.getCustomView().setBottom(0);
                addSocialMediaAccountPasswordDialog.getCustomView().setLeft(0);
                addSocialMediaAccountPasswordDialog.getCustomView().setRight(0);
                addSocialMediaAccountPasswordDialog.setCancelable(false);
                addSocialMediaAccountPasswordDialog.show();
                final View dialogView=addSocialMediaAccountPasswordDialog.getCustomView();
                if (dialogView!=null){
                    TextView dialogTitle=dialogView.findViewById(R.id.dialog_title);
                    dialogTitle.setText(R.string.add_social_media_password);
                    final EditText passwordName=dialogView.findViewById(R.id.password_name);
                    final EditText passwordUsername=dialogView.findViewById(R.id.password_username);
                    final EditText passwordText=dialogView.findViewById(R.id.password_text);
                    Button addSocialMediaPasswordButton=dialogView.findViewById(R.id.add_password_button);
                    addSocialMediaPasswordButton.setText(R.string.add_social_media_account);
                    addSocialMediaPasswordButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (passwordName.getText().toString().isEmpty() || passwordUsername.getText().toString().isEmpty() || passwordText.getText().toString().isEmpty()){
                                Snackbar.make(dialogView, R.string.fill_the_empty_areas,Snackbar.LENGTH_SHORT).show();
                            }else{
                                passwordToAdd.setPasswordCategory("SocialMediaPassword");
                                passwordToAdd.setPasswordName(passwordName.getText().toString());
                                passwordToAdd.setPasswordUsername(passwordUsername.getText().toString());
                                passwordToAdd.setPasswordText(SecurityManager.toBase64(passwordText.getText().toString()));
                                db= Room.databaseBuilder(MainActivity.this,AppDb.class,"Passwords").fallbackToDestructiveMigration().allowMainThreadQueries().build();
                                db.passwordsDao().insertOnlySinglePassword(passwordToAdd);
                                passwordToAdd=new Passwords();
                                addSocialMediaAccountPasswordDialog.dismiss();
                                socialMediaRefreshListener.onRefresh();
                            }
                        }
                    });
                    ImageView closeButton = dialogView.findViewById(R.id.close_dialog);
                    closeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            addSocialMediaAccountPasswordDialog.dismiss();
                        }
                    });
                }
            }
        });
        addEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MaterialDialog addEmailAccountPasswordDialog = new MaterialDialog.Builder(MainActivity.this).customView(R.layout.add_password_dialog,true).build();
                MaterialSpinner spinner = (MaterialSpinner) addEmailAccountPasswordDialog.getCustomView().findViewById(R.id.spinner);
                spinner.setItems(getString(R.string.choose_type),"Microsoft", "Gmail", "Yahoo");
                spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                    @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                        if (item.equals("Microsoft"))
                            passwordToAdd.setPasswordType(R.drawable.outlook);
                        else if (item.equals("Gmail"))
                            passwordToAdd.setPasswordType(R.drawable.gmail);
                        else if (item.equals("Yahoo"))
                            passwordToAdd.setPasswordType(R.drawable.yahoo);
                    }
                });
                addEmailAccountPasswordDialog.getCustomView().setPadding(0,0,0,0);
                addEmailAccountPasswordDialog.getCustomView().setTop(0);
                addEmailAccountPasswordDialog.getCustomView().setBottom(0);
                addEmailAccountPasswordDialog.getCustomView().setLeft(0);
                addEmailAccountPasswordDialog.getCustomView().setRight(0);
                addEmailAccountPasswordDialog.setCancelable(false);
                addEmailAccountPasswordDialog.show();
                final View dialogView=addEmailAccountPasswordDialog.getCustomView();
                if (dialogView!=null){
                    TextView dialogTitle=dialogView.findViewById(R.id.dialog_title);
                    dialogTitle.setText(R.string.add_email_account_password);
                    final EditText passwordName=dialogView.findViewById(R.id.password_name);
                    final EditText passwordUsername=dialogView.findViewById(R.id.password_username);
                    final EditText passwordText=dialogView.findViewById(R.id.password_text);
                    Button addEmailPasswordButton=dialogView.findViewById(R.id.add_password_button);
                    addEmailPasswordButton.setText(R.string.add_email_account);
                    addEmailPasswordButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (passwordName.getText().toString().isEmpty() || passwordUsername.getText().toString().isEmpty() || passwordText.getText().toString().isEmpty()){
                                Snackbar.make(dialogView,getString(R.string.fill_the_empty_areas),Snackbar.LENGTH_SHORT).show();
                            }else{
                                passwordToAdd.setPasswordCategory("EmailPassword");
                                passwordToAdd.setPasswordName(passwordName.getText().toString());
                                passwordToAdd.setPasswordUsername(passwordUsername.getText().toString());
                                passwordToAdd.setPasswordText(SecurityManager.toBase64(passwordText.getText().toString()));
                                db= Room.databaseBuilder(MainActivity.this,AppDb.class,"Passwords").fallbackToDestructiveMigration().allowMainThreadQueries().build();
                                db.passwordsDao().insertOnlySinglePassword(passwordToAdd);
                                passwordToAdd=new Passwords();
                                addEmailAccountPasswordDialog.dismiss();
                                emailRefreshListener.onRefresh();
                            }

                        }
                    });
                    ImageView closeButton = dialogView.findViewById(R.id.close_dialog);
                    closeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            addEmailAccountPasswordDialog.dismiss();
                        }
                    });
                }
            }
        });
        addDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MaterialDialog addDeviceAccountPasswordDialog = new MaterialDialog.Builder(MainActivity.this).customView(R.layout.add_password_dialog,true).build();
                MaterialSpinner spinner = (MaterialSpinner) addDeviceAccountPasswordDialog.getCustomView().findViewById(R.id.spinner);
                spinner.setItems(getString(R.string.choose_type),getString(R.string.computer), getString(R.string.phone),getString(R.string.tablet));
                spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                    @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                    passwordToAdd.setPasswordType(R.drawable.device);
                    }
                });
                addDeviceAccountPasswordDialog.getCustomView().setPadding(0,0,0,0);
                addDeviceAccountPasswordDialog.getCustomView().setTop(0);
                addDeviceAccountPasswordDialog.getCustomView().setBottom(0);
                addDeviceAccountPasswordDialog.getCustomView().setLeft(0);
                addDeviceAccountPasswordDialog.getCustomView().setRight(0);
                addDeviceAccountPasswordDialog.setCancelable(false);
                addDeviceAccountPasswordDialog.show();
                final View dialogView=addDeviceAccountPasswordDialog.getCustomView();
                if (dialogView!=null){
                    TextView dialogTitle=dialogView.findViewById(R.id.dialog_title);
                    dialogTitle.setText(R.string.add_device_account_password);
                    final EditText passwordName=dialogView.findViewById(R.id.password_name);
                    final EditText passwordUsername=dialogView.findViewById(R.id.password_username);
                    final EditText passwordText=dialogView.findViewById(R.id.password_text);
                    Button addDevicePasswordButton=dialogView.findViewById(R.id.add_password_button);
                    addDevicePasswordButton.setText(R.string.add_device_account);
                    addDevicePasswordButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (passwordName.getText().toString().isEmpty() || passwordUsername.getText().toString().isEmpty() || passwordText.getText().toString().isEmpty()){
                                Snackbar.make(dialogView,getString(R.string.fill_the_empty_areas),Snackbar.LENGTH_SHORT).show();
                            }else{
                                passwordToAdd.setPasswordCategory("DevicePassword");
                                passwordToAdd.setPasswordName(passwordName.getText().toString());
                                passwordToAdd.setPasswordUsername(passwordUsername.getText().toString());
                                passwordToAdd.setPasswordText(SecurityManager.toBase64(passwordText.getText().toString()));
                                db= Room.databaseBuilder(MainActivity.this,AppDb.class,"Passwords").fallbackToDestructiveMigration().allowMainThreadQueries().build();
                                db.passwordsDao().insertOnlySinglePassword(passwordToAdd);
                                passwordToAdd=new Passwords();
                                addDeviceAccountPasswordDialog.dismiss();
                                deviceRefreshListener.onRefresh();
                            }

                        }
                    });
                    ImageView closeButton = dialogView.findViewById(R.id.close_dialog);
                    closeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            addDeviceAccountPasswordDialog.dismiss();
                        }
                    });
                }
            }
        });
    }

    public void setSocialMediaRefreshListener(DataRefreshListener socialMediaRefreshListener) {
        this.socialMediaRefreshListener = socialMediaRefreshListener;
    }

    public void setEmailRefreshListener(DataRefreshListener emailRefreshListener) {
        this.emailRefreshListener = emailRefreshListener;
    }

    public void setDeviceRefreshListener(DataRefreshListener deviceRefreshListener) {
        this.deviceRefreshListener = deviceRefreshListener;
    }
}
