package com.labralab.spiral;

import java.util.List;

/**
 * Created by pc on 02.09.2017.
 */

public class Spiral {

    public static final int NUMBER_OF_PARAMETERS = 2;
    //Формула для расчета шага спирали
    public static double StepOfSpiral(List<String> params){


        double diam = Double.parseDouble(params.get(0));
        double angle = Math.toRadians(Double.parseDouble(params.get(1)));

        double step = (Math.PI * diam) / Math.tan(angle);

        return step;
    }
}
