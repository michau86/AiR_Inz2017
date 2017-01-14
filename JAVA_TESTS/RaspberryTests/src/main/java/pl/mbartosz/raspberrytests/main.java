/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mbartosz.raspberrytests;

import com.pi4j.io.serial.Baud;
import com.pi4j.io.serial.DataBits;
import com.pi4j.io.serial.FlowControl;
import com.pi4j.io.serial.Parity;
import com.pi4j.io.serial.Serial;
import com.pi4j.io.serial.SerialConfig;
import com.pi4j.io.serial.SerialDataEvent;
import com.pi4j.io.serial.SerialDataEventListener;
import com.pi4j.io.serial.SerialFactory;
import com.pi4j.io.serial.SerialPort;
import com.pi4j.io.serial.StopBits;
import com.pi4j.util.CommandArgumentParser;
import com.pi4j.util.Console;
import java.io.IOException;

/**
 *
 * @author mateu
 */
public class main {

    public static void main(String[] args) throws InterruptedException, IOException {

        final Console console = new Console();
        console.println("program started");
        console.promptForExit();

        final Serial serial = SerialFactory.createInstance();

        serial.addListener(new SerialDataEventListener() {
            @Override
            public void dataReceived(SerialDataEvent event) {
                try {
                    console.println("[HEX DATA] " + event.getHexByteString());
                    console.println(event.getAsciiString());
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        });

        try {
            SerialConfig config = new SerialConfig();
            config.device(SerialPort.getDefaultPort())
                    .baud(Baud._38400)
                    .dataBits(DataBits._8)
                    .parity(Parity.NONE)
                    .stopBits(StopBits._1)
                    .flowControl(FlowControl.NONE);

            if (args.length > 0) {
                config = CommandArgumentParser.getSerialConfig(config, args);
            }

            console.box("Connectiong to: " + config.toString(),
                    " We are sending ASCII data on the serial port every 1 second.",
                    " Data received on serial port will be dispaled below.");

            serial.open(config);

            while (console.isRunning()) {

                try {
                    serial.write((byte) 13);
                    serial.write((byte) 10);

                    serial.write("Second Line");

                    serial.write('\r');
                    serial.write('\n');

                    serial.writeln("Third Line");
                } catch (IllegalStateException ise) {
                    ise.printStackTrace();
                }
                Thread.sleep(1000);
            }
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
