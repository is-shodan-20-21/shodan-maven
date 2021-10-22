# Shodan (Maven)

La [release originale di Shodan](https://github.com/is-shodan-20-21/shodan) è stata implementata e pensata per [Eclipse](https://www.eclipse.org/ide/). 

Se si desidera, invece, utilizzare un IDE alternativo come [Visual Studio Code](https://code.visualstudio.com/), si utilizzi questa repository. La codebase di questa versione di Shodan è più stabile e aggiornata rispetto all'originale; la maggior parte del lavoro di refactoring e implementazione verrà altresì svolto su questa repository, in conformità alle consegne del progetto di Ingegneria del Software, alla quale questa fork fa capo.

## Installazione


È possibile [scaricare la repository](https://github.com/is-shodan-20-21/shodan-maven/archive/refs/heads/master.zip) oppure clonarla tramite `git clone https://github.com/is-shodan-20-21/shodan-maven.git`.

Navigare, poi, nella directory creata sotto il nome di `shodan-maven` e lanciare dapprima `mvn clean install` per installare le dipendenze e i plugin necessari alla costruzione del pacchetto WAR, e infine `mvn tomcat7:run` per il deployment della webapp. Si potrà accedere alla piattaforma via `http://localhost:8080/shodan-maven`.

## Strumenti

* [Apache Tomcat 7+](http://tomcat.apache.org/)
* [Apache Maven 3.8.2](https://maven.apache.org/download.cgi)
* [OpenJDK 17](https://jdk.java.net/17/)
* [Java Expansion Pack](https://code.visualstudio.com/docs/java/java-build)

## Credenziali

`admin : 123 [SHA256]`