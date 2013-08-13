package ppg.vitavermis.config;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.MissingResourceException;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


/**
 * @author lucas
 *
 * LIMITATION: Does no list nested classes (static or not) 
 *
 * Class containing ".*Test.*" are ignored
 *
 * Code inspiration : http://code.google.com/p/ldapbeans/source/browse/trunk/ldapbeans/src/main/java/ldapbeans/util/scanner/PackageHelper.java
 */
public final class ClassScanner {
	
	private ClassScanner() { }
	
	public static Collection<Class<?>> getPackageClasses(String pkgName) {
		Collection<String> classeNames = getPackageClasseNames(pkgName);
		return createClassesFromFilenames(classeNames);
	}
	
	static Collection<String> getPackageClasseNames(String pkgName) {
		final URL pkgResource = getPackageRessource(pkgName);
   		System.out.println("[INFO] Package ressource found : " + pkgResource);
   		final String ressourceProtocol = pkgResource.getProtocol();
        if ("file".equals(ressourceProtocol)) {
    		final File directory = new File(pkgResource.getFile());
        	return getClassFilenamesInDirectory(directory, pkgName);
        } else if ("jar".equals(ressourceProtocol)) {
        	JarFile jarfile = null;
        	try {
        		final JarURLConnection conn = (JarURLConnection) pkgResource.openConnection();
        		jarfile = conn.getJarFile();
        	} catch (IOException e) {
				throw new ClassCastException("Cannot convert to JarFile :" + e);
        	}
        	return getClassFilenamesInJar(jarfile, pkgName);
        } else {
        	throw new UnsupportedOperationException("Unknown protocol : " + ressourceProtocol);
        }		
	}
	
	static URL getPackageRessource(String pkgName) {
		ClassLoader cld = ClassLoader.getSystemClassLoader();
        if (cld == null) {
            throw new NullPointerException("Unable to access system ClassLoader");
        }
        final String pkgPath = pkgName.replace('.', '/');
        URL pkgResource = cld.getResource(pkgPath);
        if (pkgResource == null) {
        	throw new NullPointerException("No system ClassLoader resources matching " + pkgPath);
        }
        return pkgResource;
	}

	static Collection<String> getClassFilenamesInDirectory(File directory, String pkgName) {
		Collection<String> classFilenames = new LinkedList<String>();
        for (File file : directory.listFiles()) {
        	final String filename = file.getName();
        	if (file.isDirectory()) {
        		classFilenames.addAll(getClassFilenamesInDirectory(file, pkgName + '.' + filename));
        	} else if (filename.endsWith(".class")) {
        		classFilenames.add(pkgName + '.' + filename);
        	}
        }
        return classFilenames;
	}
	
	static Collection<String> getClassFilenamesInJar(JarFile jarfile, String pkgName) {
		Collection<String> classFilenames = new LinkedList<String>();
		final Enumeration<JarEntry> entries = (Enumeration<JarEntry>) jarfile.entries();
		while (entries.hasMoreElements()) {
			String jarEntryName = entries.nextElement().getName().replace('/', '.');
			if (jarEntryName.contains(pkgName) && jarEntryName.endsWith(".class")) {
				classFilenames.add(jarEntryName);
			}
		}
		return classFilenames;
	}

	static Collection<Class<?>> createClassesFromFilenames(Collection<String> classFilenames) {
		Collection<Class<?>> classes = new LinkedList<Class<?>>();
		for (String classFilename : classFilenames) {
			if (!classFilename.matches(".*Test.*")) {
				try {
					classes.add(Class.forName(classFilename.replace(".class", "")));
				} catch (ClassNotFoundException e) {
					throw new MissingResourceException("Cannot create class :" + e, classFilename, "");
				}
			}
		}
		return classes;
	}	
}
