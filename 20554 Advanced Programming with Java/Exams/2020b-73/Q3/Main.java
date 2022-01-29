import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Text tx = new Text("ab cd ef gh ij kl mn op qr st uv wx yz");
        Text tx2 = new Text("this is a test");
        ArrayList<Text> list = new ArrayList<>();
        list.add(tx);
        list.add(tx2);
        System.out.println(reduceAll(list));
       

    }

    public static  <T extends Reduceable <T> >  ArrayList<T> reduceAll(ArrayList<T> list){
        ArrayList<T> result = new ArrayList<>();

        for (T t : list) {
            try {
                while (true) {
                    t = t.reduce();
                }
            }catch (NonReduceable e){
                result.add(t);
            }
        }
        return result;
    }

}
