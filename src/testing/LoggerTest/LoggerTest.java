package testing.LoggerTest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import engine.utils.Lambda;
import engine.utils.color.ANSICode;
import engine.utils.logger.Logger;
import engine.utils.logger.LoggerInformation;
import engine.utils.logger.LoggerLabel;

public class LoggerTest 
{
    public static void main(String[] args) 
    {
        Logger logger = new Logger(System.out::print);

        ANSICode green  = ANSICode.build().foreground(0x0fff00).done();
        ANSICode red    = ANSICode.build().foreground(0xff0000).done();
        ANSICode cyan   = ANSICode.build().foreground(0x00ffff).done();
        ANSICode blue   = ANSICode.build().foreground(0x0000ff).done();
        ANSICode yellow = ANSICode.build().foreground(0xffff00).done();
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

        LoggerLabel timeLabel = LoggerLabel.build()
            .setLabelColorCode(cyan)
            .setLabel(level -> "[" + dateFormat.format(new Date()) + "]")
        .done();

        LoggerLabel infoLabel = LoggerLabel.build()
            .setCondition("info"::equalsIgnoreCase)
            .setLabelColorCode(green)
            .setLabel(level -> "[INFO]")
            .setMessageColorCode(green)
        .done();

        LoggerLabel warningLabel = LoggerLabel.build()
            .setCondition("warning"::equalsIgnoreCase)
            .setLabelColorCode(yellow)
            .setLabel(level -> "[WARNING]")
            .setMessageColorCode(yellow)
        .done();

        LoggerLabel errorLabel = LoggerLabel.build()
            .setCondition("error"::equalsIgnoreCase)
            .setLabelColorCode(red)
            .setLabel(level -> "[ERROR]")
            .setMessageColorCode(red)
        .done();

        logger.setLoggerInformations(
            LoggerInformation.build()
                .addLabel(timeLabel)
                .addLabel(infoLabel)
                .addLabel(warningLabel)
                .addLabel(errorLabel)
            .done()
        );
        
        logger.log("Hello World!");

        logger.logln("info", "Hello World!");
        logger.logln("info", "Hello World!");
        logger.logln("error", "Hello World!");

        logger.outputLog((log) -> 
        {
            try {
                FileWriter writer = new FileWriter(new File("log.txt"), true);
                writer.write(log.toString(false));
                writer.close();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
    }    
}
