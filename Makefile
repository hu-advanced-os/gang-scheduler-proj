# to initialize all containers
# this should only be run once, upon configuration
# in the event all containers are removed, you may run this again
init:
	docker-compose -p gang-scheduler-proj build

# this will stop all running containers
stop-all:
	docker stop $(docker ps -q)

# this will stop and remove all containers
kill-all:
	docker stop $(docker ps -q) && docker rm $(docker ps -a -q)

# this will remove all containers (if run as a group)
clean:
	docker-compose down 


# the following commands will start, restart, stop, check status, and remove containers

# audit ------------------------------------------
start-audit:
	docker run --name gsp-audit -it audit:latest /bin/bash

restart-audit:
	docker start gsp-audit && docker exec -it gsp-audit /bin/bash

stop-audit:
	docker stop gsp-audit

status-audit:
	docker ps --filter "name=gsp-audit"

kill-audit:
	docker rm -f gsp-audit


# generator ------------------------------------------
start-generator:
	docker run --name gsp-generator -it generator:latest /bin/bash

restart-generator:
	docker start gsp-generator && docker exec -it gsp-generator /bin/bash

stop-generator:
	docker stop gsp-generator

status-generator:
	docker ps --filter "name=gsp-generator"

kill-generator:
	docker rm -f gsp-generator


# loader ------------------------------------------
start-loader:
	docker run --name gsp-loader -it loader:latest /bin/bash

restart-loader:
	docker start gsp-loader && docker exec -it gsp-loader /bin/bash

stop-loader:
	docker stop gsp-loader

status-loader:
	docker ps --filter "name=gsp-loader"
	
kill-loader:
	docker rm -f gsp-loader


# reporting ------------------------------------------
start-reporting:
	docker run --name gsp-reporting -it reporting:latest /bin/bash

restart-reporting:
	docker start gsp-reporting && docker exec -it gsp-reporting /bin/bash

stop-reporting:
	docker stop gsp-reporting

status-reporting:
	docker ps --filter "name=gsp-reporting"

kill-reporting:
	docker rm -f gsp-reporting


# simulator ------------------------------------------
start-simulator:
	docker run --name gsp-simulator -it simulator:latest /bin/bash

restart-simulator:
	docker start gsp-simulator && docker exec -it gsp-simulator /bin/bash

stop-simulator:
	docker stop gsp-simulator

status-simulator:
	docker ps --filter "name=gsp-simulator"

kill-simulator:
	docker rm -f gsp-simulator
