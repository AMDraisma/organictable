/**
 * Created by aj on 20-5-16.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class Mapping_from_fasta {

    public static String makeMapping (String path){
        int lines = 0;

        try{
            BufferedReader br = new BufferedReader(new FileReader(path));
            String aLine;
            String protAcc;
            String probeID;
//            String newLine;
            String[][] map = null;


            while ( ( aLine = br.readLine() ) != null ) {
                if ( aLine.startsWith( ">" ) ){
                    lines++;
                }
            }

            while ( ( aLine = br.readLine() ) != null ) {
                int i =0;
                if ( aLine.startsWith( ">" ) ) {
                    protAcc = aLine.substring( aLine.indexOf(">") +1, aLine.indexOf(" ") );
                    probeID = aLine.substring( aLine.indexOf("gene:") +1, aLine.indexOf(" transcript:") );
                    for (i : lines){
                    map[i][0] = probeID;
                    map[i][1] = protAcc;
                    }
                }
            }
            br.close();

        }catch(IOException e){
            System.out.println("Exception thrown  :" + e);
        }

        return "" + lines;
//        String never = "wtf wrong";
//        return never;
    }

}
