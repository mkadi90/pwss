Aby wywołać poprawnie program nalezy do argumentów przy uruchamianiu dodac dzialanie np:
"2 + 2" - liczby od znaku dzialania musza byc oddzielone spacja.
====
Aby uruchomic servlet nalezy:
- zainstlowac tomcata
- wejsc do /etc/tomcat8
- edytowac plik tomcat-users.xml
- dodac tam: <user username="admin" password="admin" roles="manager-gui,admin-gui" />
- skopiowac zawartosc pliku root.tar.gz do foleru webapps w /var/lib/tomcat8/webapps/ uprzednio usuwajac folder ROOT w tej lokalizacji
- po uruchomieniu wejsc na localhost:8080/manager i z menu aplikacji dla lokalizacji "/" kliknac start.
- powinno dzialac

Aby samemu skompilowac servlet i potem uruchomic:
- nalezy sciagnac java ee 7 od oracle
- skompilowac plik Servleru z poleceniem - javac *.java -classpath ~/Downloads/glassfish4/glassfish/lib/javaee.jar
	gdzie sciezka do javaee.jar moze byc inna ;)
- usunac zawartosc katalogu /var/lib/tomcat8/webapps/ROOT/
- skopiowac do skompilowany plik do /var/lib/tomcat8/webapps/ROOT/WEB-INF/classes/...../plik.class (brakujace foldery utworzyc)
- w folderze WEB-INF stworzyc plik web.xml 
- nastepnie podazac krokami "jak uruchomic servlet" bez kopiowania i wypakowywania pliku root.tar.gz
