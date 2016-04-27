import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Klasse voor orthologe groepen met proteine behorende aan species
 */
public class OrthologGroup {
    private String id;      // id van groep, in ons geconf 8asp
    private int nr;         // nummer van groep, in ons geconf het oplopende getal vanaf 1000
    // dit zijn dus de id_nr in onze groups.txt bestanden (8asp_1000)

    // een arraylist van een string array.
    // we weten niet hoeveel proteinen er in een groep zitten, vandaar een arraylist
    // we weten wel dat elke proteine in een groep wordt gedefinieerd door middel van organisme|proteine, zoals:
    // acla|ACLA_004450
    // vandaar dat we een array kunnen gebruikem met twee elementen
    private ArrayList<String[]> proteins;

   public OrthologGroup (String id, int nr) {
       this.id = id;
       this.nr = nr;
       proteins = new ArrayList<String[]>();
    }

    public void addOrthologGroup(String[] info) {
        info[0] = OrthologFile.codeToAccession(info[0]);
        proteins.add(info);
    }

    @Override
    public String toString() {
        return "Ortholog group "+id+"_"+nr+", size: "+proteins.size()+" proteins";
    }

    public String[][] getSet() {
        String[][] set = new String[proteins.size()][3];
        for (int i = 0; i < proteins.size(); i++) {
            set[i][0] = ""+id;
            set[i][1] = proteins.get(i)[0];
            set[i][2] = proteins.get(i)[1];
        }
        return set;
    }

    public ArrayList<String[]> getGroupData() {
        return proteins;
    }
}
