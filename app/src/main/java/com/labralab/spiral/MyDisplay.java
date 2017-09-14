package com.labralab.spiral;


 class MyDisplay {

    //Метот для формирования числа из вводимых чисел
   public static String addNumber(String oldNumber, String addedValue){
       String newNumber = oldNumber;
       //Максимальная длинна числа
       if(oldNumber.length() < 9) {
           //В старое число == 0
           if (oldNumber.equals("0")) {
                //Если вводим точку,то добавляем ее в конец строки
               if (addedValue.equals(".")) {
                     newNumber = oldNumber + addedValue;
               } else {
                   //Если же другое чисто, то заменяем им 0
                   newNumber = addedValue;
               }

           } else {
               //Просто добавляем введенное число в конец строки
               newNumber = oldNumber + addedValue;
           }
       }
       //Точка должна быть толькоодна. Провряем
       if (oldNumber.contains(".") && addedValue.equals(".")){
           newNumber = oldNumber;
       }
        //Возвращем сформированное число
       return newNumber;
   }
    //Метод для определения размера текста в зависимости от его длянны
   public static float displayTextSize(String number){
       float size = 150;
       if(number.length() > 3) {
           size = 100;
       }
       if(number.length() > 5) {
           size = 75;
       }
       if(number.length() > 6) {
           size = 50;
       }
       if(number.length() > 10) {
           size = 30;
       }

       return size;
   }
}
