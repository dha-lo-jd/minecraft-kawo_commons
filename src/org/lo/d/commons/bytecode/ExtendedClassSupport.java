package org.lo.d.commons.bytecode;

import java.lang.reflect.Method;
import java.util.List;

import net.minecraft.launchwrapper.LaunchClassLoader;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import com.google.common.collect.Lists;

public class ExtendedClassSupport {

	public static class ClassName {

		private static final String EXT = ".class";
		private final FQN fqn;
		private final ClassPath path;
		private final String simpleName;

		public ClassName(Class<?> cls) {
			path = new ClassPathImpl(Type.getInternalName(cls));
			fqn = new FQNImpl(path.getName().replace('/', '.'));
			simpleName = cls.getSimpleName();
		}

		public String getFileName() {
			return getPath().getName().concat(EXT);
		}

		public FQN getFqn() {
			return fqn;
		}

		public ClassPath getPath() {
			return path;
		}

		public String getSimpleName() {
			return simpleName;
		}

	}

	public interface ClassPath extends StringValue {
	}

	public interface FQN extends StringValue {
	}

	public static class GenerateClassName {

		private final ClassName baseClassName;

		private final ClassName oldSuperClassName;
		private final ClassName newSuperClassName;

		private final ClassName markerInerfaceName;

		private final NewGenarateClassName newGenarateClassName;

		public GenerateClassName(Class<?> baseClass, Class<?> oldSuperClass, Class<?> newSuperClass,
				Class<?> markerInterface) {
			baseClassName = new ClassName(baseClass);
			oldSuperClassName = new ClassName(oldSuperClass);
			newSuperClassName = new ClassName(newSuperClass);
			markerInerfaceName = new ClassName(markerInterface);
			newGenarateClassName = new NewGenarateClassName(markerInerfaceName, newSuperClassName);
		}

		public ClassName getBaseClassName() {
			return baseClassName;
		}

		public ClassName getMarkerInerfaceName() {
			return markerInerfaceName;
		}

		public NewGenarateClassName getNewGenarateClassName() {
			return newGenarateClassName;
		}

		public ClassName getNewSuperClassName() {
			return newSuperClassName;
		}

		public ClassName getOldSuperClassName() {
			return oldSuperClassName;
		}
	}

	public interface NewClassFQN extends FQN {
	}

	public interface NewClassPath extends ClassPath {
	}

	public static class NewGenarateClassName {
		private final NewClassFQN fqn;
		private final NewClassPath path;

		public NewGenarateClassName(ClassName markerInerfaceName, ClassName superClassName) {
			path = new NewClassPathImpl(markerInerfaceName.getPath().getName().concat("_")
					.concat(superClassName.getSimpleName()));
			fqn = new NewClassFQNImpl(path.getName().replace('/', '.'));
		}

		public NewClassFQN getFqn() {
			return fqn;
		}

		public NewClassPath getPath() {
			return path;
		}
	}

	public interface StringValue {
		public String getName();
	}

	private static class ClassPathImpl extends StringValueObject implements ClassPath {
		public ClassPathImpl(String name) {
			super(name);
		}
	}

	private static class FQNImpl extends StringValueObject implements FQN {
		public FQNImpl(String name) {
			super(name);
		}
	}

	private static class NewClassFQNImpl extends FQNImpl implements NewClassFQN {
		public NewClassFQNImpl(String name) {
			super(name);
		}
	}

	private static class NewClassPathImpl extends ClassPathImpl implements NewClassPath {
		public NewClassPathImpl(String name) {
			super(name);
		}
	}

	private static class StringValueObject {
		private final String name;

		public StringValueObject(String name) {
			super();
			this.name = name;
		}

		public String getName() {
			return name;
		}

	}

	public static Class<?> loadAndGenerateNewExtendedClass(Class<?> baseClass, Class<?> oldSuperClass,
			Class<?> newSuperClass, Class<?> markerInterface, Iterable<Class<?>> appendInterfaces) {
		try {
			final GenerateClassName generateClassName = new ExtendedClassSupport.GenerateClassName(baseClass,
					oldSuperClass, newSuperClass, markerInterface);
			final String baseClassName = generateClassName.getBaseClassName().getPath().getName();
			NewGenarateClassName newGenarateClassName = generateClassName.getNewGenarateClassName();
			final String newClassName = newGenarateClassName.getPath().getName();
			final String oldSuperClassName = generateClassName.getOldSuperClassName().getPath().getName();
			final String newSuperClassName = generateClassName.getNewSuperClassName().getPath().getName();
			final List<String> interfaceList = Lists.newArrayList();

			ClassWriter cw = new ClassWriter(Opcodes.ASM4);
			ClassReader cr = new ClassReader(baseClass.getClassLoader().getResourceAsStream(
					generateClassName.getBaseClassName().getFileName()));
			ClassVisitor cv = new ClassVisitor(Opcodes.ASM4, cw) {

				@Override
				public void visit(int version, int access, String name, String signature, String superName,
						String[] interfaces) {
					interfaceList.addAll(Lists.newArrayList(interfaces));
					super.visit(version, access, name, signature, superName, interfaces);
				}

				@Override
				public MethodVisitor visitMethod(int access, String name, String desc, String signature,
						String[] exceptions) {
					MethodVisitor mv = cv.visitMethod(access, name, fix(desc), fix(signature), exceptions);
					if (mv != null) {
						mv = new MethodVisitor(Opcodes.ASM4, mv) {

							@Override
							public void visitFieldInsn(int opcode, String owner, String name, String desc) {
								if (owner.equals(baseClassName)) {
									mv.visitFieldInsn(opcode, newClassName, name, fix(desc));
								} else {
									mv.visitFieldInsn(opcode, owner, name, fix(desc));
								}
							}

							@Override
							public void visitLocalVariable(String name, String desc, String signature, Label start,
									Label end, int index) {
								mv.visitLocalVariable(name, fix(desc), signature, start, end, index);
							}

							@Override
							public void visitMethodInsn(int opcode, String owner, String name, String desc) {
								if (owner.equals(baseClassName)) {
									mv.visitMethodInsn(opcode, newClassName, name, fix(desc));
								} else if (owner.equals(oldSuperClassName)) {
									mv.visitMethodInsn(opcode, newSuperClassName, name, fix(desc));
								} else {
									mv.visitMethodInsn(opcode, owner, name, fix(desc));
								}
							}

							@Override
							public void visitTypeInsn(int i, String s) {
								if (baseClassName.equals(s)) {
									s = newClassName;
								}
								mv.visitTypeInsn(i, s);
							}

						};
					}
					return mv;
				}

				private String fix(String s) {
					if (s != null) {
						if (s.indexOf(baseClassName) != -1) {
							s = s.replaceAll(baseClassName, newClassName);
						}
					}
					return s;
				}
			};

			cr.accept(cv, ClassReader.EXPAND_FRAMES);

			String markerInerfaceName = generateClassName.getMarkerInerfaceName().getPath().getName();
			if (!interfaceList.contains(markerInerfaceName)) {
				interfaceList.add(markerInerfaceName);
			}
			for (Class<?> appendInterface : appendInterfaces) {
				ClassName appendInterfaceName = new ClassName(appendInterface);
				String name = appendInterfaceName.getPath().getName();
				if (!interfaceList.contains(name)) {
					interfaceList.add(name);
				}
			}
			cv.visit(Opcodes.V1_7, Opcodes.ACC_PUBLIC | Opcodes.ACC_SUPER, newClassName, null, newSuperClassName,
					interfaceList.toArray(new String[] {}));

			//			byte[] byteArray = cw.toByteArray();
			//			File f = new File("V:\\eclipse_temp\\test.class");
			//			try (FileOutputStream fo = new FileOutputStream(f);) {
			//				fo.write(byteArray);
			//			} catch (Exception e) {
			//				e.printStackTrace();
			//			}
			return loadClass(newGenarateClassName.getFqn(), cw.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	private static Class<?> loadClass(NewClassFQN className, byte[] b) {
		return loadClass(className.getName(), b);
	}

	private static Class<?> loadClass(String className, byte[] b) {
		//override classDefine (as it is protected) and define the class.
		Class<?> clazz = null;
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			if (classLoader instanceof LaunchClassLoader) {
				LaunchClassLoader rcl = (LaunchClassLoader) classLoader;
				Method method = rcl.getClass().getDeclaredMethod("runTransformers",
						new Class<?>[] { String.class, String.class, byte[].class });
				method.setAccessible(true);
				b = (byte[]) method.invoke(rcl, className, className, b);
			}
			Class<?> cls = Class.forName("java.lang.ClassLoader");
			java.lang.reflect.Method method = cls.getDeclaredMethod("defineClass", new Class[] { String.class,
					byte[].class, int.class, int.class });

			// protected method invocaton
			method.setAccessible(true);
			try {
				Object[] args = new Object[] { className, b, new Integer(0), new Integer(b.length) };
				clazz = (Class<?>) method.invoke(classLoader, args);
			} finally {
				method.setAccessible(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clazz;
	}

}
