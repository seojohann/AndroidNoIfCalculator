package com.jsbomb.noifcalculator.androidTest;


import com.jsbomb.noifcalculator.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.Until;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

/**
 * Created by seojohann on 11/10/16.
 */
@RunWith(AndroidJUnit4.class)
public class CalculatorWaitedTest {

    private static final String EMPTY = "";
    private static final int B0 = R.id.btn_0;
    private static final int B1 = R.id.btn_1;
    private static final int B2 = R.id.btn_2;
    private static final int B3 = R.id.btn_3;
    private static final int B4 = R.id.btn_4;
    private static final int B5 = R.id.btn_5;
    private static final int B6 = R.id.btn_6;
    private static final int B7 = R.id.btn_7;
    private static final int B8 = R.id.btn_8;
    private static final int B9 = R.id.btn_9;
    private static final int BENTER = R.id.btn_enter;
    private static final int BADD = R.id.btn_add;
    private static final int BSUBTRACT = R.id.btn_subtract;
    private static final int BMULTIPLY = R.id.btn_multiply;
    private static final int BDIVIDE = R.id.btn_divide;
    private static final int TV_INPUT = R.id.txt_input;

    private UiDevice mUiDevice;

    @Before
    public void waitForLaunch() {
        mUiDevice = UiDevice.getInstance(getInstrumentation());

        mUiDevice.wait(Until.hasObject(By.pkg("com.jsbomb.noifcalculator").depth(0)), 10000);
    }

    @Test
    public void operandTest() {
        click1();
        checkResult("1");
    }

    @Test
    public void twoDiditTest() {
        click7();
        click2();

        checkResult("72");
    }

    @Test
    public void addTest() {
        click7();
        clickAdd();
        click2();
        clickEnter();

        String resultString = "7 + 2\n9.0";
        checkResult(resultString);
    }

    @Test
    public void subtractTest() {
        click7();
        clickSubtract();
        click2();
        clickEnter();

        String resultString = "7 - 2\n5.0";
        checkResult(resultString);
    }

    @Test
    public void multiplyTest() {
        click7();
        clickMultiply();
        click2();
        clickEnter();

        String resultString = "7 * 2\n14.0";
        checkResult(resultString);
    }

    @Test
    public void divideTest() {
        click7();
        clickDivide();
        click2();
        clickEnter();

        String resultString = "7 / 2\n3.5";
        checkResult(resultString);
    }

    @Test
    public void divideByZeroTest() {
        click7();
        clickDivide();
        click0();
        clickEnter();

        checkZeroDivisorError();
    }

    @Test
    public void moreAddTest() {
        click7();
        clickAdd();
        click2();
        clickAdd();
        click0();
        clickAdd();
        click1();
        clickEnter();

        String resultString = "7 + 2 + 0 + 1\n10.0";
        checkResult(resultString);
    }

    @Test
    public void moreSubtractTest() {
        click7();
        clickSubtract();
        click2();
        clickSubtract();
        click0();
        clickSubtract();
        click1();
        clickEnter();

        String resultString = "7 - 2 - 0 - 1\n4.0";
        checkResult(resultString);
    }

    @Test
    public void moreMultiplyByZeroTest() {
        click7();
        clickMultiply();
        click2();
        clickMultiply();
        click0();
        clickMultiply();
        click1();
        clickEnter();

        String resultString = "7 * 2 * 0 * 1\n0.0";
        onView(withId(R.id.txt_input)).check(matches(withText(resultString)));
    }

    @Test
    public void moreDivideByZeroTest() {
        click7();
        clickDivide();
        click2();
        clickDivide();
        click0();
        clickDivide();

        checkZeroDivisorError();

        click1();
        clickEnter();

        String resultString = "1\n1.0";
        checkResult(resultString);
    }

    @Test
    public void moreMultiplyTest() {
        click7();
        clickMultiply();
        click2();
        clickMultiply();
        click3();
        clickMultiply();
        click1();
        clickEnter();

        String resultString = "7 * 2 * 3 * 1\n42.0";
        checkResult(resultString);
    }

    @Test
    public void moreDivideTest() {
        click5();
        click4();
        clickDivide();
        click9();
        clickDivide();
        click3();
        clickDivide();
        click2();
        clickEnter();

        String resultString = "54 / 9 / 3 / 2\n1.0";
        checkResult(resultString);
    }

    @Test
    public void mixedOpTest() {
        click7();
        clickMultiply();
        click2();
        clickAdd();
        click0();
        clickMultiply();
        click1();
        clickEnter();

        String resultString = "7 * 2 + 0 * 1\n14.0";
        checkResult(resultString);
    }

    @Test
    public void mixedOpTest1() {
        click7();
        clickAdd();
        click2();
        clickMultiply();
        click0();
        clickSubtract();
        click1();
        clickEnter();

        String resultString = "7 + 2 * 0 - 1\n6.0";
        checkResult(resultString);
    }

    @Test
    public void mixedOpTest2() {
        click8();
        clickMultiply();
        click6();
        clickDivide();
        click2();
        clickSubtract();
        click2();
        clickMultiply();
        click3();
        clickAdd();
        click0();
        clickSubtract();
        click1();
        click0();
        click0();
        clickEnter();

        String resultString = "8 * 6 / 2 - 2 * 3 + 0 - 100\n-82.0";
        onView(withId(R.id.txt_input)).check(matches(withText(resultString)));
    }

    private void checkZeroDivisorError() {
        String errorMsg = getInstrumentation().getTargetContext().getString(R.string.zero_divisor_error_msg);
        onView(withId(R.id.txt_input)).check(matches(withText(errorMsg)));
    }

    private void click0() {
        clickBtn(B0);
    }

    private void click1() {
        clickBtn(B1);
    }

    private void click2() {
        clickBtn(B2);
    }

    private void click3() {
        clickBtn(B3);
    }

    private void click4() {
        clickBtn(B4);
    }

    private void click5() {
        clickBtn(B5);
    }

    private void click6() {
        clickBtn(B6);
    }

    private void click7() {
        clickBtn(B7);
    }

    private void click8() {
        clickBtn(B8);
    }

    private void click9() {
        clickBtn(B9);
    }

    private void clickEnter() {
        clickBtn(BENTER);
    }

    private void clickAdd() {
        clickBtn(BADD);
    }

    private void clickSubtract() {
        clickBtn(BSUBTRACT);
    }

    private void clickMultiply() {
        clickBtn(BMULTIPLY);
    }

    private void clickDivide() {
        clickBtn(BDIVIDE);
    }

    private void clickBtn(int id) {
        onView(withId(id)).perform(click());
    }

    private void checkResult(String result) {
        onView(withId(R.id.txt_input)).check(matches(withText(result)));
    }
}
