test: tools/apache-jena/lib/sparql-shell-1.0-SNAPSHOT.jar test/test-query
	./tools/apache-jena/bin/sparql --query=test/test-query-1


tools/apache-jena/lib/sparql-shell-1.0-SNAPSHOT.jar: src/main/java/eu/europa/ec/sparql/shell/Exec.java
	mvn package
	cp target/sparql-shell-1.0-SNAPSHOT.jar tools/apache-jena/lib


tools/apache-jena:
	mkdir -p tools
	rm -rf tools/apache-jena
	wget https://dlcdn.apache.org/jena/binaries/apache-jena-4.5.0.zip -O tools/apache-jena.zip
	unzip tools/apache-jena.zip -d tools
	rm tools/apache-jena.zip
	cd tools; ln -s apache-jena-4.5.0 apache-jena

.PHONY: test
