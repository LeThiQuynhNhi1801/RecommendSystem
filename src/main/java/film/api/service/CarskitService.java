package film.api.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class CarskitService {

    public void runCarskit(String jarPath, String configPath) {
        try {
            // Kiểm tra file JAR
            File jarFile = new File(jarPath);
            if (!jarFile.exists() || !jarFile.isFile()) {
                System.err.println("JAR file not found at: " + jarFile.getAbsolutePath());
                throw new IllegalArgumentException("CARSKit JAR file not found at: " + jarPath);
            }

            // Kiểm tra file cấu hình
            File configFile = new File(configPath);
            if (!configFile.exists() || !configFile.isFile()) {
                System.err.println("Config file not found at: " + configFile.getAbsolutePath());
                throw new IllegalArgumentException("Configuration file not found at: " + configPath);
            }

            // Log thông tin đường dẫn
            System.out.println("Running CARSKit with:");
            System.out.println("JAR Path: " + jarFile.getAbsolutePath());
            System.out.println("Config Path: " + configFile.getAbsolutePath());

            // Tạo lệnh chạy CARSKit
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "java", "-jar", jarFile.getAbsolutePath(), "-c", configFile.getAbsolutePath()
            );

            // Đặt thư mục làm việc
            processBuilder.directory(new File(System.getProperty("user.dir")));
            System.out.println("Working Directory: " + processBuilder.directory().getAbsolutePath());

            // Chạy lệnh
            Process process = processBuilder.start();

            // Đọc kết quả từ stdout
            new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(process.getInputStream()))) {
                    String line;
                    System.out.println("=== CARSKit Output (stdout) ===");
                    while ((line = reader.readLine()) != null) {
                        System.out.println("[STDOUT]: " + line);
                    }
                } catch (IOException e) {
                    System.err.println("Error reading stdout: " + e.getMessage());
                }
            }).start();

            // Đọc kết quả từ stderr
            new Thread(() -> {
                try (BufferedReader errorReader = new BufferedReader(
                        new InputStreamReader(process.getErrorStream()))) {
                    String line;
                    System.out.println("=== CARSKit Error Output (stderr) ===");
                    while ((line = errorReader.readLine()) != null) {
                        System.err.println("[STDERR]: " + line);

                    }
                } catch (IOException e) {
                    System.err.println("Error reading stderr: " + e.getMessage());
                }
            }).start();

            // Kiểm tra mã thoát
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("CARSKit executed successfully!");
            } else {
                System.err.println("CARSKit execution failed with exit code: " + exitCode);
            }

        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error running CARSKit", e);
        }
    }
}
