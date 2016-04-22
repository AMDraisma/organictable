import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Klasse voor individuele orthologe groepbestanden
 */
public class OrthologFile extends FileParser {
    File file;
    ArrayList<OrthologGroup> orthologGroups;


    public OrthologFile() {
        file = OpenFile("OrthoMCL groups file", "txt");
        orthologGroups = new ArrayList<OrthologGroup>();
        if (null != file) {
            parse(file);
        }
    }

    private void parse(File file) {
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(file));
            String line;
            String[] elements, groupinfo;
            OrthologGroup group;
            while ((line = fileReader.readLine()) != null) {
                // splitst de gelezen lijn (een hele orthologe groep) en zet het resultaat in een array
                // de :? houdt in dat een dubbele punt optioneel is (nul of een keer)
                // daarna wordt een spatie gevonden
                // ":? " matcht dus alleen het volgende: "? " en " "
                elements = line.split(":? ");
                // hetzelfde verhaal, splitten op underscore
                groupinfo = elements[0].split("_");
                // elements[0] is altijd code_groepnummer, dus na het splitsen op underscore is
                // groupinfo[0] de 4-letterige code
                // groupinfo[1] is het oplopende id van de groep.
                // hier instantieren we een orthologe groepklasse met deze gegevens
                group = new OrthologGroup(groupinfo[0], Integer.parseInt(groupinfo[1]));
                // door i te laten beginnen op 1 slaan we het eerste element over (de info van de orthologe groep)
                for (int i = 1; i < elements.length; i++) {
                    // en voegen we elke proteine van elke species in de groep toe aan het object
                    group.addOrthologGroup(elements[i].split("|"));
                }
                System.out.println(group.toString());
//                break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // set de vierletterige code in groups.txt om naar volledige accession voor database
    public static String codeToAccession(String code) {
        if (Objects.equals(code, "acla")) {
            return "A_clavatus_NRRL_1";
        }
        if (Objects.equals(code, "afis")) {
            return "N_fischeri_NRRL_181";
        }
        if (Objects.equals(code, "afla")) {
            return "A_flavus_NRRL_3357";
        }
        if (Objects.equals(code, "afum")) {
            return "A_fumigatus_A1163";
        }
        if (Objects.equals(code, "anid")) {
            return "A_nidulans_FGSC_A4";
        }
        if (Objects.equals(code, "anig")) {
            return "A_niger_ATCC_1015";
        }
        if (Objects.equals(code, "aory")) {
            return "A_oryzae_RIB40";
        }
        if (Objects.equals(code, "ater")) {
            return "A_terreus_NIH2624";
        }
        if (Objects.equals(code, "ang1")) {
            return "A_niger_ATCC_1015";
        }
        if (Objects.equals(code, "ang2")) {
            return "A_niger_CBS_513_88";
        }
        return null;
    }

    public ArrayList<OrthologGroup> getOrthologGroups () {
        return orthologGroups;
    }
}
