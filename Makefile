IMAGE_NAME=sso-workshop

all: build run

build:
	podman build -t $(IMAGE_NAME) -f Containerfile .

run:
	podman run -it --rm -h $(IMAGE_NAME) \
	--name $(IMAGE_NAME) \
	-p 8080:8080 \
	$(IMAGE_NAME)

clean:
	podman rmi $$(podman images -f 'dangling=true' -q) -f 2>/dev/null || true
	podman rmi $(IMAGE_NAME) 2>/dev/null || true
