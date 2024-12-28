INSERT INTO classifiers(title, description)
VALUES('CAR_TYPE', 'Car typ classifier');

INSERT INTO classifier_values(
	classifier_id,
    ic,
    description)
SELECT
	cl.id,
    'CAR_OPTIMUM',
    'Car rent optimum car type'
 FROM classifiers as cl
 WHERE cl.title = 'CAR_TYPE';

INSERT INTO classifier_values(
	classifier_id,
    ic,
    description)
SELECT
	cl.id,
    'CAR_PREMIUM',
    'Car rent premium car type'
 FROM classifiers as cl
 WHERE cl.title = 'CAR_TYPE';

INSERT INTO classifier_values(
	classifier_id,
    ic,
    description)
SELECT
	cl.id,
    'CAR_VAN',
    'Car rent van car type'
 FROM classifiers as cl
 WHERE cl.title = 'CAR_TYPE';

INSERT INTO classifier_values(
	classifier_id,
    ic,
    description)
SELECT
	cl.id,
    'CAR_LUX',
    'Car rent luxury car type'
 FROM classifiers as cl
 WHERE cl.title = 'CAR_TYPE';

 INSERT INTO classifier_values(
 	classifier_id,
     ic,
     description)
 SELECT
 	cl.id,
     'CAR_SPORT',
     'Car rent sport car type'
  FROM classifiers as cl
  WHERE cl.title = 'CAR_TYPE';