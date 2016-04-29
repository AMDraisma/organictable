/**
 * Created by dnAJ on 27-Apr-16.
 */
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;


public class Hmmer_Out extends FileParser {
    public ArrayList<Hammer_Pfam_Storage> pfamList;

    Hmmer_Out( OTDatabase database){
        super( database );
        this.pfamList = new ArrayList<Hammer_Pfam_Storage>();
        try {
            parseFile( OpenFile( "Hmmer output", null) );
            database.insertIntoDatabase("Hmmer", finalPfamArray(), null, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void parseFile(File file) throws IOException {
        FileReader file_to_read = new FileReader(file);
        BufferedReader bf = new BufferedReader(file_to_read);

        String aLine, alotOfLines;
        String prot=null, pfam=null, pfamext=null;
        //extracts protein accession en protein family van pfam output file
        while ( ( aLine = bf.readLine() ) != null ) {
            if ( aLine.startsWith( "//" )) {
                while ( !( alotOfLines = bf.readLine() ).startsWith( "#" ) ) {
                    // output houdt hiermee op
                    if (Objects.equals(alotOfLines, "[ok]")) {
                        break;
                    }
                    if( alotOfLines.startsWith("Query:") ){
                        prot = alotOfLines.substring( alotOfLines.indexOf("|") +1, alotOfLines.indexOf("[") -2);
                    }
                    if ( alotOfLines.startsWith(" ") ){
                        String[] fam =  alotOfLines.split( "[\\s]{2,}" );
                        if ( !fam[1].startsWith("-")&&!fam[1].startsWith("E")&&!fam[1].startsWith("#")) {
                            String[] questionorexcl = fam[1].split("\\s");
                            if (questionorexcl.length == 1) {
                                if (fam.length == 11) {
                                    pfam = fam[9];
                                    pfamext = fam[10];
                                    // heb dit hiernaartie gemoved, er moet voor elke unieke combinatie proteine en
                                    // pfam een pfam storage worden gemaakt en ingevoerd
                                    Hammer_Pfam_Storage h = new Hammer_Pfam_Storage(prot, pfam, pfamext);
                                    pfamList.add(h);
                                }
                            }
                        }
                    }



                }
            }
        }
        bf.close();

        finalPfamArray();
    }

    public String[][] finalPfamArray(){
        String[][] finalpfamArray = new String[pfamList.size()][];
        int i;
        for( i = 0 ; i < pfamList.size(); i++ ){
            finalpfamArray[i] =  pfamList.get(i).convertToArray();
        }
        return finalpfamArray;
    }

}

