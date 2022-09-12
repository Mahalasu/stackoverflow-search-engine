SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for data
-- ----------------------------
DROP TABLE IF EXISTS `data`;
CREATE TABLE `data`
(
    `id`      int                                                           NOT NULL,
    `url`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         NULL,
    `caption` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
    `views`   varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL,
    `votes`   varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
