package com.example.energyfxgui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

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
                .thenAccept(response -> {
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        CurrentEnergy data = mapper.readValue(response.body(), CurrentEnergy.class);

                        javafx.application.Platform.runLater(() ->
                                outputArea.setText(
                                        "Zeitpunkt: " + data.hour + "\n" +
                                                "Community verbraucht: " + data.communityDepleted + " kWh\n" +
                                                "Netzanteil: " + data.gridPortion + " kWh"
                                ));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @FXML
    private void loadHistoricalData() {
        String start = startDateInput.getText().trim();
        String end = endDateInput.getText().trim();

        if (start.isEmpty() || end.isEmpty()) {
            outputArea.setText("Bitte Start- und Enddatum eingeben (z.B. 2025-06-11T13:00:00)");
            return;
        }

        String url = String.format("http://localhost:8080/energy/historical?start=%s&end=%s", start, end);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        HistoricalEnergy[] data = mapper.readValue(response.body(), HistoricalEnergy[].class);

                        StringBuilder sb = new StringBuilder();
                        for (HistoricalEnergy entry : data) {
                            sb.append("Zeitpunkt: ").append(entry.hour).append("\n")
                                    .append("Erzeugt: ").append(entry.produced).append(" kWh\n")
                                    .append("Verbraucht: ").append(entry.used).append(" kWh\n")
                                    .append("Netzanteil: ").append(entry.grid).append(" kWh\n\n");
                        }

                        javafx.application.Platform.runLater(() -> outputArea.setText(sb.toString()));
                    } catch (Exception e) {
                        e.printStackTrace();
                        javafx.application.Platform.runLater(() -> outputArea.setText("Fehler beim Verarbeiten der Daten."));
                    }
                });
    }
}

