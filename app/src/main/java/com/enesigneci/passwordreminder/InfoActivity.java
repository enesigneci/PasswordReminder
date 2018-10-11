package com.enesigneci.passwordreminder;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.codemybrainsout.onboarder.AhoyOnboarderActivity;
import com.codemybrainsout.onboarder.AhoyOnboarderCard;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InfoActivity extends AhoyOnboarderActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AhoyOnboarderCard description = new AhoyOnboarderCard("PasswordReminder", getString(R.string.onboarding_description), R.mipmap.ic_launcher);
        AhoyOnboarderCard masterPassword = new AhoyOnboarderCard(getString(R.string.onboarding_master_password_title), getString(R.string.onboarding_master_password_description), R.mipmap.ic_launcher);
        AhoyOnboarderCard addNewPassword = new AhoyOnboarderCard(getString(R.string.onboarding_add_new_password_title), getString(R.string.onboarding_add_new_password_description), R.mipmap.ic_launcher);
        AhoyOnboarderCard seeYourPasswords = new AhoyOnboarderCard(getString(R.string.onboarding_see_your_passwords_title), getString(R.string.onboarding_see_your_passwords_description), R.mipmap.ic_launcher);
        AhoyOnboarderCard deleteAPassword = new AhoyOnboarderCard(getString(R.string.onboarding_delete_a_password_title), getString(R.string.onboarding_delete_a_password_description), R.mipmap.ic_launcher);
        description.setBackgroundColor(R.color.black_transparent);
        description.setTitleColor(R.color.white);
        description.setDescriptionColor(R.color.grey_200);
        description.setTitleTextSize(dpToPixels(6, this));
        description.setDescriptionTextSize(dpToPixels(4, this));

        masterPassword.setBackgroundColor(R.color.black_transparent);
        masterPassword.setTitleColor(R.color.white);
        masterPassword.setDescriptionColor(R.color.grey_200);
        masterPassword.setTitleTextSize(dpToPixels(8, this));
        masterPassword.setDescriptionTextSize(dpToPixels(4, this));

        addNewPassword.setBackgroundColor(R.color.black_transparent);
        addNewPassword.setTitleColor(R.color.white);
        addNewPassword.setDescriptionColor(R.color.grey_200);
        addNewPassword.setTitleTextSize(dpToPixels(8, this));
        addNewPassword.setDescriptionTextSize(dpToPixels(4, this));

        seeYourPasswords.setBackgroundColor(R.color.black_transparent);
        seeYourPasswords.setTitleColor(R.color.white);
        seeYourPasswords.setDescriptionColor(R.color.grey_200);
        seeYourPasswords.setTitleTextSize(dpToPixels(8, this));
        seeYourPasswords.setDescriptionTextSize(dpToPixels(4, this));

        deleteAPassword.setBackgroundColor(R.color.black_transparent);
        deleteAPassword.setTitleColor(R.color.white);
        deleteAPassword.setDescriptionColor(R.color.grey_200);
        deleteAPassword.setTitleTextSize(dpToPixels(8, this));
        deleteAPassword.setDescriptionTextSize(dpToPixels(4, this));

        List<AhoyOnboarderCard> pages = new ArrayList<>();
        pages.add(description);
        pages.add(masterPassword);
        pages.add(addNewPassword);
        pages.add(seeYourPasswords);
        pages.add(deleteAPassword);
        setOnboardPages(pages);
        setFinishButtonTitle(R.string.finish);
        showNavigationControls(true);
        setGradientBackground();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setFinishButtonDrawableStyle(ContextCompat.getDrawable(this, R.drawable.rounded_button));
        }
    }

    @Override
    public void onFinishButtonPressed() {
        if (getIntent().getExtras()!=null)
            if (Objects.equals(getIntent().getExtras().get("from"), "MainActivity")) {
                startActivity(new Intent(InfoActivity.this, MainActivity.class));
            } else {
                startActivity(new Intent(InfoActivity.this, CheckOrSetMasterPasscodeActivity.class));
            }
        else {
            startActivity(new Intent(InfoActivity.this, CheckOrSetMasterPasscodeActivity.class));
        }
    }
}
