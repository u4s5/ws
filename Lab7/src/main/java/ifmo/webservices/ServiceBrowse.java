package ifmo.webservices;

import org.apache.juddi.api_v3.AccessPointType;
import org.apache.juddi.v3.client.UDDIConstants;
import org.apache.juddi.v3.client.config.UDDIClient;
import org.apache.juddi.v3.client.transport.Transport;
import org.uddi.api_v3.*;
import org.uddi.v3_service.UDDIInquiryPortType;
import org.uddi.v3_service.UDDISecurityPortType;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


public class ServiceBrowse {

    private static UDDISecurityPortType security = null;
    private static UDDIInquiryPortType inquiry = null;

    private String token;

    public ServiceBrowse(String username, String password) {
        try {
            initialize();
            this.token = getAuthKey(username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ServiceBrowse(String token) {
        try {
            initialize();
            this.token = token;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initialize() throws Exception {
        UDDIClient client = new UDDIClient("META-INF/uddi-browse.xml");
        Transport transport = client.getTransport("default");

        security = transport.getUDDISecurityService();
        inquiry = transport.getUDDIInquiryService();
    }


    private String catBagToString(CategoryBag categoryBag) {
        StringBuilder sb = new StringBuilder();
        if (categoryBag == null) {
            return "no data";
        }
        for (int i = 0; i < categoryBag.getKeyedReference().size(); i++) {
            sb.append(keyedReferenceToString(categoryBag.getKeyedReference().get(i)));
        }
        for (int i = 0; i < categoryBag.getKeyedReferenceGroup().size(); i++) {
            sb.append("Key Ref Grp: TModelKey=");
            for (int k = 0; k < categoryBag.getKeyedReferenceGroup().get(i).getKeyedReference().size();
                 k++) {
                sb.append(keyedReferenceToString(categoryBag.getKeyedReferenceGroup().get(i).getKeyedReference().get(k)));
            }
        }
        return sb.toString();
    }

    private String keyedReferenceToString(KeyedReference item) {
        StringBuilder sb = new StringBuilder();
        sb.append("Key Ref: Name=").
                append(item.getKeyName()).
                append(" Value=").
                append(item.getKeyValue()).
                append(" tModel=").
                append(item.getTModelKey()).
                append(System.getProperty("line.separator"));
        return sb.toString();
    }


    public void printBindingTemplates(BindingTemplates bindingTemplates) {
        if (bindingTemplates == null) {
            return;
        }
        for (int i = 0; i < bindingTemplates.getBindingTemplate().size(); i++) {
            System.out.println("Binding Key: " +
                    bindingTemplates.getBindingTemplate().get(i).getBindingKey());
            if (bindingTemplates.getBindingTemplate().get(i).getAccessPoint() != null) {
                String accessPoint = bindingTemplates.getBindingTemplate().get(i).getAccessPoint().getValue();
                System.out.println("Access Point: " +
                       accessPoint + " type " +
                        bindingTemplates.getBindingTemplate().get(i).getAccessPoint().getUseType());
                if (bindingTemplates.getBindingTemplate().get(i).getAccessPoint().getUseType() != null) {
                    if
                    (bindingTemplates.getBindingTemplate().get(i).getAccessPoint().getUseType().equalsIgnoreCase(AccessPointType.
                            END_POINT.toString())) {
                        System.out.println("Use this access point value as an invocation endpoint.");
                    }
                    if
                    (bindingTemplates.getBindingTemplate().get(i).getAccessPoint().getUseType().equalsIgnoreCase(AccessPointType.
                            BINDING_TEMPLATE.toString())) {
                        System.out.println("Use this access point value as a reference to another binding template.");
                    }
                    if
                    (bindingTemplates.getBindingTemplate().get(i).getAccessPoint().getUseType().equalsIgnoreCase(AccessPointType.
                            WSDL_DEPLOYMENT.toString())) {
                        System.out.println("Use this access point value as a URL to a WSDL document, " +
                                "which presumably will have a real access point defined.");
                    }
                    if
                    (bindingTemplates.getBindingTemplate().get(i).getAccessPoint().getUseType().equalsIgnoreCase(AccessPointType.
                            HOSTING_REDIRECTOR.toString())) {
                        System.out.println("Use this access point value as an Inquiry URL of another " +
                                "UDDI registry, look up the same binding template there (usage varies).");
                    }
                }
                try {
                    URL url = new URL(accessPoint);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    int responseCode = con.getResponseCode();
                    if (responseCode == 200) {
                        return;
                    }
                } catch (IOException e) {

                }
                finally {
                    System.out.println("Service is not accessible via access point: " + accessPoint);
                }
            }
        }
    }

    private String getAuthKey(String username, String password) {
        try {
            GetAuthToken getAuthTokenRoot = new GetAuthToken();
            getAuthTokenRoot.setUserID(username);
            getAuthTokenRoot.setCred(password);

            AuthToken rootAuthToken = security.getAuthToken(getAuthTokenRoot);
            System.out.println(username + " AUTHTOKEN = (don't log auth tokens!");
            return rootAuthToken.getAuthInfo();
        } catch (Exception ex) {
            System.out.println("Could not authenticate with the provided credentials " + ex.getMessage());
        }
        return null;
    }

    private String listToString(List<Name> name) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < name.size(); i++) {
            sb.append(name.get(i).getValue()).append(" ");
        }
        return sb.toString();
    }

    private String listToDescString(List<Description> name) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < name.size(); i++) {
            sb.append(name.get(i).getValue()).append(" ");
        }
        return sb.toString();
    }

    public void printServiceDetail(BusinessService get) {
        if (get == null) {
            return;
        }

        System.out.println("Service Name " + listToString(get.getName()));
        System.out.println("Service Key " + (get.getServiceKey()));
        System.out.println("Owning Business Key: " +
                get.getBusinessKey());
        System.out.println("Desc " + listToDescString(get.getDescription()));
        System.out.println("Cat bag " + catBagToString(get.getCategoryBag()));
        if (!get.getSignature().isEmpty()) {
            System.out.println("Item is digitally signed");
        } else {
            System.out.println("Item is not digitally signed");
        }
        printBindingTemplates(get.getBindingTemplates());
    }

    public void printFoundServices(String serviceName) throws Exception {
        FindService fs = new FindService();
        fs.setAuthInfo(token);
        fs.getName().add(new Name());
        fs.getName().get(0).setValue(serviceName);
        fs.setFindQualifiers(new FindQualifiers());
        fs.getFindQualifiers().getFindQualifier().add(UDDIConstants.CASE_INSENSITIVE_MATCH);

        ServiceList findService = inquiry.findService(fs);
        System.out.println("Found services: " + findService.getServiceInfos().getServiceInfo().size());

        for (int k = 0; k < findService.getServiceInfos().getServiceInfo().size(); k++) {
            GetServiceDetail gsd = new GetServiceDetail();
            for (int i = 0; i < findService.getServiceInfos().getServiceInfo().size(); i++) {
                gsd.getServiceKey().add(findService.getServiceInfos().getServiceInfo().get(i).getServiceKey());
            }

            ServiceDetail serviceDetail = inquiry.getServiceDetail(gsd);
            printServiceDetail(serviceDetail.getBusinessService().get(k));
        }
    }

    public BusinessDetail findBusinesses(String businessName) throws Exception {
        FindBusiness fs = new FindBusiness();
        fs.setAuthInfo(token);
        fs.getName().add(new Name());
        fs.getName().get(0).setValue(businessName);
        fs.setFindQualifiers(new FindQualifiers());
        fs.getFindQualifiers().getFindQualifier().add(UDDIConstants.CASE_INSENSITIVE_MATCH);

        BusinessList findBusiness = inquiry.findBusiness(fs);
        BusinessInfos businessInfos = findBusiness.getBusinessInfos();

        if (businessInfos == null) {
            return null;
        }
        return getBusinessDetail(findBusiness.getBusinessInfos());
    }

    private BusinessDetail getBusinessDetail(BusinessInfos businessInfos) throws Exception {
        GetBusinessDetail gbd = new GetBusinessDetail();
        gbd.setAuthInfo(token);
        for (int i = 0; i < businessInfos.getBusinessInfo().size(); i++) {
            gbd.getBusinessKey().add(businessInfos.getBusinessInfo().get(i).getBusinessKey());
        }
        return inquiry.getBusinessDetail(gbd);
    }
}