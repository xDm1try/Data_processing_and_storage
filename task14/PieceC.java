package task14;

public class PieceC extends Piece{
    public PieceC(){
        try{
            Thread.sleep(3000);
        }catch(InterruptedException e){
        }
        System.out.println("C created");
    }
}
