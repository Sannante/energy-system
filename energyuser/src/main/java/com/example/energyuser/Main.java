package com.example.energyuser;

public class Main {
    public static void main(String[] args) {
        MessageSender sender = new MessageSender();

        while (true) {
            try {
                sender.send();
                Thread.sleep((long) (1000 + Math.random() * 4000)); // 1–5 Sekunden
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
