package com.swq.WeiXinBasePackage.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @description: 类操作相关工具
 * @date: 2016年2月23日 上午11:00:19
 * @author: Xu
 */
public class ClassTypeUtil {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	private static final String RESOURCE_PATTERN = "/**/*.class";
	private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
	private List<String> packagesList = new LinkedList<String>();
	private List<TypeFilter> typeFilters = new LinkedList<TypeFilter>();
	private Set<Class<?>> classSet = new HashSet<Class<?>>();

	public ClassTypeUtil(String[] packagesToScan, Class<? extends Annotation>... annotationFilter) {
		if (packagesToScan != null) {
			for (String packagePath : packagesToScan) {
				this.packagesList.add(packagePath);
			}
		}
		if (annotationFilter != null) {
			for (Class<? extends Annotation> annotation : annotationFilter) {
				typeFilters.add(new AnnotationTypeFilter(annotation, false));
			}
		}
	}

	public Set<Class<?>> getClassSet() throws IOException, ClassNotFoundException {
		this.classSet.clear();
		if (!this.packagesList.isEmpty()) {
			for (String pkg : this.packagesList) {
				String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + ClassUtils.convertClassNameToResourcePath(pkg) + RESOURCE_PATTERN;
				Resource[] resources = this.resourcePatternResolver.getResources(pattern);
				MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(this.resourcePatternResolver);
				for (Resource resource : resources) {
					if (resource.isReadable()) {
						MetadataReader reader = readerFactory.getMetadataReader(resource);
						String className = reader.getClassMetadata().getClassName();
						if (matchesEntityTypeFilter(reader, readerFactory)) {
							this.classSet.add(Class.forName(className));
						}
					}
				}
			}
		}
		// 输出日志
		if (logger.isInfoEnabled()) {
			for (Class<?> clazz : this.classSet) {
				logger.info(String.format("Found class:%s", clazz.getName()));
			}
		}
		return this.classSet;
	}

	private boolean matchesEntityTypeFilter(MetadataReader reader, MetadataReaderFactory readerFactory) throws IOException {
		if (!this.typeFilters.isEmpty()) {
			for (TypeFilter filter : this.typeFilters) {
				if (filter.match(reader, readerFactory)) {
					return true;
				}
			}
		}
		return false;
	}
}