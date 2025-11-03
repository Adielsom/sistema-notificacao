package servico;

import modelo.Notificacao;
import modelo.Hanseniase;
import modelo.Malaria;
import modelo.Tuberculose;
import util.ConversorDados;

import java.io.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NotificacaoService {

    private static final String NOME_ARQUIVO = "notificacoes_data.txt";
    private static final String DELIMITADOR = "|";

    public void registrarNotificacao(Notificacao notificacao) {
        try (FileWriter fw = new FileWriter(NOME_ARQUIVO, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(notificacao.toFileString());
            bw.newLine();
            System.out.println("\nNotificação de " + notificacao.getAgravoDoenca() + " registrada com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao salvar notificação: " + e.getMessage());
        }
    }

    public List<Notificacao> lerTodasNotificacoes() {
        List<Notificacao> notificacoes = new ArrayList<>();
        File arquivo = new File(NOME_ARQUIVO);

        if (!arquivo.exists() || arquivo.length() == 0) {
            return notificacoes;
        }

        try (FileReader fr = new FileReader(arquivo);
             BufferedReader br = new BufferedReader(fr)) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;

                String[] partes = linha.split("\\" + DELIMITADOR);
                if (partes.length < 2) continue; // Linha inválida

                String agravo = partes[1];
                Notificacao notificacao = null;

                switch (agravo) {
                    case "TUBERCULOSE":
                        notificacao = Tuberculose.fromFileStringStatic(linha);
                        break;
                    case "MALÁRIA":
                        notificacao = Malaria.fromFileStringStatic(linha);
                        break;
                    case "HANSENÍASE":
                        notificacao = Hanseniase.fromFileStringStatic(linha);
                        break;
                }

                if (notificacao != null) {
                    notificacoes.add(notificacao);
                }
            }
        } catch (FileNotFoundException e) {
        }
        catch (IOException e) {
            System.err.println("Erro ao ler notificações do arquivo: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro ao parsear linha do arquivo. Verifique a sintaxe dos dados. Detalhe: " + e.getMessage());
        }
        return notificacoes;
    }

    public List<Notificacao> consultarPorNome(String nomeBusca) {
        List<Notificacao> todas = lerTodasNotificacoes();
        String nomeNormalizado = nomeBusca.toLowerCase().trim();

        return todas.stream()
                .filter(n -> n.getNomePaciente().toLowerCase().contains(nomeNormalizado))
                .collect(Collectors.toList());
    }

    public List<Notificacao> consultarPorBairro(String bairroBusca) {
        List<Notificacao> todas = lerTodasNotificacoes();
        String bairroNormalizado = bairroBusca.toLowerCase().trim();

        return todas.stream()
                .filter(n -> n.getBairro().toLowerCase().contains(bairroNormalizado))
                .collect(Collectors.toList());
    }

    public List<Notificacao> consultarPorPeriodo(LocalDate inicio, LocalDate fim) {
        List<Notificacao> todas = lerTodasNotificacoes();
        return todas.stream()
                .filter(n -> !n.getDataNotificacao().isBefore(inicio) && !n.getDataNotificacao().isAfter(fim))
                .collect(Collectors.toList());
    }

    public List<Notificacao> consultarPorAgravo(String agravoBusca) {
        List<Notificacao> todas = lerTodasNotificacoes();
        String agravoNormalizado = agravoBusca.toUpperCase().trim();

        return todas.stream()
                .filter(n -> n.getAgravoDoenca().toUpperCase().contains(agravoNormalizado))
                .collect(Collectors.toList());
    }

    public Map<String, Long> relatorioTotalPorAgravo() {
        return lerTodasNotificacoes().stream()
                .collect(Collectors.groupingBy(Notificacao::getAgravoDoenca, Collectors.counting()));
    }

    public Map<String, Long> relatorioTotalPorBairro() {
        return lerTodasNotificacoes().stream()
                .collect(Collectors.groupingBy(Notificacao::getBairro, Collectors.counting()));
    }


    public Map<String, Long> relatorioTotalPorMesAno() {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("MM/yyyy");
        return lerTodasNotificacoes().stream()
                .collect(Collectors.groupingBy(n -> n.getDataNotificacao().format(formatador), Collectors.counting()));
    }

    public Map<String, Long> relatorioTotalPorFaixaEtaria() {
        LocalDate hoje = LocalDate.now();
        return lerTodasNotificacoes().stream()
                .collect(Collectors.groupingBy(n -> {
                    int idade = Period.between(n.getDataNascimento(), hoje).getYears();
                    return ConversorDados.converterIdadeParaFaixaEtaria(idade);
                }, Collectors.counting()));
    }

    public Map<String, Long> relatorioTotalPorSexo() {
        return lerTodasNotificacoes().stream()
                .collect(Collectors.groupingBy(n -> ConversorDados.converterCodigoSexo(n.getSexo()), Collectors.counting()));
    }

    public Map<String, Long> relatorioTotalPorRacaCor() {
        return lerTodasNotificacoes().stream()
                .collect(Collectors.groupingBy(n -> ConversorDados.converterCodigoRacaCor(n.getRacaCor()), Collectors.counting()));
    }

    public Map<String, Long> relatorioTotalPorEscolaridade() {
        return lerTodasNotificacoes().stream()
                .collect(Collectors.groupingBy(n -> ConversorDados.converterCodigoEscolaridade(n.getEscolaridade()), Collectors.counting()));
    }
}