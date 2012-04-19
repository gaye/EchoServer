require 'rubygems'
require 'thrift'
load 'src/generated-rb/echo.rb'

class EchoClient
  def initialize
    @transport = Thrift::FramedTransport.new(Thrift::Socket.new("harvey", 1337))
    @protocol = Thrift::BinaryProtocol.new(@transport)
    @client = Echo::Client.new(@protocol)
  end
  
  def echo(msg)
    @transport.open
    echo = @client.echo(msg)
    @transport.close
    echo
  end
end

client = EchoClient.new
puts client.echo("something streamable")