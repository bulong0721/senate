package com.jlt.framework.service;

/**
 * Indique que le service doit être rendu transactionnel via un aspect.
 * 
 * Cela permet de simplifier la configuration Spring de la partie transactionnelle car
 * il suffit alors de déclarer le pointcut de l'aspect sur
 * this(com.salesmanager.com.jlt.senate.core.business.generic.service.ITransactionalAspectAwareService)
 */
public interface TransactionalAspectAware {

}
