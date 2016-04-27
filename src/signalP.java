import java.lang.reflect.Array;

/**
 * Created by dnAJ on 26-Apr-16.
 */
public class signalP {
    public String prot;
    public String sig;
    public String conf;

    signalP( String prot, String sig, String conf){
        this.prot = prot;
        this.sig = sig;
        this.conf = conf;
    }

    String[] convertToArray(){
        String[] contentsSigP = new String[3];
        contentsSigP[0] = prot;
        contentsSigP[1] = sig;
        contentsSigP[2] = conf;

        return contentsSigP;
    }
}
