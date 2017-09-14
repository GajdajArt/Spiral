package com.labralab.spiral;

import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        SeekBar.OnSeekBarChangeListener {
    //TextView для показа введенного значения и результата
    private TextView displayText;
    //TextView для демонстрации подсказки
    private TextView displayHint;
    //Буква "D" в формулеподсказке
    private TextView D;
    //Буква "а" в формуле подсказке
    private TextView a;
    //Выводимое значение
    private String numbers;
    //Конечный результат расчетов
    private double result;
    //Список для хранения введенных параметров
    private List<String> paramList;
    //Флаг сигнализирующий о пулучении окончательного результата
    private Boolean isCounted;
    //SeekBar для регулировки количества чисел после запятой
    private SeekBar seekBar;
    //Количество знаков после запятой
    private int afterPoint;
    //Фукциональная кнопка
    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(this);

        //Получаем стартове значение знаков после запятой
        afterPoint = seekBar.getProgress();

        numbers = "0";

        displayText = (TextView) findViewById(R.id.displayText);
        displayText.setTextSize(MyDisplay.displayTextSize(numbers));

        displayHint = (TextView) findViewById(R.id.displayHint);
        D = (TextView) findViewById(R.id.D);
        a = (TextView) findViewById(R.id.a);

        //Нажатие на FAB
        fab = (FloatingActionButton) findViewById(R.id.nextButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Если значение на дисплее не равно "0" и мы имеем посчитанный результат
                //Выводим стартовые параметры
                if (!numbers.equals("0")) {
                    if (isCounted) {
                        startParams();
                        return;
                    }
                    //Если введен только один параметр
                    if (paramList.size() < Spiral.NUMBER_OF_PARAMETERS - 1) {
                        //Добавляем новый параметр в список
                        paramList.add(numbers);
                        //Обнуляем дисплей
                        numbers = "0";
                        displayText.setText(numbers);
                        //Обновляем подсказку
                        displayHint.setText(R.string.display_text_hint_2);
                        displayText.setTextSize(MyDisplay.displayTextSize(numbers));

                        //Приводим формулу подсказку в соответствие данному этапу
                        D.setTypeface(null, Typeface.NORMAL);
                        D.setTextSize(20);
                        D.setText(paramList.get(0));
                        a.setTypeface(null, Typeface.BOLD);
                        a.setTextSize(30);
                        a.setText(" a ");

                    } else {
                        //Добавляем новый параметр в список
                        paramList.add(numbers);
                        //Редактируем подсказку
                        displayHint.setText(R.string.display_text_hint_3);
                        displayHint.setTypeface(null, Typeface.BOLD);


                        afterPoint = seekBar.getProgress();
                        //Производи расчет по формуле
                        result = Spiral.StepOfSpiral(paramList);
                        //Редактируем количество точек после запятой
                        numbers = String.format("%." + afterPoint + "f", result);
                        fab.setImageDrawable(getDrawable(R.drawable.ic_replay_white_24dp));
                        //Выводим результат на дисплей
                        displayText.setText(String.format(numbers) + " мм");
                        displayText.setTextSize(MyDisplay.displayTextSize(numbers + " мм"));

                        //Редактируем формулу-подсказку
                        a.setTextSize(20);
                        a.setText(paramList.get(1));
                        a.setTypeface(null, Typeface.NORMAL);

                        isCounted = true;

                    }
                }

            }
        });

        //Вешаем слушатели на кнопки
        setterOCL();
        //Выводим на дисплей стартовые параметры
        startParams();

    }

    //Обработка нажатия назадв зависиммости от этапа вычислений
    @Override
    public void onBackPressed() {

        switch (paramList.size()) {
            case 0:
                if (numbers.equals("0")) {
                    super.onBackPressed();
                }
                startParams();
                break;
            case 1:
                numbers = paramList.get(0);
                paramList.remove(0);
                displayText.setText(numbers);
                displayHint.setText(R.string.display_text_hint);
                displayText.setTextSize(MyDisplay.displayTextSize(numbers));
                break;
            case 2:
                startParams();
                break;

        }
    }

    //Метод для присвоения параметров по умолчанию
    private void startParams() {
        //Значение числа на дисплее
        numbers = "0";
        //Выводимы результат
        result = 0;
        //Флаг сигнализирующий о пулучении окончательного результата
        isCounted = false;
        //Список уже введенныхпараметров
        paramList = new ArrayList<>();

        //Присваемваем FAB картинку по умолчанию
        fab.setImageDrawable(getDrawable(R.drawable.ic_play_arrow_white_24dp));

        //Выводим на дисплей "0" большого размера
        displayText.setTextSize(MyDisplay.displayTextSize(numbers));
        displayText.setText(numbers);
        //Начальная подсказка на дисплее
        displayHint.setText(R.string.display_text_hint);
        displayHint.setTypeface(null, Typeface.NORMAL);

        //В формуле - подсказке выделяем параметр диаметра
        D.setTypeface(null, Typeface.BOLD);
        D.setTextSize(30);
        D.setText(" D ");
        a.setTypeface(null, Typeface.NORMAL);
        a.setTextSize(20);
        a.setText("a");


    }

    //Обработка нажатий на кнопки
    @Override
    public void onClick(View view) {

        if (isCounted) {
            startParams();
        }

        int id = view.getId();
        switch (id) {
            case R.id.button1:
                numbers = MyDisplay.addNumber(numbers, "1");
                break;
            case R.id.button2:
                numbers = MyDisplay.addNumber(numbers, "2");
                break;
            case R.id.button3:
                numbers = MyDisplay.addNumber(numbers, "3");
                break;
            case R.id.button4:
                numbers = MyDisplay.addNumber(numbers, "4");
                break;
            case R.id.button5:
                numbers = MyDisplay.addNumber(numbers, "5");
                break;
            case R.id.button6:
                numbers = MyDisplay.addNumber(numbers, "6");
                break;
            case R.id.button7:
                numbers = MyDisplay.addNumber(numbers, "7");
                break;
            case R.id.button8:
                numbers = MyDisplay.addNumber(numbers, "8");
                break;
            case R.id.button9:
                numbers = MyDisplay.addNumber(numbers, "9");
                break;
            case R.id.button0:
                numbers = MyDisplay.addNumber(numbers, "0");
                break;
            case R.id.buttonPoint:
                numbers = MyDisplay.addNumber(numbers, ".");
                break;
            case R.id.buttonC:
                numbers = "0";
        }

        //определяем разер выводимого текста
        displayText.setTextSize(MyDisplay.displayTextSize(numbers));
        //Выводим текст с учетом добавленной цифры
        displayText.setText(numbers);

    }

    //Назначаем слушатели нажатий кнопкам
    private void setterOCL() {

        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(this);

        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(this);

        Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(this);

        Button button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(this);

        Button button5 = (Button) findViewById(R.id.button5);
        button5.setOnClickListener(this);

        Button button6 = (Button) findViewById(R.id.button6);
        button6.setOnClickListener(this);

        Button button7 = (Button) findViewById(R.id.button7);
        button7.setOnClickListener(this);

        Button button8 = (Button) findViewById(R.id.button8);
        button8.setOnClickListener(this);

        Button button9 = (Button) findViewById(R.id.button9);
        button9.setOnClickListener(this);

        Button button0 = (Button) findViewById(R.id.button0);
        button0.setOnClickListener(this);

        Button buttonC = (Button) findViewById(R.id.buttonC);
        buttonC.setOnClickListener(this);

        Button buttonPoint = (Button) findViewById(R.id.buttonPoint);
        buttonPoint.setOnClickListener(this);

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        afterPoint = seekBar.getProgress();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    //Обработка смещения SeekBar
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (result != 0) {
            //Считываем значение точек послезапятой в SeekBar
            afterPoint = seekBar.getProgress();
            //переопределяем выводимое число на дисплей
            numbers = String.format("%." + afterPoint + "f", result);
            //Выводим новое число
            displayText.setText(String.format(numbers) + " мм");
            //Переопределяем размер текста
            displayText.setTextSize(MyDisplay.displayTextSize(numbers + " мм"));
        }
    }

}
