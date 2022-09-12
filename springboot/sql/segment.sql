SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for segmentation
-- ----------------------------
DROP TABLE IF EXISTS `segment`;
CREATE TABLE `segment`
(
    `id`   int                                                          NOT NULL AUTO_INCREMENT,
    `word` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
