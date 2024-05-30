import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;



public class Destination {
    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        serverSocket.bind(new InetSocketAddress("localhost", 5454));
        serverSocket.configureBlocking(false);
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        

        while (true) {
            selector.select();
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iter = selectedKeys.iterator();
            ByteBuffer buffer = ByteBuffer.allocate(256);
            while (iter.hasNext()) {
                buffer.clear();
                SelectionKey key = iter.next();

                if (key.isAcceptable()) {
                    SocketChannel client = serverSocket.accept();
                    client.configureBlocking(false);
                    client.register(selector, SelectionKey.OP_READ);
                }
                if (key.isReadable()) {
                    try{
                        SocketChannel client = (SocketChannel) key.channel();
                        
                        int count = client.read(buffer);
                        if (count == -1){
                            System.out.println("closing");
                            client.close();
                        }else{
                            System.out.println(new String(buffer.array()).trim());
                            String response = "Response for " + client.toString();
                            client.write(ByteBuffer.wrap(response.getBytes(), 0, response.length()));
                        }                              
                    }catch(IOException ex){
                        System.out.println("IOException in stream of selectedKeys isAcceptable");                                }
                }
                iter.remove();
            }


            // selectedKeys.stream().forEach(key -> {
            //     if (key.isReadable()) {
            //         try{
            //             SocketChannel client = (SocketChannel) key.channel();
            //             //ByteBuffer buffer = ByteBuffer.allocate(256);
            //             int count = client.read(buffer);
            //             if (count == -1){
            //                 System.out.println("closing");
            //                 client.close();
            //             }else{
            //                 System.out.println(new String(buffer.array()).trim());
            //                 String response = "Response for " + client.toString();
            //                 client.write(ByteBuffer.wrap(response.getBytes(), 0, response.length()));
            //             }
                        
            //         }catch(IOException ex){
            //             System.out.println("IOException in stream of selectedKeys isAcceptable");
            //         }
            //     }
            //     else if(key.isAcceptable()){
            //         try{
            //             System.out.println(serverSocket);
            //             SocketChannel client = serverSocket.accept();
            //             if(client == null){
            //                 System.out.println("client is null");
            //                 // client == null!
            //             }else{
            //                 System.err.println(client);
            //             }
            //             //NLP appears
            //             client.configureBlocking(false);
            //             System.out.println(selector);
            //             client.register(selector, SelectionKey.OP_READ);
            //         }catch(IOException ex){
            //             System.out.println("IOException in stream of selectedKeys isAcceptable");
            //         }
            //     } 
                
            // });
        }
    }
}