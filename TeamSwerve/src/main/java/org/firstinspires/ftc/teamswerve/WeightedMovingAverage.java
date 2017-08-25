package org.firstinspires.ftc.teamswerve;

import java.util.ArrayList;
import java.util.List;

public class WeightedMovingAverage
{
    //Let's define the zero element as the oldest.
    private ArrayList<Double> values;
    private double[] weights;
    private int samples = 0;

    public WeightedMovingAverage(double[] weightArray)
    {
        samples = weightArray.length;

        if (weightArray.length < 2) throw new IllegalArgumentException("Array requires at least 2 elements");

        //The elements of the weightArray must sum to 1.0.  (Since double arithmetic is not exact, we'll allow a slop factor.)
        //Confirm that the input weights sum to roughly 1.0 and throw an exception if not.
        double sumOfWeights = 0;

        for (int i = 0; i < samples; i++)
        {
            sumOfWeights += weightArray[i];
        }

        if ((sumOfWeights > 1.001) || (sumOfWeights < 0.999))
        {
            throw new IllegalArgumentException("Array elements must sum to 1.0");
        }

        values = new ArrayList<Double>(samples);
        weights = new double[samples];

        //reminder: the 0th element is the oldest one.
        for (int i = 0; i < samples; i++)
        {
            weights[i] = weightArray[i];
        }

    }

    public void addNewValue(double newValue)
    {
        //get rid of oldest value
        //only remove the last element if we have already filled the values list
        //(the values list starts empty when this class is created)
        if (values.size() == samples)
        {
            removeLastElement();
        }

        //add new value to the end of the list
        values.add(newValue);
    }

    private void removeLastElement()
    {
        if (values.size() > 0)
        {
            values.remove(0);
        }
    }

    /*
    This method applies the set weight to each sample inside of the filter.  The weight with the
    most impact on a sample is the weight closest to the newest value, or index 9.
     */
    private double applyWeight(int index)
    {
        return values.get(index) * weights[index];
    }

    /*
    This method returns the filtered values after applying the weighted filter calculations.
     */
    public double getRunningTotal()
    {
        double runningTotal = 0.0;

        for (int i = 0; i < values.size(); i++)
        {
            runningTotal += applyWeight(i);
        }
        return runningTotal;
    }
}