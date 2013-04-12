package ru.ttk.kryo.remoteejbkryo;

import com.esotericsoftware.kryo.Kryo;
import ru.ttk.building.data.model.building.*;
import ru.ttk.building.data.model.core.XReference;
import ru.ttk.building.data.model.core.dictionary.XDictionaryItem;
import ru.ttk.building.model.type.InspectionStatus;
import ru.ttk.building.model.type.InspectionType;
import ru.ttk.building.model.type.PaymentMethod;
import ru.ttk.building.model.type.UnitType;
import ru.ttk.gateway.data.model.GatewayPerson;
import ru.ttk.kryo.serializers.KryoValueDeserializer;
import ru.ttk.test.interfaces.ISimpleRemoteEJB;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.util.*;

/**
 *
 */
public class RemoteEJBKryoNativeJBossClient {

    public static void main(String[] argv) throws NamingException, IOException {

        System.out.println("Begin Kryo transfer via RemoteEJB");

        final Hashtable jndiProperties = new Hashtable();

        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        jndiProperties.put("java.naming.factory.initial","org.jboss.naming.remote.client.InitialContextFactory");
        jndiProperties.put(InitialContext.PROVIDER_URL, "remote://localhost:4447");
        jndiProperties.put("remote.connections", "default");
        jndiProperties.put("remote.connectionprovider.create.options.org.xnio.Options.SSL_ENABLED", "false");
        jndiProperties.put("remote.connection.default.connect.options.org.xnio.Options.SASL_POLICY_NOANONYMOUS", "false");


        final Context context = new InitialContext(jndiProperties);

        String path = "ejb:building/building-ejb-0.5-SNAPSHOT/SimpleRemoteEJB!ru.ttk.test.interfaces.ISimpleRemoteEJB";

        final ISimpleRemoteEJB simpleRemoteEJB = (ISimpleRemoteEJB)context.lookup(path);




        String buildingId = "db4f4e39-0815-4cb0-8641-fa668cdab739";

        long tB = System.currentTimeMillis();
        for(int i=0; i<1;i++){
            XBuilding xBuilding = simpleRemoteEJB.getPureBuildingList(buildingId);
            System.out.println("xBuilding:" + xBuilding);
            System.out.println(xBuilding.getAddress());
            System.out.println(xBuilding.getCompetitors());
        }
        System.out.println("DELTA = " + (System.currentTimeMillis()-tB));






        Kryo kryo = new Kryo();
        kryo.register(Long.class);
        kryo.register(List.class);
        kryo.register(ArrayList.class);
        kryo.register(String.class);


        kryo.register(XBuilding.class);

        kryo.register(XAddress.class);

        kryo.register(XAdvertisement.class);
        kryo.register(XCompany.class);

        kryo.register(XCompetitor.class);


        kryo.register(XEntrance.class);
        kryo.register(XInspection.class);
        kryo.register(XInspectionResult.class);

        kryo.register(XInspectionStatisticAggregator.class);
        kryo.register(XManagementCompany.class);
        kryo.register(XManagementCompanyContact.class);
        kryo.register(XPassport.class);
        kryo.register(XSubscriber.class);

        kryo.register(XUnit.class);


        kryo.register(GatewayPerson.class);

        kryo.register(GatewayPerson.class);
        kryo.register(Set.class);
        kryo.register(XReference.class);
        kryo.register(XDictionaryItem.class);
        kryo.register(Date.class);


        kryo.register(Date.class);

        kryo.register(InspectionStatus.class);
        kryo.register(InspectionType.class);
        kryo.register(PaymentMethod.class);
        kryo.register(UnitType.class);




        KryoValueDeserializer kryoValueDeserializer = new KryoValueDeserializer(kryo);


        tB = System.currentTimeMillis();
        for(int i=0; i<30;i++){
            byte[] BT = simpleRemoteEJB.getKryoBuildingList(buildingId);
            XBuilding xBuilding = (XBuilding)kryoValueDeserializer.deserialize(BT);
            System.out.println("xBuilding:" + xBuilding);
        }
        System.out.println("DELTA = " + (System.currentTimeMillis()-tB));


    }// --

}
