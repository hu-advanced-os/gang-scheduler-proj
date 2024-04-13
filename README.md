# Gang Scheudler Project [(--)](https://canvas.howard.edu/courses/44331/assignments/352332)

## Resource Repository 
[GitHub Link](https://github.com/hu-advanced-os/simulation-resources)

##  Environment Setup

#### I. Install the following:
1. Docker
   - [Mac](https://docs.docker.com/desktop/install/mac-install/) | [Windows](https://docs.docker.com/desktop/install/windows-install/)

2. Git
   - [All OS](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git)

3. Make
   - [Mac](https://stackoverflow.com/a/10265766) | [Mac via Homebrew](https://formulae.brew.sh/formula/make) | [Windows](https://gnuwin32.sourceforge.net/packages/make.htm) 
4. WSL*
   - [Windows Only](https://learn.microsoft.com/en-us/windows/wsl/install) (_*Not required for Mac users_)
5. VS Code Docker Extension (_Optional_)
   - [Mac]() | [Windows]()

#### II. Build initial environment: 

1. From a location of your choice, clone the git repository (_ssh key users may clone using ssh;_ [repository](https://github.com/hu-advanced-os/gang-scheduler-proj))

   `ie: Documents/, Projects/ etc`
``` bash
   git clone https://github.com/hu-advanced-os/gang-scheduler-proj.git
   ```

2. Navigate to the `gang-scheduler-proj` folder and build the initial docker environment
``` bash
   cd gang-scheduler-proj
   ```
   ``` 
      make init
   ```

## Usage
1. Open Docker Desktop (_to make sure Docker is running_)

2. Navigate to project location (_May look like:_ `Downloads/gang-scheduler-proj`)

3. Launch individual project by name
   
   _Project Names_
   - `audit`
   - `generator`
   - `loader`
   - `reporting`
   - `simulator`

- Using the Command Line (replace `audit` with intended project):
   ```bash
   # to start a container
   make start-audit

   # to restart a container (that was previously started)
   make restart-audit

   # to stop/pause a container
   make stop-audit
   
   # to kill/remove a container
   make kill-audit
   ```

   Global Commands
   ```bash
   # to build all containers 
   make init

   # to stop all running containers
   make stop-all

   # to stop and remove all contaienrs
   make kill-all

   # to stop all containers run as a group (this will likely not be used)
   make clean
   ```

- Using VS Code Extension
   
   - Locate docker icon on left column of VS Code
   - Select container of your choice
   - Right click, and select `start` 
   - Navigatge to `gang-scheduler-proj`
   - Open folder and select file
   - Begin coding & debugging