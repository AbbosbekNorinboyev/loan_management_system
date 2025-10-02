package uz.brb.loan_management_system.cron;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

@Component
public class LogCleanup {
    private static final Logger logger = LoggerFactory.getLogger(LogCleanup.class);
    private static final String LOG_FILE_PATH = "C:\\Abbos\\Spring Project\\Test Projects\\LoanManagementSystem\\logs\\LoanManagementSystem.log"; // Log fayl manzili

    @Scheduled(cron = "* * * */7 * *") // har 7 kunfda tozalaydi
    public void clearLogFile() {
        File file = new File(LOG_FILE_PATH);
        if (file.exists()) {
            try {
                Files.write(file.toPath(), new byte[0], StandardOpenOption.TRUNCATE_EXISTING);
                logger.info("✅ Log fayli tozalandi: " + LOG_FILE_PATH);
                System.out.println("clearLogFile ishlamoqda...");
            } catch (IOException e) {
                logger.error("❌ Log faylni tozalashda xatolik: " + e.getMessage());
            }
        } else {
            logger.warn("⚠ Log fayl topilmadi: " + LOG_FILE_PATH);
        }
    }
}
