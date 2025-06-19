package com.example.energyfxgui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.application.Platform;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class MainController {

    @FXML
    private TextArea outputArea;

    @FXML
    private Button loadButton;

    @FXML
    private Button loadHistoricalButton;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private ComboBox<String> startTimeBox;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private ComboBox<String> endTimeBox;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HttpClient httpClient = HttpClient.newHttpClient();

    @FXML
    public void initialize() {
        loadButton.setOnAction(e -> loadCurrentData());
        loadHistoricalButton.setOnAction(e -> loadHistoricalData());

        // Zeitboxen initialisieren (stundenweise)
        ObservableList<String> hours = FXCollections.observableArrayList();
        for (int h = 0; h < 24; h++) {
            hours.add(String.format("%02d:00", h));
        }
        startTimeBox.setItems(hours);
        endTimeBox.setItems(hours);

        // Standardwerte setzen
        LocalDate today = LocalDate.now();
        startDatePicker.setValue(today);
        endDatePicker.setValue(today);
        startTimeBox.setValue("08:00");
        endTimeBox.setValue("18:00");
    }

    private void loadCurrentData() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/energy/current"))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            CurrentEnergy current = objectMapper.readValue(response.body(), CurrentEnergy.class);

            String output = String.format("Aktuelle Energie-Daten:\nCommunity: %.2f%%\nGrid: %.2f%%",
                    current.communityPercentage(), current.gridPercentage());

            outputArea.setText(output);

        } catch (Exception ex) {
            outputArea.setText("Fehler beim Laden der aktuellen Daten: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void loadHistoricalData() {
        try {
            String start = getIsoDateTime(startDatePicker, startTimeBox);
            String end = getIsoDateTime(endDatePicker, endTimeBox);

            // 1. Einzelstunden abrufen
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/energy/historical?start=" +
                            URLEncoder.encode(start, StandardCharsets.UTF_8) +
                            "&end=" +
                            URLEncoder.encode(end, StandardCharsets.UTF_8)))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            List<HistoricalEnergy> data = objectMapper.readValue(
                    response.body(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, HistoricalEnergy.class)
            );

            StringBuilder builder = new StringBuilder("Historische Daten:\n");
            for (HistoricalEnergy h : data) {
                builder.append(String.format("Stunde: %s\n  Produziert: %.3f kWh\n  Verbraucht: %.3f kWh\n  Grid: %.3f kWh\n\n",
                        h.getHour(), h.getProduced(), h.getUsed(), h.getGrid()));
            }
            // 2. Summen lokal berechnen
            double sumProduced = 0;
            double sumUsed = 0;
            double sumGrid = 0;

            for (HistoricalEnergy h : data) {
                sumProduced += h.getProduced();
                sumUsed += h.getUsed();
                sumGrid += h.getGrid();
            }

            builder.append("\n--- Gesamt im Zeitraum ---\n");
            builder.append(String.format("Gesamt produziert: %.3f kWh\n", sumProduced));
            builder.append(String.format("Gesamt verbraucht: %.3f kWh\n", sumUsed));
            builder.append(String.format("Gesamt vom Grid: %.3f kWh\n", sumGrid));


            outputArea.setText(builder.toString());

        } catch (Exception ex) {
            outputArea.setText("Fehler beim Laden der historischen Daten: " + ex.getMessage());
            ex.printStackTrace();
        }
    }


    private String getIsoDateTime(DatePicker picker, ComboBox<String> box) {
        LocalDate date = picker.getValue();
        String time = box.getValue();
        return date + "T" + time;
    }
}
