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
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * Created by Azra on 06.09.2015..
 */
public class OglasTest extends ActivityInstrumentationTestCase2<NoviOglasActivity> {
    public OglasTest() {
        super(NoviOglasActivity.class);
    }

    private NoviOglasActivity oglas;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        oglas = getActivity();
    }

    public  void testOglas1() {
        onView(withId(R.id.ponudaBtn)).perform(click());
        onView(withId(R.id.scrollView2)).check(matches(isDisplayed()));
        onView(withId(R.id.trosakTxt)).check(matches(isDisplayed()));
    }

    public  void testOglas2() {
        onView(withId(R.id.potraznjaBtn)).perform(click());
        onView(withId(R.id.scrollView2)).check(matches(isDisplayed()));
        onView(withId(R.id.trosakTxt)).check(matches(not(isDisplayed())));
    }

    public  void testOglas3() {
        onView(withId(R.id.potraznjaBtn)).perform(click());
        onView(withId(R.id.polazakTxt)).perform(typeText("Mostar"));
        onView(withId(R.id.odredisteTxt)).perform(typeText("Banja Luka"));
        onView(withId(R.id.datumTxt)).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.daljeBtn)).perform(click());
        onView(withId(R.id.postaviOglas)).check(matches(isDisplayed()));
        onView(withId(R.id.postaviBtn)).perform(click());
        onView(withId(R.id.postaviBtn)).check(matches(isDisplayed()));
    }

    public  void testOglas4() {
        //pritisni "Potraznja"
        onView(withId(R.id.potraznjaBtn)).perform(click());
        //unesi polazak
        onView(withId(R.id.polazakTxt)).perform(typeText("Mostar"));
        //unesi odredište
        onView(withId(R.id.odredisteTxt)).perform(typeText("Banja Luka"));
        //klikni na datum
        //otvara se kalendar
        onView(withId(R.id.datumTxt)).perform(click());
        //klikni OK
        onView(withText("OK")).perform(click());
        //klikni "Dalje"
        onView(withId(R.id.daljeBtn)).perform(click());
        //provjeri da li se otvorio novi ekran
        onView(withId(R.id.postaviOglas)).check(matches(isDisplayed()));
        //unesi broj mjesta
        onView(withId(R.id.brMjestaTxt)).perform(typeText("2"));
        //klikni "Postavi oglas"
        onView(withId(R.id.postaviBtn)).perform(click());
        //provjeri da li je otvoren glavni ekran
        //oglas uspješno dodan
        onView(withId(R.id.mainscreen)).check(matches(isDisplayed()));
    }

    public  void testOglas5() {
        onView(withId(R.id.ponudaBtn)).perform(click());
        onView(withId(R.id.polazakTxt)).perform(typeText("Test test"));
        onView(withId(R.id.odredisteTxt)).perform(typeText("Banja Luka"));
        onView(withId(R.id.datumTxt)).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.daljeBtn)).perform(click());
        onView(withId(R.id.postaviOglas)).check(matches(isDisplayed()));
        onView(withId(R.id.brMjestaTxt)).perform(typeText("2"));
        onView(withId(R.id.postaviBtn)).perform(click());
        onView(withId(R.id.mainscreen)).check(matches(isDisplayed()));
        onView(withId(R.id.btnTraziVoznje)).perform(click());
        onView(withId(R.id.traziMjestoTxt)).perform(typeText("Test test"));
        onView(withId(R.id.traziBtn)).perform(click());
        onView(withText("Vožnje")).check(matches(isDisplayed()));
    }
}

