/**
 * Created by dnAJ on 27-Apr-16.
 */
public class Hammer_Pfam_Storage {
    public String prot;
    public String pfam;
    public String pfamext;


    Hammer_Pfam_Storage( String prot, String pfam, String pfamext ){
        this.prot = prot;
        this.pfam = pfam;
        this.pfamext = pfamext;
    }

    String[] convertToArray(){
        String[] contentsPFam = new String[3];
        contentsPFam[0] = prot;
        contentsPFam[1] = pfam;
        contentsPFam[2] = pfamext;

        return contentsPFam;
    }
}

