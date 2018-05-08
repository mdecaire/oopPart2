# oopPart2
This project reads in a file of ports, docks, jobs and persons. 
It parses the data to find what docks belong to the port, what ships belong to the port and dock,
what jobs belong to the ships, and what persons belong to the port. 

The persons have skills that are needed by the job, and the job cannot start until the ship that it 
belongs to is at the dock. 

Once the jobs on the ship are done the ship removes itself from a dock so that another ship from the ports can
move into the dock.

A status bar is given for each job, and if there are not enough resources at the port than the job is cancelled. 
The text files are what are to be read in and parsed.

This project also has sorting and searching capabilities which I intend to extend by adding a jtable that included searchability.

A Jar file has been added for anyone who would like to see a demo.
