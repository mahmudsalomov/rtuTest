package uz.maniac4j.rtutest;

import net.solarnetwork.io.modbus.ModbusBlockType;
import net.solarnetwork.io.modbus.ModbusClient;
import net.solarnetwork.io.modbus.netty.msg.RegistersModbusMessage;
import net.solarnetwork.io.modbus.rtu.jsc.JscSerialPortProvider;
import net.solarnetwork.io.modbus.rtu.netty.NettyRtuModbusClientConfig;
import net.solarnetwork.io.modbus.rtu.netty.RtuNettyModbusClient;
import net.solarnetwork.io.modbus.serial.BasicSerialParameters;
import net.solarnetwork.io.modbus.serial.SerialStopBits;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;



@RestController
@RequestMapping("/api")
public class TestController {
//
//    @Value("${unit}")
//    private Integer unit;

    @GetMapping("/test/{port}/{unit}/{start}/{count}")
    public String test(@PathVariable String port, @PathVariable Integer unit, @PathVariable Integer start, @PathVariable Integer count){
        // configure the serial port settings
        BasicSerialParameters params = new BasicSerialParameters();
        params.setBaudRate(19200);
        params.setDataBits(8);
//        params.setParity(SerialParity.None);

        params.setStopBits(SerialStopBits.One);
        NettyRtuModbusClientConfig config = new NettyRtuModbusClientConfig(port, params);

// create the client, passing in the JSC SerialPortProvider
        ModbusClient client = new RtuNettyModbusClient(config, new JscSerialPortProvider());

        try {
            client.start().get();

            RegistersModbusMessage req = RegistersModbusMessage.readRegistersRequest(
//                    ModbusBlockType.Holding, unit, 803, 12);
                    ModbusBlockType.Holding, unit, start, count);
            RegistersModbusMessage res = client.send(req).unwrap(RegistersModbusMessage.class);

            System.out.println(res);
            System.out.println(res.toString());
            short[] data = res.dataDecode();
            System.out.println("nimadir");
            System.out.println(Arrays.toString(data));
            for ( int i = 0, len = data.length; i < len; i++ ) {
                System.out.printf("%d: 0x%04X\n", len + i, data[i]);
            }

            int[] a1=new int[]{data[1],data[2]};
            int[] a2=new int[]{data[3],data[4]};
            int[] a3=new int[]{data[5],data[6]};
            int[] a4=new int[]{data[7],data[8]};
            int[] a5=new int[]{data[9],data[10]};
            int[] a6=new int[]{data[11],data[12]};

            System.out.println("SLAVE = "+unit);
            System.out.println("************************************************************************************");
            System.out.println(uz.maniac4j.storm.modbus.client.ModbusClient.ConvertRegistersToFloat(a1, uz.maniac4j.storm.modbus.client.ModbusClient.RegisterOrder.LowHigh));
            System.out.println(uz.maniac4j.storm.modbus.client.ModbusClient.ConvertRegistersToFloat(a2, uz.maniac4j.storm.modbus.client.ModbusClient.RegisterOrder.LowHigh));
            System.out.println(uz.maniac4j.storm.modbus.client.ModbusClient.ConvertRegistersToFloat(a3, uz.maniac4j.storm.modbus.client.ModbusClient.RegisterOrder.LowHigh));
            System.out.println(uz.maniac4j.storm.modbus.client.ModbusClient.ConvertRegistersToFloat(a4, uz.maniac4j.storm.modbus.client.ModbusClient.RegisterOrder.LowHigh));
            System.out.println(uz.maniac4j.storm.modbus.client.ModbusClient.ConvertRegistersToFloat(a5, uz.maniac4j.storm.modbus.client.ModbusClient.RegisterOrder.LowHigh));
            System.out.println(uz.maniac4j.storm.modbus.client.ModbusClient.ConvertRegistersToFloat(a6, uz.maniac4j.storm.modbus.client.ModbusClient.RegisterOrder.LowHigh));
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println(uz.maniac4j.storm.modbus.client.ModbusClient.ConvertRegistersToFloat(a1, uz.maniac4j.storm.modbus.client.ModbusClient.RegisterOrder.HighLow));
            System.out.println(uz.maniac4j.storm.modbus.client.ModbusClient.ConvertRegistersToFloat(a2, uz.maniac4j.storm.modbus.client.ModbusClient.RegisterOrder.HighLow));
            System.out.println(uz.maniac4j.storm.modbus.client.ModbusClient.ConvertRegistersToFloat(a3, uz.maniac4j.storm.modbus.client.ModbusClient.RegisterOrder.HighLow));
            System.out.println(uz.maniac4j.storm.modbus.client.ModbusClient.ConvertRegistersToFloat(a4, uz.maniac4j.storm.modbus.client.ModbusClient.RegisterOrder.HighLow));
            System.out.println(uz.maniac4j.storm.modbus.client.ModbusClient.ConvertRegistersToFloat(a5, uz.maniac4j.storm.modbus.client.ModbusClient.RegisterOrder.HighLow));
            System.out.println(uz.maniac4j.storm.modbus.client.ModbusClient.ConvertRegistersToFloat(a6, uz.maniac4j.storm.modbus.client.ModbusClient.RegisterOrder.HighLow));
            System.out.println("************************************************************************************");



//            RegistersModbusMessage req1 = RegistersModbusMessage.readRegistersRequest(
//                    ModbusBlockType.Holding, 3, 0, 11);
//            RegistersModbusMessage res1 = client.send(req1).unwrap(RegistersModbusMessage.class);
//
//            System.out.println(res1);
//            System.out.println(res1.toString());
//            short[] data1 = res1.dataDecode();
//            System.out.println("nimadir");
//            System.out.println(data1);
//            for ( int i = 0, len = data1.length; i < len; i++ ) {
//                System.out.printf("%d: 0x%04X\n", len + i, data1[i]);
//            }
//
//            int[] a11=new int[]{data1[0],data1[1]};
//            int[] a21=new int[]{data1[2],data1[3]};
//            int[] a31=new int[]{data1[4],data1[5]};
//            int[] a41=new int[]{data1[6],data1[7]};
//            int[] a51=new int[]{data1[8],data1[9]};
//            int[] a61=new int[]{data1[10],data1[11]};
//
//            System.out.println("SLAVE = 3");
//            System.out.println("************************************************************************************");
//            System.out.println(uz.maniac4j.storm.modbus.client.ModbusClient.ConvertRegistersToFloat(a11, uz.maniac4j.storm.modbus.client.ModbusClient.RegisterOrder.LowHigh));
//            System.out.println(uz.maniac4j.storm.modbus.client.ModbusClient.ConvertRegistersToFloat(a21, uz.maniac4j.storm.modbus.client.ModbusClient.RegisterOrder.LowHigh));
//            System.out.println(uz.maniac4j.storm.modbus.client.ModbusClient.ConvertRegistersToFloat(a31, uz.maniac4j.storm.modbus.client.ModbusClient.RegisterOrder.LowHigh));
//            System.out.println(uz.maniac4j.storm.modbus.client.ModbusClient.ConvertRegistersToFloat(a41, uz.maniac4j.storm.modbus.client.ModbusClient.RegisterOrder.LowHigh));
//            System.out.println(uz.maniac4j.storm.modbus.client.ModbusClient.ConvertRegistersToFloat(a51, uz.maniac4j.storm.modbus.client.ModbusClient.RegisterOrder.LowHigh));
//            System.out.println(uz.maniac4j.storm.modbus.client.ModbusClient.ConvertRegistersToFloat(a61, uz.maniac4j.storm.modbus.client.ModbusClient.RegisterOrder.LowHigh));
//            System.out.println();
//            System.out.println();
//            System.out.println();
//            System.out.println(uz.maniac4j.storm.modbus.client.ModbusClient.ConvertRegistersToFloat(a11, uz.maniac4j.storm.modbus.client.ModbusClient.RegisterOrder.HighLow));
//            System.out.println(uz.maniac4j.storm.modbus.client.ModbusClient.ConvertRegistersToFloat(a21, uz.maniac4j.storm.modbus.client.ModbusClient.RegisterOrder.HighLow));
//            System.out.println(uz.maniac4j.storm.modbus.client.ModbusClient.ConvertRegistersToFloat(a31, uz.maniac4j.storm.modbus.client.ModbusClient.RegisterOrder.HighLow));
//            System.out.println(uz.maniac4j.storm.modbus.client.ModbusClient.ConvertRegistersToFloat(a41, uz.maniac4j.storm.modbus.client.ModbusClient.RegisterOrder.HighLow));
//            System.out.println(uz.maniac4j.storm.modbus.client.ModbusClient.ConvertRegistersToFloat(a51, uz.maniac4j.storm.modbus.client.ModbusClient.RegisterOrder.HighLow));
//            System.out.println(uz.maniac4j.storm.modbus.client.ModbusClient.ConvertRegistersToFloat(a61, uz.maniac4j.storm.modbus.client.ModbusClient.RegisterOrder.HighLow));
//            System.out.println("************************************************************************************");

        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            client.stop();
        }
        return "Ok";
    }
}
