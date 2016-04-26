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


public class FastaFile {


    private String path;

    String file_name = "/home/aj/Dropbox/dnAJ/Novel Enzymes/week 11/test.fasta";
    private File file;


//    try {
//        file = new File(file_name);
//        String[] aryLines = file.OpenFile();
//
//        int i;
//        for ( i=0; i < aryLines.length; i++ ) {
//            System.out.println( aryLines[ i ] ) ;
//        }
//    }

    catch (IOException e){
        System.out.println( e.getMessage() );
    }

    public void parseFile(File file) throws IOException {
        FileReader file_to_read = new FileReader(file);
        BufferedReader bf = new BufferedReader(file_to_read);

        String aLine;
        StringReader sr = new StringReader(aLine);

        while ( (aLine = bf.readLine() ) != null ) {
            if ( aLine.startsWith( ">" ) ){

            }
            break;
        }

        bf.close();


    }

//    public String[] OpenFile() throws IOException{
//        FileReader fr = new FileReader(path);
//        BufferedReader textReader = new BufferedReader(fr);
//
//        int numberOfLines = parseFile();
//
//        String[] textData = new String[numberOfLines];
//
//        int i;
//        for (i=0; i < numberOfLines; i++) {
//            textData[ i ] = textReader.readLine();
//        }
//
//        textReader.close();
//        return textData;
//    }


    public String getProteins(){
        return something;
    }

}

