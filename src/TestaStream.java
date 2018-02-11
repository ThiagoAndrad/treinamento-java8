import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class TestaStream {
    public static void main(String[] args) {
        List<Pessoa> pessoas = new ArrayList<>();
        pessoas.add(new Pessoa("Luke", 30));
        pessoas.add(new Pessoa("Vader", 30));

        Manipulador<Pessoa> manipulador = Manipulador.of(pessoas);


        System.out.println("\nImprimindo a lista");
        manipulador.forEach(System.out::println);

        System.out.println("\nFiltrando e imprimindo somente pessoas com nomes que começam com a letra L");
        manipulador.filter(p  -> p.getNome().startsWith("L"))
                .forEach(System.out::println);

        System.out.println("\nMapeando os nomes de uma lista de pessoas, filtrando somente os nome que começam com a letra V e imprimindo-os");
        manipulador.map(p -> p.getNome())
                .filter(nome -> nome.startsWith("V"))
                .forEach(System.out::println);

        System.out.println("\nMapeando os nomes da lista, mapeando os nomes que começam com a letra V para uma lista de boolean e reduzindo tudo para um boolean");
        Boolean reduce = manipulador.map(p -> p.getNome())
                .map(nome -> nome.startsWith("V"))
                .reduce(true, (a, b) -> a && b);
        System.out.println(reduce);

        System.out.println("\n");
        Boolean reduce1 = manipulador.map(p -> p.getNome())
                .allMatch(nome -> nome.startsWith("V"));
        System.out.println(reduce1);

        System.out.println("\n");
        Boolean reduce2 = manipulador.map(p -> p.getNome())
                .anyMatch(nome -> nome.startsWith("V"));
        System.out.println(reduce2);

//        pessoas.stream()
//                .map(p -> p.getNome())
//                .map(nome -> nome.startsWith("V"))
//                .reduce(true, (a, b) -> a && b);
    }
}

class Manipulador<T> {

    private List<T> source;

    private Manipulador(List<T> source) {
        this.source = source;
    }

    public void forEach(Consumer<T> consumer) {
        for (T t : source) {
            consumer.accept(t);
        }
    }

    public static <T> Manipulador<T> of(List<T> source) {
        return new Manipulador<>(source);
    }

    public Manipulador<T> filter(Predicate<T> p) {
        List<T> result = new ArrayList<>();

        for (T t : source) {
            if (p.test(t)) {
                result.add(t);
            }
        }
        return Manipulador.of(result);
    }

    public <U> Manipulador<U> map(Function<T, U> f) {

        List<U> result = new ArrayList<>();
        for (T t : source) {
            U unknow = f.apply(t);
            result.add(unknow);
        }
        return Manipulador.of(result);
    }

    public <U> U reduce(U initial, BiFunction<U, T, U> biFunction) {
        U acumulador = initial;

        for (T t : source) {
            acumulador = biFunction.apply(acumulador, t);
        }
        return acumulador;
    }

    public boolean allMatch(Predicate<T> predicate) {
        return this.reduce(true, (a,b) -> a && predicate.test(b));
    }

    public boolean anyMatch(Predicate<T> predicate) {
        return this.reduce(false, (a,b) -> a || predicate.test(b));
    }
}

class Pessoa {
    private String nome;
    private int idade;

    Pessoa(String nome, int idade){
        this.nome = nome;
        this.idade = idade;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return "Pessoa{" +
                "nome='" + nome + '\'' +
                ", idade=" + idade +
                '}';
    }
}
