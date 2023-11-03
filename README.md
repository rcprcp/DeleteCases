### DeleteCases


My Downloads directory was a mess.  There were files in there and I have no idea to which case they belong.  I have gc.logs, heap dumps, and plenty of log files with no metadata to keep them separated. I set up a process where I name the directories like this: 
```
customerName_ticketNumber_mo_da
```
So an example directory name might look like: 
```
FredCo_11025_01_09
```
In this case, we want to use _ for the separator, and 2 as the token to check.  Yes, we count from 1.  

The program takes two arguments from the command line: 
```shell
-s --separator - specify the separator character in the directory name - the default is _
-t -token - specify the token (ZENDESK ticket number) to check, (count from 1)
````

In another example, the directory names are just the case numbers - e.g. 12345  Here, the separator does not matter and we want -t (or --token) 1. 

This program lists all the sub-directories of the current directory then, based on separator and token number it splits tha name, then checks Zendesk to see if the case with that number is SOLVED or CLOSED. 
If so, it prints an `rm` command that you can copy-n-paste into a shell window to remove the directory.

### Building
```shell
git clone https://github.com/rcprcp/DeleteCases
```
After checking out the repo, go into the root directory for this program and type: 
```shell

mvn clean package
```
That's it. 

### Running 
In order to access Zendesk, you will need to configure 3 environment variables. 
```shell
export ZENDESK_EMAIL="https://domain.zendesk.com"
export ZENDESK_EMAIL="bob@domain.com"
export ZENDESK_TOKEN="c0928blahblahblahblahg1h14g5"
```
Maven will build a runnable jar in the `target` subdirectory.
`cd` into the directory in which you're storing your cases, then: 
``` shell
java -jar ~/DeleteCases/target/deletecases-1.0-SNAPSHOT-jar-with-dependencies.jar [-s] [-t]
```
