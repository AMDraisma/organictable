/**
 * Created by aj on 20-5-16.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class Mapping {
    String[][] map = null;

    public String[][] makeMapping (String path){

        try{
            BufferedReader br = new BufferedReader(new FileReader(path));
            String aLine;
            int numberofProts = 0;
            int i = 0;
            String protAcc;
            String probeID;



            while ( ( aLine = br.readLine() ) != null ) {
                if ( aLine.startsWith( ">" ) ){
                    numberofProts++;
                }
            }

            map = new String[numberofProts][2];

            br = new BufferedReader(new FileReader(path));
            while ( ( aLine = br.readLine() ) != null ) {
                if ( aLine.startsWith( ">" ) ) {
                    protAcc = aLine.substring( aLine.indexOf(">") +1, aLine.indexOf(" ") );
                    probeID = aLine.substring( aLine.indexOf("gene:") +5, aLine.indexOf(" transcript:") );

                    map[i][0] = probeID;
                    map[i][1] = protAcc;
                    i++;
                }
            }
            br.close();

        }catch(IOException e){
            System.out.println("Exception thrown  :" + e);
        }

        return map;
    }

    public static String geneToProt(String geneID){

        return;
    }

}
