package film;

import film.api.helper.FileSystemHelper;
import film.api.service.CarskitService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
@Configuration
public class BootifulApplication {

	private static final Logger logger = LoggerFactory.getLogger(BootifulApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BootifulApplication.class, args);
	}

	/**
	 * Tạo các thư mục cần thiết khi khởi động ứng dụng.
	 */
	public static void setup() {
		try {
			// Tạo thư mục lưu trữ file tĩnh nếu chưa tồn tại
			Files.createDirectories(Paths.get(FileSystemHelper.STATIC_FILES_DIR));
			logger.info("Static file directory is created at: {}", FileSystemHelper.STATIC_FILES_DIR);
		} catch (IOException e) {
			logger.error("Failed to create static file directory", e);
			throw new RuntimeException("Error during setup", e);
		}
	}

	/**
	 * Chạy CARSKit khi ứng dụng khởi động.
	 */
	@Bean
	public CommandLineRunner run(CarskitService carskitService) {
		return args -> {
			String jarPath = "libs/CARSKit-v0.3.5.jar";
			String configPath = "src/main/resources/config.conf";

			logger.info("Starting CARSKit execution...");
			try {
				carskitService.runCarskit(jarPath, configPath); // Gọi service thực thi CARSKit
				logger.info("CARSKit execution completed successfully.");
			} catch (Exception e) {
				logger.error("An error occurred during CARSKit execution", e);
			}
		};
	}
}
