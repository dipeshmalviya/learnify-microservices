-- Create schemas for microservices
CREATE DATABASE IF NOT EXISTS `newusermicroservicedb` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS `newcategorymicroservicedb` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Grant permissions
GRANT ALL PRIVILEGES ON `newusermicroservicedb`.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON `newcategorymicroservicedb`.* TO 'root'@'%';
FLUSH PRIVILEGES;
