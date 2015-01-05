#define closesocket close
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>


#include <stdio.h>
#include <string.h>

#define PROTOPORT 5193
extern int errno;

char localhost[] = "localhost";


int main(int argc, char *argv[])
{
  struct hostent *ptrh;
  struct protoent *ptrp;
  struct sockaddr_in sad;
  int sd;
  int port;
  char *host;
  int n;
  char *buf = argv[1];
  char resultBuf[1000];

  memset((char *)&sad, 0, sizeof(sad));
  sad.sin_family = AF_INET;

  port=PROTOPORT;

  if (port > 0)
  {
    sad.sin_port = htons((u_short)port);
  }
  else 
  {
    fprintf(stderr, "Invalid port number %s\n",argv[2]);
    return 1;
  }

  host=localhost;
  
  ptrh=gethostbyname(host);
  if ((char *)ptrh==NULL)
  {
    fprintf(stderr, "Invalid host name %s\n",argv[1]);
    return 1;
  }

  memcpy(&sad.sin_addr,ptrh->h_addr,ptrh->h_length);

  if (((long int)(ptrp=getprotobyname("tcp")))==0)
  {
    fprintf(stderr, "Can't change TCP number\n");
    return 1;
  }
  
  sd = socket(PF_INET, SOCK_STREAM, ptrp->p_proto);
  
  if (sd < 0)
  {
    fprintf(stderr, "Can't create socket\n");
    return 1;
  }
  
  if (connect(sd, (struct sockaddr *)&sad, sizeof(sad))<0)
  {
    fprintf(stderr, "Can't connect to socket\n");
    return 1;
  }

  send(sd,buf,strlen(buf),0);
  //write(sd,buf,sizeof(buf));
  n=recv(sd,resultBuf,sizeof(resultBuf),0);
  
  if (n > 0)
  {
    printf("%s\n", resultBuf);
  }
  else
  {
    printf("%s\n", "nie odebrano wiadomosci");
  }

  closesocket(sd);

  return 0;
}