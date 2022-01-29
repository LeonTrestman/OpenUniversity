public class Text implements Reduceable<Text> {
    private String st;

    public Text(String st) {
        this.st = st;
    }

    @Override
    public Text reduce() throws NonReduceable {
        if (!st.contains(" "))
            throw new NonReduceable("Text is not reduceable");
        else{
            return new Text(st.replaceFirst(" ", "")); //String is immutable object
        }
    }

    @Override
    public String toString() {
        return "Text{" +
                "st='" + st + '\'' +
                '}';
    }
}
