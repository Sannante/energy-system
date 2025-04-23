package com.example.energyfxgui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MainController {

    @FXML
    private Button loadButton;

    @FXML
    private TextArea outputArea;
    @FXML
    private TextField startDateInput;

    @FXML
    private TextField endDateInput;

    @FXML
    public void loadData() {
        String url = "http://localhost:8080/energy/current";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> javafx.application.Platform.runLater(() ->
                        outputArea.setText(response.body())
                ));
    }
    @FXML
    private void loadHistoricalData() {
        String start = startDateInput.getText().trim();
        String end = endDateInput.getText().trim();

        if (start.isEmpty() || end.isEmpty()) {
            outputArea.setText("Bitte Start- und Enddatum eingeben (z.B. 2025-04-10)");
            return;
        }

        String url = String.format("http://localhost:8080/energy/historical?start=%s&end=%s", start, end);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> javafx.application.Platform.runLater(() ->
                        outputArea.setText(response.body())));
    }

}
