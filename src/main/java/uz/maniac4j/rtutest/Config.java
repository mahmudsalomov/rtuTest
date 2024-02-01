package uz.maniac4j.rtutest;

import net.solarnetwork.io.modbus.ModbusClient;
import net.solarnetwork.io.modbus.rtu.jsc.JscSerialPortProvider;
import net.solarnetwork.io.modbus.rtu.netty.NettyRtuModbusClientConfig;
import net.solarnetwork.io.modbus.rtu.netty.RtuNettyModbusClient;
import net.solarnetwork.io.modbus.serial.BasicSerialParameters;
import net.solarnetwork.io.modbus.serial.SerialStopBits;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uz.maniac4j.storm.modbus.server.ModbusServer;

@Configuration
public class Config {
    @Value("${rtu.port}")
    private String rtuPort;
    @Value("${tcp.port}")
    private String tcpPort;
//    @Bean
//    public ModbusClient modbusClient(){
//        BasicSerialParameters params = new BasicSerialParameters();
//        params.setBaudRate(19200);
//        params.setDataBits(8);
//        params.setReadTimeout(100);
//        params.setWaitTime(1000);
////        params.setParity(SerialParity.None);
//
//        params.setStopBits(SerialStopBits.One);
//        NettyRtuModbusClientConfig config = new NettyRtuModbusClientConfig(rtuPort, params);
//
//        return new RtuNettyModbusClient(config, new JscSerialPortProvider());
//    }

    @Bean
    public ModbusServer modbusServer(){
        ModbusServer server = new ModbusServer();
        server.setPort(502);
        server.setName("Simulator");
        return server;
    }
}
