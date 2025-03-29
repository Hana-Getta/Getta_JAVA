package game;

import java.io.*;
import java.util.*;

public class GameRecord {

    public static void saveGameRecord(String userName, int score) {
        String fileName = "game_records.txt";
        List<String> records = new ArrayList<>();

        File file = new File(fileName);
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    records.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        records.add(userName + ":" + score);

        records.sort((a, b) -> {
            String[] entryA = a.split(":");
            String[] entryB = b.split(":");

            int scoreA = Integer.parseInt(entryA[1]);
            int scoreB = Integer.parseInt(entryB[1]);

            if (scoreA != scoreB) {
                return Integer.compare(scoreB, scoreA);
            }
            return entryA[0].compareTo(entryB[0]);
        });

        if (records.size() > 5) {
            records = records.subList(0, 5);
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (String record : records) {
                bw.write(record);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("════════════════════════════════════════");
        System.out.printf("🎯 %s님의 기록: %d\n", userName, score);
        System.out.println("\uD83D\uDC51 Top 5 기록");
        System.out.println("────────────────────────────────────────");
        for (int i = 0; i < records.size(); i++) {
            String medal = switch (i) {
                case 0 -> "🥇";
                case 1 -> "🥈";
                case 2 -> "🥉";
                default -> "\uD83C\uDF89";
            };
            System.out.printf("%s %-20s\n", medal, records.get(i));
        }

        System.out.println("════════════════════════════════════════");

    }
}