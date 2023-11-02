package task14;

public interface Maker {

default void makeStuff(int i){
    try {
        Thread.currentThread().sleep(i);
    } catch (Exception e) { 
    }
    }
}