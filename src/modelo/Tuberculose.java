package modelo;
import java.time.LocalDate;

public class Tuberculose extends Notificacao {

    private final int tipoEntrada;
    private final String forma;
    private final String seExtrapulmonar;
    private final String doencasAgravosAssociados;

    private final int hiv;
    private final int baciloscopiaDiagnostico;
    private final LocalDate dataInicioTratamento;

    private static final String DELIMITADOR = "|";

    public Tuberculose(String ufNotificacao, String municipioNotificacao, String unidadeSaude,
                       LocalDate dataNotificacao, LocalDate dataDiagnostico, String nomePaciente,
                       LocalDate dataNascimento, String sexo, String gestante, int racaCor,
                       int escolaridade, String numCartaoSus, String municipioResidencia, String bairro,
                       String zona, int tipoEntrada, String forma, String seExtrapulmonar,
                       String doencasAgravosAssociados, int hiv, int baciloscopiaDiagnostico,
                       LocalDate dataInicioTratamento) {
        super("TUBERCULOSE", "A 16.9", dataNotificacao, ufNotificacao, municipioNotificacao,
                unidadeSaude, dataDiagnostico, nomePaciente, dataNascimento, sexo, gestante,
                racaCor, escolaridade, numCartaoSus, municipioResidencia, bairro, zona);
        this.tipoEntrada = tipoEntrada;
        this.forma = forma;
        this.seExtrapulmonar = seExtrapulmonar;
        this.doencasAgravosAssociados = doencasAgravosAssociados;
        this.hiv = hiv;
        this.baciloscopiaDiagnostico = baciloscopiaDiagnostico;
        this.dataInicioTratamento = dataInicioTratamento;
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
                tipoEntrada + DELIMITADOR + forma + DELIMITADOR + seExtrapulmonar + DELIMITADOR +
                doencasAgravosAssociados + DELIMITADOR + hiv + DELIMITADOR + baciloscopiaDiagnostico + DELIMITADOR +
                dataInicioTratamento;
    }

    public static Tuberculose fromFileStringStatic(String linha) {
        String[] partes = linha.split("\\" + DELIMITADOR);

        return new Tuberculose(
                partes[4], partes[5], partes[6],
                LocalDate.parse(partes[3]), LocalDate.parse(partes[7]), partes[8],
                LocalDate.parse(partes[9]), partes[10], partes[11], Integer.parseInt(partes[12]),
                Integer.parseInt(partes[13]), partes[14], partes[15], partes[16],
                partes[17], Integer.parseInt(partes[18]), partes[19], partes[20],
                partes[21], Integer.parseInt(partes[22]), Integer.parseInt(partes[23]), LocalDate.parse(partes[24])
        );
    }

    @Override
    public Notificacao fromFileString(String linha) {
        return Tuberculose.fromFileStringStatic(linha);
    }

    public int getTipoEntrada() { return tipoEntrada; }
    public String getForma() { return forma; }
    public int getHiv() { return hiv; }
}