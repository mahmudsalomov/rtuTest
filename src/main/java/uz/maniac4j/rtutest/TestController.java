package uz.maniac4j.rtutest;

import net.solarnetwork.io.modbus.ModbusBlockType;
import net.solarnetwork.io.modbus.ModbusClient;
import net.solarnetwork.io.modbus.netty.msg.RegistersModbusMessage;
import net.solarnetwork.io.modbus.rtu.jsc.JscSerialPortProvider;
import net.solarnetwork.io.modbus.rtu.netty.NettyRtuModbusClientConfig;
import net.solarnetwork.io.modbus.rtu.netty.RtuNettyModbusClient;
import net.solarnetwork.io.modbus.serial.BasicSerialParameters;
import net.solarnetwork.io.modbus.serial.SerialParity;
import net.solarnetwork.io.modbus.serial.SerialStopBits;
import org.springframework.beans.factory.annotation.Value;
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
                    ModbusBlockType.Holding, 1, 1, 2);
            RegistersModbusMessage res = client.send(req).unwrap(RegistersModbusMessage.class);

            System.out.println(res);
            System.out.println(res.toString());
            short[] data = res.dataDecode();
            System.out.println("nimadir");
            System.out.println(data);
            for ( int i = 0, len = data.length; i < len; i++ ) {
                System.out.printf("%d: 0x%04X\n", len + i, data[i]);
            }
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
