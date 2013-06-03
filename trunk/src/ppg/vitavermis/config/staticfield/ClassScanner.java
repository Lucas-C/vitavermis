package ppg.vitavermis.config.staticfield;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


/**
 * @author lucas
 *
 * Code inspiration : http://code.google.com/p/ldapbeans/source/browse/trunk/ldapbeans/src/main/java/ldapbeans/util/scanner/PackageHelper.java
 */
public final class ClassScanner {
	
	private ClassScanner() { }
	
	static Set<Class<?>> getPackageClasses(String pkgName) {
		URL pkgResource = getPackageRessource(pkgName);
   		System.out.println("[INFO] Package ressource found : " + pkgResource);
   		String ressourceProtocol = pkgResource.getProtocol(); 
        if ("file".equals(ressourceProtocol)) {
    		final File directory = new File(pkgResource.getFile());
        	return getClassesInDirectory(directory, pkgName);
        } else if ("jar".equals(ressourceProtocol)) {
        	JarFile jarfile = null;
        	try {
        		final JarURLConnection conn = (JarURLConnection) pkgResource.openConnection();
        		jarfile = conn.getJarFile();
        	} catch (IOException e) {
				throw new ClassCastException("Cannot convert to JarFile :" + e);
        	}
        	return getClassesInJar(jarfile, pkgName);
        } else {
        	throw new UnsupportedOperationException("Unknown protocol : " + ressourceProtocol);
        }
	}
	
	static URL getPackageRessource(String pkgName) {
		ClassLoader cld = ClassLoader.getSystemClassLoader();
        if (cld == null) {
            throw new NullPointerException("Unable to access system ClassLoader");
        }
        String pkgPath = pkgName.replace('.', '/');
        URL pkgResource = cld.getResource(pkgPath);
        if (pkgResource == null) {
        	throw new NullPointerException("No system ClassLoader resources matching " + pkgPath);
        }
        return pkgResource;
	}

	static Set<Class<?>> getClassesInDirectory(File directory, String pkgName) {
		Set<Class<?>> classes = new HashSet<Class<?>>();
        for (File file : directory.listFiles()) {
        	if (file.isDirectory()) {
        		classes.addAll(getClassesInDirectory(file, pkgName + '.' + file.getName()));
        	} else {
        		addClassToSet(pkgName + '.' + file.getName(), classes);
        	}
        }
        return classes;
	}
	
	static Set<Class<?>> getClassesInJar(JarFile jarfile, String pkgName) {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		final Enumeration<JarEntry> entries = (Enumeration<JarEntry>) jarfile.entries();
		while (entries.hasMoreElements()) {
			String jarEntryName = entries.nextElement().getName().replace('/', '.');
			if (jarEntryName.contains(pkgName)) {
				addClassToSet(jarEntryName, classes);
			}
		}
		return classes;
	}

	static void addClassToSet(String classfilename, Set<Class<?>> classes) {
		if (classfilename.endsWith(".class") && !classfilename.matches(".*Test.*")) {
			try {
				classes.add(Class.forName(classfilename.replace(".class", "")));
			} catch (ClassNotFoundException e) {
				throw new MissingResourceException("Cannot create class :" + e, classfilename, "");
			}
		}		
	}	
}
