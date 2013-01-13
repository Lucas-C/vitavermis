TODO:
- collisions & platformes
- infinite jump
- test perfs
- levels XML


Peu (pas ?) de documentation du code pour le moment.

* Compilé avec eclipse-java-juno-win32.zip.
(Un compilateur 64bits posera pb avec les dll, et une v 32bits fonctionnera chez tout le monde)

* Lors de la création d'un Java project à partir du dossier dans Eclipse, choisir JRE 1.6

* Options de la VM au runtime :
-Djava.library.path=${workspace_loc:DemoLucas}/exec -ea
(-Dorg.lwjgl.opengl.Display.allowSoftwareOpenGL=true)

##EXPORT:
Project righ click -> Export -> Runnable JAR
Puis copier le dossier "data/" dans DemoLucas.jar (c'est une archive au format .zip en fait)
