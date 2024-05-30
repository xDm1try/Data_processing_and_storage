import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;



public class EchoServer {
    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        serverSocket.bind(new InetSocketAddress("localhost", 6565));
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
                    SocketChannel pairOfProxyClient = SocketChannel.open(new InetSocketAddress("localhost", 5454));
                    pairOfProxyClient.configureBlocking(false);
                    client.register(selector, SelectionKey.OP_READ, pairOfProxyClient);
                    pairOfProxyClient.register(selector, SelectionKey.OP_READ, client);
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
                            SocketChannel pair = (SocketChannel) key.attachment();
                            pair.write(buffer.flip());
                        }                              
                    }catch(IOException ex){
                        System.out.println("IOException in stream of selectedKeys isReadable");                                }
                }
                iter.remove();
            }
        }
    }
}