CREATE TABLE DETECTOR
(
  ID                      INTEGER primary key AUTO_INCREMENT,
  IP                      VARCHAR(15)  not null,
  ADDRESS                 VARCHAR(50)  not null,
  SERIAL_NUMBER           VARCHAR(30)  not null,
  START_DATE              DATE         not null,
  WARRANTY_PERIOD         INTEGER      not null,
  WORK_TIME               INTEGER      not null,
  ALL_WORK_TIME           INTEGER      not null,
  EMERGENCY_POWER_TIME    INTEGER      not null,
  DETECTED_COUNT          BIGINT       not null,
  POSITIVE_DETECTED_COUNT BIGINT       not null,
  FREQUENCY               DOUBLE       not null,
  DATE                    DATE         not null,
  ERRORS                  VARCHAR(255) not null
);
