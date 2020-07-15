package com.example.myapplication.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.model.Question;

public class QuizFragment extends Fragment {

    public static final String BUNDLE_KEY_CURRENT_INDEX = "currentIndex";
    public static final String EXTRA_KEY_IS_ANSWER_TRUE = "isAnswerTrue";

    public static final int REQUEST_CODE_CHEAT_ACTIVITY = 0;

    private Button mButtonTrue;
    private Button mButtonFalse;
    private Button mButtonNext;
    private Button mButtonPrevious;
    private Button mButtonCheat;
    private TextView mTextViewQuestion;

    private boolean mIsCheated = false;
    private int mCurrentIndex = 0;
    private Question[] mQuestionBank = {
            new Question(R.string.question_australia, false),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, true),
            new Question(R.string.question_americas, false),
            new Question(R.string.question_asia, false)
    };


    public QuizFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(BUNDLE_KEY_CURRENT_INDEX);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);

        mButtonTrue = view.findViewById(R.id.button_true);
        mButtonFalse = view.findViewById(R.id.button_false);
        mButtonNext = view.findViewById(R.id.button_next);
        mButtonPrevious = view.findViewById(R.id.button_previous);
        mButtonCheat = view.findViewById(R.id.button_cheat);
        mTextViewQuestion = view.findViewById(R.id.text_view_question);

        setClickListeners();

        updateQuestion();
        return view;
    }

    private void updateQuestion() {
        Question currentQuestion = mQuestionBank[mCurrentIndex];
        mTextViewQuestion.setText(currentQuestion.getTextResId());
    }

    private void setClickListeners() {
        mButtonTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mButtonFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (++mCurrentIndex) % mQuestionBank.length;
                updateQuestion();
                mIsCheated = false;
            }
        });

        mButtonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (--mCurrentIndex + mQuestionBank.length) % mQuestionBank.length;
                updateQuestion();
                mIsCheated = false;
            }
        });

        mButtonCheat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCheatActivity();
            }
        });
    }

    private void checkAnswer(boolean userPressed) {
        boolean isAnswerTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        if (mIsCheated) {
            Toast.makeText(getActivity(), R.string.toast_judgment, Toast.LENGTH_SHORT).show();
        } else {
            if (userPressed == isAnswerTrue) {
                Toast.makeText(getActivity(), R.string.toast_correct, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), R.string.toast_incorrect, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void startCheatActivity() {
        boolean isAnswerTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        Intent intent = new Intent(getActivity(), CheatActivity.class);

        //2. send request parameters to child
        intent.putExtra(EXTRA_KEY_IS_ANSWER_TRUE, isAnswerTrue);

        //1. create parent child relations between quiz and cheat activity
        startActivityForResult(intent, REQUEST_CODE_CHEAT_ACTIVITY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK || data == null)
            return;

        //result backed from cheate
        if (requestCode == REQUEST_CODE_CHEAT_ACTIVITY) {
            mIsCheated = data.getBooleanExtra(CheatFragment.EXTRA_IS_CHEAT, false);
        }
    }
}