package com.example.myapplication.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;


public class CheatFragment extends Fragment {
    public static final String EXTRA_IS_CHEAT = "isCheated";

    private TextView mTextViewAnswer;
    private Button mButtonCheat;
    private Button mButtonBack;

    private boolean mIsAnswerTrue;


    public CheatFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getActivity().getIntent();
        //initialization
        mIsAnswerTrue = intent.getBooleanExtra(QuizFragment.EXTRA_KEY_IS_ANSWER_TRUE, false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cheat, container, false);
        mTextViewAnswer = view.findViewById(R.id.text_view_show_answer);
        mButtonCheat = view.findViewById(R.id.button_show_cheat);
        mButtonBack = view.findViewById(R.id.button_back);
        setClickListeners();
        return view;
    }

    private void setClickListeners() {
        mButtonCheat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsAnswerTrue) {
                    mTextViewAnswer.setText(R.string.button_true);
                } else {
                    mTextViewAnswer.setText(R.string.button_false);
                }

                saveResult(true);
            }
        });

        mButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    private void saveResult(boolean isCheated) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_IS_CHEAT, isCheated);

        //3. save result in activity
        getActivity().setResult(Activity.RESULT_OK, intent);

        //4. os will automatically send result back to parent.
    }
}