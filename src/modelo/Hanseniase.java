package modelo;
import java.time.LocalDate;

public class Hanseniase extends Notificacao {

    private final int numLesoesCutaneas;
    private final String formaClinica;
    private final String classificacaoOperacional;
    private final int numNervosAfetados;
    private final int grauIncapacidade;
    private final int modoDeteccao;
    private final int baciloscopia;
    private final LocalDate dataInicioTratamento;
    private final int esquemaTerapeuticoInicial;

    private static final String DELIMITADOR = "|";

    public Hanseniase(String ufNotificacao, String municipioNotificacao, String unidadeSaude,
                      LocalDate dataNotificacao, LocalDate dataDiagnostico, String nomePaciente,
                      LocalDate dataNascimento, String sexo, String gestante, int racaCor,
                      int escolaridade, String numCartaoSus, String municipioResidencia, String bairro,
                      String zona, int numLesoesCutaneas, String formaClinica, String classificacaoOperacional,
                      int numNervosAfetados, int grauIncapacidade, int modoDeteccao, int baciloscopia,
                      LocalDate dataInicioTratamento, int esquemaTerapeuticoInicial) {
        super("HANSENÍASE", "A 30.9", dataNotificacao, ufNotificacao, municipioNotificacao,
                unidadeSaude, dataDiagnostico, nomePaciente, dataNascimento, sexo, gestante,
                racaCor, escolaridade, numCartaoSus, municipioResidencia, bairro, zona);
        this.numLesoesCutaneas = numLesoesCutaneas;
        this.formaClinica = formaClinica;
        this.classificacaoOperacional = classificacaoOperacional;
        this.numNervosAfetados = numNervosAfetados;
        this.grauIncapacidade = grauIncapacidade;
        this.modoDeteccao = modoDeteccao;
        this.baciloscopia = baciloscopia;
        this.dataInicioTratamento = dataInicioTratamento;
        this.esquemaTerapeuticoInicial = esquemaTerapeuticoInicial;
    }

    @Override
    public String toFileString() {
        return gerarId() + DELIMITADOR +
                getAgravoDoenca() + DELIMITADOR + getCodigoCid10() + DELIMITADOR + getDataNotificacao() + DELIMITADOR +
                getUfNotificacao() + DELIMITADOR + getMunicipioNotificacao() + DELIMITADOR + getUnidadeSaude() + DELIMITADOR +
                getDataReferencia() + DELIMITADOR + getNomePaciente() + DELIMITADOR + getDataNascimento() + DELIMITADOR +
                getSexo() + DELIMITADOR + getGestante() + DELIMITADOR + getRacaCor() + DELIMITADOR +
                getEscolaridade() + DELIMITADOR + getNumCartaoSus() + DELIMITADOR + getMunicipioResidencia() + DELIMITADOR +
                getBairro() + DELIMITADOR + getZona() + DELIMITADOR +
                // Campos específicos
                numLesoesCutaneas + DELIMITADOR + formaClinica + DELIMITADOR + classificacaoOperacional + DELIMITADOR +
                numNervosAfetados + DELIMITADOR + grauIncapacidade + DELIMITADOR + modoDeteccao + DELIMITADOR +
                baciloscopia + DELIMITADOR + dataInicioTratamento + DELIMITADOR + esquemaTerapeuticoInicial;
    }

    public static Hanseniase fromFileStringStatic(String linha) {
        String[] partes = linha.split("\\" + DELIMITADOR);

        return new Hanseniase(
                partes[4], partes[5], partes[6],
                LocalDate.parse(partes[3]), LocalDate.parse(partes[7]), partes[8],
                LocalDate.parse(partes[9]), partes[10], partes[11], Integer.parseInt(partes[12]),
                Integer.parseInt(partes[13]), partes[14], partes[15], partes[16],
                partes[17], Integer.parseInt(partes[18]), partes[19], partes[20],
                Integer.parseInt(partes[21]), Integer.parseInt(partes[22]), Integer.parseInt(partes[23]), Integer.parseInt(partes[24]),
                LocalDate.parse(partes[25]), Integer.parseInt(partes[26])
        );
    }

    @Override
    public Notificacao fromFileString(String linha) {
        return Hanseniase.fromFileStringStatic(linha);
    }

    public String getClassificacaoOperacional() { return classificacaoOperacional; }
    public int getGrauIncapacidade() { return grauIncapacidade; }
}