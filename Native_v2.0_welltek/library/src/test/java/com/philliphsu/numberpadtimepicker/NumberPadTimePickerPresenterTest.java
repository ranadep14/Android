package com.philliphsu.numberpadtimepicker;

import com.philliphsu.numberpadtimepicker.INumberPadTimePicker.Presenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static com.philliphsu.numberpadtimepicker.ButtonTextModel.text;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class NumberPadTimePickerPresenterTest {
    private static final int MODE_12HR = 0;
    private static final int MODE_24HR = 1;

    private final INumberPadTimePicker.View[] mViews = new INumberPadTimePicker.View[2];
    private final Presenter[] mPresenters = new Presenter[2];
    private final ButtonTextModel[] mButtonTextModels = new ButtonTextModel[2];

    @Mock
    private LocaleModel mLocaleModel;

    @Before
    public final void setup() {
        // Inject mocks annotated with the @Mock annotation.
        MockitoAnnotations.initMocks(this);
        when(mLocaleModel.getTimeSeparator(anyBoolean())).thenReturn(":");

        mButtonTextModels[MODE_12HR] = new ButtonTextModel(mLocaleModel, false);
        mButtonTextModels[MODE_24HR] = new ButtonTextModel(mLocaleModel, true);
    }

    @Test
    public void verifyViewEnabledStatesForEmptyState() {
        createNewViewAndPresenter(MODE_12HR);
        mPresenters[MODE_12HR].presentState(NumberPadTimePickerState.EMPTY);
        verifyInitialization(MODE_12HR);
        verify(mViews[MODE_12HR]).setOkButtonEnabled(false);

        createNewViewAndPresenter(MODE_24HR);
        mPresenters[MODE_24HR].presentState(NumberPadTimePickerState.EMPTY);
        verifyInitialization(MODE_24HR);
        verify(mViews[MODE_24HR]).setOkButtonEnabled(false);
    }

    @Test
    public final void mode12Hr_VerifyViewEnabledStates_Input_1_to_9() {
        verifyViewEnabledStates(TestSuite.MODE_12HR_TESTS_1_TO_9, MODE_12HR);
    }

    @Test
    public final void mode24Hr_VerifyViewEnabledStates_Input_0_To_9() {
        verifyViewEnabledStates(TestSuite.MODE_24HR_TESTS_0_TO_9, MODE_24HR);
    }

    @Test
    public final void mode12Hr_VerifyViewEnabledStates_Input_10_to_95() {
        verifyViewEnabledStates(TestSuite.MODE_12HR_TESTS_10_TO_95, MODE_12HR);
    }

    @Test
    public void mode12Hr_VerifyOnTimeSetCallback() {
        for (int time = 100; time <= 1259; time++) {
            if (time % 100 > 59) {
                System.out.println("Skipping invalid time " + time);
                continue;
            }
            System.out.println("Testing time " + time);
            for (int amOrPm = 0; amOrPm < 2; amOrPm++) {
                createNewViewAndPresenter(MODE_12HR);
                if (time <= 959) {
                    inputThreeDigitTime(time, MODE_12HR);
                } else {
                    inputFourDigitTime(time, MODE_12HR);
                }
                mPresenters[MODE_12HR].onAltKeyClick(altText(amOrPm, MODE_12HR));
                final int hour = (time >= 1200 ? 0 : time / 100) + (amOrPm == 1 ? 12 : 0);
                final int minute = time % 100;
                confirmTimeSelection(mPresenters[MODE_12HR], MODE_12HR, hour, minute);
            }
        }
    }

    @Test
    public void mode12Hr_VerifyOnTimeSetCallback_UsingAltButtons() {
        for (int hour = 1; hour <= 12; hour++) {
            System.out.println("Testing time " + String.format("%d:00", hour));
            for (int amOrPm = 0; amOrPm < 2; amOrPm++) {
                createNewViewAndPresenter(MODE_12HR);
                if (hour <= 9) {
                    mPresenters[MODE_12HR].onNumberKeyClick(text(hour));
                } else {
                    mPresenters[MODE_12HR].onNumberKeyClick(text(hour / 10));
                    mPresenters[MODE_12HR].onNumberKeyClick(text(hour % 10));
                }
                mPresenters[MODE_12HR].onAltKeyClick(altText(amOrPm, MODE_12HR));
                final int expectedHour = (hour % 12) + (amOrPm == 1 ? 12 : 0);
                confirmTimeSelection(mPresenters[MODE_12HR], MODE_12HR, expectedHour, 0);
            }
        }
    }

    @Test
    public void mode24Hr_VerifyOnTimeSetCallback() {
        for (int time = 0; time <= 2359; time++) {
            if (time % 100 > 59) {
                System.out.println("Skipping invalid time " + String.format("%04d", time));
                continue;
            }
            if (time <= 959) {
                // These times can be inputted both as 3-digits or as 4-digits, so test both.
                System.out.println("Testing time as 3-digits: " + String.format("%03d", time));
                createNewViewAndPresenter(MODE_24HR);
                inputThreeDigitTime(time, MODE_24HR);
                confirmTimeSelection(mPresenters[MODE_24HR], MODE_24HR, time / 100, time % 100);

                System.out.println("Testing time as 4-digits: " + String.format("%04d", time));
                createNewViewAndPresenter(MODE_24HR);
                inputFourDigitTime(time, MODE_24HR);
                confirmTimeSelection(mPresenters[MODE_24HR], MODE_24HR, time / 100, time % 100);
            } else {
                System.out.println("Testing time: " + String.format("%04d", time));
                createNewViewAndPresenter(MODE_24HR);
                inputFourDigitTime(time, MODE_24HR);
                confirmTimeSelection(mPresenters[MODE_24HR], MODE_24HR, time / 100, time % 100);
            }
        }
    }

    @Test
    public void mode24Hr_VerifyOnTimeSetCallback_UsingAltButtons() {
        for (int hour = 0; hour <= 23; hour++) {
            for (int leftOrRight = 0; leftOrRight < 2; leftOrRight++) {
                final int minute = leftOrRight == 0 ? 0 : 30;
                if (hour <= 9) {
                    // These times can be inputted both as 3-digits or as 4-digits, so test both.
                    System.out.println("Testing time as 3-digits: " + String.format("%d:%02d", hour, minute));
                    createNewViewAndPresenter(MODE_24HR);
                    mPresenters[MODE_24HR].onNumberKeyClick(text(hour));
                    mPresenters[MODE_24HR].onAltKeyClick(altText(leftOrRight, MODE_24HR));
                    confirmTimeSelection(mPresenters[MODE_24HR], MODE_24HR, hour, minute);

                    System.out.println("Testing time as 4-digits: " + String.format("%02d:%02d", hour, minute));
                    createNewViewAndPresenter(MODE_24HR);
                    mPresenters[MODE_24HR].onNumberKeyClick(text(hour / 10));
                    mPresenters[MODE_24HR].onNumberKeyClick(text(hour % 10));
                    mPresenters[MODE_24HR].onAltKeyClick(altText(leftOrRight, MODE_24HR));
                    confirmTimeSelection(mPresenters[MODE_24HR], MODE_24HR, hour, minute);
                } else {
                    System.out.println("Testing time: " + String.format("%02d:%02d", hour, minute));
                    createNewViewAndPresenter(MODE_24HR);
                    mPresenters[MODE_24HR].onNumberKeyClick(text(hour / 10));
                    mPresenters[MODE_24HR].onNumberKeyClick(text(hour % 10));
                    mPresenters[MODE_24HR].onAltKeyClick(altText(leftOrRight, MODE_24HR));
                    confirmTimeSelection(mPresenters[MODE_24HR], MODE_24HR, hour, minute);
                }
            }
        }
    }

    @Test
    public void rotateDevice_savesAndRestoresInstanceState() {
        for (int mode = MODE_12HR; mode <= MODE_24HR; mode++) {
            createNewViewAndPresenter(mode);
            mPresenters[mode].presentState(NumberPadTimePickerState.EMPTY);
            verifyInitialization(mode);
            INumberPadTimePicker.State state = mPresenters[mode].getState();
            // Simulates rotation.
            createNewViewAndPresenter(mode);
            mPresenters[mode].presentState(state);
            verifyInitialization(mode);
        }
    }

    @Test
    public void inputDigits_rotateDevice_savesAndRestoresInstanceState() {
        verifySaveAndRestoreInstanceState(TestSuite.MODE_12HR_TESTS_1_TO_9, MODE_12HR);
        verifySaveAndRestoreInstanceState(TestSuite.MODE_24HR_TESTS_0_TO_9, MODE_24HR);
        verifySaveAndRestoreInstanceState(TestSuite.MODE_12HR_TESTS_10_TO_95, MODE_12HR);
    }
    
    private void verifySaveAndRestoreInstanceState(List<TestCase> tests, int mode) {
        for (TestCase test : tests) {
            createNewViewAndPresenter(mode);
            for (int digit : test.sequence) {
                mPresenters[mode].onNumberKeyClick(text(digit));
            }
            runTestCase(test, mode);
            INumberPadTimePicker.State state = mPresenters[mode].getState();
            // Simulates rotation.
            createNewViewAndPresenter(mode);
            mPresenters[mode].presentState(state);
            runTestCase(test, mode);
        }
    }

    private void verifyInitialization(int mode) {
        if (mode == MODE_12HR) {
            verify(mViews[MODE_12HR]).setNumberKeysEnabled(1, 10);
            verify(mViews[MODE_12HR]).setBackspaceEnabled(false);
            // Assuming no initial text for the time display, there is no need to have to call this.
            verify(mViews[MODE_12HR], never()).updateTimeDisplay(null /* value doesn't matter */);
            verify(mViews[MODE_12HR]).updateAmPmDisplay(null);
            verify(mViews[MODE_12HR]).setAmPmDisplayVisible(true);
            verify(mViews[MODE_12HR]).setAmPmDisplayIndex(mLocaleModel.isAmPmWrittenBeforeTime() ? 0 : 1);
            verify(mViews[MODE_12HR]).setLeftAltKeyText(altText(0, MODE_12HR));
            verify(mViews[MODE_12HR]).setRightAltKeyText(altText(1, MODE_12HR));
            verify(mViews[MODE_12HR]).setLeftAltKeyEnabled(false);
            verify(mViews[MODE_12HR]).setRightAltKeyEnabled(false);
        } else if (mode == MODE_24HR) {
            verify(mViews[MODE_24HR]).setNumberKeysEnabled(0, 10);
            verify(mViews[MODE_24HR]).setBackspaceEnabled(false);
            verify(mViews[MODE_24HR], never()).updateTimeDisplay(null /* value doesn't matter */);
            verify(mViews[MODE_24HR], never()).updateAmPmDisplay(null /* value doesn't matter */);
            verify(mViews[MODE_24HR]).setAmPmDisplayVisible(false);
            verify(mViews[MODE_24HR], never()).setAmPmDisplayIndex(0 /* value doesn't matter */);
            verify(mViews[MODE_24HR]).setLeftAltKeyText(altText(0, MODE_24HR));
            verify(mViews[MODE_24HR]).setRightAltKeyText(altText(1, MODE_24HR));
            verify(mViews[MODE_24HR]).setLeftAltKeyEnabled(false);
            verify(mViews[MODE_24HR]).setRightAltKeyEnabled(false);
        }
    }

    private void confirmTimeSelection(Presenter presenter, int mode, int hour, int minute) {
        presenter.onOkButtonClick();
        verify(mViews[mode]).setResult(hour, minute);
    }

    private void verifyViewEnabledStates(List<TestCase> testSuite, int mode) {
        for (TestCase test : testSuite) {
            System.out.println("Testing sequence: " + Arrays.toString(test.sequence));
            verifyViewEnabledStates(test, mode);
        }
    }

    private void verifyViewEnabledStates(TestCase test, int mode) {
        createNewViewAndPresenter(mode);
        for (int digit : test.sequence) {
            mPresenters[mode].onNumberKeyClick(text(digit));
        }
        runTestCase(test, mode);
    }

    private void runTestCase(TestCase test, int mode) {
        // There could legitimately be multiple calls to these methods with the same arguments.
        // E.g. in MODE_12HR, inputting a sequence of [1, 0, 0] will result in two calls of
        // setNumberKeysEnabled(0, 10). This is fine because we're just interested in verifying
        // the final states specified by the TestCase.
        //
        // Note that we are not verifying all multiple calls to these methods; specifically,
        // we are not verifying intermediate states. This is only verifying the final states
        // specified by the TestCase.
        verify(mViews[mode], atLeastOnce()).setNumberKeysEnabled(test.numberKeysEnabledStart,
                test.numberKeysEnabledEnd);
        verify(mViews[mode], atLeastOnce()).setBackspaceEnabled(test.backspaceEnabled);
        verify(mViews[mode], atLeastOnce()).setLeftAltKeyEnabled(test.leftAltKeyEnabled);
        verify(mViews[mode], atLeastOnce()).setRightAltKeyEnabled(test.rightAltKeyEnabled);
        verify(mViews[mode], atLeastOnce()).setOkButtonEnabled(test.okButtonEnabled);

        // Formatting of the header display is currently not the main concern.
//        verify(mViews[mode], times(test.timeDisplay == null ? 0 : 1)).updateTimeDisplay(test.timeDisplay);
//        verify(mViews[mode], times(test.ampmDisplay == null ? 0 : 1)).updateAmPmDisplay(test.ampmDisplay);
    }

    private String altText(int leftOrRight, int mode) {
        return mButtonTextModels[mode].altText(leftOrRight);
    }
    
    private void createNewViewAndPresenter(int mode) {
        mViews[mode] = mock(INumberPadTimePicker.View.class);
        mPresenters[mode] = new NumberPadTimePickerPresenter(mViews[mode], mLocaleModel, mode == MODE_24HR);
    }

    /**
     * Calls {@link Presenter#onNumberKeyClick(CharSequence) onNumberKeyClick()} for each digit
     * of a 3-digit time.
     */
    private void inputThreeDigitTime(int time, int mode) {
        mPresenters[mode].onNumberKeyClick(text(time / 100));
        mPresenters[mode].onNumberKeyClick(text((time % 100) / 10));
        mPresenters[mode].onNumberKeyClick(text(time % 10));
    }

    /**
     * Calls {@link Presenter#onNumberKeyClick(CharSequence) onNumberKeyClick()} for each digit
     * of a 4-digit time.
     */
    private void inputFourDigitTime(int time, int mode) {
        mPresenters[mode].onNumberKeyClick(text(time / 1000));
        mPresenters[mode].onNumberKeyClick(text((time % 1000) / 100));
        mPresenters[mode].onNumberKeyClick(text((time % 100) / 10));
        mPresenters[mode].onNumberKeyClick(text(time % 10));
    }
}
