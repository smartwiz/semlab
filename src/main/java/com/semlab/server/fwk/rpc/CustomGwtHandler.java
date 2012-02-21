package com.semlab.server.fwk.rpc;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gwtwidgets.server.spring.DefaultRPCServiceExporterFactory;
import org.gwtwidgets.server.spring.GWTRPCServiceExporter;
import org.gwtwidgets.server.spring.GWTRequestMapping;
import org.gwtwidgets.server.spring.RPCServiceExporter;
import org.gwtwidgets.server.spring.RPCServiceExporterFactory;
import org.gwtwidgets.server.spring.ReflectionUtils;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ServletConfigAware;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.AbstractUrlHandlerMapping;

import com.google.gwt.user.client.rpc.RemoteService;

/**
 * The GWTHandler implements a Spring {@link HandlerMapping} which maps RPC from
 * URLs to {@link RemoteService} implementations. It does so by wrapping service
 * beans with a {@link GWTRPCServiceExporter} dynamically proxying all
 * {@link RemoteService} interfaces implemented by the service and delegating
 * RPC to these interfaces to the service. It is possible to use custom
 * implementations of the {@link GWTRPCServiceExporter}, see
 * {@link #setServiceExporterFactory(RPCServiceExporterFactory)}. Will also pick
 * up any beans with an {@link GWTRequestHandlerMapping} annotation and publish
 * it under the specified URL.
 * 
 * 
 * @author John Chilton
 * @author George Georgovassilis, g.georgovassilis[at]gmail.com
 * 
 */
public class CustomGwtHandler extends AbstractUrlHandlerMapping implements
		HandlerMapping, InitializingBean, ServletContextAware,
		ServletConfigAware {
	
	private static Log log = LogFactory.getLog(CustomGwtHandler.class);

	// temporary mapping, void after bean initialisation
	private Map<String, Object> _mapping = new HashMap<String, Object>();

	protected RPCServiceExporterFactory factory;
	protected boolean disableResponseCaching = false;
	protected boolean throwUndeclaredExceptionToServletContainer = false;
	protected boolean scanParentApplicationContext = false;
	protected ServletConfig servletConfig;

	/**
	 * Scans the application context and its parents for service beans that
	 * implement the {@link GWTRequestMapping}
	 * 
	 * @param appContext
	 *            Application context
	 */
	private void scanForAnnotatedBeans(final ApplicationContext appContext) {
		if (appContext == null) {
			return;
		}
		for (String beanName : appContext
				.getBeanNamesForType(RemoteService.class)) {
			Object service = appContext.getBean(beanName);
			if (service == null)
				continue;
			Class<?> beanClass = null;
			// handling spring proxy services...
			if(AopUtils.isAopProxy(service)) {
				Advised advised = (Advised) service;
				try {
					Object target = advised.getTargetSource().getTarget();
					beanClass = target.getClass();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				beanClass = service.getClass();
			}
			final GWTRequestMapping requestMapping = ReflectionUtils
					.findAnnotation(beanClass, GWTRequestMapping.class);
			if (requestMapping == null) {
				continue;
			}

			// Create serviceExporter to bind to
			String mapping = requestMapping.value();
			if (getMappings().containsKey(mapping))
				logger.warn("Bean '" + mapping
						+ "' already in mapping, skipping.");
			else
				getMappings().put(mapping, service);
		}
		if (scanParentApplicationContext)
			scanForAnnotatedBeans(appContext.getParent());
		logger.debug("gwt mappings = " + getMappings());
	}

	/**
	 * Recursively scan the parent application contexts for annotated beans to
	 * publish. Beans from applications contexts that are lower in the hierarchy
	 * overwrite beans found in parent application contexts.
	 * 
	 * @param scanParentApplicationContext
	 *            Defaults to <code>false</code>
	 */
	public void setScanParentApplicationContext(
			boolean scanParentApplicationContext) {
		this.scanParentApplicationContext = scanParentApplicationContext;
	}

	private RPCServiceExporter initServiceInstance(RPCServiceExporter exporter,
			Object service, Class<RemoteService>[] serviceInterfaces) {
		try {
			exporter.setResponseCachingDisabled(disableResponseCaching);
			exporter.setServletContext(getServletContext());
			exporter.setServletConfig(servletConfig);
			exporter.setService(service);
			exporter.setServiceInterfaces(serviceInterfaces);
			exporter
					.setThrowUndeclaredExceptionToServletContainer(throwUndeclaredExceptionToServletContainer);
			exporter.afterPropertiesSet();
			return exporter;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, Object> getMappings() {
		return _mapping;
	}

	/**
	 * Set a mapping between URLs and services
	 * 
	 * @param mapping
	 */
	public void setMappings(Map<String, Object> mapping) {
		this._mapping = mapping;
	}

	/**
	 * Invoked automatically by Spring after initialisation.
	 */
	public void afterPropertiesSet() throws Exception {
		if (factory == null)
			factory = new DefaultRPCServiceExporterFactory();
		scanForAnnotatedBeans(getApplicationContext());
		for (Map.Entry<String, Object> entry : _mapping.entrySet()) {
//			log.debug("service = " + entry.getValue().getClass().getName());
			RPCServiceExporter exporter = factory.create();
			Class<RemoteService>[] interfaces = ReflectionUtils.getExposedInterfaces(entry
					.getValue().getClass());
//			log.debug("interfaces = ");
//			for (Class<RemoteService> class1 : interfaces) {
//				log.debug("class = " + class1.getName());
//			}
			registerHandler(entry.getKey(), initServiceInstance(exporter, entry
					.getValue(), interfaces));
		}
		this._mapping = null;
	}

	/**
	 * Optionally, a {@link RPCServiceExporterFactory} can be injected if a
	 * different implementation or setup is required. Note that after
	 * initialization, the following sequence of invocations will be performed
	 * on the {@code serviceExporter} :<br>
	 * <br>
	 * <code>
	 * 		exporter.setServletContext();<br>
	 * 		exporter.setService();<br>
	 * 		exporter.setServiceInterfaces();<br>
	 * 		exporter.afterPropertiesSet();<br>
	 *</code>
	 * 
	 * @param factory
	 */
	public void setServiceExporterFactory(RPCServiceExporterFactory factory) {
		this.factory = factory;
	}

	/**
	 * Can be used to explicitly disable caching of RPC responses in the client
	 * by modifying the HTTP headers of the response.
	 * 
	 * @param disableResponseCaching
	 */
	public void setDisableResponseCaching(boolean disableResponseCaching) {
		this.disableResponseCaching = disableResponseCaching;
	}

	/**
	 * @see {@link RPCServiceExporter#setThrowUndeclaredExceptionToServletContainer(boolean)}
	 * @param throwUndeclaredExceptionToServletContainer
	 */
	public void setThrowUndeclaredExceptionToServletContainer(
			boolean throwUndeclaredExceptionToServletContainer) {
		this.throwUndeclaredExceptionToServletContainer = throwUndeclaredExceptionToServletContainer;
	}

	/**
	 * Setter for servlet configuration
	 */
	public void setServletConfig(ServletConfig servletConfig) {
		this.servletConfig = servletConfig;
	}

}
