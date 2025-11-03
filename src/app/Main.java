package app;

import modelo.Hanseniase;
import modelo.Malaria;
import modelo.Notificacao;
import modelo.Tuberculose;
import servico.NotificacaoService;
import util.ConversorDados;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final NotificacaoService service = new NotificacaoService();

    public static void main(String[] args) {
        exibirMenuPrincipal();
    }

    private static void exibirMenuPrincipal() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n=============================================");
            System.out.println("SISTEMA DE NOTIFICAÇÃO DE AGRAVOS ");
            System.out.println("=============================================");
            System.out.println("1. Registrar Nova Notificação");
            System.out.println("2. Consultar Notificações");
            System.out.println("3. Gerar Relatórios");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                if (scanner.hasNextInt()) {
                    opcao = scanner.nextInt();
                    scanner.nextLine();
                } else {
                    scanner.nextLine();
                    opcao = -1;
                }

                switch (opcao) {
                    case 1:
                        menuRegistrarNotificacao();
                        break;
                    case 2:
                        menuConsultarNotificacoes();
                        break;
                    case 3:
                        menuGerarRelatorios();
                        break;
                    case 0:
                        System.out.println("Encerrando o sistema. Até mais!");
                        break;
                    default:
                        if (opcao != -1) {
                            System.out.println("Opção inválida. Tente novamente.");
                        }
                }
            } catch (Exception e) {
                System.out.println("Ocorreu um erro inesperado na leitura do menu: " + e.getMessage());
                opcao = -1;
            }
        }
    }

    private static String pedirCampo(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static LocalDate pedirData(String prompt) {
        while (true) {
            try {
                String dataStr = pedirCampo(prompt + " (DD/MM/AAAA): ");
                return LocalDate.parse(dataStr, ConversorDados.FORMATO_DATA);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de data inválido. Use DD/MM/AAAA.");
            }
        }
    }

    private static int pedirInt(String prompt) {
        while (true) {
            try {
                String valorStr = pedirCampo(prompt);
                return Integer.parseInt(valorStr);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número inteiro.");
            }
        }
    }

    private static void menuRegistrarNotificacao() {
        System.out.println("\n--- REGISTRAR NOVA NOTIFICAÇÃO ---");
        System.out.println("1. Tuberculose");
        System.out.println("2. Malária");
        System.out.println("3. Hanseníase");
        System.out.print("Escolha o agravo (1-3): ");

        try {
            int escolha = pedirInt("");

            System.out.println("\n[1/3] Dados Gerais e de Notificação:");
            LocalDate dataNotificacao = pedirData("Data da Notificação");
            String ufNotif = pedirCampo("UF de Notificação ");
            String municipioNotif = pedirCampo("Município de Notificação");
            String unidadeSaude = pedirCampo("Unidade de Saúde");

            System.out.println("\n[2/3] Dados do Paciente:");
            String nomePaciente = pedirCampo("Nome do Paciente");
            LocalDate dataNasc = pedirData("Data de Nascimento");
            String sexo = pedirCampo("Sexo (M/F) ");
            String gestante = pedirCampo("Gestante (Ex: 1-1º Trimestre, 2-Não, 3-Não se aplica)");
            LocalDate dataReferencia;

            System.out.println("\n[3/3] Dados de Residência e Sociodemográficos:");
            String municipioResidencia = pedirCampo("Município de Residência");
            String bairro = pedirCampo("Bairro");
            String zona = pedirCampo("Zona (1-Urbana, 2-Rural, 3-Periurbana, 4-Ignorado): ");

            int racaCor = pedirInt("Raça/Cor (Ex: 1-Branca, 2-Parda, 3-Indígena): ");
            int escolaridade = pedirInt("Escolaridade (Ex: 0-Analfabeto, 1-Ensino médio completo, 2-Superior Completa): ");
            String cartaoSus = pedirCampo("Nº Cartão SUS: ");


            Notificacao novaNotificacao = null;

            switch (escolha) {
                case 1:
                    System.out.println("\n[Dados Específicos: TUBERCULOSE]");
                    dataReferencia = pedirData("Data do Diagnóstico");
                    int tipoEntrada = pedirInt("Tipo de Entrada (1-Caso Novo, 2-Recidiva, 3-Transferência): ");
                    String forma = pedirCampo("Forma (1-Pulmonar, 2-Extrapulmonar, 3-Mista): ");
                    String seExtra = (forma.equals("2") || forma.equals("3")) ? pedirCampo("Se Extrapulmonar (Ex: 1-Pleural, 6-Miliar, 7-Meningoencefálico): ") : "NA";
                    int hiv = pedirInt("HIV (1-Positivo, 2-Negativo, 4-Não Realizado): ");
                    int bacilo = pedirInt("Baciloscopia de Escarro (1-Positiva, 2-Negativa, 3-Não Realizada): ");
                    LocalDate dataInicioTrat = pedirData("Data Início do Tratamento Atual");
                    String doencasAss = pedirCampo("Doenças e Agravos Associados (Ex: Aids, Diabetes, Nenhuma): ");

                    novaNotificacao = new Tuberculose(ufNotif, municipioNotif, unidadeSaude, dataNotificacao,
                            dataReferencia, nomePaciente, dataNasc, sexo, gestante, racaCor,
                            escolaridade, cartaoSus, municipioResidencia, bairro, zona,
                            tipoEntrada, forma, seExtra, doencasAss, hiv, bacilo, dataInicioTrat);
                    break;

                case 2:
                    System.out.println("\n[Dados Específicos: MALÁRIA]");
                    dataReferencia = pedirData("Data dos Primeiros Sintomas ");
                    String atividade = pedirCampo("Principal Atividade nos Últimos 15 Dias (Ex: 4-Turismo, 5-Garimpagem): ");
                    int sintomas = pedirInt("Sintomas (1-Com sintomas, 2-Sem sintomas): ");
                    LocalDate dataExame = pedirData("Data do Exame");
                    int resultadoExame = pedirInt("Resultado do Exame (Ex: 1-Negativo, 2-F, 4-V): ");
                    int esquemaTrat = pedirInt("Esquema de tratamento utilizado (1-12, 99-Outro): ");
                    LocalDate dataInicioTratMalaria = pedirData("Data Início do Tratamento:");
                    int classificacaoFinal = pedirInt("Classificação Final (1-Confirmado, 2-Descartado): ");
                    String municipioInfeccao = pedirCampo("Município Provável da Infecção: ");

                    novaNotificacao = new Malaria(ufNotif, municipioNotif, unidadeSaude, dataNotificacao,
                            dataReferencia, nomePaciente, dataNasc, sexo, gestante, racaCor,
                            escolaridade, cartaoSus, municipioResidencia, bairro, zona,
                            atividade, sintomas, dataExame, resultadoExame, esquemaTrat,
                            dataInicioTratMalaria, classificacaoFinal, municipioInfeccao);
                    break;

                case 3:
                    System.out.println("\n[Dados Específicos: HANSENÍASE]");
                    dataReferencia = pedirData("Data do Diagnóstico");
                    int numLesoes = pedirInt("Nº de Lesões Cutâneas: ");
                    String formaClinica = pedirCampo("Forma Clínica (1-I, 2-T, 3-D, 4-V): ");
                    String classificacaoOp = pedirCampo("Classificação Operacional (1-PB, 2-MB): ");
                    int nervos = pedirInt("Nº de Nervos Afetados[cite: 82]: ");
                    int grauIncapacidade = pedirInt("Grau de Incapacidade (0-Grau Zero, 2-Grau II, 3-Não Avaliado): ");
                    int modoDetec = pedirInt("Modo de Detecção (1-Encaminhamento, 2-Demanda Espontânea, 4-Exame de Contatos): ");
                    int baciloscopia = pedirInt("Baciloscopia (1-Positiva, 2-Negativa, 3-Não realizada): ");
                    LocalDate dataInicioTratHans = pedirData("Data Início do Tratamento ");
                    int esquemaInicial = pedirInt("Esquema Terapêutico Inicial (1-PQT/PB/6 doses, 2-PQT/MB/12 doses): ");

                    novaNotificacao = new Hanseniase(ufNotif, municipioNotif, unidadeSaude, dataNotificacao,
                            dataReferencia, nomePaciente, dataNasc, sexo, gestante, racaCor,
                            escolaridade, cartaoSus, municipioResidencia, bairro, zona,
                            numLesoes, formaClinica, classificacaoOp, nervos, grauIncapacidade,
                            modoDetec, baciloscopia, dataInicioTratHans, esquemaInicial);
                    break;

                default:
                    System.out.println("Opção de agravo inválida.");
                    return;
            }

            service.registrarNotificacao(novaNotificacao);

        } catch (Exception e) {
            System.out.println("Ocorreu um erro no registro da notificação: " + e.getMessage());
        }
    }


    private static void menuConsultarNotificacoes() {
        System.out.println("\n--- CONSULTAR NOTIFICAÇÕES ---");
        System.out.println("1. Por Nome do Paciente");
        System.out.println("2. Por Bairro de Residência");
        System.out.println("3. Por Período de Notificação");
        System.out.println("4. Por Agravo/Doença");
        System.out.print("Escolha o critério de consulta (1-4): ");

        try {
            int escolha = pedirInt("");
            List<Notificacao> resultados = new ArrayList<>();

            switch (escolha) {
                case 1:
                    String nome = pedirCampo("Digite o nome ou parte do nome do paciente: ");
                    resultados = service.consultarPorNome(nome);
                    break;
                case 2:
                    String bairro = pedirCampo("Digite o Bairro de Residência: ");
                    resultados = service.consultarPorBairro(bairro);
                    break;
                case 3:
                    System.out.println("Informe o período de busca:");
                    LocalDate inicio = pedirData("Data de Início");
                    LocalDate fim = pedirData("Data Final");
                    resultados = service.consultarPorPeriodo(inicio, fim);
                    break;
                case 4:
                    String agravo = pedirCampo("Digite o nome do Agravo (Tuberculose, Malária ou Hanseníase): ");
                    resultados = service.consultarPorAgravo(agravo);
                    break;
                default:
                    System.out.println("Opção de consulta inválida.");
                    return;
            }

            exibirResultados(resultados);

        } catch (Exception e) {
            System.out.println("Erro na consulta: " + e.getMessage());
        }
    }

    private static void exibirResultados(List<Notificacao> resultados) {
        if (resultados.isEmpty()) {
            System.out.println("Nenhuma notificação encontrada para o critério fornecido.");
        } else {
            System.out.println("\n--- RESULTADOS DA CONSULTA (" + resultados.size() + " encontrados) ---");
            resultados.forEach(n -> System.out.println("- " + n.toString()));
            System.out.println("-------------------------------------");
        }
    }

    // --- MENU 3: GERAR RELATÓRIOS ---

    private static void menuGerarRelatorios() {
        System.out.println("\n--- GERAR RELATÓRIOS ---");
        System.out.println("1. Total de Notificações por Agravo");
        System.out.println("2. Total de Notificações por Bairro");
        System.out.println("3. Total de Notificações por Mês/Ano");
        System.out.println("4. Total de Notificações por Faixa Etária");
        System.out.println("5. Total de Notificações por Sexo");
        System.out.println("6. Total de Notificações por Raça/Cor");
        System.out.println("7. Total de Notificações por Escolaridade");
        System.out.print("Escolha o relatório (1-7): ");

        try {
            int escolha = pedirInt("");
            Map<String, Long> resultado = null;
            String titulo = "";

            switch (escolha) {
                case 1:
                    resultado = service.relatorioTotalPorAgravo();
                    titulo = "TOTAL POR AGRAVO";
                    break;
                case 2:
                    resultado = service.relatorioTotalPorBairro();
                    titulo = "TOTAL POR BAIRRO";
                    break;
                case 3:
                    resultado = service.relatorioTotalPorMesAno();
                    titulo = "TOTAL POR MÊS/ANO";
                    break;
                case 4:
                    resultado = service.relatorioTotalPorFaixaEtaria();
                    titulo = "TOTAL POR FAIXA ETÁRIA";
                    break;
                case 5:
                    resultado = service.relatorioTotalPorSexo();
                    titulo = "TOTAL POR SEXO";
                    break;
                case 6:
                    resultado = service.relatorioTotalPorRacaCor();
                    titulo = "TOTAL POR RAÇA/COR";
                    break;
                case 7:
                    resultado = service.relatorioTotalPorEscolaridade();
                    titulo = "TOTAL POR ESCOLARIDADE";
                    break;
                default:
                    System.out.println("Opção de relatório inválida.");
                    return;
            }

            exibirRelatorio(titulo, resultado);

        } catch (Exception e) {
            System.out.println("Erro ao gerar relatório: " + e.getMessage());
        }
    }

    private static void exibirRelatorio(String titulo, Map<String, Long> dados) {
        if (dados == null || dados.isEmpty()) {
            System.out.println("Não há dados registrados para gerar este relatório.");
            return;
        }
        System.out.println("\n--- RELATÓRIO: " + titulo + " ---");
        dados.forEach((chave, valor) -> System.out.printf("%-30s: %d\n", chave, valor));
        System.out.println("-------------------------------------");
        long total = dados.values().stream().mapToLong(Long::longValue).sum();
        System.out.printf("TOTAL GERAL DE NOTIFICAÇÕES: %d\n", total);
    }
}