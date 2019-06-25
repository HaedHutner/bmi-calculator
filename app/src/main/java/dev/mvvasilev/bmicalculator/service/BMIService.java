package dev.mvvasilev.bmicalculator.service;

import android.content.Context;

import java.time.LocalDateTime;
import java.util.Set;

import dev.mvvasilev.bmicalculator.entity.BMICalculation;
import dev.mvvasilev.bmicalculator.repository.BMICalculationRepository;

public class BMIService {

    private Context context;

    public BMIService(Context context) {
        this.context = context;
    }

    public BMICalculation calculateBMI(double weight, double height) {
        BMICalculationRepository repository = new BMICalculationRepository(context);

        BMICalculation calculation = new BMICalculation();
        calculation.setWeight(weight);
        calculation.setHeight(height);
        calculation.setTimestamp(LocalDateTime.now());

        calculation.setResult(weight / Math.pow(height, 2));

        return repository.save(calculation);
    }

    public Set<BMICalculation> getCalculationHistory() {
        return new BMICalculationRepository(context).getAll();
    }
}
