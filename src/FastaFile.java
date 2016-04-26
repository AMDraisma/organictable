/**
 * Created by aj on 22-4-16.
 *
 * public
 * method (void) parse gene.fasta file
 *      open fasta (put in private arraylist)
 *      collect header
 *          protein accession (>numberhingy)
 *
 *public arraylist get proteins die returned
 */

import java.io.*;
import java.util.ArrayList;


public class FastaFile {

    public ArrayList proteinList = new ArrayList();
    private String File_Name ;

    public ArrayList<String> getProteins(){
        return proteinList;
    }

    FastaFile( String file_name ){
        File_Name = file_name;
        try {
            parseFile( new File(File_Name) );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void parseFile(File file) throws IOException {
        FileReader file_to_read = new FileReader(file);
        BufferedReader bf = new BufferedReader(file_to_read);

        String aLine = "x";
        StringReader sr = new StringReader(aLine);

        while ( (aLine = bf.readLine() ) != null ) {
            if ( aLine.startsWith( ">" ) ){
                String pr = aLine.substring( aLine.indexOf( ">" ) +1 , aLine.indexOf( " " ));
                proteinList.add(pr);
            }
        }
        bf.close();
    }
}

