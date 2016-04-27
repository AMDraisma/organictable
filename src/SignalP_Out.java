import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by dnAJ on 26-Apr-16.
 */
public class SignalP_Out extends FileParser {
    public ArrayList<signalP> signalList;

//    public java.util.ArrayList<String> getSignal(){
//        return signalList;
//    }

    SignalP_Out( OTDatabase database){
        super( database );
        this.signalList = new ArrayList<signalP>();
        try {
            parseFile( OpenFile( "file.short_out", "short_out") );
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void parseFile(File file) throws IOException {
        FileReader file_to_read = new FileReader(file);
        BufferedReader bf = new BufferedReader(file_to_read);

        String aLine;
        //extracts protein accession, signal boolean and d-score from signalp short_output file
        while ( (aLine = bf.readLine() ) != null ) {
            if ( aLine.startsWith( "#" ) != true ) {
                String prot = aLine.substring(0, aLine.indexOf(" ") - 1);

                String[] sig =  aLine.split( "[^N\"Y]" );
                String sigp = Arrays.toString(sig).substring( Arrays.toString(sig).length() -2, Arrays.toString(sig).length() -1);

                String[] conf = aLine.split( "\\s[N|Y]\\s.*" );
                String confi = Arrays.toString(conf).substring( Arrays.toString(conf).length() -6, Arrays.toString(conf).length() -1 );

                signalP s = new signalP(prot, sigp, confi);
                signalList.add(s);
            }
        }
        bf.close();

        finalSigpArray();
    }

    public String[][] finalSigpArray(){
        String[][] finalsigpArray = new String[signalList.size()][];
        int i;
        for( i = 0 ; i < signalList.size(); i++ ){
            finalsigpArray[i] =  signalList.get(i).convertToArray();
        }
        return finalsigpArray;
    }

}
