/* Sample TCP client */

#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <strings.h>

#define BUFLEN 10000


int main(int argc, char **argv) {
	int sock_fd;
	struct sockaddr_in serv_addr;

	if (argc != 3) {
		printf("usage: %s <IP address> <TCP port>\n", argv[0]);
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

	connect(sock_fd,(struct sockaddr*) &serv_addr, sizeof(serv_addr));

	// send and receive byte
	char byteToSend = 120;
	char receivedByte;
	send(sock_fd,&byteToSend,1, 0);
	printf("sent: %d\n", byteToSend);
	recv(sock_fd,&receivedByte,1,0);
	printf("received: %d\n", receivedByte);

	// send and receive short
	short shortToSend = 32000;
	short receivedShort;
	send(sock_fd,&shortToSend,2, 0);
	printf("sent: %d\n", shortToSend);
	recv(sock_fd,(void*)&receivedShort,2,0);
	printf("received: %d\n", ntohs(receivedShort));

	// send and receive int
	int intToSend = 1000000000;
	int receivedInt;
	send(sock_fd,&intToSend,4, 0);
	printf("sent: %d\n", intToSend);
	recv(sock_fd,&receivedInt,4,0);
	printf("received: %d\n", ntohl(receivedInt));

	// send and receive long
	long long longToSend = 1000000000000000000;
	long long receivedLong;
	send(sock_fd,&longToSend,8, 0);
	printf("sent: %lld\n", longToSend);
	recv(sock_fd,&receivedLong,8,0);
	printf("received: %lld\n", htobe64(receivedLong));

	shutdown(sock_fd,2);
	return EXIT_SUCCESS;
}

