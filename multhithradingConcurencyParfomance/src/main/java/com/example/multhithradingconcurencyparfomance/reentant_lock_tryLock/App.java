package com.example.multhithradingconcurencyparfomance.reentant_lock_tryLock;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.FillTransition;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.multhithradingconcurencyparfomance.reentant_lock_tryLock.Currencies.fromName;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GridPane grid = createGrid();
        Map<Label, Label> cryptoLabels = Stream.of(Currencies.values())
                .collect(Collectors.toMap(this::createNameLabel, this::createValueLabel));
        addLabelsToGrid(cryptoLabels, grid);
        double width = 300;
        double height = 250;
        Rectangle background = createBackgroundRectangleWithAnimation(width, height);
        StackPane root = new StackPane(background, grid);

        primaryStage.setTitle("Cryptocurrency Prices");
        primaryStage.setScene(new Scene(root, width, height));

        PriceContainer pricesContainer = new PriceContainer();
        PriceUpdater priceUpdater = new PriceUpdater(pricesContainer);
        AnimationTimer animationTimer = createAnimationTimer(cryptoLabels, pricesContainer);

        addWindowResizeListener(primaryStage, background);

        animationTimer.start();
        priceUpdater.start();

        primaryStage.show();
    }

    private static AnimationTimer createAnimationTimer(Map<Label, Label> cryptoLabels, PriceContainer pricesContainer) {
        return new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (pricesContainer.getLock().tryLock()) {
                    try {
                        cryptoLabels.values().forEach(valLAbel ->
                                valLAbel.setText(pricesContainer.getPrice(fromName(valLAbel.getId()))));
                    } finally {
                        pricesContainer.getLock().unlock();
                    }
                }
            }
        };
    }

    private void addWindowResizeListener(Stage stage, Rectangle background) {
        ChangeListener<Number> stageSizeListener = ((observable, oldValue, newValue) -> {
            background.setHeight(stage.getHeight());
            background.setWidth(stage.getWidth());
        });
        stage.widthProperty().addListener(stageSizeListener);
        stage.heightProperty().addListener(stageSizeListener);
    }

    private Label createValueLabel(Currencies currency) {
        Label label = new Label("0");
        label.setId(currency.name());
        return label;
    }

    private Label createNameLabel(Currencies currency) {
        Label nameLabel = new Label(currency.name());
        nameLabel.setTextFill(Color.BLUE);
        nameLabel.setOnMousePressed(event -> nameLabel.setTextFill(Color.RED));
        nameLabel.setOnMouseReleased(event -> nameLabel.setTextFill(Color.BLUE));
        return nameLabel;
    }

    private GridPane createGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);
        return grid;
    }

    private void addLabelsToGrid(Map<Label, Label> labels, GridPane grid) {
        AtomicInteger row = new AtomicInteger(-1);
        labels.forEach((nameLabel, valLabel) -> grid.addRow(row.incrementAndGet(), nameLabel, valLabel));
    }

    private Rectangle createBackgroundRectangleWithAnimation(double width, double height) {
        Rectangle background = new Rectangle(width, height);
        FillTransition fillTransition = new FillTransition(Duration.millis(1000), background, Color.LIGHTGREEN, Color.LIGHTBLUE);
        fillTransition.setCycleCount(Animation.INDEFINITE);
        fillTransition.setAutoReverse(true);
        fillTransition.play();
        return background;
    }

    @Override
    public void stop() {
        System.exit(0);
    }
}
