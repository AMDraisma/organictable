import java.util.ArrayList;
import java.util.List;


public class CSVRow {

    public static final String header = "Protein\t" +
            "Organism\t" +
            "A. clavatus NRRL 1 orths\t" +
            "A. flavus NRRL 3357 orths\t" +
            "A. fumigatus A1163 orths\t" +
            "A. nidulans FGSC A4 orths\t" +
            "A. niger ATCC 1015 orths\t" +
            "A. niger CBS 513.88 orths\t" +
            "A. oryzae RIB40 orths\t" +
            "A. terreus NIH2624 orths\t" +
            "N. fischeri NRRL 181 orths\t" +
            "SignalP sig\t" +
            "SignalP conf\t" +
            "Pfam-A family\t" +
            "Pfam-A family description\n";

    static class organism {
        public static final int A_clavatus_NRRL_1 = 0;
        public static final int A_flavus_NRRL_3357 = 1;
        public static final int A_fumigatus_A1163 = 2;
        public static final int A_nidulans_FGSC_A4 = 3;
        public static final int A_niger_ATCC_1015 = 4;
        public static final int A_niger_CBS_513_88 = 5;
        public static final int A_oryzae_RIB40 = 6;
        public static final int A_terreus_NIH2624 = 7;
        public static final int N_fischeri_NRRL_181 = 8;
    }

    private String protaccession;
    private String orgaccession;
    private ArrayList<ArrayList<String>> orths;
    private String sig;
    private String conf;
    private String pfam;
    private String pfamext;

    public CSVRow(String protaccession, String orgaccession, String sig, String conf, String pfam, String pfamext) {



        this.protaccession = protaccession;
        this.orgaccession = orgaccession;
        this.sig = sig;
        this.conf = conf;
        this.pfam = pfam;
        this.pfamext = pfamext;
        orths = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            this.orths.add(new ArrayList<String>());
        }
    }

    public void addOrth(int orth, String protaccession) {
        this.orths.get(orth).add(protaccession);
    }

    public void addOrth(String orth) {
        if (orth.startsWith("ACLA")) {
            addOrth(organism.A_clavatus_NRRL_1, orth);
        }
        if (orth.startsWith("AFL2T")) {
            addOrth(organism.A_flavus_NRRL_3357, orth);
        }
        if (orth.startsWith("AFUB")) {
            addOrth(organism.A_fumigatus_A1163, orth);
        }
        if (orth.startsWith("AN")) {
            addOrth(organism.A_nidulans_FGSC_A4, orth);
        }
        if (orth.endsWith("-mRNA")) {
            addOrth(organism.A_niger_ATCC_1015, orth);
        }
        if (orth.startsWith("An")) {
            addOrth(organism.A_niger_CBS_513_88, orth);
        }
        if (orth.startsWith("AO")) {
            addOrth(organism.A_oryzae_RIB40, orth);
        }
        if (orth.startsWith("ATET")) {
            addOrth(organism.A_terreus_NIH2624, orth);
        }
        if (orth.startsWith("NFIA")) {
            addOrth(organism.N_fischeri_NRRL_181, orth);
        }
    }

    public String getOrths() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            if (this.orths.get(i).size() > 0) {
                for (String prot : this.orths.get(i)) {
                    sb.append(prot);
                    sb.append(", ");
                }
            }else{
                sb.append("NA");
            }
            if (sb.length() > 0 && sb.charAt(sb.length()-1) == ',') {
                sb.deleteCharAt(sb.length() - 1);
            }
            sb.append("\t");
        }
        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }

    @Override
    public String toString () {
        return protaccession+"\t"+orgaccession+"\t"+getOrths()+"\t"+sig+"\t"+conf+"\t"+pfam+"\t"+pfamext+"\n";
    }
}
