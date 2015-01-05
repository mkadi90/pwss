// Implementation of the ServerSocket class

#include "ServerSocket.h"
#include "SocketException.h"
#include <sstream>
#include <vector>
#include <iostream>
#include <cstdlib>

ServerSocket::ServerSocket ( int port )
{
  if ( ! Socket::create() )
    {
      throw SocketException ( "Could not create server socket." );
    }

  if ( ! Socket::bind ( port ) )
    {
      throw SocketException ( "Could not bind to port." );
    }

  if ( ! Socket::listen() )
    {
      throw SocketException ( "Could not listen to socket." );
    }

}

ServerSocket::~ServerSocket()
{
}


const std::string ServerSocket::process( const std::string& s ) const
{
  std::stringstream ss(s);
  std::string buf;
  std::vector<std::string> splitted;

  while(ss >> buf)
  {
    splitted.push_back(buf);
  }

  int firstNr = atoi(splitted[0].c_str());
  int secondNr = atoi(splitted[2].c_str());

  std::string operation = splitted[1];

  std::string result = "Result is: ";

  int res;

  std::ostringstream oss;

  if (operation == "+")
  {
    res = firstNr + secondNr;
    oss << res;
    return result + oss.str();
  }
  else if (operation == "-")
  {
    res = firstNr - secondNr;
    oss << res;
    return result + oss.str();
  }
  else if (operation == "*")
  {
    res = firstNr * secondNr;
    oss << res;
    return result + oss.str();
  }
  else if (operation == "/")
  {
    res = firstNr / secondNr;
    oss << res;
    return result + oss.str();
  }

  return "";
}

const ServerSocket& ServerSocket::operator << ( const std::string& s ) const
{

  std::string result = process(s);

  if ( ! Socket::send ( result ) )
    {
      throw SocketException ( "Could not write to socket." );
    }

  return *this;

}


const ServerSocket& ServerSocket::operator >> ( std::string& s ) const
{
  if ( ! Socket::recv ( s ) )
    {
      throw SocketException ( "Could not read from socket." );
    }

  return *this;
}

void ServerSocket::accept ( ServerSocket& sock )
{
  if ( ! Socket::accept ( sock ) )
    {
      throw SocketException ( "Could not accept socket." );
    }
}
