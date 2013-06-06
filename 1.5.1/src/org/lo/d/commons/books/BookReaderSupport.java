package org.lo.d.commons.books;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

import org.lo.d.commons.books.BookReaderSupport.BookCommandBean.Alias;
import org.lo.d.commons.books.BookReaderSupport.BookCommandBean.Aliases;
import org.lo.d.commons.books.BookReaderSupport.BookCommandBean.Required;
import org.lo.d.commons.reflections.ReflectionSupport;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

public class BookReaderSupport {

	public interface BookCommandBean {
		@Retention(RetentionPolicy.RUNTIME)
		@Target({
				ElementType.METHOD, ElementType.FIELD
		})
		@interface Alias {
			String value();
		}

		@Retention(RetentionPolicy.RUNTIME)
		@Target({
				ElementType.METHOD, ElementType.FIELD
		})
		@interface Aliases {
			Alias[] value();
		}

		@Retention(RetentionPolicy.RUNTIME)
		@Target({
				ElementType.METHOD, ElementType.FIELD
		})
		@interface Required {
		}

	}

	public interface BookCommandBeanFactory<T extends BookCommandBean> {
		T createBean();
	}

	public interface BookReader {
		public enum State {
			CONTINUE, BREAK, ;
		}

		State readLine(String line);
	}

	public static void readBook(ItemStack book, BookReader bookReader) {
		if (book.getTagCompound() == null) {
			return;
		}
		NBTTagList pages = book.getTagCompound().getTagList("pages");
		if (pages == null || pages.tagCount() == 0) {
			return;
		}

		List<String> lines = splitBookText(pages);

		for (String line : lines) {
			if (bookReader.readLine(line) == BookReader.State.BREAK) {
				return;
			}
		}
		return;
	}

	public static <T extends BookCommandBean> T readBookCommandToBean(ItemStack book, BookCommandBeanFactory<T> factory) {
		T bean = factory.createBean();

		final Multimap<String, String> map = readBookCommandToMap(book);

		Field[] fields = ReflectionSupport.getFilteredRecursiveFields(bean.getClass(),
				new ReflectionSupport.FieldFilter() {
					@Override
					public State doFilter(Field field) {
						if (Modifier.isStatic(field.getModifiers())) {
							return State.EXCLUDE;
						}
						if (!map.containsKey(field.getName().toUpperCase())) {
							for (String k : getAliasKeys(field)) {
								if (map.containsKey(k.toUpperCase())) {
									return State.INCLUDE;
								}
							}
							return State.EXCLUDE;
						}
						return State.INCLUDE;
					}
				});

		for (Field field : fields) {
			String key = field.getName().toUpperCase();
			if (!map.containsKey(key)) {
				for (String k : getAliasKeys(field)) {
					String upperK = k.toUpperCase();
					if (map.containsKey(upperK)) {
						key = upperK;
						break;
					}
				}
			}

			if (!map.containsKey(key)) {
				Required required = field.getAnnotation(Required.class);
				if (required != null) {
					return null;
				} else {
					continue;
				}
			}

			Collection<String> values = map.get(key);

			if (values.size() == 1 && field.getType() == String.class) {
				try {
					field.setAccessible(true);
					field.set(bean, values.iterator().next());
				} catch (IllegalArgumentException e) {
				} catch (IllegalAccessException e) {
				}
			} else if (values.size() > 1 && field.getType() == String[].class) {
				try {
					field.setAccessible(true);
					field.set(bean, values.toArray(new String[] {}));
				} catch (IllegalArgumentException e) {
				} catch (IllegalAccessException e) {
				}
			}

		}

		return bean;
	}

	public static Multimap<String, String> readBookCommandToMap(ItemStack book) {
		final Multimap<String, String> map = ArrayListMultimap.create();
		if (book != null) {
			readBook(book, new BookReader() {
				@Override
				public State readLine(String line) {
					String[] prop = line.split(",");
					if (prop == null || prop.length < 2) {
						return State.CONTINUE;
					}
					String key = prop[0].trim().toUpperCase();
					if (key.isEmpty()) {
						return State.CONTINUE;
					}
					List<String> values = Lists.newArrayList();
					for (int i = 1; i < prop.length; i++) {
						values.add(prop[i].trim().toUpperCase());
					}

					map.putAll(key, values);
					return State.CONTINUE;
				}
			});
		}
		return map;
	}

	public static List<String> splitBookText(NBTTagList pages) {
		List<String> lines = Lists.newArrayList();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < pages.tagCount(); i++) {
			NBTBase tag = pages.tagAt(i);
			if (!(tag instanceof NBTTagString)) {
				return Lists.newArrayList();
			}
			NBTTagString nbtTagString = (NBTTagString) tag;
			String l = nbtTagString.data.replaceAll("[\\r\\n]", "");
			sb.append(l);

			if (l.contains(";")) {
				String[] ls = sb.toString().split(";");
				for (String str : ls) {
					String trimedStr = str == null ? "" : str;
					if (ls[ls.length - 1] != str) {
						lines.add(trimedStr);
					} else {
						sb = new StringBuilder(trimedStr);
					}
				}
			}
		}
		String[] ls = sb.toString().split(";");
		for (String str : ls) {
			String trimedStr = str == null ? "" : str;
			lines.add(trimedStr);
		}
		return lines;
	}

	private static Set<String> getAliasKeys(AnnotatedElement element) {
		{
			Alias alias = element.getAnnotation(Alias.class);
			if (alias != null) {
				return Sets.newHashSet(alias.value());
			}
		}

		Aliases aliases = element.getAnnotation(Aliases.class);
		if (aliases != null) {
			HashSet<String> set = Sets.newHashSet();
			for (Alias alias : aliases.value()) {
				set.add(alias.value());
			}
			return set;
		}

		return Sets.newHashSet();
	}
}
