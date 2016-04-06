package com.azra.putujmozajedno;

import android.app.Application;
import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ApplicationTestCase;
import android.util.Log;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

/**
 * Created by Azra on 06.09.2015..
 */
public class LoginTest extends ActivityInstrumentationTestCase2<Login> {
    public LoginTest() {
        super(Login.class);
    }

    private Login login;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        login = getActivity();
        Sesija sesija = new Sesija(login);
        sesija.logout();
    }

    public  void testLogin1(){
        onView(withId(R.id.login)).perform(click());
        onView(withId(R.id.login)).check(matches(isDisplayed()));
    }

    public void testLogin2() {
        onView(withId(R.id.email)).perform(typeText(""));
        onView(withId(R.id.password)).perform(typeText("123"));
        onView(withId(R.id.login)).perform(click());
        onView(withId(R.id.login)).check(matches(isDisplayed()));
    }

    public void testLogin3() {

        onView(withId(R.id.password)).perform(typeText("123"));
        onView(withId(R.id.login)).perform(click());
        onView(withId(R.id.password)).check(matches(isDisplayed()));
    }

    public void testLogin4() {
        //unesi e-mail
        onView(withId(R.id.email)).perform(typeText("azramah@gmail.com"));
        //pritisni "Prijavi se"
        onView(withId(R.id.login)).perform(click());
        //provjeri da li je dugme "Prijavi se" i dalje vidljivo
        //nije se otvorila nova Aktivnost
        onView(withId(R.id.login)).check(matches(isDisplayed()));
    }

    public  void testPrijava() {
        onView(withId(R.id.email)).perform(typeText("azramah6@gmail.com"));
        onView(withId(R.id.password)).perform(typeText("123"));
        onView(withId(R.id.login)).perform(click());
        onView(withId(R.id.mainscreen)).check(matches(isDisplayed()));
    }

    public  void testRegistracija() {
        onView(withId(R.id.register)).perform(click());
        onView(withId(R.id.imeKorisnika)).check(matches(isDisplayed()));
    }

}

