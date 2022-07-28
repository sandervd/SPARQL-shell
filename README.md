# SPARQL Shell
SPARQL shell is an Apache Jena plugin, that allows executing shell commands as a SPARQL function.

## Setup
Build the jar:
```
mvn package
```
Copy the resulting package to the 'lib' folder of your Jena installation.

Alternatively grab the JAR from the release on GitHub.

## Example usage
To convert Markdown to HTML, we can use the following query:
```
PREFIX f: <java:eu.europa.ec.sparql.shell.>
SELECT ?s 
{ 
  BIND("# Title\n - Item 1\n - Item 2" as ?t)
  BIND(f:Exec("pandoc -f markdown -t html", ?t) as ?s)

}
```
This will execute the pandoc tool to do the convertion.

The output of this query is:
```
--------------------------------------------------------------------------------
| s                                                                            |
================================================================================
| "<h1 id=\"title\">Title</h1>\n<ul>\n<li>Item 1</li>\n<li>Item 2</li>\n</ul>" |
--------------------------------------------------------------------------------
```

## Security consideration
The executed process runs as a subprocess of the Jena SPARQL/Fuseki process.
This is a serious security risk. It should not be used in a public endpoint or where the data is untrusted.

