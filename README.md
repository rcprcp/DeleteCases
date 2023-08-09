### DeleteCases


My Downloads directory is a mess.  There are files there and I have no idea which case they belong to.  I have screen grabs, heap dumps, and plenty of log files with no metadata to keep them separated.  

So, I set up a "policy" where I name the directories like this: 
```
customerName_ticketNumber_mo_da
```
So an example directory name might look like: 
```
IBM_11025_01_09
```

This program looks for sub-directories of the current directory that match this naming convention,
that is - 4 parts in the name with the parts separated by "_" 

It then checks if second token (the ZD ticket number) is CLOSED or SOLVED, and if so it prints an rm command you can 
cut-n-paste into a shell window to remove the directory.

### building. 
After checking out the repo, go into the root directory for this program and type: 
```mvn clean package```
That should build it.   :) 

### run it. 
Maven will build a runnable jar in the `target` directory.
cd into the directory in which you're storing your cases, directory, then: 
``` shell
java -jar ~/DeleteCases/target/deletecases-1.0-SNAPSHOT-jar-with-dependencies.jar
```
