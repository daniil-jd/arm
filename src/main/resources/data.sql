INSERT INTO PUBLIC.DETECTOR
(IP, ADDRESS, SERIAL_NUMBER, START_DATE, WARRANTY_PERIOD)
VALUES('192.168.111.10', 'Суконная Слобода', '#123', '2018-12-1', 3);

INSERT INTO PUBLIC.DETECTOR_DATA
(DETECTOR_ID, WORK_TIME, ALL_WORK_TIME, EMERGENCY_POWER_TIME, DETECTED_COUNT, POSITIVE_DETECTED_COUNT, FREQUENCY, DATE, ERRORS)
VALUES(1, 7, 12, 0, 654, 302, 10000, '2018-12-12', '');