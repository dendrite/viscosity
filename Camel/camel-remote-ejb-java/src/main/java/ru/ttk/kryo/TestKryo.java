package ru.ttk.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.io.OutputChunked;
import com.esotericsoftware.kryo.serializers.CollectionSerializer;
import com.esotericsoftware.kryo.serializers.DefaultSerializers;
import com.esotericsoftware.kryo.serializers.FieldSerializer;
import ru.ttk.kryo.serializers.KryoValueDeserializer;
import ru.ttk.kryo.serializers.KryoValueSerializer;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 */
public class TestKryo {

    public static void main(String[] argv) throws IOException {

        System.out.println("test it");


        List<TClass1> list = new ArrayList<TClass1>();

        for(int i=0;i<100000;i++){
            TClass1 c1 = new TClass1(i,System.currentTimeMillis(),"NAME#" + i);
            list.add(c1);
        }


        TClass2 c2 = new TClass2(1,"CLASS 2", list);

        System.out.println("c2:" + c2.getId() + " " + c2.getName() + " " + c2.getList().get(3));


        Kryo kryo = new Kryo();
//        kryo.register(TClass1.class, new FieldSerializer(kryo, TClass1.class));
//        kryo.register(TClass2.class, new FieldSerializer(kryo, TClass2.class));
//        kryo.register(Long.class);
//        kryo.register(List.class);
//        kryo.register(ArrayList.class);
//        kryo.register(String.class);
        //kryo.register(TClass1.class);
        //kryo.register(TClass2.class);


        FieldSerializer someClassSerializer = new FieldSerializer(kryo, TClass2.class);

        CollectionSerializer listSerializer = new CollectionSerializer();
        listSerializer.setElementClass(TClass1.class,someClassSerializer);
        listSerializer.setElementClass(Long.class,new DefaultSerializers.LongSerializer());
        listSerializer.setElementClass(String.class,new DefaultSerializers.StringSerializer());

        listSerializer.setElementsCanBeNull(false);
        someClassSerializer.getField("list").setClass(ArrayList.class, listSerializer);
        kryo.register(TClass2.class, someClassSerializer);



        KryoValueSerializer kryoValueSerializer = new KryoValueSerializer(kryo);
        long tB = System.currentTimeMillis();
        byte[] c2_bytes = kryoValueSerializer.serialize(c2);
        System.out.println("time:" + (System.currentTimeMillis()-tB));
        System.out.println("c2_bytes:" + c2_bytes.length);

        KryoValueDeserializer kryoValueDeserializer = new KryoValueDeserializer(kryo);
        TClass1 c22 = (TClass1) kryoValueDeserializer.deserialize(c2_bytes);

        System.out.println("c22:" + c22.getId() + " " + c22.getName() );// + " " + c22.getList().get(3));


        FileOutputStream fosKryo = new FileOutputStream("d:/ttk/serialize.kryo");
        fosKryo.write(c2_bytes);
        fosKryo.flush();
        fosKryo.close();

        FileOutputStream fos = new FileOutputStream("d:/ttk/serialize.java_standart");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        tB = System.currentTimeMillis();
        oos.writeObject(c2);
        System.out.println("time:" + (System.currentTimeMillis()-tB));
        oos.flush();
        oos.close();


////        kryo.register(TClass1.class);
////        kryo.register(TClass2.class);
//
//
//        //ByteBuffer buffer = ByteBuffer.allocateDirect(1024*10);
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//
//        BufferedOutputStream outputStream = new BufferedOutputStream( new FileOutputStream( new File("d:/ttk/kryo.buffer")), 1024*10000);
//
//        Output output = new Output(outputStream, 1024*10000);
//
//        kryo.writeObject(output, c2);
//
////        output.getBuffer();
////
////        TClass2 c2_2 = null;
////        Input in = new Input(byteArrayOutputStream.toByteArray());
////
////        c2_2 = kryo.readObject(in,TClass2.class);
////        System.out.println("c2_2:"  + c2_2);
////        System.out.println("\n\n c2_2:" + c2_2.getId() + " " + c2_2.getName() + " " + c2_2.getList().get(3));
//
//
//        Output _kryoOut = new Output(2000, 2000000000);
//
//

    }

}
