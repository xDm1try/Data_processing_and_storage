package task14;

public class PieceA extends Piece{
    public PieceA(){
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
        }
        System.out.println("A created");
    }
}
