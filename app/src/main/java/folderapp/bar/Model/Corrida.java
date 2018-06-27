package folderapp.bar.Model;

import java.util.Date;

import folderapp.bar.R;

public class Corrida {
    private int id;
    private String comment;
    private float maxKm;
    private int maxTempo;
    private float km; // Sistema que introduz no banco //API Location ou algum outro sensor que detecta movimentação.
    private int tempo; // Sistema que introduz no banco
    private String diaMesAno; //Sistema que introduz no banco
    private String horario; // Sistema que introduz no banco
    private boolean finalizada; // Sistema que introduz no banco
    private int medalha;

    public Corrida(){

    }

    public Corrida(String comment, float maxKm, int maxTempo){
        this.comment = comment;
        this.maxKm = maxKm;
        this.maxTempo = maxTempo;
    }

    public Corrida(int id, String comment, float maxKm, int maxTempo) {
        this.id = id;
        this.comment = comment;
        this.maxKm = maxKm;
        this.maxTempo = maxTempo;
    }

    public Corrida(String comment, float maxKm, float km, int maxTempo, int tempo, String diaMesAno, String horario, boolean finalizada) {
        this.comment = comment;
        this.maxKm = maxKm;
        this.km = km;
        this.maxTempo = maxTempo;
        this.tempo = tempo;
        this.diaMesAno = diaMesAno;
        this.horario = horario;
        this.finalizada = finalizada;
    }

    public Corrida(int id, String comment, float maxKm, float km, int maxTempo, int tempo, String diaMesAno, String horario, boolean finalizada) {
        this.id = id;
        this.comment = comment;
        this.maxKm = maxKm;
        this.km = km;
        this.maxTempo = maxTempo;
        this.tempo = tempo;
        this.diaMesAno = diaMesAno;
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

    public int getMaxTempo() {
        return maxTempo;
    }

    public void setMaxTempo(int maxTempo) {
        this.maxTempo = maxTempo;
    }

    public int getTempo() {
        return tempo;
    }

    public void setTempo(int tempo) {
        this.tempo = tempo;
    }

    public String getdiaMesAno() {
        return diaMesAno;
    }

    public void setdiaMesAno(String diaMesAno) {
        this.diaMesAno = diaMesAno;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public int getMedalha() {
        return medalha;
    }

    public void setMedalha(int medalha) {
        this.medalha = medalha;
    }

    public boolean isFinalizada() {
        return finalizada;
    }

    public void setFinalizada(boolean finalizada) {
        this.finalizada = finalizada;
    }

    public String[] convetToHoursFormat(int min) {

        int minutos = min;
        int horas;

        horas = minutos/60;
        minutos = (minutos-(60*horas));

        return new String[]{String.valueOf(horas), String.valueOf(minutos)};
    }
    public void setMinutosMaxTempo(int minutos, int horas){
        this.setMaxTempo(minutos+horas*60);
    }
    public void setMinutosTempo(int minutos, int horas){
        this.setTempo(minutos+horas*60);
    }
    public int obterResource(){
        switch (getMedalha()){
            case 1:
                return R.drawable.ouro;
            case 2:
                return R.drawable.prata;
            case 3:
                return R.drawable.bronze;
        }
        return 0;
    }
}

