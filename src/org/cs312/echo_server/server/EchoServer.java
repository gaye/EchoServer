package org.cs312.echo_server.server;

import org.apache.log4j.BasicConfigurator;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TNonblockingServer.Args;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.cs312.echo_server.generated.Echo;
import org.cs312.echo_server.generated.Echo.Iface;

public class EchoServer implements Echo.Iface {
  TServer server;
  int port;
  
  public EchoServer(int port) {
    this.port = port;
  }
  
  public void start() {
    try {
      server = new TNonblockingServer(
          new Args(new TNonblockingServerSocket(port))
              .processor(new Echo.Processor<Iface>(this))
              .protocolFactory(new TBinaryProtocol.Factory()));
      System.out.printf("Echo server running on port %d", port);
      server.serve();
    } catch (TTransportException e) {
      e.printStackTrace();
    }
  }

  @Override
  public String echo(String msg) throws TException {
    return msg;
  }
  
  public static void main(String[] args) {
    BasicConfigurator.configure();
    EchoServer server = new EchoServer(1337);
    server.start();
  }

}
