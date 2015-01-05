#include "ClientSocket.h"
#include "SocketException.h"
#include <iostream>
#include <string>

int main ( int argc, char * argv[] )
{
  try
  {

    ClientSocket client_socket ( "localhost", 5193 );

    std::string reply;
    std::string message = argv[1];
    try
    {
      client_socket << message;
      client_socket >> reply;
    }
    catch ( SocketException& ) {}

    std::cout << "We received this response from the server:\n\"" << reply << "\"\n";;

  }
  catch ( SocketException& e )
  {
    std::cout << "Exception was caught:" << e.description() << "\n";
  }

  return 0;
}
