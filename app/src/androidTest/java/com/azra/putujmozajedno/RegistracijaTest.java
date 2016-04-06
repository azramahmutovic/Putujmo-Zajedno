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
public class RegistracijaTest extends ActivityInstrumentationTestCase2<NoviKorisnikActivity> {
    public RegistracijaTest() {
        super(NoviKorisnikActivity.class);
    }

    private NoviKorisnikActivity registracija;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        registracija = getActivity();
    }

    public  void testRegistracija1(){
        onView(withId(R.id.spasiDugme)).perform(click());
        onView(withId(R.id.spasiDugme)).check(matches(isDisplayed()));
    }

    public void testRegistracija2() {
        onView(withId(R.id.imeKorisnika)).perform(typeText("Edin"));
        onView(withId(R.id.spasiDugme)).perform(click());
        onView(withId(R.id.spasiDugme)).check(matches(isDisplayed()));
    }

    public void testRegistracija3() {
        onView(withId(R.id.imeKorisnika)).perform(typeText("Edin"));
        onView(withId(R.id.prezimeKorisnika)).perform(typeText("Hadzialic"));
        onView(withId(R.id.spasiDugme)).perform(click());
        onView(withId(R.id.spasiDugme)).check(matches(isDisplayed()));
    }

    public void testRegistracija4() {
        onView(withId(R.id.imeKorisnika)).perform(typeText("Edin"));
        onView(withId(R.id.prezimeKorisnika)).perform(typeText("Hadzialic"));
        onView(withId(R.id.passwordKorisnika)).perform(typeText("password"));
        onView(withId(R.id.emailKorisnika)).perform(typeText("nesto@nesto"));
        onView(withId(R.id.spasiDugme)).perform(click());
        onView(withId(R.id.mainscreen)).check(matches(isDisplayed()));
    }

}

