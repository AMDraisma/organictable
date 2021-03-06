import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

/**
 * Dit is een overkoepelende klasse voor fileparsers.
 * hierin komen algemene functies die we voor alle soorten bestanden kunnen gebruiken
 * voornamelijk het openen van bestanden is van belang hier
 */
public class FileParser extends JFrame {

    OTDatabase database;

    public FileParser(OTDatabase database) {
        this.database = database;
    }

    // neemt de beschrijving van de bestanden welke worden geparsed en de extensies als argumenten
    // geeft een bestand terug welke de gebruiker heeft geselecteerd in een open bestand dialoog
    public File OpenFile(String description, String[] extensions) {
        // de filechooser class is een klasse welke een open file window kan oproepen
        JFileChooser filechooser = new JFileChooser();

        // zorg ervoor dat alleen bestanden, geen folders kunnen worden geselecteerd
        filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        // zorg ervoor dat alleen .extensie bestanden kunnen worden geselecteerd als er een extensie is gedefinieerd
        if (extensions != null) {
            filechooser.setAcceptAllFileFilterUsed(false);
            // we filteren op bestanden met de extensie welke is doorgegeven aan de constructor
            filechooser.setFileFilter(new FileNameExtensionFilter(description, extensions));
        }else{
            filechooser.setAcceptAllFileFilterUsed(true);
        }



        // het argument van showopendialog is een jframe, zoals het hoofdscherm. hebben we niet nodig.
        // de integer welke de filechooser teruggeeft zegt iets over wat de gebruiker heeft gedaan
        int returnconfue = filechooser.showOpenDialog(null);
        // filechooser geeft APPROVE_OPTION terug als het selecteren goed is gegaan
        if (returnconfue == JFileChooser.APPROVE_OPTION) {
            System.out.println(filechooser.getSelectedFile().getName());
            return filechooser.getSelectedFile();
        }else{
            return null;
        }
    }


}
