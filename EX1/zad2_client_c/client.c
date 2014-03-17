/* Sample TCP client */

#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <strings.h>
#include <sys/types.h>
#include <errno.h>

#define BUFLEN 10000


int main(int argc, char **argv) {
	int sock_fd;
	struct sockaddr_in serv_addr;
	char* filePath = argv[3];

	if (argc != 4) {
		printf("usage: %s <IP address> <TCP port> <filepath>\n", argv[0]);
		exit(EXIT_FAILURE);
	}
	// create the socket
	sock_fd = socket(AF_INET, SOCK_STREAM, 0);
	if (!sock_fd) {
		perror("socket");
		exit(EXIT_FAILURE);
	}
	bzero(&serv_addr, sizeof(serv_addr));
	serv_addr.sin_family = AF_INET;
	serv_addr.sin_addr.s_addr = inet_addr(argv[1]);
	serv_addr.sin_port = htons(atoi(argv[2]));

	if(connect(sock_fd,(struct sockaddr*) &serv_addr, sizeof(serv_addr)) == -1){
		fprintf(stderr, "Connection error: %s\n",strerror(errno));
		exit(1);
	}

	//send file
	char buffer[BUFLEN];
	FILE* file = fopen(filePath,"r");
	if(file==NULL){
		fprintf(stderr, "Opening file error: %s\n",strerror(errno));
		exit(1);
	}
	int size;
	while((size=fread(buffer,1,BUFLEN,file))>0)
		send(sock_fd,buffer,size,0);

	shutdown(sock_fd,2);
	return EXIT_SUCCESS;
}

