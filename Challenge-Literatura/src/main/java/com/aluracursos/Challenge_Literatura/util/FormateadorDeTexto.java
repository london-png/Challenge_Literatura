package com.aluracursos.Challenge_Literatura.util;

public class FormateadorDeTexto {


    public static String formatear(String text, int maxWidth) {
        if (text == null || text.trim().isEmpty()) {
            return "No disponible";
        }

        StringBuilder formatted = new StringBuilder();
        String[] words = text.split("\\s+");
        String currentLine = "";

        for (String word : words) {
            if ((currentLine + " " + word).length() > maxWidth && !currentLine.isEmpty()) {
                formatted.append(currentLine).append("\n      ");
                currentLine = word;
            } else {
                if (!currentLine.isEmpty()) {
                    currentLine += " ";
                }
                currentLine += word;
            }
        }

        if (!currentLine.isEmpty()) {
            formatted.append(currentLine);
        }

        return formatted.toString();
    }
}