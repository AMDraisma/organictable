import java.util.ArrayList;
import java.util.Objects;


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
            "Pfam-A family description\t" +
            "Glucose\t" +
            "Xylose\t" +
            "Maltose\t" +
            "Glycerol\t" +
            "Sucrose\t" +
            "Xylan\t" +
            "Avicel\t" +
            "Rice straw\t" +
            "Nocarbon\n";

    static class Organism {
        public static final int A_clavatus_NRRL_1 = 0;
        public static final int A_flavus_NRRL_3357 = 1;
        public static final int A_fumigatus_A1163 = 2;
        public static final int A_nidulans_FGSC_A4 = 3;
        public static final int A_niger_ATCC_1015 = 4;
        public static final int A_niger_CBS_513_88 = 5;
        public static final int A_oryzae_RIB40 = 6;
        public static final int A_terreus_NIH2624 = 7;
        public static final int N_fischeri_NRRL_181 = 8;
        public static final int A_niger_BO1 = 9;
        public static final int A_oryzae_A1560 = 10;
        public static final int A_fumigatus_Z5 = 11;

        public static int parseString(String s) {
            if (Objects.equals(s, "A_oryzae_RIB40"))
                return A_oryzae_RIB40;
            if (Objects.equals(s, "A_niger_BO1"))
                return A_niger_BO1;
            if (Objects.equals(s, "A_niger_ATCC_1015"))
                return A_niger_BO1;
//                return A_niger_ATCC_1015;
            if (Objects.equals(s, "A_oryzae_A1560"))
                return A_oryzae_RIB40;
//                return A_oryzae_A1560;
            if (Objects.equals(s, "A_nidulans_FGSC_A4"))
                return A_nidulans_FGSC_A4;
            if (Objects.equals(s, "A_fumigatus_Z5"))
                return A_fumigatus_Z5;
            System.out.println(s);
            return -1;
        }
    }

    // TODO: this entire thing would look nicer with enums
    static class Substrate {
        public static final int Glucose = 0;
        public static final int Xylose = 1;
        public static final int Maltose = 2;
        public static final int Glycerol = 3;
        public static final int Sucrose = 4;
        public static final int Xylan = 5;
        public static final int Avicel = 6;
        public static final int Ricestraw = 7;
        public static final int Nocarbon = 8;

        public static int parseString(String s) {
            if (Objects.equals(s, "Glucose"))
                return Glucose;
            if (Objects.equals(s, "Xylose"))
                return Xylose;
            if (Objects.equals(s, "Maltose"))
                return Maltose;
            if (Objects.equals(s, "Glycerol"))
                return Glycerol;
            if (Objects.equals(s, "Sucrose"))
                return Sucrose;
            if (Objects.equals(s, "Xylan"))
                return Xylan;
            if (Objects.equals(s, "Avicel"))
                return Avicel;
            if (Objects.equals(s, "Rice_Straw"))
                return Ricestraw;
            if (Objects.equals(s, "No_carbon"))
                return Nocarbon;
            System.out.println(s);
            return -1;
        }
    }

    private String protaccession;
    private String orgaccession;
    private ArrayList<ArrayList<String>> orths;
    private String sig;
    private String conf;
    private String pfam;
    private String pfamext;
    private ArrayList<ArrayList<Double>> exprs;

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
        exprs = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            this.exprs.add(new ArrayList<Double>());
        }
    }

    public void addExpr(int substrate, Double value) {
        exprs.get(substrate).add(value);
    }

    public void addExpr(String substrate, Double value) {
        addExpr(Substrate.parseString(substrate), value);
    }

    public void addOrth(int orth, String protaccession) {
        this.orths.get(orth).add(protaccession);
    }

    public void addOrth(String orth) {
        if (orth.startsWith("ACLA")) {
            addOrth(Organism.A_clavatus_NRRL_1, orth);
        }
        if (orth.startsWith("AFL2T")) {
            addOrth(Organism.A_flavus_NRRL_3357, orth);
        }
        if (orth.startsWith("AFUB")) {
            addOrth(Organism.A_fumigatus_A1163, orth);
        }
        if (orth.startsWith("AN")) {
            addOrth(Organism.A_nidulans_FGSC_A4, orth);
        }
        if (orth.endsWith("-mRNA")) {
            addOrth(Organism.A_niger_ATCC_1015, orth);
        }
        if (orth.startsWith("An")) {
            addOrth(Organism.A_niger_CBS_513_88, orth);
        }
        if (orth.startsWith("AO")) {
            addOrth(Organism.A_oryzae_RIB40, orth);
        }
        if (orth.startsWith("ATET")) {
            addOrth(Organism.A_terreus_NIH2624, orth);
        }
        if (orth.startsWith("NFIA")) {
            addOrth(Organism.N_fischeri_NRRL_181, orth);
        }
    }

    public String getOrths() {
        StringBuilder sb = new StringBuilder();
        String prefix = "";
        for (int i = 0; i < 9; i++) {
            if (this.orths.get(i).size() > 0) {
                for (String prot : this.orths.get(i)) {
                    sb.append(prefix);
                    prefix = ", ";
                    sb.append(prot);
                }
            }else{
                sb.append("NA");
            }
            if (sb.length() > 0 && Objects.equals(sb.charAt(sb.length()-1),  ',')) {
                sb.deleteCharAt(sb.length() - 1);
            }
            sb.append("\t");
        }
        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }

    public String getExprs() {
        return getExprs(false, false);
    }

    public String getExprs(boolean one, boolean average) {
        StringBuilder sb = new StringBuilder();
        String prefix = "";
        Double sum;
        Double n;
        for (int i = 0; i < 9; i++) {
            if (this.exprs.get(i).size() > 0) {
                if (one) {
                    sb.append(this.exprs.get(i).get(0));
                } else {
                    sum = 0.0;
                    for (Double value : this.exprs.get(i)) {
                        if (average) {
                            sum += value;
                        }else {
                            sb.append(prefix);
                            prefix = ", ";
                            sb.append("" + value);
                        }
                    }
                    n = sum/this.exprs.get(i).size();
                    sb.append(String.format("%d.3", n));
                }
            }else{
                sb.append("NA");
            }
            if (sb.length() > 0 && Objects.equals(sb.charAt(sb.length()-1),  ',')) {
                sb.deleteCharAt(sb.length() - 1);
            }
            sb.append("\t");
        }
        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }

    @Override
    public String toString () {
        return protaccession+"\t"+orgaccession+"\t"+getOrths()+"\t"+sig+"\t"+conf+"\t"+pfam+"\t"+pfamext+"\t"+getExprs(true, true)+"\n";
    }
}
