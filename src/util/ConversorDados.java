package util;

import java.time.format.DateTimeFormatter;

public class ConversorDados {

    public static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static String converterCodigoRacaCor(int codigo) {
        return switch (codigo) {
            case 1 -> "Branca";
            case 2 -> "Preta";
            case 3 -> "Amarela";
            case 4 -> "Parda";
            case 5 -> "Indígena";
            case 9 -> "Ignorado";
            default -> "Código Inválido";
        };
    }

    public static String converterCodigoEscolaridade(int codigo) {
        return switch (codigo) {
            case 0 -> "Analfabeto";
            case 1 -> "1ª a 4ª série incompleta do EF";
            case 2 -> "4ª série completa do EF";
            case 3 -> "5ª à 8ª série incompleta do EF";
            case 4 -> "Ensino fundamental completo";
            case 5 -> "Ensino médio incompleto";
            case 6 -> "Ensino médio completo";
            case 7 -> "Educação superior incompleta";
            case 8 -> "Educação superior completa";
            case 9 -> "Ignorado";
            case 10 -> "Não se aplica";
            default -> "Código Inválido";
        };
    }

    public static String converterCodigoSexo(String codigo) {
        return switch (codigo.toUpperCase()) {
            case "M" -> "Masculino";
            case "F" -> "Feminino";
            default -> "Ignorado/Inválido";
        };
    }

    public static String converterIdadeParaFaixaEtaria(int idade) {
        if (idade >= 0 && idade <= 4) return "0 a 4 anos";
        if (idade >= 5 && idade <= 9) return "5 a 9 anos";
        if (idade >= 10 && idade <= 19) return "10 a 19 anos";
        if (idade >= 20 && idade <= 39) return "20 a 39 anos";
        if (idade >= 40 && idade <= 59) return "40 a 59 anos";
        if (idade >= 60) return "60 anos ou mais";
        return "Idade Desconhecida";
    }
}