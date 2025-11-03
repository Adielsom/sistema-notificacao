package modelo;
import java.time.LocalDate;

public class Malaria extends Notificacao {

    private final String principalAtividade15Dias;
    private final int sintomas;
    private final LocalDate dataExame;
    private final int resultadoExame;
    private final int esquemaTratamento;
    private final LocalDate dataInicioTratamento;
    private final int classificacaoFinal;
    private final String municipioProvavelInfeccao;

    private static final String DELIMITADOR = "|";

    public Malaria(String ufNotificacao, String municipioNotificacao, String unidadeSaude,
                   LocalDate dataNotificacao, LocalDate dataPrimeirosSintomas, String nomePaciente,
                   LocalDate dataNascimento, String sexo, String gestante, int racaCor,
                   int escolaridade, String numCartaoSus, String municipioResidencia, String bairro,
                   String zona, String principalAtividade15Dias, int sintomas, LocalDate dataExame,
                   int resultadoExame, int esquemaTratamento, LocalDate dataInicioTratamento,
                   int classificacaoFinal, String municipioProvavelInfeccao) {
        super("MALÁRIA", "B54", dataNotificacao, ufNotificacao, municipioNotificacao,
                unidadeSaude, dataPrimeirosSintomas, nomePaciente, dataNascimento, sexo, gestante,
                racaCor, escolaridade, numCartaoSus, municipioResidencia, bairro, zona);
        this.principalAtividade15Dias = principalAtividade15Dias;
        this.sintomas = sintomas;
        this.dataExame = dataExame;
        this.resultadoExame = resultadoExame;
        this.esquemaTratamento = esquemaTratamento;
        this.dataInicioTratamento = dataInicioTratamento;
        this.classificacaoFinal = classificacaoFinal;
        this.municipioProvavelInfeccao = municipioProvavelInfeccao;
    }

    @Override
    public String toFileString() {
        return gerarId() + DELIMITADOR +
                getAgravoDoenca() + DELIMITADOR + getCodigoCid10() + DELIMITADOR + getDataNotificacao() + DELIMITADOR +
                getUfNotificacao() + DELIMITADOR + getMunicipioNotificacao() + DELIMITADOR + getUnidadeSaude() + DELIMITADOR +
                getDataReferencia() + DELIMITADOR + getNomePaciente() + DELIMITADOR + getDataNascimento() + DELIMITADOR +
                getSexo() + DELIMITADOR + getGestante() + DELIMITADOR + getRacaCor() + DELIMITADOR + // CORREÇÃO: Usando DELIMITADOR
                getEscolaridade() + DELIMITADOR + getNumCartaoSus() + DELIMITADOR + getMunicipioResidencia() + DELIMITADOR +
                getBairro() + DELIMITADOR + getZona() + DELIMITADOR +
                // Campos específicos
                principalAtividade15Dias + DELIMITADOR + sintomas + DELIMITADOR + dataExame + DELIMITADOR +
                resultadoExame + DELIMITADOR + esquemaTratamento + DELIMITADOR + dataInicioTratamento + DELIMITADOR +
                classificacaoFinal + DELIMITADOR + municipioProvavelInfeccao;
    }

    public static Malaria fromFileStringStatic(String linha) {
        String[] partes = linha.split("\\" + DELIMITADOR);

        return new Malaria(
                partes[4], partes[5], partes[6],
                LocalDate.parse(partes[3]), LocalDate.parse(partes[7]), partes[8],
                LocalDate.parse(partes[9]), partes[10], partes[11], Integer.parseInt(partes[12]),
                Integer.parseInt(partes[13]), partes[14], partes[15], partes[16],
                partes[17], partes[18], Integer.parseInt(partes[19]), LocalDate.parse(partes[20]),
                Integer.parseInt(partes[21]), Integer.parseInt(partes[22]), LocalDate.parse(partes[23]),
                Integer.parseInt(partes[24]), partes[25]
        );
    }

    @Override
    public Notificacao fromFileString(String linha) {
        return Malaria.fromFileStringStatic(linha);
    }

    public int getResultadoExame() { return resultadoExame; }
    public int getClassificacaoFinal() { return classificacaoFinal; }
}