package org.lo.d.commons.reflections;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.lo.d.commons.StreamSupport;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class ReflectionSupport {

	public interface FieldFilter {
		enum State {
			INCLUDE, EXCLUDE;
		}

		State doFilter(Field field);
	}

	public interface MethodFilter {
		enum State {
			INCLUDE, EXCLUDE;
		}

		State doFilter(Method method);
	}

	public static class Test {
		static {
			System.out.println("Test");
			System.out.println("Test");
			System.out.println("Test");
			System.out.println("Test");
			System.out.println("Test");
			System.out.println("Test");
			System.out.println("Test");
		}
	}

	public interface Worker {
		void work(Class<?> cls);
	}

	private static class CompositeFileClassesFindJob implements Callable<Void> {
		private final File compositeFile;
		private final String packageName;
		private final Set<? extends Worker> workers;
		private final ClassLoader classLoader;

		private CompositeFileClassesFindJob(File compositeFile, String packageName, Set<? extends Worker> workers,
				ClassLoader classLoader) {
			this.compositeFile = compositeFile;
			this.packageName = packageName;
			this.workers = workers;
			this.classLoader = classLoader;
		}

		@Override
		public Void call() throws Exception {
			findClassesInCompositeFile(compositeFile, packageName, workers, classLoader);
			return null;
		}

		private void findClassesInCompositeFile(File compositeFile, String packageName, Set<? extends Worker> workers,
				ClassLoader classLoader) {
			if (!compositeFile.exists()) {
				return;
			}
			if (compositeFile.isDirectory()) {
				return;
			}
			FileInputStream fileinputstream = null;
			ZipInputStream zipinputstream = null;
			try {
				fileinputstream = new FileInputStream(compositeFile);
				zipinputstream = new ZipInputStream(fileinputstream);
				ZipEntry zipentry;

				do {
					zipentry = zipinputstream.getNextEntry();
					if (zipentry == null) {
						break;
					}
					if (!zipentry.isDirectory()) {
						String lname = zipentry.getName();
						boolean exclude = false;
						for (Pattern pattern : PATTERN_FILE_EXCLUDES) {
							exclude = exclude || pattern.matcher(lname).find();
						}

						if (lname.endsWith(".class")) {
							lname = lname.replace('/', '.');
							try {

								Class<?> _class;
								try {
									_class = Class.forName(lname.substring(0, lname.length() - 6), false, classLoader);
								} catch (ExceptionInInitializerError e) {
									e.printStackTrace();
									// happen, for example, in classes, which depend on
									// Spring to inject some beans, and which fail,
									// if dependency is not fulfilled
									_class = Class.forName(lname.substring(0, lname.length() - 6), false, Thread
											.currentThread().getContextClassLoader());
								}
								for (Worker worker : workers) {
									worker.work(_class);
								}
							} catch (Throwable e) {
							}
						}
					}
				} while (true);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				StreamSupport.quietClose(zipinputstream);
				StreamSupport.quietClose(fileinputstream);
			}
		}
	}

	private static class DefaultFieldFilter implements FieldFilter {

		@Override
		public State doFilter(Field field) {
			return State.INCLUDE;
		}
	}

	private static class DefaultMethodFilter implements MethodFilter {
		@Override
		public State doFilter(Method method) {
			return State.INCLUDE;
		}
	}

	private static final Pattern[] PATTERN_DIR_EXCLUDES;

	static {
		PATTERN_DIR_EXCLUDES = new Pattern[] { Pattern.compile("[\\\\/]bin[\\\\/]((?!minecraft.jar)[^\\\\])+$"),
				Pattern.compile("[\\\\/]lib[\\\\/][^\\\\/]+$"), };
	}

	private static final Pattern[] PATTERN_FILE_EXCLUDES;

	static {
		PATTERN_FILE_EXCLUDES = new Pattern[] { Pattern.compile("^java\\."), Pattern.compile("^javax\\."), };
	}

	public static Field[] getAllRecursiveFields(Class<?> cls) {
		return getFilteredRecursiveFields(cls, new DefaultFieldFilter());
	}

	public static Method[] getAllRecursiveMethods(Class<?> cls) {
		return getFilteredRecursiveMethods(cls, new DefaultMethodFilter());
	}

	/**
	 * Scans all classes accessible from the context class loader which belong
	 * to the given package and subpackages.
	 *
	 * @param packageName
	 *            The base package
	 * @return The classes
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static void getClasses(String packageName, Set<? extends Worker> workers) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		assert classLoader != null;
		String path = packageName.replace('.', '/');
		Enumeration<URL> resources;
		Set<File> dirs = Sets.newHashSet();
		try {
			resources = classLoader.getResources(path);
			while (resources.hasMoreElements()) {
				URL resource = resources.nextElement();
				String fileName = resource.getFile();
				String fileNameDecoded = URLDecoder.decode(fileName, "UTF-8");
				dirs.add(new File(fileNameDecoded));
			}
			if (classLoader instanceof URLClassLoader) {
				for (URL resource : ((URLClassLoader) classLoader).getURLs()) {
					String fileName = resource.getFile();
					String fileNameDecoded = URLDecoder.decode(fileName, "UTF-8");
					dirs.add(new File(fileNameDecoded));
				}
			}
		} catch (IOException e1) {
		}
		ExecutorService executorService = Executors.newCachedThreadPool();
		List<Callable<Void>> calls = Lists.newArrayList();
		for (File directory : dirs) {
			String directoryString = directory.toString();
			boolean exclude = false;
			for (Pattern pattern : PATTERN_DIR_EXCLUDES) {
				exclude = exclude || pattern.matcher(directoryString).find();
			}
			if (exclude) {
				continue;
			}
			System.out.println(directory);
			findClasses(directory, packageName, workers, classLoader, calls);
		}

		try {
			executorService.invokeAll(calls);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		executorService.shutdown();
	}

	public static Field[] getFilteredRecursiveFields(Class<?> cls, final FieldFilter filter) {
		final Set<Field> fields = Sets.newLinkedHashSet();

		recursiveProcess(cls, new Worker() {
			@Override
			public void work(Class<?> cls) {
				for (Field field : cls.getDeclaredFields()) {
					for (Field f : fields) {
						if (f.getName().equals(field.getName())) {
							continue;
						}
					}
					if (filter.doFilter(field) == FieldFilter.State.INCLUDE) {
						fields.add(field);
					}
				}
			}
		});

		return fields.toArray(new Field[] {});
	}

	public static Method[] getFilteredRecursiveMethods(Class<?> cls, final MethodFilter filter) {
		final Set<Method> methods = Sets.newLinkedHashSet();
		recursiveProcess(cls, new Worker() {
			@Override
			public void work(Class<?> cls) {
				for (Method method : cls.getDeclaredMethods()) {
					for (Method m : methods) {
						if (m.getName().equals(method.getName())) {
							continue;
						}
					}
					if (filter.doFilter(method) == MethodFilter.State.INCLUDE) {
						methods.add(method);
					}
				}
			}
		});
		return methods.toArray(new Method[] {});
	}

	public static void main(String[] args) throws URISyntaxException {
		String directoryString;
		directoryString = "V:\\forge_mcp\\1.5.2-7.8.0.712\\forge\\mcp\\jars\\bin\\lwjgl_util.jar";
		for (Pattern pattern : PATTERN_DIR_EXCLUDES) {
			Matcher matcher = pattern.matcher(directoryString);
			if (matcher.find()) {
				System.out.println("find: " + matcher.group());
			}
		}
		directoryString = "V:\\forge_mcp\\1.5.2-7.8.0.712\\forge\\mcp\\jars\\bin\\minecraft.jar";
		for (Pattern pattern : PATTERN_DIR_EXCLUDES) {
			Matcher matcher = pattern.matcher(directoryString);
			if (matcher.find()) {
				System.out.println("find: " + matcher.group());
			}
		}
		getClasses("", Sets.newHashSet(new Worker() {
			@Override
			public void work(Class<?> cls) {
			}
		}));
		//		new Test();
	}

	public static void recursiveProcess(Class<?> cls, Worker worker) {
		if (cls == null) {
			return;
		}
		worker.work(cls);
		recursiveProcess(cls.getSuperclass(), worker);
	}

	/**
	 * Recursive method used to find all classes in a given directory and
	 * subdirs.
	 *
	 * @param directory
	 *            The base directory
	 * @param packageName
	 *            The package name for classes found inside the base directory
	 * @param classLoader
	 * @return The classes
	 * @throws ClassNotFoundException
	 */
	private static void findClasses(File directory, String packageName, Set<? extends Worker> workers,
			ClassLoader classLoader, List<Callable<Void>> lazyAsyncCalls) {
		if (!directory.exists()) {
			return;
		}
		if (!directory.isDirectory()) {
			lazyAsyncCalls.add(new CompositeFileClassesFindJob(directory, packageName, workers, classLoader));
			return;
		}
		File[] files = directory.listFiles();
		String sep = packageName.isEmpty() ? "" : ".";
		for (File file : files) {
			String fileName = file.getName();
			if (file.isDirectory()) {
				assert !fileName.contains(".");
				findClasses(file, packageName + sep + fileName, workers, classLoader, lazyAsyncCalls);
			} else if (fileName.endsWith(".class")) {
				try {
					Class<?> _class;
					try {
						_class = Class.forName(packageName + sep + fileName.substring(0, fileName.length() - 6), false,
								classLoader);
					} catch (ExceptionInInitializerError e) {
						e.printStackTrace();
						// happen, for example, in classes, which depend on
						// Spring to inject some beans, and which fail,
						// if dependency is not fulfilled
						_class = Class.forName(packageName + sep + fileName.substring(0, fileName.length() - 6), false,
								Thread.currentThread().getContextClassLoader());
					}
					for (Worker worker : workers) {
						worker.work(_class);
					}
				} catch (Throwable e) {
				}
			} else {
				lazyAsyncCalls.add(new CompositeFileClassesFindJob(directory, packageName, workers, classLoader));
			}
		}
		return;
	}
}
