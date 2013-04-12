package ru.ttk.gateway.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import ru.transtk.dc.dccore.portableEntities.PObject;
import ru.transtk.dc.orgStruct.portableEntities.PPerson;
import ru.transtk.dc.orgStruct.portableEntities.PSection;
import ru.ttk.gateway.data.model.GatewayEntity;
import ru.ttk.gateway.data.model.GatewayPerson;
import ru.ttk.gateway.service.IGatewayEntity;

import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 */
public class GatewayEntityUtils {

    private final static Logger LOG = Logger.getLogger(GatewayEntityUtils.class);

    public static final String DATA_SOURCE_ORGANISATION_STRUCTURE = "ORGSTRUCT.DB";

    public static final String PPERSON_FULL_CLASS_NAME = PPerson.class.getCanonicalName();
    public static final String PSECTION_FULL_CLASS_NAME = PSection.class.getCanonicalName();

//    public GatewayPerson convertToGatewayPerson(IGatewayEntity gatewayEntity){
//        if(gatewayEntity != null){
//            GatewayPerson gatewayPerson = new GatewayPerson();
//            gatewayPerson.setId(gatewayEntity.getId());
//            gatewayPerson.setDisplayName(gatewayEntity.getDisplayName());
//            gatewayPerson.setPortableFullClassName(gatewayEntity.getPortableFullClassName());
//
//            return gatewayPerson;
//        }
//        return null;
//    }

    /**
     *
     * @param id
     * @param portableFullClassName
     * @param dataSourceName
     * @return
     */
    public URI createURI(String id, String portableFullClassName, String dataSourceName){
        if(!StringUtils.isEmpty(id) && !StringUtils.isEmpty(portableFullClassName) && !StringUtils.isEmpty(dataSourceName)){
            try{
                return new URI("entity", dataSourceName, "/" + portableFullClassName + "/" + id, null);
            }catch(Exception ex){
                LOG.error("Could not parse or get an external Entity:",ex);
            }
        }
        return null;
    }

    /**
     * GateWay Entity - should contains annotation @PortableClass
     *
     * @param gatewayEntity
     * @return
     */
    public URI createURI(IGatewayEntity gatewayEntity){

        URI reference = null;
        LOG.debug("gatewayEntity: " + gatewayEntity);
        try {

            LOG.info("gatewayEntity: " + gatewayEntity + " class:" + gatewayEntity.getClass().getCanonicalName());
            if(gatewayEntity != null && gatewayEntity.getId() != null && gatewayEntity.getPortableFullClassName() != null && gatewayEntity.getDataSourceName() != null){

                LOG.debug("Going to make UTI like this:" + new URI("entity", gatewayEntity.getDataSourceName(), "/" + gatewayEntity.getPortableFullClassName() + "/" + gatewayEntity.getId(), null));
                System.out.println("Going to make UTI like this:" + new URI("entity", gatewayEntity.getDataSourceName(), "/" + gatewayEntity.getPortableFullClassName() + "/" + gatewayEntity.getId(), null));

                reference = new URI("entity", gatewayEntity.getDataSourceName(), "/" + gatewayEntity.getPortableFullClassName() + "/" + gatewayEntity.getId(), null);
            }

        } catch (Exception ex) {
            LOG.error("Entity could not parse values annotation @PortableClass for Entity class:" + gatewayEntity, ex);
        }finally{
            return reference;
        }
    }

    /**
     *
     * @param pObject
     * @return
     */
    public IGatewayEntity createGatewayEntity(PObject pObject,String dataSourceName){

        if(pObject != null){
            IGatewayEntity gateEntity = new GatewayEntity();

            gateEntity.setId(pObject.getId());
            gateEntity.setDisplayName(pObject.getDisplayName());
            gateEntity.setPortableFullClassName(pObject.getEntityClass().getCanonicalName());
            gateEntity.setDataSourceName(dataSourceName);

            return gateEntity;
        }

        return null;
    }

    public GatewayPerson createGatewayPerson(PObject pObject,String dataSourceName){

        if(pObject != null){
            GatewayPerson gateEntity = new GatewayPerson();

            gateEntity.setId(pObject.getId());
            gateEntity.setDisplayName(pObject.getDisplayName());
            gateEntity.setPortableFullClassName(pObject.getEntityClass().getCanonicalName());
            gateEntity.setDataSourceName(dataSourceName);

            return gateEntity;
        }

        return null;
    }

    /**
     *
     * @param personId
     * @return
     */
    public URI getPersonURI(String personId){
        URI uri = null;
        try {
            uri = new URI("entity",DATA_SOURCE_ORGANISATION_STRUCTURE, "/" + PPerson.class.getCanonicalName() + "/" + personId, null );
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }finally{
            return uri;
        }
    }

}




