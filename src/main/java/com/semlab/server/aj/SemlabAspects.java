package com.semlab.server.aj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.semlab.shared.services.SemlabService;


@Aspect
public class SemlabAspects {

	private final Logger log = LoggerFactory.getLogger(SemlabService.class);

//	/**
//	 * catches all exceptions thrown during database access
//	 */
//	@AfterThrowing(pointcut = "rs.co.neotech.server.aspectj.SemsegPointcuts.dataAccessOperation()", throwing = "ex")
//	public void doRecoveryActions(Exception ex) {
//		log.error("Exception caught by aspect: " + ex.getMessage());
//		ex.printStackTrace();
//	}
//
//	@Around("rs.co.neotech.server.aspectj.SemsegPointcuts.enrichInvocation()")
//	public Object doExpandProfiling(ProceedingJoinPoint pjp) throws Throwable {
//		if (pjp.getSignature().getName().equals("enrich")) {
////			log.debug(":: -------------------------------------------------------------------");
////			log.debug(":: Enrich service invoked");
//		}
//		Object retVal = pjp.proceed();
//		if (pjp.getSignature().getName().equals("enrich")) {
////			log.debug(":: Enrich service finished");
////			log.debug(":: -------------------------------------------------------------------");
//		}	
//		return retVal;
//	}
//
//	@Around("rs.co.neotech.server.aspectj.SemsegPointcuts.insertInvocation()")
//	public Object doInsertProfiling(ProceedingJoinPoint pjp) throws Throwable {
//		if (pjp.getSignature().getName().equals("parseProfil")) {
//			log.debug(":: -------------------------------------------------------------------");
//			log.debug(":: Insert service invoked");
//		}
//
//		Object retVal = pjp.proceed();
//
//		if (pjp.getSignature().getName().equals("parseProfil")) {
//			log.debug(":: Insert service finished");
//			log.debug(":: -------------------------------------------------------------------");
//		}
//		return retVal;
//	}
//	
//	@Around("rs.co.neotech.server.aspectj.SemsegPointcuts.updateInvocation()")
//	public Object doUpdateProfiling(ProceedingJoinPoint pjp) throws Throwable {
//		if (pjp.getSignature().getName().equals("update")) {
////			log.debug(":: -------------------------------------------------------------------");
////			log.debug(":: Update service invoked");
//		}
//
//		Object retVal = pjp.proceed();
//
//		if (pjp.getSignature().getName().equals("update")) {
////			log.debug(":: Update service finished");
////			log.debug(":: -------------------------------------------------------------------");
//		}
//		return retVal;
//	}
//	
//	@Around("rs.co.neotech.server.aspectj.SemsegPointcuts.insertInvocation()")
//	public Object doUpdateWeatherProfiling(ProceedingJoinPoint pjp) throws Throwable {
//		if (pjp.getSignature().getName().equals("updateWeather")) {
//			log.debug(":: -------------------------------------------------------------------");
//			log.debug(":: Weather service invoked");
//		}
//
//		Object retVal = pjp.proceed();
//
//		if (pjp.getSignature().getName().equals("updateWeather")) {
//			log.debug(":: Weather service finished");
//			log.debug(":: -------------------------------------------------------------------");
//		}
//		return retVal;
//	}
//
//	@Around("rs.co.neotech.server.aspectj.SemsegPointcuts.rpcInvocation()")
//	public Object doRpcCall(ProceedingJoinPoint pjp) throws Throwable {
//		String methodName = pjp.getSignature().getName();
//
//		log.debug(":: ######################################################################");
//		log.debug(":: RPC start -> " + methodName + " :: ");
//		Object[] args = pjp.getArgs();
//		StringBuilder sb = new StringBuilder();
//
//		for (int i = 0; i < args.length; i++) {
//			sb.append(args[i]);
//			if (args.length > i + 1)
//				sb.append(", ");
//		}
//		log.debug(":: RPC args -> (" + sb.toString() + ")");
//
//		Object retVal = pjp.proceed();
//
//		if (retVal == null)
//			retVal = new String("void");
//		log.debug(":: RPC return -> " + retVal + " :: ");
//		log.debug(":: RPC end -> " + methodName + " :: ");
//		log.debug(":: ######################################################################");
//		log.debug("");
//		return retVal;
//	}
//	
//	@AfterThrowing(pointcut = "rs.co.neotech.server.aspectj.SemsegPointcuts.rpcInvocation()", throwing = "ex")
//	public void doRpcException(Exception ex) {
//		log.error("Exception caught by aspect: " + ex.getMessage());
//		ex.printStackTrace();
//	}

}
