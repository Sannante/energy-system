package com.example.energyproducer;

public class Main {
    public static void main(String[] args) {
        MessageSender sender = new MessageSender();

        while (true) {
            try {
                sender.send();
                Thread.sleep(5000); // 5 Sekunden Pause
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
