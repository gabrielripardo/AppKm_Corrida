package folderapp.bar.Model;

public class Perfil {
    private int id;
    private String nome;
    private String foto;

    public Perfil(){

    }

    public Perfil(String nome, String foto) {
        this.nome = nome;
        this.foto = foto;
    }

    public Perfil(int id, String nome, String foto) {
        this.id = id;
        this.nome = nome;
        this.foto = foto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
