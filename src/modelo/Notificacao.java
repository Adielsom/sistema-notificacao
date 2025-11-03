package modelo;

import java.io.Serializable;
import java.time.LocalDate;
import util.ConversorDados;

public abstract class Notificacao implements Serializable {

    private final String agravoDoenca;
    private final String codigoCid10;
    private final LocalDate dataNotificacao;
    private final String ufNotificacao;
    private final String municipioNotificacao;
    private final String unidadeSaude;

    private final LocalDate dataReferencia;
    private final String nomePaciente;
    private final LocalDate dataNascimento;
    private final String sexo;
    private final String gestante;
    private final int racaCor;
    private final int escolaridade;
    private final String numCartaoSus;

    private final String municipioResidencia;
    private final String bairro;
    private final String zona;

    public Notificacao(String agravoDoenca, String codigoCid10, LocalDate dataNotificacao, String ufNotificacao,
                       String municipioNotificacao, String unidadeSaude, LocalDate dataReferencia,
                       String nomePaciente, LocalDate dataNascimento, String sexo, String gestante,
                       int racaCor, int escolaridade, String numCartaoSus, String municipioResidencia,
                       String bairro, String zona) {
        this.agravoDoenca = agravoDoenca;
        this.codigoCid10 = codigoCid10;
        this.dataNotificacao = dataNotificacao;
        this.ufNotificacao = ufNotificacao;
        this.municipioNotificacao = municipioNotificacao;
        this.unidadeSaude = unidadeSaude;
        this.dataReferencia = dataReferencia;
        this.nomePaciente = nomePaciente;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
        this.gestante = gestante;
        this.racaCor = racaCor;
        this.escolaridade = escolaridade;
        this.numCartaoSus = numCartaoSus;
        this.municipioResidencia = municipioResidencia;
        this.bairro = bairro;
        this.zona = zona;
    }

    public abstract String toFileString();
    public abstract Notificacao fromFileString(String linha);

    public String gerarId() {
        return this.agravoDoenca.substring(0, 3) + "_" + this.dataNotificacao.toString() + "_" + nomePaciente.replaceAll("\\s", "_");
    }

    public String getAgravoDoenca() { return agravoDoenca;}
    public LocalDate getDataNotificacao() { return dataNotificacao;}
    public String getNomePaciente() { return nomePaciente; }
    public LocalDate getDataNascimento() { return dataNascimento;}
    public String getSexo() { return sexo; }
    public int getRacaCor() { return racaCor;}
    public int getEscolaridade() { return escolaridade;}
    public String getBairro() { return bairro; }
    public String getZona() { return zona; }
    public String getCodigoCid10() { return codigoCid10;}
    public LocalDate getDataReferencia() { return dataReferencia; }

    public String getUfNotificacao() { return ufNotificacao; }
    public String getMunicipioNotificacao() { return municipioNotificacao;}
    public String getUnidadeSaude() { return unidadeSaude; }
    public String getGestante() { return gestante; }
    public String getNumCartaoSus() { return numCartaoSus; }
    public String getMunicipioResidencia() { return municipioResidencia; }


    @Override
    public String toString() {
        return String.format("Agravo: %s | Paciente: %s | Bairro: %s | Data Notificação: %s",
                agravoDoenca, nomePaciente, bairro, dataNotificacao.format(ConversorDados.FORMATO_DATA));
    }
}