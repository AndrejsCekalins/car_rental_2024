INSERT INTO classifiers(title, description)
VALUES('VEHICLE_TYPE', 'Car typ classifier');

INSERT INTO classifier_values(
	classifier_id,
    ic,
    description)
SELECT
	cl.id,
    'CAR_OPTIMUM',
    'Car rent optimum vehicle type'
 FROM classifiers as cl
 WHERE cl.title = 'VEHICLE_TYPE';

INSERT INTO classifier_values(
	classifier_id,
    ic,
    description)
SELECT
	cl.id,
    'CAR_PREMIUM',
    'Car rent premium vehicle type'
 FROM classifiers as cl
 WHERE cl.title = 'VEHICLE_TYPE';

INSERT INTO classifier_values(
	classifier_id,
    ic,
    description)
SELECT
	cl.id,
    'CAR_VAN',
    'Car rent van vehicle type'
 FROM classifiers as cl
 WHERE cl.title = 'VEHICLE_TYPE';

INSERT INTO classifier_values(
	classifier_id,
    ic,
    description)
SELECT
	cl.id,
    'CAR_LUX',
    'Car rent luxury vehicle type'
 FROM classifiers as cl
 WHERE cl.title = 'VEHICLE_TYPE';

 INSERT INTO classifier_values(
 	classifier_id,
     ic,
     description)
 SELECT
 	cl.id,
     'CAR_SPORT',
     'Car rent sport vehicle type'
  FROM classifiers as cl
  WHERE cl.title = 'VEHICLE_TYPE';