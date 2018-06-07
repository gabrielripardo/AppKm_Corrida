package folderapp.bar.Model;

import java.util.Date;

public class Corrida {
    private int id;
    private String comment;
    private float maxKm;
    private String maxTempo;
    private float km; // Sistema que introduz no banco
    private String tempo; // Sistema que introduz no banco
    private String diaHoraAno; //Sistema que introduz no banco
    private String horario; // Sistema que introduz no banco
    private boolean finalizada; // Sistema que introduz no banco

    public Corrida(){

    }

    public Corrida(String comment, float maxKm, String maxTempo){
        this.comment = comment;
        this.maxKm = maxKm;
        this.maxTempo = maxTempo;
    }

    public Corrida(int id, String comment, float maxKm, String maxTempo) {
        this.id = id;
        this.comment = comment;
        this.maxKm = maxKm;
        this.maxTempo = maxTempo;
    }

    public Corrida(String comment, float maxKm, float km, String maxTempo, String tempo, String diaHoraAno, String horario, boolean finalizada) {
        this.comment = comment;
        this.maxKm = maxKm;
        this.km = km;
        this.maxTempo = maxTempo;
        this.tempo = tempo;
        this.diaHoraAno = diaHoraAno;
        this.horario = horario;
        this.finalizada = finalizada;
    }

    public Corrida(int id, String comment, float maxKm, float km, String maxTempo, String tempo, String diaHoraAno, String horario, boolean finalizada) {
        this.id = id;
        this.comment = comment;
        this.maxKm = maxKm;
        this.km = km;
        this.maxTempo = maxTempo;
        this.tempo = tempo;
        this.diaHoraAno = diaHoraAno;
        this.horario = horario;
        this.finalizada = finalizada;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public float getMaxKm() {
        return maxKm;
    }

    public void setMaxKm(float maxKm) {
        this.maxKm = maxKm;
    }

    public float getKm() {
        return km;
    }

    public void setKm(float km) {
        this.km = km;
    }

    public String getMaxTempo() {
        return maxTempo;
    }

    public void setMaxTempo(String maxTempo) {
        this.maxTempo = maxTempo;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public String getDiaHoraAno() {
        return diaHoraAno;
    }

    public void setDiaHoraAno(String diaHoraAno) {
        this.diaHoraAno = diaHoraAno;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public boolean isFinalizada() {
        return finalizada;
    }

    public void setFinalizada(boolean finalizada) {
        this.finalizada = finalizada;
    }
}

