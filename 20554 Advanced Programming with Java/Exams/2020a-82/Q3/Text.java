public class Text implements Reverseable<Text> {
    private String st = "";

    public String getSt() {
        return st;
    }

    public Text(String st) {
        this.st = st;
    }

    @Override
    public Text reverse() throws Exception {
        if (st.length() < 2) {
            throw new Exception("Text is too short");
        }
        String revSt = "";
        for (int i = st.length() - 1; i >= 0; i--) {
            revSt += st.charAt(i);
        }

        return new Text(revSt);
    }

    @Override
    public String toString() {
        return "Text{" +
                "st='" + st + '\'' +
                '}';
    }
}
