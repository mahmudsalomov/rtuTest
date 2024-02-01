package uz.maniac4j.rtutest;

import jakarta.annotation.PostConstruct;
import net.solarnetwork.io.modbus.ModbusBlockType;
import net.solarnetwork.io.modbus.ModbusClient;
import net.solarnetwork.io.modbus.netty.msg.RegistersModbusMessage;
import net.solarnetwork.io.modbus.rtu.jsc.JscSerialPortProvider;
import net.solarnetwork.io.modbus.rtu.netty.NettyRtuModbusClientConfig;
import net.solarnetwork.io.modbus.rtu.netty.RtuNettyModbusClient;
import net.solarnetwork.io.modbus.serial.BasicSerialParameters;
import net.solarnetwork.io.modbus.serial.SerialStopBits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import uz.maniac4j.storm.modbus.server.ModbusServer;

import java.util.Arrays;

@Service
@EnableScheduling
public class Forwarder {
    @Value("${rtu.port}")
    private String rtuPort;
    @Value("${tcp.port}")
    private String tcpPort;
    @Value("${loop}")
    private Integer loop;

    private ModbusClient modbusClient;
    @Autowired
    private ModbusServer modbusServer;

    @PostConstruct
    private void init(){
        BasicSerialParameters params = new BasicSerialParameters();
        params.setBaudRate(19200);
        params.setDataBits(8);
        params.setReadTimeout(100);
        params.setWaitTime(1000);
//        params.setParity(SerialParity.None);

        params.setStopBits(SerialStopBits.One);
        NettyRtuModbusClientConfig config = new NettyRtuModbusClientConfig(rtuPort, params);
        modbusClient=new RtuNettyModbusClient(config, new JscSerialPortProvider());
    }

    @Scheduled(fixedDelay = 10000)
    public void forward() {
        if (loop==1){
            try {
                if (!modbusClient.isConnected()) {
//                    BasicSerialParameters params = new BasicSerialParameters();
//                    params.setBaudRate(19200);
//                    params.setDataBits(8);
//                    params.setReadTimeout(100);
//                    params.setWaitTime(1000);
////        params.setParity(SerialParity.None);
//
//                    params.setStopBits(SerialStopBits.One);
//                    NettyRtuModbusClientConfig config = new NettyRtuModbusClientConfig(rtuPort, params);
//                    modbusClient=new RtuNettyModbusClient(config, new JscSerialPortProvider());
                    modbusClient.start().get();
                }
                if (!modbusServer.isAlive()) {
                    modbusServer.start();
                }

                if (modbusClient.isConnected()){
                    RegistersModbusMessage req = RegistersModbusMessage.readRegistersRequest(
                            ModbusBlockType.Holding, 1, 803, 12);
                    RegistersModbusMessage res = modbusClient.send(req).unwrap(RegistersModbusMessage.class);
                    short[] data = res.dataDecode();
                    System.out.println(Arrays.toString(data));
                    for (int i = 0; i < data.length; i++) {
                        modbusServer.holdingRegisters[i] = data[i];
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }


}
