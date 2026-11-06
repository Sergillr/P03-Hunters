# PDF Hunters

PDF Hunters is a Java 21 Maven project for an authorized password-search exercise
using the protected PDFs in [hunter/data](hunter/data).

## Build and generate the API documentation

From the repository root, run:

    mvn -f hunter/pom.xml test
    mvn -f hunter/pom.xml javadoc:javadoc

Open hunter/target/reports/apidocs/index.html to browse the generated API documentation.

## Current behavior

The entry point, com.example.App, currently has an empty run method. The search services can be called independently. AlphanumericHunter tests passwords beginning with c, followed by one character from aeioutr and
four lowercase ASCII letters or digits. PinBreaker searches four-digit PINs from
0000 through 9999 and can be called independently. The bundled exercise materials
also describe two dictionary missions.

Both search services resolve file paths relative to the Maven module, test each
candidate with PDFBox, and report the accepted password, attempt count, and elapsed
search time in whole milliseconds.
