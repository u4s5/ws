/*
 * Copyright 2001-2010 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package ifmo.webservices;

import org.apache.juddi.api_v3.AccessPointType;
import org.apache.juddi.v3.client.config.UDDIClient;
import org.apache.juddi.v3.client.transport.Transport;
import org.uddi.api_v3.*;
import org.uddi.v3_service.UDDIPublicationPortType;
import org.uddi.v3_service.UDDISecurityPortType;


public class ServiceRegister {

    private static UDDISecurityPortType security = null;
    private static UDDIPublicationPortType publish = null;

    private AuthToken token;

    public ServiceRegister(String username, String password) {
        try {
            UDDIClient uddiClient = new UDDIClient("META-INF/uddi-register.xml");
            Transport transport = uddiClient.getTransport("default");

            security = transport.getUDDISecurityService();
            publish = transport.getUDDIPublishService();

            this.token = GetAuthToken(username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private AuthToken GetAuthToken(String username, String password) {
        try {
            GetAuthToken getAuthTokenRoot = new GetAuthToken();
            getAuthTokenRoot.setUserID(username);
            getAuthTokenRoot.setCred(password);

            return security.getAuthToken(getAuthTokenRoot);
        } catch (Exception ex) {
            System.out.println("Could not authenticate with the provided credentials " + ex.getMessage());
        }
        return null;
    }

    private BusinessEntity createBusinessEntity(String businessName) {
        BusinessEntity businessEntity = new BusinessEntity();
        Name newBusName = new Name();
        newBusName.setValue(businessName);
        businessEntity.getName().add(newBusName);
        return businessEntity;
    }

    public void publish(String businessName, String serviceName, String endPoint) {
        try {
            BusinessEntity businessEntity;
            ServiceBrowse serviceBrowse = new ServiceBrowse(token.getAuthInfo());
            BusinessDetail businessDetail = serviceBrowse.findBusinesses(businessName);

            if (businessDetail == null || businessDetail.getBusinessEntity().size() == 0) {
                businessEntity = createBusinessEntity(businessName);
                System.out.println("Business with such name was not found, creating new");
            } else {
                businessEntity = serviceBrowse.findBusinesses(businessName).getBusinessEntity().get(0);
                System.out.println("Business with such name already exists and will be used for service");
            }

            SaveBusiness sb = new SaveBusiness();
            sb.getBusinessEntity().add(businessEntity);
            sb.setAuthInfo(this.token.getAuthInfo());
            BusinessDetail bd = publish.saveBusiness(sb);
            String busKey = bd.getBusinessEntity().get(0).getBusinessKey();
            System.out.println("Business key:  " + busKey);

            BusinessService service = new BusinessService();
            service.setBusinessKey(busKey);
            Name servName = new Name();
            servName.setValue(serviceName);
            service.getName().add(servName);

            BindingTemplate bindingTemplate = new BindingTemplate();
            AccessPoint accessPoint = new AccessPoint();
            accessPoint.setUseType(AccessPointType.WSDL_DEPLOYMENT.toString());
            accessPoint.setValue(endPoint);
            bindingTemplate.setAccessPoint(accessPoint);
            BindingTemplates bindingTemplates = new BindingTemplates();

            bindingTemplate = UDDIClient.addSOAPtModels(bindingTemplate);
            bindingTemplates.getBindingTemplate().add(bindingTemplate);

            service.setBindingTemplates(bindingTemplates);

            SaveService ss = new SaveService();
            ss.getBusinessService().add(service);
            ss.setAuthInfo(this.token.getAuthInfo());
            ServiceDetail sd = publish.saveService(ss);

            System.out.println("Service has been created");
            String servKey = sd.getBusinessService().get(0).getServiceKey();
            System.out.println("Service key:  " + servKey);

            security.discardAuthToken(new DiscardAuthToken(this.token.getAuthInfo()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
