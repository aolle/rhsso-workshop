CONJUR-CLI_IMAGE_NAME=conjur-cli
CONJUR-CLI_GIT_URL=https://github.com/cyberark/cyberark-conjur-cli.git
PROXY_IMAGE_NAME=httpd-conjur-proxy

CONJUR_POD_NAME=conjur-pod
# it's a demo, it's ok
CONJUR_DATA_KEY=tnwICwIa2kOVH2KkWvpPw+VA5AagyJPtu4nEim1UwCc=

.PHONY: build clean all start

all: build run

build:
	podman build -t $(CONJUR-CLI_IMAGE_NAME) $(CONJUR-CLI_GIT_URL)
	podman build -t $(PROXY_IMAGE_NAME) proxy

run:
	podman run -dt --pod new:$(CONJUR_POD_NAME) -e POSTGRES_HOST_AUTH_METHOD=trust -p 8432:5432 -p 18443:443 -p 18080:80 --name postgres docker.io/postgres:10.16

	podman run -dt --pod $(CONJUR_POD_NAME) -e DATABASE_URL=postgresql://postgres:secret@postgres/postgres -e CONJUR_DATA_KEY="$(CONJUR_DATA_KEY)" --name conjur docker.io/cyberark/conjur server

	podman run -dt --pod $(CONJUR_POD_NAME) --name $(PROXY_IMAGE_NAME) $(PROXY_IMAGE_NAME)

	podman run -dt --pod $(CONJUR_POD_NAME) --name $(CONJUR-CLI_IMAGE_NAME) --entrypoint="sleep" conjur-cli infinity

stop:
	podman pod stop $(CONJUR_POD_NAME)

rm:
	podman pod rm -f $(CONJUR_POD_NAME)

clean:
	podman rmi $$(podman images -f 'dangling=true' -q) -f 2>/dev/null || true
	podman rmi $(CONJUR-CLI_IMAGE_NAME) $(PROXY_IMAGE_NAME) docker.io/cyberark/conjur docker.io/postgres:10.16 2>/dev/null || true
