#define closesocket close
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>
#include <errno.h>

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define PROTOPORT 5193
#define QLEN 6

int visits = 0;

int main(int argc, char *argv[])
{
  struct hostent *ptrh;
  struct protoent *ptrp;
  struct sockaddr_in sad;
  struct sockaddr_in cad;
  int sd,sd2;
  int port;
  int alen;
  char buf[1000];

  memset((char *)&sad, 0, sizeof(sad));
  sad.sin_family = AF_INET;
  sad.sin_addr.s_addr=INADDR_ANY;

  if (argc > 1)
  {
    port = atoi(argv[1]);
  } 
  else port=PROTOPORT;
  
  if (port > 0)
  {
    sad.sin_port = htons((u_short)port);
  }
  else 
  {
    fprintf(stderr, "Invalid port number %s\n",argv[2]);
    return 1;
  }
  
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

  if (bind(sd, (struct sockaddr *)&sad, sizeof(sad))<0)
  {
    fprintf(stderr, "Bind failure\n");
    return 1;
  }
  
  if (listen(sd,QLEN)<0)
  {
    fprintf(stderr, "Listen failure\n");
    return 1;
  }

  while (1)
  {
    alen=sizeof(cad);

    if ((sd2=accept(sd,(struct sockaddr *)&cad,&alen))<0)
    {
     fprintf(stderr,"ERRNO=%d\n",errno);
     fprintf(stderr, "Accept failure\n");
     return 1;
    }
   
    read(sd2, buf, sizeof(buf));

    printf("I get from client: %s\n", buf);

    int result = processMessage(buf);

	memset(&buf[0], 0, sizeof(buf));

    sprintf(buf, "Result is: %d", result);

   	send(sd2,buf,strlen(buf),0);

	memset(&buf[0], 0, sizeof(buf));   	

   	closesocket(sd2);
 }

 closesocket(sd2);

 return 0;
}

int processMessage(char * buff)
{
	char *firstNumber = (char *)(malloc(sizeof(char) * 100));
	char *operation = (char *)(malloc(sizeof(char) * 100));
	char *secondNumber = (char *)(malloc(sizeof(char) * 100));

	char tmpBuff[1000];

	strcpy(tmpBuff, buff);


	char *token;

	token = strtok(tmpBuff, " ");

	strcpy(firstNumber, token);

	token = strtok(NULL, " ");

	strcpy(operation, token);

	token = strtok(NULL, " ");

	strcpy(secondNumber, token);

	int firstNr = atoi(firstNumber);
	int secondNr = atoi(secondNumber);

	if (operation[0] == '+')
	{
		return firstNr + secondNr;
	}
	else if (operation[0] == '-')
	{
		return firstNr - secondNr;
	}
	else if (operation[0] == '*')
	{
		return firstNr * secondNr;
	}
	else if (operation[0] == '/')
	{
		return firstNr / secondNr;
	}

	return 0;
}