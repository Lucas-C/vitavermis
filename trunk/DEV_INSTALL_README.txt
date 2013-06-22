# This file is intended to explain how to setup the needed tools to develop on the project

#---------------#
# Eclipse setup #
#---------------#

IMPORTANT: use 'trunk/' as the project root

# Linux (tested under Ubuntu 11.10)

sudo aptitude install java7-jdk

# Had to get latest Eclipse version :
# it's quite easy, I took v4.2 and followed this http://askubuntu.com/questions/68018/how-do-i-install-eclipse-indigo-3-7-1

# Can be useful too
sudo update-alternatives --config java
sudo update-alternatives --config javac
sudo update-alternatives --config jar

#-----------------#
# Eclipse plugins #
#-----------------#

# Checkstyle: source code analyzer
Eclipse : Help -> Install New Software : http://eclipse-cs.sf.net/update/
Sélectionner Checkstyle + Uncategorized
Après install : Click-droit sur projet VitaVermis -> Checkstyle -> ActivateCheckstyle 


# Findbug: 
Eclipse : Help -> Install New Software : http://andrei.gmxhome.de/eclipse/
Choisir Beta->Findbugs (la version officielle courante contient un bug)

#---------------------#
# Compiling & running #
#---------------------#

Click-droit sur projet VitaVermis -> RunAs -> Run Configurations
* Créer une nouvelle configuration de type Java Application
* Choisir ppg.vitavermis.Main comme "Main class"
* Dans Arguments->VM arguments ajouter -Djava.library.path=exec

##Eclipse export:
Project righ click -> Export -> Runnable JAR
Puis copier le dossier "data/" dans DemoLucas.jar (c'est une archive au format .zip en fait)

##Manual export::
cd trunk/
#1-Compile
rm -rf bin/*
libs="src/" ; for f in lib/*.jar; do libs="$libs:$f"; done
javac -cp $libs -d bin/ $(find src/ -name *.java)
#2-Pack
mkdir lib_extracted
for file in $(find ../lib/ -name *.jar | grep -v mockito); do jar xf $file; done
cd ..
jar cmf MANIFEST.MF exec/VitaVermis.jar -C bin/ ppg/ -C lib_extracted/ . -C data/ .
rm -rf lib_extracted
#3-Run!
cd exec
java -cp . -jar VitaVermis.jar
# To be sure you're using the correct Java : namei $(which java)

#--------#
# Others #
#--------#

# Subversion 1.7 on Ubuntu 11.10
echo "deb http://opensource.wandisco.com/ubuntu lucid svn17" | sudo tee /etc/apt/sources.list.d/svn.list
sudo wget -q http://opensource.wandisco.com/wandisco-debian.gpg -O- | sudo apt-key add -
sudo apt-get update
sudo apt-get dist-upgrade

# Yedit: YAML files editor
Eclipse : Help -> Install New Software : http://dadacoalition.org/yedit/


