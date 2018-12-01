package com.example.ss4.hackathons;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import cz.msebera.android.httpclient.Header;

public class QuizTest extends AppCompatActivity {

    TextView question1, question2, question3, question4, question5, timerValue;

    LinearLayout question1LinearLayoutForFillInTheBlanks, question1LinearLayoutForTrueOrFalse, question1LinearLayoutForImageWithTrueOrFalse,
            question1LinearLayoutForImageWithFillInTheBlanks, question1LinearLayoutForImageWithMultipleChoice, question1LinearLayoutForMultipleChoice;

    LinearLayout question2LinearLayoutForFillInTheBlanks, question2LinearLayoutForTrueOrFalse, question2LinearLayoutForImageWithTrueOrFalse,
            question2LinearLayoutForImageWithFillInTheBlanks, question2LinearLayoutForImageWithMultipleChoice, question2LinearLayoutForMultipleChoice;

    LinearLayout question3LinearLayoutForFillInTheBlanks, question3LinearLayoutForTrueOrFalse, question3LinearLayoutForImageWithTrueOrFalse,
            question3LinearLayoutForImageWithFillInTheBlanks, question3LinearLayoutForImageWithMultipleChoice, question3LinearLayoutForMultipleChoice;

    LinearLayout question4LinearLayoutForFillInTheBlanks, question4LinearLayoutForTrueOrFalse, question4LinearLayoutForImageWithTrueOrFalse,
            question4LinearLayoutForImageWithFillInTheBlanks, question4LinearLayoutForImageWithMultipleChoice, question4LinearLayoutForMultipleChoice;

    LinearLayout question5LinearLayoutForFillInTheBlanks, question5LinearLayoutForTrueOrFalse, question5LinearLayoutForImageWithTrueOrFalse,
            question5LinearLayoutForImageWithFillInTheBlanks, question5LinearLayoutForImageWithMultipleChoice, question5LinearLayoutForMultipleChoice;

    TextView question1ForMultipleChoice, question1ForImageWithMultipleChoice, question1ForImageWithFillInTheBlanks, question1ForImageWithTrueOrFalse,
            question1ForFillInTheBlanks, question1ForTrueOrFalse;

    ImageView question1ImageWithMultipleChoice, question1ImageWithFillInTheBlanks, question1ImageWithTrueOrFalse;

    RadioButton question1ForMultipleChoiceAns1, question1ForMultipleChoiceAns2, question1ForMultipleChoiceAns3, question1ForMultipleChoiceAns4,
            question1ForImageWithMultipleChoiceAns1, question1ForImageWithMultipleChoiceAns2, question1ForImageWithMultipleChoiceAns3, question1ForImageWithMultipleChoiceAns4,
            question1ForImageWithTrueOrFalseAns1, question1ForImageWithTrueOrFalseAns2,
            question1ForFillInTheBlanksAns1, question1ForFillInTheBlanksAns2;

    EditText question1ImageWithFillInTheBlanksAnswer, question1ForFillInTheBlanksAnswer;

    TextView question2ForMultipleChoice, question2ForImageWithMultipleChoice, question2ForImageWithFillInTheBlanks, question2ForImageWithTrueOrFalse,
            question2ForFillInTheBlanks, question2ForTrueOrFalse;

    ImageView question2ImageWithMultipleChoice, question2ImageWithFillInTheBlanks, question2ImageWithTrueOrFalse;

    RadioButton question2ForMultipleChoiceAns1, question2ForMultipleChoiceAns2, question2ForMultipleChoiceAns3, question2ForMultipleChoiceAns4,
            question2ForImageWithMultipleChoiceAns1, question2ForImageWithMultipleChoiceAns2, question2ForImageWithMultipleChoiceAns3, question2ForImageWithMultipleChoiceAns4,
            question2ForImageWithTrueOrFalseAns1, question2ForImageWithTrueOrFalseAns2,
            question2ForFillInTheBlanksAns1, question2ForFillInTheBlanksAns2;

    EditText question2ImageWithFillInTheBlanksAnswer, question2ForFillInTheBlanksAnswer;

    TextView question3ForMultipleChoice, question3ForImageWithMultipleChoice, question3ForImageWithFillInTheBlanks, question3ForImageWithTrueOrFalse,
            question3ForFillInTheBlanks, question3ForTrueOrFalse;

    ImageView question3ImageWithMultipleChoice, question3ImageWithFillInTheBlanks, question3ImageWithTrueOrFalse;

    RadioButton question3ForMultipleChoiceAns1, question3ForMultipleChoiceAns2, question3ForMultipleChoiceAns3, question3ForMultipleChoiceAns4,
            question3ForImageWithMultipleChoiceAns1, question3ForImageWithMultipleChoiceAns2, question3ForImageWithMultipleChoiceAns3, question3ForImageWithMultipleChoiceAns4,
            question3ForImageWithTrueOrFalseAns1, question3ForImageWithTrueOrFalseAns2,
            question3ForFillInTheBlanksAns1, question3ForFillInTheBlanksAns2;

    EditText question3ImageWithFillInTheBlanksAnswer, question3ForFillInTheBlanksAnswer;

    TextView question4ForMultipleChoice, question4ForImageWithMultipleChoice, question4ForImageWithFillInTheBlanks, question4ForImageWithTrueOrFalse,
            question4ForFillInTheBlanks, question4ForTrueOrFalse;

    ImageView question4ImageWithMultipleChoice, question4ImageWithFillInTheBlanks, question4ImageWithTrueOrFalse;

    RadioButton question4ForMultipleChoiceAns1, question4ForMultipleChoiceAns2, question4ForMultipleChoiceAns3, question4ForMultipleChoiceAns4,
            question4ForImageWithMultipleChoiceAns1, question4ForImageWithMultipleChoiceAns2, question4ForImageWithMultipleChoiceAns3, question4ForImageWithMultipleChoiceAns4,
            question4ForImageWithTrueOrFalseAns1, question4ForImageWithTrueOrFalseAns2,
            question4ForFillInTheBlanksAns1, question4ForFillInTheBlanksAns2;

    EditText question4ImageWithFillInTheBlanksAnswer, question4ForFillInTheBlanksAnswer;

    TextView question5ForMultipleChoice, question5ForImageWithMultipleChoice, question5ForImageWithFillInTheBlanks, question5ForImageWithTrueOrFalse,
            question5ForFillInTheBlanks, question5ForTrueOrFalse;

    ImageView question5ImageWithMultipleChoice, question5ImageWithFillInTheBlanks, question5ImageWithTrueOrFalse;

    RadioButton question5ForMultipleChoiceAns1, question5ForMultipleChoiceAns2, question5ForMultipleChoiceAns3, question5ForMultipleChoiceAns4,
            question5ForImageWithMultipleChoiceAns1, question5ForImageWithMultipleChoiceAns2, question5ForImageWithMultipleChoiceAns3, question5ForImageWithMultipleChoiceAns4,
            question5ForImageWithTrueOrFalseAns1, question5ForImageWithTrueOrFalseAns2,
            question5ForFillInTheBlanksAns1, question5ForFillInTheBlanksAns2;

    EditText question5ImageWithFillInTheBlanksAnswer, question5ForFillInTheBlanksAnswer;

    String questionNo1, questionNo2, questionNo3, questionNo4, questionNo5, hackathonId, email, name, collegeId;
    String ansForQuestionNo1, ansForQuestionNo2, ansForQuestionNo3, ansForQuestionNo4, ansForQuestionNo5;

    String questionNo1Type, questionNo2Type, questionNo3Type, questionNo4Type, questionNo5Type;

    TextView submitButton;

    private long startTime = 0L;
    private Handler customHandler = new Handler();
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;

    Boolean isInternetPresent = false;

    String image_url = "http://101.53.139.52:8080/Incubation20.0/hackathonimages/";

    ImageView img;
    Bitmap bitmap;
    ProgressDialog pDialog;

    Bundle extras;

    final int DEFAULT_TIMEOUT = 60 * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_test);

        timerValue=(TextView)findViewById(R.id.quizTimeLeft);

        question1LinearLayoutForFillInTheBlanks=(LinearLayout)findViewById(R.id.Question1LinearLayoutForFillInTheBlanks);
        question1LinearLayoutForTrueOrFalse=(LinearLayout)findViewById(R.id.Question1LinearLayoutForTrueOrFalse);
        question1LinearLayoutForImageWithTrueOrFalse=(LinearLayout)findViewById(R.id.Question1LinearLayoutForImageWithTrueOrFalse);
        question1LinearLayoutForImageWithFillInTheBlanks=(LinearLayout)findViewById(R.id.Question1LinearLayoutForImageWithFillInTheBlanks);
        question1LinearLayoutForImageWithMultipleChoice=(LinearLayout)findViewById(R.id.Question1LinearLayoutForImageWithMultipleChoice);
        question1LinearLayoutForMultipleChoice=(LinearLayout)findViewById(R.id.Question1LinearLayoutForMultipleChoice);

        question2LinearLayoutForFillInTheBlanks=(LinearLayout)findViewById(R.id.Question2LinearLayoutForFillInTheBlanks);
        question2LinearLayoutForTrueOrFalse=(LinearLayout)findViewById(R.id.Question2LinearLayoutForTrueOrFalse);
        question2LinearLayoutForImageWithTrueOrFalse=(LinearLayout)findViewById(R.id.Question2LinearLayoutForImageWithTrueOrFalse);
        question2LinearLayoutForImageWithFillInTheBlanks=(LinearLayout)findViewById(R.id.Question2LinearLayoutForImageWithFillInTheBlanks);
        question2LinearLayoutForImageWithMultipleChoice=(LinearLayout)findViewById(R.id.Question2LinearLayoutForImageWithMultipleChoice);
        question2LinearLayoutForMultipleChoice=(LinearLayout)findViewById(R.id.Question2LinearLayoutForMultipleChoice);

        question3LinearLayoutForFillInTheBlanks=(LinearLayout)findViewById(R.id.Question3LinearLayoutForFillInTheBlanks);
        question3LinearLayoutForTrueOrFalse=(LinearLayout)findViewById(R.id.Question3LinearLayoutForTrueOrFalse);
        question3LinearLayoutForImageWithTrueOrFalse=(LinearLayout)findViewById(R.id.Question3LinearLayoutForImageWithTrueOrFalse);
        question3LinearLayoutForImageWithFillInTheBlanks=(LinearLayout)findViewById(R.id.Question3LinearLayoutForImageWithFillInTheBlanks);
        question3LinearLayoutForImageWithMultipleChoice=(LinearLayout)findViewById(R.id.Question3LinearLayoutForImageWithMultipleChoice);
        question3LinearLayoutForMultipleChoice=(LinearLayout)findViewById(R.id.Question3LinearLayoutForMultipleChoice);

        question4LinearLayoutForFillInTheBlanks=(LinearLayout)findViewById(R.id.Question4LinearLayoutForFillInTheBlanks);
        question4LinearLayoutForTrueOrFalse=(LinearLayout)findViewById(R.id.Question4LinearLayoutForTrueOrFalse);
        question4LinearLayoutForImageWithTrueOrFalse=(LinearLayout)findViewById(R.id.Question4LinearLayoutForImageWithTrueOrFalse);
        question4LinearLayoutForImageWithFillInTheBlanks=(LinearLayout)findViewById(R.id.Question4LinearLayoutForImageWithFillInTheBlanks);
        question4LinearLayoutForImageWithMultipleChoice=(LinearLayout)findViewById(R.id.Question4LinearLayoutForImageWithMultipleChoice);
        question4LinearLayoutForMultipleChoice=(LinearLayout)findViewById(R.id.Question4LinearLayoutForMultipleChoice);

        question5LinearLayoutForFillInTheBlanks=(LinearLayout)findViewById(R.id.Question5LinearLayoutForFillInTheBlanks);
        question5LinearLayoutForTrueOrFalse=(LinearLayout)findViewById(R.id.Question5LinearLayoutForTrueOrFalse);
        question5LinearLayoutForImageWithTrueOrFalse=(LinearLayout)findViewById(R.id.Question5LinearLayoutForImageWithTrueOrFalse);
        question5LinearLayoutForImageWithFillInTheBlanks=(LinearLayout)findViewById(R.id.Question5LinearLayoutForImageWithFillInTheBlanks);
        question5LinearLayoutForImageWithMultipleChoice=(LinearLayout)findViewById(R.id.Question5LinearLayoutForImageWithMultipleChoice);
        question5LinearLayoutForMultipleChoice=(LinearLayout)findViewById(R.id.Question5LinearLayoutForMultipleChoice);


        question1ForMultipleChoice=(TextView) findViewById(R.id.txtquestion1ForMultipleChoice);
        question1ForImageWithMultipleChoice=(TextView) findViewById(R.id.txtAnswerQuestion1ForImageWithMultipleChoice);
        question1ForImageWithFillInTheBlanks=(TextView) findViewById(R.id.txtQuestion1ForImageWithFillInTheBlanks);
        question1ForImageWithTrueOrFalse=(TextView) findViewById(R.id.txtAnswerQuestion1ForImageWithTrueOrFalse);
        question1ForFillInTheBlanks=(TextView) findViewById(R.id.txtQuestion1ForFillInTheBlanks);
        question1ForTrueOrFalse=(TextView) findViewById(R.id.txtquestion1ForTrueOrFalse);

        question2ForMultipleChoice=(TextView) findViewById(R.id.txtquestion2ForMultipleChoice);
        question2ForImageWithMultipleChoice=(TextView) findViewById(R.id.txtAnswerQuestion2ForImageWithMultipleChoice);
        question2ForImageWithFillInTheBlanks=(TextView) findViewById(R.id.txtQuestion2ForImageWithFillInTheBlanks);
        question2ForImageWithTrueOrFalse=(TextView) findViewById(R.id.txtAnswerQuestion2ForImageWithTrueOrFalse);
        question2ForFillInTheBlanks=(TextView) findViewById(R.id.txtQuestion2ForFillInTheBlanks);
        question2ForTrueOrFalse=(TextView) findViewById(R.id.txtquestion2ForTrueOrFalse);

        question3ForMultipleChoice=(TextView) findViewById(R.id.txtquestion3ForMultipleChoice);
        question3ForImageWithMultipleChoice=(TextView) findViewById(R.id.txtAnswerQuestion3ForImageWithMultipleChoice);
        question3ForImageWithFillInTheBlanks=(TextView) findViewById(R.id.txtQuestion3ForImageWithFillInTheBlanks);
        question3ForImageWithTrueOrFalse=(TextView) findViewById(R.id.txtAnswerQuestion3ForImageWithTrueOrFalse);
        question3ForFillInTheBlanks=(TextView) findViewById(R.id.txtQuestion3ForFillInTheBlanks);
        question3ForTrueOrFalse=(TextView) findViewById(R.id.txtquestion3ForTrueOrFalse);

        question4ForMultipleChoice=(TextView) findViewById(R.id.txtquestion4ForMultipleChoice);
        question4ForImageWithMultipleChoice=(TextView) findViewById(R.id.txtAnswerQuestion4ForImageWithMultipleChoice);
        question4ForImageWithFillInTheBlanks=(TextView) findViewById(R.id.txtQuestion4ForImageWithFillInTheBlanks);
        question4ForImageWithTrueOrFalse=(TextView) findViewById(R.id.txtAnswerQuestion4ForImageWithTrueOrFalse);
        question4ForFillInTheBlanks=(TextView) findViewById(R.id.txtQuestion4ForFillInTheBlanks);
        question4ForTrueOrFalse=(TextView) findViewById(R.id.txtquestion4ForTrueOrFalse);

        question5ForMultipleChoice=(TextView) findViewById(R.id.txtquestion5ForMultipleChoice);
        question5ForImageWithMultipleChoice=(TextView) findViewById(R.id.txtAnswerQuestion5ForImageWithMultipleChoice);
        question5ForImageWithFillInTheBlanks=(TextView) findViewById(R.id.txtQuestion5ForImageWithFillInTheBlanks);
        question5ForImageWithTrueOrFalse=(TextView) findViewById(R.id.txtAnswerQuestion5ForImageWithTrueOrFalse);
        question5ForFillInTheBlanks=(TextView) findViewById(R.id.txtQuestion5ForFillInTheBlanks);
        question5ForTrueOrFalse=(TextView) findViewById(R.id.txtquestion5ForTrueOrFalse);


        question1ImageWithMultipleChoice=(ImageView)findViewById(R.id.txtquestion1ForImageWithMultipleChoice);
        question1ImageWithFillInTheBlanks=(ImageView)findViewById(R.id.txtquestion1ForImageWithFillInTheBlanks);
        question1ImageWithTrueOrFalse=(ImageView)findViewById(R.id.txtquestion1ForImageWithTrueOrFalse);

        question2ImageWithMultipleChoice=(ImageView)findViewById(R.id.txtquestion2ForImageWithMultipleChoice);
        question2ImageWithFillInTheBlanks=(ImageView)findViewById(R.id.txtquestion2ForImageWithFillInTheBlanks);
        question2ImageWithTrueOrFalse=(ImageView)findViewById(R.id.txtquestion2ForImageWithTrueOrFalse);

        question3ImageWithMultipleChoice=(ImageView)findViewById(R.id.txtquestion3ForImageWithMultipleChoice);
        question3ImageWithFillInTheBlanks=(ImageView)findViewById(R.id.txtquestion3ForImageWithFillInTheBlanks);
        question3ImageWithTrueOrFalse=(ImageView)findViewById(R.id.txtquestion3ForImageWithTrueOrFalse);

        question4ImageWithMultipleChoice=(ImageView)findViewById(R.id.txtquestion4ForImageWithMultipleChoice);
        question4ImageWithFillInTheBlanks=(ImageView)findViewById(R.id.txtquestion4ForImageWithFillInTheBlanks);
        question4ImageWithTrueOrFalse=(ImageView)findViewById(R.id.txtquestion4ForImageWithTrueOrFalse);

        question5ImageWithMultipleChoice=(ImageView)findViewById(R.id.txtquestion5ForImageWithMultipleChoice);
        question5ImageWithFillInTheBlanks=(ImageView)findViewById(R.id.txtquestion5ForImageWithFillInTheBlanks);
        question5ImageWithTrueOrFalse=(ImageView)findViewById(R.id.txtquestion5ForImageWithTrueOrFalse);

        question1ForMultipleChoiceAns1=(RadioButton) findViewById(R.id.ans1ForQuizQuestion1ForMultipleChoice);
        question1ForMultipleChoiceAns2=(RadioButton) findViewById(R.id.ans2ForQuizQuestion1ForMultipleChoice);
        question1ForMultipleChoiceAns3=(RadioButton) findViewById(R.id.ans3ForQuizQuestion1ForMultipleChoice);
        question1ForMultipleChoiceAns4=(RadioButton) findViewById(R.id.ans4ForQuizQuestion1ForMultipleChoice);

        question1ForImageWithMultipleChoiceAns1=(RadioButton) findViewById(R.id.ans1ForQuizQuestion1ForImageWithMultipleChoice);
        question1ForImageWithMultipleChoiceAns2=(RadioButton) findViewById(R.id.ans2ForQuizQuestion1ForImageWithMultipleChoice);
        question1ForImageWithMultipleChoiceAns3=(RadioButton) findViewById(R.id.ans3ForQuizQuestion1ForImageWithMultipleChoice);
        question1ForImageWithMultipleChoiceAns4=(RadioButton) findViewById(R.id.ans4ForQuizQuestion1ForImageWithMultipleChoice);

        question1ForImageWithTrueOrFalseAns1=(RadioButton) findViewById(R.id.ans1ForQuizQuestion1ForImageWithTrueOrFalse);
        question1ForImageWithTrueOrFalseAns2=(RadioButton) findViewById(R.id.ans2ForQuizQuestion1ForImageWithTrueOrFalse);

        question1ForFillInTheBlanksAns1=(RadioButton) findViewById(R.id.ans1ForQuizQuestion1ForTrueOrFalse);
        question1ForFillInTheBlanksAns2=(RadioButton) findViewById(R.id.ans2ForQuizQuestion1ForTrueOrFalse);


        question2ForMultipleChoiceAns1=(RadioButton) findViewById(R.id.ans1ForQuizQuestion2ForMultipleChoice);
        question2ForMultipleChoiceAns2=(RadioButton) findViewById(R.id.ans2ForQuizQuestion2ForMultipleChoice);
        question2ForMultipleChoiceAns3=(RadioButton) findViewById(R.id.ans3ForQuizQuestion2ForMultipleChoice);
        question2ForMultipleChoiceAns4=(RadioButton) findViewById(R.id.ans4ForQuizQuestion2ForMultipleChoice);

        question2ForImageWithMultipleChoiceAns1=(RadioButton) findViewById(R.id.ans1ForQuizQuestion2ForImageWithMultipleChoice);
        question2ForImageWithMultipleChoiceAns2=(RadioButton) findViewById(R.id.ans2ForQuizQuestion2ForImageWithMultipleChoice);
        question2ForImageWithMultipleChoiceAns3=(RadioButton) findViewById(R.id.ans3ForQuizQuestion2ForImageWithMultipleChoice);
        question2ForImageWithMultipleChoiceAns4=(RadioButton) findViewById(R.id.ans4ForQuizQuestion2ForImageWithMultipleChoice);

        question2ForImageWithTrueOrFalseAns1=(RadioButton) findViewById(R.id.ans1ForQuizQuestion2ForImageWithTrueOrFalse);
        question2ForImageWithTrueOrFalseAns2=(RadioButton) findViewById(R.id.ans2ForQuizQuestion2ForImageWithTrueOrFalse);

        question2ForFillInTheBlanksAns1=(RadioButton) findViewById(R.id.ans1ForQuizQuestion2ForTrueOrFalse);
        question2ForFillInTheBlanksAns2=(RadioButton) findViewById(R.id.ans2ForQuizQuestion2ForTrueOrFalse);


        question3ForMultipleChoiceAns1=(RadioButton) findViewById(R.id.ans1ForQuizQuestion3ForMultipleChoice);
        question3ForMultipleChoiceAns2=(RadioButton) findViewById(R.id.ans2ForQuizQuestion3ForMultipleChoice);
        question3ForMultipleChoiceAns3=(RadioButton) findViewById(R.id.ans3ForQuizQuestion3ForMultipleChoice);
        question3ForMultipleChoiceAns4=(RadioButton) findViewById(R.id.ans4ForQuizQuestion3ForMultipleChoice);

        question3ForImageWithMultipleChoiceAns1=(RadioButton) findViewById(R.id.ans1ForQuizQuestion3ForImageWithMultipleChoice);
        question3ForImageWithMultipleChoiceAns2=(RadioButton) findViewById(R.id.ans2ForQuizQuestion3ForImageWithMultipleChoice);
        question3ForImageWithMultipleChoiceAns3=(RadioButton) findViewById(R.id.ans3ForQuizQuestion3ForImageWithMultipleChoice);
        question3ForImageWithMultipleChoiceAns4=(RadioButton) findViewById(R.id.ans4ForQuizQuestion3ForImageWithMultipleChoice);

        question3ForImageWithTrueOrFalseAns1=(RadioButton) findViewById(R.id.ans1ForQuizQuestion3ForImageWithTrueOrFalse);
        question3ForImageWithTrueOrFalseAns2=(RadioButton) findViewById(R.id.ans2ForQuizQuestion3ForImageWithTrueOrFalse);

        question3ForFillInTheBlanksAns1=(RadioButton) findViewById(R.id.ans1ForQuizQuestion3ForTrueOrFalse);
        question3ForFillInTheBlanksAns2=(RadioButton) findViewById(R.id.ans2ForQuizQuestion3ForTrueOrFalse);



        question4ForMultipleChoiceAns1=(RadioButton) findViewById(R.id.ans1ForQuizQuestion4ForMultipleChoice);
        question4ForMultipleChoiceAns2=(RadioButton) findViewById(R.id.ans2ForQuizQuestion4ForMultipleChoice);
        question4ForMultipleChoiceAns3=(RadioButton) findViewById(R.id.ans3ForQuizQuestion4ForMultipleChoice);
        question4ForMultipleChoiceAns4=(RadioButton) findViewById(R.id.ans4ForQuizQuestion4ForMultipleChoice);

        question4ForImageWithMultipleChoiceAns1=(RadioButton) findViewById(R.id.ans1ForQuizQuestion4ForImageWithMultipleChoice);
        question4ForImageWithMultipleChoiceAns2=(RadioButton) findViewById(R.id.ans2ForQuizQuestion4ForImageWithMultipleChoice);
        question4ForImageWithMultipleChoiceAns3=(RadioButton) findViewById(R.id.ans3ForQuizQuestion4ForImageWithMultipleChoice);
        question4ForImageWithMultipleChoiceAns4=(RadioButton) findViewById(R.id.ans4ForQuizQuestion4ForImageWithMultipleChoice);

        question4ForImageWithTrueOrFalseAns1=(RadioButton) findViewById(R.id.ans1ForQuizQuestion4ForImageWithTrueOrFalse);
        question4ForImageWithTrueOrFalseAns2=(RadioButton) findViewById(R.id.ans2ForQuizQuestion4ForImageWithTrueOrFalse);

        question4ForFillInTheBlanksAns1=(RadioButton) findViewById(R.id.ans1ForQuizQuestion4ForTrueOrFalse);
        question4ForFillInTheBlanksAns2=(RadioButton) findViewById(R.id.ans2ForQuizQuestion4ForTrueOrFalse);


        question5ForMultipleChoiceAns1=(RadioButton) findViewById(R.id.ans1ForQuizQuestion5ForMultipleChoice);
        question5ForMultipleChoiceAns2=(RadioButton) findViewById(R.id.ans2ForQuizQuestion5ForMultipleChoice);
        question5ForMultipleChoiceAns3=(RadioButton) findViewById(R.id.ans3ForQuizQuestion5ForMultipleChoice);
        question5ForMultipleChoiceAns4=(RadioButton) findViewById(R.id.ans4ForQuizQuestion5ForMultipleChoice);

        question5ForImageWithMultipleChoiceAns1=(RadioButton) findViewById(R.id.ans1ForQuizQuestion5ForImageWithMultipleChoice);
        question5ForImageWithMultipleChoiceAns2=(RadioButton) findViewById(R.id.ans2ForQuizQuestion5ForImageWithMultipleChoice);
        question5ForImageWithMultipleChoiceAns3=(RadioButton) findViewById(R.id.ans3ForQuizQuestion5ForImageWithMultipleChoice);
        question5ForImageWithMultipleChoiceAns4=(RadioButton) findViewById(R.id.ans4ForQuizQuestion5ForImageWithMultipleChoice);

        question5ForImageWithTrueOrFalseAns1=(RadioButton) findViewById(R.id.ans1ForQuizQuestion5ForImageWithTrueOrFalse);
        question5ForImageWithTrueOrFalseAns2=(RadioButton) findViewById(R.id.ans2ForQuizQuestion5ForImageWithTrueOrFalse);

        question5ForFillInTheBlanksAns1=(RadioButton) findViewById(R.id.ans1ForQuizQuestion5ForTrueOrFalse);
        question5ForFillInTheBlanksAns2=(RadioButton) findViewById(R.id.ans2ForQuizQuestion5ForTrueOrFalse);


        question1ImageWithFillInTheBlanksAnswer=(EditText) findViewById(R.id.txtAnswerForQuestion1ForImageWithFillInTheBlanks);
        question1ForFillInTheBlanksAnswer=(EditText) findViewById(R.id.txtAnswerForQuestion1ForFillInTheBlanks);

        question2ImageWithFillInTheBlanksAnswer=(EditText) findViewById(R.id.txtAnswerForQuestion2ForImageWithFillInTheBlanks);
        question2ForFillInTheBlanksAnswer=(EditText) findViewById(R.id.txtAnswerForQuestion2ForFillInTheBlanks);

        question3ImageWithFillInTheBlanksAnswer=(EditText) findViewById(R.id.txtAnswerForQuestion3ForImageWithFillInTheBlanks);
        question3ForFillInTheBlanksAnswer=(EditText) findViewById(R.id.txtAnswerForQuestion3ForFillInTheBlanks);

        question4ImageWithFillInTheBlanksAnswer=(EditText) findViewById(R.id.txtAnswerForQuestion4ForImageWithFillInTheBlanks);
        question4ForFillInTheBlanksAnswer=(EditText) findViewById(R.id.txtAnswerForQuestion4ForFillInTheBlanks);

        question5ImageWithFillInTheBlanksAnswer=(EditText) findViewById(R.id.txtAnswerForQuestion5ForImageWithFillInTheBlanks);
        question5ForFillInTheBlanksAnswer=(EditText) findViewById(R.id.txtAnswerForQuestion5ForFillInTheBlanks);

        submitButton=(TextView)findViewById(R.id.loginTextViewContinue);

        SharedPreferences sharedpreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        //Getting value from session
        name=sharedpreferences.getString("name", "string value");
        email=sharedpreferences.getString("email", "string value");
        collegeId=sharedpreferences.getString("collegeId", "string value");

        extras = getIntent().getExtras();
        hackathonId=extras.getString("hackathonId");

        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent=cd.checkNetwork();
        if(isInternetPresent) {
            pDialog = new ProgressDialog(QuizTest.this);
            pDialog.setMessage("Loading Question ....");
            pDialog.show();

            RequestParams params = new RequestParams();
            params.put("hackathonId", hackathonId);
            getQuestions(params);
        }
        else{
            showAlertDialog(QuizTest.this, "No Internet Connection",
                    "You don't have internet connection.", false);
        }


    }

    public void getQuestions(RequestParams params) {

        // Show Progress Dialog

        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(DEFAULT_TIMEOUT);
        client.get("http://incubator.shinelogics.com/getQuestionAndAnswer.html", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    // JSON Object
                    JSONArray jArray = new JSONArray(new String(responseBody));


                    if (jArray != null) {
                        for (int i=0;i<jArray.length();i++){
                            JSONObject jsonObj = jArray.getJSONObject(i);
                            System.out.println("Question :"+jArray.getJSONObject(i));
                            if(i==0) {
                                questionNo1=jsonObj.getString("id");
                                questionNo1Type=jsonObj.getString("questionType");
                                if (jsonObj.getString("questionType") != null && jsonObj.getString("questionType").equalsIgnoreCase("TextWithMultipleChoice")) {

                                    question1LinearLayoutForMultipleChoice.setVisibility(View.VISIBLE);
                                    question1ForMultipleChoice.setText(jsonObj.getString("question"));

                                    question1ForMultipleChoiceAns1.setText(jsonObj.getString("answerOption1"));
                                    question1ForMultipleChoiceAns2.setText(jsonObj.getString("answerOption2"));
                                    question1ForMultipleChoiceAns3.setText(jsonObj.getString("answerOption3"));
                                    question1ForMultipleChoiceAns4.setText(jsonObj.getString("answerOption4"));


                                } else if (jsonObj.getString("questionType") != null && jsonObj.getString("questionType").equalsIgnoreCase("ImageWithMultipleChoice")) {

                                    question1LinearLayoutForImageWithMultipleChoice.setVisibility(View.VISIBLE);
                                    question1ForImageWithMultipleChoice.setText(jsonObj.getString("question"));

                                    ImageFromURL url=new ImageFromURL(jsonObj.getString("imageCaption"), question1ImageWithMultipleChoice);

                                    question1ForImageWithMultipleChoiceAns1.setText(jsonObj.getString("answerOption1"));
                                    question1ForImageWithMultipleChoiceAns2.setText(jsonObj.getString("answerOption2"));
                                    question1ForImageWithMultipleChoiceAns3.setText(jsonObj.getString("answerOption3"));
                                    question1ForImageWithMultipleChoiceAns4.setText(jsonObj.getString("answerOption4"));

                                } else if (jsonObj.getString("questionType") != null && jsonObj.getString("questionType").equalsIgnoreCase("ImageWithFillInTheBlanks")) {

                                    question1LinearLayoutForImageWithFillInTheBlanks.setVisibility(View.VISIBLE);
                                    question1ForImageWithFillInTheBlanks.setText(jsonObj.getString("question"));

                                    ImageFromURL url=new ImageFromURL(jsonObj.getString("imageCaption"), question1ImageWithFillInTheBlanks);



                                } else if (jsonObj.getString("questionType") != null && jsonObj.getString("questionType").equalsIgnoreCase("ImageWithTrueOrFalse")) {

                                    question1LinearLayoutForImageWithTrueOrFalse.setVisibility(View.VISIBLE);
                                    question1ForImageWithTrueOrFalse.setText(jsonObj.getString("question"));

                                    ImageFromURL url=new ImageFromURL(jsonObj.getString("imageCaption"), question1ImageWithTrueOrFalse);


                                } else if (jsonObj.getString("questionType") != null && jsonObj.getString("questionType").equalsIgnoreCase("TextWithFillInTheBlanks")) {

                                    question1LinearLayoutForFillInTheBlanks.setVisibility(View.VISIBLE);
                                    question1ForFillInTheBlanks.setText(jsonObj.getString("question"));


                                } else if (jsonObj.getString("questionType") != null && jsonObj.getString("questionType").equalsIgnoreCase("TextWithTrueOrFalse")) {

                                    question1LinearLayoutForTrueOrFalse.setVisibility(View.VISIBLE);
                                    question1ForTrueOrFalse.setText(jsonObj.getString("question"));

                                }
                            }

                            if(i==1) {

                                questionNo2=jsonObj.getString("id");
                                questionNo2Type=jsonObj.getString("questionType");
                                if (jsonObj.getString("questionType") != null && jsonObj.getString("questionType").equalsIgnoreCase("TextWithMultipleChoice")) {

                                    question2LinearLayoutForMultipleChoice.setVisibility(View.VISIBLE);
                                    question2ForMultipleChoice.setText(jsonObj.getString("question"));

                                    question2ForMultipleChoiceAns1.setText(jsonObj.getString("answerOption1"));
                                    question2ForMultipleChoiceAns2.setText(jsonObj.getString("answerOption2"));
                                    question2ForMultipleChoiceAns3.setText(jsonObj.getString("answerOption3"));
                                    question2ForMultipleChoiceAns4.setText(jsonObj.getString("answerOption4"));


                                } else if (jsonObj.getString("questionType") != null && jsonObj.getString("questionType").equalsIgnoreCase("ImageWithMultipleChoice")) {

                                    question2LinearLayoutForImageWithMultipleChoice.setVisibility(View.VISIBLE);
                                    question2ForImageWithMultipleChoice.setText(jsonObj.getString("question"));

                                    ImageFromURL url=new ImageFromURL(jsonObj.getString("imageCaption"), question2ImageWithMultipleChoice);

                                    question2ForImageWithMultipleChoiceAns1.setText(jsonObj.getString("answerOption1"));
                                    question2ForImageWithMultipleChoiceAns2.setText(jsonObj.getString("answerOption2"));
                                    question2ForImageWithMultipleChoiceAns3.setText(jsonObj.getString("answerOption3"));
                                    question2ForImageWithMultipleChoiceAns4.setText(jsonObj.getString("answerOption4"));

                                } else if (jsonObj.getString("questionType") != null && jsonObj.getString("questionType").equalsIgnoreCase("ImageWithFillInTheBlanks")) {

                                    question2LinearLayoutForImageWithFillInTheBlanks.setVisibility(View.VISIBLE);
                                    question2ForImageWithFillInTheBlanks.setText(jsonObj.getString("question"));

                                    ImageFromURL url=new ImageFromURL(jsonObj.getString("imageCaption"), question2ImageWithFillInTheBlanks);



                                } else if (jsonObj.getString("questionType") != null && jsonObj.getString("questionType").equalsIgnoreCase("ImageWithTrueOrFalse")) {

                                    question2LinearLayoutForImageWithTrueOrFalse.setVisibility(View.VISIBLE);
                                    question2ForImageWithTrueOrFalse.setText(jsonObj.getString("question"));

                                    ImageFromURL url=new ImageFromURL(jsonObj.getString("imageCaption"), question2ImageWithTrueOrFalse);


                                } else if (jsonObj.getString("questionType") != null && jsonObj.getString("questionType").equalsIgnoreCase("TextWithFillInTheBlanks")) {

                                    question2LinearLayoutForFillInTheBlanks.setVisibility(View.VISIBLE);
                                    question2ForFillInTheBlanks.setText(jsonObj.getString("question"));


                                } else if (jsonObj.getString("questionType") != null && jsonObj.getString("questionType").equalsIgnoreCase("TextWithTrueOrFalse")) {

                                    question2LinearLayoutForTrueOrFalse.setVisibility(View.VISIBLE);
                                    question2ForTrueOrFalse.setText(jsonObj.getString("question"));

                                }
                            }

                            if(i==2) {
                                questionNo3=jsonObj.getString("id");
                                questionNo3Type=jsonObj.getString("questionType");
                                if (jsonObj.getString("questionType") != null && jsonObj.getString("questionType").equalsIgnoreCase("TextWithMultipleChoice")) {

                                    question3LinearLayoutForMultipleChoice.setVisibility(View.VISIBLE);
                                    question3ForMultipleChoice.setText(jsonObj.getString("question"));

                                    question3ForMultipleChoiceAns1.setText(jsonObj.getString("answerOption1"));
                                    question3ForMultipleChoiceAns2.setText(jsonObj.getString("answerOption2"));
                                    question3ForMultipleChoiceAns3.setText(jsonObj.getString("answerOption3"));
                                    question3ForMultipleChoiceAns4.setText(jsonObj.getString("answerOption4"));


                                } else if (jsonObj.getString("questionType") != null && jsonObj.getString("questionType").equalsIgnoreCase("ImageWithMultipleChoice")) {

                                    question3LinearLayoutForImageWithMultipleChoice.setVisibility(View.VISIBLE);
                                    question3ForImageWithMultipleChoice.setText(jsonObj.getString("question"));

                                    ImageFromURL url=new ImageFromURL(jsonObj.getString("imageCaption"), question3ImageWithMultipleChoice);

                                    question3ForImageWithMultipleChoiceAns1.setText(jsonObj.getString("answerOption1"));
                                    question3ForImageWithMultipleChoiceAns2.setText(jsonObj.getString("answerOption2"));
                                    question3ForImageWithMultipleChoiceAns3.setText(jsonObj.getString("answerOption3"));
                                    question3ForImageWithMultipleChoiceAns4.setText(jsonObj.getString("answerOption4"));

                                } else if (jsonObj.getString("questionType") != null && jsonObj.getString("questionType").equalsIgnoreCase("ImageWithFillInTheBlanks")) {

                                    question3LinearLayoutForImageWithFillInTheBlanks.setVisibility(View.VISIBLE);
                                    question3ForImageWithFillInTheBlanks.setText(jsonObj.getString("question"));

                                    ImageFromURL url=new ImageFromURL(jsonObj.getString("imageCaption"), question3ImageWithFillInTheBlanks);



                                } else if (jsonObj.getString("questionType") != null && jsonObj.getString("questionType").equalsIgnoreCase("ImageWithTrueOrFalse")) {

                                    question3LinearLayoutForImageWithTrueOrFalse.setVisibility(View.VISIBLE);
                                    question3ForImageWithTrueOrFalse.setText(jsonObj.getString("question"));

                                    ImageFromURL url=new ImageFromURL(jsonObj.getString("imageCaption"), question3ImageWithTrueOrFalse);


                                } else if (jsonObj.getString("questionType") != null && jsonObj.getString("questionType").equalsIgnoreCase("TextWithFillInTheBlanks")) {

                                    question3LinearLayoutForFillInTheBlanks.setVisibility(View.VISIBLE);
                                    question3ForFillInTheBlanks.setText(jsonObj.getString("question"));


                                } else if (jsonObj.getString("questionType") != null && jsonObj.getString("questionType").equalsIgnoreCase("TextWithTrueOrFalse")) {

                                    question3LinearLayoutForTrueOrFalse.setVisibility(View.VISIBLE);
                                    question3ForTrueOrFalse.setText(jsonObj.getString("question"));

                                }
                            }

                            if(i==3) {

                                questionNo4=jsonObj.getString("id");
                                questionNo4Type=jsonObj.getString("questionType");

                                if (jsonObj.getString("questionType") != null && jsonObj.getString("questionType").equalsIgnoreCase("TextWithMultipleChoice")) {

                                    question4LinearLayoutForMultipleChoice.setVisibility(View.VISIBLE);
                                    question4ForMultipleChoice.setText(jsonObj.getString("question"));

                                    question4ForMultipleChoiceAns1.setText(jsonObj.getString("answerOption1"));
                                    question4ForMultipleChoiceAns2.setText(jsonObj.getString("answerOption2"));
                                    question4ForMultipleChoiceAns3.setText(jsonObj.getString("answerOption3"));
                                    question4ForMultipleChoiceAns4.setText(jsonObj.getString("answerOption4"));


                                } else if (jsonObj.getString("questionType") != null && jsonObj.getString("questionType").equalsIgnoreCase("ImageWithMultipleChoice")) {

                                    question4LinearLayoutForImageWithMultipleChoice.setVisibility(View.VISIBLE);
                                    question4ForImageWithMultipleChoice.setText(jsonObj.getString("question"));

                                    ImageFromURL url=new ImageFromURL(jsonObj.getString("imageCaption"), question4ImageWithMultipleChoice);

                                    question4ForImageWithMultipleChoiceAns1.setText(jsonObj.getString("answerOption1"));
                                    question4ForImageWithMultipleChoiceAns2.setText(jsonObj.getString("answerOption2"));
                                    question4ForImageWithMultipleChoiceAns3.setText(jsonObj.getString("answerOption3"));
                                    question4ForImageWithMultipleChoiceAns4.setText(jsonObj.getString("answerOption4"));

                                } else if (jsonObj.getString("questionType") != null && jsonObj.getString("questionType").equalsIgnoreCase("ImageWithFillInTheBlanks")) {

                                    question4LinearLayoutForImageWithFillInTheBlanks.setVisibility(View.VISIBLE);
                                    question4ForImageWithFillInTheBlanks.setText(jsonObj.getString("question"));

                                    ImageFromURL url=new ImageFromURL(jsonObj.getString("imageCaption"), question4ImageWithFillInTheBlanks);



                                } else if (jsonObj.getString("questionType") != null && jsonObj.getString("questionType").equalsIgnoreCase("ImageWithTrueOrFalse")) {

                                    question4LinearLayoutForImageWithTrueOrFalse.setVisibility(View.VISIBLE);
                                    question4ForImageWithTrueOrFalse.setText(jsonObj.getString("question"));

                                    ImageFromURL url=new ImageFromURL(jsonObj.getString("imageCaption"), question4ImageWithTrueOrFalse);


                                } else if (jsonObj.getString("questionType") != null && jsonObj.getString("questionType").equalsIgnoreCase("TextWithFillInTheBlanks")) {

                                    question4LinearLayoutForFillInTheBlanks.setVisibility(View.VISIBLE);
                                    question4ForFillInTheBlanks.setText(jsonObj.getString("question"));


                                } else if (jsonObj.getString("questionType") != null && jsonObj.getString("questionType").equalsIgnoreCase("TextWithTrueOrFalse")) {

                                    question4LinearLayoutForTrueOrFalse.setVisibility(View.VISIBLE);
                                    question4ForTrueOrFalse.setText(jsonObj.getString("question"));

                                }
                            }

                            if(i==4) {

                                questionNo5=jsonObj.getString("id");
                                questionNo5Type=jsonObj.getString("questionType");

                                if (jsonObj.getString("questionType") != null && jsonObj.getString("questionType").equalsIgnoreCase("TextWithMultipleChoice")) {

                                    question5LinearLayoutForMultipleChoice.setVisibility(View.VISIBLE);
                                    question5ForMultipleChoice.setText(jsonObj.getString("question"));

                                    question5ForMultipleChoiceAns1.setText(jsonObj.getString("answerOption1"));
                                    question5ForMultipleChoiceAns2.setText(jsonObj.getString("answerOption2"));
                                    question5ForMultipleChoiceAns3.setText(jsonObj.getString("answerOption3"));
                                    question5ForMultipleChoiceAns4.setText(jsonObj.getString("answerOption4"));


                                } else if (jsonObj.getString("questionType") != null && jsonObj.getString("questionType").equalsIgnoreCase("ImageWithMultipleChoice")) {

                                    question5LinearLayoutForImageWithMultipleChoice.setVisibility(View.VISIBLE);
                                    question5ForImageWithMultipleChoice.setText(jsonObj.getString("question"));

                                    ImageFromURL url=new ImageFromURL(jsonObj.getString("imageCaption"), question5ImageWithMultipleChoice);

                                    question5ForImageWithMultipleChoiceAns1.setText(jsonObj.getString("answerOption1"));
                                    question5ForImageWithMultipleChoiceAns2.setText(jsonObj.getString("answerOption2"));
                                    question5ForImageWithMultipleChoiceAns3.setText(jsonObj.getString("answerOption3"));
                                    question5ForImageWithMultipleChoiceAns4.setText(jsonObj.getString("answerOption4"));

                                } else if (jsonObj.getString("questionType") != null && jsonObj.getString("questionType").equalsIgnoreCase("ImageWithFillInTheBlanks")) {

                                    question5LinearLayoutForImageWithFillInTheBlanks.setVisibility(View.VISIBLE);
                                    question5ForImageWithFillInTheBlanks.setText(jsonObj.getString("question"));

                                    ImageFromURL url=new ImageFromURL(jsonObj.getString("imageCaption"), question5ImageWithFillInTheBlanks);



                                } else if (jsonObj.getString("questionType") != null && jsonObj.getString("questionType").equalsIgnoreCase("ImageWithTrueOrFalse")) {

                                    question5LinearLayoutForImageWithTrueOrFalse.setVisibility(View.VISIBLE);
                                    question5ForImageWithTrueOrFalse.setText(jsonObj.getString("question"));

                                    ImageFromURL url=new ImageFromURL(jsonObj.getString("imageCaption"), question5ImageWithTrueOrFalse);


                                } else if (jsonObj.getString("questionType") != null && jsonObj.getString("questionType").equalsIgnoreCase("TextWithFillInTheBlanks")) {

                                    question5LinearLayoutForFillInTheBlanks.setVisibility(View.VISIBLE);
                                    question5ForFillInTheBlanks.setText(jsonObj.getString("question"));


                                } else if (jsonObj.getString("questionType") != null && jsonObj.getString("questionType").equalsIgnoreCase("TextWithTrueOrFalse")) {

                                    question5LinearLayoutForTrueOrFalse.setVisibility(View.VISIBLE);
                                    question5ForTrueOrFalse.setText(jsonObj.getString("question"));

                                }
                            }
                        }
                    }
                    pDialog.dismiss();
                    startTime = SystemClock.uptimeMillis();
                    customHandler.postDelayed(updateTimerThread, 0);



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                // When Http response code is '404'
                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else {
                    Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }
            }
            // When the response returned by REST has Http response code '200'

        });

    }

    public void onRadioButtonClickedQuestion1ForMultipleChoice(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();


        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.ans1ForQuizQuestion1ForMultipleChoice:

                if (checked)
                    ansForQuestionNo1 = question1ForMultipleChoiceAns1.getText().toString();
                break;
            case R.id.ans2ForQuizQuestion1ForMultipleChoice:
                if (checked)
                    ansForQuestionNo1 = question1ForMultipleChoiceAns2.getText().toString();
                break;
            case R.id.ans3ForQuizQuestion1ForMultipleChoice:
                if (checked)
                    ansForQuestionNo1 = question1ForMultipleChoiceAns3.getText().toString();
                break;
            case R.id.ans4ForQuizQuestion1ForMultipleChoice:
                if (checked)
                    ansForQuestionNo1 = question1ForMultipleChoiceAns4.getText().toString();
                break;


        }
    }

    public void onRadioButtonClickedQuestion1ForImageWithMultipleChoice(View view) {

        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.ans1ForQuizQuestion1ForImageWithMultipleChoice:

                if (checked)
                    ansForQuestionNo1 = question1ForImageWithMultipleChoiceAns1.getText().toString();
                break;
            case R.id.ans2ForQuizQuestion1ForImageWithMultipleChoice:
                if (checked)
                    ansForQuestionNo1 = question1ForImageWithMultipleChoiceAns2.getText().toString();
                break;
            case R.id.ans3ForQuizQuestion1ForImageWithMultipleChoice:
                if (checked)
                    ansForQuestionNo1 = question1ForImageWithMultipleChoiceAns3.getText().toString();
                break;
            case R.id.ans4ForQuizQuestion1ForImageWithMultipleChoice:
                if (checked)
                    ansForQuestionNo1 = question1ForImageWithMultipleChoiceAns4.getText().toString();
                break;

        }
    }

    public void onRadioButtonClickedQuestion1ForImageWithTrueOrFalse(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.ans1ForQuizQuestion1ForImageWithTrueOrFalse:

                if (checked)
                    ansForQuestionNo1 = question1ForImageWithTrueOrFalseAns1.getText().toString();
                break;
            case R.id.ans2ForQuizQuestion1ForImageWithTrueOrFalse:
                if (checked)
                    ansForQuestionNo1 = question1ForImageWithTrueOrFalseAns2.getText().toString();
                break;
//
        }
    }

    public void onRadioButtonClickedQuestion1ForTrueOrFalse(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.ans1ForQuizQuestion1ForTrueOrFalse:

                if (checked)
                    ansForQuestionNo1 = question1ForFillInTheBlanksAns1.getText().toString();
                break;
            case R.id.ans2ForQuizQuestion1ForTrueOrFalse:
                if (checked)
                    ansForQuestionNo1 = question1ForFillInTheBlanksAns2.getText().toString();
                break;


        }
    }


    public void onRadioButtonClickedQuestion2ForMultipleChoice(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();


        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.ans1ForQuizQuestion2ForMultipleChoice:

                if (checked)
                    ansForQuestionNo2 = question2ForMultipleChoiceAns1.getText().toString();
                break;
            case R.id.ans2ForQuizQuestion2ForMultipleChoice:
                if (checked)
                    ansForQuestionNo2 = question2ForMultipleChoiceAns2.getText().toString();
                break;
            case R.id.ans3ForQuizQuestion2ForMultipleChoice:
                if (checked)
                    ansForQuestionNo2 = question2ForMultipleChoiceAns3.getText().toString();
                break;
            case R.id.ans4ForQuizQuestion2ForMultipleChoice:
                if (checked)
                    ansForQuestionNo2 = question2ForMultipleChoiceAns4.getText().toString();
                break;


        }
    }

    public void onRadioButtonClickedQuestion2ForImageWithMultipleChoice(View view) {

        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.ans1ForQuizQuestion2ForImageWithMultipleChoice:

                if (checked)
                    ansForQuestionNo2 = question2ForImageWithMultipleChoiceAns1.getText().toString();
                break;
            case R.id.ans2ForQuizQuestion2ForImageWithMultipleChoice:
                if (checked)
                    ansForQuestionNo2 = question2ForImageWithMultipleChoiceAns2.getText().toString();
                break;
            case R.id.ans3ForQuizQuestion2ForImageWithMultipleChoice:
                if (checked)
                    ansForQuestionNo2 = question2ForImageWithMultipleChoiceAns3.getText().toString();
                break;
            case R.id.ans4ForQuizQuestion2ForImageWithMultipleChoice:
                if (checked)
                    ansForQuestionNo2 = question2ForImageWithMultipleChoiceAns4.getText().toString();
                break;

        }
    }

    public void onRadioButtonClickedQuestion2ForImageWithTrueOrFalse(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.ans1ForQuizQuestion2ForImageWithTrueOrFalse:

                if (checked)
                    ansForQuestionNo2 = question2ForImageWithTrueOrFalseAns1.getText().toString();
                break;
            case R.id.ans2ForQuizQuestion2ForImageWithTrueOrFalse:
                if (checked)
                    ansForQuestionNo2 = question2ForImageWithTrueOrFalseAns2.getText().toString();
                break;
//
        }
    }

    public void onRadioButtonClickedQuestion2ForTrueOrFalse(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.ans1ForQuizQuestion2ForTrueOrFalse:

                if (checked)
                    ansForQuestionNo2 = question2ForFillInTheBlanksAns1.getText().toString();
                break;
            case R.id.ans2ForQuizQuestion2ForTrueOrFalse:
                if (checked)
                    ansForQuestionNo2 = question2ForFillInTheBlanksAns2.getText().toString();
                break;


        }
    }


    public void onRadioButtonClickedQuestion3ForMultipleChoice(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();


        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.ans1ForQuizQuestion3ForMultipleChoice:

                if (checked)
                    ansForQuestionNo3 = question3ForMultipleChoiceAns1.getText().toString();
                break;
            case R.id.ans2ForQuizQuestion3ForMultipleChoice:
                if (checked)
                    ansForQuestionNo3 = question3ForMultipleChoiceAns2.getText().toString();
                break;
            case R.id.ans3ForQuizQuestion3ForMultipleChoice:
                if (checked)
                    ansForQuestionNo3 = question3ForMultipleChoiceAns3.getText().toString();
                break;
            case R.id.ans4ForQuizQuestion3ForMultipleChoice:
                if (checked)
                    ansForQuestionNo3 = question3ForMultipleChoiceAns4.getText().toString();
                break;


        }
    }

    public void onRadioButtonClickedQuestion3ForImageWithMultipleChoice(View view) {

        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.ans1ForQuizQuestion3ForImageWithMultipleChoice:

                if (checked)
                    ansForQuestionNo3 = question3ForImageWithMultipleChoiceAns1.getText().toString();
                break;
            case R.id.ans2ForQuizQuestion3ForImageWithMultipleChoice:
                if (checked)
                    ansForQuestionNo3 = question3ForImageWithMultipleChoiceAns2.getText().toString();
                break;
            case R.id.ans3ForQuizQuestion3ForImageWithMultipleChoice:
                if (checked)
                    ansForQuestionNo3 = question3ForImageWithMultipleChoiceAns3.getText().toString();
                break;
            case R.id.ans4ForQuizQuestion3ForImageWithMultipleChoice:
                if (checked)
                    ansForQuestionNo3 = question3ForImageWithMultipleChoiceAns4.getText().toString();
                break;

        }
    }

    public void onRadioButtonClickedQuestion3ForImageWithTrueOrFalse(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.ans1ForQuizQuestion3ForImageWithTrueOrFalse:

                if (checked)
                    ansForQuestionNo3 = question3ForImageWithTrueOrFalseAns1.getText().toString();
                break;
            case R.id.ans2ForQuizQuestion3ForImageWithTrueOrFalse:
                if (checked)
                    ansForQuestionNo3 = question3ForImageWithTrueOrFalseAns2.getText().toString();
                break;
//
        }
    }

    public void onRadioButtonClickedQuestion3ForTrueOrFalse(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.ans1ForQuizQuestion3ForTrueOrFalse:

                if (checked)
                    ansForQuestionNo3 = question3ForFillInTheBlanksAns1.getText().toString();
                break;
            case R.id.ans2ForQuizQuestion3ForTrueOrFalse:
                if (checked)
                    ansForQuestionNo3 = question3ForFillInTheBlanksAns2.getText().toString();
                break;


        }
    }


    public void onRadioButtonClickedQuestion4ForMultipleChoice(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();


        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.ans1ForQuizQuestion4ForMultipleChoice:

                if (checked)
                    ansForQuestionNo4 = question4ForMultipleChoiceAns1.getText().toString();
                break;
            case R.id.ans2ForQuizQuestion4ForMultipleChoice:
                if (checked)
                    ansForQuestionNo4 = question4ForMultipleChoiceAns2.getText().toString();
                break;
            case R.id.ans3ForQuizQuestion4ForMultipleChoice:
                if (checked)
                    ansForQuestionNo4 = question4ForMultipleChoiceAns3.getText().toString();
                break;
            case R.id.ans4ForQuizQuestion4ForMultipleChoice:
                if (checked)
                    ansForQuestionNo4 = question4ForMultipleChoiceAns4.getText().toString();
                break;


        }
    }

    public void onRadioButtonClickedQuestion4ForImageWithMultipleChoice(View view) {

        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.ans1ForQuizQuestion4ForImageWithMultipleChoice:

                if (checked)
                    ansForQuestionNo4 = question4ForImageWithMultipleChoiceAns1.getText().toString();
                break;
            case R.id.ans2ForQuizQuestion4ForImageWithMultipleChoice:
                if (checked)
                    ansForQuestionNo4 = question4ForImageWithMultipleChoiceAns2.getText().toString();
                break;
            case R.id.ans3ForQuizQuestion4ForImageWithMultipleChoice:
                if (checked)
                    ansForQuestionNo4 = question4ForImageWithMultipleChoiceAns3.getText().toString();
                break;
            case R.id.ans4ForQuizQuestion4ForImageWithMultipleChoice:
                if (checked)
                    ansForQuestionNo4 = question4ForImageWithMultipleChoiceAns4.getText().toString();
                break;

        }
    }

    public void onRadioButtonClickedQuestion4ForImageWithTrueOrFalse(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.ans1ForQuizQuestion4ForImageWithTrueOrFalse:

                if (checked)
                    ansForQuestionNo4 = question4ForImageWithTrueOrFalseAns1.getText().toString();
                break;
            case R.id.ans2ForQuizQuestion4ForImageWithTrueOrFalse:
                if (checked)
                    ansForQuestionNo4 = question4ForImageWithTrueOrFalseAns2.getText().toString();
                break;
//
        }
    }

    public void onRadioButtonClickedQuestion4ForTrueOrFalse(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.ans1ForQuizQuestion4ForTrueOrFalse:

                if (checked)
                    ansForQuestionNo4 = question4ForFillInTheBlanksAns1.getText().toString();
                break;
            case R.id.ans2ForQuizQuestion4ForTrueOrFalse:
                if (checked)
                    ansForQuestionNo4 = question4ForFillInTheBlanksAns2.getText().toString();
                break;


        }
    }


    public void onRadioButtonClickedQuestion5ForMultipleChoice(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();


        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.ans1ForQuizQuestion5ForMultipleChoice:

                if (checked)
                    ansForQuestionNo5 = question5ForMultipleChoiceAns1.getText().toString();
                break;
            case R.id.ans2ForQuizQuestion5ForMultipleChoice:
                if (checked)
                    ansForQuestionNo5 = question5ForMultipleChoiceAns2.getText().toString();
                break;
            case R.id.ans3ForQuizQuestion5ForMultipleChoice:
                if (checked)
                    ansForQuestionNo5 = question5ForMultipleChoiceAns3.getText().toString();
                break;
            case R.id.ans4ForQuizQuestion5ForMultipleChoice:
                if (checked)
                    ansForQuestionNo5 = question5ForMultipleChoiceAns4.getText().toString();
                break;


        }
    }

    public void onRadioButtonClickedQuestion5ForImageWithMultipleChoice(View view) {

        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.ans1ForQuizQuestion5ForImageWithMultipleChoice:

                if (checked)
                    ansForQuestionNo5 = question5ForImageWithMultipleChoiceAns1.getText().toString();
                break;
            case R.id.ans2ForQuizQuestion5ForImageWithMultipleChoice:
                if (checked)
                    ansForQuestionNo5 = question5ForImageWithMultipleChoiceAns2.getText().toString();
                break;
            case R.id.ans3ForQuizQuestion5ForImageWithMultipleChoice:
                if (checked)
                    ansForQuestionNo5 = question5ForImageWithMultipleChoiceAns3.getText().toString();
                break;
            case R.id.ans4ForQuizQuestion5ForImageWithMultipleChoice:
                if (checked)
                    ansForQuestionNo5 = question5ForImageWithMultipleChoiceAns4.getText().toString();
                break;

        }
    }

    public void onRadioButtonClickedQuestion5ForImageWithTrueOrFalse(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.ans1ForQuizQuestion5ForImageWithTrueOrFalse:

                if (checked)
                    ansForQuestionNo5 = question5ForImageWithTrueOrFalseAns1.getText().toString();
                break;
            case R.id.ans2ForQuizQuestion5ForImageWithTrueOrFalse:
                if (checked)
                    ansForQuestionNo5 = question5ForImageWithTrueOrFalseAns2.getText().toString();
                break;
//
        }
    }

    public void onRadioButtonClickedQuestion5ForTrueOrFalse(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.ans1ForQuizQuestion5ForTrueOrFalse:

                if (checked)
                    ansForQuestionNo5 = question5ForFillInTheBlanksAns1.getText().toString();
                break;
            case R.id.ans2ForQuizQuestion5ForTrueOrFalse:
                if (checked)
                    ansForQuestionNo5 = question5ForFillInTheBlanksAns2.getText().toString();
                break;


        }
    }

    private Runnable updateTimerThread = new Runnable() {

        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;
            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            timerValue.setText("" + mins + ":"
                    + String.format("%02d", secs) + ":"
                    + String.format("%03d", milliseconds));
            customHandler.postDelayed(this, 0);
        }
    };

    public void submitTest(View view){
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent=cd.checkNetwork();
        if(isInternetPresent) {
            submitButton.setEnabled(false);

            if(questionNo1Type.equalsIgnoreCase("ImageWithFillInTheBlanks")){
                ansForQuestionNo1=question1ImageWithFillInTheBlanksAnswer.getText().toString();

            }
            else if(questionNo1Type.equalsIgnoreCase("TextWithFillInTheBlanks")){
                ansForQuestionNo1=question1ForFillInTheBlanksAnswer.getText().toString();
            }

            if(questionNo2Type.equalsIgnoreCase("ImageWithFillInTheBlanks")){
                ansForQuestionNo2=question2ImageWithFillInTheBlanksAnswer.getText().toString();

            }
            else if(questionNo2Type.equalsIgnoreCase("TextWithFillInTheBlanks")){
                ansForQuestionNo2=question2ForFillInTheBlanksAnswer.getText().toString();
            }


            if(questionNo3Type.equalsIgnoreCase("ImageWithFillInTheBlanks")){
                ansForQuestionNo3=question3ImageWithFillInTheBlanksAnswer.getText().toString();

            }
            else if(questionNo3Type.equalsIgnoreCase("TextWithFillInTheBlanks")){
                ansForQuestionNo3=question3ForFillInTheBlanksAnswer.getText().toString();
            }


            if(questionNo4Type.equalsIgnoreCase("ImageWithFillInTheBlanks")){
                ansForQuestionNo4=question4ImageWithFillInTheBlanksAnswer.getText().toString();

            }
            else if(questionNo4Type.equalsIgnoreCase("TextWithFillInTheBlanks")){
                ansForQuestionNo4=question4ForFillInTheBlanksAnswer.getText().toString();
            }


            if(questionNo5Type.equalsIgnoreCase("ImageWithFillInTheBlanks")){
                ansForQuestionNo5=question5ImageWithFillInTheBlanksAnswer.getText().toString();

            }
            else if(questionNo5Type.equalsIgnoreCase("TextWithFillInTheBlanks")){
                ansForQuestionNo5=question5ForFillInTheBlanksAnswer.getText().toString();
            }


            RequestParams params = new RequestParams();
            params.put("question1", questionNo1);
            params.put("question2", questionNo2);
            params.put("question3", questionNo3);
            params.put("question4", questionNo4);
            params.put("question5", questionNo5);
            params.put("hackathonId", hackathonId);
            params.put("answer1", ansForQuestionNo1);
            params.put("answer2", ansForQuestionNo2);
            params.put("answer3", ansForQuestionNo3);
            params.put("answer4", ansForQuestionNo4);
            params.put("answer5", ansForQuestionNo5);
            params.put("email", email);
            params.put("name", name);
            params.put("testDuration", timerValue.getText().toString());
            submitTest(params);

        }
        else{
            showAlertDialog(QuizTest.this, "No Internet Connection",
                    "You don't have internet connection.", false);
        }
    }

    public void submitTest(RequestParams params) {

        // Show Progress Dialog

        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(DEFAULT_TIMEOUT);
        client.get("http://incubator.shinelogics.com/submitExam.html",params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    // JSON Object
                    String result= new String(responseBody, "UTF-8");


                    showAlertDialogForSuccess(QuizTest.this, "Thank You",
                            "Result will out on "+result+".", false);

                }  catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                // When Http response code is '404'
                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else {
                    Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }
            }
            // When the response returned by REST has Http response code '200'

        });

    }

    public void showAlertDialog(Context context, String title, String message, Boolean status) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setIcon(R.drawable.internet_faild)
                .setMessage(message)
                .setCancelable(false)
                .setNegativeButton("Close",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void showAlertDialogForSuccess(Context context, String title, String message, Boolean status) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setIcon(R.drawable.success)
                .setMessage(message)
                .setCancelable(false)
                .setNegativeButton("Close",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Intent loginIntent = new Intent(getApplicationContext(), Home.class);
                        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(loginIntent);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(QuizTest.this);
            pDialog.setMessage("Loading Image ....");
            pDialog.show();

        }
        protected Bitmap doInBackground(String... args) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream)new URL(args[0]).getContent());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap image) {

            if(image != null){
                img.setImageBitmap(image);
                pDialog.dismiss();

            }else{

                pDialog.dismiss();
                Toast.makeText(QuizTest.this, "Image Does Not exist or Network Error", Toast.LENGTH_SHORT).show();

            }
        }
    }
}
