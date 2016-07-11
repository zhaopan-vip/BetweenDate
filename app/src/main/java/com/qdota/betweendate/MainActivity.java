package com.qdota.betweendate;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    Button mButton1;
    Button mButton2;
    TextView mText1;
    TextView mText2;
    TextView mText3;
    GregorianCalendar mCalendar1;
    GregorianCalendar mCalendar2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton1 = (Button) findViewById(R.id.button1);
        mButton2 = (Button) findViewById(R.id.button2);
        mText1 = (TextView) findViewById(R.id.text1);
        mText2 = (TextView) findViewById(R.id.text2);
        mText3 = (TextView) findViewById(R.id.text3);

        mCalendar1 = new GregorianCalendar();
        mCalendar2 = new GregorianCalendar();

        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v, mCalendar1, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        setCalendar(mCalendar1, year, monthOfYear, dayOfMonth);
                        updateText(mText1, mCalendar1);
                        setBetweenResult(mText3, mCalendar1, mCalendar2);
                    }
                });
            }
        });
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v, mCalendar2, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        setCalendar(mCalendar2, year, monthOfYear, dayOfMonth);
                        updateText(mText2, mCalendar2);
                        setBetweenResult(mText3, mCalendar1, mCalendar2);
                    }
                });
            }
        });
        updateText(mText1, mCalendar1);
        updateText(mText2, mCalendar2);
        setBetweenResult(mText3, mCalendar1, mCalendar2);
    }

    public static void setCalendar(Calendar c, int year, int monthOfYear, int dayOfMonth) {
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, monthOfYear);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
    }

    public static void updateText(TextView textView, GregorianCalendar c) {
        textView.setText(DateFormat.getDateFormat(textView.getContext()).format(c.getTime()));
    }

    public static void setBetweenResult(TextView textView, GregorianCalendar c1, GregorianCalendar c2) {
        Utility.BetweenDate bd = Utility.calcBetweenDate(c1, c2);
        textView.setText(
                String.format(
                        textView.getContext().getString(R.string.between_date_format),
                        bd.year, bd.month, bd.day));
    }

    public static class DatePickerFragment extends DialogFragment {

        public static DatePickerFragment newInstance(GregorianCalendar c, DatePickerDialog.OnDateSetListener listener) {
            Bundle args = new Bundle();
            args.putSerializable(ARG_KEY_CALENDAR, c);

            DatePickerFragment dlg = new DatePickerFragment();
            dlg.mListener = listener;
            dlg.setArguments(args);
            return dlg;
        }
        private static final String ARG_KEY_CALENDAR = "arg_key_calendar";
        DatePickerDialog.OnDateSetListener mListener;
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = (Calendar) getArguments().getSerializable(ARG_KEY_CALENDAR);
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), mListener, year, month, day);
        }
    }

    public void showDatePickerDialog(View v, GregorianCalendar c, DatePickerDialog.OnDateSetListener listener) {
        DatePickerFragment dlg = DatePickerFragment.newInstance(c, listener);
        dlg.show(getSupportFragmentManager(), "datePicker");
    }
}
