package testing.LoggerTest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        //ANSICode blue   = ANSICode.build().foreground(0x0000ff).done();
        ANSICode yellow = ANSICode.build().foreground(0xffff00).done();
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

        LoggerLabel timeLabel = new LoggerLabel(
            (level) -> true,
            (level) -> dateFormat.format(new Date()),
            cyan,
            null
        );

        LoggerLabel infoLabel = new LoggerLabel(
            (level) -> level.equals("info"),
            (level) -> "INFO",
            green,
            green
        );

        LoggerLabel warningLabel = new LoggerLabel(
            (level) -> level.equals("warning"),
            (level) -> "WARNING",
            yellow,
            yellow
        );

        LoggerLabel errorLabel = new LoggerLabel(
            (level) -> level.equals("error"),
            (level) -> "ERROR",
            red,
            red
        );

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
                e.printStackTrace();
            }
        });
    }    
}
