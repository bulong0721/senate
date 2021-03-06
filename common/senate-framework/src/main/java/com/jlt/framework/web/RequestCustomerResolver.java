/*
 * #%L
 * BroadleafCommerce Common Libraries
 * %%
 * Copyright (C) 2009 - 2013 Broadleaf Commerce
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.jlt.framework.web;

import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * This resolver is responsible for returning the Customer object that is currently present on the request.
 * 
 * @author Andre Azzolini (apazzolini)
 */
public interface RequestCustomerResolver {

    Object getCustomer(HttpServletRequest request);

    Object getCustomer();

    Object getCustomer(WebRequest request);

    void setCustomer(Object customer);

    String getCustomerRequestAttributeName();

    void setCustomerRequestAttributeName(String customerRequestAttributeName);


}
