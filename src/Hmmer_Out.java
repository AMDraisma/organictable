/**
 * Created by dnAJ on 27-Apr-16.
 */
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import static jdk.nashorn.internal.parser.TokenType.OR;


public class Hmmer_Out extends FileParser {
    public ArrayList<Hammer_Pfam_Storage> pfamList;

//    public java.util.ArrayList<String> getSignal(){
//        return finalSigpArray();
//    }

    Hmmer_Out( OTDatabase database){
        super( database );
        this.pfamList = new ArrayList<Hammer_Pfam_Storage>();
        try {
            parseFile( OpenFile( "Hmmer output", null) );
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void parseFile(File file) throws IOException {
        FileReader file_to_read = new FileReader(file);
        BufferedReader bf = new BufferedReader(file_to_read);

        String aLine, anotherLine, alotOfLines;
        //extracts protein accession en protein family van pfam output file
        while ( ( aLine = bf.readLine() ) != null ) {
            if ( aLine.startsWith( "//" ) == true ) {
                while ( ( alotOfLines = bf.readLine() ).startsWith( "#" ) != true ) {
                    String prot=null, pfam=null, pfamext=null;
                    if( alotOfLines.startsWith("Query:") == true ){
                        prot = alotOfLines.substring( alotOfLines.indexOf("|") +1, alotOfLines.indexOf("[") -2);
                    }
                    if ( alotOfLines.startsWith(" ") == true ){
                        String[] fam =  alotOfLines.split( "[\\s]{2,}" );
                        if ( !fam[1].startsWith("-")&&!fam[1].startsWith("E")&&!fam[1].startsWith("#")) {
                            String[] questionorexcl = fam[1].split("\\s");
                            if (questionorexcl.length == 1) {
                                pfam = fam[9];
                                pfamext = fam[10];
                            }
                        }
                    }

                Hammer_Pfam_Storage h = new Hammer_Pfam_Storage(prot, pfam, pfamext);
                pfamList.add(h);

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

