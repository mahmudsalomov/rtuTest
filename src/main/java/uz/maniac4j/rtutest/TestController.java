package uz.maniac4j.rtutest;

import net.solarnetwork.io.modbus.ModbusBlockType;
import net.solarnetwork.io.modbus.ModbusClient;
import net.solarnetwork.io.modbus.netty.msg.RegistersModbusMessage;
import net.solarnetwork.io.modbus.rtu.jsc.JscSerialPortProvider;
import net.solarnetwork.io.modbus.rtu.netty.NettyRtuModbusClientConfig;
import net.solarnetwork.io.modbus.rtu.netty.RtuNettyModbusClient;
import net.solarnetwork.io.modbus.serial.BasicSerialParameters;
import net.solarnetwork.io.modbus.serial.SerialStopBits;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;



@RestController
@RequestMapping("/api")
public class TestController {
//
//    @Value("${rtu.port}")
//    private String port;

    @GetMapping("/test/{port}")
    public String test(@PathVariable String port){
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
                    ModbusBlockType.Holding, 1, 1, 12);
            RegistersModbusMessage res = client.send(req).unwrap(RegistersModbusMessage.class);

            System.out.println(res);
            System.out.println(res.toString());
            short[] data = res.dataDecode();
            System.out.println("nimadir");
            System.out.println(data);
            for ( int i = 0, len = data.length; i < len; i++ ) {
                System.out.printf("%d: 0x%04X\n", len + i, data[i]);
            }

            int[] a1=new int[]{data[0],data[1]};
            int[] a2=new int[]{data[2],data[3]};
            int[] a3=new int[]{data[4],data[5]};
            int[] a4=new int[]{data[6],data[7]};
            int[] a5=new int[]{data[8],data[9]};
            int[] a6=new int[]{data[10],data[11]};

            System.out.println("************************************************************************************");
            System.out.println(uz.maniac4j.storm.modbus.client.ModbusClient.ConvertRegistersToFloat(a1, uz.maniac4j.storm.modbus.client.ModbusClient.RegisterOrder.HighLow));
            System.out.println(uz.maniac4j.storm.modbus.client.ModbusClient.ConvertRegistersToFloat(a2, uz.maniac4j.storm.modbus.client.ModbusClient.RegisterOrder.HighLow));
            System.out.println(uz.maniac4j.storm.modbus.client.ModbusClient.ConvertRegistersToFloat(a3, uz.maniac4j.storm.modbus.client.ModbusClient.RegisterOrder.HighLow));
            System.out.println(uz.maniac4j.storm.modbus.client.ModbusClient.ConvertRegistersToFloat(a4, uz.maniac4j.storm.modbus.client.ModbusClient.RegisterOrder.HighLow));
            System.out.println(uz.maniac4j.storm.modbus.client.ModbusClient.ConvertRegistersToFloat(a5, uz.maniac4j.storm.modbus.client.ModbusClient.RegisterOrder.HighLow));
            System.out.println(uz.maniac4j.storm.modbus.client.ModbusClient.ConvertRegistersToFloat(a6, uz.maniac4j.storm.modbus.client.ModbusClient.RegisterOrder.HighLow));
            System.out.println("************************************************************************************");


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
