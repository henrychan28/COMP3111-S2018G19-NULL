package ui.comp3111;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class testing extends Application{    

private final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
private ScheduledFuture future;

private Random random = new Random();

private int[] getData() {
    int[] result = new int[10];
    for (int i = 0; i < result.length; i++) {
        result[i] = random.nextInt(10);
    }
    return result;
}

private static void dataToSeries(int[] data, XYChart.Series<Number, Number> series) {
    int len = data.length;

    int size = series.getData().size();
    if (size > len) {
        series.getData().subList(len, series.getData().size()).clear();
    } else if (size < len) {
        for (; size < len; size++) {
            series.getData().add(new XYChart.Data<>(0, size));
        }
    }

    for (int i = 0; i < len; i++) {
        series.getData().get(i).setXValue(data[i]);
    }
}



@Override
public void start(Stage primaryStage) {
    ToggleButton btn = new ToggleButton("updating");
    btn.setVisible(true);
    btn.setSelected(false);
    //ObservableBooleanValue x = false;

    XYChart.Series<Number, Number> series = new XYChart.Series<>();

    LineChart<Number, Number> chart = new LineChart<>(new NumberAxis(), new NumberAxis(), FXCollections.observableArrayList(series));
    chart.setAnimated(false);

    Runnable dataGetter = () -> {
        try {
            // simulate some delay caused by the io operation
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
        }
        int[] data = getData();
        Platform.runLater(() -> {
            // update ui
            dataToSeries(data, series);
        });
    };


    btn.selectedProperty().addListener((a, b, newValue) -> {
     // x.addListener((a, b, newValue) -> {

    	btn.setText("Stop");
        if (newValue) {
            // update every second
            future = service.scheduleWithFixedDelay(dataGetter, 0, 1, TimeUnit.SECONDS);
        } else {
            // stop updates
            future.cancel(true);
            future = null;
        }
    });

    VBox root = new VBox(10, chart, btn);

    Scene scene = new Scene(root);

    primaryStage.setScene(scene);
    primaryStage.show();
}

@Override
public void stop() throws Exception {
    service.shutdownNow();
}
public static void main(String[] args) {
	launch(args);
}
}
    