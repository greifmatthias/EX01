package be.greifmatthias.ex;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends Activity {

//    Controls
    private TextView tvExercise;
    private TextView tvPad;
    private TextView tvResult;

    private Exercise current;

    private int valid;
    private int invalid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Init
        valid = 0;
        invalid = 0;

//        Get controls
        this.tvExercise = findViewById(R.id.tvExercise);
        this.tvPad = findViewById(R.id.tvPad);
        this.tvResult = findViewById(R.id.tvResult);

//        Setup screen
        updateUi();

    }

    public void onPadbuttonClick(View view){
        this.tvPad.setText(this.tvPad.getText() + "" + ((Button)view).getText());
    }

    public void onPadsubmitClick(View view){
        if(this.tvPad.getText().length() > 0) {
            if (current.getResult().equals(this.tvPad.getText())) {
                valid++;
            } else {
                invalid++;
            }

            updateUi();
        }
    }

    public void onPaddeleteClick(View view){
        if(this.tvPad.getText().length() > 0){
            this.tvPad.setText(this.tvPad.getText().toString().substring(0, this.tvPad.getText().length() - 1));
        }
    }

    private void updateUi(){
//        Generate exercise
        current = new Exercise();
        this.tvExercise.setText(current.getQuest());

//        Clear pad
        this.tvPad.setText("");

//        Update results
        tvResult.setText("Correct: " + this.valid + " Incorrect: " + this.invalid);
    }


    private enum TYPE {
        PLUS,
        MINUS,
        MAAL,
        DIVIDE
    };

    private class Exercise{
        private int[] numbers;
        private TYPE type;

        public Exercise(){
            this.numbers = new int[2];

//            Random type, generate numbers
            switch (getRandom(0, 3)){
                case 0:
                    this.type = TYPE.PLUS;

                    this.numbers[0] = getRandom(0, 100);
                    this.numbers[1] = getRandom(0, 100 - this.numbers[0]);
                    break;
                case 1:
                    this.type = TYPE.MINUS;

                    this.numbers[0] = getRandom(0, 100);
                    this.numbers[1] = getRandom(0, this.numbers[0]);
                    break;
                case 2:
                    this.type = TYPE.MAAL;

                    this.numbers[0] = getRandom(0, 10);
                    this.numbers[1] = getRandom(0, 10);
                    break;
                case 3:
                    this.type = TYPE.DIVIDE;

                    this.numbers[0] = getRandom(0, 100);
                    this.numbers[1] = getRandom(this.numbers[0], 10);
                    break;
            }
        }

        public String getQuest(){
            String output = "";

            boolean isfirst = true;
            for(int number : this.numbers){
                if(!isfirst){
                    output += getTypesymbol() + number;
                }else{
                    output += number + "";
                    isfirst = false;
                }
            }

            return output;
        }

        public String getResult(){
            int output = 0;

            switch (this.type){
                case PLUS:
                    for (int number : this.numbers){
                        output += number;
                    }
                    break;
                case MINUS:
                    output = numbers[0] - numbers[1];
                    break;
                case MAAL:
                    output = 1;
                    for (int number : this.numbers){
                        output *= number;
                    }
                    break;
                case DIVIDE:
                    output = numbers[0] / numbers[1];
                    break;
            }

            return output + "";
        }

        private String getTypesymbol(){
            switch (this.type){
                case PLUS:
                    return " + ";
                case MINUS:
                    return " - ";
                case MAAL:
                    return " x ";
                case DIVIDE:
                    return " / ";
            }

            return "";
        }

        private int getRandom(int min, int max) {
            return (int)((Math.random() * ((max - min) + 1)) + min);
        }
    }
}
