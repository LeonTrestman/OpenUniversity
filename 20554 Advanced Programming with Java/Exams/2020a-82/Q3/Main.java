import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Text text = new Text("leonardo");
        Text text2 = new Text("diana");
        Text text3 = new Text("jose");
        Text text4 = new Text("f");
        ArrayList<Text> list = new ArrayList<Text>();
        list.add(text);
        list.add(text2);
        list.add(text3);
        list.add(text4);

        System.out.println(reverseAll(list));
    }

    public static <T extends Reverseable<T>> ArrayList<T> reverseAll (ArrayList<T> list) {
        var revArr = new ArrayList<T>();
        for (T element : list) {
            try {
                revArr.add(element.reverse());
            } catch (Exception e) {
                System.out.println("error," + element + " element not reversable");
                revArr.add(element);
            }
        }

        return revArr;
    }
}
