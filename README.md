# PDF Hunters

PDF Hunters is a Java 21 Maven project for an authorized password-search exercise
using the protected PDFs in [hunter/data](hunter/data).

## Build and generate the API documentation

From the repository root, run:

    mvn -f hunter/pom.xml test
    mvn -f hunter/pom.xml javadoc:javadoc

Open hunter/target/reports/apidocs/index.html to browse the generated API documentation.

## Password searches

The command-line entry point, com.example.App, currently has no configured search.
The services can be called directly:

- DictionaryAttack.breakDictionary(pdfPath, dictionaryPath) reads a UTF-8
  dictionary one line at a time and tests each line in file order. Spaces and
  blank lines are preserved as part of the candidate input. The search stops
  at the first accepted entry. Pass a Path for the dictionary, for example
  ProjectPaths.resolve("data/diccionari.txt").
- AlphanumericHunter.breakAlphanumeric(pdfPath) tests candidates starting with
  c, followed by one of aeioutr and four lowercase ASCII letters or digits.
- PinBreaker.breakPin(pdfPath) tests four-digit PINs from 0000 through 9999.

Pass each PDF path as a string relative to the Maven module. On success, the
services return the accepted candidate, the number of attempts including the
successful attempt, and the elapsed search time in whole milliseconds. They
throw IllegalStateException when the candidate search is exhausted and
UncheckedIOException when an I/O error prevents the search from completing.
