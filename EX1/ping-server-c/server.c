/* Sample TCP server */

#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <strings.h>
#include <unistd.h>

#define BUFLEN 10000

int main(int argc, char **argv) {
	int sock_fd, cli_fd;
	int len;
	socklen_t cli_len;
	struct sockaddr_in serv_addr;
	struct sockaddr_in cli_addr;
	char sendline[5] = "PONG";
	char recvline[BUFLEN];
	int ret;

	if (argc != 2) {
		printf("usage: %s <TCP port>\n", argv[0]);
		exit(EXIT_FAILURE);
	}

	// create the socket (add missing arguments)
	sock_fd = socket(AF_INET, SOCK_STREAM, 0);

	if (!sock_fd) {
		perror("socket");
		exit(EXIT_FAILURE);
	}

	bzero(&serv_addr, sizeof(serv_addr));
	serv_addr.sin_family = AF_INET;
	serv_addr.sin_addr.s_addr = htonl(INADDR_ANY);
	serv_addr.sin_port = htons(atoi(argv[1]));


	// set SO_REUSEADDR socket option (please explain the option's meaning)
	int so_reuseaddr = 1;
	ret = setsockopt(sock_fd,SOL_SOCKET,SO_REUSEADDR,&so_reuseaddr, sizeof so_reuseaddr);
	if (ret<0) {
		perror("setsockopt");
	}

	// bind with the use of bind procedure
	ret = bind(sock_fd, (struct sockaddr*)&serv_addr, sizeof(serv_addr));
	if (ret<0) {
		perror("bind");
	}

	// start listening with the use of listen procedure
	listen(sock_fd, 5);

	while (1) {
		// accept the connection and assign descriptor to cli_fd
		cli_fd = accept(sock_fd, (struct sockaddr*)&cli_addr, &cli_len);

		// receive data to recvline buffer with the "recv" system call and assign number of received bytes to len
		len = recv(cli_fd, recvline, BUFLEN, 0);
		printf("received bytes: %d\n", len);
		recvline[len] = 0;
		printf("received: %s\n", recvline);

		// send sendline buffer with the "send" system call and assign number of sent bytes to len
		len = send(cli_fd, sendline, strlen(sendline), 0);
		printf("sent bytes: %d\n", len);
		printf("sent: %s\n", sendline);

		close(cli_fd);
	}

	return EXIT_SUCCESS;
}

