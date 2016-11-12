package com.jsbomb.noifcalculator.androidTest;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.noifcalculator.MainActivity;
import com.example.noifcalculator.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by seojohann on 11/10/16.
 */
@RunWith(AndroidJUnit4.class)
public class CalculatorTest {

    private static final String EMPTY = "";

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void operandTest() {
        onView(withId(R.id.btn_1)).perform(click());

        onView(withId(R.id.txt_input)).check(matches(withText("1")));
    }

    @Test
    public void addTest() {
        onView(withId(R.id.btn_7)).perform(click());
        onView(withId(R.id.btn_add)).perform(click());
        onView(withId(R.id.btn_2)).perform(click());
        onView(withId(R.id.btn_enter)).perform(click());

        String resultString = "7 + 2\n9.0";
        onView(withId(R.id.txt_input)).check(matches(withText(resultString)));
    }

    @Test
    public void subtractTest() {
        onView(withId(R.id.btn_7)).perform(click());
        onView(withId(R.id.btn_subtract)).perform(click());
        onView(withId(R.id.btn_2)).perform(click());
        onView(withId(R.id.btn_enter)).perform(click());

        String resultString = "7 - 2\n5.0";
        onView(withId(R.id.txt_input)).check(matches(withText(resultString)));
    }

    @Test
    public void multiplyTest() {
        onView(withId(R.id.btn_7)).perform(click());
        onView(withId(R.id.btn_multiply)).perform(click());
        onView(withId(R.id.btn_2)).perform(click());
        onView(withId(R.id.btn_enter)).perform(click());

        String resultString = "7 * 2\n14.0";
        onView(withId(R.id.txt_input)).check(matches(withText(resultString)));
    }

    @Test
    public void divideTest() {
        onView(withId(R.id.btn_7)).perform(click());
        onView(withId(R.id.btn_divide)).perform(click());
        onView(withId(R.id.btn_2)).perform(click());
        onView(withId(R.id.btn_enter)).perform(click());

        String resultString = "7 / 2\n3.5";
        onView(withId(R.id.txt_input)).check(matches(withText(resultString)));
    }

    @Test
    public void divideByZeroTest() {
        onView(withId(R.id.btn_7)).perform(click());
        onView(withId(R.id.btn_divide)).perform(click());
        onView(withId(R.id.btn_0)).perform(click());
        onView(withId(R.id.btn_enter)).perform(click());

        String errorMsg = mActivityRule.getActivity().getString(R.string.zero_divisor_error_msg);
        onView(withId(R.id.txt_input)).check(matches(withText(errorMsg)));
    }
}
