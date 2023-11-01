package task14;

public class PieceB extends Piece{
    public PieceB(){
        try{
            Thread.sleep(2000);
        }catch(InterruptedException e){
        }
        System.out.println("B created");
    }
}
