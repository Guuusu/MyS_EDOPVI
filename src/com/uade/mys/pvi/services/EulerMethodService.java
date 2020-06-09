package com.uade.mys.pvi.services;

import javafx.scene.chart.XYChart;

public class EulerMethodService {

    private FunctionResolver functionResolver = new FunctionResolver();

    public XYChart.Series<Number, Double> getEulerMethodSolutions(String expression, Double initialTime, Double endTime,
                                                                  Double initialValue, Double intervals) {

        double h = (endTime - initialTime) / intervals;
        XYChart.Series<Number, Double> series = new XYChart.Series<Number, Double>();
        series.setName("Euler");
        Double u = initialValue;
        Double t = (double) initialTime;
        double f = functionResolver.solveFunction(expression, t, u);
        series.getData().add(new XYChart.Data<Number, Double>(t, (double) u));

        for (int k = 1; k <= intervals; k++) {

            u = (u + (h * f));
            t = (double) (initialTime + (k * h));
            series.getData().add(new XYChart.Data<Number, Double>(t, u));

            f = functionResolver.solveFunction(expression, t, u);

        }

        return series;
    }

    public XYChart.Series<Number, Double> getEnhaEulerMethodSolutions(String expression, Double initialTime, Double endTime,
                                                                      Double initialValue, Double intervals) {

        double h = (endTime - initialTime) / intervals;
        XYChart.Series<Number, Double> series = new XYChart.Series<Number, Double>();
        series.setName("Euler Mejorado");
        Double u = initialValue;
        Double t = (double) initialTime;
        double f = functionResolver.solveFunction(expression, t, u);
        series.getData().add(new XYChart.Data<Number, Double>(t, u));
        Double predictor = u + (h * f);
        for (int k = 1; k <= intervals; k++) {
            t = (initialTime + (k * h));
            u = u + (h / 2) * (f + functionResolver.solveFunction(expression, t, predictor));

            series.getData().add(new XYChart.Data<Number, Double>(t, u));

            f = functionResolver.solveFunction(expression, t, u);
            predictor = u + (h * f);

        }

        return series;

    }
}