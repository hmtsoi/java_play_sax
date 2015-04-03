# java_play_sax

This is a small java implementation of SAX inspired by Sec 2.3 of the paper "iSAX: Indexing and Mining Terabyte Sized Time Series. SIGKDD 2008" by Jin Shieh and Eamonn Keogh.

This program will generate a time series of 4000 elements of value between 10 and 60 (with possibly time collisions). The series will then be reduced to a series with dimension 50 and with SAX words of cardinality of 16.  

The above specifications are hard-coded in the class "Main.java" (in package "play.sax.main").


## Software Requirements

- Java 8
- Gradle 2.2.1
- R 3.1.2

Of course, you have to set your JAVA_HOME and GRADLE_HOME variables correctly.
Besides, this java program is calling a Rscript using R via the path /usr/bin/Rscript (working at least on MacOS X 10.9.5 and Ubuntu 14.04 by standard installation of R), you can test if the R script is running correctly by using `./gradlew testRScript`. Otherwise, you have to rewrite the method "writeRScript" in SaxRepresentationImpl.java (in package "sax.data.transform") correspondingly.

## Implementation 
Configure the directory by running `gradle wrapper`.

Then run `./gradlew run` and have fun.

