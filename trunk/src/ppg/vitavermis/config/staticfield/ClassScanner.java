package ppg.vitavermis.config.staticfield;

import java.io.File;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;


/**
 * @author lucas
 *
 * Listing a pkg classes : http://code.google.com/p/ldapbeans/source/browse/trunk/ldapbeans/src/main/java/ldapbeans/util/scanner/PackageHelper.java
 */
public final class ClassScanner {
	
	private ClassScanner() { }
	
	static Set<Class<?>> getPackageClasses(String pkgName) {
		File directory = getPackageDirectory(pkgName);
   		System.out.println("[INFO] Package directory found : " + directory.getAbsolutePath());
		if (!directory.isDirectory()) {
			throw new StaticFieldLoadingError("Wrong package directory found : " + directory);
		}
		final String ignorePattern = ppg.vitavermis.config.Settings.PATTERN_FOR_CLASSES_TO_IGNORE_STATIC_PARAMS;
		return getClassesInDirectory(directory, pkgName, ignorePattern);
	}
	
	static Set<Class<?>> getClassesInDirectory(File directory, String pkgName, String ignorePattern) {
		Set<Class<?>> classes = new HashSet<Class<?>>();
        for (File file : directory.listFiles()) {
        	if (file.isDirectory()) {
        		classes.addAll(getClassesInDirectory(file, pkgName + '.' + file.getName(), ignorePattern));
        	} else if (file.getName().endsWith(".class")) {
        		String className = pkgName + '.' + file.getName().replace(".class", "");
        		if (!className.matches(ignorePattern)) {
        			try {
        				classes.add(Class.forName(className));
					} catch (ClassNotFoundException e) {
				   		System.out.println("[WARNING] Class " + className + "not found");
					}
        		}
        	}
        }
        return classes;
	}
	
	static File getPackageDirectory(String pkgName) {
		ClassLoader cld = ClassLoader.getSystemClassLoader();
        if (cld == null) {
            throw new StaticFieldLoadingError("Unable to access system ClassLoader");
        }
        String pkgPath = pkgName.replace('.', '/');
        URL pkgResource = cld.getResource(pkgPath);
        if (pkgResource == null) {
        	throw new StaticFieldLoadingError("No system ClassLoader resources matching " + pkgPath);
        }
        if ("jar".equals(pkgResource.getProtocol())) {
        	throw new StaticFieldLoadingError("UNHANDLED at the moment, tell @lucas about this");
        	/*JarURLConnection conn = pkgResource.openConnection();
            JarFile jarfile = conn.getJarFile();
            if (!pkgPath.endsWith("/")) {
            	pkgPath += '/';
            }
            return new ZipFileProxy(jarfile).getFile(path);*/
        }
        return new File(pkgResource.getFile());
	}
	
}
