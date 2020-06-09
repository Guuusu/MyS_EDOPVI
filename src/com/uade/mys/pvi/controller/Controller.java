package com.uade.mys.pvi.controller;

import com.uade.mys.pvi.services.EulerMethodService;
import com.uade.mys.pvi.services.FunctionResolver;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

import java.util.function.Predicate;

public class Controller {


    private EulerMethodService eulerMethodService = new EulerMethodService();
    private FunctionResolver functionResolver = new FunctionResolver();
    private StringBuilder sb = new StringBuilder("");

    @FXML
    private TextField functionField;
    @FXML
    private TextField InitialTimeField;
    @FXML
    private TextField endTimeField;
    @FXML
    private TextField initialValueField;
    @FXML
    private TextField eulerIntervalField;
    @FXML
    private ToggleButton eulerToggle;
    @FXML
    public TextArea valuesTextArea;
    @FXML
    public TextField enhaEulerIntervalField;
    @FXML
    public ToggleButton enhaEulerToggle;
    @FXML
    public TextArea parameterArea;
    @FXML
    private LineChart<Number,Double> lineChart;

    @FXML
    public void calculateInitialFunction(ActionEvent actionEvent) {
        lineChart.getData().clear();
        if (checkParameters()) {
            String expresion = functionField.getText();
            sb = new StringBuilder("Ecuacion :" + expresion+"\n");
            double initialTime = Double.parseDouble(InitialTimeField.getText());
            double endTime = Double.parseDouble(endTimeField.getText());
            sb.append("Intervalo : " + initialTime + " < t < " + endTime+"\n");
            double initialValue = Double.parseDouble(initialValueField.getText());
            sb.append("Valor inicial :" + initialValue+"\n");

            //graphFunction(expresion, initialTime, endTime);
            parameterArea.appendText(sb.toString());
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Parametros Invalidos");
            alert.setHeaderText("Debe Ingresar todos los parametros de la funcion");
            alert.show();
        }
    }

    private void graphFunction(String expresion, double initialTime, double endTime) {

        XYChart.Series<Number,Double>  series = new XYChart.Series<Number,Double>();
        series.setName("F(t,x)");
        for (int x = (int) initialTime; x <= endTime; x++){
            double resultado = functionResolver.solveFunction(expresion,(double)x,(double)0);
            series.getData().add(new XYChart.Data<Number,Double>(x,resultado));
        }
        lineChart.getData().add(series);

    }
    @FXML
    public void handleEulerMethodClicked(ActionEvent actionEvent) {
        if (checkParameters() && eulerIntervalField!= null &&
                !eulerIntervalField.getText().isEmpty()){
            if(eulerToggle.isSelected()){
                String expresion = functionField.getText();
                XYChart.Series<Number,Double>  series = new XYChart.Series<Number,Double>();
                double initialTime = Double.parseDouble(InitialTimeField.getText());
                double endTime = Double.parseDouble(endTimeField.getText());
                double initialValue = Double.parseDouble(initialValueField.getText());
                Double interval = Double.parseDouble(eulerIntervalField.getText());
                System.out.println("Valor inicial :" + initialValue);
               sb = new StringBuilder("Metodo Euler \n");
               parameterArea.appendText(sb.toString()+"     Cantidad de Intervalos : "+interval+"\n");
                XYChart.Series<Number, Double> resultSerie = eulerMethodService.getEulerMethodSolutions(expresion, initialTime, endTime, (double) initialValue, interval);
                resultSerie.getData().forEach((XYChart.Data data) -> {
                    String t = (String.format("%.2g", Double.parseDouble(data.getXValue().toString())));
                    String x = (String.format("%.5g", Double.parseDouble(data.getYValue().toString())));
                    sb.append("t = ").append(t).append(" ,  x = ").append(x).append("\n");
                });
                valuesTextArea.appendText(sb.toString());
                lineChart.getData().add(resultSerie);
            }else{
                lineChart.getData().removeIf(new Predicate<XYChart.Series<Number, Double>>() {
                    @Override
                    public boolean test(XYChart.Series<Number, Double> numberDoubleSeries) {
                        return numberDoubleSeries.getName().equalsIgnoreCase("Euler");
                    }
                });
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Parametros Invalidos");
            alert.setHeaderText("Debe Ingresar todos los parametros del metodo");
            alert.show();
            eulerToggle.setSelected(false);
        }
    }
    @FXML
    public void handleEnhaEulerClicked(ActionEvent actionEvent) {
        if (checkParameters() && enhaEulerIntervalField!= null &&
                !enhaEulerIntervalField.getText().isEmpty()){
            if(enhaEulerToggle.isSelected()){
                String expresion = functionField.getText();
                XYChart.Series<Number,Double>  series = new XYChart.Series<Number,Double>();
                double initialTime = Double.parseDouble(InitialTimeField.getText());
                double endTime = Double.parseDouble(endTimeField.getText());
                double initialValue = Double.parseDouble(initialValueField.getText());
                Double interval = Double.parseDouble(enhaEulerIntervalField.getText());
                sb = new StringBuilder("Metodo Euler Mejorado \n");
                parameterArea.appendText(sb.toString()+"     Cantidad de Intervalos : "+interval+"\n");
                XYChart.Series<Number, Double> resultSerie = eulerMethodService.getEnhaEulerMethodSolutions(expresion, initialTime, endTime, (double) initialValue, interval);
                resultSerie.getData().forEach((XYChart.Data data) -> {
                    String t = (String.format("%.2g", Double.parseDouble(data.getXValue().toString())));
                    String x = (String.format("%.5g", Double.parseDouble(data.getYValue().toString())));
                    sb.append("t = ").append(t).append(" ,  x = ").append(x).append("\n");
                });
                valuesTextArea.appendText(sb.toString());
                lineChart.getData().add(resultSerie);
            }else{
                lineChart.getData().removeIf(new Predicate<XYChart.Series<Number, Double>>() {
                    @Override
                    public boolean test(XYChart.Series<Number, Double> numberDoubleSeries) {
                        return numberDoubleSeries.getName().equalsIgnoreCase("Euler Mejorado");
                    }
                });
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Parametros Invalidos");
            alert.setHeaderText("Debe Ingresar todos los parametros del metodo");
            alert.show();
            enhaEulerToggle.setSelected(false);
        }
    }

    private Boolean checkParameters() {
        if (initialValueField == null || endTimeField.getText().isEmpty()){
            return false;
        }
        if (InitialTimeField == null || InitialTimeField.getText().isEmpty()){
            return false;
        }
        if (endTimeField == null || endTimeField.getText().isEmpty()){
            return false;
        }
        if (functionField == null || functionField.getText().isEmpty()){
            return false;
        }
        return true;
    }

}
