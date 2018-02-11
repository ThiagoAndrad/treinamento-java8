import java.util.ArrayList;
import java.util.List;

public class TestaGenerics {

    public static void main(String[] args) {
        List<A> l1 = null;
        List<? extends A> l2 = l1;

//        new DeuRuim().getFirst(new ArrayList<?>());
    }

    public static void swap(List<? extends Number> source, List<? extends Number> dest){
        Number first = source.get(0);
//        source.set(0, dest.get(0));
//        dest.set(0, first);
    }
}

class A {

}

class B extends A{

}

class DeuRuim <T> {

    T getFirst(List<T> source) {
        return source.get(0);
    }
}
