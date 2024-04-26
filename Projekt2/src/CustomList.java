import java.util.AbstractList;
import java.util.Iterator;

public class CustomList <T> extends AbstractList {
    private Node<T> head; //samo T - tylko samo value, bez informacji kto jest przy sąsiedzie
    private Node<T> tail;
    private int size=0;

    public Node<T> getHead() {
        return head;
    }

    public Node<T> getTail() {
        return tail;
    }

    public void addLast(T value){
        Node<T> newNode = new Node<T>(value);

        if (head==null){
            head=newNode;
            //tail=newNode;
        }
        else{
            tail.next = newNode;
        }

        tail = newNode;
        size++;
    }

    public T getLast(){
        //gdyby uzytkownik pracowal na pustej liscie, to by wywalilo blad, musimy sie zabezpieczyc przed tym, wiec
        //sprawdzamy czy tail jest rozny od null
        /*
        nie bedzie exception, bo gdybym chcial pobrac osobe o nazwisku xyz i ta osoba nie istnieje
        to powinno zwrocic null, wygodne, bo exception by nam konczyl wykonanie programu, gdybysmy go nie obsluzyli poprawnie
         */
        if (tail == null){
            return null;
        }
        return getTail().value;
    }

    public void addFirst(T value){
        Node<T> newNode = new Node<>(value);
        newNode.next=head;
        head = newNode;
        if (head == null) {
            tail = newNode;
            head = newNode;
        }
        size++;
    }
    public T getFirst(){
        if (head == null){
            return null;
        }
        return head.value;
    }

    public T removeLast(){
        if (head == null){
            return null;
        }

        Node<T> tmp = head;
        while (tmp.next!=tail){ //dopoki nie znalezlismy taila; to petla while musi zrobic jeszce jeden obrót
            tmp = tmp.next;
        }
        //tail=null; //nie musimy pisac tego, bo i tak go zje garbage collector
        T retVal=tail.value;
        tail=tmp;
        size--;
        return retVal;
    }


    public T removeFirst(){
        if (head == null){
            return null;
        }
        T retVal=head.value;

        Node<T> tmp = head.next;
        head=tmp;
        size--;
        return retVal;
    }

    @Override
    public T get(int index) {

        if (index >=0 && index<size){
            Node<T> tmp = head;

            for (int i=0; i<index; i++){
                tmp=tmp.next;
            }
            return tmp.value;
        }
        else{
            return null;
        }
    }

    public Iterator<T> iterator(){
        return new Iterator<T>() {
            Node<T> current = head;
            @Override
            public boolean hasNext() {
                return current.next != null;
            }

            @Override
            public T next() {
                if (head == null){
                    return null;
                }
                T retVal = current.value;
                current = current.next;
                return retVal;
            }
        };
    }
/*Tworze iterator do mojej kolekcji i potem mozna chodzi po nim bardzo prosto;
bo wystarczy ze wywolam hasNext
 */
/*
jak mowimy o drzewie; jestesmy w stanie napisac co najwyzej tyle ile wysokosc drzewa
 */

    //iterator - przesuwanie w prawo itd, ulatwia prace z pętlami



    @Override
    public int size() {
        return size;
    }


    public static class Node<T>{
        T value;
        Node <T> next;

        Node (T value){
            this.value = value;
            this.next= null;
        }
    }
}
