import java.io.*;
import java.util.ArrayList;

/**
 * Created by dnAJ on 26-Apr-16.
 */
public class SignalP_Out extends FileParser {
    public ArrayList<signalP> signalList;
    private String File_Name;

//    public java.util.ArrayList<String> getSignal(){
//        return signalList;
//    }

    SignalP_Out( OTDatabase database){
        super( database );
        this.signalList = new ArrayList<signalP>();
        try {
            parseFile( OpenFile( "short signalP output", "short_out") );
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void parseFile(File file) throws IOException {
        FileReader file_to_read = new FileReader(file);
        BufferedReader bf = new BufferedReader(file_to_read);

        String aLine;

        while ( (aLine = bf.readLine() ) != null ) {
            if ( aLine.startsWith( "#" ) != true ) {
                String prot = aLine.substring(0, aLine.indexOf(" ") - 1);
                String sig =  aLine.charAt(66) + "";
                String conf = null;
                signalP s = new signalP( prot, sig, conf);
                signalList.add(s);
            }
        }

        bf.close();
    }
}
