from pymodbus.server import StartTcpServer as StartServer
from pymodbus.client import ModbusSerialClient as ModbusClient
from pymodbus.datastore.remote import RemoteSlaveContext
from pymodbus.datastore import ModbusSlaveContext, ModbusServerContext
from serial import Serial


def run_serial_forwarder():
    client = ModbusClient(method="rtu", port="COM2", stopbits=1, bytesize=8, parity='N', baudrate=19200)

    store = RemoteSlaveContext(client, slave=1)
    store3 = RemoteSlaveContext(client, slave=3)
    context = ModbusServerContext(slaves={1: store, 3: store3}, single=False)

    StartServer(context=context, address=("0.0.0.0", 504))


if __name__ == "__main__":
    run_serial_forwarder()
