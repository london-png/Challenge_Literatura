package com.aluracursos.Challenge_Literatura.util;

public class FormateadorDeTexto {

    //la usamos para que tome un texto largo y lo divida en lineas cortas para que no se salga de la pantalla
    public static String formatear(String text, int maxWidth) {
        if (text == null || text.trim().isEmpty()) {
            return "No disponible";
        }

        //se contruye el nuevo texto formateado
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

        //añade la ultima linea
        if (!currentLine.isEmpty()) {
            formatted.append(currentLine);
        }

        return formatted.toString();
    }
}