CC   = javac

all:
	$(CC) *.java

clean:
	@echo Cleaning...
	rm -f *.class

zip:
	zip codigo.zip *.java

submit:
	powershell.exe python submit.py