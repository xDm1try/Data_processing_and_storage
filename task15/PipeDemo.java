import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

public class PipeDemo {
   public static void main(String[] args) throws IOException {
      //An instance of Pipe is created
      Pipe pipe = Pipe.open();
      // gets the pipe's sink channel
      Pipe.SinkChannel skChannel = pipe.sink();
      String testData = "Test Data to Check java NIO Channels Pipe.";
      ByteBuffer buffer = ByteBuffer.allocate(512);
      buffer.clear();
      buffer.put(testData.getBytes());
      buffer.flip();
      //write data into sink channel.
      while(buffer.hasRemaining()) {
         skChannel.write(buffer);
      }
      //gets  pipe's source channel
      Pipe.SourceChannel sourceChannel = pipe.source();
      buffer = ByteBuffer.allocate(512);
      //write data into console     
      while(sourceChannel.read(buffer) > 0){
         //limit is set to current position and position is set to zero
         buffer.flip();
         while(buffer.hasRemaining()){
            char ch = (char) buffer.get();
            System.out.print(ch);
         }
         //position is set to zero and limit is set to capacity to clear the buffer.
         buffer.clear();
      }
   }
}
